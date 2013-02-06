/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.tree;

public interface MutableTreeNode extends TreeNode
{

    void insert(MutableTreeNode child, int index);
    void remove(int index);
    void remove(MutableTreeNode node);
    void setUserObject(Object object);
    void removeFromParent();
    void setParent(MutableTreeNode newParent);
    
}
