/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import swingwt.awt.event.*;


import org.eclipse.swt.widgets.*;


public abstract class JSWTMenuComponent extends AbstractButton {
    
    /** The SWT peer this component is representing */
    protected org.eclipse.swt.widgets.MenuItem peer = null;
    
    /** Cache value */
    protected Icon pImage = null;
    /** Cache value */
    protected int pAccelerator = 0;
    protected String pNamedAccelerator = "";
    /** Cache value */
    protected String pText = "";
    /** Swing action used to generate the component */
    protected Action menuAction = null;
    
    /** Thread safe return value */
    private Object retval;
    
    
    
    
    /* Menu event constants */
    protected final static int SELECTED = 0;
    protected final static int DESELECTED = 1;
    protected final static int CANCELED = 2;
    
    public void setIcon(final Icon icon) { 
        pImage = icon; 
        final JSWTMenuComponent pthis = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    peer.setImage(SwingWTUtils.getSWTImageFromSwingIcon(pthis, icon)); 
            }
        });
    }
            
    
    
    public int getMnemonic() { return (int) pMnemonic; };
    public void setMnemonic(char mnemonic) { pMnemonic = mnemonic; setPeerText(); }
    public void setMnemonic(int mnemonic) { pMnemonic = (char) mnemonic; setPeerText(); }
    
    /** @deprecated - Replaced by <code>getText</code> */
    public String getLabel() { return getText(); }
    /** @deprecated - Replaced by <code>setText</code> */
    public void setLabel(String label) { setText(label); }
    
    public String getText() { 
        retval = "";
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    retval = peer.getText();
                else
                    retval = pText;
            }
        });
        return retval.toString();
    }
    
    public void setText(String text) { 
        if (text == null) text = "";
        pText = text; 
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer)) {
                    peer.setText(pText); setPeerText(); showAccelerator(); 
                }
            }
        });
    }
    
    public String getToolTipText() { return ""; }
    public void setToolTipText(String tip) { }
    
    public String getActionCommand() { return actionCommand; }
    public void setActionCommand(String command) { actionCommand = command; }

    /*
     * After changes to AbstractButton.setPeerText, this method seems unnecessary.
    protected void setPeerText() {
        // If we don't have a mnemonic, don't show it
        if (pMnemonic == ' ') return;
        String text = ( peer == null ? pText : peer.getText());
        // If there is already a mnemonic - don't do anything
        if (text.indexOf("&") != -1) return;
        
        // Convert the mnemonic and text to a lower case string to make 
        // the match case insensitive
        String lower = text.toLowerCase();
        String mn = new String(new char[] { pMnemonic }).toLowerCase();
        int pos = lower.indexOf(mn);
        
        if (pos != -1) {
            text = text.substring(0, pos) + "&" + text.substring(pos, text.length());
            setText(text);
        }
    }
    */
    
    private void showAccelerator() {
        // Already accelerator shown - don't do anything
        if (pText.indexOf("\t") != -1) return;
	if (pNamedAccelerator == null) return;
	if (pNamedAccelerator.equals("")) return;
        pText += "\t" + pNamedAccelerator;
        setText(pText);
    }
    
    public void setSwingWTParent(org.eclipse.swt.widgets.Menu parent, org.eclipse.swt.widgets.Shell shell) throws Exception {
        throw new Exception("Override in subclass");
    }
    
        
    /** Handles registering of accelerator keys for menu items. */
    public void setAccelerator(final KeyStroke k) {
        
        // Translate the keystroke to an equivalent SWT         
	SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    peer.setAccelerator( swingwt.awt.event.KeyEvent.translateAWTKey(k.getKeyCode()) 
                        + swingwt.awt.event.InputEvent.translateAWTModifiers(k.getModifiers()));
                else
                    pAccelerator = swingwt.awt.event.KeyEvent.translateAWTKey(k.getKeyCode()) 
                        + swingwt.awt.event.InputEvent.translateAWTModifiers(k.getModifiers());
            }
        }); 
        
        // Make a string to represent the accelerator
        pNamedAccelerator = "";
        if ((k.getModifiers() & InputEvent.CTRL_MASK) > 0) pNamedAccelerator += "Ctrl+";
        if ((k.getModifiers() & InputEvent.ALT_MASK) > 0)
            pNamedAccelerator += "Alt+";
        if ((k.getModifiers() & InputEvent.SHIFT_DOWN_MASK) > 0 || (k.getModifiers() & InputEvent.SHIFT_MASK) > 0)
            pNamedAccelerator += "Shift+";
        if (k.getKeyCode() == KeyEvent.VK_SPACE) pNamedAccelerator += "Space";
        else if (k.getKeyCode() == KeyEvent.VK_F1) pNamedAccelerator += "F1";
        else if (k.getKeyCode() == KeyEvent.VK_F2) pNamedAccelerator += "F2";
        else if (k.getKeyCode() == KeyEvent.VK_F3) pNamedAccelerator += "F3";
        else if (k.getKeyCode() == KeyEvent.VK_F4) pNamedAccelerator += "F4";
        else if (k.getKeyCode() == KeyEvent.VK_F5) pNamedAccelerator += "F5";
        else if (k.getKeyCode() == KeyEvent.VK_F6) pNamedAccelerator += "F6";
        else if (k.getKeyCode() == KeyEvent.VK_F7) pNamedAccelerator += "F7";
        else if (k.getKeyCode() == KeyEvent.VK_F8) pNamedAccelerator += "F8";
        else if (k.getKeyCode() == KeyEvent.VK_F9) pNamedAccelerator += "F9";
        else if (k.getKeyCode() == KeyEvent.VK_F10) pNamedAccelerator += "F10";
        else if (k.getKeyCode() == KeyEvent.VK_F11) pNamedAccelerator += "F11";
        else if (k.getKeyCode() == KeyEvent.VK_F12) pNamedAccelerator += "F12";
        else 
            pNamedAccelerator += new String(new char[] {(char) swingwt.awt.event.KeyEvent.translateAWTKey(k.getKeyCode())} );
        
	// Set it
        showAccelerator();
    }
    
    /** Returns the SWT Menu peer */
    public Widget getMenuPeer() { return peer; }

    /** Change the enabled state of the component */
    public void setEnabled(final boolean b) { 
        pEnabled = b;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    peer.setEnabled(b); 
            }
        });
    }
    
    /** Returns true if the component is enabled */
    public boolean isEnabled() { 
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer))
                    retval = new Boolean(peer.isEnabled()); 
                else
                    retval = new Boolean(pEnabled);
            }
        });
        return ((Boolean) retval).booleanValue();
    }
    
    /** Destroys the component */
    public void dispose() { 
         SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTMenuControlAvailable(peer)) 
                    { peer.dispose(); peer = null; }
            }
         });
    }
    
    /** Unworkable on native platforms */
    public void requestFocus() {}
    
    
    /** Add an <code>ActionListener</code> to this component */
    public void addActionListener(ActionListener l) {
        this._getActionListeners().add(l);
    }
    
    /** Remove an <code>ActionListener</code> from the component */
    public void removeActionListener(ActionListener l) {
        this._getActionListeners().remove(l);    
    }

    /** 
     *  Sends action events to listeners
     */
    public void processActionEvent(int id) 
    {
    	
        ActionEvent ae = new ActionEvent(this, id, this.getActionCommand());
        for (int i = 0; i < actionListeners.size(); i++) {
            ActionListener al = (ActionListener) actionListeners.get(i);
            al.actionPerformed(ae);
        }
        
        
    }
    
    /**
     * When the component has been added to a container, this
     * routine gets called to tell it to set up SWT listeners
     * for all its events. We can map these then to the
     * AWT-style events and Swing events
     */
    public void registerEvents() 
    {
        
         SwingUtilities.invokeSync(new Runnable() {
             
             public void run() {

                // Action Events
                // ========================
                peer.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
                    public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                        processActionEvent(0);
                    }
                    public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {
                        //processActionEvent(0);    
                    }
                });
                peer.addArmListener(new org.eclipse.swt.events.ArmListener() {
                    public void widgetArmed(org.eclipse.swt.events.ArmEvent e) {
                    	
                        processItemEvent();
                    }
                });
                
             }
         });
    }
    /**
     * If the component has just been added to a container, but
     * properties were set in the meantime, this routine gets
     * called by JMenu/JMenuBar to set the cached properties.
     */
    public void assertCachedProperties() {
        final JSWTMenuComponent pthis = this;
         SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                peer.setEnabled(pEnabled);
                if (pImage != null) peer.setImage(SwingWTUtils.getSWTImageFromSwingIcon(pthis, pImage));
                if (pMnemonic != ' ') setPeerText();
                if (pAccelerator != 0) { peer.setAccelerator(pAccelerator); showAccelerator(); }
                peer.setText(pText);
            }
         });
    }
    
}
