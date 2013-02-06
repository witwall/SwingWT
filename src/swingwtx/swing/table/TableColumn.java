/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.table;

public class TableColumn {

    protected TableCellRenderer cellRenderer = null;
    protected TableCellRenderer headerRenderer = null;
    private int idx = 0;
    private int width = 0;
    private int maxWidth = 0;
    private int minWidth = 0;
    private int preferredWidth = 0;
    private boolean resizable = true;
    private Object headerValue = null;
    private Object identifier = null;
    private TableCellEditor tableCellEditor = null;

    public TableColumn() {}
    public TableColumn(int index) { idx = index; }
    public TableColumn(int index, int width) { idx = index; this.width = width; }
    public TableColumn(int index, int width, TableCellRenderer renderer, TableCellEditor editor) { idx = index; this.width = width; cellRenderer = renderer; tableCellEditor = editor;}


    public void setCellRenderer(TableCellRenderer newrenderer) {
        cellRenderer = newrenderer;
    }

    public TableCellRenderer getCellRenderer() {
        return cellRenderer;
    }

    /** Not implemented - any reason to? */
    public void setHeaderRenderer(TableCellRenderer headerRenderer) {
        this.headerRenderer = headerRenderer;
    }

    public TableCellRenderer getHeaderRenderer() {
        return headerRenderer;
    }

    public int getModelIndex() {
        return idx;
    }

    public void setModelIndex(int index) {
        idx = index;
    }

    public java.lang.Object getHeaderValue() {
        return headerValue;
    }
    public void setHeaderValue(java.lang.Object headerValue) {
        this.headerValue = headerValue;
    }

    public java.lang.Object getIdentifier() {
        return (identifier == null) ? getHeaderValue() : identifier;
    }
    public void setIdentifier(java.lang.Object identifier) {
        this.identifier = identifier;
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    /** Setter for property maxWidth.
     * @param maxWidth New value of property maxWidth.
     *
     */
    public void setMaxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getMinWidth() {
        return minWidth;
    }

    public void setMinWidth(int minWidth) {
        this.minWidth = minWidth;
    }

    public int getPreferredWidth() {
        return preferredWidth;
    }

    public void setPreferredWidth(int preferredWidth) {
        this.preferredWidth = preferredWidth;
    }

    public boolean isResizable() {
        return resizable;
    }

    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    public TableCellEditor getCellEditor() {
        return tableCellEditor;
    }
    
    public void setCellEditor(TableCellEditor tableCellEditor) {
        this.tableCellEditor = tableCellEditor;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
