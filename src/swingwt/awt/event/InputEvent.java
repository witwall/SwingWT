/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.event;

import swingwt.awt.*;

import org.eclipse.swt.*;

public abstract class InputEvent extends ComponentEvent {

    public static final int SHIFT_MASK = 1 << 0;
    public static final int CTRL_MASK = 1 << 1;
    public static final int META_MASK = 1 << 2;
    public static final int ALT_MASK = 1 << 3;
    public static final int ALT_GRAPH_MASK = 1 << 5;
    public static final int BUTTON1_MASK = 1 << 4;
    public static final int BUTTON2_MASK = ALT_MASK;
    public static final int BUTTON3_MASK = META_MASK;
    public static final int SHIFT_DOWN_MASK = 1 << 6;
    public static final int CTRL_DOWN_MASK = 1 << 7;
    public static final int META_DOWN_MASK = 1 << 8;
    public static final int ALT_DOWN_MASK = 1 << 9;
    public static final int BUTTON1_DOWN_MASK = 1 << 10;
    public static final int BUTTON2_DOWN_MASK = 1 << 11;
    public static final int BUTTON3_DOWN_MASK = 1 << 12;
    public static final int ALT_GRAPH_DOWN_MASK = 1 << 13;

    private long when;
    int modifiers = 0;

    InputEvent(Component source, int id, long when, int modifiers) {
        super(source, id);
        this.when = when;
        this.modifiers = modifiers;
    }

    public InputEvent(Component source) { super(source); }
    public InputEvent(Component source, int id) { super(source, id); }
    
    public int getModifiers() { return modifiers; }
    public boolean isAltDown() { return ( modifiers & ALT_DOWN_MASK ) != 0; }
    public boolean isAltGraphDown() { return ( modifiers & ALT_GRAPH_DOWN_MASK ) != 0; }
    public boolean isShiftDown() { return ( modifiers & SHIFT_DOWN_MASK ) != 0; }
    public boolean isControlDown() { return ( modifiers & CTRL_DOWN_MASK ) != 0; }
    public boolean isMetaDown() { return ( modifiers & META_DOWN_MASK ) != 0; }
    
    
    public long getWhen() {
    	return when;
    }
    
    /** For given SWT key and mouse modifiers, translates to AWT */
    public static int translateSWTModifiers(int swtModifiers) {
        int awtModifiers = 0;
        if ((swtModifiers & SWT.CTRL) > 0)
            awtModifiers = awtModifiers | CTRL_MASK;
        if ((swtModifiers & SWT.SHIFT) > 0)
            awtModifiers = awtModifiers | SHIFT_MASK;
        if ((swtModifiers & SWT.ALT) > 0)
            awtModifiers = awtModifiers | ALT_MASK;
        if ((swtModifiers & SWT.BUTTON1) > 0)
            awtModifiers = awtModifiers | BUTTON1_DOWN_MASK;
        if ((swtModifiers & SWT.BUTTON2) > 0)
            awtModifiers = awtModifiers | BUTTON2_DOWN_MASK;
        if ((swtModifiers & SWT.BUTTON3) > 0)
            awtModifiers = awtModifiers | BUTTON3_DOWN_MASK;
        if ((swtModifiers & SWT.COMMAND) > 0)
            awtModifiers = awtModifiers | META_MASK;
        return awtModifiers;
    }
    
    /** For given AWT key and mouse modifiers, translates to SWT */
    public static int translateAWTModifiers(int awtModifiers) {
        int swtModifiers = 0;
        if ((awtModifiers & CTRL_MASK) > 0)
            swtModifiers = swtModifiers | SWT.CTRL;
        if ((awtModifiers & SHIFT_MASK) > 0)
            swtModifiers = swtModifiers | SWT.SHIFT;
        if ((awtModifiers & ALT_MASK) > 0)
            swtModifiers = swtModifiers | SWT.ALT;
        if ((awtModifiers & BUTTON1_DOWN_MASK) > 0)
            swtModifiers = swtModifiers | SWT.BUTTON1;
        if ((awtModifiers & BUTTON2_DOWN_MASK) > 0)
            swtModifiers = swtModifiers | SWT.BUTTON2;
        if ((awtModifiers & BUTTON3_DOWN_MASK) > 0)
            swtModifiers = swtModifiers | SWT.BUTTON3;
        if ((awtModifiers & META_MASK) > 0)
            swtModifiers = swtModifiers | SWT.COMMAND;
        return swtModifiers;
    }
    
}
