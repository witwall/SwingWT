/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwtx.swing.tree;

public class TreePath {
    
    private TreePath parentPath;
    private Object lastPathComponent;
    
    protected TreePath() {
    }
    
    public TreePath(Object singlePath) {
        lastPathComponent = singlePath;
        parentPath = null;
    }
    
    protected TreePath(TreePath parent, Object lastElement) {
        parentPath = parent;
        lastPathComponent = lastElement;
    }
    
    protected TreePath(Object[] path, int length) {
        lastPathComponent = path[path.length - length];
        if(length > 1)
            parentPath = new TreePath(path, length - 1);
    }
    
    public TreePath(Object[] path) {
        lastPathComponent = path[0];
        if (path.length > 1) {
            parentPath  = new TreePath(path, path.length - 1);
        }
    }
    
    public Object[] getPath() {
        int i = getPathCount();
        Object[] result = new Object[i--];
        for(TreePath path = this; path != null; path = path.parentPath) {
            result[i--] = path.lastPathComponent;
        }
        return result;
    }
    
    public Object getLastPathComponent() {
        return lastPathComponent;
    }
    
    public int getPathCount() {
        int result = 0;
        for (TreePath path = this; path != null; path = path.parentPath) {
            result++;
        }
        return result;
    }
    
    public Object getPathComponent(int element) {
        TreePath path = this;
        int pathLength = getPathCount();
        for(int i = pathLength-1; i != element; i--) {
            path = path.parentPath;
        }
        return path.lastPathComponent;
    }
    
    public TreePath pathByAddingChild(Object child) {
        return new TreePath(this, child);
    }
    
    public TreePath getParentPath() {
        return parentPath;
    }
    
    public String toString() {
        StringBuffer s = new StringBuffer("[");
        for(int c = 0, m = getPathCount(); c<m;
	    c++) {
            if(c> 0)
                s.append(", ");
            s.append(getPathComponent(c));
        }
        s.append("]");
        return s.toString();
    }
    
}
