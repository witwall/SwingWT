/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import swingwt.awt.image.*;

public interface CompositeContext {
    public void dispose();
    public void compose(Raster src,
                        Raster dstIn,
			WritableRaster dstOut);

}
