/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

 
 */

package swingwtx.swing.event;

import java.util.EventListener;

public interface AncestorListener extends EventListener 
{
    public void ancestorAdded(AncestorEvent aEvent);
    public void ancestorRemoved(AncestorEvent aEvent);
    public void ancestorMoved(AncestorEvent aEvent);

}


