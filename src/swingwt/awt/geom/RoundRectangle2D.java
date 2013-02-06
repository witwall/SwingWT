package swingwt.awt.geom;

import swingwt.awt.Rectangle;
import swingwt.awt.Shape;

public abstract class RoundRectangle2D extends RectangularShape {

	public static class Double extends RoundRectangle2D {

		public double getArcHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getArcWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void setRoundRect(double x, double y, double w, double h, double arcWidth, double arcHeight) {
			// TODO Auto-generated method stub
			
		}

		public double getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getX() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getY() {
			// TODO Auto-generated method stub
			return 0;
		}

		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		public void setFrame(double x, double y, double w, double h) {
			// TODO Auto-generated method stub
			
		}
				
	}
	
	public static class Float extends RoundRectangle2D {

		public double getArcHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getArcWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		public void setRoundRect(double x, double y, double w, double h, double arcWidth, double arcHeight) {
			// TODO Auto-generated method stub
			
		}

		public double getHeight() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getWidth() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getX() {
			// TODO Auto-generated method stub
			return 0;
		}

		public double getY() {
			// TODO Auto-generated method stub
			return 0;
		}

		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return false;
		}

		public void setFrame(double x, double y, double w, double h) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public abstract  double 	getArcHeight();
	public abstract  double 	getArcWidth(); 
	public abstract  void 	setRoundRect(double x, double y, 
			double w, double h, double arcWidth, double arcHeight);

	public void setRoundRect(RoundRectangle2D rr){
		
	}
	
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
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
		// TODO Auto-generated method stub
		return null;
	}

	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		// TODO Auto-generated method stub
		return null;
	}

}
