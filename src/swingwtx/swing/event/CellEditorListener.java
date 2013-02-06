/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

 
 */

package swingwtx.swing.event;

public interface CellEditorListener extends java.util.EventListener {

    public void editingStopped(ChangeEvent e);
    public void editingCanceled(ChangeEvent e);
    
}

