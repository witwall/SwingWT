/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   
 
 */


package swingwtx.swing;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import swingwt.awt.Component;
import swingwtx.swing.event.*;


import java.util.*;

public class JMenu extends JMenuItem implements MenuElement {
    
    /** Menu container for this item if it has one */
    private Menu container = null;
    /** The shell this menu is living on, or null if
     *  it is a popup menu */
    private Shell shell = null;
    
    /** Menu Listener objects */
    protected Vector menuListeners = new Vector();
    
    private int iRetval;
    private Object retval;
    
    public JMenu() {}
    public JMenu(Action a) { setAction(a); }
    public JMenu(String text) { this(text, false); }
    public JMenu(String text, boolean isTearOff) { pText = text; }

    protected void registerSWTMenuComponent(final JSWTMenuComponent c) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer)) {
                    try {
                        c.setSwingWTParent(container, shell);
                        c.assertCachedProperties();
                        c.registerEvents();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });    	
    }
    public void addMenuListener(MenuListener l) {
        menuListeners.add(l);    
    }
    
    public void removeMenuListener(MenuListener l) {
        menuListeners.remove(l);    
    }
    /** 
     *  Sends menu events to listeners
     */
    public void processMenuEvent(int id) {
    	
    	
        MenuEvent me = new MenuEvent(this);
        for (int i = 0; i < menuListeners.size(); i++) {
            MenuListener ml = (MenuListener) menuListeners.get(i);
            switch (id) {
                case SELECTED: ml.menuSelected(me); break;
                case DESELECTED: ml.menuDeselected(me); break;
                case CANCELED: ml.menuCanceled(me); break;
            }
        }
        //processItemEvent();
        
    }
    
    
    /** Adds a component to the container */
    public JSWTMenuComponent add(final JSWTMenuComponent c) {
    	comps.add(c);
    	registerSWTMenuComponent(c);
        return c;
    }
    
    /** Inserts a component to the container */
    public JSWTMenuComponent insert(final JSWTMenuComponent c, int index) {
    	comps.add(index, c);
    	registerSWTMenuComponent(c);
        return c;
    }
    
    public JSWTMenuComponent add(JSWTMenuComponent c, String name) {
        return add(c);
    }
    
    public JMenu add(JMenu c) {
        return (JMenu) add((JSWTMenuComponent) c);
    }
    
    public JMenu add(JMenu c, String name) {
        return (JMenu) add((JSWTMenuComponent) c, name);
    }
    
    public JMenuItem add(JMenuItem c) {
        return (JMenuItem) add((JSWTMenuComponent) c);
    }
    
    public JMenuItem add(JMenuItem c, String name) {
        return (JMenuItem) add((JSWTMenuComponent) c, name);
    }
    
    /** Adds an action to the container */
    public JMenuItem add(Action a) {
        JMenuItem jmenu = new JMenuItem(a);
        add(jmenu);
        return jmenu;
    }
    
    /** Adds a new menu item with the specified text */
    public JMenuItem add(String text) {
        JMenuItem jmenu = new JMenuItem(text);
        add(jmenu);
        return jmenu;
    }
    
    /** Adds a component to the menu.
     *  @throws IllegalArgumentException - SwingWT cannot support this.
     */
    public Component add(Component c) {
    	// I don't understand why this is necessary but it seems to be in 
    	// in some cases
    	if(c instanceof JSWTMenuComponent) {
    		return add((JSWTMenuComponent)c);
    	}
        throw new IllegalArgumentException("Sorry, SwingWT cannot support components in menus: " + c);
    }
    
    /** Adds a component to the menu at the index specified.
     *  @throws IllegalArgumentException - SwingWT cannot support this.
     */
    public Component add(Component c, int index) {
        throw new IllegalArgumentException("Sorry, SwingWT cannot support components in menus");
    }

    public JMenuItem insert(JMenuItem item, int index) {
    	return (JMenuItem)insert((JSWTMenuComponent)item, index);
    }
    
    /** Removes a component from the menu
     *  @throws IllegalArgumentException - SwingWT cannot support this.
     */
    public void remove(Component c) {
        throw new IllegalArgumentException("Sorry, SwingWT cannot support components in menus");
    }
    
    public void remove(JSWTMenuComponent c) {
        c.dispose();
    }
    
    /** Removes the menu item with the specified index from the list */
    public void remove(int index) {
        JSWTMenuComponent c = (JSWTMenuComponent) comps.get(index);
        comps.remove(index);
        c.dispose();
    }
    
    public void removeAll() {
        Iterator i = comps.iterator();
        while (i.hasNext()) {
            JSWTMenuComponent c = (JSWTMenuComponent) i.next();
            c.dispose();
        }
        
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (peer != null && container != null) {
                    // Kill the old container, destroying the children
                    container.dispose();
                    // Recreate it
                    container = new Menu(shell, SWT.DROP_DOWN);
                    peer.setMenu(container);
                }
            }
        });
    }
    /**
     * When the component has been added to a container, this
     * routine gets called to tell it to set up SWT listeners
     * for all its events. We can map these then to the
     * AWT-style events and Swing events
     */
    public void registerEvents() 
    {
    	 super.registerEvents();
         SwingUtilities.invokeSync(new Runnable() 
         {
             public void run() 
             {
            	 container.addMenuListener(new org.eclipse.swt.events.MenuListener() 
            	 {
                     public void menuShown(org.eclipse.swt.events.MenuEvent e) 
                     {
                         processMenuEvent(0);
                     }
                     public void menuHidden(org.eclipse.swt.events.MenuEvent e) 
                     {
                          processMenuEvent(1);
                     }
                 });
            	 
             }
         });
         
    }
    public void addSeparator() {
        add(new JSeparator());
    }
   
    /** Generates a new JPopupMenu with references to the
     *  same child components as this JMenu. Should work just
     *  like Swing in practice (and doesn't waste objects - aside
     *  from the JPopupMenu itself, the existing objects are reused
     */
    public JPopupMenu getPopupMenu() {
        return new JPopupMenu(comps);
    }
    
    public void setSwingWTParent(Menu parent, Shell shell) throws Exception {
        this.shell = shell;
        peer = new MenuItem(parent, SWT.CASCADE);
        container = new Menu(shell, SWT.DROP_DOWN);
        peer.setMenu(container);
        
        // If we have some children waiting to be added, do it now
        if (comps.size() > 0) {
            Object[] o = comps.toArray();
            for (int i = 0; i < o.length; i++)
                add((JSWTMenuComponent) o[i]);
        }
        
    }
    public boolean isSelected() 
    {
        return peer.getSelection();
    }
    public void setSelected(boolean b) 
    {
    	peer.setSelection(b);
    }
    public JMenuItem getItem(final int index) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    retval = container.getItem(index);
                else
                    retval = comps.get(index);
            }
        });
        
        if(retval instanceof JMenuItem) {
        	return (JMenuItem) retval;
        }
        
        return null;
    }
    
    public int getItemCount() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    iRetval = container.getItemCount();
                else
                    iRetval = comps.size();
            }
        });
        return iRetval;
    }
    
    public boolean isTearOff() { return false; }
    /*
     * Return the Component at  position <p>
     */
    public Component getMenuComponent(int p) 
    {
    	if (p>=comps.size() || p<0)
    	{
    		return null;
    	}
    	else
    	{
    		return (Component)comps.get(p);
    	}
    		
    }
    public int getMenuComponentCount() {
        // TODO exclude separators from count
        return getItemCount();
    }
    
    public Component getComponent() {
        return (Component) this;
    }

    public int countComponents()
    {
    	return comps.size();
    }

    
    public MenuElement[] getSubElements() {
        MenuElement[] me = new MenuElement[comps.size()];
        for (int i = 0; i < comps.size(); i++) {
            me[i] = (MenuElement) comps.get(i);
        }
        return me;
    }
    
    public void menuSelectionChanged(boolean isIncluded) {
    }
    
    public void processKeyEvent(swingwt.awt.event.KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }
    
    public void processMouseEvent(swingwt.awt.event.MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }
}
