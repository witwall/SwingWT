package swingwt.awt.geom;

import swingwt.awt.Rectangle;
import swingwt.awt.Shape;

public abstract class QuadCurve2D implements Cloneable, Shape {
	GeneralPath path = new GeneralPath(GeneralPath.WIND_EVEN_ODD, 2);

	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle2D getBounds2D() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(Point2D p) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersects(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(Rectangle2D r) {
		// TODO Auto-generated method stub
		return false;
	}

	public PathIterator getPathIterator(AffineTransform at) {
		path.reset();
		path.moveTo((float)getX1(),(float)getY1());
		path.quadTo((float)getCtrlX(), (float)getCtrlY(), (float)getX2(), (float)getY2());
		
		return path.getPathIterator(at);
	}

	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract Point2D getCtrlPt();
	
	public abstract double getCtrlX();

	public abstract double getCtrlY();

	public abstract Point2D getP1(); 
	
	public abstract Point2D getP2();
	
	public abstract double getX1();
	
	public abstract double getX2();

	public abstract double getY1();

	public abstract double getY2();

	public abstract void setCurve(double x1, double y1, double ctrlx, double ctrly, double x2, double y2);
	
	public static class Double extends QuadCurve2D {

		public double x1;
		public double y1;
		public double ctrlx;
		public double ctrly;
		public double x2;
		public double y2;

		public Double() {
			this(0,0,0,0,0,0);
		}
		
		public Double(double x1,
                double y1,
                double ctrlx,
                double ctrly,
                double x2,
                double y2){
			setCurve(x1,y1,ctrlx,ctrly,x2,y2);
        }
		
		public Point2D getCtrlPt() {
			return new Point2D.Double(ctrlx, ctrly);
		}

		public double getCtrlX() {
			return ctrlx;
		}

		public double getCtrlY() {
			return ctrly;
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

		public double getX2() {
			return x2;
		}

		public double getY1() {
			return y1;
		}

		public double getY2() {
			return y2;
		}

		public void setCurve(double x1, double y1, double ctrlx, double ctrly, double x2, double y2) {
			this.x1 = x1;
			this.y1 = y1;
			this.ctrlx = ctrlx;
			this.ctrly = ctrly;
			this.x2 = x2;
			this.y2 = y2;
		}
		
	}
	
	public static class Float extends QuadCurve2D {

		public Point2D getCtrlPt() {
			// TODO Auto-generated method stub
			return null;
		}

		public double getCtrlX() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getCtrlY() {
			// TODO Auto-generated method stub
			return 0;
		}

		public Point2D getP1() {
			// TODO Auto-generated method stub
			return null;
		}

		public Point2D getP2() {
			// TODO Auto-generated method stub
			return null;
		}

		public double getX1() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getX2() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getY1() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getY2() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void setCurve(double x1, double y1, double ctrlx, double ctrly, double x2, double y2) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
