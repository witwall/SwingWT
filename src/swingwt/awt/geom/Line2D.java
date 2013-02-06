/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.geom;

import swingwt.awt.*;

public abstract class Line2D implements Shape {
	GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 2);
	
    // Abstract methods...
	public abstract  Point2D getP1();	
    public abstract Point2D getP2();
    public abstract double getX1();
    public abstract double getY1();
    public abstract double getX2();
    public abstract double getY2();
    public abstract void setLine(double X1, double Y1, double X2, double Y2);
	
	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}
	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}
	public PathIterator getPathIterator(AffineTransform at) {
		path.reset();
		path.moveTo((float)getX1(), (float)getY1());
		path.lineTo((float)getX2(), (float)getY2());
		
		return path.getPathIterator(at);
	}
	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}    	

	public Object clone() {
    	try {
    		return super.clone();
    	} catch (CloneNotSupportedException e) {
    		// this should never happen
    		throw new Error();
    	}
    }
    
    public static class Float extends Line2D{
    	public float x1;
    	public float y1;
    	public float x2;
    	public float y2;
    	
    	public Float(){
    		this(0,0,0,0);
    	}
    	
    	public Float(float x1, float y1, float x2, float y2){
    		setLine(x1,y1,x2,y2);
    	}
    	
		public Point2D getP1() {
			return new Point2D.Float(x1,y1);
		}

		public Point2D getP2() {
			return new Point2D.Float(x2,y2);
		}

		public double getX1() {
			return x1;
		}

		public double getX2() {
			return y1;
		}

		public double getY1() {
			return x2;
		}

		public double getY2() {
			return y2;
		}

		public void setLine(double x1, double y1, double x2, double y2) {
			this.x1 = (float)x1;
			this.y1 = (float)y1;
			this.x2 = (float)x2;
			this.y2 = (float)y2;			
		}
    	
    }
    
    public static class Double extends Line2D{
    	public double x1;
    	public double y1;
    	public double x2;
    	public double y2;

    	public Double() {
    		this(0,0,0,0);
    	}    	
    	
    	public Double(Point2D p1, Point2D p2) {
    		this(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    	}
    	
    	public Double(double x1, double y1, double x2, double y2) {
    		setLine(x1,y1,x2,y2);
    	}
    	
    	public Point2D getP1() {
    		return new Point2D.Double(x1,y1);
		}

    	public Point2D getP2() {
    		return new Point2D.Double(x2,y2);
		}

    	public double getX1() {
    		return x1;
    	}
    	
    	public double getY1() {
    		return y1;
    	}

    	public double getX2() {
    		return x2;
    	}

    	public double getY2() {
    		return y2;
    	}
    	
		public void setLine(double x1, double y1, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
    }
}
