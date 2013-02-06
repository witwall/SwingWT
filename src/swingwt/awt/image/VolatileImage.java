/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.awt.image;

import swingwt.awt.*;

/**
 * Actually implemented it useful enough to do something :)
 * Although it is abstract in AWT, there is no need to make it
 * abstract here, since we can now use a straight image and 
 * just override the bits of behaviour we want.
 *
 * @author Robin Rawson-Tetley
 */
public class VolatileImage extends BufferedImage {
    
    public static final int IMAGE_OK = 0;
    public static final int IMAGE_RESTORED = 1;
    public static final int IMAGE_INCOMPATIBLE = 2;
    
    public VolatileImage(int width, int height, int imagetype) { super(width, height, imagetype); }
    
    public BufferedImage getSnapshot() { return this; }

    public int getWidth() { return super.getWidth(null); }
    public int getHeight() { return super.getHeight(null); }
    //public ImageProducer getSource() { return null; }
    public void flush() { }
    public int validate(GraphicsConfiguration gc) { return IMAGE_OK; }
    public boolean contentsLost() { return false; }
    public ImageCapabilities getCapabilities() { return new ImageCapabilities(true); }

}
