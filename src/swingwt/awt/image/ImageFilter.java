/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net


*/
package swingwt.awt.image;

import java.util.Hashtable;

/**
 * @author Dan
 *
 */
public class ImageFilter implements ImageConsumer {

    public ImageFilter getFilterInstance(ImageConsumer imageConsumer) {
        return null;
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
    public void setPixels(int x, int y, int w, int h, ColorModel model, int[] pixels, int off, int scansize) {
    }

    /*
     * @see swingwt.awt.image.ImageConsumer#imageComplete(int)
     */
    public void imageComplete(int status) {
    }
}
