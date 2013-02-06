/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.event;

import swingwtx.swing.table.*;

public class TableModelEvent extends java.util.EventObject
{
    public static final int INSERT =  1;
    public static final int UPDATE =  0;
    public static final int DELETE = -1;
    public static final int HEADER_ROW = -1;
    public static final int ALL_COLUMNS = -1;

    protected int type;
    protected int firstRow;
    protected int lastRow;
    protected int column;

    public TableModelEvent(TableModel source) {
	this(source, 0, Integer.MAX_VALUE, ALL_COLUMNS, UPDATE);
    }
    public TableModelEvent(TableModel source, int row) {
	this(source, row, row, ALL_COLUMNS, UPDATE);
    }
    public TableModelEvent(TableModel source, int firstRow, int lastRow) {
	this(source, firstRow, lastRow, ALL_COLUMNS, UPDATE);
    }
    public TableModelEvent(TableModel source, int firstRow, int lastRow, int column) {
	this(source, firstRow, lastRow, column, UPDATE);
    }
    public TableModelEvent(TableModel source, int firstRow, int lastRow, int column, int type) {
	super(source);
	this.firstRow = firstRow;
	this.lastRow = lastRow;
	this.column = column;
	this.type = type;
    }
    public int getFirstRow() { return firstRow; };
    public int getLastRow() { return lastRow; };
    public int getColumn() { return column; }; 
    public int getType() { return type; }
}
