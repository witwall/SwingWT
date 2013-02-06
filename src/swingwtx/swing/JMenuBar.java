/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import swingwt.awt.Component;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.*;

public class JMenuBar extends JComponent implements MenuElement {
    
    /** The SWT menu this container represents */
    protected Menu menu = null;
    /** Stores children until we're ready */
    /** The SWT shell this MenuBar is attached to */
    protected Shell shell = null;
    private Object retval;

    private int iRetval;
    
    public JMenuBar() {
    }

    /** Adds a component to the container */
    public JSWTMenuComponent add(final JSWTMenuComponent c) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                try { 
                	comps.add(c);
                    if (menu != null) {
                        c.setSwingWTParent(menu, shell);
                        c.assertCachedProperties();
                        c.registerEvents();
                    }
                } 
                catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        });
        return c;
    }
    
    public JMenu add(JMenu c) {
        return (JMenu) add((JSWTMenuComponent) c);    
    }
    
    public JMenuItem add(JMenuItem c) {
        return (JMenuItem) add((JSWTMenuComponent) c);    
    }
    
    public Component add(Component c) {
        if (c instanceof JSWTMenuComponent)
            return add(((JSWTMenuComponent) c));
        else
            return c;
    }
    
        
    public void remove(JSWTMenuComponent c) {
        c.dispose();    
    }
    
    public void setVisible(final boolean b) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (menu != null)
                    menu.setVisible(b);
            }
        });
    }
    
    public boolean isVisible() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (menu != null)
                    retval = new Boolean(menu.isVisible());
                else
                    retval = new Boolean(false);
            }
        });
        return ((Boolean) retval).booleanValue();
    }
    
    public swingwt.awt.Dimension getPreferredSize() {
        return new swingwt.awt.Dimension(0, 0);    
    }
    
    public void addSeparator() { 
        add(new JSeparator());
    }
    
    public Menu getSWTMenu() {
        return menu;
    }
    
    public void setSwingWTParent(Shell parent) {
        shell = parent;
        menu = new Menu(parent, SWT.BAR);    
        parent.setMenuBar(menu);
        
        // If we have some children waiting to be added, do it now
        if (comps.size() > 0) {
    	    Object[] o = comps.toArray();
	     for (int i = 0; i < o.length; i++) {
		     add((JSWTMenuComponent) o[i]);
             }
        }
    }
    
    public JMenuItem getItem(final int index) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(shell))
                    retval = menu.getItem(index);
                else
                	retval = comps.get(index);
            }
        });
        return (JMenuItem) retval;
    }
    
    public int getItemCount() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(shell))
                    iRetval = menu.getItemCount();
                else
                	iRetval = comps.size();
            }
        });
        return iRetval;
    }

    public int getMenuCount() {
        // TODO
        return getItemCount();
    }

    public JMenu getMenu(final int index) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(shell))
                    retval = menu.getItem(index);
                else
                	retval = comps.get(index);
            }
        });
        return (JMenu) retval;
    }
    
    public Component getComponent() {
        return (Component) this;
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
