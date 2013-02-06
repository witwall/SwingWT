/**
 * SwingWT
 *
 * This program is dual licenced under the terms of the Common Public 
 * License v1.0 and the GNU Lesser Public License (LGPL) version 2 or
 * later. Please choose whichever is most applicable to you.
 */
package swingwtx.swing.plaf;

import java.io.Serializable;

import swingwt.awt.Component;
import swingwt.awt.Graphics;
import swingwt.awt.Insets;
import swingwtx.swing.border.Border;

/**
 * TODO Comment!!
 * 
 * @author Thiago Tonelli Bartolomei
 */
public class BorderUIResource implements Border, UIResource, Serializable {

	/**
	 * TODO Comment!!
	 */
	private static final long serialVersionUID = 1L;

	public BorderUIResource(Border delegate) {
		 
	}
	
	/** 
	 * TODO Comment!!
	 *
	 * @param c
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void paintBorder(Component c, Graphics g, int x, int y, int width,int height) {
		// TODO Auto-generated method stub
	}

	/** 
	 * TODO Comment!!
	 *
	 * @param c
	 * @return
	 */
	public Insets getBorderInsets(Component c) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * TODO Comment!!
	 *
	 * @return
	 */
	public boolean isBorderOpaque() {
		// TODO Auto-generated method stub
		return false;
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
