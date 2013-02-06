/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.awt.image;

import swingwt.awt.Transparency;

/**
 * @author Robin Rawson-Tetley
 */
public class ColorModel implements Transparency {
    protected int pixel_bits;
	
	public ColorModel() {
	// this provides the same default as before
	this(4);
	}	
    public ColorModel(int bits) {
    	pixel_bits = bits;    	
    }
    
    public static ColorModel getRGBdefault() {
	return new DirectColorModel(32);
    }
    public boolean hasAlpha() {
        return false;
    }
    public boolean isAlphaPremultiplied() {
        return false;
    }
    public int getTransferType() {
        return 0;
    }
    public int getPixelSize() {
	return pixel_bits;
    }
    public int getComponentSize(int componentIdx) {
        return 0;
    }
    public int[] getComponentSize() {
        return null;
    }
    public int getTransparency() {
        return OPAQUE;
    }
    public int getNumComponents() {
        return 0;
    }
    public int getNumColorComponents() {
        return 0;
    }
    public int getRed(int pixel) {
        return 0;
    }
    public int getGreen(int pixel) {
        return 0;
    }
    public int getBlue(int pixel) {
        return 0; 
    }
    public int getAlpha(int pixel) {
        return 0;
    }
    public int getRGB(int pixel) {
	return (getAlpha(pixel) << 24) | (getRed(pixel) << 16) | (getGreen(pixel) << 8) | (getBlue(pixel) << 0);
    }
    public int getRed(Object inData) {
        return 0;
    }
    public int getGreen(Object inData) {
        return 0;
    }
    public int getBlue(Object inData) {
        return 0;
    }
    public int getAlpha(Object inData) {
        return 0;
    }
    public int getRGB(Object inData) {
        return (getAlpha(inData) << 24) | (getRed(inData) << 16) | (getGreen(inData) << 8) | (getBlue(inData) << 0);
    }
    public Object getDataElements(int rgb, Object pixel) {
        throw new UnsupportedOperationException("");
    }
    public int[] getComponents(int pixel, int[] components, int offset) {
        throw new UnsupportedOperationException("");
    }
    public int[] getComponents(Object pixel, int[] components, int offset) {
        throw new UnsupportedOperationException("");
    }
    public int[] getUnnormalizedComponents(float[] normComponents, int normOffset, int[] components, int offset) {
        return null;
    }
    public float[] getNormalizedComponents(int[] components, int offset, float[] normComponents, int normOffset) {
        return null;
    }
    public int getDataElement(int[] components, int offset) {
        throw new UnsupportedOperationException("");
    }
    public Object getDataElements(int[] components, int offset, Object obj) {
        throw new UnsupportedOperationException("");
    }
    public int getDataElement(float[] normComponents, int normOffset) {
        return 0;
    }
    public Object getDataElements(float[] normComponents, int normOffset, Object obj) {
        return null;
    }
    public float[] getNormalizedComponents(Object pixel, float[] normComponents, int normOffset) {
         return null;
    }
    public ColorModel coerceData (WritableRaster raster, boolean isAlphaPremultiplied) { 
        throw new UnsupportedOperationException("");
    }
    public boolean isCompatibleRaster(Raster raster) {
        throw new UnsupportedOperationException("");
    }
    public WritableRaster createCompatibleWritableRaster(int w, int h) {
        return new WritableRaster(w, h);
    }
    public WritableRaster getAlphaRaster(WritableRaster raster) {
        return null;
    }
}
