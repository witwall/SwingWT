package swingwt.awt.image;


public abstract class RGBImageFilter extends ImageFilter {

	public IndexColorModel filterIndexColorModel(IndexColorModel icm){
		throw new UnsupportedOperationException("");
	}
	
	public abstract int filterRGB(int x, int y, int rgb);
}
