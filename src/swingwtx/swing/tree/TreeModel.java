/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.tree;

import swingwtx.swing.event.*;

public interface TreeModel
{

    public Object getRoot();
    public Object getChild(Object parent, int index);
    public int getChildCount(Object parent);
    public boolean isLeaf(Object node);
    public void valueForPathChanged(TreePath path, Object newValue);
    public int getIndexOfChild(Object parent, Object child);
    void addTreeModelListener(TreeModelListener l);
    void removeTreeModelListener(TreeModelListener l);

}

