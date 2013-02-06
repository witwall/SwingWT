/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.plaf;

import swingwt.awt.Font;
import swingwtx.swing.plaf.UIResource;

public class FontUIResource extends Font implements UIResource {
    public FontUIResource(String name, int style, int size) {
	super(name, style, size);
    }
    public FontUIResource(Font font) {
	super(font.getName(), font.getStyle(), font.getSize());
    }
}
