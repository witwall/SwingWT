/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

 
 */

package swingwtx.swing.tree;

import swingwtx.swing.event.TreeExpansionEvent;

public class ExpandVetoException extends Exception {
    protected TreeExpansionEvent event;
    public ExpandVetoException(TreeExpansionEvent event) {
        this(event, null);
    }
    public ExpandVetoException(TreeExpansionEvent event, String message) {
        super(message);
        this.event = event;
    }
}
