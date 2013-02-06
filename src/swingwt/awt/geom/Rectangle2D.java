/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.geom;


public abstract class Rectangle2D extends RectangularShape 
	implements Cloneable
{

    public static final int OUT_LEFT = 1;
    public static final int OUT_TOP = 2;
    public static final int OUT_RIGHT = 4;
    public static final int OUT_BOTTOM = 8;
    
    protected Rectangle2D() {
    }
    public abstract void setRect(double x, double y, double w, double h);
    public void setRect(Rectangle2D r) {
	setRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }
    public boolean intersectsLine(double x1, double y1, double x2, double y2) {
        // FIXME: Implement
	return false;
    }
    public boolean intersectsLine(Line2D l) {
	// FIXME: Implement
        return false;
    }
    public abstract int outcode(double x, double y);
    
    public int outcode(Point2D p) {
        // FIXME: Implement
	return 0;
    }
    
    public void setFrame(double x, double y, double w, double h) {
	setRect(x, y, w, h);
    }
    public Rectangle2D getBounds2D() {
	return (Rectangle2D) clone();
    }
    public boolean contains(double x, double y) {
        double x0 = getX();
        double y0 = getY();
        return (x >= x0 && y >= y0 && x < x0 + getWidth() && y < y0 + getHeight());
    }
    public boolean intersects(double x, double y, double w, double h) {
        double x0 = getX();
        double y0 = getY();
        
        return ( (w > 0 && h > 0 && getWidth() > 0 && getHeight() > 0) &&
                 (x < x0 + getWidth()) &&
                 (x + w > x0) &&
                 (y < y0 + getHeight()) &&
                 (y + h > y0) );
    }
    public boolean contains(double x, double y, double w, double h) {
        double x0 = getX();
        double y0 = getY();

        return ( (w > 0 && h > 0 && getWidth() > 0 && getHeight() > 0) &&
                 (x >= x0) &&
                 (x + w <= x0 + getWidth()) &&
                 (y >= y0) &&
                 (y + h <= y0 + getHeight()) );
    }
    public abstract Rectangle2D createIntersection(Rectangle2D r);
    
    public static void intersect(Rectangle2D src1,
				 Rectangle2D src2,
				 Rectangle2D dest) {

    }
    public abstract Rectangle2D createUnion(Rectangle2D r);
    public static void union(Rectangle2D src1,
			     Rectangle2D src2,
			     Rectangle2D dest) {
        double x = Math.min(src1.getX(), src2.getX());
        double y = Math.min(src1.getY(), src2.getY());
        dest.setRect(x,
                	 y,
                	 Math.max(src1.getMaxX(), src2.getMaxX()) - x,
                	 Math.max(src1.getMaxY(), src2.getMaxY()) - y);
    }
    public void add(double newx, double newy) {

    }
    public void add(Point2D pt) {
    }
    public void add(Rectangle2D r) {

    }
    public PathIterator getPathIterator(AffineTransform at) {
	// FIXME: Implement
        return null;
    }
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
	// FIXME: Implement
        return null;
    }
    public boolean equals(Object obj) {
	if (obj == this) {
	    return true;
	}
	if (obj instanceof Rectangle2D) {
	    Rectangle2D r2d = (Rectangle2D) obj;
	    return ((getX() == r2d.getX()) &&
		    (getY() == r2d.getY()) &&
		    (getWidth() == r2d.getWidth()) &&
		    (getHeight() == r2d.getHeight()));
	}
	return false;
    }
    
    public abstract double getHeight();
    public abstract double getWidth();
    public abstract double getX();
    public abstract double getY();
    public abstract boolean isEmpty();
    
    public static class Double extends Rectangle2D {
	public double x;
	public double y;
	public double width;
	public double height;
	public Double() {
	}
	public Double(double x, double y, double w, double h) {
	    setRect(x, y, w, h);
	}
	public double getX() {
	    return x;
	}
	public double getY() {
	    return y;
	}
	public double getWidth() {
	    return width;
	}
	public double getHeight() {
	    return height;
	}
	public boolean isEmpty() {
	    return (width <= 0.0) || (height <= 0.0);
	}
	public void setRect(double x, double y, double w, double h) {
	    this.x = x; this.y = y; this.width = w; this.height = h;
	}
	public void setRect(Rectangle2D r) {
	    this.x = r.getX();
	    this.y = r.getY();
	    this.width = r.getWidth();
	    this.height = r.getHeight();
	}
	public int outcode(double x, double y) {
            // FIXME: Implement
	    return 0;
	}
	public Rectangle2D getBounds2D() {
	    return new Double(x, y, width, height);
	}
	public Rectangle2D createIntersection(Rectangle2D r) {
		Double retval = new Double();
	    intersect(this, r, retval);
	    return retval;
	}
	public Rectangle2D createUnion(Rectangle2D r) {
	    Double retval = new Double();
	    union(this, r, retval);
	    return retval;
	}
	public String toString() {
	    return getClass().getName()
		+ "[x=" + x +
		",y=" + y +
		",w=" + width +
		",h=" + height + "]";
	}
    }
    
    public static class Float extends Rectangle2D implements Cloneable{
	public float x;
	public float y;
	public float width;
	public float height;
	public Float() {
	}
	public Float(float x, float y, float w, float h) {
	    setRect(x, y, w, h);
	}
	public double getX() {
	    return (double) x;
	}
	public double getY() {
	    return (double) y;
	}
	public double getWidth() {
	    return (double) width;
	}
	public double getHeight() {
	    return (double) height;
	}
	public boolean isEmpty() {
	    return (width <= 0.0f) || (height <= 0.0f);
	}
	public void setRect(float x, float y, float w, float h) {
	    this.x = x;
	    this.y = y;
	    this.width = w;
	    this.height = h;
	}
	public void setRect(double x, double y, double w, double h) {
	    this.x = (float) x;
	    this.y = (float) y;
	    this.width = (float) w;
	    this.height = (float) h;
	}
	public void setRect(Rectangle2D r) {
	    this.x = (float) r.getX();
	    this.y = (float) r.getY();
	    this.width = (float) r.getWidth();
	    this.height = (float) r.getHeight();
	}
	public int outcode(double x, double y) {
	    // FIXME: Implement
            return 0;
	}

	public Rectangle2D createIntersection(Rectangle2D r) {
		Float retval = new Float();
	    intersect(this, r, retval);
	    return retval;
	}
	public Rectangle2D createUnion(Rectangle2D r) {
	    Float retval = new Float();
	    union(this, r, retval);
	    return retval;
	}
	public String toString() {
	    return getClass().getName()
		+ "[x=" + x +
		",y=" + y +
		",w=" + width +
		",h=" + height + "]";
	}
    }

}
