/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.event;


public interface TreeModelListener extends java.util.EventListener {

    void treeNodesChanged(TreeModelEvent e);
    void treeNodesInserted(TreeModelEvent e);
    void treeNodesRemoved(TreeModelEvent e);
    void treeStructureChanged(TreeModelEvent e);

}

