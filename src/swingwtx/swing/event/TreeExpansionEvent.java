/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.event;

import swingwtx.swing.tree.*;

public class TreeExpansionEvent extends java.util.EventObject{
    protected TreePath path;
    public TreeExpansionEvent(Object source, TreePath path) {
	super(source);
	this.path = path;
    }
    public TreePath getPath() { return path; }
}
