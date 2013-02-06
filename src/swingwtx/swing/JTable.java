/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import java.text.MessageFormat;
import java.util.EventObject;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import swingwt.awt.Component;
import swingwt.awt.Dimension;
import swingwt.awt.Point;
import swingwt.awt.event.MouseEvent;
import swingwt.awt.print.PrinterException;
import swingwtx.swing.event.CellEditorListener;
import swingwtx.swing.event.ChangeEvent;
import swingwtx.swing.event.ListSelectionEvent;
import swingwtx.swing.event.ListSelectionListener;
import swingwtx.swing.event.TableColumnModelEvent;
import swingwtx.swing.event.TableColumnModelListener;
import swingwtx.swing.event.TableModelEvent;
import swingwtx.swing.event.TableModelListener;
import swingwtx.swing.table.DefaultTableCellRenderer;
import swingwtx.swing.table.DefaultTableColumnModel;
import swingwtx.swing.table.DefaultTableModel;
import swingwtx.swing.table.JTableHeader;
import swingwtx.swing.table.TableCellEditor;
import swingwtx.swing.table.TableCellRenderer;
import swingwtx.swing.table.TableColumn;
import swingwtx.swing.table.TableColumnModel;
import swingwtx.swing.table.TableModel;



public class JTable extends swingwtx.swing.JComponent implements TableModelListener, 
                                                                 TableColumnModelListener, 
                                                                 ListSelectionListener,
                                                                 CellEditorListener {

    public static final int AUTO_RESIZE_OFF = 0;
    public static final int AUTO_RESIZE_NEXT_COLUMN = 1;
    public static final int AUTO_RESIZE_SUBSEQUENT_COLUMNS = 2;
    public static final int AUTO_RESIZE_LAST_COLUMN = 3;
    public static final int AUTO_RESIZE_ALL_COLUMNS = 4;
    
    protected Table ppeer = null;
    protected JTableHeader header = null;
    private TableModel model = null;
    protected TableColumnModel columnModel = null;
    protected ListSelectionModel listSelectionModel = null;
    protected TableCellEditor cellEditor = null;
    protected int pRowHeight = 20;
    protected swingwt.awt.Dimension pIntercell = null;
    /** Cache - whether to show grid lines */
    protected boolean pLines = true;
    protected int pSelRow = -1;
    protected int pSelCol = -1;
    /** Cache of values for current cell editor */
    protected int editingColumn = -1;
    protected int editingRow = -1;
    protected TableCellEditor editingEditor = null;
    protected swingwt.awt.Component editingComponent = null;
    /** Whether cell selection (and thus cell editing) is enabled  */
    protected boolean cellSelectionEnabled = true;
    /** The SWT table editor */
    protected org.eclipse.swt.custom.TableEditor swtEditor = null;
    /** The SWT table cursor */
    protected org.eclipse.swt.custom.TableCursor swtCursor = null;
    /** Whether or not to use SWT's fast virtual tables */
    protected boolean useFastVirtualTables = SwingWTUtils.isUseSWTFastVirtualTables();
    
    /** Whether the table has been displayed yet. If it has then we force a redraw
     *  when setSwingWTParent is called since it must mean it has been removed
     *  from a container and redrawn.
     *  This is used in conjunction with modelDirty to make sure we only fill
     *  the table from the model when we need to.
     */
    protected boolean tableDrawnOnce = false;

    /** Whether the model has been changed. Used by setSwingWTParent to decide whether
        or not to refresh the data. */
    private boolean modelDirty = true;
    private boolean columnModelDirty = true;
    
    private HashMap defaultRenderers = null;
    private HashMap defaultEditors = null;
    
    protected boolean rowSelection = true, columnSelection = false;

     public JTable() {
         this(null);
     }
     public JTable(int numRows, int numCols) {
         this(new DefaultTableModel(numRows, numCols));
     }
     public JTable(Object[][] rowData, Object[] columnNames) {
         this(new DefaultTableModel(rowData, columnNames));
     }
     public JTable(Vector rowData, Vector columnNames) {
         this(new DefaultTableModel(rowData, columnNames));
     }
     public JTable(TableModel dm) {
         this(dm, null);
     }
     public JTable(TableModel dm, TableColumnModel columnModel) {
         this(dm, columnModel, null);
     }
     public JTable(TableModel dm, TableColumnModel columnModel, ListSelectionModel selectionModel) {

         if (dm == null)
             dm = new DefaultTableModel();

         setModel(dm);

         if (columnModel != null) {
             setColumnModel(columnModel);
         }

         if (selectionModel == null)
             selectionModel = new DefaultListSelectionModel(this);
         setSelectionModel(selectionModel);
         createDefaultRenderers();
         setCellEditor(new DefaultCellEditor(new JTextField()));
     }

    public TableModel getModel() { return model; }
    public void setModel(TableModel dm, boolean noRefresh) {
        if (model == dm) return;
        model = dm;
        generateDefaultColumnModel();
        model.addTableModelListener(this);
    }
    public void setModel(TableModel dm) {
        setModel(dm, true);
        modelDirty = true;
        refreshTable();
    }
    public void clearSelection() { if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return; ppeer.deselectAll(); }
    public void doLayout() { refreshTable(); }
    public int getColumnCount() { return model.getColumnCount(); }
    public String getColumnName(int index) { return model.getColumnName(index); }
    public swingwtx.swing.table.TableColumn getColumn(Object identifier) { return columnModel.getColumn(columnModel.getColumnIndex(identifier)); }
    public swingwtx.swing.table.TableColumn getColumn(int index) { return columnModel.getColumn(index); }
    public int getRowCount() { return model.getRowCount(); }
    /** NOT IMPLEMENTED */
    public int getSelectedColumn() { if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return pSelCol; else return -1; }
    /** NOT IMPLEMENTED */
    public int getSelectedColumnCount() { return 0; }
    /** NOT IMPLEMENTED */
    public int[] getSelectedColumns() { return null; }
    public void setRowSelectionInterval(final int first, final int last) {
        final int[] sel = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.setSelection(first, last);
            }
        });
    }
    public int getSelectedRow() {
        final int[] sel = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    sel[0] = pSelRow;
                else
                    sel[0] = ppeer.getSelectionIndex();
            }
        });
        return sel[0];
    }
    public int getSelectedRowCount() {
        final int[] sel = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    sel[0] = 0;
                else
                    sel[0] = ppeer.getSelectionCount();
                }
        });
        return sel[0];
    }
    private int[] selRows = new int[0];
    public int[] getSelectedRows() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    selRows = null;
                else
                    selRows = ppeer.getSelectionIndices();
            }
        });
        return selRows;
    }
    public boolean getShowHorizontalLines() { return isShowGrid(); }
    public boolean isShowHorizontalLines() { return isShowGrid(); }
    public boolean getShowVerticalLines() { return isShowGrid(); }
    public boolean isShowVerticalLines() { return isShowGrid(); }
    public boolean getShowGrid() { return isShowGrid(); }
    public boolean isShowGrid() {
        final boolean[] ret = new boolean[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    ret[0] = false;
                else
                    ret[0] = ppeer.getLinesVisible();
            }
        });
        return ret[0];
    }
    public void setShowHorizontalLines(boolean b) { setShowGrid(b); }
    public void setShowVerticalLines(boolean b) { setShowGrid(b); }
    public void setShowGrid(final boolean b) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    pLines = b;
                else
                    ppeer.setLinesVisible(b);
            }
        });
    }
    public Object getValueAt(int row, int col) { return model.getValueAt(row, col); }
    public void resizeAndRepaint() { refreshTable(); }
    public void selectAll() { if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.selectAll(); }
    public void setValueAt(Object value, int row, int col) { model.setValueAt(value, row, col); }
    public TableColumnModel getColumnModel() { return columnModel; }
    /** NOT IMPLEMENTED - just override the column model produced - you won't even notice */
    public void setAutoCreateColumnsFromModel(boolean b) {}
    public void setColumnModel(TableColumnModel model) {
        if (columnModel == model) return;
        columnModel = model;
        modelDirty = true;
        refreshTable();
        columnModel.addColumnModelListener(this);
    }

    public void createDefaultColumnsFromModel() {
        TableModel model = getModel();
        TableColumnModel tableColumnModel = getColumnModel();
        
        // Delete all existing columns in the column model
        if (model != null && tableColumnModel != null) {
            
            while (tableColumnModel.getColumnCount() > 0)
                tableColumnModel.removeColumn(tableColumnModel.getColumn(0));
            
            for (int i = 0; i < model.getColumnCount(); i++) {

                swingwtx.swing.table.TableColumn tc = new swingwtx.swing.table.TableColumn();

                // Column width calculation
                //int colWidth = (model.getColumnName(i).length() * 10) * 2;
                int colWidth = 0;
                // Set values
                tc.setWidth(colWidth);
                tc.setPreferredWidth(colWidth);
                tc.setMaxWidth(colWidth);
                tc.setMinWidth(colWidth);
                tc.setHeaderValue(model.getColumnName(i));
                tc.setIdentifier(model.getColumnName(i));
                //tc.setCellRenderer(new DefaultTableCellRenderer());
                tc.setCellEditor(cellEditor); // Default the cell editor for the whole table
                tc.setModelIndex(i);
                tc.setResizable(true);

                columnModel.addColumn(tc);
            }
        }
    }

    public void addColumn(TableColumn tableColumn) {
        if (tableColumn.getHeaderValue() == null)
            tableColumn.setHeaderValue( getModel().getColumnName(tableColumn.getModelIndex()) );
        getColumnModel().addColumn(tableColumn);
    }
    
    /** NOT IMPLEMENTED */
    public void setRowSelectionAllowed(boolean b) { this.rowSelection = b; }
    /** NOT IMPLEMENTED */
    public void setColumnSelectionAllowed(boolean b) { this.columnSelection = b; }
    public boolean getRowSelectionAllowed(boolean b) { return rowSelection; }
    public void setCellSelectionEnabled(boolean b) { cellSelectionEnabled = b; modelDirty = true; refreshTable(); }
    public boolean getCellSelectionEnabled() { return cellSelectionEnabled; }
    public boolean getColumnSelectionAllowed(boolean b) { return columnSelection; }
    /** NOT IMPLEMENTED - HARD RETURNS A COLOUR VALUE */
    public swingwt.awt.Color getSelectionBackground() { return swingwt.awt.Color.BLUE; }
    /** NOT IMPLEMENTED - HARD RETURNS A COLOUR VALUE */
    public swingwt.awt.Color getSelectionForeground() { return swingwt.awt.Color.WHITE; }
    /** NOT IMPLEMENTED - MEANINGLESS TO PLATFORM TABLE */
    public void setSelectionBackground(swingwt.awt.Color c) {}
    /** NOT IMPLEMENTED - MEANINGLESS TO PLATFORM TABLE */
    public void setSelectionForeground(swingwt.awt.Color c) {}
    /** NOT IMPLEMENTED */
    public void setIntercellSpacing(swingwt.awt.Dimension d) { pIntercell = d; }
    public swingwt.awt.Dimension getIntercellSpacing() { if (pIntercell ==  null) return new swingwt.awt.Dimension(0, 0); else return pIntercell; }
    public int getRowHeight() { return pRowHeight; }
    /** NOT IMPLEMENTED */
    public void setRowHeight(int height) { pRowHeight = height; }
    /** NOT IMPLEMENTED */
    public void setRowHeight(int row, int height) { setRowHeight(height); }
    /** NOT IMPLEMENTED */
    public int rowAtPoint(swingwt.awt.Point p) { return -1; }
    /** NOT IMPLEMENTED */
    public int getAutoResizeMode() { return 0; }
    /** NOT IMPLEMENTED */
    public void setAutoResizeMode(int size) {}

    public ListSelectionModel getSelectionModel() { return listSelectionModel; }
    public void setSelectionModel(ListSelectionModel l) {
        if (l != null) {
            listSelectionModel = l;
            l.addListSelectionListener(this);
        }
    }

    public void setSelectionMode(int mode) {
        listSelectionModel.setSelectionMode(mode);
    }
    
    public void setDefaultRenderer(Class columnClass, TableCellRenderer tableCellRenderer) {
        if (tableCellRenderer == null)
            defaultRenderers.remove(columnClass);
        else
            defaultRenderers.put(columnClass, tableCellRenderer);
    }
    
    public TableCellRenderer getDefaultRenderer(Class columnClass) {
        if (columnClass == null) return null;
        
        TableCellRenderer renderer = (TableCellRenderer) defaultRenderers.get(columnClass);
        if (renderer == null)
            renderer = getDefaultRenderer(columnClass.getSuperclass());
        
        return renderer;
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        TableCellRenderer renderer = columnModel.getColumn(column).getCellRenderer();
        
        if (renderer == null) {
            Class columnClass = getModel().getColumnClass(convertColumnIndexToModel(column));
            renderer = getDefaultRenderer(columnClass);
        }
        
        return renderer;
    }
    
    protected void createDefaultRenderers()
    {
        defaultRenderers = new HashMap();
        
        // Generic renderer for all objects - JLabel.
        // TODO: This should be expanded later to support checkboxes for booleans, etc
        defaultRenderers.put(Object.class, new DefaultTableCellRenderer());
        defaultRenderers.put(Boolean.class, new BooleanTableCellRenderer());
    }
    
    class BooleanTableCellRenderer implements TableCellRenderer {

        /*
         * @see swingwtx.swing.table.TableCellRenderer#getTableCellRendererComponent(swingwtx.swing.JTable, java.lang.Object, boolean, boolean, int, int)
         */
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
        {
            boolean boolValue = value == null ? false : ((Boolean)value).booleanValue();
            return new swingwtx.swing.JCheckBox("", boolValue);
        }
        
    }

    public swingwt.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
	return renderer.getTableCellRendererComponent( this,
	    getValueAt(row,column),
	    isCellSelected(row,column),
	    false /*TODO*/,
	    row,
	    column);
    }

    public boolean isCellSelected(int row, int col) {
	int srow = -1;
	int scol = -1;
	try {    
            srow = ((Integer) swtCursor.getRow().getData("rowindex")).intValue();
            scol = swtCursor.getColumn();
	}
	catch (Exception e) {}
	if (srow == row && scol == col) return true;
	if (getSelectedRow() == row) return true;
	return false;
    }

    /**
     *  Tells the table that it needs to redraw when
     *  refreshTable() is next called.
     *  @param Whether the table should be redrawn
     *  when refreshTable() is next called.
     */
    public void setModelDirty(boolean b) {
        modelDirty = b;
    }

    public void changeSelection(final int row, final int col, final boolean toggle, final boolean extend) {
        pSelRow = row;
        pSelCol = col;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    ppeer.setSelection(row);
                    processListSelection();
                }
            }
        });
    }

    /** Scrolls the table to the selected row. This is not a Swing method */
    public void showSelection() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.showSelection();
            }
        });
    }

    /**
     * SWT table columns aren't movable, so columnIndex == modelIndex
     */
    public int convertColumnIndexToModel(int index) {
        return index;
    }

    /**
     * Given that we have a valid table model, generates the default
     * column model for it.
     */
    private void generateDefaultColumnModel() {
        columnModel = new DefaultTableColumnModel();
        createDefaultColumnsFromModel();
    }

    public JTableHeader getTableHeader() {
        if (header == null)
            header = new JTableHeader(this);
        return header;
    }

    public void setTableHeader(JTableHeader header) {
	// TODO: implement null table header
	this.header = header;
    }

    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        
        int style = SWT.BORDER;
        // This is semi-incorrect, however this will mimic Swing behavior
        // when row selection is on and column selection is off.
        if (rowSelection && !columnSelection) style |= SWT.FULL_SELECTION;
        
        ppeer = new Table(parent.getComposite(), style );
        peer = ppeer;
        this.parent = parent;
        
        // Cached values
        ppeer.setLinesVisible(pLines);

        // Is this the first time? If it is, then don't do anything as we've
        // taken care of that stuff. If not, force a reload as this must be
        // added to a container for the nth time so the data will need to
        // be reloaded, irrespective of whether the model is dirty.
        if (!tableDrawnOnce)
            tableDrawnOnce = true;
        else
            setDirty(true);

        // Load data if necessary
        if (modelDirty) refreshTable();

        // Selection
        if (pSelRow != -1) ppeer.setSelection(pSelRow);
    }

    /**
     * Sets whether the model is dirty for forcing updates
     */
    public void setDirty(boolean b) {
        modelDirty = b;
    }

    /**
     * Forces a complete rebuild of the peer from the
     * data model.
     */
    public void refreshTable() {

        // Don't do anything if the model hasn't changed
        if (!modelDirty) return;
        modelDirty = false;

        final JTable table = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                // Don't do anything if we can't see the table anyway - the
                // model is still dirty though
                if (!SwingWTUtils.isSWTControlAvailable(ppeer)) { modelDirty = true; return; }

                // Clear out everything from the peer if necessary
                ppeer.removeAll();
                ppeer.setHeaderVisible(true);

                // Regenerate SWT table columns

                // Drop any existing columns by redoing the table
                ppeer.dispose();
                
                // Some components don't have a layout
                if (parent.getLayout() != null)
                    parent.getLayout().removeLayoutComponent(table);
                
                // Work out if the table has an editable column - if not, we
                // can use full row selection by turning off cellSelection
                // automatically
                if (model.getRowCount() > 0) {
		    boolean hasOneEditableColumn = false;
	            for (int i = 0; i < model.getColumnCount(); i++)
			if (model.isCellEditable(0, i)) hasOneEditableColumn = true;
		    if (!hasOneEditableColumn) {
                        cellSelectionEnabled = false;
		    }
		}
                
                ppeer = new Table(parent.getComposite(), SWT.BORDER | 
			(listSelectionModel.getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION ? SWT.MULTI : SWT.SINGLE) |
			(!cellSelectionEnabled ? SWT.FULL_SELECTION : SWT.NONE) |
                        (SwingWTUtils.isUseSWTFastVirtualTables() ? SWT.VIRTUAL : SWT.NONE)
                        );
                                                        
                parent.addComponentToLayout(table);
                ppeer.setHeaderVisible(true);
                registerTableListSelectionEvents();
                peer = ppeer;

                // Lay it out so we see it
                parent.invalidate();

                int columnCount = Math.min(model.getColumnCount(), columnModel.getColumnCount());
                for (int i = 0; i < columnCount; i++) {

                    org.eclipse.swt.widgets.TableColumn tc =
                        new org.eclipse.swt.widgets.TableColumn(ppeer, 0);

                    tc.setText(columnModel.getColumn(i).getHeaderValue().toString());
                    tc.setResizable(columnModel.getColumn(i).isResizable());

                    // If no col width was set in the model, use the width of
                    // the text in the column as a starting point, then scan all the data
                    // in the column to find the widest one
                    if (columnModel.getColumn(i).getWidth() == 0) {
                        int colwidth = SwingWTUtils.getRenderStringWidth(columnModel.getColumn(i).getHeaderValue().toString());
                        int contentwidth = 0;
                        for (int z = 0; z < model.getRowCount(); z++) {
                            Object value = model.getValueAt(z, i);
                            if (value == null) value = "";
                            contentwidth = (SwingWTUtils.getRenderStringWidth(value.toString()));
                            if (contentwidth > colwidth) colwidth = contentwidth;
                        }
                        tc.setWidth(colwidth);
                    }
                    else
                        tc.setWidth(columnModel.getColumn(i).getWidth());

                    // Event catcher for a column being clicked
                    final int index = i;
                    tc.addSelectionListener( new org.eclipse.swt.events.SelectionListener() {
                        public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                            processColumnClick(index);
                        }
                        public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {}
                    });
                }

                // Cached values and events
                ppeer.setLinesVisible(pLines);
                assertCachedProperties();
                registerEvents();

		// Create a cursor and editor if there's some data and
		// at least one editable cell. This is because the cursors frankly look
		// like total shite, so we need to avoid them if the table isn't editable.
		if (model.getRowCount() > 0) {
		    
                    boolean hasOneEditableColumn = false;
	            for (int i = 0; i < model.getColumnCount(); i++)
			if (model.isCellEditable(0, i)) hasOneEditableColumn = true;	
                    
		    if (hasOneEditableColumn && cellSelectionEnabled) {
                        swtEditor = new org.eclipse.swt.custom.TableEditor(ppeer);
                        createTableCursor();
		    }
		}
                
                
                /*
                 * This code is for handling TableItems with SWT fast
                 * VIRTUAL implementation
                 */
                if (useFastVirtualTables) {

                    // Set the data size within the table
                    ppeer.setItemCount(model.getRowCount());
                    
                    // Set a callback on the table peer for loading
                    // items from the model.
                    ppeer.addListener (SWT.SetData, new Listener () {
                        public void handleEvent (Event event) {

                            TableItem ti = (TableItem) event.item;
                            int i = ppeer.indexOf(ti);

                            // Store index for retreival from table cursor
                            ti.setData("rowindex", new Integer(i));

                            for (int z = 0; z < model.getColumnCount(); z++) {

                                // Get the cell renderer for this item
                                swingwt.awt.Component cr =
                                    getCellRenderer(i, z).getTableCellRendererComponent(
                                        table, model.getValueAt(i, z),
                                        getSelectedRow() == i,
                                        true,
                                        i, z);

                                if (cr instanceof JLabel) {

                                    JLabel renderer = (JLabel) cr;

                                    // Use the text from the renderer
                                    ti.setText(z, renderer.getText() );

                                    // If there's an image, render it:
                                    if (renderer.getIcon() != null)
                                        ti.setImage(z, SwingWTUtils.getSWTImageFromSwingIcon(table, renderer.getIcon()));

                                    // Colours
                                    if (renderer.isBackgroundSet())
                                        if (renderer.getBackground().getSWTColor() != null)
                                            ti.setBackground(z, renderer.getBackground().getSWTColor());
                                    if (renderer.isForegroundSet())
                                        if (renderer.getForeground().getSWTColor() != null)
                                            ti.setForeground(z, renderer.getForeground().getSWTColor());

                                    // Font
                                    if (renderer.hasSetFont())
                                        ti.setFont(z, renderer.getFont().getSWTFont());

                                }
                                else if (cr instanceof swingwtx.swing.JCheckBox) {
                                    swingwtx.swing.JCheckBox renderer = (swingwtx.swing.JCheckBox) cr;
                                    ti.setChecked(renderer.isSelected());
                                }
                                else
                                    ti.setText(z,"CellRenderer not JLabel: " + cr.getClass().getName());
                            }
                        }
                    });
                }
                else {
                    
                    /*
                     * Use the slow up-front SWT TableItem creation from pre 3.0M8
                     */
                    for (int i = 0; i < model.getRowCount(); i++) {


                        TableItem ti = new TableItem(ppeer, SWT.NONE);
                        for (int z = 0; z < model.getColumnCount(); z++) {

                            // Get the cell renderer for this item
                            swingwt.awt.Component cr =
                                getCellRenderer(i, z).getTableCellRendererComponent(
                                    table, model.getValueAt(i, z),
                                    getSelectedRow() == i,
                                    true,
                                    i, z);

                            if (cr instanceof JLabel) {

                                JLabel renderer = (JLabel) cr;

                                // Use the text from the renderer
                                ti.setText(z, renderer.getText() );
                                
                                // Store index for retreival from table cursor
                                ti.setData("rowindex", new Integer(i));

                                // If there's an image, render it:
                                if (renderer.getIcon() != null)
                                    ti.setImage(z, SwingWTUtils.getSWTImageFromSwingIcon(table, renderer.getIcon()));

                                // Colours
                                if (renderer.getBackground() != null)
                                    if (renderer.getBackground().getSWTColor() != null)
                                        ti.setBackground(z, renderer.getBackground().getSWTColor());
                                if (renderer.getForeground() != null)
                                    if (renderer.getForeground().getSWTColor() != null)
                                        ti.setForeground(z, renderer.getForeground().getSWTColor());

                                // Font
                                if (renderer.hasSetFont())
                                    ti.setFont(z, renderer.getFont().getSWTFont());

                            }
                            else if (cr instanceof swingwtx.swing.JCheckBox) {
                                swingwtx.swing.JCheckBox renderer = (swingwtx.swing.JCheckBox) cr;
                                ti.setChecked(renderer.isSelected());
                            }
                            else
                                ti.setText(z,"CellRenderer not JLabel: " + cr.getClass().getName());
                        }
                    }
                }
                
            }
        });
    }

    /**
     * Just like refreshTable(), except it does not generate
     * the column headers and only updates the rows specified.
     * This is used for change events from the model.
     */
    public void refreshTable(final int rowFrom, final int rowTo) {

        // Don't do anything if the model hasn't changed
        if (!modelDirty) return;
        modelDirty = false;

        final JTable table = this;
        SwingUtilities.invokeAsync(new Runnable() {
            public void run() {
                // Don't do anything if we can't see the table anyway
                if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return;
                // Now that we have those, lets generate the
                // table data
                for (int i = rowFrom; i < model.getRowCount() && i <= rowTo; i++) {
                    TableItem ti = ppeer.getItem(i);
                    for (int z = 0; z < model.getColumnCount(); z++) {

                        // Get the cell renderer for this item
                        swingwt.awt.Component cr = 
                            getCellRenderer(i, z).getTableCellRendererComponent(
                                table, model.getValueAt(i, z),
                                getSelectedRow() == i,
                                true,
                                i, z);

                        if (cr instanceof JLabel) {
                            
                            JLabel renderer = (JLabel) cr;
                            
                            // Use the text from the renderer
                            ti.setText(z, renderer.getText() );
                            
                            // Store index for retreival from table cursor
                            ti.setData("rowindex", new Integer(i));

                            // If there's an image, render it:
                            if (renderer.getIcon() != null)
                                ti.setImage(z, SwingWTUtils.getSWTImageFromSwingIcon(table, renderer.getIcon()));

                            // Colours
                            if (renderer.getBackground() != null)
                                if (renderer.getBackground().getSWTColor() != null)
                                    ti.setBackground(z, renderer.getBackground().getSWTColor());
                            if (renderer.getForeground() != null)
                                if (renderer.getForeground().getSWTColor() != null)
                                    ti.setForeground(z, renderer.getForeground().getSWTColor());

                            // Font
                            if (renderer.hasSetFont())
                                ti.setFont(z, renderer.getFont().getSWTFont());
                        }
                        else if (cr instanceof swingwtx.swing.JCheckBox) {
                            swingwtx.swing.JCheckBox renderer = (swingwtx.swing.JCheckBox) cr;
                            ti.setChecked(renderer.isSelected());
                        }
                        else
                            ti.setText("CellRenderer must descend JLabel");
                    }
                }
            }
        });

    }

    /**
     * Assigns events to the cursor for selection so that we can
     * update the ListSelectionModel for this component.
     */
    protected void registerCursorSelectionEvents() {
        swtCursor.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                processListSelection();
                processCellEdit();
            }
        });
    }
    
    /**
     * Assigns events to the table for selection
     */
    protected void registerTableListSelectionEvents() {
        ppeer.addSelectionListener( new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                processListSelection();
            }
        });
    }
     
    /**
     * Called back when a row/cell is selected in the table
     * to fire events on to user processes.
     */
    protected void processListSelection() {
        // Handle ListSelectionModel
        if (listSelectionModel instanceof DefaultListSelectionModel) {
            if (listSelectionModel.getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
                int lowest = ppeer.getSelectionIndex(); int highest = ppeer.getSelectionIndex();
                int sel[] = ppeer.getSelectionIndices();
                for (int i = 0; i < sel.length; i++) {
                    if (lowest > sel[i]) lowest = sel[i];
                    if (highest < sel[i]) highest = sel[i];
                }
                ((DefaultListSelectionModel) listSelectionModel).fireListSelectionEvent(JTable.this, lowest, highest);
            }
            else {
                ((DefaultListSelectionModel) listSelectionModel).fireListSelectionEvent(JTable.this, ppeer.getSelectionIndex(),
                                                                                        ppeer.getSelectionIndex());
            }
        }
    }
    
    
    /**
     * Called back when the user selects a cell in the table
     * to handle the editing
     */
    protected void processCellEdit() {
              
        // If cell selection isn't enabled, don't bother - we can't edit
        if (!cellSelectionEnabled) return;
        
        // If we have no cursor, then we're in the middle of something and it's
        // gone wrong so try to recover as best we can and bail !
	if (swtCursor != null) {
            if (swtCursor.isDisposed()) {
                createTableCursor();
                return;
            }
	}
	else {
	    // If the cursor isn't created, we don't have any data
	    // so there's nothing to edit
	    return;
	}
        
        // Identify selected row and column
        editingRow = ((Integer) swtCursor.getRow().getData("rowindex")).intValue();
        editingColumn = swtCursor.getColumn();
        
        // If the model says we can't edit this field, don't do it
        if (!model.isCellEditable(editingRow, editingColumn)) return;
        
        // Get the editing component for the column
        editingEditor = columnModel.getColumn(editingColumn).getCellEditor();
        if (editingEditor == null) editingEditor = cellEditor;
        editingComponent = editingEditor.getTableCellEditorComponent(this, model.getValueAt(editingRow, editingColumn), true, editingRow, editingColumn);

        // Make sure we are listening for changes to the editor
        if (editingEditor instanceof DefaultCellEditor)
            if (((DefaultCellEditor) editingEditor).getCellEditorListeners() == null)
                editingEditor.addCellEditorListener(this);
        
        // Clean up any previous editor
        if (swtEditor != null) {
            if (swtEditor.getEditor() != null)
                swtEditor.getEditor().dispose();
            swtEditor.dispose();
            swtEditor = null;
        }
        
        // Get current SWT row
        TableItem item = ppeer.getItem(editingRow);

        // The control that will be the editor must be a child of the Table.
        // I use a private class here that basically pretends to be a 
        // container, but allows containment of an SWT Table (which isn't
        // really a container, but needs to be for the editor)
        try {
            editingComponent.setSwingWTParent(new TableEditorContainer(this));
            editingComponent.registerEvents();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Destroy the cursor
        swtCursor.dispose();
        
        // Open the editor in the right place
        swtEditor = new org.eclipse.swt.custom.TableEditor(ppeer);
        swtEditor.horizontalAlignment = SWT.LEFT;
        swtEditor.grabHorizontal = true;
        swtEditor.minimumWidth = 50;
        swtEditor.setEditor(editingComponent.getSWTPeer(), item, editingColumn);
        
        // Make sure the value is correct
        if (editingEditor instanceof DefaultCellEditor) {
            ((DefaultCellEditor) editingEditor).getHandler().setValue(model.getValueAt(editingRow, editingColumn));
        }

        // Assign focus to the editor
        editingComponent.grabFocus();

    }
    
    /** Callback when the editor is cancelled - need to regenerate the table cursor*/
    public void editingCanceled(ChangeEvent e) {
        createTableCursor();
    }    
    
    /** Callback when the editor is stopped - need to regenerate the table cursor
     *  and put any changes back into the model.
     */
    public void editingStopped(ChangeEvent e) {
        // Get new value
        Object value = editingEditor.getCellEditorValue();
        
        // Update model values
        model.setValueAt(value, editingRow, editingColumn);
        modelDirty = true;
        refreshTable(editingRow, editingRow + 1);
        
        // Recreate the cursor
        createTableCursor();
    }

    /**
     * When a column is clicked by the user, this routine gets fired,
     * which generates a mouse event for the thing and then
     * sends it to the TableHeader mouseEvent listeners for
     * dispatch to the user process
     */
    protected void processColumnClick(int index) {

        // If the TableColumnModel is compatible, store
        // the last clicked index there
        if (columnModel instanceof DefaultTableColumnModel)
            ((DefaultTableColumnModel) columnModel).lastColClicked = index;

        MouseEvent e = new MouseEvent(this.getTableHeader());
        e.setID(MouseEvent.MOUSE_CLICKED);
        e.setX(SwingWTUtils.getDisplay().getCursorLocation().x);
        e.setY(SwingWTUtils.getDisplay().getCursorLocation().y);
        e.setButton(SWT.BUTTON1);
        e.clickCount = 1;
        getTableHeader().processMouseEvent(e);
    }
    
    
    public TableCellEditor getCellEditor() {
        return cellEditor;
    }
    
    public TableCellEditor getCellEditor(int row, int col) {
        return cellEditor;
    }
    
    public void setCellEditor(TableCellEditor cell) {
        cellEditor = cell;    
        cellEditor.addCellEditorListener(this);
    }

    public int getEditingColumn() {
        return getSelectedColumn();    
    }
    
    public void setEditingColumn(int aColumn) {
        editingColumn = aColumn;
    }
    
    public int getEditingRow() {
        return getSelectedRow();        
    }
    
    public void setEditingRow(int aRow) {
        editingRow = aRow;
        changeSelection(aRow, editingColumn, false, false);
    }
    
    /**
     * Called by the model to let us know when things change.
     */
    public void tableChanged(TableModelEvent e) {
        modelDirty = true;
        // Redraw if all the columns have changed - this means
        // that some kind of structure change took place.
        if (e.getColumn() == TableModelEvent.ALL_COLUMNS) {
            refreshTable();
        }
        else
            // otherwise, just update the data in the changed rows
            refreshTable(e.getFirstRow(), e.getLastRow());
    }

    public void valueChanged(ListSelectionEvent e) {
        columnSelectionChanged(e);
    }

    public void columnAdded(TableColumnModelEvent e) {
        modelDirty = true;
        refreshTable();
    }

    public void columnMarginChanged(ChangeEvent e) {
        // FIXME: Not implemented
    }

    public void columnMoved(TableColumnModelEvent e) {
        modelDirty = true;
        refreshTable();
    }

    public void columnRemoved(TableColumnModelEvent e) {
        modelDirty = true;
        refreshTable();
    }

    public void columnSelectionChanged(ListSelectionEvent e) {
        // FIXME: Not implemented
    }

    /** Creates the table cursor and maps events to it */
    protected void createTableCursor() {
        swtCursor = new org.eclipse.swt.custom.TableCursor(ppeer, SWT.NONE);     
        registerCursorSelectionEvents();
    }
    
    /**
     * A class containing an SWT table that pretends to be a container
     * - this is for cell editing purposes.
     */
    private class TableEditorContainer extends swingwt.awt.Container {
        public TableEditorContainer(JTable parent) {
            this.peer = parent.getSWTPeer();
            this.composite = (org.eclipse.swt.widgets.Composite) parent.getSWTPeer();
        }
    }

    public boolean isEditing() {
	boolean isEdit = false;
	isEdit = swtCursor != null; // If cursor is null we can't be editing
	if (swtCursor != null)
	    isEdit = swtCursor.isDisposed();  // Cursor is disposed during edit
        return isEdit;
    }

    public void removeColumn(TableColumn tableColumn) {
        getColumnModel().removeColumn(tableColumn);
    }
    
    public Dimension getPreferredScrollableViewportSize()
    {
        // TODO actual value probably less
        return getSize();
    }
    
    public void setPreferredScrollableViewportSize(Dimension dimension)
    {
        // TODO
    }
    
    public boolean isCellEditable(int row, int column)
    {
        return model.isCellEditable(row, column);
    }
    
    public boolean editCellAt(int row, int column, EventObject e) {
        // TODO
        return false;
    }

    public boolean editCellAt(int row, int column) {
        return editCellAt(row, column, null);
    }
    
    protected int columnAtPoint(Point p) {
        return columnModel.getColumnIndexAtX(p.x);
    }
    //TODO: more to do here
    public void setDefaultEditor(Class columnClass, TableCellEditor editor) 
    {
        if (editor != null) 
        {
        	defaultEditors.put(columnClass, editor);
        }
        else 
        {
        	defaultEditors.remove(columnClass);
        }
    }
    //TODO: more to do here    
    public TableCellEditor getDefaultEditor(Class columnClass) 
    {
    	if (columnClass==null)return null;
        TableCellEditor editor = (TableCellEditor) defaultEditors.get(columnClass);
        if (editor == null)
            editor = getDefaultEditor(columnClass.getSuperclass());
        
        return editor;
    }

    /**
     * In Java 1.5 this is an enum of JTable. This implementation tries to preserve
     * the behavior and the syntax used by clients, while being compatible to Java 1.4. 
     * 
     * @author Thiago Tonelli Bartolomei
     */
    public static class PrintMode {
        public static PrintMode NORMAL = new PrintMode();
        public static PrintMode FIT_WIDTH = new PrintMode();
    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @param printMode
     * @param headerFormat
     * @param footerFormat
     * @return
     * @throws PrinterException
     */
    public boolean print(PrintMode printMode,
            MessageFormat headerFormat,
            MessageFormat footerFormat) throws PrinterException {
    
    	return false;
    }
    
    public void setRowSorter(RowSorter sorter) {
    	
    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @param b
     */
    public void setDragEnabled(boolean b) {
    	
    }
}
