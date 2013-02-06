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
import swingwt.awt.Insets;
import swingwt.awt.event.KeyEvent;
import swingwt.awt.event.KeyListener;
import swingwtx.accessibility.Accessible;

public class JCheckBox extends JToggleButton implements SwingConstants, Accessible {

    /** AWT uses Checkboxes for radio buttons as well **/
    private boolean isAWTRadio = false;

	public JCheckBox() {
		super();
	}

	public JCheckBox(Icon icon) {
		super(icon);
	}

	public JCheckBox(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public JCheckBox(String text) {
		super(text);
	}

	public JCheckBox(Action a) {
		super(a);
	}

	public JCheckBox(String text, boolean selected) {
		super(text, selected);
	}

	public JCheckBox(String text, Icon icon) {
		super(text, icon);
	}

	public JCheckBox(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
	}

	/** This constructor is used for AWT CheckboxGroup support **/
	public JCheckBox(String text, boolean selected, ButtonGroup bg) {
		super(text, selected);
		isAWTRadio = true;
	}
 
    /**
     * Sends KeyEvents to component listeners
     * Overriden from Component to handle ItemEvents
     */
    public void processKeyEvent(KeyEvent e) {
    	// TODO - this is very close to JToggleButton.processKeyEvent. Check if we need this...
        Iterator i = keyListeners.iterator();
        while (i.hasNext()) {
            KeyListener ml = (KeyListener) i.next();
            if (e.eventID == KeyEvent.PRESSED) { ml.keyTyped(e); processItemEvent(); }
            if (e.eventID == KeyEvent.RELEASED) ml.keyReleased(e);
            if (e.eventID == KeyEvent.PRESSED) ml.keyPressed(e);
        }  
    }

    /** Overriden to calculate non-realised
     *  preferred size.
     *//*
    protected swingwt.awt.Dimension calculatePreferredSize() {
        // Use the text height/width + 6 pixels for
        // borders and an extra 8 pixels for the checkbox itself.
        swingwt.awt.Dimension size = new swingwt.awt.Dimension( 
            SwingWTUtils.getRenderStringWidth(pText) + 6, 
            SwingWTUtils.getRenderStringHeight(pText) + 6);
        setSize(size);
        return size;
    }*/
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     * 
     * @see JRadioButton#setSwingWTParent
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        if (! hasDefaultIcon()) {
        	toggle = false;
	        if (isAWTRadio) {
	            ppeer = new Button(parent.getComposite(), SWT.RADIO);
	        } else {
	            ppeer = new Button(parent.getComposite(), SWT.CHECK);
	        }
        } else {
        	// See JRadioButton#setSwingWTParent
            ppeer = new Button(parent.getComposite(), SWT.TOGGLE);
        }
        ppeer.setAlignment(
        	//SwingWTUtils.translateSwingAlignmentConstant(pVAlign) | 
            SwingWTUtils.translateSwingAlignmentConstant(pHAlign));
        peer = ppeer;
        this.parent = parent;
    }
    
    /**
     * Creates the default margin for buttons.
     *
     * @return
     */
    protected Insets createDefaultMargin() {
    	// This is the default margin with the standard L&F in Java 1.6, GTK for Linux.
		// It may vary depending on platforms and L&F 
		return new Insets(2, 10, 2, 10);
    }
     
    protected void adjust(Dimension size, boolean hasText, boolean hasIcon) {
    	if (toggle && ! hasText) {
	    	size.height -= 10;
	    	size.width -= 10;
    	}
    }
}
