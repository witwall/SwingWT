/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */

package swingwtx.swing.event;

public interface TableColumnModelListener {
    
    public void columnAdded(TableColumnModelEvent e);
    public void columnRemoved(TableColumnModelEvent e);
    public void columnMoved(TableColumnModelEvent e);
    public void columnMarginChanged(ChangeEvent e);
    public void columnSelectionChanged(ListSelectionEvent e);
}
