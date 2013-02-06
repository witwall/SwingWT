/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   
*/
package swingwt.awt.event;

import swingwt.awt.*;

public class WindowEvent extends AWTEvent {
    
    public static final int WINDOW_FIRST = 200;
    public static final int WINDOW_OPENED = 0 + WINDOW_FIRST;
    public static final int WINDOW_CLOSING = 1 + WINDOW_FIRST;
    public static final int WINDOW_CLOSED = 2 + WINDOW_FIRST;
    public static final int WINDOW_ICONIFIED = 3 + WINDOW_FIRST;
    public static final int WINDOW_DEICONIFIED = 4 + WINDOW_FIRST;
    public static final int WINDOW_ACTIVATED = 5 + WINDOW_FIRST;
    public static final int WINDOW_DEACTIVATED = 6 + WINDOW_FIRST;
    public static final int WINDOW_GAINED_FOCUS = 7 + WINDOW_FIRST;
    public static final int WINDOW_LOST_FOCUS = 8 + WINDOW_FIRST;
    public static final int WINDOW_STATE_CHANGED = 9 + WINDOW_FIRST;
    
    protected Window opposite;
    protected int oldState;
    protected int newState;

    public WindowEvent(Window source, int id, Window opposite,
		       int oldState, int newState) {
        super(source, id);
	this.opposite = opposite;
	this.oldState = oldState;
	this.newState = newState;
    }

    public WindowEvent(Window source, int id, Window opposite) {
        this(source, id, opposite, 0, 0);
    }
    public WindowEvent(Window source, int id) {
        this(source, id, null, 0, 0);
    }
    public Window getWindow() {
        return (source instanceof Window) ? (Window)source : null;
    }
    public Window getOppositeWindow() {
        return opposite;
    }
    public int getOldState() {
	return oldState;
    }
    public int getNewState() {
	return newState;
    }
    public String paramString() {
        String typeStr;
        switch(this.getID()) {
          case WINDOW_OPENED:
              typeStr = "WINDOW_OPENED";
              break;
          case WINDOW_CLOSING:
              typeStr = "WINDOW_CLOSING";
              break;
          case WINDOW_CLOSED:
              typeStr = "WINDOW_CLOSED";
              break;
          case WINDOW_ICONIFIED:
              typeStr = "WINDOW_ICONIFIED";
              break;
          case WINDOW_DEICONIFIED:
              typeStr = "WINDOW_DEICONIFIED";
              break;
          case WINDOW_ACTIVATED:
              typeStr = "WINDOW_ACTIVATED";
              break;
          case WINDOW_DEACTIVATED:
              typeStr = "WINDOW_DEACTIVATED";
              break;
	  case WINDOW_GAINED_FOCUS:
	      typeStr = "WINDOW_GAINED_FOCUS";
	      break;
	  case WINDOW_LOST_FOCUS:
	      typeStr = "WINDOW_LOST_FOCUS";
	      break;
	  case WINDOW_STATE_CHANGED:
	      typeStr = "WINDOW_STATE_CHANGED";
	      break;
          default:
              typeStr = "unknown type";
        }
	return typeStr + ",opposite=" + getOppositeWindow()
	    + ",oldState=" + oldState + ",newState=" + newState;
    }
}
