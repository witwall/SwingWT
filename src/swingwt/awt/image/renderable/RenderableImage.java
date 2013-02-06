package swingwt.awt.image.renderable;

import java.util.Vector;

import swingwt.awt.RenderingHints;
import swingwt.awt.image.RenderedImage;

public interface RenderableImage {
	public static final String 	HINTS_OBSERVED =	"HINTS_OBSERVED";
	
	RenderedImage 	createDefaultRendering();
	RenderedImage 	createRendering(RenderContext renderContext);
	RenderedImage 	createScaledRendering(int w, int h, RenderingHints hints);
	float 	getHeight();
	float 	getMinX();
	float 	getMinY();
	Object 	getProperty(String name);
	String[] 	getPropertyNames();
	Vector 	getSources();
	float 	getWidth();
	boolean 	isDynamic(); 
}
