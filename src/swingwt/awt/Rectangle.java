/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import swingwt.awt.geom.*;

public class Rectangle extends Rectangle2D implements Shape {

	public int x;

	public int y;

	public int width;

	public int height;

	/**
	 * Creates a new rectangle that represents a widget at this location and with this size.
	 *
	 * @param location
	 * @param size
	 */
	Rectangle(org.eclipse.swt.graphics.Point location, org.eclipse.swt.graphics.Point size) {
		x = location.x;
		y = location.y;
		width = size.x;
		height = size.y;
	}
	
	public Rectangle() {
		this(0, 0, 0, 0);
	}

	public Rectangle(Rectangle r) {
		this(r.x, r.y, r.width, r.height);
	}

	public Rectangle(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Rectangle(int width, int height) {
		this(0, 0, width, height);
	}

	public Rectangle(Point p, Dimension d) {
		this(p.x, p.y, d.width, d.height);
	}

	public Rectangle(Point p) {
		this(p.x, p.y, 0, 0);
	}

	public Rectangle(Dimension d) {
		this(0, 0, d.width, d.height);
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

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public Rectangle2D getBounds2D() {
		return new Rectangle(x, y, width, height);
	}

	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}

	public void setBounds(int x, int y, int width, int height) {
		reshape(x, y, width, height);
	}

	public void setRect(double x, double y, double width, double height) {
		int x0 = (int) Math.floor(x);
		int y0 = (int) Math.floor(y);
		int x1 = (int) Math.ceil(x + width);
		int y1 = (int) Math.ceil(y + height);
		setBounds(x0, y0, x1 - x0, y1 - y0);
	}

	public void reshape(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
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

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void translate(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public Dimension getSize() {
		return new Dimension(width, height);
	}

	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	public void setSize(int width, int height) {
		resize(width, height);
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	public boolean contains(int x, int y) {
		return inside(x, y);
	}

	public boolean contains(Rectangle r) {
		return contains(r.x, r.y, r.width, r.height);
	}

	public boolean contains(int X, int Y, int W, int H) {
		return super.contains(X, Y, W, H);
	}

	public boolean inside(int X, int Y) {
		return (width > 0 && height > 0) && (X >= x && X < x + width)
				&& (Y >= y && Y < y + height);
	}

	public boolean intersects(Rectangle r) {
		return super.intersects(r.x, r.y, r.width, r.height);
	}

	public Rectangle intersection(Rectangle r) {
		Rectangle retval = new Rectangle();
		intersect(this, r, retval);
		return retval;
	}

	public boolean isEmpty() {
		return (width <= 0) || (height <= 0);
	}

	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle r = (Rectangle) obj;
			return ((x == r.x) && (y == r.y) && (width == r.width) && (height == r.height));
		}
		return super.equals(obj);
	}

	public String toString() {
		return getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width
				+ ",height=" + height + "]";
	}

	public boolean contains(Rectangle2D r) {
		return contains((Rectangle) r);
	}

	public boolean contains(Point2D p) {
		return contains((Point) p);
	}

	public boolean contains(double x, double y) {
		return contains((int) x, (int) y);
	}

	public boolean contains(double x, double y, double w, double h) {
		return contains((int) x, (int) y, (int) w, (int) h);
	}

	public boolean intersects(Rectangle2D r) {
		return intersects((Rectangle) r);
	}

	public boolean intersects(double x, double y, double w, double h) {
		return intersects((int) x, (int) y, (int) w, (int) h);
	}

	public void grow(int h, int v) {
		x -= h;
		y -= v;
		width += 2 * h;
		height += 2 * v;
	}

	public Rectangle2D createIntersection(Rectangle2D r) {
		Rectangle2D retval = (Rectangle2D) clone();
		intersect(this, r, retval);
		return retval;
	}

	public Rectangle union(Rectangle r) {
		Rectangle retval = (Rectangle) clone();
		union(this, r, retval);
		return retval;
	}

	public Rectangle2D createUnion(Rectangle2D r) {
		Rectangle2D retval = (Rectangle2D) clone();
		union(this, r, retval);
		return retval;
	}

	public int outcode(double x, double y) {
		// FIXME: Implement
		return 0;
	}

	// DEBUG SUPPORT

	/**
	 * Sub-types can override this to provide a better identifier during debug.
	 * 
	 * @return
	 */
	public String debugId() {
		return "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
	}
}
