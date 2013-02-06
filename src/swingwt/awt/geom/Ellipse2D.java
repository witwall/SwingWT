package swingwt.awt.geom;

import swingwt.awt.Shape;

public abstract class Ellipse2D extends RectangularShape implements Shape {
	
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFrame(double x, double y, double w, double h) {
		// TODO Auto-generated method stub

	}

	public Rectangle2D getBounds2D() {
		return new Rectangle2D.Double(getX(), getY(), getWidth(), getHeight());
	}

	public PathIterator getPathIterator(AffineTransform at) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean contains(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean intersects(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean contains(double x, double y, double w, double h) {
		// TODO Auto-generated method stub
		return false;
	}

	public static class Double extends Ellipse2D {
		public double x;
		public double y;
		public double width;
		public double height;
		
		public double getHeight() {
			return height;
		}
		
		public double getWidth() {
			return width;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}
		
	}

	public static class Float extends Ellipse2D {
		public float x;
		public float y;
		public float width;
		public float height;
		
		public Float(float x, float y, float width, float height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public double getHeight() {
			return height;
		}
		
		public double getWidth() {
			return width;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}
		
	}
}
