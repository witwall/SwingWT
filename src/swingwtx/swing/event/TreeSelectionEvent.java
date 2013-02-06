/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.event;

import swingwtx.swing.tree.*;

public class TreeSelectionEvent extends java.util.EventObject {
    
    protected TreePath[] paths;
    protected boolean[] areNew;
    protected TreePath oldLeadSelectionPath;
    protected TreePath newLeadSelectionPath;

    public TreeSelectionEvent(Object source, TreePath[] paths, boolean[] areNew, TreePath oldLeadSelectionPath, TreePath newLeadSelectionPath) {
	super(source);
	this.paths = paths;
	this.areNew = areNew;
	this.oldLeadSelectionPath = oldLeadSelectionPath;
	this.newLeadSelectionPath = newLeadSelectionPath;
    }

    public TreeSelectionEvent(Object source, TreePath path, boolean isNew, TreePath oldLeadSelectionPath, TreePath newLeadSelectionPath) {
	super(source);
	paths = new TreePath[1];
	paths[0] = path;
	areNew = new boolean[1];
	areNew[0] = isNew;
	this.oldLeadSelectionPath = oldLeadSelectionPath;
	this.newLeadSelectionPath = newLeadSelectionPath;
    }

    public TreePath[] getPaths() {
        int numPaths;
	TreePath[] retPaths;

	numPaths = paths.length;
	retPaths = new TreePath[numPaths];
	System.arraycopy(paths, 0, retPaths, 0, numPaths);
	return retPaths;
    }


    public TreePath getPath() {
	return paths[0];
    }

    public boolean isAddedPath() {
	return areNew[0];
    }

    public boolean isAddedPath(TreePath path) {
	for(int counter = paths.length - 1; counter >= 0; counter--)
	    if(paths[counter].equals(path))
		return areNew[counter];
        throw new IllegalArgumentException("Invalid path");
    }

    public boolean isAddedPath(int index) {
	if (paths == null || index < 0 || index >= paths.length) {
	    throw new IllegalArgumentException("Index too high");
	}
	return areNew[index];
    }

    public TreePath getOldLeadSelectionPath() {
	return oldLeadSelectionPath;
    }

    public TreePath getNewLeadSelectionPath() {
	return newLeadSelectionPath;
    }
    
}
