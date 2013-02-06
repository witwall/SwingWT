/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt;

import swingwtx.swing.JPopupMenu;

public class PopupMenu extends Menu {
    
    public PopupMenu() { swingPeer = new JPopupMenu(); }
    public PopupMenu(String label) { swingPeer = new JPopupMenu(label); }

    private JPopupMenu getSwingPeer() { return (JPopupMenu) swingPeer; }
    
    public void show(Component origin, int x, int y) {
        getSwingPeer().setLocation(x, y);
        getSwingPeer().show();
    }
}
