/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   
 
 */

package swingwtx.swing.plaf;

import swingwt.awt.Rectangle;
import swingwtx.swing.JTree;
import swingwtx.swing.tree.TreePath;

public interface TreeUI {
    Rectangle getPathBounds(JTree tree, TreePath path);
}
