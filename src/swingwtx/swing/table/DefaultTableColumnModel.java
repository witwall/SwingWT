/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.table;

import java.util.*;

import swingwtx.swing.*;
import swingwtx.swing.event.*;

public class DefaultTableColumnModel implements TableColumnModel {

    private Vector columns = new Vector();
    private Vector listenerList = new Vector();
    private int columnMargin = 0;

    /** This is a real cheat!  Because we can't work out X position
     *  accurately, I'm going to assume that when somebody calls
     *  getColumnIndexAtX, they really want the column the mouse
     *  was over last. Since I do have that info, I set it here
     *  and return it for getColumnIndexAtX
     */
    public int lastColClicked = -1;

    public DefaultTableColumnModel() { }

    public void addColumn(TableColumn t) {
        columns.add(t);
        fireColumnAdded(new TableColumnModelEvent(this, 0, columns.indexOf(t)));
    }

    public void removeColumn(TableColumn t) {
        columns.remove(t);
        fireColumnRemoved(new TableColumnModelEvent(this, 0, columns.indexOf(t)));
    }

    public int getColumnCount() {
        return columns.size();
    }

    public TableColumn getColumn(int columnIndex) {
        if (columnIndex == -1) return null;
	    else return (TableColumn) columns.elementAt(columnIndex);
    }

    public int getColumnIndexAtX(int xPosition) {
        return lastColClicked;
    }

    public int getColumnIndex(Object columnIdentifier) {
        for (int i = 0; i < columns.size(); i++) {
            TableColumn tc = (TableColumn) columns.get(i);
            if (tc.getIdentifier().equals(columnIdentifier))
                return i;
        }
        throw new IllegalArgumentException("Column not found!");
    }

    public int getColumnMargin() {
        return 0;
    }

    public boolean getColumnSelectionAllowed() {
        return false;
    }

    public Enumeration getColumns() {
        return columns.elements();
    }

    public int getSelectedColumnCount() {
        return 0;
    }

    public int[] getSelectedColumns() {
        return null;
    }

    public ListSelectionModel getSelectionModel() {
        return new DefaultListSelectionModel();
    }

    public int getTotalColumnWidth() {
        return 0;
    }

    public void moveColumn(int columnIndex, int newIndex) {
    }

    public void setColumnSelectionAllowed(boolean flag) {
    }

    public void setSelectionModel(ListSelectionModel newModel) {
    }

    public void setColumnMargin(int newMargin) {
        columnMargin = newMargin;
        fireColumnMarginChanged();
    }

    public void addColumnModelListener(TableColumnModelListener x) {
        listenerList.add(x);
    }

    public void removeColumnModelListener(TableColumnModelListener x) {
        listenerList.remove(x);
    }

    protected void fireColumnAdded(TableColumnModelEvent e) {
         int size = listenerList.size();
         for(int i = 0; i < size; i++) {
             ((TableColumnModelListener )listenerList.get(i)).columnAdded(e);
         }
     }

     protected void fireColumnMarginChanged() {
         int size = listenerList.size();
         for(int i = 0; i < size; i++) {
             ((TableColumnModelListener )listenerList.get(i)).columnMarginChanged(new swingwtx.swing.event.ChangeEvent(this));
         }
     }

     protected void fireColumnMoved(TableColumnModelEvent e) {
         int size = listenerList.size();
         for(int i = 0; i < size; i++) {
             ((TableColumnModelListener )listenerList.get(i)).columnMoved(e);
         }
     }

     protected void fireColumnRemoved(TableColumnModelEvent e) {
         int size = listenerList.size();
         for(int i = 0; i < size; i++) {
             ((TableColumnModelListener )listenerList.get(i)).columnRemoved(e);
         }
     }

     protected  void fireColumnSelectionChanged(ListSelectionEvent e) {
         int size = listenerList.size();
         for(int i = 0; i < size; i++) {
             ((TableColumnModelListener )listenerList.get(i)).columnSelectionChanged(e);
         }
     }

     public void valueChanged(ListSelectionEvent e) {
         fireColumnSelectionChanged(e);
     }

}
