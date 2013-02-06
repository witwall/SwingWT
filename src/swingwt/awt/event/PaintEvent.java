/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwt.awt.event;

import swingwt.awt.Component;
import swingwt.awt.Rectangle;

/**
 * @author Dan
 *
 */
public class PaintEvent extends ComponentEvent
{
    public static final int PAINT_FIRST 	= 800;
    public static final int PAINT_LAST		= 801;
    public static final int PAINT			= PAINT_FIRST;
    public static final int UPDATE			= PAINT_FIRST + 1;

    Rectangle updateRect;

    public PaintEvent(Component source, int id, Rectangle updateRect) {
        super(source, id);
        this.updateRect = updateRect;
    }
    public Rectangle getUpdateRect() { return updateRect; }
    public void setUpdateRect(Rectangle updateRect) { this.updateRect = updateRect; }
}
