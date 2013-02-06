/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.custom.event;

/** Not a real Swing event, but part of my JClosableTabbedPane
 *  for use by MDI apps
 */
public interface TabCloseListener extends java.util.EventListener
{
    /** Fired when a tab is closed. Return a true value
     *  if you want the tab closed in your event handler */
    public boolean tabClosed(int index);
}
