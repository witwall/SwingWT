/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.tree;

import java.beans.PropertyChangeListener;
import swingwtx.swing.event.*;

/**
 *
 * FIXME: NOT IMPLEMENTED
 */
public class DefaultTreeSelectionModel implements TreeSelectionModel {
    
    public DefaultTreeSelectionModel() {
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
    }
    
    public void addSelectionPath(TreePath path) {
    }
    
    public void addSelectionPaths(TreePath[] paths) {
    }
    
    public void addTreeSelectionListener(TreeSelectionListener x) {
    }
    
    public void clearSelection() {
    }
    
    public TreePath getLeadSelectionPath() {
        return null;
    }
    
    public int getLeadSelectionRow() {
        return 0;
    }
    
    public int getMaxSelectionRow() {
        return 0;
    }
    
    public int getMinSelectionRow() {
        return 0;
    }
    
    public RowMapper getRowMapper() {
        return null;
    }
    
    public int getSelectionCount() {
        return 0;
    }
    
    public int getSelectionMode() {
        return 0;
    }
    
    public TreePath getSelectionPath() {
        return null;
    }
    
    public TreePath[] getSelectionPaths() {
        return null;
    }
    
    public int[] getSelectionRows() {
        return null;
    }
    
    public boolean isPathSelected(TreePath path) {
        return false;
    }
    
    public boolean isRowSelected(int row) {
        return false;
    }
    
    public boolean isSelectionEmpty() {
        return false;
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
    }
    
    public void removeSelectionPath(TreePath path) {
    }
    
    public void removeSelectionPaths(TreePath[] paths) {
    }
    
    public void removeTreeSelectionListener(TreeSelectionListener x) {
    }
    
    public void resetRowSelection() {
    }
    
    public void setRowMapper(RowMapper newMapper) {
    }
    
    public void setSelectionMode(int mode) {
    }
    
    public void setSelectionPath(TreePath path) {
    }
    
    public void setSelectionPaths(TreePath[] paths) {
    }
    
}
