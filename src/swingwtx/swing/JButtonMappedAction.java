/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 

 
 */

package swingwtx.swing;

import swingwt.awt.event.*;

/**
 * Utility class - maps a JButton and Action together.
 *
 * Used by JToolBar/JButton when linking toolbar items
 * together.
 *
 * @author Robin Rawson-Tetley
 */
class JButtonMappedAction extends AbstractAction {
    
    JButton button = null;
    Action origAction = null; 
    
    public JButtonMappedAction(JButton b) {
        button = b;    
    }
    public void actionPerformed(ActionEvent e) {
        button.processActionEvent(0);
    }
    public JButton getJButton() { return button; }
    public void setOrigAction(Action a)
    {
    	this.origAction = a;
    }
    public Action getOrigAction()
    {
    	return origAction;
    }
    
}
