/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 

 */
package swingwt.awt;

import swingwt.awt.geom.AffineTransform;
import swingwt.awt.image.*;

/**
 * Another great abstract class which
 * is implimented in SwingWT.
 * 
 * @see swingwt.awt.Graphics
 * @author Daniel Spiewak
 */
public class GraphicsConfiguration {

	private static BufferCapabilities defaultBufferCaps;
	private static ImageCapabilities defaultImageCaps;

	protected GraphicsConfiguration() {
		
	}

	public GraphicsDevice getDevice() {
		// FIXME this is a stub
		return new GraphicsDevice();
	}

	public BufferedImage createCompatibleImage(int width, int height) {
		return null;
	}

	public VolatileImage createCompatibleVolatileImage(int width, int height) {
		return null;
	}

	public VolatileImage createCompatibleVolatileImage(int width, int height, ImageCapabilities caps) throws AWTException {
		// REMIND : check caps
		return createCompatibleVolatileImage(width, height);
	}

	public BufferedImage createCompatibleImage(int width, int height, int transparency) {
		return null;
	}

	public ColorModel getColorModel() {
		return new ColorModel();
	}

	public ColorModel getColorModel(int transparency) {
		return null;
	}

	public AffineTransform getDefaultTransform() {
		return null;
	}

	public AffineTransform getNormalizingTransform() {
		return null;
	}

	public Rectangle getBounds() {
		return new Rectangle( 0, 0, Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height );
	}

	private static class DefaultBufferCapabilities extends BufferCapabilities {
		public DefaultBufferCapabilities(ImageCapabilities imageCaps) {
			super(imageCaps, imageCaps, null);
		}
	}

	public BufferCapabilities getBufferCapabilities() {
		if (defaultBufferCaps == null) {
			defaultBufferCaps =
				new DefaultBufferCapabilities(getImageCapabilities());
		}
		return defaultBufferCaps;
	}

	public ImageCapabilities getImageCapabilities() {
		if (defaultImageCaps == null) {
			defaultImageCaps = new ImageCapabilities(false);
		}
		return defaultImageCaps;
	}
}

/*
 *****************************************************
 * Copyright 2003 Completely Random Solutions *
 *                                												*
 * DISCLAMER:                                 					*
 * We are not responsible for any damage      		*
 * directly or indirectly caused by the usage 			*
 * of this or any other class in assosiation  			*
 * with this class.  Use at your own risk.   			*
 * This or any other class by CRS is not   			*
 * certified for use in life support systems, 			*
 * the Space Shuttle, in use or developement  		*
 * of nuclear reactors, weapons of mass       		*
 * destruction, or in interplanitary conflict.				*
 * (Unless otherwise specified)               				*
 *****************************************************
 */
