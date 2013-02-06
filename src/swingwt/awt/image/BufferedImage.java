/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt.image;

import java.util.Hashtable;
import swingwt.awt.*;

public class BufferedImage extends Image {
    
    public static final int TYPE_CUSTOM = 0;
    public static final int TYPE_INT_RGB = 1;
    public static final int TYPE_INT_ARGB = 2;
    public static final int TYPE_INT_ARGB_PRE = 3;
    public static final int TYPE_INT_BGR = 4;
    public static final int TYPE_3BYTE_BGR = 5;
    public static final int TYPE_4BYTE_ABGR = 6;
    public static final int TYPE_4BYTE_ABGR_PRE = 7;
    public static final int TYPE_USHORT_565_RGB = 8;
    public static final int TYPE_USHORT_555_RGB = 9;
    public static final int TYPE_BYTE_GRAY = 10;
    public static final int TYPE_USHORT_GRAY = 11;
    public static final int TYPE_BYTE_BINARY = 12;
    public static final int TYPE_BYTE_INDEXED = 13;

    public BufferedImage(org.eclipse.swt.graphics.Image image) {
        this.image = image;
        createGraphics();
    }

    public BufferedImage(int width, int height, int imagetype) {
        this(new org.eclipse.swt.graphics.Image(swingwtx.swing.SwingWTUtils.getDisplay(), width, height));
    }

    public BufferedImage(ColorModel model, WritableRaster raster, boolean isRasterPremultiplied, Hashtable properties) {
        this(raster.getWidth(), raster.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
    }

}
