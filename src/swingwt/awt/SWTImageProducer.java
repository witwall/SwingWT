package swingwt.awt;

import java.util.Vector;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.RGB;

import swingwt.awt.image.ColorModel;
import swingwt.awt.image.ImageConsumer;
import swingwt.awt.image.ImageProducer;

public class SWTImageProducer implements ImageProducer {	
	Image swtImage;
	Vector consumers = new Vector();
	
	public SWTImageProducer(Image swtImage) {
		this.swtImage = swtImage;
	}
		
	public void addConsumer(ImageConsumer ic) {
		if(consumers.contains(ic)) {
			return;
		}
		
		consumers.add(ic);
	}

	public boolean isConsumer(ImageConsumer ic) {
		return consumers.contains(ic);
	}

	public void removeConsumer(ImageConsumer ic) {
		consumers.remove(ic);
	}

	public void startProduction(ImageConsumer ic) {
		consumers.add(ic);
		ImageData imgData = swtImage.getImageData();
		int size = imgData.width*imgData.height;
		int [] pixels = new int [size];
		
		imgData.getPixels(0,0,size,pixels, 0);
		
		// This isn't the most efficient way to do this, but it
		// should work for any swt image with any palette
		for(int i=0; i<size; i++) {
			int pixel = pixels[i];
			RGB rgb = imgData.palette.getRGB(pixel);
			pixels[i] = rgb.red << 16 | rgb.green << 8 | rgb.blue;
			if(imgData.transparentPixel == pixel) {
				// do nothing this will give it an alpha value of 0
			} else if(imgData.alphaData != null) {
				pixels[i] = pixels[i] | (imgData.alphaData[i] & 0xFF) << 24; 
			} else {
				pixels[i] = pixels[i] | 0xFF000000;
			}
		}
				
		ColorModel cModel = ColorModel.getRGBdefault();
		
		for(int i=0; i<consumers.size(); i++) {
			ImageConsumer consumer = (ImageConsumer)consumers.get(i);
			consumer.setColorModel(cModel);
			consumer.setDimensions(imgData.width,imgData.height);
			consumer.setPixels(0,0,imgData.width,imgData.height,
					cModel,pixels,0,imgData.width);
		}
	}

	public void requestTopDownLeftRightResend(ImageConsumer ic) {
		
	}

}
