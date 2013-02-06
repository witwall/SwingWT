/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;

import swingwtx.swing.event.*;

import java.util.*;

/**
 * Handles tying to components and how selections are
 * performed/reported.
 *
 * Note: This class is very important for JTable/JList and if you
 * want to implement your own, you are better off extending this
 * class than starting with something that implements ListSelectionModel.
 *
 * @author Robin Rawson-Tetley
 */
public class DefaultListSelectionModel implements ListSelectionModel {

    /** 
     *  We hold a reference to the component we are being a selection
     *  model for. This allows us to manipulate it's selection
     *  much easier and retrieve info about the
     *  selection from the component (and the component
     *  can update us when the selection changes).
     */
    protected JComponent component = null;
    protected JTable table = null;
    protected JList list = null;
    protected boolean isTable = true;
    protected Vector listeners = new Vector();
    protected int index0 = -1;
    protected int index1 = -1;
    protected int mode = ListSelectionModel.SINGLE_SELECTION;
    protected boolean isAdjusting = false;
    
    public DefaultListSelectionModel() {}
    
    public DefaultListSelectionModel(JComponent component) {
        this.component = component;
        isTable = component instanceof JTable;
        if (isTable)
            table = (JTable) component;
        else
            list = (JList) component;
    }
    
    public void addListSelectionListener(ListSelectionListener x) {
        listeners.add(x);
    }
    
    public void addSelectionInterval(int index0, int index1) {
        setSelectionInterval(index0, index1);
    }
    
    public void clearSelection() {
        if (isTable)
            table.clearSelection();
        else 
            list.clearSelection();
    }
    
    public int getAnchorSelectionIndex() {
        return index0;
    }
    
    public int getLeadSelectionIndex() {
        return index1;
    }
    
    public int getMaxSelectionIndex() {
        return index1;
    }
    
    public int getMinSelectionIndex() {
        return index0;
    }
    
    public int getSelectionMode() {
        return mode;
    }
    
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }
    
    public void insertIndexInterval(int index, int length, boolean before) {
    }
    
    public boolean isSelectedIndex(int index) {
        return index >= index0 && index <= index1;
    }
    
    public boolean isSelectionEmpty() {
        return ((index0 == -1) && (index1 == -1));
    }
    
    public void removeIndexInterval(int index0, int index1) {
        index0 = -1; index1 = -1;
    }
    
    public void removeListSelectionListener(swingwtx.swing.event.ListSelectionListener x) {
        listeners.remove(x);
    }
    
    public void removeSelectionInterval(int index0, int index1) {
        index0 = -1; index1 = -1;
    }
    
    public void setAnchorSelectionIndex(int index) {
        index0 = index;
        updateComponentForSelection();
    }
    
    public void setLeadSelectionIndex(int index) {
        index1 = index;
        updateComponentForSelection();
    }
    
    public void setSelectionInterval(int index0, int index1) {
        this.index0 = index0; this.index1 = index1;
        updateComponentForSelection();
    }
    
    public void setSelectionMode(int selectionMode) {
        mode = selectionMode;
        if (isTable) {
            table.setModelDirty(true);
            table.refreshTable();
        }
    }
    
    public void setValueIsAdjusting(boolean valueIsAdjusting) {
        isAdjusting = valueIsAdjusting;
    }
    
    /**
     * Sets the component to have the selection specified
     * in this model.
     */
    protected void updateComponentForSelection() {
        if (isTable) {
            if (mode == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) 
                for (int i = index0; i <= index1; i++)
                    table.changeSelection(i, 0, true, false);
        }
        else {
            if (mode == ListSelectionModel.MULTIPLE_INTERVAL_SELECTION) {
                int s[] = new int[index1 - index0 + 1];
                for (int i = 0; i < s.length; i++)
                    s[i] = index0 + 1;
                list.setSelectedIndices(s);
            }
        }
    }
    
    /**
     * Callback routine for components so they can update
     * this model with their current selection as user
     * interaction changes them. 
     *
     * This does mean that if you make your own ListSelectionModel
     * and don't extend this class, you're pretty much screwed :-)
     */
    public void fireListSelectionEvent(Object source, int index0, int index1) {
        
        this.index0 = index0;
        this.index1 = index1;
        
        // Drop out if no listeners
        if (listeners.size() == 0) return;
        
        // Fire events
        ListSelectionEvent e = new ListSelectionEvent(source, index0, index1, false);
        for (int i = 0; i < listeners.size(); i++) {
            ((ListSelectionListener) listeners.get(i)).valueChanged(e);
        }
    }
    
}
