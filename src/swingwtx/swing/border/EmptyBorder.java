/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.border;

import swingwt.awt.*;

/**
 * An empty border, but one that may occupy some space.
 * 
 * @author Robin Rawson-Tetley
 * @author Thiago Tonelli Bartolomei
 */
public class EmptyBorder extends AbstractBorder implements Border {
	
	/**
	 * The paddings of the border.
	 */
	protected int left, right, top, bottom;

	/**
	 * Creates a new empty border with paddings determined by these insets.
	 *
	 * @param borderInsets
	 */
	public EmptyBorder(Insets borderInsets) {
		this(borderInsets.top, borderInsets.left, borderInsets.bottom, borderInsets.right);
	}

	/**
	 * Creates an empty border with these paddings.
	 *
	 * @param top
	 * @param left
	 * @param bottom
	 * @param right
	 */
	public EmptyBorder(int top, int left, int bottom, int right) {
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
	}

	/**
	 * Gets the insets of this border.
	 *
	 * @param c
	 * @return
	 */
	public Insets getBorderInsets(Component c) {
		return new Insets(top, left, bottom, right);
	}

	/**
	 * Gets the insets of this border.
	 *
	 * @return
	 */
	public Insets getBorderInsets() {
		return new Insets(top, left, bottom, right);
	}

	/**
	 * Fills the insets with data from this border.
	 *
	 * @param c
	 * @param insets
	 * @return
	 */
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.top = top;
		insets.left = left;
		insets.bottom = bottom;
		insets.right = right;
		return insets;
	}
	
	public String toString() {
		return "[top=" + top + ",left=" + left + ",bottom=" + bottom + ",right=" + right + "]";
	}
}
