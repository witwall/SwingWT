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

public class JMenuItem extends JSWTMenuComponent implements ButtonModel {
    
    private Shell shell = null;
    
    public JMenuItem() {setModel(this);}
    public JMenuItem(Action a) { setAction(a); setModel(this); }
    public JMenuItem(Icon icon) { this("", icon); }
    public JMenuItem(String text) {this(text, null); }
    public JMenuItem(String text, Icon icon) { pText = text; pImage = icon; setModel(this);}
    public JMenuItem(String text, int mnemonic) { pText = text; setMnemonic(mnemonic); setModel(this);}
    
    public void setAction(Action a) {
        if (a.getValue(Action.ACCELERATOR_KEY) != null) {
            if (a.getValue(Action.ACCELERATOR_KEY) != null)         
                setAccelerator((KeyStroke) a.getValue(Action.ACCELERATOR_KEY));
        }
        super.setAction(a);
    }
    
    public void setSwingWTParent(Menu parent, Shell shell) throws Exception { 
        this.shell = shell;
        peer = new MenuItem(parent, SWT.PUSH);
    }
    
    public boolean isSelected() {
        return false;
    }
    
    public void setSelected(boolean b) {
    }
    
    public Object[] getSelectedObjects() {
        return null;
    }
    
    public boolean isArmed() {
        return false;
    }
    
    public boolean isPressed() {
        return isSelected();
    }
    
    public boolean isRollover() {
        return false;
    }
    
    public void setArmed(boolean b) {
    }
    
    public void setPressed(boolean b) {
    }
    
    public void setRollover(boolean b) {
    }
    
    public Component getComponent() {
        return (Component) this;
    }
    
    public MenuElement[] getSubElements() {
        // Can't have subelements
        return null;
    }
    
    public void menuSelectionChanged(boolean isIncluded) {
    }
    
    public void processKeyEvent(swingwt.awt.event.KeyEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }
    
    public void processMouseEvent(swingwt.awt.event.MouseEvent event, MenuElement[] path, MenuSelectionManager manager) {
    }
    
}
