/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;

public class FocusManager extends swingwt.awt.KeyboardFocusManager {
    
    private static FocusManager currentManager = null;
    
    public static FocusManager getCurrentManager() {
        return currentManager;
    }
    
    public static void setCurrentManager(FocusManager manager) {
        currentManager = manager;
    }
    
    /** Meant to be overriden in subclasses. Unlike Swing, we default
     *  to a null manager, that handles focusing in the order they
     *  were added to the screen.
     */
    public void focusNextComponent(swingwt.awt.Component component) {
    }
    
    /** Meant to be overriden in subclasses. Unlike Swing, we default
     *  to a null manager, that handles focusing in the order they
     *  were added to the screen.
     */
    public void focusPreviousComponent(swingwt.awt.Component component) {
    }
    
}
