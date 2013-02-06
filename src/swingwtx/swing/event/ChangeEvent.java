/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net
 */
package swingwtx.swing.event;

import java.util.EventObject;

/**
 * ChangeEvent is used to notify interested parties that 
 * state has changed in the event source.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public class ChangeEvent extends EventObject {
	 public ChangeEvent(Object source) {
			 super(source);
	 }
}
