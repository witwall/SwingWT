/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 

 
 */

package swingwtx.swing.event;

import swingwtx.swing.tree.ExpandVetoException;

public interface TreeWillExpandListener {
    public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException;
    public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException;
}
