/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt;

import swingwtx.swing.*;

import java.io.File;
import java.io.FileFilter;

/**
 * Wrapper around JFileChooser to enable
 * AWT compatibility.
 *
 * @author Robin Rawson-Tetley
 */
public class FileDialog extends Dialog {
    
    public static final int LOAD = 0;
    public static final int SAVE = 1;
    
    protected int mode = LOAD;
    protected JFileChooser swingFileChooser = null;
    protected Component parent = null;
    protected String file = null;
    protected String dir = null;
    protected FileFilter filter = null;
    
    public FileDialog(Frame parent) { this(parent, "Open", LOAD); }
    public FileDialog(Frame parent, String title) { this(parent, title, LOAD); }
    
    public FileDialog(Frame parent, String title, int mode) {
        swingFileChooser = new JFileChooser();
        swingFileChooser.setTitle(title);
        this.mode = mode;
        this.parent = parent;
    }
    
    public void show() {
        setVisible(true);    
    }
    public void setVisible(boolean b) {
        if (!b) return;
        int result = 0;
        if (mode == LOAD)
            result = swingFileChooser.showOpenDialog(parent);
        else
            result = swingFileChooser.showSaveDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            file = swingFileChooser.getSelectedFile().getAbsolutePath();
            dir = swingFileChooser.getSelectedFile().getPath();
        }
        else {
            file = null;
            dir = null;
        }
    }
    
    public String getDirectory() {
        return dir;
    }
    
    public String getFile() {
        return file;    
    }
    
    public FileFilter getFilenameFilter() {
        return filter;    
    }
    
    public void setFilenameFilter(FileFilter f) {
        filter = f;    
    }
    
    public void setDirectory(String dir) {
        swingFileChooser.setSelectedFile(new File(dir));    
    }
    
    public void setFile(String file) {
        swingFileChooser.setSelectedFile(new File(file));    
    }
    
    public int getMode() {
        return mode;    
    }
    
    public void setMode(int mode) {
        this.mode = mode;    
    }
}
