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

public interface TableColumnModel {
    public void addColumn(TableColumn aColumn);
    public void removeColumn(TableColumn column);
    public void moveColumn(int columnIndex, int newIndex);
    public void setColumnMargin(int newMargin);
    public int getColumnCount();
    public Enumeration getColumns();
    public int getColumnIndex(Object columnIdentifier);
    public TableColumn getColumn(int columnIndex);
    public int getColumnMargin();
    public int getColumnIndexAtX(int xPosition);
    public int getTotalColumnWidth();
    public void setColumnSelectionAllowed(boolean flag);
    public boolean getColumnSelectionAllowed();
    public int[] getSelectedColumns();
    public int getSelectedColumnCount();
    public void setSelectionModel(ListSelectionModel newModel); 
    public ListSelectionModel getSelectionModel(); 
    public void addColumnModelListener(TableColumnModelListener x);
    public void removeColumnModelListener(TableColumnModelListener x);
}
