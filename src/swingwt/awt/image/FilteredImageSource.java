/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwt.awt.image;

import java.util.HashMap;

/**
 * @author Dan
 *
 */
public class FilteredImageSource implements ImageProducer {

    ImageProducer imageProducer;
    ImageFilter imageFilter;
    HashMap consumers = new HashMap();
    
    public FilteredImageSource(ImageProducer imageProducer, ImageFilter imageFilter) {
    	this.imageProducer = imageProducer;
    	this.imageFilter = imageFilter;
    }

    /*
     * @see swingwt.awt.image.ImageProducer#addConsumer(swingwt.awt.image.ImageConsumer)
     */
    public void addConsumer(ImageConsumer imageConsumer) {
        if (consumers.containsKey(imageConsumer))
            return;

        ImageFilter filter = imageFilter.getFilterInstance(imageConsumer);
        consumers.put(imageConsumer, filter);
        imageProducer.addConsumer(filter);
	}

    /*
     * @see swingwt.awt.image.ImageProducer#isConsumer(swingwt.awt.image.ImageConsumer)
     */
    public boolean isConsumer(ImageConsumer imageConsumer) {
        ImageFilter filter = (ImageFilter) consumers.get(imageConsumer);
        if (filter != null) return imageProducer.isConsumer(filter);
        return false;
    }

    /*
     * @see swingwt.awt.image.ImageProducer#removeConsumer(swingwt.awt.image.ImageConsumer)
     */
    public void removeConsumer(ImageConsumer imageConsumer) {
        ImageFilter filter = (ImageFilter) consumers.remove(imageConsumer);
        if (filter != null) imageProducer.removeConsumer(filter);
	}

    /*
     * @see swingwt.awt.image.ImageProducer#startProduction(swingwt.awt.image.ImageConsumer)
     */
    public void startProduction(ImageConsumer imageConsumer) {
        ImageFilter filter;
        if ( !consumers.containsKey(imageConsumer) ) {
            filter = imageFilter.getFilterInstance(imageConsumer);
            consumers.put(imageConsumer, filter);
            imageProducer.addConsumer(filter);
        }
        else filter = (ImageFilter) consumers.get(imageConsumer);
        imageProducer.startProduction(filter);
	}

	/*
	 * @see swingwt.awt.image.ImageProducer#requestTopDownLeftRightResend(swingwt.awt.image.ImageConsumer)
	 */
	public void requestTopDownLeftRightResend(ImageConsumer imageConsumer) {
	    ImageFilter filter = (ImageFilter) consumers.get(imageConsumer);
	    imageProducer.requestTopDownLeftRightResend(filter);
	}
}
