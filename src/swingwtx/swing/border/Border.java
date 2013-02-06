/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.border;

import swingwt.awt.Component;
import swingwt.awt.Graphics;
import swingwt.awt.Insets;

/**
 * An interface for widget border implementations.
 * 
 * @author Robin Rawson-Tetley
 */
public interface Border {
	
	/**
	 * Calls to give the border implementation a chance to paint itself on this component,
	 * using this Graphics. The coordinates indicate the position and size of the painted 
	 * border. 
	 *
	 * @param c
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height);
    
    /**
     * Gets the border insets (its paddings).
     *
     * @param c the component in which the border is applied.
     * @return
     */
    public Insets getBorderInsets(Component c);
    
    /**
     * True if the border is opaque, in which case it is responsible 
     * for painting its own background.
     *
     * @return
     */
    public boolean isBorderOpaque();
    
	/**
  	 * Sub-types can implement this to provide a better identifier during debug. 
  	 *
  	 * @return
  	 */
  	public String debugId();
}
