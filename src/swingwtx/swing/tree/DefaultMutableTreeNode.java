/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.tree;

import java.util.*;

public class DefaultMutableTreeNode implements MutableTreeNode {

    /** The SWT TreeItem peer */
    public org.eclipse.swt.widgets.TreeItem peer = null;
    
    /** An empty enumeration */
    static public final Enumeration EMPTY_ENUMERATION = new Enumeration() {
        public Object nextElement() {
            throw new NoSuchElementException("No elements");
        }
        public boolean hasMoreElements() { return false; }
    };

    protected MutableTreeNode parent;
    protected boolean allowsChildren;
    protected Vector children;
    protected Object userObject;

    public DefaultMutableTreeNode() {
	this(null);
    }
    
    public DefaultMutableTreeNode(Object userObject) {
	this(userObject, true);
    }

    public DefaultMutableTreeNode(Object userObject, boolean allowsChildren) {
	super();
	this.allowsChildren = allowsChildren;
	this.userObject = userObject;
        parent = null;
    }

    public void insert(MutableTreeNode newChild, int childIndex) {
        MutableTreeNode oldParent = (MutableTreeNode) newChild.getParent();
        if (oldParent != null)
            oldParent.remove(newChild);
        newChild.setParent(this);
        if (children == null)
            children = new Vector();
        children.insertElementAt(newChild, childIndex); 
    }

    public void remove(int childIndex) {
	MutableTreeNode child = (MutableTreeNode) getChildAt(childIndex);
	children.removeElementAt(childIndex);
	child.setParent(null);
    }
    
    public void add(MutableTreeNode newChild) {
	if(newChild.getParent() == this && newChild != null)
	    insert(newChild, getChildCount() - 1);
	else
	    insert(newChild, getChildCount());
    }

    public void setParent(MutableTreeNode newParent) {
	parent = newParent;
    }

    public TreeNode getParent() {
	return parent;
    }    

    public void removeFromParent() {
	MutableTreeNode parent = (MutableTreeNode) getParent();
	parent.remove(this);
    }

    public TreeNode getChildAt(int index) {
	return (TreeNode) children.elementAt(index);
    }

    public int getChildCount() {
	if (children == null)
	    return 0;
	else
	    return children.size();
    }

    public int getIndex(TreeNode aChild) {
	return children.indexOf(aChild);
    }

    public Enumeration children() {
	if (children != null)
	    return children.elements();	
        else
            return EMPTY_ENUMERATION;
    }
    
    public boolean getAllowsChildren() {
	return allowsChildren;
    }
    
    public void setAllowsChildren(boolean allows) {
	if (allows != allowsChildren) {
	    allowsChildren = allows;
	    if (!allowsChildren) {
		removeAllChildren();
	    }
	}
    }

    public Object getUserObject() {
	return userObject;
    }
    
    public void setUserObject(Object userObject) {
	this.userObject = userObject;
    }

    public void remove(MutableTreeNode aChild) {
	remove(getIndex(aChild));	
    }
    
    public void removeAllChildren() {
	for (int i = getChildCount() - 1; i >= 0; i--) {
	    remove(i);
	}
    }
    
    public boolean isLeaf() {
        return getChildCount() > 0;    
    }

    public TreeNode[] getPath() {
        ArrayList path = new ArrayList();
	TreeNode current = this;
	while (current!=null) {
	    path.add(0,current);
	    current = current.getParent();
	}
	return (TreeNode[])path.toArray(new TreeNode[0]);
    }
		    
    
    public boolean isNodeAncestor(TreeNode anotherNode) {
	TreeNode ancestor = this;
	while (ancestor != null) {
	    if (ancestor == anotherNode) {
		return true;
	    }
            ancestor = ancestor.getParent();
	}
	return false;
    }

    public boolean isNodeDescendant(DefaultMutableTreeNode anotherNode) {
	return anotherNode.isNodeAncestor(this);
    }

    public TreeNode getRoot() {
	TreeNode anc = this;
	TreeNode previous;
        previous = anc;
	while (anc != null) {
	    anc = anc.getParent();
	}
	return previous;
    }

    public boolean isRoot() {
	return getParent() == null;
    }

    public String toString() {
	if (userObject == null) {
	    return null;
	} else {
	    return userObject.toString();
	}
    }

    public Enumeration depthFirstEnumeration() {
        Vector v = buildDepthFirstVector(this, new Vector());
        return v.elements();
    }

    public int getLeafCount() {
        Vector v = buildLeafOnlyVector(this, new Vector());
        return v.size();
    }

    private static Vector buildDepthFirstVector(TreeNode node, Vector v) {
        for (int i = 0; i < node.getChildCount(); i++) {
            buildDepthFirstVector(node.getChildAt(i), v);
        }
        v.add(node);
        
        return v;
    }

    private static Vector buildLeafOnlyVector(TreeNode node, Vector v) {
        for (int i = 0; i < node.getChildCount(); i++) {
            buildLeafOnlyVector(node.getChildAt(i), v);
        }
        if (node.isLeaf()) {
            v.add(node);
        }
        
        return v;
    }

    public Object[] getUserObjectPath() {
        ArrayList path = new ArrayList();
    	TreeNode current = this;
    	while (current!=null) {
    	    path.add(0,((DefaultMutableTreeNode)current).getUserObject());
    	    current = current.getParent();
    	}
    	return path.toArray();
    }
	// Tested with wrong parameters and null    
    public TreeNode getChildAfter(TreeNode aChild) 
    {
    	if (aChild == null){throw new IllegalArgumentException("child is null");}
    	int index = getIndex(aChild);
    	if (index == -1){throw new IllegalArgumentException("not a child");}
    	if (index < getChildCount() - 1){return getChildAt(index + 1);} else {return null;}
    }
    	// Tested with wrong parameters and null
    public TreeNode getChildBefore(TreeNode aChild) 
    {
        	if (aChild == null){throw new IllegalArgumentException("child is null");}
        	int index = getIndex(aChild);
        	if (index == -1){throw new IllegalArgumentException("not a child");}
        	if (index > 0){return getChildAt(index - 1);}else{return null;}
    }
    public TreeNode getFirstChild() 
    {
    	if (getChildCount()==0)throw new NoSuchElementException("this not has no children");
    	return getChildAt(0);
    }
    public TreeNode getLastChild() 
    {
    	if (getChildCount()==0)throw new NoSuchElementException("this not has no children");
    	return getChildAt(getChildCount()-1);
    }
}
