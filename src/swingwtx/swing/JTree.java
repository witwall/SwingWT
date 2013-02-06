/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net


 */

package swingwtx.swing;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.*;

import swingwtx.swing.event.*;
import swingwtx.swing.tree.*;
import swingwtx.swing.plaf.*;

import java.util.*;

public class JTree extends swingwtx.swing.JComponent implements TreeModelListener, CellEditorListener {

    protected Tree ppeer = null;
    protected TreeModel model = null;
    protected TreeCellRenderer cellRenderer = new DefaultTreeCellRenderer();
    protected TreeCellEditor cellEditor = new DefaultCellEditor(new JTextField());
    protected boolean isEditable = false;
    protected boolean isRootVisible = true;
    protected TreePath anchorSelectionPath = null;
    /** SWT Editor */
    protected org.eclipse.swt.custom.TreeEditor swtEditor = null;
    protected org.eclipse.swt.widgets.TreeItem editingTreeItem = null;
    protected Object editingNode = null;
    protected swingwt.awt.Component editingComponent = null;

    /** Thread safe return value */
    private Object retval = null;

    protected Vector treeWillExpansionListeners = new Vector();
    protected Vector treeExpansionListeners = new Vector();
    protected Vector treeSelectionListeners = new Vector();

    private HashMap mPeerMap = new HashMap();

    public JTree() { cellEditor.addCellEditorListener(this); }
    public JTree(TreeNode root) { this.model = new DefaultTreeModel(root); cellEditor.addCellEditorListener(this); }
    public JTree(TreeModel model) { this.model = model; cellEditor.addCellEditorListener(this); }

    // ps: - use a hashmap for peer searching
    protected TreeItem getPeerTreeItem(Object swingNode) {
        return (TreeItem)mPeerMap.get(swingNode);
    }

    public void addTreeExpansionListener(TreeExpansionListener l) {
        treeExpansionListeners.add(l);
    }
    public void removeTreeExpansionListener(TreeExpansionListener l) {
        treeExpansionListeners.remove(l);
    }
    public void addWillTreeExpansionListener(TreeWillExpandListener l) {

        treeWillExpansionListeners.add(l);
    }
    public void removeTreeWillExpansionListener(TreeWillExpandListener l) {
        treeWillExpansionListeners.remove(l);
    }
    public void addTreeSelectionListener(TreeSelectionListener l) {
        treeSelectionListeners.add(l);
    }
    public void removeTreeSelectionListener(TreeSelectionListener l) {
        treeSelectionListeners.remove(l);
    }

    public void setModel(TreeModel model){
        this.model = model;
        model.addTreeModelListener(this);
        drawTree();
    }

    public TreeModel getModel(){
        return model;
    }

    public boolean isEditable() { return isEditable; }
    public void setEditable(boolean b) { isEditable = b; }
    public void setCellEditor(TreeCellEditor cell) { cellEditor = cell;
}
    public TreeCellEditor getCellEditor() { return cellEditor; }

    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new Tree(parent.getComposite(), SWT.BORDER );

        // Editor
        swtEditor = new org.eclipse.swt.custom.TreeEditor(ppeer);

        // Cached properties
        peer = ppeer;
        this.parent = parent;

        drawTree();

        registerTreeEvents();

    }

    /**
     * Registers some tree specific events
     * and maps them to Swing events.
     */
    protected void registerTreeEvents() {

        ppeer.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                processTreeSelection(e);
                processTreeEdit(e.item.getData(), (org.eclipse.swt.widgets.TreeItem) e.item);
            }
            public void widgetDefaultSelected(SelectionEvent e) {
            }
        });

        ppeer.addTreeListener(new TreeListener() {
            public void treeCollapsed(TreeEvent e) {
                processTreeExpansion(e, true);
            }
            public void treeExpanded(TreeEvent e) {
                processTreeExpansion(e, false);
            }
        });

    }

    /**
     * Called back when the user selects a node to allow them to
     * edit the value (if the tree is editable)
     */
    protected void processTreeEdit(Object node, org.eclipse.swt.widgets.TreeItem item) {

        // If cell selection isn't enabled, don't bother - we can't edit

        if (!isEditable) return;

        if (!isPathEditable(getTreePath(node))) return;

        // We can't edit things that aren't descended from DefaultMutableTreeNode
        // ps: I think we should just fire value changed through TreeSelectionEvent
        if (!(node instanceof DefaultMutableTreeNode)) return;

        editingTreeItem = item;
        editingNode = node;

        // Get the editing component for the tree
        Object userObject = null;
        if(node instanceof DefaultMutableTreeNode) {
            userObject = ((DefaultMutableTreeNode) node).getUserObject();
        }
        editingComponent = cellEditor.getTreeCellEditorComponent(this,
                userObject, true, true, model.isLeaf(node), 1);

        // Clean up any previous editor
        if (swtEditor != null) {
            if (swtEditor.getEditor() != null)
                swtEditor.getEditor().dispose();
            swtEditor.dispose();
            swtEditor = null;
        }

        // The control that will be the editor must be a child of the Tree.
        // I use a private class here that basically pretends to be a
        // container, but allows containment of an SWT Table (which isn't
        // really a container, but needs to be for the editor)
        try {
            editingComponent.setSwingWTParent(new TreeEditorContainer(this));
            editingComponent.registerEvents();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Open the editor in the right place
        swtEditor = new org.eclipse.swt.custom.TreeEditor(ppeer);
        swtEditor.horizontalAlignment = SWT.LEFT;
        swtEditor.grabHorizontal = true;
        swtEditor.minimumWidth = 50;
        swtEditor.setEditor(editingComponent.getSWTPeer(), item);

        // Make sure the value is correct
        if ((cellEditor instanceof DefaultCellEditor) && (node instanceof DefaultMutableTreeNode)) {
            ((DefaultCellEditor) cellEditor).getHandler().setValue(((DefaultMutableTreeNode)node).getUserObject());

        }

        // Assign focus to the editor
        editingComponent.grabFocus();
    }

    /** Callback when the editor is stopped - need to
     *  put any changes back into the model.
     */
    public void editingStopped(ChangeEvent e) {
        // Get new value
        Object value = cellEditor.getCellEditorValue();

        // Update model values
        if(editingNode instanceof DefaultMutableTreeNode) {
            ((DefaultMutableTreeNode)editingNode).setUserObject(value);
        }

        // Update on-screen value using cell renderer
            // Get the peer from the node
            org.eclipse.swt.widgets.TreeItem item = getPeerTreeItem(editingNode);
            if (item == null) { // ps: just in case
                return;
            }
            // Get the renderer for this thing
            boolean isleaf = model.isLeaf(editingNode);
            JLabel cr = (JLabel) cellRenderer.getTreeCellRendererComponent(this,
                    editingNode,
                    false, isleaf,
                    isleaf, 0,
                    true);

            // Textual value
            item.setText(cr.getText());

            // Icon
            if (cr.getIcon() != null)
 	        item.setImage(SwingWTUtils.getSWTImageFromSwingIcon(this, cr.getIcon()));

            // Colours
            if (cr.getBackground() != null)
                if (cr.getBackground().getSWTColor() != null)
		    item.setBackground(cr.getBackground().getSWTColor());
            if (cr.getForeground() != null)
                if (cr.getForeground().getSWTColor() != null)
		    item.setForeground(cr.getForeground().getSWTColor());

            // Font
            if (cr.hasSetFont())
                item.setFont(cr.getFont().getSWTFont());
        }

    public void fireTreeExpanded(TreePath path) {
        TreeExpansionEvent event = new TreeExpansionEvent(this, path);
        Iterator i = treeExpansionListeners.iterator();
        while(i.hasNext()) {
            ((TreeExpansionListener)i.next()).treeExpanded(event);
        }
    }

    public TreePath getAnchorSelectionPath() {
        return anchorSelectionPath;
    }
    public void setAnchorSelectionPath(TreePath path) {
        this.anchorSelectionPath = path;
    }

    protected void processTreeSelection(SelectionEvent e) {
        if (treeSelectionListeners.size() == 0) return;
        Iterator i = treeSelectionListeners.iterator();
        //TreeSelectionEvent ev = new TreeSelectionEvent(e.data, getTreePath((TreeNode) e.item.getData()), false, getTreePath((TreeNode) e.item.getData()), getTreePath((TreeNode) e.item.getData()));
        // Check this - are we sure it's the JTree and not the selected item as the event source?
        TreeSelectionEvent ev = new TreeSelectionEvent(this, getTreePath(e.item.getData()), false, getTreePath(e.item.getData()), getTreePath(e.item.getData()));
        while (i.hasNext()) {
            TreeSelectionListener l = (TreeSelectionListener) i.next();
            l.valueChanged(ev);
        }
    }

    protected void processTreeExpansion(TreeEvent e, boolean collapse) {

        try {
            fireTreeWillExpandOrCollapse(e,collapse);
        }
        catch (ExpandVetoException ex) {
            e.doit = false;
            return;
        }

	 // Re-render the expanded/collapsed node since it's changed
	 renderNode((org.eclipse.swt.widgets.TreeItem) e.item);

        if (treeExpansionListeners.size() == 0) return;
        Iterator i = treeExpansionListeners.iterator();
        TreeExpansionEvent ev = new TreeExpansionEvent(e.item.getData(), getTreePath(e.item.getData()));
        while (i.hasNext()) {
            TreeExpansionListener l = (TreeExpansionListener) i.next();
            if (collapse)
                l.treeCollapsed(ev);
            else
                l.treeExpanded(ev);
        }
    }

    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is created using <code>path</code> parameter.
     *
     * @param e the TreeExpansionEvent instance
     * @param collapse boolean indication for collapse state of the tree

     */
    public void fireTreeWillExpandOrCollapse(TreeEvent e, boolean collapse) throws ExpandVetoException {
        if (treeWillExpansionListeners.size() != 0) {
            Iterator itrWillExpand = treeWillExpansionListeners.iterator();
            TreeExpansionEvent ev = new TreeExpansionEvent(e.item.getData(), getTreePath(e.item.getData()));
            while (itrWillExpand.hasNext()) {
                TreeWillExpandListener l = (TreeWillExpandListener) itrWillExpand.next();
                if (collapse) {
                    l.treeWillCollapse(ev);
                }
                else {
                    l.treeWillExpand(ev);
                }
            }
        }
    }


    private Vector findTreePath(Object root, Object searchedNode) {
        if(root == searchedNode) {
            Vector result = new Vector();
            result.add(root);
            return result;
        }
        int count = model.getChildCount(root);
        for(int i = 0; i < count; i++) {
            Vector result = findTreePath(model.getChild(root, i), searchedNode);
            if(result != null) {
                result.add(root);
                return result;
            }
        }
        return null;
    }

    /** Returns a TreePath from a given node - not a real Swing
     *  method, but useful for us here for generating events */
    public TreePath getTreePath(Object node) {
        Vector path = findTreePath(model.getRoot(), node);
        Object[] retPath;
        if(path != null) {
            retPath = path.toArray();
            path.removeAllElements();
            path = null;
        }
        else {
            retPath = new Object[]{node};
        }
        return new TreePath(retPath);
    }

    public void addSelectionPaths(TreePath[] path) {/* TODO */}

    public TreePath getSelectionPath() {

        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return null;

        SwingUtilities.invokeSync( new Runnable() {
            public void run() {
                TreeItem[] selpath = ppeer.getSelection();
                if (selpath.length == 0) { retval = null; return; }

                Object[] nodes = new Object[selpath.length];
                for (int i = 0; i < selpath.length; i++) {
                    nodes[i] = selpath[i].getData();
                }
                retval = new TreePath(nodes);
            }
        });

        return (TreePath) retval;
    }

    public TreePath[] getSelectionPaths() {
        return new TreePath[] { getSelectionPath() };
    }

    public void addSelectionPath(TreePath t) {
        setSelectionPath(t);
    }

    public void removeSelectionPath(TreePath t) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.deselectAll();
            }
        });
    }

    public void removeSelectionPaths(TreePath t[]) {
        removeSelectionPath(null);
    }

    /** NOT IMPLEMENTED */
    public void setScrollsOnExpand(boolean b) {
    }

    /** NOT IMPLEMENTED */
    public void setShowsRootHandles(boolean b) {
    }

    /** NOT IMPLEMENTED */
    public void setSelectionModel(TreeSelectionModel model) {
    }

    /** NOT IMPLEMENTED - RETURNS DUMMY STUB */
    public TreeSelectionModel getSelectionModel() {
        return new DefaultTreeSelectionModel();
    }

    /**
     *  Convert the given tree node value to text - The
     *  default implementation simply returns 
     *  <code>(value == null)?"":value.toString(<code>,
     *  however sublcasses may overide this method to 
     *  provide alternative behaviour  Used by renderers
     *  to provide a text representation of the value
     *
     *  @param value The value of the tree node
     *  @param selected if the tree node is selected
     *  @param expanded if the tree node is expanded
     *  @param leaf it the tree node is a leaf
     *  @param row the row of the display
     *  @param hasFocus if the tree node has focus
     *  @return the text representation of the tree node
     */
     public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
	  return (value == null) ? "" : value.toString();
     }

    /** Scrolls the Tree to show the selected path */
    public void scrollPathToVisible(final TreePath t) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    Object dmt = t.getLastPathComponent();
                    ppeer.showItem(getPeerTreeItem(dmt));
                }
            }
        });
    }

    public void setSelectionPaths(TreePath[] t) {
        setSelectionPath(t[0]);
    }

    public void setSelectionPath(TreePath t) {

        if (t == null) return;
        if (t.getPathCount() == 0) return;
        if (ppeer == null) return; // ps: SWT widget yet not created

        final Object[] nodes = t.getPath();

        SwingUtilities.invokeSync( new Runnable() {
            public void run() {
                TreeItem[] ti = new TreeItem[nodes.length];
                for (int i = 0; i < nodes.length; i++) {
                    ti[i] = getPeerTreeItem(nodes[i]);
                }
                ppeer.setSelection(ti);
            }
        });

    }

    public TreePath getPathForLocation(int x, int y) {
        return getClosestPathForLocation(x, y);
    }

    public TreePath getClosestPathForLocation(final int x, final int y)
{
        final TreePath[] path = new TreePath[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    TreeItem item = ppeer.getItem(new org.eclipse.swt.graphics.Point(x, y));
                    if (item!=null)
                        path[0] = new TreePath(item.getData());
                    else
                        path[0] = null;
                }
                else
                    path[0] = null;
            }
        });
        return path[0];
    }

    /**
     * Because SWT Trees don't have a row concept (apart from
     * a row count), we have to make a guess. Now, because
     * our JTree.drawTree() works down the model from it's
     * root, we guess that drawn item x = row x - which should
     * work for the most part (apart from it being horribly
     * wrong with collapsed items).
     */
    public TreePath getPathForRow(final int row) {
        final TreePath[] path = new TreePath[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    TreeItem item = ppeer.getItems()[row];
                    path[0] = new TreePath(item.getData());
                }
                else
                    path[0] = null;
            }
        });
        return path[0];
    }

    public int getRowCount() {
        final int[] rowCount = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    rowCount[0] = ppeer.getItemHeight();
                else
                    rowCount[0] = 0;
            }
        });
        return rowCount[0];
    }

    /**
     * @see getPathForRow for faults with row based stuff - use
     * path methods if possible.
     */
    public void expandRow(int row) {
        expandPath(getPathForRow(row));
    }

    /**
     * @see getPathForRow for faults with row based stuff - use
     * path methods if possible.
     */
    public void collapseRow(int row) {
        collapsePath(getPathForRow(row));
    }

    public void expandPath(TreePath parent) {
        if (parent == null)
            return;
        Object element = parent.getLastPathComponent();
        if (parent.getParentPath()!=null && parent.getLastPathComponent()!=null){
            expandPath(parent.getParentPath());
        }
        if (element == null)
            return;

        fireTreeExpanded(parent);

        TreeItem peeritem = getPeerTreeItem(element);
        if (peeritem != null) {
           if (!peeritem.isDisposed()) {
               if (model.isLeaf(element)) {
                   if (!peeritem.getExpanded()) {
                       peeritem.setExpanded(true); renderNode(peeritem);
                   }
               }
           }
        }
    }

    public void collapsePath(TreePath parent) {
        if (parent == null)
            return;
        Object element = parent.getLastPathComponent();
        if (parent.getParentPath()!=null && parent.getLastPathComponent()!=null){
            collapsePath(parent.getParentPath());
        }
        if (element == null)
            return;

            TreeItem peeritem = getPeerTreeItem(element);
            if (peeritem != null)
               if (!peeritem.isDisposed()) {
                   if (model.getChildCount(element) != 0) {
                       if (peeritem.getExpanded()) {
                           peeritem.setExpanded(false); renderNode(peeritem);
         		}
                   }
               }

    }

    /**
     * Draws the tree from the model data
     */
    public void drawTree() {

        // Don't bother drawing if no peer has been created
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return;

        // No point if we haven't got a model either      
        if (model == null) return;
        
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {

                // Clear the tree
                ppeer.removeAll();

                // ROOT BASED RENDER:
                // =====================================
                
                Object root = model.getRoot();
                if (root != null) {
                    if(isRootVisible){
                        addNodes(ppeer, null, root);
                    }
                    else {
                        int count = model.getChildCount(root);
                        if (count > 0) {
                            for (int i = 0; i < count; i++) {
                                addNodes(ppeer, null, model.getChild(root, i));
                            }
                        }
                    }
                }
                
                // Auto-expand root node (believe this is Swing behavior)
                //expandPath( new TreePath((TreeNode)root) );
            }
        });
    }

    public Object getLastSelectedPathComponent() {
        TreePath t = getSelectionPath();
        return t != null ? t.getLastPathComponent() : null;
    }

    /**
     * Responsible for adding a branch of the tree. Will
     * recursively call itself until the tree is complete.
     */
    private void addNodes(Tree tree, TreeItem treeItem, Object swingNode) {

        // This node
        TreeItem item = null;
        if (tree != null) {
            item = new TreeItem(tree, SWT.BORDER);
        }
        else
            item = new TreeItem(treeItem, SWT.BORDER);

        // Get user object - little unsure here if people aren't using
        // DefaultMutableTreeNode as the stock TreeNode interface doesn't
        // seem to support retreival of a data object from the node.
        // Need to do some research here and see how Swing handles it.
        // Set peer so user apps can tweak items directly if they want
        mPeerMap.put(swingNode, item);

        // Get the renderer for this thing
        swingwt.awt.Component renderer =
            cellRenderer.getTreeCellRendererComponent(
                this,
                swingNode,
                false,              // selected
                !(tree == null),    // expanded (true for root nodes here)
                model.isLeaf(swingNode), // leaf
                0,                  // row
                true);              // hasFocus

	 // Draw it
	 renderNode(item, renderer);

        // Store the node in the peer (could be nasty cyclic
        // reference for GC).
        item.setData(swingNode);

        // Any children
        int count = model.getChildCount(swingNode);
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                addNodes(null, item, model.getChild(swingNode, i));
            }
        }

        if (tree != null) {
	        // Swing auto expands root nodes
	        item.setExpanded(true);
        }
    }


    /** Overloaded renderNode - grabs the cell renderer and
     *  swingNode from the TreeItem and calls the other renderNode -
     *  this is useful once the node has already been created and
     *  we need to redraw it. Used by collapse/expand stuff to
     *  update icons correctly.
     */
    private void renderNode(org.eclipse.swt.widgets.TreeItem item) {

	 Object swingNode = item.getData();

	 // Get user object - little unsure here if people aren't using
        // DefaultMutableTreeNode as the stock TreeNode interface doesn't
        // seem to support retreival of a data object from the node.
        // Need to do some research here and see how Swing handles it.

        // Set peer so user apps can tweak items directly if they want
        // ps: use hashmap
        mPeerMap.put(swingNode, item);

        // Get the renderer for this thing
        swingwt.awt.Component renderer =
            cellRenderer.getTreeCellRendererComponent(
                this,
                swingNode,
                false,              // selected
                !item.getExpanded(),// expanded
                model.isLeaf(swingNode), // leaf
                0,                  // row
                true);              // hasFocus

	 renderNode(item, renderer);

    }

    /** Helper routine - given an SWT TreeItem for a node and
     *  and a component cell renderer, this routine will
     *  appropriately draw the SWT TreeItem. This is used by
     *  drawTree().
     */
    private void renderNode(org.eclipse.swt.widgets.TreeItem item, swingwt.awt.Component renderer) {

        // If the renderer is a JLabel, get the
        // text and image from it.
        if (renderer instanceof JLabel) {

            JLabel cr = (JLabel) renderer;

            // Textual value
            item.setText(cr.getText());

            // Icon
            if (cr.getIcon() != null)
		item.setImage(SwingWTUtils.getSWTImageFromSwingIcon(this, cr.getIcon()));

            // Colours
            if (cr.getBackground() != null)
                if (cr.getBackground().getSWTColor() != null)
		    item.setBackground(cr.getBackground().getSWTColor());
            if (cr.getForeground() != null)
                if (cr.getForeground().getSWTColor() != null)
		    item.setForeground(cr.getForeground().getSWTColor());

            // Font
            if (cr.hasSetFont())
                item.setFont(cr.getFont().getSWTFont());

        }
        else
        {
            item.setText("CellRenderer not JLabel: " + renderer.getClass().getName());
        }
    }

    public void setCellRenderer(TreeCellRenderer renderer) {
cellRenderer = renderer; }
    public TreeCellRenderer getCellRenderer() { return cellRenderer; }


    public void treeNodesChanged(TreeModelEvent e) { drawTree(); }
    public void treeNodesInserted(TreeModelEvent e) { drawTree(); }
    public void treeNodesRemoved(TreeModelEvent e) { drawTree(); }
    public void treeStructureChanged(TreeModelEvent e) { drawTree(); }

    /**
     * Determines whether or not the root node from the TreeModel is visible.
     * @param rootVisible   true if the root node of the tree is to be displayed
     */
    public void setRootVisible(boolean rootVisible){
        isRootVisible   = rootVisible;
    }

    /**
     * Returns true if the root node of the tree is displayed
     * @return true if the root node of the tree is displayed
     */
    public boolean isRootVisible(){
        return isRootVisible;
    }

    public void editingCanceled(ChangeEvent e) {
    }

    /**
     * A class containing an SWT tree that pretends to be a container
     * - this is for cell editing purposes.
     */
    private class TreeEditorContainer extends swingwt.awt.Container {
        public TreeEditorContainer(JTree parent) {
            this.peer = parent.getSWTPeer();
            this.composite = (org.eclipse.swt.widgets.Composite) parent.getSWTPeer();
        }
    }

    public boolean isPathEditable(TreePath path) {
        return true;
    }

    public boolean isExpanded(int j)
    {
        // TODO 
        return false;
    }
    
    public int getRowForLocation(int x, int y)
    {
        // TODO
        return 0;
    }
    
    public void setSelectionRow(int row)
    {
        // TODO
    }
    
    public boolean isEditing()
    {
        // TODO
        return false;
    }
    
    public void stopEditing()
    {
        // TODO
    }
    
    public void addTreeWillExpandListener(TreeWillExpandListener tree)
    {
        // TODO
    }
    
    protected void setInvokesStopCellEditing(boolean b)
    {
        // TODO
    }
    
    protected void startEditingAtPath(TreePath newChildPath)
    {
        // TODO
    }
    
    protected TreeUI getUI()
    {
        // TODO
        return null;
    }
    
    public void treeDidChange()
    {
        // TODO
    }
    
    public void setSelectionInterval(int i, int rowCount)
    {
        // TODO
    }
	/**
	 * TODO Comment!!
	 *
	 * @param dragEnabled
	 */
	public void setDragEnabled(boolean dragEnabled) {
		// TODO Auto-generated method stub
		
	}


}

