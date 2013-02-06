/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
   
*/
package swingwtx.swing;

import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import swingwt.awt.Dimension;
import swingwt.awt.event.KeyEvent;
import swingwt.awt.event.KeyListener;
import swingwt.awt.event.MouseEvent;
import swingwt.awt.event.MouseListener;

public class JToggleButton extends AbstractButton implements SwingConstants {
    
    /**
     * A custom style flag for the SWT Button
     */
    protected int customStyle = 0;
	
    /**
     * Indicates that the TOGGLE flag is used for the peer.
     * 
     * (note that sub-types may use RADIO or CHECK for example.
     */
	protected boolean toggle = true;
	
    public JToggleButton() {
        this("", null, false);
    }

    public JToggleButton(String text) {
        this(text, null, false);
    }
    
    public JToggleButton(Icon icon) {
        this(null, icon, false);
    }
    
    public JToggleButton(Action a) {
        this();
        setAction(a);
    }
    
    public JToggleButton(String text, Icon icon) {
        this(text, icon, false);
    }
    
    public JToggleButton(String text, boolean selected) {
        this(text, null, selected);
    }
    
    public JToggleButton(Icon icon, boolean selected) {
        this("", icon, selected);
    }
    
    public JToggleButton(String text, Icon icon, boolean selected) {
    	super();
    	setText(text);
    	setIcon(icon);
    	_setSelected(selected);
    }	
	
    /**
     * This simulates how toggle buttons change their state,
     * i.e., they toggle the selected property.
     * 
     * @see AbstractButton#doClick(int)
     */
    protected void changeState() {
    	this._setSelected(! this.isSelected());
    }
    
    /**
     *  Sends mouse events to component listeners 
     *  Overriden from Component to handle ItemEvents
     */
    public void processMouseEvent(MouseEvent e) {
        Iterator i = mouseListeners.iterator();
        int eventID = e.getID();
        while (i.hasNext()) {
            MouseListener ml = (MouseListener) i.next();
            if (eventID == MouseEvent.MOUSE_CLICKED) { ml.mouseClicked(e); processItemEvent(); }
            if (eventID == MouseEvent.MOUSE_ENTERED) ml.mouseEntered(e);
            if (eventID == MouseEvent.MOUSE_EXITED) ml.mouseExited(e);
            if (eventID == MouseEvent.MOUSE_PRESSED) ml.mousePressed(e);
            if (eventID == MouseEvent.MOUSE_RELEASED) ml.mouseReleased(e);
        }
    }
    
    /**
     * Sends KeyEvents to component listeners
     * Overriden from Component to handle ItemEvents
     */
    public void processKeyEvent(KeyEvent e) {
        Iterator i = keyListeners.iterator();
        while (i.hasNext()) {
            KeyListener ml = (KeyListener) i.next();
            if (e.eventID == KeyEvent.PRESSED) { ml.keyTyped(e); processItemEvent(); }
            if (e.eventID == KeyEvent.RELEASED) ml.keyReleased(e);
            if (e.eventID == KeyEvent.PRESSED) ml.keyPressed(e);
        }  
    }
    
    /**
     * Sets a custom style flag.
     * 
     * This is not part of the Swing API, it is here only so that SwingWT clients
     * can instantiate toggle buttons with a different style.
     * 
     * This method should be called prior to adding the toggle button to a container.
     *
     * @param style
     */
    public void setCustomSWTStyle(int style) {
        this.customStyle = style;
    }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new Button(parent.getComposite(), SWT.TOGGLE | customStyle);
        ppeer.setAlignment(
        		//pVAlign | 
        		pHAlign);
        peer = ppeer;
        this.parent = parent;
    }
    
    //@Override
    protected Dimension calculatePreferredSize() {

    	// If TOGGLE style is in use and there's no text, only respect explicitly
    	// user defined margins, otherwise use the icon's size
    	if (toggle && hasDefaultIcon() && ! hasShowingText()) {
    		Dimension size = new Dimension();
    		if (pMargin != null) {
    			size.height = pMargin.top + pMargin.bottom;
    			size.width = pMargin.left + pMargin.right;
    		}
    		Icon icon = getIcon();
    		size.height += icon.getIconHeight();
    		size.width += icon.getIconWidth();
    		return size;
    	}
    	
    	return super.calculatePreferredSize();
    }
}
