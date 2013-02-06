/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.table;

import swingwt.awt.Point;
import swingwtx.swing.JTable;

public class JTableHeader extends swingwtx.swing.JComponent {

    protected JTable table = null;
    protected TableColumn resizingColumn = null;
    private TableCellRenderer defaultRenderer;

    public JTableHeader(JTable table) { this.table = table; }
    
    public TableColumnModel getColumnModel() { return table.getColumnModel(); }
    public void setColumnModel(TableColumnModel t) { table.setColumnModel(t); }
    public boolean isReorderingAllowed() { return false; }
    public void setReorderingAllowed(boolean b) {}
    public JTable getTable() { return table; }
    public int columnAtPoint(Point point) {
        int x = point.x;
        if (!getComponentOrientation().isLeftToRight()) {
            if (table != null && table.getAutoResizeMode() != swingwtx.swing.JTable.AUTO_RESIZE_OFF) {
                x = table.getWidth() - x;
            }
            else x = super.getWidth() - x;
        }

        return getColumnModel().getColumnIndexAtX(x);
    }
    public TableColumn getResizingColumn() { return resizingColumn; }
    public void setResizingColumn(TableColumn resizingColumn) { this.resizingColumn = resizingColumn; }
    public TableCellRenderer getDefaultRenderer() 
    {
    	/** TODO */
    	//return null;
    	return defaultRenderer;
    }
    //TODO: reflect this renderer in the gui if it is possible
    public void setDefaultRenderer(TableCellRenderer defaultRenderer) 
    {
    	this.defaultRenderer = defaultRenderer;
    }

    
}
