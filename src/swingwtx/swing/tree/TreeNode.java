/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.tree;

import java.util.Enumeration;

public interface TreeNode
{

    TreeNode getChildAt(int childIndex);
    int getChildCount();
    TreeNode getParent();
    int getIndex(TreeNode node);
    boolean getAllowsChildren();
    boolean isLeaf();
    Enumeration children();
    
}
