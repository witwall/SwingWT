/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */
package swingwt.awt.dnd;

import swingwt.awt.Point;
import swingwt.awt.datatransfer.Transferable;

/** @author Laurent Martell */
public class DropTargetDropEvent extends DropTargetEvent {
    public DropTargetDropEvent(DropTargetContext context,
                               Point cursorLocation,
                               int dropAction,
                               int srcActions) 
    {
        super(context);
        this.location = cursorLocation;
    }

    public DropTargetDropEvent(DropTargetContext context,
                               Point cursorLocation,
                               int dropAction,
                               int srcActions,
                               boolean isLocal) 
    {
        super(context);
        this.location = cursorLocation;
    }

    Point location;

    public Transferable getTransferable() {
        return getDropTargetContext().getTransferable();
    }

    public Point getLocation() {
        return location;
    }

    public void acceptDrop(int dropAction) {
    }

    public void rejectDrop() {
    }
}
