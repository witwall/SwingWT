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
 * TODO: implement
 * @author Dan
 */
public class MemoryImageSource implements ImageProducer
{
    private int w, h, off, scan;
    private Hashtable props = null;
    private ColorModel cm = null;
    private Object pix;
    
    
	public MemoryImageSource(int w, int h, int[] pix, int off, int scan) { this(w, h, ColorModel.getRGBdefault(), pix, off, scan, null); }
	public MemoryImageSource(int w, int h, int[] pix, int off, int scan, Hashtable props) { this(w, h, ColorModel.getRGBdefault(), pix, off, scan, props); }
	
    public MemoryImageSource(int w, int h, ColorModel cm, byte[] pix, int off, int scan) { this(w, h, cm, pix, off, scan, null); }
	public MemoryImageSource(int w, int h, ColorModel cm, byte[] pix, int off, int scan, Hashtable props) { init(w, h, cm, pix, off, scan, props); }
	public MemoryImageSource(int w, int h, ColorModel cm, int[] pix, int off, int scan) { this(w, h, cm, pix, off, scan, null); }
	public MemoryImageSource(int w, int h, ColorModel cm, int[] pix, int off, int scan, Hashtable props) { init(w, h, cm, pix, off, scan, props); }

	private void init(int w, int h, ColorModel cm, Object pix, int off, int scan, Hashtable props) {
	    this.w = w;
	    this.h = h;
	    this.cm = cm;
	    this.pix = pix;
	    this.off = off;
	    this.scan = scan;
	    this.props = props == null ? new Hashtable() : props;
	}
	
    /*
     * @see swingwt.awt.image.ImageProducer#addConsumer(swingwt.awt.image.ImageConsumer)
     */
    public void addConsumer(ImageConsumer ic) {
    }

    /*
     * @see swingwt.awt.image.ImageProducer#isConsumer(swingwt.awt.image.ImageConsumer)
     */
    public boolean isConsumer(ImageConsumer ic) {
        return false;
    }

    /*
     * @see swingwt.awt.image.ImageProducer#removeConsumer(swingwt.awt.image.ImageConsumer)
     */
    public void removeConsumer(ImageConsumer ic) {
    }

    /*
     * @see swingwt.awt.image.ImageProducer#startProduction(swingwt.awt.image.ImageConsumer)
     */
    public void startProduction(ImageConsumer ic) {
    }

    /*
     * @see swingwt.awt.image.ImageProducer#requestTopDownLeftRightResend(swingwt.awt.image.ImageConsumer)
     */
    public void requestTopDownLeftRightResend(ImageConsumer ic) {
    }
    
	public void newPixels() {}
	public void newPixels(byte[] newpix, ColorModel newmodel, int offset, int scansize) {}
	public void newPixels(int[] newpix, ColorModel newmodel, int offset, int scansize) {}
	public void newPixels(int x, int y, int w, int h) {}
	public void newPixels(int x, int y, int w, int h, boolean framenotify) {}
	public void setAnimated(boolean animated) {}
	public void setFullBufferUpdates(boolean fullbuffers) {}
}
