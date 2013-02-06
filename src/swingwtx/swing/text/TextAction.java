/*
   SwingWT
   Copyright(c)2004, Robin Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwtx.swing.text;

import swingwt.awt.Component;
import swingwt.awt.event.ActionEvent;
import swingwtx.swing.SwingWTUtils;
import swingwtx.swing.AbstractAction;

/**
 * @author Robin Rawson-Tetley
 *  
 */
public abstract class TextAction extends AbstractAction {

    public TextAction(String name) { super(name); }

    /** Synonymous with getFocusedComponent() */
    protected final JTextComponent getTextComponent() {
	return getFocusedComponent();
    }
    
    /** Returns the text component that currently has the focus */
    protected final JTextComponent getFocusedComponent() {
        Component c = SwingWTUtils.getFocusedComponent();
	if (c == null) return null;
	if (!(c instanceof JTextComponent)) return null;
	return (JTextComponent) c;
    }

    protected final JTextComponent getTextComponent(ActionEvent e) 
    {
    	return (JTextComponent)e.getSource();
    }

    
}
