/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwt.awt;

import swingwt.awt.image.ImageObserver;
import swingwt.awt.image.ImageProducer;

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.Display;

public class Image {
    
    public final static int SCALE_DEFAULT = 1; 
    public final static int SCALE_FAST = 2;
    public final static int SCALE_SMOOTH = 4;
    public final static int SCALE_REPLICATE = 8;
    public final static int SCALE_AREA_AVERAGING = 16;
    
    public org.eclipse.swt.graphics.Image image = null;
    protected swingwt.awt.Graphics2D gc = null;
    protected ImageProducer source;

    /**
     * A constructor that simply sets the wrappee.
     *
     * @param image
     */
    Image(org.eclipse.swt.graphics.Image image) {
    	this.image = image;
    }
    
    public Image(){
        //image = new org.eclipse.swt.graphics.Image();

    	// FIXME: we should actually be setting the image
    	// source in the caller.  So we can setup url sources
    	// and such.  But in the meantime we leave the source
    	// null, and then when it is requested we created from
    	// the hopefully available swt image.
    }
    
    public org.eclipse.swt.graphics.Image getSWTImage(){
    	return image;
    }
    
    /** 
     * Flush all resources associated with this image
     * 
     * According to the api docs this method should do 
     * nothing to offscreen images, so it really shouldn't
     * dispose of the image in that case.
     * 
     * However if this image was loaded from a url then 
     * it could dispose the image.  But then all the methods
     * that need the image would need to pull the image again.
     * 
     */
    public void flush() {
    	System.err.println("image.flush()");
        if (image != null)
	    image.dispose();
	if (gc != null)
	    gc.dispose();
	image = null;
	gc = null;
    }

    public int getHeight() { return getHeight(null); }
    public int getHeight(ImageObserver img) {
        if (image != null)
            return image.getBounds().height;
        else
            return 0;
    }

    public int getWidth() { return getWidth(null); }
    public int getWidth(ImageObserver img) {
        if (image != null)
            return image.getBounds().width;
        else
            return 0;
    }
    
    public Image getScaledInstance(int width, int height, int hints) {
        
        if (image == null) return null;
        if (width < 0 && height < 0) throw new IllegalArgumentException("You must supply width or height");
        
        // If width or height is negative, we need to maintain the
        // aspect ratio for the one that's missing
        if (height < 0) {
            double aspectRatio = ((double) image.getBounds().width) / ((double) image.getBounds().height);
            height = (int) ((double) width * (double) aspectRatio);
        }
        if (width < 0) {
            double aspectRatio = ((double) image.getBounds().width) / ((double) image.getBounds().height);
            width = (int) ((double) height * (double) aspectRatio);
        }
        
        // Create our new output image
        org.eclipse.swt.graphics.Image destImage = 
            new org.eclipse.swt.graphics.Image(Display.getDefault() , width, height);
        
        // Paint our image on it
        GC gc = new GC(destImage);
        gc.drawImage(image, 0, 0, image.getBounds().width, image.getBounds().height, 0, 0, width, height);
        
        // Construct our return image
        Image retImage = new Image();
        retImage.image = destImage;
        
        // Dispose of the GC
        gc.dispose();
        
        return retImage;
        
    }
    
    public Graphics getGraphics() { return createGraphics(); }
 
    /**
     * In awt/swing 
     * it is common pratice to create multiple graphics 
     * on the same image at the same time and then dispose them.
     * However in swt there can only be one gc active
     * on an image at a time.  
     * The reason awt/swing uses multiple graphics is so 
     * the state of the graphics is preserved 
     * (stroke, color ...), in swingwt this info is 
     * saved in the actual swt GC object.
	 * Soon the swingwt graphics2D object will correctly handle
	 * the create method, so that will take care of this problem  
     * 
     * @return
     */
    public Graphics2D createGraphics() { 
        if (gc != null) {
        	return (Graphics2D)gc.create();
        }
        // We have an image and no GC now - create it
        else if (image != null) {
            gc = new SWTGraphics2DRenderer( new GC(image), true);
            return gc;
        }
        else 
            return null;
    }
    
    public ImageProducer getSource() {
    	if(source != null) {
    		return source;
    	}
    	
    	if(image == null) {
    		return null;
    	}
    	
    	source = new SWTImageProducer(image);    	
    	return source;
    }
}
