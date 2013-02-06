/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net

 */
package swingwt.awt.event;

import java.util.EventListener;

/**
 * A listener for component events.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public interface ComponentListener extends EventListener {

	public void componentResized(ComponentEvent e);

	public void componentMoved(ComponentEvent e);

	public void componentShown(ComponentEvent e);

	public void componentHidden(ComponentEvent e);
}
