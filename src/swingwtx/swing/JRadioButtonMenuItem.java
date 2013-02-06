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

import swingwt.awt.event.*;

import java.util.*;

public class JRadioButtonMenuItem extends JMenuItem implements ButtonModel {
    
    private Shell shell = null;
    protected boolean pState = false;
    protected Vector itemListeners = new Vector();
    private Object retval;

    public JRadioButtonMenuItem() {setModel(this);}
    public JRadioButtonMenuItem(Action a) { setAction(a); setModel(this);}
    public JRadioButtonMenuItem(Icon icon) { this("", icon); }
    public JRadioButtonMenuItem(Icon icon, boolean b) { this("", icon, b); }
    public JRadioButtonMenuItem(String text) { this(text, null, false); }
    public JRadioButtonMenuItem(String text, boolean b) { this(text, null, b); }
    public JRadioButtonMenuItem(String text, Icon icon) { this(text, null, false); }
    public JRadioButtonMenuItem(String text, Icon icon, boolean b) { pText = text; pImage = icon; pState = b; setModel(this); }
    public JRadioButtonMenuItem(String text, int mnemonic) { pText = text; setMnemonic(mnemonic); setModel(this);}
    public JRadioButtonMenuItem(String text, int mnemonic, boolean b) { pText = text; setMnemonic(mnemonic); pState = b; setModel(this);}
    
    public Object[] getSelectedObjects() { return new Object[] { getText() };}
    public boolean getState() { 
         SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    retval = new Boolean(ppeer.getSelection());
                else
                    retval = new Boolean(pState);
            }
         });
         return ((Boolean) retval).booleanValue();
    }
        
    public void setState(boolean b) { 
         pState = b;
         SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    ppeer.setSelection(pState);
            }
         });
    }
    public boolean isSelected() { return getState(); }
    public void setSelected(boolean b) { setState(b); }
    
    public void addItemListener(ItemListener l) {
        itemListeners.add(l);
    }
    
    public void removeItemListener(ItemListener l) {
        itemListeners.remove(l);
    }
    
    /** 
     *  Sends action events to listeners
     *  Overriden from JSWTMenuComponent to handle selection events
     */
    public void processActionEvent(int id) {
        super.processActionEvent(id);
	processItemEvent();
    }
    
    /**
     * Handles firing of Item events for when selection changes
     */
    public void processItemEvent() {
        if (itemListeners.size() == 0) return;
        Iterator i = itemListeners.iterator();
        ItemEvent e = new ItemEvent(this, 0, this, (ppeer.getSelection() ? ItemEvent.SELECTED : ItemEvent.DESELECTED));
        while (i.hasNext()) {
            ItemListener il = (ItemListener) i.next();
            il.itemStateChanged(e);
        }
    }
    
    public void setSwingWTParent(Menu parent, Shell shell) throws Exception { 
        this.shell = shell;
        peer = new MenuItem(parent, SWT.RADIO);
        peer.setSelection(pState);
    }
    
}
