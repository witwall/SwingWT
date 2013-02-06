/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.font;

public class GlyphJustificationInfo {

    public static final int PRIORITY_KASHIDA = 0;
    public static final int PRIORITY_WHITESPACE = 1;
    public static final int PRIORITY_INTERCHAR = 2;
    public static final int PRIORITY_NONE = 3;
    public float weight;
    public int growPriority;
    public boolean growAbsorb;
    public float growLeftLimit;
    public float growRightLimit;
    public int shrinkPriority;
    public boolean shrinkAbsorb;
    public float shrinkLeftLimit;
    public float shrinkRightLimit;
    
     public GlyphJustificationInfo(float weight,
                                  boolean growAbsorb, 
                                  int growPriority,
                                  float growLeftLimit,
                                  float growRightLimit,
                                  boolean shrinkAbsorb, 
                                  int shrinkPriority,
                                  float shrinkLeftLimit, 
                                  float shrinkRightLimit)
    {
        this.weight = weight;
        this.growAbsorb = growAbsorb;
        this.growPriority = growPriority;
        this.growLeftLimit = growLeftLimit;
        this.growRightLimit = growRightLimit;
        this.shrinkAbsorb = shrinkAbsorb;
        this.shrinkPriority = shrinkPriority;
        this.shrinkLeftLimit = shrinkLeftLimit;
        this.shrinkRightLimit = shrinkRightLimit;
    }
}
