/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.event;

public interface PopupMenuListener extends java.util.EventListener
{
    public void popupMenuCanceled(PopupMenuEvent e);
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e);
    public void popupMenuWillBecomeVisible(PopupMenuEvent e);
}
