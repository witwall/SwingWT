/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.border;

import swingwt.awt.*;

public abstract class AbstractBorder implements Border {

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	}

	public Insets getBorderInsets(Component c) {
		Insets insets = new Insets();
		return getBorderInsets(c, insets);
	}

	public Insets getBorderInsets(Component c, Insets insets) {
		return insets;
	}

	public boolean isBorderOpaque() {
		return false;
	}

	public Rectangle getInteriorRectangle(Component c, int x, int y, int width, int height) {
		return getInteriorRectangle(c, this, x, y, width, height);
	}

	public static Rectangle getInteriorRectangle(Component c, Border b, int x, int y, int width, int height) {
		Insets insets;
		if (b != null)
			insets = b.getBorderInsets(c);
		else
			insets = new Insets(0, 0, 0, 0);
		return new Rectangle(x + insets.left, y + insets.top, width
				- insets.right - insets.left, height - insets.top
				- insets.bottom);
	}

	static boolean isLeftToRight(Component c) {
		return c.getComponentOrientation().isLeftToRight();
	}

    /**
  	 * Sub-types can implement this to provide a better identifier during debug. 
  	 *
  	 * @return
  	 */
  	public String debugId() {
  		return "";
  	}
}
