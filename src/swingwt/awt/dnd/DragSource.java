/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */

package swingwt.awt.dnd;

import swingwt.awt.GraphicsEnvironment;
import swingwt.awt.HeadlessException;
import swingwt.awt.dnd.DragGestureListener;
import swingwt.awt.Component;


/** @author Laurent Martell */
public class DragSource {
    public DragSource() {
    }

    private static final DragSource defaultDragSource =
        (GraphicsEnvironment.isHeadless()) ? null : new DragSource();

    public static DragSource getDefaultDragSource() {
        if (GraphicsEnvironment.isHeadless()) {
            throw new HeadlessException("");
        } else {
            return defaultDragSource;
        }
    }

    public DragGestureRecognizer createDefaultDragGestureRecognizer(
        Component c,
        int actions,
        DragGestureListener dgl) 
    {
        return null;
    }
}
