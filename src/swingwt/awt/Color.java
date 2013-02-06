/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwt.awt;

import swingwtx.swing.*;

public class Color implements Paint {
   
    protected org.eclipse.swt.graphics.Color swtColour = null;
    protected int alpha = 255;

    public static final Color black = new Color(0, 0, 0);
    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color blue = new Color(0, 0, 255);
    public static final Color BLUE = new Color(0, 0, 255);
    public static final Color cyan = new Color(0, 255, 255);
    public static final Color CYAN = new Color(0, 255, 255);
    public static final Color darkGray = new Color(64, 64, 64);
    public static final Color DARK_GRAY = new Color(64, 64, 64);
    public static final Color gray = new Color(128, 128, 128);
    public static final Color GRAY = new Color(128, 128, 128);
    public static final Color green = new Color(0, 255, 0);
    public static final Color GREEN = new Color(0, 255, 0);
    public static final Color lightGray = new Color(192, 192, 192);
    public static final Color LIGHT_GRAY = new Color(192, 192, 192);
    public static final Color magenta = new Color(255, 0, 255);
    public static final Color MAGENTA = new Color(255, 0, 255);
    public static final Color orange = new Color(255, 200, 0);
    public static final Color ORANGE = new Color(255, 200, 0);
    public static final Color pink = new Color(255, 175, 175);
    public static final Color PINK = new Color(255, 175, 175);
    public static final Color red = new Color(255, 0, 0);
    public static final Color RED = new Color(255, 0, 0);
    public static final Color white = new Color(255, 255, 255);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color yellow = new Color(255, 255, 0);
    public static final Color YELLOW = new Color(255, 255, 0);
    
    protected static final double colourScale = 0.7;
    
    public Color(org.eclipse.swt.graphics.Color swtColour) {
        this.swtColour = swtColour;
    }
    
    public Color(float r, float g, float b) {
        this((int) (255*r), (int) (255*g), (int) (255*b), 255);
    }
    
    public Color(float r, float g, float b, float a) {
        this((int) (255*r), (int) (255*g), (int) (255*b), (int) (255*a));
    }
    
    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }
    
    public Color(int r, int g, int b, int a) {
        this.swtColour = new org.eclipse.swt.graphics.Color(SwingWTUtils.getDisplay(), new org.eclipse.swt.graphics.RGB(r, g, b));
        this.alpha = a;
    }
    
    public Color(int rgb) {
        int[] comps = breakRGBComponents(rgb);
        this.swtColour = new org.eclipse.swt.graphics.Color(
            SwingWTUtils.getDisplay(), comps[0], comps[1], comps[2]);
    }
    
    public org.eclipse.swt.graphics.Color getSWTColor() {
        return swtColour;    
    }
    
    public void dispose() {
        swtColour.dispose();    
    }
    
    public int getRed() { return swtColour.getRed(); }
    public int getGreen() { return swtColour.getGreen(); }
    public int getBlue() { return swtColour.getBlue(); }
    public int getAlpha() { return alpha; }
    
    public int getRGB() {
        return ((getAlpha() & 0xFF) << 24) |
                ((getRed() & 0xFF) << 16) |
                ((getGreen() & 0xFF) << 8)  |
                ((getBlue() & 0xFF) << 0);
    }
    
    /**
     * Converts the big single integer containing all colour values
     * into the separate RGB bits and returns them in an array.
     */
    protected int[] breakRGBComponents(int value) {
        int[] cols = new int[3];
        cols[0] = 255;
        cols[1] = 255;
        cols[2] = 255;
        return cols;
    }

    
    public Color brighter() {
        int r = getRed();
        int g = getGreen();
        int b = getBlue();
        int i = (int)(1.0/(1.0-colourScale));
        if ( r == 0 && g == 0 && b == 0) {
           return new Color(i, i, i);
        }
        if ( r > 0 && r < i ) r = i;
        if ( g > 0 && g < i ) g = i;
        if ( b > 0 && b < i ) b = i;
        return new Color(Math.min((int)(r/colourScale), 255), Math.min((int)(g/colourScale), 255), Math.min((int)(b/colourScale), 255));
    }
    
    public Color darker() {
	return new Color(Math.max((int)(getRed() * colourScale), 0), Math.max((int)(getGreen() * colourScale), 0), Math.max((int)(getBlue() * colourScale), 0));
    }
    
    public String toString() {
        if (swtColour == null) return "null";
        return "Color: " + swtColour.getRed() + ", " + swtColour.getGreen() + ", " + swtColour.getBlue();
    }
    
    public PaintContext createContext(swingwt.awt.image.ColorModel cm, 
        Rectangle deviceBounds, 
        swingwt.awt.geom.Rectangle2D userBounds, 
        swingwt.awt.geom.AffineTransform xform, 
        RenderingHints hints) {
            return null;
    }
    
    public int getTransparency() { return (alpha == 255 ? OPAQUE : TRANSLUCENT); }
    
    public float[] getRGBColorComponents(float[] compArray) {
        float[] retval;
        
        if (compArray == null) retval = new float[3];
        else retval = compArray;

        retval[0] = ((float)getRed())/255f;
        retval[1] = ((float)getGreen())/255f;
        retval[2] = ((float)getBlue())/255f;

        return retval;
    }
    public static Color decode(String st) throws NumberFormatException 
    {
    	int ii = (Integer.decode(st)).intValue();
    	return new Color((ii >> 16) & 0xFF, (ii >> 8) & 0xFF, ii & 0xFF);
    }
    public static Color getColor(String cl){return getColor(cl, null);}
    public static Color getColor(String cl, Color c) 
    {
    	Integer i = Integer.getInteger(cl);
    	if (i == null) {return c;}
    	int ii = (Integer.decode(cl)).intValue();
    	return new Color((ii >> 16) & 0xFF, (ii >> 8) & 0xFF, ii & 0xFF);
    }

}
