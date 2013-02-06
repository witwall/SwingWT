package swingwt.awt.image.renderable;

import swingwt.awt.RenderingHints;
import swingwt.awt.Shape;
import swingwt.awt.geom.AffineTransform;

public class RenderContext {
	public RenderContext(AffineTransform usr2dev){
		
	}
	
	public RenderContext(AffineTransform usr2dev,
            RenderingHints hints){
		
	}
	
	public RenderContext(AffineTransform usr2dev,
            Shape aoi){
		
	}
	
	public RenderContext(AffineTransform usr2dev,
            Shape aoi,
            RenderingHints hints){
		
	}
	
	public void 	concatenateTransform(AffineTransform modTransform){
		
	}
	public void 	concetenateTransform(AffineTransform modTransform){
		
	}
	
	public Shape 	getAreaOfInterest(){
		return null;
	}
	
	public RenderingHints 	getRenderingHints(){
		return null;
	}
	
	public AffineTransform 	getTransform(){
		return null;
	}
	
	public void 	preConcatenateTransform(AffineTransform modTransform){
		
	}

	public void 	preConcetenateTransform(AffineTransform modTransform){
		
	}

	public void 	setAreaOfInterest(Shape newAoi){
		
	}
	public void 	setRenderingHints(RenderingHints hints){
		
	}
	
	public void 	setTransform(AffineTransform newTransform){
		
	}
}
