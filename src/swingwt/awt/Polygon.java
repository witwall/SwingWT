/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt;

import swingwt.awt.geom.AffineTransform;
import swingwt.awt.geom.PathIterator;
import swingwt.awt.geom.Point2D;
import swingwt.awt.geom.Rectangle2D;

/**
 * An implementation of a Polygon composed of line segments.
 * 
 * @author Thiago Tonelli Bartolomei
 */
public class Polygon implements Shape {

	/**
	 * Used internally to increment the xpoints/ypoints arrays when needed.
	 */
	protected final int INCREMENT = 10;
	
	/**
	 * The number of points in the polygon.
	 */
	public int npoints = 0;

	/**
	 * The array of x coordinates. Only the npoints first values are meaningful.
	 */
	public int xpoints[] = null;

	/**
	 * The array of y coordinates. Only the npoints first values are meaningful.
	 */
	public int ypoints[] = null;

	/**
	 * A rectangle around the polygon. Calculated on demand and kept synchronized.
	 */
	protected Rectangle bounds = null;

	/**
	 * Creates an empty polygon.
	 */
	public Polygon() {
	}

	/**
	 * Creates a polygon with n points, whose coordinates are taken from the xpoints
	 * and ypoints arrays.
	 *
	 * @param xpoints
	 * @param ypoints
	 * @param n
	 */
	public Polygon(int xpoints[], int ypoints[], int n) {
		if (n < 0)
			throw new NegativeArraySizeException("npoints < 0");
		
		this.xpoints = xpoints;
		this.ypoints = ypoints;
		this.npoints = n;
	}

	/**
	 * Resets the polygon, deleting all its points and freeing
	 * the points arrays and bounds.
	 */
	public void reset() {
		this.npoints = 0;
		this.xpoints = null;
		this.ypoints = null;
		this.bounds = null;
	}

	/**
	 * Invalidates the bounds rectangle.
	 */
	public void invalidate() {
		bounds = null;
	}

	/**
	 * Translates this polygon, moving it in the plane.
	 *
	 * @param deltaX
	 * @param deltaY
	 */
	public void translate(int deltaX, int deltaY) {
		for (int i = 0; i < npoints; i++) {
			xpoints[i] += deltaX;
			ypoints[i] += deltaY;
		}
		if (null != bounds) {
			bounds.translate(deltaX, deltaY);
		}
	}

	/**
	 * Adds a new point with these coordinates to the polygon.
	 *
	 * @param x
	 * @param y
	 */
	public void addPoint(int x, int y) {
		if (xpoints == null || npoints == xpoints.length) {
			// increase array size
			int[] newXpoints = new int[npoints + INCREMENT];
			int[] newYpoints = new int[npoints + INCREMENT];
			if (null != xpoints) {
				System.arraycopy(xpoints, 0, newXpoints, 0, npoints);
				System.arraycopy(ypoints, 0, newYpoints, 0, npoints);
			}
			xpoints = newXpoints;
			ypoints = newYpoints;
		}
		xpoints[npoints] = x;
		ypoints[npoints] = y;
		npoints++;
		recomputeBounds();
	}

	/**
	 * Gets a rectangle which contains the whole polygon.
	 * 
	 * This rectangle object will be kept synchronized if the polygon
	 * changes (by adding points or translating, for example).
	 *
	 * @return
	 */
	public Rectangle getBounds() {
		return getBoundingBox();
	}

	public Rectangle getBoundingBox() {
		if (null == bounds) {
			int[] b = computeBounds();
			bounds = new Rectangle(b[0], b[1], b[2], b[3]);
		}
		return bounds;
	}

	public boolean contains(Point p) {
		return contains(p.x, p.y);
	}

	public boolean contains(int x, int y) {
		return contains((double) x, (double) y);
	}

	public boolean inside(int x, int y) {
		return contains((double) x, (double) y);
	}

	public Rectangle2D getBounds2D() {
		return getBounds();
	}

	/**
	 * Check if a point with these coordinates is inside this polygon.
	 * 
	 * We use Randolph Franklin's point inclusion algorithm.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(double x, double y) {
		// Quick test for the bounding box
		if (! getBoundingBox().contains(x, y)) {
			return false;
		}
		
		// Use the point inclusion algorithm by Randolph Franklin.
		// See  http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
		/*
		 * Copyright (c) 1970-2003, Wm. Randolph Franklin
		 * 
		 * Permission is hereby granted, free of charge, to any person obtaining
		 * a copy of this software and associated documentation files (the
		 * "Software"), to deal in the Software without restriction, including
		 * without limitation the rights to use, copy, modify, merge, publish,
		 * distribute, sublicense, and/or sell copies of the Software, and to
		 * permit persons to whom the Software is furnished to do so, subject to
		 * the following conditions:
		 * 
		 * Redistributions of source code must retain the above copyright
		 * notice, this list of conditions and the following disclaimers.
		 * Redistributions in binary form must reproduce the above copyright
		 * notice in the documentation and/or other materials provided with the
		 * distribution. The name of W. Randolph Franklin may not be used to
		 * endorse or promote products derived from this Software without
		 * specific prior written permission.
		 * 
		 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
		 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
		 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
		 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
		 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
		 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
		 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
		 * SOFTWARE.
		 */
		
		// This version is a modification of the C version. I am not sure that it works
		// properly in all cases (it is very sensitive to the inequalities and types), 
		// but it was good enough for my tests.
		boolean ret = false;
		
		for (int i = 0, j = npoints - 1; i < npoints; j = i++)
			if ((ypoints[i] > y) != (ypoints[j] > y) &&
			    (x < ((xpoints[j] - xpoints[i]) * (y - ypoints[i])) / (ypoints[j] - ypoints[i]) + xpoints[i]))
				ret = ! ret;
		
		return ret;
	}

	public boolean contains(Point2D p) {
		return contains(p.getX(), p.getY());
	}

	public boolean intersects(double x, double y, double w, double h) {
		// FIXME: Implement
		return false;
	}

	public boolean intersects(Rectangle2D r) {
		return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	public boolean contains(double x, double y, double w, double h) {
		// FIXME: Implement
		return false;
	}

	public boolean contains(Rectangle2D r) {
		return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
	}

	public PathIterator getPathIterator(AffineTransform at) {
		return null;
	}

	public PathIterator getPathIterator(AffineTransform at, double flatness) {
		return null;
	}
	
	/**
	 * Reshapes the bounds rectangle if needed.
	 */
	protected void recomputeBounds() {
		if (null != bounds) {
			int[] b = computeBounds();
			bounds.reshape(b[0], b[1], b[2], b[3]);
		}
	}
	
	/**
	 * Computes a bounding box around this polygon.
	 *
	 * @return
	 */
	protected int[] computeBounds() {
		int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE;
		int maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
		
		for (int i = 0; i < npoints; i++) {
			if (xpoints[i] > maxx) maxx = xpoints[i];
			if (ypoints[i] > maxy) maxy = ypoints[i];
			if (xpoints[i] < minx) minx = xpoints[i];
			if (ypoints[i] < miny) miny = ypoints[i];
		}
		
		return new int[] {minx, miny, maxx - minx, maxy - miny};
	}
}

