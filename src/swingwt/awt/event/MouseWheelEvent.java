/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

 */

package swingwt.awt.event;

import swingwt.awt.Component;

/**
 * MouseWheelEvent
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class MouseWheelEvent extends MouseEvent
{
    public static final int WHEEL_UNIT_SCROLL = 0;
    public static final int WHEEL_BLOCK_SCROLL = 1;

    int scrollType, scrollAmount, wheelRotation;

    public MouseWheelEvent( Component source, int id, long when, int modifiers,
                            int x, int y, int clickCount, boolean popupTrigger,
                            int scrollType, int scrollAmount, int wheelRotation )
    {
        super(source, id, when, modifiers, x, y, clickCount, popupTrigger);
        this.scrollType = scrollType;
        this.scrollAmount = scrollAmount;
        this.wheelRotation = wheelRotation;
    }
    public int getScrollType() { return scrollType; }
    public int getScrollAmount() { return scrollAmount; }
    public int getWheelRotation() { return wheelRotation; }
    public int getUnitsToScroll() { return scrollAmount * wheelRotation; }
}
