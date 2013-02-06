/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import swingwt.awt.image.*;

public interface PaintContext {
    public void dispose();
    ColorModel getColorModel();
    Raster getRaster(int x,
		     int y,
		     int w,
		     int h);
}
