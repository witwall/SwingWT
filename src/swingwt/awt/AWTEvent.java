/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwt.awt;

public class AWTEvent extends java.util.EventObject implements java.io.Serializable {
    
    protected int eventID = 0;
    protected boolean consumed = false;
    
    public final static long ACTION_EVENT_MASK = 128L;
    public final static long ADJUSTMENT_EVENT_MASK = 256L;
    public final static long COMPONENT_EVENT_MASK = 1L;
    public final static long CONTAINER_EVENT_MASK = 2L;
    public final static long FOCUS_EVENT_MASK = 4L;
    public final static long HIERARCHY_BOUNDS_EVENT_MASK = 65536L;    
    public final static long HIERARCHY_EVENT_MASK = 32768L;
    public final static long INPUT_METHOD_EVENT_MASK = 2048L;
    public final static long INVOCATION_EVENT_MASK = 16384L;
    public final static long ITEM_EVENT_MASK = 512L;    
    public final static long KEY_EVENT_MASK = 8L;
    public final static long MOUSE_EVENT_MASK = 16L;
    public final static long MOUSE_MOTION_EVENT_MASK = 32L;
    public final static long MOUSE_WHEEL_EVENT_MASK = 131072L;
    public final static long PAINT_EVENT_MASK = 8192L;    
    public final static int RESERVED_ID_MAX = 1999;
    public final static long TEXT_EVENT_MASK = 1024L;
    public final static long WINDOW_EVENT_MASK = 64L;    
    public final static long WINDOW_FOCUS_EVENT_MASK = 524288L;
    public final static long WINDOW_STATE_EVENT_MASK = 262144L;    
    
    public AWTEvent(Object source) {
        super(source);
    }
    public AWTEvent(Object source, int id) {
        super(source);
        eventID = id;
    }
    
    public int getID() {
        return eventID;    
    }
    
    public void setSource(Object newSource) {
        this.source = newSource;    
    }
    
    public boolean isConsumed() {
        return consumed;
    }
    
    public void consume() {
        consumed = true;
    }
    
}
