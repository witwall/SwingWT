/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.geom;

public interface PathIterator {
  
    public static final int WIND_EVEN_ODD	= 0;
    public static final int WIND_NON_ZERO	= 1;
    public static final int SEG_MOVETO		= 0;
    public static final int SEG_LINETO		= 1;
    public static final int SEG_QUADTO		= 2;
    public static final int SEG_CUBICTO		= 3;
    public static final int SEG_CLOSE		= 4;
    public int getWindingRule();
    public boolean isDone();
    public void next();
    public int currentSegment(float[] coords);
    public int currentSegment(double[] coords);
}
