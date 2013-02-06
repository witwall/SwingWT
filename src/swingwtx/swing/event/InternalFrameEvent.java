/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing.event;

import swingwtx.swing.*;
import swingwt.awt.*;

public class InternalFrameEvent extends AWTEvent implements java.io.Serializable {
    
    public static final int INTERNAL_FRAME_OPENED = 0;
    public static final int INTERNAL_FRAME_CLOSING = 1;
    public static final int INTERNAL_FRAME_CLOSED = 2;
    public static final int INTERNAL_FRAME_ICONIFIED = 3;
    public static final int INTERNAL_FRAME_DEICONIFIED = 4;
    public static final int INTERNAL_FRAME_ACTIVATED = 5;
    public static final int INTERNAL_FRAME_DEACTIVATED = 6;
    public static final int INTERNAL_FRAME_GAINED_FOCUS = 7;
    public static final int INTERNAL_FRAME_LOST_FOCUS = 8;
    public static final int INTERNAL_FRAME_STATE_CHANGED = 9;
    
    public InternalFrameEvent(Object source, int id) {
        super(source, id);
    }
    
    public JInternalFrame getInternalFrame() {
        return (JInternalFrame) this.getSource();
    }
    
}
