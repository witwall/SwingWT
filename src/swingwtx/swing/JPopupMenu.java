/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import swingwtx.swing.event.*;

import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.*;

public class JPopupMenu extends JMenuBar {
    
    protected Vector popupListeners = new Vector();
    
    protected final static int CANCELED = 0;
    protected final static int INVISIBLE = 1;
    protected final static int VISIBLE = 2;
    
    private swingwt.awt.Component invoker = null;
    
    public JPopupMenu() { }
    public JPopupMenu(String label) { }
    
    /** Only to be used by JMenu on demand for getPopupMenu() */
    protected JPopupMenu(Vector childComponents) { this.comps = childComponents; }
    
    public void addPopupMenuListener(PopupMenuListener l) {
        popupListeners.add(l);
    }
    
    public void removePopupMenuListener(PopupMenuListener l) {
        popupListeners.remove(l);
    }
    
    public void processPopupMenuEvent(int id) {
        Iterator i = popupListeners.iterator();
        PopupMenuEvent e = new PopupMenuEvent(this);
        while (i.hasNext()) {
            PopupMenuListener l = (PopupMenuListener) i.next();
            switch(id) {
                case CANCELED: l.popupMenuCanceled(e); break;
                case INVISIBLE: l.popupMenuWillBecomeInvisible(e); break;
                case VISIBLE: l.popupMenuWillBecomeVisible(e); break;
            }
        }
    }
    
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b)
            processPopupMenuEvent(VISIBLE);
        else {
            processPopupMenuEvent(INVISIBLE);
            processPopupMenuEvent(CANCELED);
        }
    }
    
    public void show(swingwt.awt.Component c, int x, int y) { 
        
        if (c!=null)setInvoker(c);
    	// Create the actual menu if it hasn't been already
        // and tie it to the component
    	
	boolean needToCreatePeer = menu == null;
	if (!needToCreatePeer)
	    needToCreatePeer = menu.isDisposed();
	
        if (needToCreatePeer) {
            setSwingWTParent(c.getSWTPeer().getShell());
            c.getSWTPeer().setMenu(menu);
        }
        
        // activate it's popup menu:
        menu.setVisible(true);
        
    }

    public void setSwingWTParent(Shell parent) {
        shell = parent;
        menu = new Menu(parent, SWT.POP_UP);
        // If we have some children waiting to be added, do it now
        if (comps.size() > 0) {
	     Object[] o = comps.toArray();
	     for (int i = 0; i < o.length; i++) {
		     add((JSWTMenuComponent) o[i]);
             }
        }
        menu.addMenuListener((MenuListener) new MenuListener() {
            
			public void menuHidden(MenuEvent arg0) {
	                    processPopupMenuEvent(INVISIBLE);
	                    processPopupMenuEvent(CANCELED);
			}

			public void menuShown(MenuEvent arg0) {
                            processPopupMenuEvent(VISIBLE);
			}
        	
        });
    }   
    // Do nothing here 
    public void setLightWeightPopupEnabled(boolean b){}
    public static void setDefaultLightWeightPopupEnabled(boolean aFlag){}
    public swingwt.awt.Component getInvoker()
    {
    	return invoker;
    }
    public void setInvoker(swingwt.awt.Component invoker)
    {
    	this.invoker = invoker;
    }
    
}
