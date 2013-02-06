/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

import swingwt.awt.Dimension;
import swingwt.awt.Insets;
import swingwtx.accessibility.AccessibleContext;

public class JRadioButton extends JToggleButton implements ButtonModel, SwingConstants {
	
	public JRadioButton() {
		super();
	}

	public JRadioButton(String text) {
		super(text);
	}

	public JRadioButton(String text, Icon icon) {
		super(text, icon);
	}

	public JRadioButton(String text, boolean selected) {
		super(text, selected);
	}

	public JRadioButton(Icon icon) {
		super(icon);
	}

	public JRadioButton(Action a) {
		super(a);
	}

	public JRadioButton(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public JRadioButton(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
	}

    /** 
     * Overridden to calculate non-realized  preferred size.
     *//*
    protected swingwt.awt.Dimension calculatePreferredSize() {

    	Dimension size = 
    		(null != pIcon) ?
    		    	// if there's an icon, use the icon's size because we're using Toggle buttons    				
    				new Dimension(pIcon.getIconWidth(), pIcon.getIconHeight()) :
        
    				// Use the text height/width + 2 pixels for
    				// borders and an extra 8 pixels for the radio itself.
    				new Dimension( 
    						SwingWTUtils.getRenderStringWidth(pText) + 10, 
    						SwingWTUtils.getRenderStringHeight(pText) + 6);
        setSize(size);
        return size;
    }
    
    /**
     * Override computing the preferred size, because we'd rather calculate
     * an estimate ourselves when we have an icon.
     *
     * @return
     *//*
    protected Dimension computePreferredSize() {
    	if (null != pIcon)
    		return calculatePreferredSize();
    	
    	return super.computePreferredSize();
    }*/
    
	 /**
     * Creates the default margin for buttons.
     *
     * @return
     */
    protected Insets createDefaultMargin() {
    	// This is the default margin with the basic UI in Java 1.6.
		// It may vary depending on platforms and L&F
    	return new Insets(3, 0, 3, 5);
    }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        if (! hasDefaultIcon()) {
        	ppeer = new Button(parent.getComposite(), SWT.RADIO | SWT.LEFT);
        	toggle = false;
        } else {
        	// Radio buttons do not show images properly in SWT (they show images on the side).
        	// So we try to use a toggle. It is still ugly because we cannot remove the border
        	// of the toggle button, and it does not expand properly, but it is better than 
        	// a Radio button.
        	// The appropriate solution here is to use a composite and draw the icon on it.
        	// But then we have to fix AbstractButton because it expects a Button as ppeer.
        	ppeer = new Button(parent.getComposite(), SWT.TOGGLE);
        }
        
        ppeer.setAlignment(
        	SwingWTUtils.translateSwingAlignmentConstant(pVAlign) | 
            SwingWTUtils.translateSwingAlignmentConstant(pHAlign));
        peer = ppeer;
        this.parent = parent;
    }
    
    protected void adjust(Dimension size, boolean hasText, boolean hasIcon) {
    	
    	if (! hasIcon) {
    		// for the radio icon
    		size.width += 10;
    	}
    }
        
    /**
	 * Sub-types should override this method to create the appropriate
	 * accessible context for the object.
	 *
	 * @return a newly created accessible context
	 */
	protected AccessibleContext createAccessibleContext() {
		return new AccessibleJRadioButton();
	}
	
	/**
	 * An implementation of AccessibleContext, which should be extended by sub-types.
	 * 
	 * @author Thiago Tonelli Bartolomei
	 */
	protected class AccessibleJRadioButton extends AccessibleAbstractButton {
	}
}
