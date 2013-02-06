/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.border;

import swingwt.awt.*;

public class CompoundBorder extends AbstractBorder implements Border {

    private Border outside;
    private Border inside;

    public CompoundBorder() {}
    public CompoundBorder(Border outsideBorder, Border insideBorder) {
        outside = outsideBorder;
        inside = insideBorder;
    }

    private void addInsets(Insets lvalue, Insets rvalue) {
        lvalue.left += rvalue.left;
        lvalue.right += rvalue.right;
        lvalue.top += rvalue.top;
        lvalue.bottom += rvalue.bottom;
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.right = insets.top = insets.bottom = 0;
        if (outside != null) {
            addInsets(insets, outside.getBorderInsets(c));
        }
        if (inside != null) {
            addInsets(insets, inside.getBorderInsets(c));
        }
        return insets;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Rectangle r;
        if (outside != null) {
            outside.paintBorder(c, g, x, y, width, height);
            r = getInteriorRectangle(c, outside, x, y, width, height);
        } else {
            r = new Rectangle(x, y, width, height);
        }
        if (inside != null) {
            inside.paintBorder(c, g, r.x, r.y, r.width, r.height);
        }
    }

    /**
  	 * Sub-types can implement this to provide a better identifier during debug. 
  	 *
  	 * @return
  	 */
  	public String debugId() {
  		return outside.debugId() + "/" + inside.debugId();
  	}
}
