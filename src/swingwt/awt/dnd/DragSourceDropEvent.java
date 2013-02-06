/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */
package swingwt.awt.dnd;

/** @author Laurent Martell */
public class DragSourceDropEvent extends DragSourceEvent {

    public DragSourceDropEvent(DragSourceContext context) {
        super(context);
    }

    public DragSourceDropEvent(DragSourceContext context,
                               int action,
                               boolean success,
                               int x,
                               int y)
    {
        super(context,x,y);
    }

    public DragSourceDropEvent(DragSourceContext context,
                               int action,
                               boolean success) {
        super(context);
    }
}
