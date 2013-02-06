/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwt.awt;

import org.eclipse.swt.*;

import swingwtx.swing.*;

public class Cursor {
    
    protected org.eclipse.swt.graphics.Cursor cursor = null;
    
    public static final int DEFAULT_CURSOR = 0;
    public static final int CROSSHAIR_CURSOR = 1;
    public static final int TEXT_CURSOR = 2;
    public static final int WAIT_CURSOR	= 3;
    public static final int SW_RESIZE_CURSOR = 4;
    public static final int SE_RESIZE_CURSOR = 5;
    public static final int NW_RESIZE_CURSOR = 6;
    public static final int NE_RESIZE_CURSOR = 7;
    public static final int N_RESIZE_CURSOR = 8;
    public static final int S_RESIZE_CURSOR = 9;
    public static final int W_RESIZE_CURSOR = 10;
    public static final int E_RESIZE_CURSOR = 11;
    public static final int HAND_CURSOR = 12;
    public static final int MOVE_CURSOR = 13;
    
    private static Cursor pre_default = null;
    private static Cursor pre_crosshair = null;
    private static Cursor pre_text = null;
    private static Cursor pre_wait = null;
    private static Cursor pre_sw = null;
    private static Cursor pre_se = null;
    private static Cursor pre_nw = null;
    private static Cursor pre_ne = null;
    private static Cursor pre_n = null;
    private static Cursor pre_s = null;
    private static Cursor pre_w = null;
    private static Cursor pre_e = null;
    private static Cursor pre_hand = null;
    private static Cursor pre_move = null;
    
    private static Object retval = null;
    private static org.eclipse.swt.graphics.Cursor createSWTCursor(final int type) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                retval = new org.eclipse.swt.graphics.Cursor(SwingWTUtils.getDisplay(), type);
            }
        });
        return (org.eclipse.swt.graphics.Cursor) retval;
    }
    
    private static void generatePredefinedCursors() {
        pre_default = new Cursor(createSWTCursor(SWT.CURSOR_ARROW));
        pre_crosshair = new Cursor(createSWTCursor(SWT.CURSOR_CROSS));
        pre_text = new Cursor(createSWTCursor(SWT.CURSOR_IBEAM));
        pre_wait = new Cursor(createSWTCursor(SWT.CURSOR_WAIT));
        pre_sw = new Cursor(createSWTCursor(SWT.CURSOR_SIZESW));
        pre_se = new Cursor(createSWTCursor(SWT.CURSOR_SIZESE));
        pre_nw = new Cursor(createSWTCursor(SWT.CURSOR_SIZENW));
        pre_ne = new Cursor(createSWTCursor(SWT.CURSOR_SIZENE));
        pre_n = new Cursor(createSWTCursor(SWT.CURSOR_SIZEN));
        pre_s = new Cursor(createSWTCursor(SWT.CURSOR_SIZES));
        pre_w = new Cursor(createSWTCursor(SWT.CURSOR_SIZEW));
        pre_e = new Cursor(createSWTCursor(SWT.CURSOR_SIZEE));
        pre_hand = new Cursor(createSWTCursor(SWT.CURSOR_HAND));
        pre_move = new Cursor(createSWTCursor(SWT.CURSOR_SIZEALL));
    }
    
    public Cursor(org.eclipse.swt.graphics.Cursor swtCursor) {
        this.cursor = swtCursor;
    }
    
    public Cursor(int type) {
        this(Cursor.getPredefinedCursor(type).getSWTCursor());
    }
    
    public static Cursor getPredefinedCursor(int type) {
        if (pre_default == null) generatePredefinedCursors();
        Cursor retCursor = null;
        switch(type) {
            case(DEFAULT_CURSOR): retCursor = pre_default; break;
            case(CROSSHAIR_CURSOR): retCursor = pre_crosshair; break;
            case(TEXT_CURSOR): retCursor = pre_text; break;
            case(WAIT_CURSOR): retCursor = pre_wait; break;
            case(SW_RESIZE_CURSOR): retCursor = pre_sw; break;
            case(SE_RESIZE_CURSOR): retCursor = pre_se; break;
            case(NW_RESIZE_CURSOR): retCursor = pre_nw; break;
            case(NE_RESIZE_CURSOR): retCursor = pre_ne; break;
            case(N_RESIZE_CURSOR): retCursor = pre_n; break;
            case(S_RESIZE_CURSOR): retCursor = pre_s; break;
            case(E_RESIZE_CURSOR): retCursor = pre_e; break;
            case(W_RESIZE_CURSOR): retCursor = pre_w; break;
            case(HAND_CURSOR): retCursor = pre_hand; break;
            // this was commented out why?
            case(MOVE_CURSOR): retCursor = pre_move; break; // -- Same as Resize All
        }
        return retCursor;
    }

    public static Cursor getDefaultCursor() {
        return getPredefinedCursor(DEFAULT_CURSOR);
    }
    
    public org.eclipse.swt.graphics.Cursor getSWTCursor() { return cursor; }
    
    /** FIXME: Genuine constants. Does anyone use these??? */
    public int getType() {
        return DEFAULT_CURSOR;
    }
    
    /** FIXME: Real names - again I ask, does anyone use this??? */
    public String getName() {
        return "Default Cursor";    
    }
    
    public void dispose() {
        cursor.dispose();    
    }
   
    protected void finalize() throws Throwable {
        dispose();
    }
}
