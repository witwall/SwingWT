/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */
package swingwt.awt.dnd;

import swingwt.awt.datatransfer.Transferable;

/** @author Laurent Martell */
public class DropTargetContext {
    public DropTarget getDropTarget() {
        return null; // TODO
    }
    protected Transferable getTransferable() {
        return null; // TODO        
    }
    public void dropComplete(boolean success) {
    }
    
}
