/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Vector;

import org.eclipse.swt.SWT;

import swingwt.awt.BorderLayout;
import swingwt.awt.Component;
import swingwt.awt.Dimension;
import swingwt.awt.FlowLayout;
import swingwt.awt.Frame;
import swingwt.awt.GridLayout;
import swingwt.awt.event.ActionEvent;
import swingwt.awt.event.ActionListener;
import swingwt.awt.event.ItemEvent;
import swingwt.awt.event.ItemListener;
import swingwt.awt.event.MouseAdapter;
import swingwt.awt.event.MouseEvent;
import swingwtx.swing.border.EmptyBorder;
import swingwtx.swing.filechooser.FileView;



/**
 * JFileChooser is now a custom control (like Swing's), however
 * if you only use the showXDialog() methods without calling
 * setAccessory() or setExtensions(), you will get a native
 * platform file chooser dialog instead.
 *
 * You can force native or non-native by creating an instance
 * of JFileChooser and doing [instance].useNative = true/false
 *
 * This hopefully supplies the best of both worlds.
 *
 * FIXME: Does not support multiple file selection or directories
 *        in custom mode (works fine for platform native)
 *
 * @author Robin Rawson-Tetley
 */
public class JFileChooser extends JPanel {

    public static final int OPEN_DIALOG = 0;
    public static final int SAVE_DIALOG = 1;
    public static final int CUSTOM_DIALOG = 2;

    public static final int CANCEL_OPTION = 1;
    public static final String CANCEL_SELECTION = "CANCEL";
    public static final int APPROVE_OPTION = 0;

    public static final int ERROR_OPTION = -1;
    public static final int FILES_ONLY = 0;
    public static final int DIRECTORIES_ONLY = 1;
    public static final int FILES_AND_DIRECTORIES = 2;

    // Property strings
    public static final String ACCESSORY_CHANGED_PROPERTY = "AccessoryChangedProperty";
    public static final String ACCEPT_ALL_FILE_FILTER_USED_CHANGED_PROPERTY = "acceptAllFileFilterUsedChanged";
    public static final String DIALOG_TITLE_CHANGED_PROPERTY = "DialogTitleChangedProperty";
    public static final String DIALOG_TYPE_CHANGED_PROPERTY = "DialogTypeChangedProperty";
    public static final String CHOOSABLE_FILE_FILTER_CHANGED_PROPERTY = "ChoosableFileFilterChangedProperty";
    public static final String FILE_VIEW_CHANGED_PROPERTY = "fileViewChanged";
    public static final String FILE_HIDING_CHANGED_PROPERTY = "FileHidingChanged";
    public static final String FILE_FILTER_CHANGED_PROPERTY = "fileFilterChanged";
    public static final String FILE_SELECTION_MODE_CHANGED_PROPERTY = "fileSelectionChanged";
    public static final String APPROVE_BUTTON_TEXT_CHANGED_PROPERTY = "ApproveButtonTextChangedProperty";
    public static final String APPROVE_BUTTON_TOOL_TIP_TEXT_CHANGED_PROPERTY = "ApproveButtonToolTipTextChangedProperty";
    public static final String APPROVE_BUTTON_MNEMONIC_CHANGED_PROPERTY = "ApproveButtonMnemonicChangedProperty";
    public static final String CONTROL_BUTTONS_ARE_SHOWN_CHANGED_PROPERTY = "ControlButtonsAreShownChangedProperty";
    public static final String DIRECTORY_CHANGED_PROPERTY = "directoryChanged";
    public static final String SELECTED_FILE_CHANGED_PROPERTY = "SelectedFileChangedProperty";
    public static final String SELECTED_FILES_CHANGED_PROPERTY = "SelectedFilesChangedProperty";
    public static final String MULTI_SELECTION_ENABLED_CHANGED_PROPERTY = "MultiSelectionEnabledChangedProperty";
    public static final String FILE_SYSTEM_VIEW_CHANGED_PROPERTY = "FileSystemViewChanged";

    protected ImageIcon dirIcon = SwingWTUtils.getPixmap(JFileChooser.class, "dir.png");
    protected ImageIcon fileIcon = SwingWTUtils.getPixmap(JFileChooser.class, "file.png");

    protected int dialogType = OPEN_DIALOG;
    protected String dialogTitle = "";
    protected int fileSelection = FILES_ONLY;
    protected File curPath = new File("");
    protected File curFile = new File("");
    protected File[] curFiles = null;
    protected boolean multiSelect = false;
    protected String[] filters = null;
    protected String[] filterNames = null;

    protected Vector fileFilters = new Vector();

    /**
     *  Whether or not to use native chooser - is set to false if setAccessory()
     *  or setExtensions() is called. Since native ones can't support this.
     *
     *  If this is embedded in a container as a component, then this value is ignored
     *  anyway. It is only relevant for showOpenDialog()/showSaveDialog()
     *
     *  You can set this value directly prior to calling a show method to force
     *  the component to use either native, or custom dialogs as you wish if you don't
     *  want it to intelligently work out what to use.
     */
    public boolean useNative = true;

    // Controls used for presenting our file chooser when not
    // using the native platform one.
    protected JTextField location = null;
    protected JComboBox filter = null;
    protected JPanel centralPanel = null;
    protected JListTable fileList = null;
    protected JPanel buttonPanel = null;
    protected JLabel locationLabel = null;
    protected JButton okButton = null;
    protected JComponent fcAccessory = null;
    protected FileChooserDialog customDialog = null;
    protected int dialogReturnValue = CANCEL_OPTION;
    protected JFileChooser.FileFilterWrapper allFiles = null;
    
    /**
     * Support for remembering the path of the last instantiated file chooser.
     * This is not Swing functionality - it is added here for convenience.
     */
    private static File lastDir = null;
    private static boolean useLastDirectory = false;
    public static void setUseLastDirectory(boolean b) {
        useLastDirectory = b;
    }

    /** Creates a new instance of JFileChooser */
    public JFileChooser() {
        this(useLastDirectory && lastDir != null ? lastDir : new File(""));
    }
    public JFileChooser(String currentDirectoryPath) {
        this(new File(currentDirectoryPath));
    }
    public JFileChooser(File currentDirectory) {
        curPath = currentDirectory;
        curFile = currentDirectory;
        layoutComponent();
    }

    public File getSelectedFile() { return curFile; }
    public void setSelectedFile(File file) { curFile = file; ensureFileIsVisible(file); }

    public File[] getSelectedFiles() { return curFiles; }
    public void setSelectedFiles(File[] selectedFiles) { curFiles = selectedFiles; }

    public File getCurrentDirectory() { return curPath; }
    public void setCurrentDirectory(File dir) { curPath = dir; ensureFileIsVisible(dir); }
    public void changeToParentDirectory() { setCurrentDirectory(curPath.getParentFile()); }

    /**
     * Scans "drives" according to platform. UNIX platforms have / (root), plus
     * the contents of the /mnt directory, along with /cdrom and /cdrom1 if
     * they exist.
     *
     * Win32 machines we scan a: through to z: and any that exist are added
     * to the list.
     *
     * I am well aware that this is a quick and dirty solution that will work
     * for probably 99% of PC owners with Windows and a popular *nix distro
     * (Mandrake, Red Hat, Debian, FreeBSD are fine with this stuff)
     */
    public void scanDrives(JComboBox driveBox) {

        File[] f = File.listRoots();
        for (int i = 0; i < f.length; i++) {
            checkDriveEntry(f[i].getAbsolutePath(), f[i].getAbsolutePath(), driveBox);
        }

        /*
        if (SwingWTUtils.isWindows()) {
            // Don't check floppies as Win2K+ barf with stupid messages
            // to insert one - HOW STUPID?
            driveBox.addItem(new DriveEntry(new File("A:"), "Floppy Disk (A:)"));
            checkDriveEntry("C:", "Disk (C:)", driveBox);
            checkDriveEntry("D:", "Disk (D:)", driveBox);
            checkDriveEntry("E:", "Disk (E:)", driveBox);
            checkDriveEntry("F:", "Disk (F:)", driveBox);
            checkDriveEntry("G:", "Disk (G:)", driveBox);
            checkDriveEntry("H:", "Disk (H:)", driveBox);
            checkDriveEntry("I:", "Disk (I:)", driveBox);
            checkDriveEntry("J:", "Disk (J:)", driveBox);
            checkDriveEntry("K:", "Disk (K:)", driveBox);
            checkDriveEntry("L:", "Disk (L:)", driveBox);
            checkDriveEntry("M:", "Disk (M:)", driveBox);
            checkDriveEntry("N:", "Disk (N:)", driveBox);
            checkDriveEntry("O:", "Disk (O:)", driveBox);
            checkDriveEntry("P:", "Disk (P:)", driveBox);
            checkDriveEntry("Q:", "Disk (Q:)", driveBox);
            checkDriveEntry("R:", "Disk (R:)", driveBox);
            checkDriveEntry("S:", "Disk (S:)", driveBox);
            checkDriveEntry("T:", "Disk (T:)", driveBox);
            checkDriveEntry("U:", "Disk (U:)", driveBox);
            checkDriveEntry("V:", "Disk (V:)", driveBox);
            checkDriveEntry("W:", "Disk (W:)", driveBox);
            checkDriveEntry("X:", "Disk (X:)", driveBox);
            checkDriveEntry("Y:", "Disk (Y:)", driveBox);
            checkDriveEntry("Z:", "Disk (Z:)", driveBox);
            // Make sure the first selected entry is the first drive (ie. Not floppy)
            // so that the list is synchronised.
            driveBox.setSelectedIndex(1);
        }
        else
        {
            checkDriveEntry("/", "Root (/)", driveBox);
            checkDriveEntry("/mnt/auto", "Automatic Mount (/mnt/auto)", driveBox);
            checkDriveEntry("/floppy", "Floppy Disk (/floppy)", driveBox);
            checkDriveEntry("/mnt/floppy", "Floppy Disk (/mnt/floppy)", driveBox);
            checkDriveEntry("/media/floppy", "Floppy Disk (/media/floppy)", driveBox);
            checkDriveEntry("/cdrom", "CDROM (/cdrom)", driveBox);
            checkDriveEntry("/cdrom1", "CDROM (/cdrom1)", driveBox);
            checkDriveEntry("/media/cdrom", "CDROM (/media/cdrom)", driveBox);
            checkDriveEntry("/media/cdrom1", "CDROM (/media/cdrom1)", driveBox);
            checkDriveEntry("/mnt/cdrom", "CDROM (/mnt/cdrom)", driveBox);
            checkDriveEntry("/mnt/cdrom1", "CDROM (/mnt/cdrom1)", driveBox);
            checkDriveEntry("/mnt/dvd", "DVD ROM (/mnt/dvd)", driveBox);
            checkDriveEntry("/mnt/windows", "Windows Disk (/mnt/windows)", driveBox);
            checkDriveEntry("/mnt/win_c", "Windows Disk (/mnt/win_c)", driveBox);
            checkDriveEntry("/mnt/win_d", "Windows Disk (/mnt/win_d)", driveBox);
            checkDriveEntry("/mnt/removable", "Removable Device (/mnt/removable)", driveBox);
            checkDriveEntry("/mnt/usbdisk", "USB Disk (/mnt/usbdisk)", driveBox);
            checkDriveEntry("/mnt/zip", "Zip Disk (/mnt/zip)", driveBox);
        }
        */

    }

    /**
     * Checks if the specified file path exists and if it does, creates
     * a <code>DriveEntry</code> object, wraps the path and description
     * up with it and loads it into the drives combo box for display.
     */
    protected void checkDriveEntry(String path, String displayName, JComboBox driveBox) {
        try {
            File f = new File(path);
            if (f.exists())
                driveBox.addItem(new DriveEntry(f, displayName));
        }
        catch (Exception e) {}
    }

    /**
     * Refreshes the on-screen list for the current location
     */
    public void rescanCurrentDirectory() {

        // Drop out if the filter box isn't created yet
        if (filter == null) return;

        try {

            File loc = new File(curFile.getAbsolutePath());

            // Back up if it's not a directory
            if (!loc.isDirectory())
                loc = curFile.getParentFile();

            // Drop out if we don't have a directory (getParent() returns
            // null if the File object has no directory.
            if (loc == null) return;

            // Filter the list of files according to the filter (if any) chosen
            File[] fulldir = loc.listFiles();
            Vector matches = new Vector();
            JFileChooser.FileFilterWrapper w = (JFileChooser.FileFilterWrapper) filter.getSelectedItem();
            swingwtx.swing.filechooser.FileFilter theFilter = null;
            if (w != null) theFilter = w.getFilter();

            for (int i = 0; i < fulldir.length; i++) {
                if (fulldir[i].getName().startsWith("."))
                    i=i; // Ignore hidden files
                else if (theFilter == null)
                    matches.add(fulldir[i]);
                else if (theFilter.accept(fulldir[i]))
                    matches.add(fulldir[i]);
            }

            boolean needsParent = (loc.getParentFile() != null);

            File[] filteredDir = null;

            int noFiles = ( needsParent ? matches.size() + 1 : matches.size() );
            filteredDir = new File[noFiles];

            // Add the ".." directory
            if (needsParent)
                filteredDir[0] = curFile.getAbsoluteFile().getParentFile();

            for (int i = 0; i < matches.size(); i++) {
                if (needsParent)
                    filteredDir[i + 1] = (File) matches.get(i);
                else
                    filteredDir[i] = (File) matches.get(i);
            }

            // Sort the list
            sortFiles(filteredDir);

            // Add them to the list
            fileList.setListData( filteredDir );
            
            // Display the location
            locationLabel.setText(loc.getAbsolutePath());

        }
        catch (StringIndexOutOfBoundsException e) {
            // I hate having to write special case handlers, but there is a nasty
            // bug in the GNU Classpath stuff that prevents reading of parent directories
            // -- this gets around it. Besides, this exception type shouldn't ever be thrown
            // up to the parent.
            e.printStackTrace();
        }

    }

    public void ensureFileIsVisible(File f) { /* FIXME: Find and display file */ }

    public boolean getControlButtonsAreShown() { return buttonPanel.getParent() != null; }
    public void setControlButtonsAreShown(boolean b) {
        if (b)
            add(buttonPanel, BorderLayout.SOUTH);
        else
            remove(buttonPanel);
    }
    public int getDialogType() { return dialogType; }
    public void setDialogType(int dialogType) { this.dialogType = dialogType; }
    public void setDialogTitle(String dialogTitle) { this.dialogTitle = dialogTitle; }
    public String getDialogTitle() { return dialogTitle; }
    public void setApproveButtonToolTipText(String toolTipText) { okButton.setToolTipText(toolTipText); }
    public String getApproveButtonToolTipText() { return okButton.getToolTipText(); }
    public void setApproveButtonMnemonic(int mnemonic) { okButton.setMnemonic(mnemonic); }
    public void setApproveButtonMnemonic(char mnemonic) { okButton.setMnemonic(mnemonic); }
    public void setApproveButtonText(String approveButtonText) { okButton.setText(approveButtonText); }
    public String getApproveButtonText() { return okButton.getText(); }
    public JComponent getAccessory() { return fcAccessory; }
    public void setAccessory(JComponent newAccessory) { useNative = false; fcAccessory = newAccessory; centralPanel.add(newAccessory, BorderLayout.EAST); }
    public void setFileSelectionMode(int mode) { fileSelection = mode; }
    public int getFileSelectionMode() { return fileSelection; }
    public boolean isFileSelectionEnabled() {return (fileSelection == FILES_ONLY || fileSelection == FILES_AND_DIRECTORIES);}
    public boolean isDirectorySelectionEnabled() { return (fileSelection == FILES_AND_DIRECTORIES || fileSelection == DIRECTORIES_ONLY);}
    public void setMultiSelectionEnabled(boolean b) { multiSelect = b; }
    public boolean isMultiSelectionEnabled() { return multiSelect; }
    public boolean isFileHidingEnabled() { return false;}
    public void setFileHidingEnabled(boolean b) {}

    /**
     * NOT A REAL SWING METHOD - THIS SETS FILTERS FOR THE PLATFORM
     * DIALOGS INSTEAD OF OUR CUSTOM FILECHOOSER.
     *
     * Because setting Swing-style filters trips use of our custom
     * dialog instead of the platform-native one, you can use this
     * method instead to add filters to the native dialogs.
     */
    public void setExtensionFilters(String[] extensions, String[] names) {
        filters = extensions;
        filterNames = names;
    }

    public void setFileFilter(swingwtx.swing.filechooser.FileFilter fileFilter) {
        useNative = false;
        FileFilterWrapper wrapper = new FileFilterWrapper(fileFilter);
        filter.addItem(wrapper);
        filter.setSelectedItem(wrapper);
    }

    public swingwtx.swing.filechooser.FileFilter getFileFilter() { return (swingwtx.swing.filechooser.FileFilter) filter.getSelectedItem(); }
    public swingwtx.swing.filechooser.FileFilter[] getChoosableFileFilters() {
        swingwtx.swing.filechooser.FileFilter[] ff = new swingwtx.swing.filechooser.FileFilter[fileFilters.size()];
        for (int i = 0; i < fileFilters.size(); i++) {
            ff[i] = (swingwtx.swing.filechooser.FileFilter) fileFilters.get(i);
        }
        return ff;
    }
    public void addChoosableFileFilter(swingwtx.swing.filechooser.FileFilter f) {
        JFileChooser.FileFilterWrapper w = new JFileChooser.FileFilterWrapper(f);
        filter.addItem(w);
    }
    public boolean removeChoosableFileFilter(swingwtx.swing.filechooser.FileFilter f) {
        for (int i = 0; i < filter.getItemCount(); i++)
            if ( ((FileFilterWrapper) filter.getItemAt(i)).getFilter().equals(f) ) {
                filter.removeItemAt(i);
                 return true;
            }
        return false;
    }
    public void resetChoosableFileFilters() {
        filter.removeAllItems();
        fileFilters.removeAllElements();
    }
    public boolean isAcceptAllFileFilterUsed() { return true; }
    public void setAcceptAllFileFilterUsed(boolean b) {}

    /** Wrapper routine - calls either showNativeOpenDialog or showCustomOpenDialog
     *  depending on options chosen
     */
    public int showOpenDialog(Component parent) {
        if (useNative)
            return showNativeOpenDialog(parent);
        else
            return showCustomOpenDialog(parent);
    }
   
    /** Displays the file chooser with the appropriate approval button text */
    public int showDialog(Component parent, String approveButtonText) {
        dialogType = OPEN_DIALOG;
        String dt = (dialogTitle.equals("") ? "Open" : dialogTitle);
        okButton.setText(approveButtonText);
        customDialog = new FileChooserDialog(dt, this);
        rescanCurrentDirectory();   // perhaps not the best place but now its consistent
        customDialog.show();
        post_init();
        return dialogReturnValue;
    }
    
    /**
     * This is a convenience method to the DirectoryDialog SWT class.  I'm sure
     * it can be integrated better into JFileChooser (with the directory selection
     * flag), but one problem is it doesn't allow multi-select.  So, here's a simple
     * wrapper for those who need it...
     * @param parent
     * @return Directory name selected by user.  NULL if nothing selected (cancel pressed)
     */
    public String showDirectorySelectDialog(Component parent, String message) {
        org.eclipse.swt.widgets.DirectoryDialog d =
            new org.eclipse.swt.widgets.DirectoryDialog(parent.getSWTPeer().getShell());
        if (dialogTitle.equals(""))
            d.setText("Select a directory");
        else
            d.setText(dialogTitle);
        
        d.setMessage(message);
 
        if (curPath == null) curPath = new File("");
        d.setFilterPath(curPath.getAbsolutePath());

        post_init();
        
        return d.open();
    }

    protected int showCustomOpenDialog(Component parent) {
        dialogType = OPEN_DIALOG;
        String dt = (dialogTitle.equals("") ? "Open" : dialogTitle);
        customDialog = new FileChooserDialog(dt, this);
        rescanCurrentDirectory();   // perhaps not the best place but now its consistent
        customDialog.show();
        
        post_init();
        
        return dialogReturnValue;
    }

    protected int showNativeOpenDialog(Component parent) {
	
	// Platform select directory dialog
        if (fileSelection == JFileChooser.DIRECTORIES_ONLY) {
	    org.eclipse.swt.widgets.DirectoryDialog d = new org.eclipse.swt.widgets.DirectoryDialog(
	        parent.getSWTPeer().getShell(), SWT.OPEN | (multiSelect ? SWT.MULTI : SWT.NONE));
	        if (dialogTitle.equals("")) 
		    d.setText("Open");
		else
		    d.setText(dialogTitle);
		d.setFilterPath(curPath.getAbsolutePath());
		String chosen = d.open();
		
        post_init();
        
		if (chosen == null)
		     return CANCEL_OPTION;
		else {
		     curPath = new File(chosen);
		     curFile = new File(chosen);
		     return APPROVE_OPTION;
		}
	}																					 
	// Regular open dialog -----------
        dialogType = OPEN_DIALOG;
        org.eclipse.swt.widgets.FileDialog f = new org.eclipse.swt.widgets.FileDialog(parent.getSWTPeer().getShell(), SWT.OPEN | (multiSelect ? SWT.MULTI : SWT.NONE) );
        if (dialogTitle.equals(""))
            f.setText("Open");
        else
            f.setText(dialogTitle);
 
        if (curPath == null) curPath = new File("");
        f.setFilterPath(curPath.getAbsolutePath());
        
        if (filters != null) {
            f.setFilterExtensions(filters);
            f.setFilterNames(filterNames);
        }
        String chosen = f.open();
        if (chosen == null) return CANCEL_OPTION;
        // If it's a multi-select, set the files
        // 3.4.2004; aweber : in normal java swing curFile is the first selected File here it's the last but i
        //                    think this doesn't matter

        if (multiSelect) {
            curFile = new File(chosen);
            curFiles = new File[f.getFileNames().length];
            for (int i = 0; i < f.getFileNames().length; i++) {
                curFiles[i] = new File(f.getFilterPath(), f.getFileNames()[i]);
                curFiles[i] = new File(curFiles[i].getAbsolutePath());
            }
            curPath = curFile.getParentFile();
        }
        else {
            curFile = new File(chosen);
            curPath = curFile.getParentFile();
        }
		
        post_init();
        
        return APPROVE_OPTION;
    }

    public int showSaveDialog(Component parent) {
        if (useNative)
            return showNativeSaveDialog(parent);
        else
            return showCustomSaveDialog(parent);
    }

    protected int showNativeSaveDialog(Component parent) {
        dialogType = SAVE_DIALOG;
        org.eclipse.swt.widgets.FileDialog f = new org.eclipse.swt.widgets.FileDialog(parent.getSWTPeer().getShell(), SWT.SAVE );
        if (dialogTitle.equals(""))
            f.setText("Save As");
        else
            f.setText(dialogTitle);
        f.setFilterPath(curPath.getAbsolutePath());
        if (filters != null) {
            f.setFilterExtensions(filters);
            f.setFilterNames(filterNames);
        }
	if (curFile == null) curFile = new File("");
	f.setFileName(curFile.getName());

        String chosen = f.open();
        if (chosen == null) return CANCEL_OPTION;
        // If it's a multi-select, set the files
        // 3.4.2004; aweber : in normal java swing curFile is the first selected File here it's the last but i
        //                    think this doesn't matter

        if (multiSelect) {
            curFile = new File(chosen);
            curFiles = new File[f.getFileNames().length];
            for (int i = 0; i < f.getFileNames().length; i++) {
                curFiles[i] = new File(f.getFileNames()[i]);
            }
            curPath = curFile.getParentFile();
        }
        else {
            curFile = new File(chosen);
            curPath = curFile.getParentFile();
        }
        
        post_init();
        
        return APPROVE_OPTION;
    }

    protected int showCustomSaveDialog(Component parent) {
        dialogType = SAVE_DIALOG;
        String dt = (dialogTitle.equals("") ? "Save" : dialogTitle);
        customDialog = new FileChooserDialog(dt, this);
        rescanCurrentDirectory();   // perhaps not the best place but now its consistent
        customDialog.show();

        // Build the file for return. curPath could be a complete file
        // path, but it might just be a directory with the name still
        // in the box
        if (curPath.isDirectory()) {
            String newFile = curPath.getAbsolutePath();
            if (!newFile.endsWith(File.separator))
                newFile += File.separator;
            curPath = new File(newFile + location.getText());
        }
		
        post_init();

        return dialogReturnValue;
    }

    /**
     * 
     * TODO Comment!!
     *
     * @param b
     */
    public void setDragEnabled(boolean b) {
    	
    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @param fileView
     */
    public void setFileView(FileView fileView) {
    	
    }
    
    public String getTitle() { return dialogTitle; }
    public void setTitle(String title) { dialogTitle = title; }

    /**
     * Sorts an array of Java <code>File</code> references
     * into alphabetical and directory order.
     * Crude bubblesort algorithm.
     */
    public File[] sortFiles(File[] sort) {

        boolean madeAChange = true;
        while (madeAChange) {
            madeAChange = false;
            for (int i = 0; i < sort.length-1; i++) {
                // Directories take precedence, so if we have a 
                // non-directory lower than a directory, we swap them
                // too.
                if ((!sort[i].isDirectory()) && sort[i+1].isDirectory()) {
                    File buf = sort[i+1];
                    sort[i+1] = sort[i];
                    sort[i] = buf;
                    buf = null;
                    madeAChange = true;
                }

		// Compare the names
		int pos = sort[i].toString().compareTo(sort[i+1].toString());

		// If moving them wouldn't put a non-directory in front
		// of a directory, do it
                if ( pos > 0 ) {
		    if (sort[i].isDirectory() && !sort[i+1].isDirectory()) {
		        // Swapping here would put a non-directory in front
			// of a directory, so do nothing.
		    }
		    else {
                        File buf = sort[i+1];
                        sort[i+1] = sort[i];
                        sort[i] = buf;
                        buf = null;
                        madeAChange = true;
                    }
		}
            }
        }
        return sort;
    }

    /**
     * Sends property change event to all listeners
     */
    protected void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
        PropertyChangeEvent e = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
        for (int i = 0; i < propertyChangeListeners.size(); i++) {
            ((PropertyChangeListener) propertyChangeListeners.get(i)).propertyChange(e);
        }
	forceRelayout();
    }

    /**
     * Lays out the whole thing - this is really for problems with accessories not resizing.
     */
    protected void forceRelayout() {
        SwingUtilities.invokeSync(new Runnable() {
	    public void run() {
		 int x = customDialog.getSize().width;
		 int y = customDialog.getSize().height;
		 x++; y++;
		 customDialog.setSize(x, y);
		 x--; y--;
		 customDialog.setSize(x, y);
	    }
	});
    }

    /**
     * Lays out native widgets for this component.
     */
    protected void layoutComponent() {

        // Pixel gap between components
        final int MGN = 2;

        setLayout(new BorderLayout(MGN, MGN));

        // NORTH - Drives, CENTER - centre pane with table and accessory, SOUTH - file, type, Ok and Cancel
        JPanel pnlTool = new JPanel(new BorderLayout(MGN, MGN));
        pnlTool.setBorder(new EmptyBorder(MGN, MGN, MGN, MGN));

        // Drives selector
        final JComboBox drives = new JComboBox();
        drives.setPreferredSize(new Dimension(200, 25));
        scanDrives(drives);
        drives.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                DriveEntry d = (DriveEntry) drives.getSelectedItem();

                // Update where we are
                curFile = (File) d.fileRef;
                rescanCurrentDirectory();
            }
        });
        // If we only have one drive, don't bother showing the
        // drives box
        if (drives.getItemCount() > 1) {
            pnlTool.add(new JLabel("Drive:"), BorderLayout.WEST);
            pnlTool.add(drives, BorderLayout.CENTER);
        }
                
        // Current location
        JPanel pnlLocationLabel = new JPanel();
        pnlLocationLabel.setBorder(new EmptyBorder(MGN, MGN, MGN, MGN));
        pnlLocationLabel.setLayout(new BorderLayout(MGN, MGN));
        locationLabel = new JLabel();
        locationLabel.setText(curPath.getAbsolutePath());
        pnlLocationLabel.add(locationLabel, BorderLayout.CENTER);
        pnlTool.add(pnlLocationLabel, BorderLayout.SOUTH);

        // Toolbar
        add(pnlTool, BorderLayout.NORTH);


        // ===== CENTRAL PANEL - HOLDS FILE LIST AND ENTRY BOXES ===============
        centralPanel = new JPanel();
        centralPanel.setBorder(new EmptyBorder(MGN, MGN, MGN, MGN));
        centralPanel.setLayout(new BorderLayout(MGN, MGN));

        // File list component
        fileList = new JListTable();
        fileList.setCellRenderer(new JFileChooser.FileListRenderer());
        // Used for changing into directories and firing property change
        fileList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                // Assume no file selected
                curFile = null;
                // Don't do anything if there's no selection
                if (fileList.getSelectedIndex() == -1) return;
                if (fileList.getSelectedValue() == null) return;

                // Update where we are
                curFile = (File) fileList.getSelectedValue();
                curPath = curFile.getParentFile();

                // If it isn't a directory, show the name in the text entry
                // box and fire the property changed event for accessories
                if(e.getClickCount() == 2) {
                    if (!curFile.isDirectory()) {
                        location.setText( curFile.getName() );
                        firePropertyChangeEvent( SELECTED_FILE_CHANGED_PROPERTY, null, fileList.getSelectedValue());
                        okButton.processActionEvent(0); // simuate mouseclick
                    } else {
                        rescanCurrentDirectory();
                    }
                } else {
                    if (!curFile.isDirectory()) {
                        location.setText( curFile.getName() );
                        firePropertyChangeEvent( SELECTED_FILE_CHANGED_PROPERTY, null, fileList.getSelectedValue());
                    }
                }
            }
        });

        centralPanel.add(fileList, BorderLayout.CENTER);

        // Entry boxes panel
        JPanel entryBoxes = new JPanel();
        entryBoxes.setBorder(new EmptyBorder(MGN, MGN, MGN, MGN));
        entryBoxes.setLayout(new GridLayout(2, 0, MGN, MGN));

        // Location panel
        JPanel pnlLocation = new JPanel();
        pnlLocation.setBorder(new EmptyBorder(MGN, MGN, MGN, MGN));
        pnlLocation.setLayout(new BorderLayout(MGN, MGN));
        JLabel lblLoc = new JLabel("Location:");
        pnlLocation.add(lblLoc, BorderLayout.WEST);
        lblLoc.setPreferredSize(new Dimension(100, 29));

        location = new JTextField();
        pnlLocation.add(location, BorderLayout.CENTER);

        entryBoxes.add(pnlLocation);

        // Filter panel
        JPanel pnlFilter = new JPanel();
        pnlFilter.setBorder(new EmptyBorder(MGN, MGN, MGN, MGN));
        pnlFilter.setLayout(new BorderLayout(MGN, MGN));
        JLabel lblFilter = new JLabel("Filter:");
        pnlFilter.add(lblFilter, BorderLayout.WEST);
        lblFilter.setPreferredSize(new Dimension(100, 29));

        filter = new JComboBox();
        pnlFilter.add(filter, BorderLayout.CENTER);
        allFiles = new JFileChooser.FileFilterWrapper(new JFileChooser.AllFileFilter());
        filter.addItem(allFiles);
        filter.addItemListener( new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                rescanCurrentDirectory();
            }
        });

        entryBoxes.add(pnlFilter);

        centralPanel.add(entryBoxes, BorderLayout.SOUTH);


        add(centralPanel, BorderLayout.CENTER);

        // Buttons
        buttonPanel = new JPanel();
        buttonPanel.setBorder(new EmptyBorder(MGN, MGN, MGN, MGN));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        okButton = (JButton) buttonPanel.add(new JButton("Ok"));
        okButton.setMnemonic('o');
        //okButton.setPreferredSize(new Dimension(100, 35));
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialogReturnValue = APPROVE_OPTION;

                if(multiSelect) {
                    Object[] aktFiles = fileList.getSelectedValues();
                    int length = aktFiles.length;
                    curFiles = new File[length];
                    for(int i = 0; i < length; i++) {
                        curFiles[i] = (File) aktFiles[i];
                    }
                }

                if (customDialog != null) customDialog.dispose();
            }
        });

        JButton cancelButton = (JButton) buttonPanel.add(new JButton("Cancel"));
        cancelButton.setMnemonic('c');
        //cancelButton.setPreferredSize(new Dimension(100, 35));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialogReturnValue = CANCEL_OPTION;
                if (customDialog != null) customDialog.dispose();
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);

        // rescanCurrentDirectory();        // if there are file filters definied by the user they're not set at this Point
    }

    /**
     * Simple JDialog subclass so we can override size and things
     * like that.
     */
    private class FileChooserDialog extends JDialog {
        public FileChooserDialog(String title, JFileChooser fc) {
            super((Frame) null, title);
            this.setSize(520, 400);
            this.getContentPane().setLayout(new BorderLayout());
            this.getContentPane().add(fc, BorderLayout.CENTER);
            this.setModal(true);
            this.setLocationRelativeTo(null);
        }
    }

    public Icon getIcon(File file) {
        return file.isDirectory() ? dirIcon : fileIcon;
    }

    /**
     * Rendering class for showing file entries. Handles icons.
     */
    private class FileListRenderer extends JLabel implements ListCellRenderer {

        public FileListRenderer() {
            //setOpaque(true);
        }

        public swingwt.awt.Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            File f = (File) value;

            // Parent folder - special case
            if (index == 0) {
                setText("..");
                setIcon(dirIcon);
                return this;
            }

            // Name
            String name = f.getName();
            if (name.indexOf(File.separator) != -1)
                name = name.substring(name.lastIndexOf(File.separator), name.length());
            setText(name);

            // Icon
            boolean isDir = f.isDirectory();
            setIcon(isDir ? dirIcon : fileIcon);

            return this;
         }
    }

    private class AllFileFilter extends swingwtx.swing.filechooser.FileFilter {
        public boolean accept(File f) {
            return true;
        }
        public String getDescription() {
            return "All Files";
        }
    }

    /** Wraps up the FileFilter class. Why the fuck didn't the interface on that
     *  use toString() to return the description? Did they do it on purpose to make
     *  it an arse with list components or what?
     */
    private class FileFilterWrapper {
        private swingwtx.swing.filechooser.FileFilter filter = null;
        public FileFilterWrapper(swingwtx.swing.filechooser.FileFilter f) {
            filter = f;
        }
        public swingwtx.swing.filechooser.FileFilter getFilter() { return filter; }
        public String toString() {
            return filter.getDescription();
        }
    }

    // Called by showDialog methods after dialog closes.
    private void post_init() {
        lastDir = getCurrentDirectory();
    }

    /**
     * Objects stored in the Drives combo box. Encapsulates a display name
     * and a file reference.
     */
    private class DriveEntry {
        public File fileRef = null;
        public String displayName = null;
        public DriveEntry(File file, String displayName) { fileRef = file; this.displayName = displayName; }
        public String toString() { return displayName; }
    }
}
