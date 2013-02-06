/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.border;

import swingwt.awt.*;

public class LineBorder extends AbstractBorder implements Border {
    public static Border createBlackLineBorder() { return new LineBorder(Color.BLACK, 1); }
    public static Border createGrayLineBorder() { return new LineBorder(Color.GRAY, 1); }

    private Color color;
    private int thickness;
    private boolean roundedCorners;

    public LineBorder(Color color) {
        this(color, 1, false);
    }

    public LineBorder(Color color, int thickness) {
        this(color, thickness, false);
    }

    public LineBorder(Color color, int thickness, boolean roundedCorners) {
        this.color = color;
        this.thickness = thickness;
        this.roundedCorners = roundedCorners;
    }

    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = thickness;
        insets.right = thickness;
        insets.top = thickness;
        insets.bottom = thickness;
        return insets;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.setColor(color);
        g.drawRect(x, y, width-3, height-3);
    }
}
