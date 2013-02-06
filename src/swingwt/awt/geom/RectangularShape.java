/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt.geom;

import swingwt.awt.*;

public abstract class RectangularShape implements Shape, Cloneable {
	
    protected RectangularShape() { }

    public abstract double getX();
    public abstract double getY();
    public abstract double getWidth();
    public abstract double getHeight();
    public double getMinX() { return getX(); }
    public double getMinY() { return getY(); }
    public double getMaxX() { return getX() + getWidth(); }
    public double getMaxY() { return getY() + getHeight(); }
    public double getCenterX() { return getX() + getWidth() / 2.0; }
    public double getCenterY() { return getY() + getHeight() / 2.0; }
    public Rectangle2D getFrame() { return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight()); }
    public abstract boolean isEmpty();
    public abstract void setFrame(double x, double y, double w, double h);
    public void setFrame(Point2D loc, Dimension2D size) {  }
    public void setFrame(Rectangle2D r) {  }
    public void setFrameFromDiagonal(double x1, double y1,
				     double x2, double y2) {

    }
    public void setFrameFromDiagonal(Point2D p1, Point2D p2) {
    }
    public void setFrameFromCenter(double centerX, double centerY,
				   double cornerX, double cornerY) {

    }
    public void setFrameFromCenter(Point2D center, Point2D corner) {
    }
    public boolean contains(Point2D p) {
	// FIXME: Implement
	return false;
    }
    public boolean intersects(Rectangle2D r) {
	// FIXME: Implement
	return false;
    }
    public boolean contains(Rectangle2D r) {
	// FIXME: Implement
	return false;
    }
    public Rectangle getBounds() {
	double width = getWidth();
	double height = getHeight();
	if (width < 0 || height < 0) {
	    return new Rectangle();
	}
	double x = getX();
	double y = getY();
	double x1 = Math.floor(x);
	double y1 = Math.floor(y);
	double x2 = Math.ceil(x + width);
	double y2 = Math.ceil(y + height);
	return new Rectangle((int) x1, (int) y1, (int) (x2 - x1), (int) (y2 - y1));
    }
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
	// FIXME: Implement
        return null;
    }
    public Object clone() {
	try {
	    return super.clone();
	} 
        catch (CloneNotSupportedException e) {
            throw new InternalError();
	}
    }
    
    public abstract Rectangle2D getBounds2D();
    public abstract PathIterator getPathIterator(AffineTransform at);
    
}
