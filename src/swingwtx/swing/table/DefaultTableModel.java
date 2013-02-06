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
import java.io.*;

public class DefaultTableModel extends AbstractTableModel implements Serializable {

    protected Vector dataVector = new Vector();
    protected Vector columnIdentifiers = new Vector();

    public DefaultTableModel() {
        this(0, 0);
    }

    public DefaultTableModel(Vector columnNames, int rowCount) {
        setDataVector(new Vector(rowCount), columnNames);
    }

    public DefaultTableModel(Object[] columnNames, int rowCount) {
        this(convertToVector(columnNames), rowCount);
    }
    
    public DefaultTableModel(int rowCount, int columnCount) {
        this(new Vector(columnCount), rowCount); 
    }

    public DefaultTableModel(Vector data, Vector columnNames) {
        setDataVector(data, columnNames);
    }

    public DefaultTableModel(Object[][] data, Object[] columnNames) {
        setDataVector(data, columnNames);
    }

    public Vector getDataVector() {
        return dataVector;
    }

    public void setDataVector(Vector dataVector, Vector columnIdentifiers) {
        if (dataVector == null) dataVector = new Vector();
        this.dataVector = dataVector;
        this.columnIdentifiers = columnIdentifiers; 
        updateRowData(0, getRowCount());
        fireTableStructureChanged();
    }

    public void setDataVector(Object[][] dataVector, Object[] columnIdentifiers) {
        setDataVector(convertToVector(dataVector), convertToVector(columnIdentifiers));
    }

    public void newDataAvailable(TableModelEvent event) {
        fireTableChanged(event);
    }

    public void newRowsAdded(TableModelEvent e) {
        updateRowData(e.getFirstRow(), e.getLastRow() + 1); 
        fireTableChanged(e);
    }

    public void rowsRemoved(TableModelEvent event) {
        fireTableChanged(event);
    }

    public void setNumRows(int rowCount) { 
        int old = getRowCount();
	if (old == rowCount) { 
	    return; 
	}
	dataVector.setSize(rowCount);
        if (rowCount <= old) {
            fireTableRowsDeleted(rowCount, old - 1);
        }
        else {
	    updateRowData(old, rowCount); 
            fireTableRowsInserted(old, rowCount - 1);
        }
    }

    public void setRowCount(int rowCount) { 
	setNumRows(rowCount); 
    } 

    public void addRow(Vector rowData) {
        insertRow(getRowCount(), rowData);
    }

    public void addRow(Object[] rowData) {
        addRow(convertToVector(rowData));
    }


    public void addColumn(Object columnName) {
        addColumn(columnName, (Vector)null);
    }

    public void addColumn(Object columnName, Vector columnData) {
        columnIdentifiers.addElement(columnName); 
	if (columnData != null) { 
            int columnSize = columnData.size(); 
            if (columnSize > getRowCount()) { 
	        dataVector.setSize(columnSize);
            }
	    updateRowData(0, getRowCount()); 
	    int newColumn = getColumnCount() - 1; 
	    for(int i = 0; i < columnSize; i++) { 
		  Vector row = (Vector) dataVector.elementAt(i);
		  row.setElementAt(columnData.elementAt(i), newColumn); 
	    }
	} 
        else { 
	    updateRowData(0, getRowCount()); 
        }

        fireTableStructureChanged();
    }

    public void addColumn(Object columnName, Object[] columnData) {
        addColumn(columnName, convertToVector(columnData));
    }
    
    public void insertRow(int row, Vector rowData) {
	dataVector.insertElementAt(rowData, row); 
	updateRowData(row, row+1); 
        fireTableRowsInserted(row, row);
    }

    public void insertRow(int row, Object[] rowData) {
        insertRow(row, convertToVector(rowData));
    }

    public void removeRow(int row) {
        dataVector.removeElementAt(row);
        fireTableRowsDeleted(row, row);
    }

    public void setColumnIdentifiers(Vector columnIdentifiers) {
	setDataVector(dataVector, columnIdentifiers); 
    }

    public void setColumnIdentifiers(Object[] newIdentifiers) {
        setColumnIdentifiers(convertToVector(newIdentifiers));
    }

    public void setColumnCount(int columnCount) { 
	columnIdentifiers.setSize(columnCount); 
	updateRowData(0, getRowCount()); 
	fireTableStructureChanged();
    } 

    public int getRowCount() {
        return dataVector.size();
    }

    public int getColumnCount() {
        return columnIdentifiers.size();
    }

    public String getColumnName(int column) {
        return columnIdentifiers.elementAt(column).toString(); 
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public Object getValueAt(int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        return rowVector.elementAt(column);
    }

    public void setValueAt(Object aValue, int row, int column) {
        Vector rowVector = (Vector)dataVector.elementAt(row);
        rowVector.setElementAt(aValue, column);
        fireTableCellUpdated(row, column);
    }

    protected static Vector convertToVector(Object[] anArray) {
        Vector v = new Vector(anArray.length);
        for (int i=0; i < anArray.length; i++) {
            v.addElement(anArray[i]);
        }
        return v;
    }

    protected static Vector convertToVector(Object[][] anArray) {
        Vector v = new Vector(anArray.length);
        for (int i=0; i < anArray.length; i++) {
            v.addElement(convertToVector(anArray[i]));
        }
        return v;
    }

    private void updateRowData(int from, int to) { 
	dataVector.setSize(getRowCount()); 
        for (int i = from; i < to; i++) { 
	    if (dataVector.elementAt(i) == null) { 
		dataVector.setElementAt(new Vector(), i); 
	    }
	    ((Vector) dataVector.elementAt(i)).setSize(getColumnCount());
	}
    }
    
    public void addTableModelListener(swingwtx.swing.event.TableModelListener l) {
        listenerList.add(l);
    }
    
    public void removeTableModelListener(swingwtx.swing.event.TableModelListener l) {
        listenerList.remove(l);
    }
    
}
