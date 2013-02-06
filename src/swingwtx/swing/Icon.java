/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
*/
package swingwtx.swing;

import swingwt.awt.*;

/**
 * Now matches the Swing icon interface without
 * the need for the extraneous getImage() method.
 *
 * @author Bob Tetley
 */
public interface Icon {
    public int getIconHeight();
    public int getIconWidth();
    public void paintIcon(Component c, Graphics g, int x, int y);
}
