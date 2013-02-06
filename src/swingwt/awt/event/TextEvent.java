/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net

   
 */
package swingwt.awt.event;

import swingwt.awt.AWTEvent;

/**
 * 
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public class TextEvent extends AWTEvent {
	public static final int TEXT_FIRST 	= 900;
	public static final int TEXT_LAST 	= 900;
	public static final int TEXT_VALUE_CHANGED	= TEXT_FIRST;

	private static final long serialVersionUID = 6269902291250941179L;

	/**
	 *
	 * @param source the TextComponent object that originated the event
	 * @param id an integer that identifies the event type
	 */
	public TextEvent(Object source, int id) {
		super(source, id);
	}
}
