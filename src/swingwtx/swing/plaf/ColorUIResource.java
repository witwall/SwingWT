/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwtx.swing.plaf;

import swingwt.awt.Color;
import swingwtx.swing.plaf.UIResource;

public class ColorUIResource extends Color implements UIResource {
    public ColorUIResource(int r, int g, int b) {
	super(r, g, b);
    }

    public ColorUIResource(int rgb) {
	super(rgb);
    }

    public ColorUIResource(float r, float g, float b) {
	super(r, g, b);
    }

    public ColorUIResource(Color c) {
	super(c.getRed(), c.getGreen(), c.getBlue());
    }
}
