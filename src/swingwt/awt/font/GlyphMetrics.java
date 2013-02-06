/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.font;

import swingwt.awt.geom.*;

public final class GlyphMetrics {

    public static final byte STANDARD = 0;
    public static final byte LIGATURE = 1;
    public static final byte COMBINING = 2;
    public static final byte COMPONENT = 3;
    public static final byte WHITESPACE = 4;
    
    Rectangle2D bounds;
    
    public GlyphMetrics(float advance, Rectangle2D bounds, byte glyphType) {
    	this.bounds = bounds;

    }
    public GlyphMetrics(boolean horizontal, float advanceX, float advanceY,
			Rectangle2D bounds, byte glyphType) {
    	this.bounds = bounds;
	
    }
    public float getAdvance() {
        return 0;
    }
    public float getAdvanceX() {
        return 0;
    }
    public float getAdvanceY() {
        return 0;
    }
    public Rectangle2D getBounds2D() {
    	return bounds;
    }
    public float getLSB() {
        return 0;
    }
    public float getRSB() {
        return 0;
    }
    public int getType() {
        return 0;
    }
    public boolean isStandard() {
        return true;
    }
    public boolean isLigature() {
        return false;
    }
    public boolean isCombining() {
        return false;
    }
    public boolean isComponent() {
        return false;
    }
    public boolean isWhitespace() {
        return false;
    }
}


