/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;


import java.util.Arrays;
import swingwt.awt.font.*;
import swingwt.awt.geom.*;
import swingwtx.swing.SwingUtilities;

public class FontMetrics {
    
    protected Font font;
    
    /** An SWT graphics context used to get info about the font */
    protected org.eclipse.swt.graphics.GC gc = null;
    /** A drawable surface for the GC to be generated from */
    protected org.eclipse.swt.graphics.Image im = null;

    protected FontMetrics(Font font) { 
        this.font = font; 
        widths = new int[256];
        Arrays.fill(widths,-1);
    }
    
    private void checkGC() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (gc != null) return;
                im = new org.eclipse.swt.graphics.Image(swingwtx.swing.SwingWTUtils.getDisplay(), 100, 100); 
                gc = new org.eclipse.swt.graphics.GC(im);
                if (FontMetrics.this.font != null)
                    gc.setFont(FontMetrics.this.font.getSWTFont());
            }
        });
    }
    
    public Font getFont() { return font; }
    public int getLeading() { return 0; }
    public int getAscent() { 
        final int[] ret = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                checkGC(); 
                ret[0] = gc.stringExtent("I").y; 
            }
        });
        return ret[0];
    }
    public int getDescent() { return 0; }
    public int getHeight() { return getLeading() + getAscent() + getDescent(); }
    public int getMaxAscent() { return getAscent(); } 
    public int getMaxDescent() { return getDescent(); }
    public int getMaxDecent() { return getMaxDescent(); } 
    public int getMaxAdvance() { return -1; }
    public int charWidth(int ch) { return charWidth((char)ch); }
    public int charWidth(char ch) {
	if (ch < 256) {
	    return getWidths()[ch];
	}
	char data[] = {ch};
	return charsWidth(data, 0, 1);
    }
    public int stringWidth(final String str) {
        final int[] ret = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                checkGC();
                ret[0] = gc.stringExtent(str).x;
            }
        });
        return ret[0];
    }
    public int charsWidth(char data[], int off, int len) {
	return stringWidth(new String(data, off, len));
    }
    public int bytesWidth(byte data[], int off, int len) {
	return stringWidth(new String(data).substring(off, off+len));
    }
    
    int widths[];
    public int[] getWidths() {
        for (char ch = 0 ; ch < 256 ; ch++) {
            if (widths[ch]==-1)
                widths[ch] = stringWidth(""+ch);
        }
	return widths;
    }

    public boolean hasUniformLineMetrics() { return false; }

    public LineMetrics getLineMetrics( String str, Graphics context) {
        return new LineMetrics(str.length(), getHeight());
    }
    
    public LineMetrics getLineMetrics( String str,
                                            int beginIndex, int limit,
                                            Graphics context) {
        return new LineMetrics(limit - beginIndex, getHeight());
    }

    public LineMetrics getLineMetrics(char [] chars,
                                            int beginIndex, int limit,
                                            Graphics context) {
        return getLineMetrics(
                                new String(chars), beginIndex, limit, context);
    }
    public Rectangle2D getStringBounds(final String str, Graphics context) {
        final Rectangle[] ret = new Rectangle[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                checkGC();
                org.eclipse.swt.graphics.Point p = gc.stringExtent(str);
                ret[0] = new Rectangle(p.x, p.y);
            }
        });
        return ret[0];
    }
    public Rectangle2D getStringBounds( String str,
                                        int beginIndex, int limit,
                                        Graphics context) {
        return getStringBounds(str, context);
    }
    public Rectangle2D getStringBounds( char [] chars,
                                        int beginIndex, int limit,
                                        Graphics context) {
        return getStringBounds(new String(chars), context);
    }
    public Rectangle2D getMaxCharBounds(Graphics context) {
        return new Rectangle(0, 0);
    }
    public String toString() {
	return getClass().getName() +
	    "[font=" + getFont() +
	    "ascent=" + getAscent() +
	    ", descent=" + getDescent() +
	    ", height=" + getHeight() + "]";
    }

    protected void finalize() throws Throwable {
        if (im != null) im.dispose();
        if (gc != null) gc.dispose();
        super.finalize();
    }
    
}
