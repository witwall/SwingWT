/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

   

*/


package swingwt.awt;

import swingwt.awt.geom.Dimension2D;

public class Dimension extends Dimension2D {

	public int width;

	public int height;

	/**
	 * Creates a dimension from the corresponding SWT Point object.
	 *
	 * @param point
	 */
	Dimension(org.eclipse.swt.graphics.Point point) {
		width = point.x;
		height = point.y;
	}
	
	/**
	 * Creates a (0, 0) Dimension.
	 */
	public Dimension() {
		this(0, 0);
	}

	public Dimension(Dimension d) {
		this(d.width, d.height);
	}

	public Dimension(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

	public void setSize(double width, double height) {
		this.width = (int) Math.ceil(width);
		this.height = (int) Math.ceil(height);
	}

	public Dimension getSize() {
		return new Dimension(width, height);
	}

	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Dimension) {
			Dimension d = (Dimension) obj;
			return (width == d.width) && (height == d.height);
		}
		return false;
	}

	public Object clone() {
		return new Dimension(width, height);
	}
	
	public String toString() {
		return getClass().getName() + "[width=" + width + ",height=" + height + "]";
	}
	
	// DEBUG SUPPORT

	/**
	 * Sub-types can override this to provide a better identifier during debug.
	 * 
	 * @return
	 */
	public String debugId() {
		return "[width=" + width + ",height=" + height + "]";
	}
}
