/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.awt;

import java.beans.PropertyChangeListener;

import swingwtx.swing.FocusManager;


/**
 * Not nearly complete, but enough to handle focusNext/focusPrevious
 * which is enough to write your own focus manager!
 */
public abstract class KeyboardFocusManager {
    
	public static final int FORWARD_TRAVERSAL_KEYS = 0;
    public static final int BACKWARD_TRAVERSAL_KEYS = 1;
    public static final int UP_CYCLE_TRAVERSAL_KEYS = 2;
    public static final int DOWN_CYCLE_TRAVERSAL_KEYS = 3;
    public KeyboardFocusManager() {}
    
    public void focusNextComponent() {  }
    public abstract void focusNextComponent(Component component);
    public void focusPreviousComponent() {  }
    public abstract void focusPreviousComponent(Component component);
    
    public static KeyboardFocusManager getCurrentKeyboardFocusManager() {
        KeyboardFocusManager manager = FocusManager.getCurrentManager();
        if (manager == null) {
            manager = new FocusManager();
            FocusManager.setCurrentManager((FocusManager)manager);
        }
        return manager;
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        // TODO
    }

    /**
     * @param form
     */
    public void addKeyEventDispatcher(KeyEventDispatcher dispatcher)
    {
        // TODO
    }

    public Component getFocusOwner() {
        // TODO
        return null;
    }
    
}
