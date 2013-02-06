/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.geom;

import org.eclipse.swt.graphics.Path;

import swingwt.awt.*;
import swingwtx.swing.SwingWTUtils;

public final class GeneralPath implements Shape, Cloneable {
	Path swtPath;
	
    public static final int WIND_EVEN_ODD = PathIterator.WIND_EVEN_ODD;
    public static final int WIND_NON_ZERO = PathIterator.WIND_NON_ZERO;

    public GeneralPath() {
    	swtPath = new Path(SwingWTUtils.getDisplay());
    }

    public GeneralPath(int rule) {
    	this();
    }

    public GeneralPath(int rule, int initialCapacity) {
    	this();
    }

    public GeneralPath(Shape s) {
    	this();
    }
 
    public synchronized void moveTo(float x, float y) {
    	swtPath.moveTo(x,y);
    }
    
    public synchronized void lineTo(float x, float y) {
    	swtPath.lineTo(x,y);
    }
    
    public synchronized void quadTo(float x1, float y1, float x2, float y2) {
    	swtPath.quadTo(x1,y1,x2,y2);
    }

    public synchronized void curveTo(float x1, float y1,
				     float x2, float y2,
				     float x3, float y3) {
    	swtPath.cubicTo(x1,y1,x2,y2,x3,y3);
    }

    public synchronized void closePath() {
    	swtPath.close();
    }

    public void append(Shape s, boolean connect) {
    }
    public void append(PathIterator pi, boolean connect) {
    }
    public synchronized int getWindingRule() {
        return 0;
    }
    public void setWindingRule(int rule) {
    }
    public synchronized void reset() {
    	swtPath.dispose();
    	swtPath = new Path(SwingWTUtils.getDisplay());
    }
    public void transform(AffineTransform at) {
    }
    public synchronized Shape createTransformedShape(AffineTransform at) {
	return null;
    }
    public Rectangle getBounds() {
    	float [] bounds = new float[4];
    	swtPath.getBounds(bounds);
    	return new Rectangle((int)bounds[0],(int)bounds[1],(int)bounds[2],(int)bounds[3]);
    }
    public synchronized Rectangle2D getBounds2D() {
    	float [] bounds = new float[4];
    	swtPath.getBounds(bounds);
    	return new Rectangle2D.Float(bounds[0],bounds[1],bounds[2],bounds[3]);
    }

    public boolean contains(double x, double y) {
    	// could use the path contains here but we need a GC for that    	
    	// swtPath.contains
    	return false;
    }
    public boolean contains(Point2D p) {
	// FIXME: Implement
        return false;
    }
    public boolean contains(double x, double y, double w, double h) {
        return false;
    }
    public boolean contains(Rectangle2D r) {
	return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    public boolean intersects(double x, double y, double w, double h) {
	return false;
    }
    public boolean intersects(Rectangle2D r) {
	return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    public PathIterator getPathIterator(AffineTransform at) {
    	return new SWTPathIterator(swtPath);
    }

    public PathIterator getPathIterator(AffineTransform at, double flatness) {
	return null;
    }
    public Object clone() {
	return null;
    }
}

