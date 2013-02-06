/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */
package swingwtx.swing.event;

import java.util.EventListener;

/**
 * Defines an object which listens for ChangeEvents.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public interface ChangeListener extends EventListener {
	void stateChanged(ChangeEvent e);
}
