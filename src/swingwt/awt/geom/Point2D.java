/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
*/

package swingwt.awt.geom;

public abstract class Point2D implements Cloneable{

    // Abstract methods...
    public abstract void setLocation(double x, double y);
    public abstract double getX();
    public abstract double getY();
    
    public double distanceSq(double x, double y) { return distanceSq(getX(), getY(), x, y); }
    
    public static double distance(double x1, double y1, double x2, double y2) { return Math.sqrt(distanceSq(x1, y1, x2, y2));}
    public static double distanceSq(double x1, double y1, double x2, double y2) {
        double x = x1 - x2;
        double y = y1 - y2;
        return (x*x + y*y);
    }

    public double distance(double x, double y) { return distance(getX(), getY(), x, y); }
    public double distance(Point2D point) { return distance(point.getX(), point.getY()); }
    public double distanceSq(Point2D point) { return distanceSq(point.getX(), point.getY()); }
    public void setLocation(Point2D point) { setLocation(point.getX(), point.getY());}
    
    public Object clone() {
    	try {
    		return super.clone();
    	} catch (CloneNotSupportedException e) {
    		// this should never happen
    		throw new Error();
    	}
    }
    
    public static class Float extends Point2D{
    	public float x, y;

    	public Float() {}
    	public Float(float x, float y) {
    	    this.x = x;
    	    this.y = y;
    	}

    	public double getX() { return (double) x; }
    	public double getY() { return (double) y; }
    	
    	public void setLocation(double x, double y) { this.x = (float) x; this.y = (float) y; }
    	public void setLocation(float x, float y) { this.x = x; this.y = y; }
    }
    
    public static class Double extends Point2D{
    	public double x, y;

    	public Double() {}
    	public Double(double x, double y) {
    	    this.x = x;
    	    this.y = y;
    	}

    	public double getX() { return x; }
    	public double getY() { return y; }
    	
    	public void setLocation(double x, double y) { this.x = x; this.y = y; }
    	public void setLocation(float x, float y) { this.x = x; this.y = y; }
    }
    
}

