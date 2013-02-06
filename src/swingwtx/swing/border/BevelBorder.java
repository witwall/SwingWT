/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.border;

import swingwt.awt.*;

public class BevelBorder extends AbstractBorder implements Border {
    
    public static final int RAISED  = 0;
    public static final int LOWERED = 1;
    protected int bevelType = RAISED;
    
    protected BevelBorder() {}
    public BevelBorder(int bevelType) { this.bevelType = bevelType; }
    public BevelBorder(int bevelType, Color highlight, Color shadow) { this(bevelType); }
    public BevelBorder(int bevelType, Color highlightOuterColor, 
                       Color highlightInnerColor, Color shadowOuterColor, 
                       Color shadowInnerColor) { this(bevelType); }
}
