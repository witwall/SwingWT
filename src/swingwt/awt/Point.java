/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */
package swingwt.awt;

import swingwt.awt.geom.Point2D;

/**
 * A point in the plane.
 * 
 * @author R. Rawson-Tetley
 * @author Thiago Tonelli Bartolomei
 */
public final class Point extends Point2D {

	/**
	 * The X axis coordinate.
	 */
	public int x;

	/**
	 * The Y axis coordinate.
	 */
	public int y;

	/**
	 * Creates a new point based on an SWT point.
	 *
	 * @param point
	 */
	Point(org.eclipse.swt.graphics.Point point) {
		x = point.x;
		y = point.y;
	}
	
	/**
	 * Creates a point in the origin (0,0).
	 */
	public Point() {
		this(0, 0);
	}

	/**
	 * Clones a point, creating a new one with the same values.
	 *
	 * @param p
	 */
	public Point(Point p) {
		this(p.x, p.y);
	}

	/**
	 * Creates a new point with these coordinates.
	 *
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Point getLocation() {
		return new Point(x, y);
	}

	public void setLocation(Point p) {
		setLocation(p.x, p.y);
	}

	public void setLocation(int x, int y) {
		move(x, y);
	}

	public void setLocation(double x, double y) {
		this.x = (int) Math.floor(x + 0.5);
		this.y = (int) Math.floor(y + 0.5);
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void translate(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point pt = (Point) obj;
			return (x == pt.x) && (y == pt.y);
		}
		return super.equals(obj);
	}

	/**
	 * Creates a new point with the same position as this one.
	 *
	 * @return
	 */
	public Object clone() {
		return new Point(x, y);
	}
	
	public String toString() {
		return getClass().getName() + "[x=" + x + ",y=" + y + "]";
	}
}
