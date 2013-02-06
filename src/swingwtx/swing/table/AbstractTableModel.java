/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.table;

import swingwtx.swing.event.*;

import java.util.*;


public abstract class AbstractTableModel implements TableModel
{

    protected Vector listenerList = new Vector();

    /** MUST OVERRIDE IN SUBCLASS */
    public String getColumnName(int column) { return null; }

    public int findColumn(String columnName) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (columnName.equals(getColumnName(i))) {
                return i;
            }
        }
        return -1;
    }

    /** MUST OVERRIDE IN SUBCLASS */
    public int getColumnCount() { return 0; }

    public Class getColumnClass(int columnIndex) {
	return Object.class;
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
	return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public void addTableModelListener(TableModelListener l) {
	listenerList.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
	listenerList.remove(l);
    }

    public Object[] getTableModelListeners() {
        return listenerList.toArray();
    }

    public void fireTableDataChanged() {
        fireTableChanged(new TableModelEvent(this));
    }

    public void fireTableStructureChanged() {
        fireTableChanged(new TableModelEvent(this, TableModelEvent.HEADER_ROW));
    }

    public void fireTableRowsInserted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
    }

    public void fireTableRowsDeleted(int firstRow, int lastRow) {
        fireTableChanged(new TableModelEvent(this, firstRow, lastRow, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
    }

    public void fireTableCellUpdated(int row, int column) {
        fireTableChanged(new TableModelEvent(this, row, row, column));
    }

    public void fireTableChanged(TableModelEvent e) {
	Object[] listeners = listenerList.toArray();
	for (int i = 0; i < listeners.length; i++) {
            ((TableModelListener) listeners[i]).tableChanged(e);
        }
    }

    public Object[] getListeners(Class listenerType) { 
	return listenerList.toArray(); 
    }
}
