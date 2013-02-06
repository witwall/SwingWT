/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwt.awt.dnd;

import java.util.EventObject;

/** @author Laurent Martell */
public class DropTargetEvent extends EventObject {
    public DropTargetEvent(DropTargetContext context) {
        super(context.getDropTarget());
        this.context = context;
    }
    protected DropTargetContext context;
    public DropTargetContext getDropTargetContext() {
        return context;
    }
}
