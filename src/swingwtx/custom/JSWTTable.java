/*
 * SwingWT Copyright(c)2003-2004, R. Rawson-Tetley
 * 
 * For more information on distributing and using this program, please see thef
 * accompanying "COPYING" file.
 * 
 * Contact me by electronic mail: bobintetley@users.sourceforge.net
 * 
 *  
 */

package swingwtx.custom;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableCursor;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import swingwt.awt.*;
import swingwt.awt.event.*;
import swingwtx.swing.*;
import swingwtx.swing.event.*;
import swingwtx.swing.table.*;

/**
 * Alternative JTable implementation which uses the
 * JFace API to get functionality. Very limited support
 * for SWT editors at present.
 * 
 * @author aki
 */
public class JSWTTable extends swingwtx.swing.JTable implements
        TableModelListener, TableColumnModelListener, ListSelectionListener,
        CellEditorListener
{
    /** SwingWT TableHeader / not used */
    protected JTableHeader header = null;
    /** The ColumnModel for this table */
    protected TableColumnModel columnModel = null;
    /** The SelectionModel for the Table */
    protected ListSelectionModel listSelectionModel = null;

    /** Cache - whether to show grid lines */
    protected boolean pLines = true;
    /** The selected row of the Table */
    protected int pSelRow = -1;
    /** The selected column of the Table */
    protected int pSelCol = -1;
    /** Cache of value for current cell editor */
    protected int editingColumn = -1;
    /** Cache of values for current cell editor */
    protected int editingRow = -1;


    /** Whether cell selection (and thus cell editing) is enabled  */
    protected boolean cellSelectionEnabled = true;
    /** Whether row and column selection are enabled  */
    protected boolean rowSelection = true, columnSelection = false;
    /** The SWT table cursor */
    protected TableCursor swtCursor = null;

    private SimpleContentProvider contentProvider = new SimpleContentProvider();
    private TableViewer viewer = null;
    private Table ppeer = null;
    private String[] columnNames = null;
    private boolean setModel = false;
    private TableModel model = null;
    
    

    /**
     * Whether the model has been changed. Used by setSwingWTParent to decide
     * whether or not to refresh the data.
     */
    private boolean modelDirty = true;
    private HashMap defaultRenderers = new HashMap();
    private HashMap sorter = new HashMap();
    private boolean autoSort = false;
    private int sortedColumn = -1;
    private boolean reverseSort = false;

    /**
     * Creates an empty Table, a default TableModel will by used
     */
    public JSWTTable()
    {
        this(null);
    }

    /**
     * Creates an empty Table with the a number of columns and rows.
     * 
     * @param numRows
     *            The number of rows in the new Table
     * @param numCols
     *            The number of columns in the new Table
     */
    public JSWTTable(int numRows, int numCols)
    {
        this(new DefaultTableModel(numRows, numCols));
    }

    /**
     * Creates a new Table with the given data.
     * @param rowData The Rows t display in the Table
     * @param columnNames The ColumnNames
     */
    public JSWTTable(Object[][] rowData, Object[] columnNames)
    {
        this(new DefaultTableModel(rowData, columnNames));
    }

    /**
     * Creates a new Table with the given data.
     * @param rowData The Rows t display in the Table
     * @param columnNames The ColumnNames
     */
    public JSWTTable(Vector rowData, Vector columnNames)
    {
        this(new DefaultTableModel(rowData, columnNames));
    }

    /**
     * Creates a new Table from the given Model.
     * 
     * @param dm The TableModel for the Table.
     */
    public JSWTTable(TableModel dm)
    {
        this(dm, null);
    }

    /**
     * Creates a new Table from the given Model and ColumnModel.
     * 
     * @param dm The TableModel for the Table.
     * @param columnModel The ColumnModel for the Table.
     */
    public JSWTTable(TableModel dm, TableColumnModel columnModel)
    {
        this(dm, columnModel, null);
    }

    /**
     * Creates a new Table from the given Model, ColumnModel and SelectionModel.
     * 
     * @param dm The TableModel for the Table.
     * @param columnModel The ColumnModel for the Table
     * @param selectionModel The Selectionmodel for the Table
     */
    public JSWTTable(TableModel dm, TableColumnModel columnModel,
            ListSelectionModel selectionModel)
    {

        if (dm == null)
            dm = new DefaultTableModel();

        setModel(dm);

        if (columnModel != null)
        {
            setColumnModel(columnModel);
        }

        if (selectionModel == null)
            selectionModel = new DefaultListSelectionModel(this);
        setSelectionModel(selectionModel);
        
        // create a default renderer for the class Object
        defaultRenderers.put(Object.class, new DefaultTableCellRenderer());
    }

    /**
     * Returns the TableModel of this Table.
     * @return The TableModel
     */
    public TableModel getModel()
    {
        return model;
    }
    
    /**
     * Returns the number of Rows in this Table.
     * @return The number of Rows
     */
    public int getRowCount() 
    { 
        return model.getRowCount(); 
    }
    
    /**
     * Returns the number of Columns in this Table.
     * @return The number of Columns
     */
    public int getColumnCount() 
    { 
        return model.getColumnCount(); 
    }
    
    /**
     * Select nothing in the Table
     */
    public void clearSelection() 
    { 
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return; ppeer.deselectAll(); 
    }
    
    /**
     * Re-Layout the Table
     */
    public void doLayout() 
    { 
        modelDirty = true;
        refreshTable(); 
    }

    /**
     * Returns the name of a column
     * @param index The index of a column
     * @return The name of the column
     */
    public String getColumnName(int index) 
    { 
        return model.getColumnName(index); 
    }
    
    /**
     * Returns the TableColumn to the given identifier
     * @param identifier A Column-Identifier
     * @return The TableColumn to the Identifier
     */
    public swingwtx.swing.table.TableColumn getColumn(Object identifier) 
    { 
        return columnModel.getColumn(columnModel.getColumnIndex(identifier)); 
    }
    
    /**
     * Returns the TableColumn to the given index
     * @param index A Column-Index
     * @return The TableColumn to the Index
     */    
    public swingwtx.swing.table.TableColumn getColumn(int index) 
    { 
        return columnModel.getColumn(index); 
    }
    
    /**
     * Returns the index of the selected column, -1 if no column is selected
     * @return The selected column
     */
    public int getSelectedColumn() 
    { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) 
            return pSelCol; 
        else return -1; 
    }
    
    /**
     * Returns the ColumnModel.
     * @return The ColumnModel of this Table
     * 
     */
    public TableColumnModel getColumnModel() 
    { 
        return columnModel; 
    }
    
    /**
     * Returns whether column selection is allowed or not 
     * @param b not used
     * @return Column selection allowed
     */
    public boolean getColumnSelectionAllowed(boolean b) 
    { 
        return columnSelection; 
    }
    
    /**
     * Sets whether column selection is allowed or not 
     * @param b Column selection allowed
     */
    public void setColumnSelectionAllowed(boolean b) 
    { 
        columnSelection = b; 
    }

    /**
     * Returns whether row selection is allowed or not 
     * @param b not used
     * @return Row selection allowed
     */
    public boolean getRowSelectionAllowed(boolean b) 
    { 
        return rowSelection; 
    }
    
    /**
     * @return the current selected row
     */
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
    
    /**
     * Returns the number of selected rows
     * @return Number of selected rows
     */
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
    
    /**
     * Returns the indices of the selected rows
     * @return Indices of selected rows
     */
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
    
    
    /**
     * Creates cell editors for the table columns in the range from.. to 
     */
    private void createDefaultEditors(int from, int to)
    {
        org.eclipse.jface.viewers.CellEditor[] editors = new org.eclipse.jface.viewers.CellEditor[getColumnCount()];
        org.eclipse.jface.viewers.CellEditor[] oldEditors = viewer.getCellEditors();
        
        if (oldEditors != null)
        {
            for( int i=0; i< Math.min(oldEditors.length, editors.length); i++)
                editors[i] = oldEditors[i];
        }
          
        for (int i = from; i < to; i++)
        {         
            org.eclipse.jface.viewers.CellEditor ce = new TextCellEditor(ppeer);
            ce.getControl().setData("isDefault", "true");

            if (model.getColumnClass(i) == Boolean.class)
            {
                ce = new CheckboxCellEditor();
            }
            if (model.getColumnClass(i) == Integer.class)
            {
                ((Text) ce.getControl())
                        .addVerifyListener(new VerifyListener()
                        {
                            public void verifyText(VerifyEvent e)
                            {
                                e.doit = e.text
                                        .matches("[\\-0-9]*");
                            }
                        });
            }
            editors[i] = ce;
        }
        viewer.setCellEditors(editors);
    }
    
    

    /**
     * Assigns a new TableModel to the Table
     * @param dm The new TableModel
     * @param noRefresh Wether the Table should be updeted (not used)
     */
    public void setModel(TableModel dm, boolean noRefresh)
    {
        if (model != dm)
        {
            model = dm;

            if (sorter != null)
                sorter.clear();
            generateDefaultColumnModel();

            int columnCount = columnModel.getColumnCount();

            columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++)
            {
                columnNames[i] = columnModel.getColumn(i).getHeaderValue()
                        .toString();
            }
        }

        if (SwingWTUtils.isSWTControlAvailable(ppeer))
        {
            SwingUtilities.invokeSync(new Runnable()
            {
                public void run()
                {
                    registerTableListSelectionEvents();
                    
                    if (viewer == null)
                        viewer = new TableViewer(ppeer);
                    
                    viewer.setContentProvider(contentProvider);
                    viewer.setColumnProperties(columnNames);
                    viewer.setInput(model);

                    createDefaultEditors(0,getColumnCount());

                    viewer.setLabelProvider(new SimpleLabelProvider());
                    viewer.setCellModifier(new SimpleCellModifier());

                    modelDirty = true;
                    refreshTable();
                }
            });
        } else
        {
            setModel = true;
        }
    }
    
    /**
     * Assigns a new TableModel to the Table
     * @param dm The new TableModel
     */
    public void setModel(TableModel dm)
    {
        setModel(dm, true);
    }

    /**
     * Sets the ColumnModel of this Table
     * @param model The new ColumnModel
     */
    public void setColumnModel(TableColumnModel model)
    {
        if (columnModel == model)
            return;
        columnModel = model;
        modelDirty = true;
        refreshTable();
        columnModel.addColumnModelListener(this);
    }
    
    /**
     * Enables/disables cell selection
     * @param b Cell selectio enabled
     */
    public void setCellSelectionEnabled(boolean b) 
    { 
        cellSelectionEnabled = b; 
        modelDirty = true; 
        refreshTable(); 
    }

    /**
     * Creates the TableColumns from the Model
     */
    public void createDefaultColumnsFromModel()
    {
        TableColumnModel tableColumnModel = getColumnModel();

        // Delete all existing columns in the column model
        if (model != null && tableColumnModel != null)
        {

            while (tableColumnModel.getColumnCount() > 0)
                tableColumnModel.removeColumn(tableColumnModel.getColumn(0));

            for (int i = 0; i < model.getColumnCount(); i++)
            {

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

                tc.setModelIndex(i);
                tc.setResizable(true);

                columnModel.addColumn(tc);
            }
        }
    }
    
    /**
     * Returns if the table grid is show
     * @return Table gird visible
     */
    public boolean getShowHorizontalLines() 
    { 
        return isShowGrid(); 
    }
    
    /**
     * Returns if the table grid is show
     * @return Table gird visible
     */
    public boolean getShowVerticalLines() 
    { 
        return isShowGrid(); 
    }
    
    /**
     * Returns if the table grid is show
     * @return Table gird visible
     */
    public boolean isShowHorizontalLines() 
    { 
        return isShowGrid(); 
    }
    
    /**
     * Returns if the table grid is show
     * @return Table gird visible
     */
    public boolean isShowVerticalLines() 
    { 
        return isShowGrid(); 
    }
    
    /**
     * Returns if the table grid is show
     * @return Table gird visible
     */
    public boolean getShowGrid() 
    { 
        return isShowGrid(); 
    }
    
    /**
     * Returns if the table grid is show
     * @return Table gird visible
     */
    public boolean isShowGrid() 
    {
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

    /**
     * Adds a new column to the Table
     * @param tableColumn The new TableColumn
     */
    public void addColumn(TableColumn tableColumn)
    {
        int oldCount = getColumnModel().getColumnCount();
        if (tableColumn.getHeaderValue() == null)
            tableColumn.setHeaderValue(getModel().getColumnName(
                    tableColumn.getModelIndex()));
        getColumnModel().addColumn(tableColumn);
        
        setModelDirty(true);
        refreshTable();
        viewer.setColumnProperties(columnNames);
        createDefaultEditors(oldCount, oldCount+1);
    }
    
    /**
     * Returns the SelectionModel used by this Table.
     * @return The SelectionModel 
     */
    public ListSelectionModel getSelectionModel()
    {
        return listSelectionModel;
    }

    /**
     * Sets the SelectionModel used by this Table.
     * @param l The SelectionModel 
     */
    public void setSelectionModel(ListSelectionModel l)
    {
        if (l != null)
        {
            listSelectionModel = l;
            l.addListSelectionListener(this);
        }
    }

    /**
     * Sets the SelectionMode for the Table
     * @param mode The new Selectionmode
     */
    public void setSelectionMode(int mode)
    {
        listSelectionModel.setSelectionMode(mode);
    }
    
    /**
     * Enables/disables rowselection
     * @param b Row selectio allowed
     */
    public void setRowSelectionAllowed(boolean b) 
    { 
        rowSelection = b; 
    }
    
    /**
     * Sets an interval of selected rows
     * @param first The first selcted row
     * @param last The last selected row
     */
    public void setRowSelectionInterval(final int first, final int last) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.setSelection(first, last);
            }
        });
    }
    
    /**
     * Sets whether the table grid is visible
     * @param b Grid shown
     */
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
    
    /**
     * Sets whether the table grid is visible
     * @param b Grid shown
     */
    public void setShowHorizontalLines(boolean b) 
    { 
        setShowGrid(b); 
    }
    
    /**
     * Sets whether the table grid is visible
     * @param b Grid shown
     */
    public void setShowVerticalLines(boolean b) 
    { 
        setShowGrid(b);
    }

    /**
     * Sets the default renderer for all columns of a Class
     * @param columnClass The Class for which the Renderer will be set
     * @param tableCellRenderer The new Renderer
     */
    public void setDefaultRenderer(Class columnClass,
            TableCellRenderer tableCellRenderer)
    {
        if (tableCellRenderer == null)
            defaultRenderers.remove(columnClass);
        else
            defaultRenderers.put(columnClass, tableCellRenderer);
    }
    
   /**
    * Sets a SWT cell editor for this column
    * @param column The index of the column 
    * @param editor A SWT cell editor
    */
    public void setSWTEditor(int column, org.eclipse.jface.viewers.CellEditor editor)
    {
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
        {
            editor.getControl().setParent(ppeer);
            org.eclipse.jface.viewers.CellEditor[] editors = viewer.getCellEditors();
            if (column < editors.length)
                editors[column] = editor;
        }
    }
    
    /**
     * Removes a SWT cell editor from a table column
     * @param column The index of the column 
     */
    public void removeSWTEditor(int column)
    {
        org.eclipse.jface.viewers.CellEditor[] editors = viewer.getCellEditors();
        if (column < editors.length)
            editors[column] = null;
    }



    /**
     * Returns the Renderer for a cell
     * @param row The row index of the cell
     * @param column The column index of the cell
     * @return The Renderer
     * 
     */
    public TableCellRenderer getCellRenderer(int row, int column)
    {
        TableCellRenderer renderer = columnModel.getColumn(column)
                .getCellRenderer();
        if (renderer == null)
        {
            Class columnClass = getModel().getColumnClass(
                    convertColumnIndexToModel(column));         
            renderer = getDefaultRenderer(columnClass);
        }

        return renderer;
    }
    
    /**
     * Returns if cell selection is enable
     * @param b not used
     * @return cellSelectionEnabled
     */
    public boolean getCellSelectionEnabled(boolean b) 
    { 
        return cellSelectionEnabled; 
    }
    
    /**
     * Returns the dufault Rederer for a class
     * @param columnClass The column class
     * @return The default renderer
     * 
     */
    public TableCellRenderer getDefaultRenderer(Class columnClass) {
        
        if (columnClass == null) return null;
        
        TableCellRenderer renderer = (TableCellRenderer) defaultRenderers.get(columnClass);
        if (renderer == null)
            renderer = getDefaultRenderer(columnClass.getSuperclass());
        
        return renderer;
    }

    /**
     * Returns wether a cell is selected or not.
     * 
     * @param row The row index of the cell
     * @param col The column index of the cell
     * @return
     * 
     */
    public boolean isCellSelected(int row, int col)
    {
        int srow = -1;
        int scol = -1;
        try
        {
            srow = ((Integer) swtCursor.getRow().getData("rowindex"))
                    .intValue();
            scol = swtCursor.getColumn();
        } catch (Exception e)
        {
        }
        if (srow == row && scol == col)
            return true;
        if (getSelectedRow() == row)
            return true;
        return false;
    }

    /**
     * Tells the table that it needs to redraw when refreshTable() is next
     * called.
     * 
     * @param b
     *            Whether the table should be redrawn when refreshTable() is
     *            next called.
     */
    public void setModelDirty(boolean b)
    {
        modelDirty = b;
    }

    /**
     * @param row
     * @param col
     * @param toggle
     * @param extend
     * 
     */
    public void changeSelection(final int row, final int col,
            final boolean toggle, final boolean extend)
    {
        pSelRow = row;
        pSelCol = col;
        SwingUtilities.invokeSync(new Runnable()
        {
            public void run()
            {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                {
                    ppeer.setSelection(row);
                    processListSelection();
                }
            }
        });
    }

    /** Scrolls the table to the selected row. This is not a Swing method */
    public void showSelection()
    {
        SwingUtilities.invokeSync(new Runnable()
        {
            public void run()
            {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.showSelection();
            }
        });
    }

    /**
     * SWT table columns aren't movable, so columnIndex == modelIndex
     * 
     * @param index
     * @return The 'converted' index
     */
    public int convertColumnIndexToModel(int index)
    {
        return index;
    }

    /**
     * Given that we have a valid table model, generates the default column
     * model for it.
     */
    private void generateDefaultColumnModel()
    {
        columnModel = new DefaultTableColumnModel();
        createDefaultColumnsFromModel();
    }

    /**
     * Returns the header of the Table.
     * @return The TableHeader
     *  
     */
    public JTableHeader getTableHeader()
    {
        if (header == null)
            header = new JTableHeader(this);
        return header;
    }
    
    /**
     * Returns the value of the spezified cell
     * @param row The row index of the cell
     * @param col The column index of the cell
     * @return The value in this cell
     */
    public Object getValueAt(int row, int col) 
    { 
        if (model != null)
            return model.getValueAt(row, col); 
        else
            return null;
    }

    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer and
     * load in any cached properties.
     * 
     * @param parent
     *            The SwingWT Parent Frame
     * @throws Exception
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception
    {
        descendantHasPeer = true;

        int style = SWT.BORDER;
        // This is semi-incorrect, however this will mimic Swing behavior
        // when row selection is on and column selection is off.
        if (rowSelection && !columnSelection)
            style |= SWT.FULL_SELECTION;

        ppeer = new Table(parent.getComposite(), style);

        peer = ppeer;
        this.parent = parent;

        // Cached values
        ppeer.setLinesVisible(pLines);

        if (setModel)
            setModel(model);

        // Selection
        if (pSelRow != -1)
            ppeer.setSelection(pSelRow);

        // register key events
        ppeer.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                if (e.keyCode == SWT.CR)
                    JSWTTable.this.processCellEdit();
            }

            public void keyReleased(KeyEvent e)
            {
                if (e.keyCode == SWT.ARROW_RIGHT)
                    pSelCol++;
                if (e.keyCode == SWT.ARROW_LEFT)
                    pSelCol--;
            }
        });
        
        // register mouse events
        ppeer.addMouseListener(new MouseAdapter()
        {
            public void mouseDown(org.eclipse.swt.events.MouseEvent e) 
            {
                pSelRow = ppeer.getSelectionIndex();
            }
        });
    }
    
    /**
     * Sets the cell in the Table(modell) to the given value
     * @param value The new value of the cell
     * @param row The row index of the cell
     * @param col The column index of the cell
     */
    public void setValueAt(Object value, int row, int col) 
    { 
        if (model != null)
        	model.setValueAt(value, row, col); 
    }

    /**
     * Sets whether the model is dirty for forcing updates
     * 
     * @param b
     *            modelDirty
     */
    public void setDirty(boolean b)
    {
        modelDirty = b;
    }

    /**
     * Forces a complete rebuild of the peer from the data model.
     */
    public void refreshTable()
    {

        // Don't do anything if the model hasn't changed
        if (!modelDirty || model == null || ppeer == null)
            return;
        modelDirty = false;

        SwingUtilities.invokeSync(new Runnable()
        {
            public void run()
            {
                // Don't do anything if we can't see the table anyway - the
                // model is still dirty though
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                {
                    modelDirty = true;
                    return;
                }

                // Clear out everything from the peer if necessary
                ppeer.removeAll();

                int columnCount = Math.min(ppeer.getColumnCount(), columnModel
                        .getColumnCount());
                
                // save columnwidths to the model to reuse after the refresh 
                for (int i = 0; i < columnCount; i++)
                {
                    columnModel.getColumn(i).setWidth(ppeer.getColumn(i).getWidth());
                }
                
                columnCount = Math.min(model.getColumnCount(), columnModel
                        .getColumnCount());

                if (ppeer.getColumnCount() < columnCount)
                {
                    while (ppeer.getColumnCount() < columnCount)
                    {
                        final org.eclipse.swt.widgets.TableColumn tc = new org.eclipse.swt.widgets.TableColumn(
                                ppeer, ppeer.getColumnCount());
                    }
                } 
                else
                {
                    while (ppeer.getColumnCount() > columnCount)
                        ppeer.getColumn(ppeer.getColumnCount() - 1).dispose();
                }

                ppeer.setHeaderVisible(true);

                columnNames = new String[columnCount];
                
                for (int i = 0; i < columnCount; i++)
                {
                    org.eclipse.swt.widgets.TableColumn tc = ppeer.getColumn(i);

                    tc.setText(columnModel.getColumn(i).getHeaderValue()
                            .toString());
                    tc.setResizable(columnModel.getColumn(i).isResizable());
                    columnNames[i] = tc.getText();

                    // If no col width was set in the model, use the width of
                    // the text in the column as a starting point, then scan all
                    // the data
                    // in the column to find the widest one
                    if (columnModel.getColumn(i).getWidth() == 0)
                    {
                        int colwidth = SwingWTUtils
                                .getRenderStringWidth(columnModel.getColumn(i)
                                        .getHeaderValue().toString());
                        int contentwidth = 0;
                        for (int z = 0; z < model.getRowCount(); z++)
                        {
                            Object value = model.getValueAt(z, i);
                            if (value == null)
                                value = "";
                            contentwidth = (SwingWTUtils
                                    .getRenderStringWidth(value.toString()));
                            if (contentwidth > colwidth)
                                colwidth = contentwidth;
                        }
                        tc.setWidth(colwidth);
                    } else
                        tc.setWidth(columnModel.getColumn(i).getWidth());

                    // Event catcher for a column being clicked
                    if (tc.getData("listenerSet") == null) // not set yet
                    {
	                    final int index = i;
	                    tc.addSelectionListener(new org.eclipse.swt.events.SelectionListener()
	                            {
	                                public void widgetSelected(
	                                        org.eclipse.swt.events.SelectionEvent e)
	                                {
	                                    processColumnClick(index);
	                                }
	
	                                public void widgetDefaultSelected(
	                                        org.eclipse.swt.events.SelectionEvent e)
	                                {
	                                }
	                            });
	                    //this prevents that this listener is set twice
	                    tc.setData("listenerSet", new Boolean(true));
                    }
                }

                // Cached values and events
                ppeer.setLinesVisible(pLines);
                assertCachedProperties();
                registerEvents();

                viewer.refresh();
            }
        });
    }

    /**
     * Refreshs the Table
     * @param rowFrom not used
     * @param rowTo not used
     */
    public void refreshTable(final int rowFrom, final int rowTo) {
        refreshTable();
    }
    /**
     * Just like refreshTable(), except it does not generate the column headers
     * and only updates the rows specified. This is used for change events from
     * the model.
     * 
     * @param rowFrom
     *            The first row to refresh
     * @param rowTo
     *            The last row to refresh
     */
    public void insertRows(final int rowFrom, final int rowTo)
    {
        for (int i = rowFrom; i <= rowTo; i++)
        {
            Object[] item = new Object[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++)
            {
                item[j] = model.getValueAt(i - 1, j);
            }
            contentProvider.addItem(item);
        }
    }

    /**
     * Assigns events to the table for selection
     */
    protected void registerTableListSelectionEvents()
    {
        ppeer.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter()
                {
                    public void widgetSelected(
                            org.eclipse.swt.events.SelectionEvent e)
                    {
                        processListSelection();
                    }
                });
    }

    /**
     * Called back when a row/cell is selected in the table to fire events on to
     * user processes.
     */
    protected void processListSelection()
    {
        // Handle ListSelectionModel
        if (listSelectionModel instanceof DefaultListSelectionModel)
        {
            if (listSelectionModel.getSelectionMode() == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION)
            {
                int lowest = ppeer.getSelectionIndex();
                int highest = ppeer.getSelectionIndex();
                int sel[] = ppeer.getSelectionIndices();
                for (int i = 0; i < sel.length; i++)
                {
                    if (lowest > sel[i])
                        lowest = sel[i];
                    if (highest < sel[i])
                        highest = sel[i];
                }
                ((DefaultListSelectionModel) listSelectionModel)
                        .fireListSelectionEvent(this, lowest, highest);
            } else
            {
                ((DefaultListSelectionModel) listSelectionModel)
                        .fireListSelectionEvent(this,
                                ppeer.getSelectionIndex(), ppeer
                                        .getSelectionIndex());
            }
        }
    }

    /**
     * When a column is clicked by the user, this routine gets fired, which
     * generates a mouse event for the thing and then sends it to the
     * TableHeader mouseEvent listeners for dispatch to the user process
     * 
     * @param index
     *            The index of the Column
     */
    protected void processColumnClick(int index)
    {
        pSelCol = index;
        
        // If the TableColumnModel is compatible, store
        // the last clicked index there
        if (columnModel instanceof DefaultTableColumnModel)
            ((DefaultTableColumnModel) columnModel).lastColClicked = index;

        MouseEvent e = new MouseEvent(this.getTableHeader());
        e.setID(MouseEvent.MOUSE_CLICKED);
        e.setX(SwingWTUtils.getDisplay().getCursorLocation().x);
        e.setY(SwingWTUtils.getDisplay().getCursorLocation().y);
        e.setButton(SWT.BUTTON1);
        e.clickCount = 01;
        getTableHeader().processMouseEvent(e);

        //sort
        if (sortedColumn==index)	// sort reverse if this column is already sorted
            reverseSort = !reverseSort;
        viewer.setSorter(null);
        sortedColumn = -1;	// no sorted column
        ViewerSorter newSorter = (ViewerSorter) sorter.get(columnModel
                .getColumn(index));
        if (newSorter != null)	// if there is a sorter for the clicked column
        {
            sortedColumn = index;
            viewer.setSorter(newSorter);
        }
        else
        {
            reverseSort = false;
        }
    }

    /**
     * Returns the edited Column
     * @return The current edditing column
     * 
     */
    public int getEditingColumn()
    {
        return getSelectedColumn();
    }

    /**
     * Sets the edited Column
     * @param aColumn The new edited Column 
     */
    public void setEditingColumn(int aColumn)
    {
        editingColumn = aColumn;
    }

    /**
     * Returns the selected row
     * @return The selected row
     */
    public int getEditingRow()
    {
        return getSelectedRow();
    }

    /**
     * Only sets the selected Row
     * @param aRow The new editing Row
     */
    public void setEditingRow(int aRow)
    {
        editingRow = aRow;
        changeSelection(aRow, editingColumn, false, false);
    }

    /**
     * Called by the model to let us know when things change.
     * 
     * @param e
     */
    public void tableChanged(TableModelEvent e)
    {
        modelDirty = true;
        // Redraw if all the columns have changed - this means
        // that some kind of structure change took place.
        if (e.getType() == TableModelEvent.INSERT)
            insertRows(e.getFirstRow(), e.getLastRow());
        else
        {
            if (e.getType() == TableModelEvent.UPDATE)
            {
                for (int i = e.getFirstRow(); i <= e.getLastRow(); i++)
                {
                    contentProvider.updateItem(((IStructuredSelection) viewer
                            .getSelection()).getFirstElement());

                }
            }

            else if (e.getType() == TableModelEvent.DELETE)
            {
                for (int i = e.getFirstRow(); i <= e.getLastRow(); i++)
                {
                    ppeer.select(e.getFirstRow());
                    contentProvider.removeItem(((IStructuredSelection) viewer
                            .getSelection()).getFirstElement());
                    ppeer.select(e.getFirstRow());
                }
            }
        }
    }

    /**
     * Called when the selection changed
     * @param e
     */
    public void valueChanged(ListSelectionEvent e)
    {
        columnSelectionChanged(e);
    }

    /**
     * Called when a column was added
     * @param e
     */
    public void columnAdded(TableColumnModelEvent e)
    {
        modelDirty = true;
        refreshTable();
    }

    /**
     * Called when a column was moved
     * @param e
     */
    public void columnMoved(TableColumnModelEvent e)
    {
        modelDirty = true;
        refreshTable();
    }

    /**
     * Called when a column is removed
     * @param e
     */
    public void columnRemoved(TableColumnModelEvent e)
    {
        modelDirty = true;
        refreshTable();
    }


    /**
     * Tells if a cell editor is active
     * @return True if a cell editor is active, otherwise false 
     * 
     */
    public boolean isEditing()
    {
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
        {
            return viewer.isCellEditorActive();
        }
        return false;
        
    }

    /**
     * Removes a given Column from the Table
     * @param tableColumn The Column to remove
     */
    public void removeColumn(TableColumn tableColumn)
    {
        int index = getColumnModel().getColumnIndex(tableColumn.getIdentifier());
        getColumnModel().removeColumn(tableColumn);
        
        org.eclipse.jface.viewers.CellEditor[] editors = new org.eclipse.jface.viewers.CellEditor[getColumnCount()];
        org.eclipse.jface.viewers.CellEditor[] oldEditors = viewer.getCellEditors();
        for (int i=0; i<index; i++)
            editors[i] = oldEditors[i]; 
        for (int i=index; i < editors.length; i++)
            editors[i] = oldEditors[i+1];
        viewer.setCellEditors(editors);
        
        setModelDirty(true);
        refreshTable();
        viewer.setColumnProperties(columnNames);
               
    }
    
    /**
     * Does a Table refresh.
     */
    public void resizeAndRepaint() 
    { 
        refreshTable(); 
    }
    
    /**
     * Selects everything in the Table.
     */
    public void selectAll() 
    { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.selectAll(); 
    }
    

    /**
     * Rreturns the renderer component for the given cell-renderer
     * @param renderer TAbleCellRenderer
     * @param row Rowindex
     * @param column Columnindex
     * @return The renderer component
     * 
     */
    public swingwt.awt.Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
		return renderer.getTableCellRendererComponent( this,
		    getValueAt(row,column),
		    isCellSelected(row,column),
		    false /*TODO*/,
		    row,
		    column);
    }

    /**
     * Called back when the user selects a cell in the table to handle the
     * editing
     */
    protected void processCellEdit()
    {
        if (viewer == null || ppeer.getItemCount() == 0)
            return;

        if (pSelCol < 0)
            pSelCol = 0;
        if (pSelCol >= model.getColumnCount())
            pSelCol = model.getColumnCount() - 1;

        pSelRow = ppeer.getSelectionIndex();
        if (pSelRow < 0)
            pSelRow = 0;

        viewer.editElement(((TableItem) ppeer.getItem(pSelRow)).getData(),
                pSelCol);
    }

    /**
     * Callback when the editor is cancelled - need to regenerate the table
     * cursor
     * 
     * @param e
     *            The ChangeEvent
     */
    public void editingCanceled(ChangeEvent e)
    {
    }

    /**
     * Callback when the editor is stopped - need to regenerate the table cursor
     * and put any changes back into the model.
     * 
     * @param e
     *            The ChangeEvent
     */
    public void editingStopped(ChangeEvent e)
    {
    }

    // -----------------------------------------------------------------

    /**
     * Sets a TableSorter for a column, the sorter will sort the table using the
     * given Comparator
     * 
     * @param index
     *            ColumnIndex of the column to sort
     * @param comp
     *            Comparator for the sorting
     */
    public void setSorter(final int index, final Comparator comp)
    {
        if (index > columnModel.getColumnCount())
            return;

        sorter.put(columnModel.getColumn(index), new ViewerSorter()
        {
            public int compare(Viewer viewer, Object o1, Object o2)
            {
                Object[] row1 = (Object[]) ((TableRow) o1).data;
                Object[] row2 = (Object[]) ((TableRow) o2).data;

                if (reverseSort)
                    return comp.compare(
                            ((Object[]) row2)[sortedColumn], 
                            ((Object[]) row1)[sortedColumn]);
                else
                    return comp.compare(
                            ((Object[]) row1)[sortedColumn], 
                            ((Object[]) row2)[sortedColumn]);
            }
        });
        refreshTable();
    }

    /**
     * Removes a sorter from a column
     * 
     * @param index
     *            ColumnIndex of the Sorter to remove
     */
    public void removeSorter(int index)
    {
        sorter.remove(columnModel.getColumn(index));
        viewer.setSorter(null);
    }

    /**
     * @return Returns the autoSort.
     */
    public boolean isAutoSort()
    {
        return autoSort;
    }

    /**
     * @param autoSort
     *            The autoSort to set.
     */
    public void setAutoSort(boolean autoSort)
    {
        this.autoSort = autoSort;
    }

    // -----------------------------------------------------------------

    /**
     * This class provides content for the SWT Table Widget and its TableViewer,
     * the contents are extracted from a Tablemodel.
     */
    class SimpleContentProvider implements IStructuredContentProvider 
    {

        /** (non-Javadoc)
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer v, Object oldInput, Object newInput)
        {
            if (newInput != null)
                ((TableModel) newInput).addTableModelListener(JSWTTable.this);
            if (oldInput != null)
                ((TableModel) oldInput)
                        .removeTableModelListener(JSWTTable.this);
        }

        /** (non-Javadoc)
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose()
        {
            model.removeTableModelListener(JSWTTable.this);
        }

        /**
         * Extract the data from the model
         * 
         * @param parent
         *            not used
         * @return the data from the TableModel as an array of TableRows
         */
        public Object[] getElements(Object parent)
        {
            Object[] data = new Object[model.getRowCount()];
            for (int i = 0; i < model.getRowCount(); i++)
            {
                Object[] row = new Object[model.getColumnCount()];
                for (int j = 0; j < model.getColumnCount(); j++)
                {
                    row[j] = model.getValueAt(i, j);
                }
                data[i] = new TableRow(row, i);
            }

            return data;
        }

        /**
         * Adds a new TableItem to the Table the Item will be created by the
         * TableViewer with thr data Object
         * 
         * @param data
         *            The data for the new TableRow
         */
        public void addItem(Object data)
        {
            if (viewer != null)
            {
                TableRow row = new TableRow(data, ppeer.getItemCount());
                viewer.add(row);
            }
        }

        /**
         * Remove an item from the table
         * 
         * @param item
         *            The Item to remove
         */
        public void removeItem(Object item)
        {
            if (viewer != null)
                viewer.remove(item);
        }

        /**
         * Update an item in the table
         * 
         * @param item
         *            The Item to update
         */
        public void updateItem(Object item)
        {
            if (viewer != null)
                viewer.update(item, null);
        }
    }

    /**
     * This class defines the Labels wich will be shown in each TableCell
     * 
     * @author aki
     */
    public class SimpleLabelProvider extends LabelProvider implements
            ITableLabelProvider
    {

        /**
         * Returns the images that are displayed int the TableColumns to use
         * Images the User must set a renderer, which returns an JLabel
         * containining an Icon. If no renderer is set, no Icons are displayed
         * 
         * @param element
         *            The TableRow for which the Image is searched
         * @param columnIndex
         *            The TableColumn for which the Image is searched
         * @return The image for the given TableRow and column
         */
        public Image getColumnImage(Object element, int columnIndex)
        {
            if (columnIndex >= getColumnCount()) return null;
            if (element != null && ((TableRow) element).data != null)
            {
                Object[] data = (Object[]) ((TableRow) element).data;
                int rowIndex = ((TableRow) element).index;

                TableCellRenderer renderer = getCellRenderer(rowIndex, columnIndex);
                if (renderer != null)
                {
                    Component c = renderer.getTableCellRendererComponent(
                            JSWTTable.this, data[columnIndex], true, true,
                            rowIndex, columnIndex);
                    if (c instanceof JLabel)
                    {
                        JLabel l = (JLabel) c;
                        return SwingWTUtils.getSWTImageFromSwingIcon(l, l
                                .getIcon());
                    }
                }
            }
            return null;
        }

        /**
         * Returns the Strings that are displayed int the TableColumns to use
         * special text the User must set a renderer, which returns an JLabel.
         * If no renderer is set, the toString() Method will be used to get a
         * String for the cell
         * 
         * @param element
         *            The TableRow for which the Text is searched
         * @param columnIndex
         *            The TableColumn for which the Text is searched
         * @return The Text for the given TableRow and column
         */
        public String getColumnText(Object element, int columnIndex)
        {
            if (columnIndex >= getColumnCount()) return "";
            if (element != null && ((TableRow) element).data != null)
            {
                Object[] data = (Object[]) ((TableRow) element).data;
                int rowIndex = ((TableRow) element).index;

                TableCellRenderer renderer = getCellRenderer(rowIndex, columnIndex);
                if (renderer != null)
                {
                    Component c = renderer.getTableCellRendererComponent(
                            JSWTTable.this, data[columnIndex], true, true,
                            rowIndex, columnIndex);
                    if (c instanceof JLabel)
                    {
                        JLabel l = (JLabel) c;
                        return l.getText();
                    }
                }
                return data[columnIndex].toString();
            }
            return "";
        }
    }

    /**
     * Handles modifications in the cells of the Table
     * 
     * @author aki
     */
    class SimpleCellModifier implements ICellModifier
    {

        /**
         * Decides if a cell con be modified, according to the Tablemodel
         * 
         * @param element
         *            The TableRow
         * @param column
         *            The TableColumn
         * @return can you modify this Cell?
         *  
         */
        public boolean canModify(Object element, String column)
        {
            int columnIndex = Arrays.asList(columnNames).indexOf(column);
            int rowIndex = ((TableRow) element).index;
            return model.isCellEditable(rowIndex, columnIndex);
        }

        /**
         * Returns the value to edit in a CellEditor
         * 
         * @param element
         *            The TableRow to edit
         * @param column
         *            The Column to edit
         * @return The value to edit
         */
        public Object getValue(Object element, String column)
        {
            int columnIndex = Arrays.asList(columnNames).indexOf(column);

            if (element != null && ((TableRow) element).data != null)
            {
                Object[] o = (Object[]) ((TableRow) element).data;

                if (viewer.getCellEditors()[columnIndex] instanceof TextCellEditor
                        && viewer.getCellEditors()[columnIndex].getControl()
                                .getData("isDefault").equals("true"))

                    return o[columnIndex].toString();
                else
                    return o[columnIndex];
            }
            return "";
        }

        /**
         * Changes the content of a cell to the new Value
         * 
         * @param element
         *            The TableRow to modify
         * @param column
         *            The Column to modify
         * @param newValue
         *            The new Value
         */
        public void modify(Object element, String column, Object newValue)
        {
            int columnIndex = Arrays.asList(columnNames).indexOf(column);

            TableItem item = (TableItem) element;
            TableRow row = (TableRow) item.getData();

            model.setValueAt(newValue, row.index, columnIndex);

            ((Object[]) row.data)[columnIndex] = newValue;
            contentProvider.updateItem((TableRow) item.getData());

            // re-sort if autosort is active
            if (isAutoSort() && sortedColumn == columnIndex)
            {
                ViewerSorter newSorter = (ViewerSorter) sorter.get(columnModel
                        .getColumn(columnIndex));
                if (newSorter != null)
                {
                    viewer.setSorter(null);
                    viewer.setSorter(newSorter);
                }
            }

            pSelCol = columnIndex;
            ppeer.setSelection(ppeer.indexOf(item));
            ppeer.showSelection();
        }
    }

    /**
     * 
     * @author aki
     */
    class TableRow
    {
        /** the index in the Tablemodel for this TableRow */
        public int index;

        /** the data belonging to this TableRow */
        public Object data;

        /**
         * Creates a new TableRow with the data and index
         * 
         * @param data
         *            The data for the new Row
         * @param index
         *            The index of the new Row
         */
        public TableRow(Object data, int index)
        {
            this.data = data;
            this.index = index;
        }

        /**
         * Two Rows are equal if they have the same index
         * 
         * @param row
         *            The Row to compare
         * @return Are two TableRows equal
         */
        public boolean equals(TableRow row)
        {
            return (this.index == row.index);
        }
    }
}
