/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.font;
    
public class LineMetrics {

    private int numChars = 0;
    private float ascent = 0;
    private float descent = 0;
    private float leading = 0;
    private float height = 0;
    private int baselineindex = 0;
    private float[] baselineoffsets = new float[0];
    private float strikethroughoffset = 0;
    private float strikethroughthickness = 1;
    private float underlineoffset = 1;
    private float underlinethickness = 1;
    
    public LineMetrics(int numChars, int height) { this.numChars = numChars; this.height = height; strikethroughoffset = height / 2; }
    
    public int getNumChars() { return numChars; }
    public float getAscent() { return ascent; }
    public float getDescent() { return descent; }
    public float getLeading() { return leading; }
    public float getHeight() { return height; }
    public int getBaselineIndex() { return baselineindex; }
    public float[] getBaselineOffsets() { return baselineoffsets; }
    public float getStrikethroughOffset() { return strikethroughoffset; }
    public float getStrikethroughThickness() { return strikethroughthickness; }
    public float getUnderlineOffset() { return underlineoffset; }
    public float getUnderlineThickness() { return underlinethickness; }
}
