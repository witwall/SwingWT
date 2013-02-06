package swingwt.awt.image;

public class DirectColorModel extends ColorModel {
	public DirectColorModel(int bits){
		super(bits);
	}

    public int getRed(int pixel) {
        return (pixel & 0xFF0000) >> 16;
    }
    public int getGreen(int pixel) {
        return (pixel & 0xFF00) >> 8;
    }
    public int getBlue(int pixel) {
    	return (pixel & 0xFF);
    }
    public int getAlpha(int pixel) {
    	return (pixel & 0xFF000000) >> 24;
    }

    public int getTransparency(){
    	return TRANSLUCENT;
    }
}
