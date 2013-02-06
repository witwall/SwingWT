/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */
package swingwt.awt.dnd;

import swingwt.awt.Point;
import swingwt.awt.datatransfer.DataFlavor;

/** @author Laurent Martell */
public class DropTargetDragEvent extends DropTargetEvent {

    public DropTargetDragEvent(DropTargetContext context,
                               Point cursorLocation,
                               int dropAction,
                               int srcActions)
    {
        super(context);
    }

    public void acceptDrag(int dragOperation) {
    }

    public void rejectDrag() {
    }

    public DataFlavor[] getCurrentDataFlavors() {
        return new DataFlavor[0];
    }
}
