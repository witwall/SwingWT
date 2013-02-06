/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */
package swingwt.awt.dnd;

import swingwt.awt.Insets;

import org.eclipse.swt.graphics.Point;

/**
 * 
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public interface Autoscroll {
	
	/**
	 * Returns the insets of the container
	 */
	public Insets getAutoscrollInsets();
	
	/**
 	* notify the coponent to autoscroll
 	* @param cursorLocn the location of the cursor
 	*/
	public void autoscroll(Point cursorLocn);
}
