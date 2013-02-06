/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.util.Vector;

import swingwt.awt.event.ActionListener;
import swingwtx.accessibility.AccessibleContext;
import swingwtx.swing.JMenuItem;
import swingwtx.swing.KeyStroke;

public class MenuItem extends MenuComponent {
    
    protected MenuShortcut shortcut = null;
    
    private Vector actionListeners = new Vector();
    
    public MenuItem() { swingPeer = new JMenuItem(); }
    public MenuItem(String text) { swingPeer = new JMenuItem(text); }
    
    private JMenuItem getSwingPeer() { return (JMenuItem) swingPeer; }

    public MenuShortcut getShortcut() { return shortcut; }
    public void deleteShortcut() { getSwingPeer().setAccelerator(null); }
    public void setShortcut(MenuShortcut s) { 
        // Translate menu shortcut to swing Keystroke
        shortcut = s; 
        KeyStroke ks = KeyStroke.getKeyStroke( s.getKey(), 
            ( s.usesShiftModifier() ? swingwt.awt.event.KeyEvent.SHIFT_MASK : 0 ) |
            swingwt.awt.event.KeyEvent.CTRL_MASK
            );
        getSwingPeer().setAccelerator(ks);
    }
    
    public void addActionListener(ActionListener l) { getSwingPeer().addActionListener(l); }
    
    // TODO: Implement
    //protected void disableEvents(long eventsToDisable) {  }
    //protected void enableEvents(long eventsToEnable) {  }
	//protected void processActionEvent(ActionEvent e) { getSwingPeer().processActionEvent(e); }
	
	public void setEnabled(boolean b) { getSwingPeer().setEnabled(b); }
	public void enable() { setEnabled(true); }
	public void enable(boolean b) { setEnabled(b); }
    public void disable() { setEnabled(false); }
	
	public AccessibleContext getAccessibleContext() { return swingPeer.getAccessibleContext(); }
	public String getActionCommand() { return getSwingPeer().getActionCommand(); }
	public ActionListener[] getActionListeners() { return (ActionListener[])actionListeners.toArray(new ActionListener[0]); }
	/** @deprecated - replaced by getText() */
	public String getLabel() { return getSwingPeer().getText(); }
	public boolean isEnabled() { return getSwingPeer().isEnabled(); }
	public void removeActionListener(ActionListener l) { getSwingPeer().removeActionListener(l); }
	public void setActionCommand(String command) { getSwingPeer().setActionCommand(command); }
	/** @deprecated - replaced by setText() */
	public void setLabel(String label) { getSwingPeer().setText(label); }
}
