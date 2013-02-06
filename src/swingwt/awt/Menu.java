/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.util.HashMap;

import swingwtx.swing.JComponent;
import swingwtx.swing.JMenu;
import swingwtx.swing.JMenuItem;

public class Menu extends MenuItem implements MenuContainer {
    
    public Menu() { swingPeer = new JMenu(); }
    public Menu(String text) { swingPeer = new JMenu(text); }
    
    private JMenu getSwingPeer() { return (JMenu) swingPeer; }

    // Maintains a cache of MenuItems mapped to the corresponding JMenuItems
    private HashMap menuItems = new HashMap();
    
    private void addItemToCache(MenuItem menuItem) {
        menuItems.put(menuItem.swingPeer, menuItem);
    }
    
    private void removeItemFromCache(Object menuComponent) {
        menuItems.remove(menuComponent);
    }
    
    private MenuItem getItemForPeer(JMenuItem jMenuItem) {
        return (MenuItem) menuItems.get(jMenuItem);
    }
    
    public MenuItem add(MenuItem mi) { getSwingPeer().add((JMenuItem) mi.swingPeer); addItemToCache(mi); return mi; }
    public void add(String label) { MenuItem menuItem = new MenuItem(label); getSwingPeer().add(menuItem.swingPeer); }
    public void addSeparator() { getSwingPeer().addSeparator(); }
    public int countItems() { return getItemCount(); }
    public MenuItem getItem(int index) { JMenuItem menuItem = getSwingPeer().getItem(index); return getItemForPeer(menuItem); }
    public int getItemCount() { return getSwingPeer().getItemCount(); }
    public void insert(MenuItem menuitem, int index) {  }
    public void insert(String label, int index) {  }
    public void insertSeparator(int index) {  }
    public boolean isTearOff() { return getSwingPeer().isTearOff(); }
    public void remove(int index) { JMenuItem menuItem = getSwingPeer().getItem(index); getSwingPeer().remove(index); removeItemFromCache(menuItem); }
    public void remove(MenuComponent item) { JComponent comp = item.swingPeer; getSwingPeer().remove(comp); removeItemFromCache(comp); }
    public void removeAll() { getSwingPeer().removeAll(); menuItems.clear(); }
}
