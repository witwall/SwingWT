/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import swingwt.awt.BorderLayout;
import swingwt.awt.Component;
import swingwt.awt.Container;
import swingwt.awt.Dimension;
import swingwt.awt.Frame;
import swingwt.awt.Toolkit;
import swingwt.awt.event.ActionEvent;
import swingwt.awt.event.ActionListener;


// TODO: all showXXX methods should take swingwt.awt.Component instead of Container
//public class JOptionPane extends JPanel {
public class JOptionPane extends JPanel implements swingwtx.accessibility.Accessible{
    
    public final static int CANCEL_OPTION = SWT.CANCEL;
    public final static int YES_OPTION = SWT.YES;
    public final static int NO_OPTION = SWT.NO;
    public final static int OK_OPTION = SWT.OK;
    public final static int OK_CANCEL_OPTION = SWT.OK | SWT.CANCEL;
    public final static int CLOSED_OPTION = SWT.DEFAULT;
    public final static int DEFAULT_OPTION = SWT.DEFAULT;
    public final static int YES_NO_OPTION = SWT.YES | SWT.NO;
    public final static int YES_NO_CANCEL_OPTION = SWT.YES | SWT.NO | SWT.CANCEL;
    public final static int WARNING_MESSAGE = SWT.ICON_WARNING;
    public final static int QUESTION_MESSAGE = SWT.ICON_QUESTION;
    public final static int PLAIN_MESSAGE = SWT.DEFAULT;
    public final static int INFORMATION_MESSAGE = SWT.ICON_INFORMATION;
    public final static int ERROR_MESSAGE = SWT.ICON_ERROR;
    // Tracked with System.out.println(JOptionPane.UNINITIALIZED_VALUE.toString());
    public static final Object      UNINITIALIZED_VALUE = "uninitializedValue";
    
    protected static Object lastInputDialogReturnValue = null;
    protected static int lastInputDialogReturnIndex = -1;
    
    private static int confRetVal = 0;
    private static boolean jobFinished = false;
    private static JFrame rootFrame = null;
    
    public static final String      ICON_PROPERTY = "icon";
    public static final String      MESSAGE_PROPERTY = "message";
    public static final String      VALUE_PROPERTY = "value";
    public static final String      OPTIONS_PROPERTY = "options";
    public static final String      INITIAL_VALUE_PROPERTY = "initialValue";
    public static final String      MESSAGE_TYPE_PROPERTY = "messageType";
    public static final String      OPTION_TYPE_PROPERTY = "optionType";
    public static final String      SELECTION_VALUES_PROPERTY = "selectionValues";
    public static final String      INITIAL_SELECTION_VALUE_PROPERTY = "initialSelectionValue";
    public static final String      INPUT_VALUE_PROPERTY = "inputValue";
    public static final String      WANTS_INPUT_PROPERTY = "wantsInput";

    protected boolean                         wantsInput;
    transient protected Object                value;
    transient protected Object                initialValue;
    protected transient Object[]              selectionValues;
    protected transient Object                inputValue;
    protected transient Object                initialSelectionValue;

    
    // ---------------- Instance stuff
    protected Object message = null;
    protected int messageType = INFORMATION_MESSAGE;
    private int dOptionType = 0;
    private Icon dIcon = null;
    private Object[] dOptions = null;
    private Object dInitialValue = null;
    private Object dValue = null;
    private Object dInputValue = null;
    
    public JOptionPane() {  }
    public JOptionPane(Object message, int messageType) { this(message, messageType, 0); }
    public JOptionPane(Object message, int messageType, int optionType) { this(message, messageType, optionType, null); }
    public JOptionPane(Object message, int messageType, int optionType, Icon icon) { this(message, messageType, optionType, icon, null); }
    public JOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options) { this(message, messageType, optionType, icon, options, null); }
    public JOptionPane(Object message, int messageType, int optionType, Icon icon, Object[] options, Object initialValue) { 
        this.message = message;
        this.messageType = messageType;
        dOptionType = optionType;
        dIcon = icon;
        dOptions = options;
        dInitialValue = initialValue;
    }
    
    public Object getMessage() { return message; }
    public int getMessageType() { return messageType; }
    public int getOptionType() { return dOptionType; }
    public Icon getIcon() { return dIcon; }
    public Object[] getOptions() { return dOptions; }
    public Object getInitialValue() { return dInitialValue; }
    public Object getValue() { return dValue; }
    public Object getInputValue() { return dInputValue; }
    public void setMessage(Object message) { this.message = message; }
    public void setMessageType(int messageType) { this.messageType = messageType; }
    public void setOptionType(int optionType) { dOptionType = optionType; }
    public void setIcon(Icon icon) { dIcon = icon; }
    public void setOptions(Object[] options) { dOptions = options; }
    public void setInitialValue(Object initialValue) { dInitialValue = initialValue; }
    public void setValue(Object value) { dValue = value; }
    public void setInputValue(Object value) { dInputValue = value; }
    
    /** TODO: Not implemented. Needs to translate settings to a real dialog */
    public JDialog createDialog(Component parent, String title) {
        return null;
    }
    
    /** TODO: Not implemented. Needs to translate settings to a real internal frame */
    public JInternalFrame createInternalFrame(Component parent, String title) {
        return null;
    }
    
    // TODO: More to come due to that a lot of code is missing
    public void setWantsInput(boolean newValue) {
        boolean            oldValue = wantsInput;

        wantsInput = newValue;
        //firePropertyChange(WANTS_INPUT_PROPERTY, oldValue, newValue);
    }
    public boolean getWantsInput() {
        return wantsInput;
    }
    // Dummy ?
    public void selectInitialValue() 
    {
/*    	
        OptionPaneUI         ui = getUI();
        if (ui != null) 
    	{
            ui.selectInitialValue(this);
        }
*/         
    }

    public void setSelectionValues(Object[] newValues) {
        Object[]           oldValues = selectionValues;

        selectionValues = newValues;
        //firePropertyChange(SELECTION_VALUES_PROPERTY, oldValues, newValues);
        if(selectionValues != null)
            setWantsInput(true);
    }
  
    public Object[] getSelectionValues() {
        return selectionValues;
    }
    public void setInitialSelectionValue(Object newValue) {
        Object          oldValue = initialSelectionValue;
        initialSelectionValue = newValue;
        //firePropertyChange(INITIAL_SELECTION_VALUE_PROPERTY, oldValue,
        //                   newValue);
    }

    // ------------------- Static stuff

    public static Frame getFrameForComponent(Component parentComponent) {
    	Component current = parentComponent;
    	while(current != null && !(current instanceof Frame)) {
    		current = current.getParent();
    	}
    	
    	if(current == null) {
    		return rootFrame;
    	}
    	
    	return (Frame)current;
    }
    
    public static void setRootFrame(JFrame frame) {
        rootFrame = frame;
    }
    
    public static JFrame getRootFrame() {
        return rootFrame;
    }
    
//  showConfirmdialog is changed and added some Title  and Icon (null)
    // TODO Fix the icon
    public static int showConfirmDialog(swingwt.awt.Component parent, Object message) {
        return showConfirmDialog(parent, message, "", QUESTION_MESSAGE);    
    }
    public static int showConfirmDialog(swingwt.awt.Component parent, Object message, String title, int messageType) {
        return showConfirmDialog(parent, message, title, 0, messageType);
    }
    
    public static int showConfirmDialog(final swingwt.awt.Component parent, final Object message, final String title, final int messageType, final int buttons) {
        return showConfirmDialog(parent, message, title, buttons,messageType,null);
    }
    public static int showConfirmDialog(final swingwt.awt.Component parent, final Object message,
            final String title, final int buttons, final int messageType, final Icon icon) 
    {
    	confRetVal = 0;
    	if (parent instanceof Container)
	    	SwingUtilities.invokeSync(new Runnable() {
	    		public void run() {
	    			Shell container = getModalParent((Container) parent);
	    			MessageBox m = new MessageBox(container, messageType | SWT.YES | SWT.NO);
	    			m.setMessage(getOptionPaneMessage(message));
	    			m.setText(title);
	    			
	    			confRetVal = m.open();
	    			switch (confRetVal) {
	    			case SWT.YES: confRetVal = YES_OPTION; break;
	    			case SWT.NO: confRetVal = NO_OPTION; break;
	    			}
	    		}
	    	});
    	return confRetVal;
    }
    
    public static void showMessageDialog(swingwt.awt.Component parent, Object message) {
        showMessageDialog(parent, message, "Message", SWT.ICON_INFORMATION);
    }
    
    /** FIXME: Where the fuck is the title? */
    public static void showMessageDialog(final swingwt.awt.Component parent, final Object message, final String title, final int messageType) {
    	if (parent instanceof Container)
	        SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
	                MessageBox m = new MessageBox(getModalParent((Container) parent), messageType | SWT.OK);
	                m.setMessage(getOptionPaneMessage(message));
	                m.setText(title);
	                m.open();
	            }
	        });
    }
    // Internal fake to ordinary messageDialog
    public static void showInternalMessageDialog(swingwt.awt.Container parent, Object message) {
        showInternalMessageDialog(parent, message, "Information", SWT.ICON_INFORMATION);
    }
    public static void showInternalMessageDialog(final swingwt.awt.Component parent, final Object message, final String title, final int messageType) {
        showInternalMessageDialog((Container) parent, message, title, messageType);
    }
    public static void showInternalMessageDialog(final swingwt.awt.Container parent, final Object message, final String title, final int messageType) {
        showMessageDialog(parent,message,title,messageType);
    }
    public static String showInputDialog(swingwt.awt.Component parent, Object message) { 
        SwingStyleInputDialog dlg = new SwingStyleInputDialog(parent, getOptionPaneMessage(message), "Input", 0, null, null, null);
        dlg.show();
        if (lastInputDialogReturnValue == null)
            return null;
        else
            return lastInputDialogReturnValue.toString();
    }
    public static String showInputDialog(swingwt.awt.Component parent, Object message, Object initialSelectionValue) { 
        SwingStyleInputDialog dlg = new SwingStyleInputDialog(parent, getOptionPaneMessage(message), "Input", 0, null, null, initialSelectionValue);
        dlg.show();
        if (lastInputDialogReturnValue == null)
            return null;
        else
            return lastInputDialogReturnValue.toString();
    }
    
    public static String showInputDialog(swingwt.awt.Component parent, Object message, String title, int messageType) { 
        SwingStyleInputDialog dlg = new SwingStyleInputDialog(parent, getOptionPaneMessage(message), title, messageType, null, null, null);
        dlg.show();
        if (lastInputDialogReturnValue == null)
            return null;
        else
            return lastInputDialogReturnValue.toString();
    }
    
    public static Object showInputDialog(final swingwt.awt.Component parent, final Object message, final String title, final int messageType, final Icon icon, final Object[] selectionValues, final Object initialSelectionValue) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                SwingStyleInputDialog dlg = new SwingStyleInputDialog(parent, getOptionPaneMessage(message), title, messageType, icon, selectionValues, initialSelectionValue);
                dlg.show();
            }
        });
        return lastInputDialogReturnValue;
    }
    
    public static int showOptionDialog(final swingwt.awt.Component parent, final Object message, final String title, final int optionType, final int messageType,
                                       final Icon icon, final Object[] options, final Object initialValue) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                SwingStyleInputDialog dlg = new SwingStyleInputDialog(parent, getOptionPaneMessage(message), title, messageType, icon, options, initialValue);
                dlg.show();
            }
        });
        return lastInputDialogReturnIndex;
    }
    
    /**
     * Handles Swing-style messages, strips HTML and converts
     * String arrays to a single string
     */
    protected static String getOptionPaneMessage(Object message) {
        String out = "";
        // If we have an array, concatenate
        if (message instanceof String[]) {
            String[] msgs = (String[]) message;
            for (int i = 0; i < msgs.length; i++) {
                out += msgs[i] + " ";
            }
        }
        else
            // Otherwise, just take the string content
            out = "" + message;
        
        // Strip HTML
        out = SwingWTUtils.removeHTML(out);
        
        return out;
    }
    
    /**
     * Ties up the current thread until a particular
     * task is done.
     */
    private static void waitToFinish() {
        while (!isJobFinished()) {
            try {
                Thread.sleep(50);    
            }
            catch(Exception e) {}
        }
    }
        
    protected static synchronized boolean isJobFinished() { return jobFinished; }
    protected static synchronized void setJobFinished(boolean b) { jobFinished = b; }
 
    /** Determines from the object passed whether it should be
     *  the parent for the dialog.
     *  If the container is null, the root pane set by the developer is used - 
     *      if that's null too, a shared frame is used (created if it doesn't exist)
     *  If the container is not a frame of some type, the containing window for it is used.
     *  THIS ROUTINE IS NOT THREAD SAFE - ONLY CALL FROM THE EVENT DISPATCH THREAD
     */
    private static Shell getModalParent(Container c) {
        if (c == null) {
            if (getRootFrame() == null)
                return getSharedFrame();
            else
                return ((Shell) getRootFrame().getSWTPeer());
        }
        if (!(c.composite instanceof Shell))
            return c.composite.getShell();
        else
            return (Shell) c.composite; 
    }
    private static Shell sharedFrame = null;
    /**
     * Invisible shell used as the parent for dialogs
     * with a null container.
     */
    public static Shell getSharedFrame() {
        if ( sharedFrame == null ) {
            sharedFrame = new Shell(SwingWTUtils.getDisplay(), SWT.NO_TRIM);    
        }
        return sharedFrame;
    }

    public static Object showInternalInputDialog(Container parent, String msg, String title, int type, Object tbd1, Object tbd2, String tbd3)
    {
        // TODO need internal version
        return showInputDialog(parent, msg, title, type);
    }

}

/** 
 * Behaves like the input dialog used by Swing
 */
class SwingStyleInputDialog extends JDialog {
    
    private boolean isTextEntry = false;
    private JTextArea text = null;
    private JList sel = null;
    
    public SwingStyleInputDialog(swingwt.awt.Component parent, Object message, String title, int messageType, Icon icon, Object[] selectionValues, Object initialSelectionValue) {
        
        super((Frame) null, title, true);
        
        // Is it a text entry? Must be if no choices
        isTextEntry = (selectionValues == null);
        
        // Common items
        // --------------
        setTitle(title);
        
        JLabel mess = new JLabel(message.toString());
        getContentPane().add(mess, BorderLayout.NORTH);
        
        // Buttons
        JPanel pnlButtons = new JPanel();
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        
        JButton btnOk = new JButton("Ok");
        btnOk.setMnemonic('O');
        btnOk.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnOk_clicked();    
            }
        });
        pnlButtons.add(btnOk);
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setMnemonic('C');
        btnCancel.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnCancel_clicked();    
            }
        });
        pnlButtons.add(btnCancel);
        
        
        if (isTextEntry) {   
            // Present a new text entry area
            JScrollPane scr = new JScrollPane();
            text = new JTextArea();
            text.setWrapStyleWord(true);
            text.setLineWrap(true);
	    if (initialSelectionValue != null)
	        text.setText(initialSelectionValue.toString());
            scr.setViewportView(text);
            scr.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            getContentPane().add(scr, BorderLayout.CENTER);
            // Set size and display pos of dialog
            setSize(400, 150);
        }
        else
        {
            // Make a JList with stuff in there
            sel = new JList(selectionValues);
            try {
                sel.setSelectedValue(initialSelectionValue, true);
            }
            catch (NullPointerException e) {}
            catch (Exception e) {}
            getContentPane().add(sel, BorderLayout.CENTER);
            // Set size and display pos of dialog
            setSize(400, 300);
        }
        
        // Center it
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
          frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
          frameSize.width = screenSize.width;
        }
        setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    }
    
    protected void btnOk_clicked() {
        if (isTextEntry)
            JOptionPane.lastInputDialogReturnValue = text.getText();
        else {
            JOptionPane.lastInputDialogReturnValue = sel.getSelectedValue();
            JOptionPane.lastInputDialogReturnIndex = sel.getSelectedIndex();
        }
        JOptionPane.setJobFinished(true);
        dispose();
    }
    
    protected void btnCancel_clicked() {
        JOptionPane.lastInputDialogReturnValue = null;
        JOptionPane.lastInputDialogReturnIndex = -1;
        JOptionPane.setJobFinished(true);
        dispose();
    }
    
}
