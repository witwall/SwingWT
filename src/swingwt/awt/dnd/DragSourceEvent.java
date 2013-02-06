/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwt.awt.dnd;

import swingwt.awt.Point;
import java.util.EventObject;

/** @author Laurent Martell */
public class DragSourceEvent extends EventObject {
    public DragSourceEvent(DragSourceContext dsc) { 
        super(dsc); 
        hasLocation = false;
    }

    public DragSourceEvent(DragSourceContext dsc, int x, int y) { 
        super(dsc);
        hasLocation = true;
        this.x = x;
        this.y = y;
    }

    boolean hasLocation;
    int x;
    int y;

    public DragSourceContext getDragSourceContext() {
        return (DragSourceContext)getSource();
    }

    public Point getLocation() {
        return hasLocation ? new Point(x, y) : null;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
