/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.geom;

import org.eclipse.swt.graphics.Transform;

import swingwt.awt.*;
import swingwtx.swing.SwingWTUtils;

public class AffineTransform implements Cloneable {

    public static final int TYPE_IDENTITY = 0;
    public static final int TYPE_TRANSLATION = 1;
    public static final int TYPE_UNIFORM_SCALE = 2;
    public static final int TYPE_GENERAL_SCALE = 4;
    public static final int TYPE_MASK_SCALE = (TYPE_UNIFORM_SCALE |
					       TYPE_GENERAL_SCALE);
    public static final int TYPE_FLIP = 64;
    public static final int TYPE_QUADRANT_ROTATION = 8;
    public static final int TYPE_GENERAL_ROTATION = 16;
    public static final int TYPE_MASK_ROTATION = (TYPE_QUADRANT_ROTATION |
						  TYPE_GENERAL_ROTATION);

    public static final int TYPE_GENERAL_TRANSFORM = 32;
    
    Transform swtTransform;
 
    public static Transform createSWTTranform(float [] elements) {
    	return new Transform(SwingWTUtils.getDisplay(), elements);
    }
    
    // this array is used my many methods, this makes the methods 
    // not thread safe for now.
	float [] elements = new float [6];
    
    public AffineTransform(Transform swtTransform) {
    	float [] members = new float [6];
    	swtTransform.getElements(members);
    	this.swtTransform = new Transform(SwingWTUtils.getDisplay(), members);
    }
    
    public AffineTransform() {
    	this.swtTransform = new Transform(SwingWTUtils.getDisplay());    	
    }

    public AffineTransform(AffineTransform Tx) {
    	Tx.swtTransform.getElements(elements);
    	swtTransform = createSWTTranform(elements);
    }


    public AffineTransform(float m00, float m10,
			   float m01, float m11,
			   float m02, float m12) {
    	swtTransform = new Transform(SwingWTUtils.getDisplay(),
    			m00, m10, m01, m11, m02, m12);
    }

    public AffineTransform(float[] flatmatrix) {
    	swtTransform = new Transform(SwingWTUtils.getDisplay(), flatmatrix);
    }

    public AffineTransform(double m00, double m10,
			   double m01, double m11,
			   double m02, double m12) {
    	swtTransform = new Transform(SwingWTUtils.getDisplay(),
    			(float)m00, (float)m10, (float)m01, (float)m11, (float)m02, (float)m12);
    }

    public AffineTransform(double[] flatmatrix) {
    	swtTransform = new Transform(SwingWTUtils.getDisplay(),
    			(float)flatmatrix[0], (float)flatmatrix[1], 
    			(float)flatmatrix[2], (float)flatmatrix[3], 
    			(float)flatmatrix[4], (float)flatmatrix[5]);
    }

    public static AffineTransform getTranslateInstance(double tx, double ty) {
    	AffineTransform awtTransform = new AffineTransform();
    	awtTransform.translate(tx, ty);
    	return awtTransform;
    }

    public static AffineTransform getRotateInstance(double theta) {
    	AffineTransform awtTransform = new AffineTransform();
    	awtTransform.rotate(theta);
    	return awtTransform;
    }

    public static AffineTransform getRotateInstance(double theta,
						    double x, double y) {
    	AffineTransform awtTransform = new AffineTransform();
	    awtTransform.setToTranslation(x, y);	
	    awtTransform.rotate(theta);		
	    awtTransform.translate(-x, -y);	
	    return awtTransform;
    }

    public static AffineTransform getScaleInstance(double sx, double sy) {
    	AffineTransform awtTransform = new AffineTransform();
    	awtTransform.scale(sx, sy);
    	return awtTransform;
    }

    public static AffineTransform getShearInstance(double shx, double shy) {
    	AffineTransform awtTransform = new AffineTransform();
    	// shear isn't implemented yet
    	awtTransform.shear(shx, shy);
    	return awtTransform;
    }

    public Transform getSWTTransform(){
    	return swtTransform;
    }
    
    public int getType() {
	return 0;
    }
    
    public double getDeterminant() {
        return 0;
    }
    
    public void getMatrix(double[] flatmatrix) {
    	swtTransform.getElements(elements);
    	for(int i=0; i<6; i++){
    		flatmatrix[i] = elements[i];
    	}
    }
    
    public double getScaleX() {
    	swtTransform.getElements(elements);
    	return elements[0];
    }

    public double getScaleY() {
    	swtTransform.getElements(elements);
    	return elements[3];
    }
    
    public double getShearX() {
    	swtTransform.getElements(elements);
    	return elements[1];
    }
    
    public double getShearY() {
    	swtTransform.getElements(elements);
    	return elements[2];
    }
    
    public double getTranslateX() {
    	swtTransform.getElements(elements);
    	return elements[4];
    }

    public double getTranslateY() {
    	swtTransform.getElements(elements);
    	return elements[5];
    }

    public void translate(double tx, double ty) {
    	swtTransform.translate((float)tx, (float)ty);
    }

    public void rotate(double theta) {
    	swtTransform.rotate((float)theta);
    }
    
    
    public void rotate(double theta, double x, double y) {
    }
    
    public void scale(double sx, double sy) {
    	swtTransform.scale((float)sx, (float)sy);
    }

    public void shear(double shx, double shy) {
    }

    public void setToIdentity() {
    }

    public void setToTranslation(double tx, double ty) {
    }
    
    public void setToRotation(double theta) {
    }
    
    public void setToRotation(double theta, double x, double y) {
    }
    
    public void setToScale(double sx, double sy) {
    }

    public void setToShear(double shx, double shy) {
    }

    public void setTransform(AffineTransform Tx) {
    }

    public void setTransform(double m00, double m10,
			     double m01, double m11,
			     double m02, double m12) {
    }

    public void concatenate(AffineTransform Tx) {
    }
    
    public void preConcatenate(AffineTransform Tx) {
    }
    
    public AffineTransform createInverse()
	throws NoninvertibleTransformException
    {  
    	swtTransform.getElements(elements);  
    	AffineTransform awtTransform = new AffineTransform(elements);
    	awtTransform.swtTransform.invert();
        return awtTransform;
    }
    public Point2D transform(Point2D ptSrc, Point2D ptDst) {
    	float [] ptSrcArray = new float[2];
    	ptSrcArray[0] = (float)ptSrc.getX();
    	ptSrcArray[1] = (float)ptSrc.getY();
    	swtTransform.transform(ptSrcArray);
    	if(ptDst == null) {
    		return new Point2D.Float(ptSrcArray[0],ptSrcArray[1]);
    	} 
    	
    	ptDst.setLocation(ptSrcArray[0],ptSrcArray[1]);
    	return ptDst;
    }
    
    public void transform(Point2D[] ptSrc, int srcOff,
			  Point2D[] ptDst, int dstOff,
			  int numPts) {
    }

    public void transform(float[] srcPts, int srcOff,
			  float[] dstPts, int dstOff,
			  int numPts) {
    }

    public void transform(double[] srcPts, int srcOff,
			  double[] dstPts, int dstOff,
			  int numPts) {
	
    }
    
    public void transform(float[] srcPts, int srcOff,
			  double[] dstPts, int dstOff,
			  int numPts) {
    }
    public void transform(double[] srcPts, int srcOff,
			  float[] dstPts, int dstOff,
			  int numPts) {
	
    }
    public Point2D inverseTransform(Point2D ptSrc, Point2D ptDst)
	throws NoninvertibleTransformException
    {
        // FIXME: Implement
        return null;
    }
    public void inverseTransform(double[] srcPts, int srcOff,
                                 double[] dstPts, int dstOff,
                                 int numPts)
	throws NoninvertibleTransformException
    {

    }
    public Point2D deltaTransform(Point2D ptSrc, Point2D ptDst) {

        return null;
    }
    public void deltaTransform(double[] srcPts, int srcOff,
			       double[] dstPts, int dstOff,
			       int numPts) {
    }
    public Shape createTransformedShape(Shape pSrc) {
    	GeneralPath path = new GeneralPath();
    	PathIterator pIter = pSrc.getPathIterator(this);
    	path.append(pIter, false);
    	return path;
    }
    
    public boolean isIdentity() {
        return false;
    }
    
    protected void finalize() {
    	if(swtTransform != null) {
    		swtTransform.dispose();
    	}
    }
}
