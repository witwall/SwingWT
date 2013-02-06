/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file..

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt.event;

import swingwt.awt.Component;

public class FocusEvent extends ComponentEvent implements java.io.Serializable
{
    public static final int FOCUS_FIRST = 1004;
    public static final int FOCUS_LAST = 1005;
    public static final int FOCUS_GAINED = FOCUS_FIRST;
    public static final int FOCUS_LOST = 1 + FOCUS_FIRST;

    private boolean temporary;
    private Component opposite;

    public FocusEvent(Component source, int id, boolean temporary) { this(source, id, temporary, null); }
    public FocusEvent(Component source, int id) { this(source, id, false); }
    public FocusEvent(Component source, int id, boolean temporary, Component opposite)
    {
        super(source, id);
        this.temporary = temporary;
        this.opposite = opposite;
    }

    public boolean isTemporary() { return temporary; }
    public Component getOppositeComponent() { return opposite; }
}
