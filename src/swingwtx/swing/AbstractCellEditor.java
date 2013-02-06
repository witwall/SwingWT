/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */
package swingwtx.swing;

import java.util.*;

import swingwtx.swing.event.*;

/**
 * Base class for cell editors
 *
 * @author  Robin Rawson-Tetley
 */
public abstract class AbstractCellEditor implements CellEditor {

    protected Vector listeners = new Vector();

    public boolean isCellEditable(EventObject e) { 
	return true; 
    } 

    public boolean shouldSelectCell(EventObject anEvent) { 
	return true; 
    }

    public boolean stopCellEditing() { 
	fireEditingStopped(); 
	return true;
    }

    public void  cancelCellEditing() { 
	fireEditingCanceled(); 
    }


    public void addCellEditorListener(CellEditorListener l) {
	listeners.add(l);
    }

    public void removeCellEditorListener(CellEditorListener l) {
	listeners.remove(l);
    }

    public CellEditorListener[] getCellEditorListeners() {
        if (listeners.size() == 0) return null;
        CellEditorListener[] l = new CellEditorListener[listeners.size()];
        for (int i = 0; i < listeners.size(); i++)
            l[i] = (CellEditorListener) listeners.get(i);
        return l;
    }

    protected void fireEditingStopped() {
	for (int i = 0; i < listeners.size(); i++) {
            ChangeEvent e = new ChangeEvent(this);
            ((CellEditorListener) listeners.get(i)).editingStopped(e);
	}
    }

    protected void fireEditingCanceled() {
	for (int i = 0; i < listeners.size(); i++) {
            ChangeEvent e = new ChangeEvent(this);
            ((CellEditorListener) listeners.get(i)).editingCanceled(e);
	}
    }
}
