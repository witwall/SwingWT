/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net


*/
package swingwt.awt.image;

import swingwt.awt.Image;
import java.util.Hashtable;

/**
 * TODO: implement
 * @author Dan
 */
public class PixelGrabber implements ImageConsumer {

    private ImageProducer imageProducer;
    
    private int dstX, dstY, dstW, dstH;
    private int dstOff, dstScan;

    private ColorModel colorModel;
    private byte[] bytePixels;
    private int[] intPixels;

    private boolean grabbing;
    private int flags;

    private static final int GRABBEDBITS = (ImageObserver.FRAMEBITS
					    | ImageObserver.ALLBITS);
    private static final int DONEBITS = (GRABBEDBITS
					 | ImageObserver.ERROR);

    
    public PixelGrabber(Image img, int x, int y, int w, int h, boolean forceRGB) {
        imageProducer = img.getSource();
    	dstX = x;
    	dstY = y;
    	dstW = w;
    	dstH = h;
    	if (forceRGB) colorModel = ColorModel.getRGBdefault();
    }
    
    public PixelGrabber(Image img, int x, int y, int w, int h, int[] pix, int off, int scansize) { this(img.getSource(), x, y, w, h, pix, off, scansize); };    
    public PixelGrabber(ImageProducer ip, int x, int y, int w, int h, int[] pix, int off, int scansize) {
        imageProducer = ip;
    	dstX = x;
    	dstY = y;
    	dstW = w;
    	dstH = h;
    	dstOff = off;
    	dstScan = scansize;
    	intPixels = pix;
    	colorModel = ColorModel.getRGBdefault();
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#setDimensions(int, int)
     */
    public void setDimensions(int width, int height) {
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#setProperties(java.util.Hashtable)
     */
    public void setProperties(Hashtable props) {
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#setColorModel(swingwt.awt.image.ColorModel)
     */
    public void setColorModel(ColorModel model) {
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#setHints(int)
     */
    public void setHints(int hintflags) {
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#setPixels(int, int, int, int, swingwt.awt.image.ColorModel, byte[], int, int)
     */
    public void setPixels(int x, int y, int w, int h, ColorModel model, byte[] pixels, int off, int scansize) {
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#setPixels(int, int, int, int, swingwt.awt.image.ColorModel, int[], int, int)
     */
    public void setPixels(int x, int y, int w, int h, ColorModel model, int[] pixels, int off, int scansize)
    {
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#imageComplete(int)
     */
    public void imageComplete(int status)
    {
    }
    
    public void abortGrabbing() {}
    public ColorModel getColorModel() { return null; }
    public int getHeight() { return 0; }
    public Object getPixels() { return null; }
    public int getStatus() { return 0; }
    public int getWidth() { return 0; }
    public boolean grabPixels() { return false; }
    public boolean grabPixels(long ms) throws InterruptedException { return false; }
    public void startGrabbing() {}
    public int status() { return 0; }
}
