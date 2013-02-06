package swingwt.awt;

import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;

import swingwt.awt.image.ColorModel;
import swingwt.awt.image.ImageConsumer;

/**
 * Used to create images from the pixel data created from
 * an ImageProducer
 * @author Robin Rawson-Tetley
 */
class SWTImageConsumer implements ImageConsumer {        	
    public ImageData imageData = null;
    public boolean imageComplete = false;
	
    ColorModel colorModel = null;
    
	public ImageData getImageData(){
		return imageData;		
	}
	
    public void imageComplete(int status) { imageComplete = true; }
    public void setColorModel(ColorModel model) {
    	colorModel = model;
    }        
    public void setDimensions(int width, int height) {
    	if(imageData != null) {
    		throw new IllegalStateException("already have image data, data might be lost");
    	}
    	imageData = new ImageData(width,height, 24, new PaletteData(0xFF0000,0xFF00,0xFF));
    	if(colorModel != null &&
    			colorModel.getTransparency() == Transparency.TRANSLUCENT) {
    		// allocate the byte buffer for the alphaChannel
    		imageData.alphaData = new byte[width*height];
    		imageData.transparentPixel = -1;
    	}
    }
    public void setHints(int hintflags) {}
    public void setProperties(java.util.Hashtable props) {}
    public void setPixels(int x, int y, int w, int h, ColorModel model, int[] pixels, int off, int scansize) {
    	if(imageData == null) {
    		imageData = new ImageData(x+w,y+h,model.getPixelSize(), new PaletteData(0xFF0000,0xFF00,0xFF));
    	} else if((x+w > imageData.width) || (y+h > imageData.height)) {
    		// the image is growing, and they didn't call setDimension first
    		throw new IllegalStateException("already have image data, data might be lost");    		
    	}
    	
    	imageData.setPixels(x,y,w*h,pixels,0);
    	if(model != null && 
    			model.getTransparency() == Transparency.TRANSLUCENT) {
    		int size = (x+w)*(y+h);
    		if(imageData.alphaData == null) {
    			imageData.alphaData = new byte[size];    			
    		}

    		for(int inRow=0; inRow<h; inRow++) {
    			int outRowIndex = (y + inRow)*imageData.width;
    			int inRowIndex = inRow * scansize + off;
    			for(int inCol=0; inCol<w; inCol++) {
    				imageData.alphaData[outRowIndex + inCol + x] =
    					(byte)model.getAlpha(pixels[inRowIndex + inCol]);
    			}
    		}
    		
    		imageData.transparentPixel = -1;
    	}
    }
    
    public void setPixels(int x, int y, int w, int h, ColorModel model, byte[] pixels, int off, int scansize) {
        // Load the pixel data into the image 

    	// FIXME this should use an approach like above.  
    	// It seems like this isn't doing the correct thing, 
    	// but the specification of this
    	// method isn't clear.  Is there only 1 byte per pixel, or can 
    	// there be multiple bytes.  
    	imageData = 
                new org.eclipse.swt.graphics.ImageData( w, h, 16, 
                    new org.eclipse.swt.graphics.PaletteData(255, 255, 255)
                    , scansize, pixels);
    }
}
