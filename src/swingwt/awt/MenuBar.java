/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.util.Enumeration;
import java.util.HashMap;

import swingwtx.swing.JComponent;
import swingwtx.swing.JMenuBar;
import swingwtx.swing.JMenuItem;

public class MenuBar extends MenuComponent implements MenuContainer  {
    
    public MenuBar() { swingPeer = new JMenuBar(); }

    public JMenuBar getJMenuBar() { return getSwingPeer(); }
    private JMenuBar getSwingPeer() { return (JMenuBar) swingPeer; }

    // Maintains a cache of MenuItems mapped to the corresponding JMenuItems
    private HashMap menuItems = new HashMap();
    
    private void addItemToCache(Menu menu) {
        menuItems.put(menu.swingPeer, menu);
    }
    
    private void removeItemFromCache(Object menuComponent) {
        menuItems.remove(menuComponent);
    }
    
    private Menu getItemForPeer(JMenuItem jMenuItem) {
        return (Menu) menuItems.get(jMenuItem);
    }

    public Menu add(Menu m) { getSwingPeer().add(m.swingPeer); addItemToCache(m); return m; }
    public void addNotify() { getSwingPeer().addNotify(); }
    public int countMenus() { return getMenuCount(); }
    //public void deleteShortcut(MenuShortcut s) { getSwingPeer().removeShortcut(); }\
    public Menu getHelpMenu() { return null; }
    public Menu getMenu(int i) { JMenuItem jMenuItem = getSwingPeer().getItem(i); return getItemForPeer(jMenuItem); }
    public int getMenuCount() { return getSwingPeer().getItemCount(); }
    public MenuItem getShortcutMenuItem(MenuShortcut s) { return null; }
    public void remove(int index) { JMenuItem menuItem = getSwingPeer().getItem(index); getSwingPeer().remove(index); removeItemFromCache(menuItem); }
    public void remove(MenuComponent item) { JComponent comp = item.swingPeer; getSwingPeer().remove(comp); removeItemFromCache(comp); }
    public void removeNotify() { getSwingPeer().removeNotify(); }
    public void setHelpMenu(Menu m) {  }
    public Enumeration shortcuts() { return null; }
}
