/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   
*/


package swingwt.awt.event;

import org.eclipse.swt.SWT;

import swingwt.awt.*;

public class MouseEvent extends InputEvent implements java.io.Serializable {
    
    public static final int NOBUTTON = 0;
    public static final int BUTTON1 = 1;
    public static final int BUTTON2 = 2;
    public static final int BUTTON3 = 3;
    public static final int BUTTON1_MASK = BUTTON1;
    public static final int BUTTON2_MASK = BUTTON2;
    public static final int BUTTON3_MASK = BUTTON3;
    
    public static final int MOUSE_FIRST = 500;
    public static final int MOUSE_LAST = 507;
    public static final int MOUSE_CLICKED = MOUSE_FIRST;
    public static final int MOUSE_PRESSED = 1 + MOUSE_FIRST; 
    public static final int MOUSE_RELEASED = 2 + MOUSE_FIRST;
    public static final int MOUSE_MOVED = 3 + MOUSE_FIRST;
    public static final int MOUSE_ENTERED = 4 + MOUSE_FIRST; 
    public static final int MOUSE_EXITED = 5 + MOUSE_FIRST; 
    public static final int MOUSE_DRAGGED = 6 + MOUSE_FIRST; 
    public static final int MOUSE_WHEEL = 7 + MOUSE_FIRST; 
    
    public int clickCount = 1;
    
    private int button = 0;
    private int x = 0;
    private int y = 0;
    private boolean popupTrigger = false;

    public MouseEvent( Component source, int id, long when, int modifiers,
                int x, int y, int clickCount, boolean popupTrigger ) {
        this(source, id, when, modifiers, x, y, clickCount, popupTrigger, NOBUTTON);
    }

    public MouseEvent( Component source, int id, long when, int modifiers, int x, int y,
                int clickCount, boolean popupTrigger, int button) {
        super(source, id, when, modifiers);

        this.x = x;
        this.y = y;
        this.clickCount = clickCount;
        this.popupTrigger = popupTrigger;
        this.button = button;
    }

    public MouseEvent(Component source) {
        super(source);
    }
    
    /**
     * This method is not part of the awt api but it makes
     * it easier to work with these events
     * @param id
     */
    public void setID(int id){
    	eventID = id;
    }
    
    /**
     * This method is not part of the awt api but it makes
     * it easier to work with these events
     */
    public void setModifiers(int modifiers){
    	this.modifiers = modifiers;
    }
    
	/**
     * If this mouse event should trigger a popup I guess.
     * I never used this, but I've just set it to respond
     * to whether it's a right click for convenience
     */
    public boolean isPopupTrigger() {
        return button == 3;
    }

    /*
	 * scytacki: does this need to return 0 for some reason?
     * There are button flags that are modifiers. 
     * These should be set for compatability.
     *
     * commenting this out lets the super implementation handle
     * this.  
     *
    public int getModifiers() {
        // We don't have keyboard modifiers for mice (unlike AWT)
    	
        return 0;    
    }
    */
    
    /** Returns the number of mouse clicks associated with this event */
    public int getClickCount() {
        return clickCount;    
    }
    
    /** Getter for property button.
     * @return Value of property button.
     *
     */
    public int getButton() {
        return button;
    }
    
    /** Setter for property button.
     * @param button New value of property button.
     *
     */
    public void setButton(int button) {
        this.button = button;
    }
    
    /** Getter for property x.
     * @return Value of property x.
     *
     */
    public int getX() {
        return x;
    }
    
    /** Setter for property x.
     * @param x New value of property x.
     *
     */
    public void setX(int x) {
        this.x = x;
    }
    
    /** Getter for property y.
     * @return Value of property y.
     *
     */
    public int getY() {
        return y;
    }
    
    /** Setter for property y.
     * @param y New value of property y.
     *
     */
    public void setY(int y) {
        this.y = y;
    }

    public Point getPoint() {
        return new Point(x, y);
    }

    public void translatePoint(int x, int y) {
        this.x += x;
        this.y += y;
    }
    
    public int translateSWTButton(int swtButton) {
        int ret = NOBUTTON;
        if (( swtButton & org.eclipse.swt.SWT.BUTTON1 ) > 0) ret = ret | BUTTON1;
        if (( swtButton & org.eclipse.swt.SWT.BUTTON2 ) > 0) ret = ret | BUTTON2;
        if (( swtButton & org.eclipse.swt.SWT.BUTTON3 ) > 0) ret = ret | BUTTON3;
        return ret;
    }
    
    public static int getModifiersFromButtons(int button, int swtStateMask){
    	int mods = 0;
    	switch(button){
    	case 1:
    		mods += InputEvent.BUTTON1_DOWN_MASK;
    	case 2:
    		mods += InputEvent.BUTTON2_DOWN_MASK;
    	case 3:
    		mods += InputEvent.BUTTON3_DOWN_MASK;
    	}
    	if ((swtStateMask & SWT.CTRL) > 0) mods += InputEvent.CTRL_DOWN_MASK;
    	if ((swtStateMask & SWT.ALT) > 0) mods += InputEvent.ALT_DOWN_MASK;
    	if ((swtStateMask & SWT.SHIFT) > 0) mods += InputEvent.SHIFT_DOWN_MASK;
    	return mods;
    }
    
    public static int getModifiersFromSWTMask(int swtMask){
        int ret = 0;
        if (( swtMask & org.eclipse.swt.SWT.BUTTON1 ) > 0) ret = ret | BUTTON1_DOWN_MASK;
        if (( swtMask & org.eclipse.swt.SWT.BUTTON2 ) > 0) ret = ret | BUTTON2_DOWN_MASK;
        if (( swtMask & org.eclipse.swt.SWT.BUTTON3 ) > 0) ret = ret | BUTTON3_DOWN_MASK;
        return ret;
    	
    }
}
