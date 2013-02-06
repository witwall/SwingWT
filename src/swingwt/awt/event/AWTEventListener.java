/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.awt.event;

import java.util.EventListener;
import swingwt.awt.AWTEvent;
/**
 * A SwingWT  public interface   AWTEventListener
 * @author Nigu
 *
 */
public interface AWTEventListener extends EventListener 
{
    public void eventDispatched(AWTEvent event);
}
