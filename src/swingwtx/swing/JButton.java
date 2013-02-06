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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolItem;

import swingwt.awt.Dimension;
import swingwt.awt.Insets;

public class JButton extends AbstractButton implements SwingConstants, ButtonModel {

    protected boolean pDefaultCapable = true;
    protected swingwt.awt.Container pDefaultButtonParent = null;
    /** If this JButton is representing an SWT tool button, a reference to it */
    protected ToolItem pSWTToolButton = null;

	public JButton() {
		this("", null);
	}

	public JButton(String text) {
		this(text, null);
	}

	public JButton(Action a) {
		this();
        setAction(a);
	}

	public JButton(Icon icon) {
		this("", icon);
	}

	public JButton(String text, Icon icon) {
		super();
		pHAlign = CENTER;
    	setText(text);
    	setIcon(icon);
	}
	
    /**
     * @see AbstractButton#setAction(Action, boolean) 
     */
    protected JButton(Action a, boolean addAsListener) {
    	setAction(a, addAsListener); 
    }
    
    /**
     * If this JButton is part of a JButtonMappedAction, update it
     */
    private void updateMappedAction() {
        if (pAction instanceof JButtonMappedAction) {
            pAction.putValue(Action.NAME, getText());
            pAction.putValue(Action.SHORT_DESCRIPTION, getToolTipText());
            pAction.putValue(Action.SMALL_ICON, getIcon());
            pAction.putValue(Action.DISABLED_ICON, getIcon());
            pAction.putValue(Action.MNEMONIC_KEY, new Integer(getMnemonic()));    
        }
    }
    
    /**
     * If this JButton is representing an SWT ToolItem, then update it's properties
     * from this
     */
    private void updateToolButton() {
        if (pSWTToolButton == null) {
        	return;
        }
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                pSWTToolButton.setText(getText());
                pSWTToolButton.setToolTipText(getToolTipText());
                pSWTToolButton.setEnabled(isEnabled());
                pSWTToolButton.setImage(SwingWTUtils.getSWTImageFromSwingIcon(JButton.this, getIcon()));
            }
        });  
    }
    
    public void setIcon(Icon icon) {
    	super.setIcon(icon);
        // renderText(); -- No longer needed
    	updateToolButton();
        updateMappedAction();
	}
        
    /** Overrides superclass so we can update any ToolItem */
    public void setToolTipText(String text) {
        super.setToolTipText(text);
        updateToolButton();
        updateMappedAction();
    }
    
    /** Overrides superclass as we need to read cached text for
     *  tool button purposes */
    public String getText() {
        return pText;    
    }
    
    /** Overrides superclass so we can render it where necessary */
    public void setText(String text) {
    	
        super.setText(text);
        
        // redraw the window version if we have a foreground color (see method for more info)
        // if (pForeground != null) handleWindowsForegroundKludge(); -- No longer needed
        // renderText(); -- No longer needed
        
	updateMappedAction();
        updateToolButton();
    }
    
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        updateToolButton();
    }
    
    public boolean getDefaultCapable() { return pDefaultCapable; }
    public void setDefaultCapable(boolean b) { pDefaultCapable = b; }

   /* 9 Aug 06 - RRT, SWT 3.2 supports Image/Text buttons so
    *  this code is no longer needed - left here for reference
    * ====================================
    *
	protected void setColorTextImage(org.eclipse.swt.widgets.Button button, String text, Color color) {
 	
 		//if (button == null) return;
 		
 		button.setForeground(color.getSWTColor());
 		final org.eclipse.swt.graphics.Image img = createTextImage(button, text);
 		
 		button.setText(text);
 		button.setImage(img);
 		button.addDisposeListener(new DisposeListener() {
 			public void widgetDisposed(DisposeEvent e) {
 				if (!img.isDisposed()) {
 					img.dispose();
 				}
 			}
 		});
 	}

    private org.eclipse.swt.graphics.Image createTextImage(org.eclipse.swt.widgets.Button button, String text) {
 		GC buttonGC = new GC(button);
 		org.eclipse.swt.graphics.Point size = buttonGC.textExtent(text, SWT.DRAW_MNEMONIC);
 		org.eclipse.swt.graphics.Font font = buttonGC.getFont();
 		buttonGC.dispose();
 		
 		org.eclipse.swt.graphics.Image img = new org.eclipse.swt.graphics.Image(button.getDisplay(), size.x, size.y);
 		
 		GC imgGC = new GC(img);
 		imgGC.setFont(font);
 		imgGC.setBackground(button.getBackground());
 		imgGC.setForeground(button.getForeground());
 		imgGC.drawText(text, 0, 0, SWT.DRAW_MNEMONIC);
 		imgGC.dispose();
 		
 		return img;
    }
    
     protected void renderText() {
         
        if (pText == null)
            return;
        if (!SwingWTUtils.isSWTControlAvailable(ppeer))
            return;
        if (pText.length() == 2 && pText.startsWith("&"))
            return;
        if (pText.equals("") || pImage == null)
            return;

        final JButton pthis = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC(ppeer.getImage());
                gc.setFont(ppeer.getFont()); // Required to calculate correct string width

                // Calculate the text width
                org.eclipse.swt.graphics.Point p = gc.textExtent(pText);

                // Create a new image accounting for a two pixel margin and
                // the text width
                int width = ppeer.getImage().getBounds().width + 2;
                int height = ppeer.getImage().getBounds().height;
                org.eclipse.swt.graphics.Image im = new org.eclipse.swt.graphics.Image(SwingWTUtils.getDisplay(), width + p.x, height);

                // Draw the old image on it
                org.eclipse.swt.graphics.GC ngc = new org.eclipse.swt.graphics.GC(im);
                ngc.setFont(ppeer.getFont()); // Required to properly render font on image-text button

                 // Here, I set the background and foreground to the composite 
                 // background colour, so if your image is a PNG, you get
                 // the right alpha channel
                 ngc.setBackground(ppeer.getShell().getBackground());
                 ngc.setForeground(ppeer.getShell().getBackground());
                 ngc.fillRectangle(0, 0, width + p.x, height);

                 // Change foreground back to the regular colour for text rendering
                 ngc.setForeground(ppeer.getShell().getForeground());
                 ngc.drawImage(SwingWTUtils.getSWTImageFromSwingIcon(pthis, pImage), 0, 0);

                 // We have to take & out of the text, because I'm too lazy
                 // to support mnemonics (yes, I could draw a line, whatever - I'll
                 // revisit this)
                 String text = pText;
                 int mnPos = text.indexOf("&");
                 if (mnPos != -1)
                     text = text.substring(0, mnPos) + text.substring(mnPos + 1, text.length());

                 // Draw the text
                 ngc.drawText(text, width, (height - p.y) / 2, true);

                 // Drop the drawing contexts
                 ngc.dispose();
                 gc.dispose();

                 // Update the image
                 ppeer.setImage(im);
             }
         });
     }
     
     protected void handleWindowsForegroundKludge() {
     	
    	if (SwingWTUtils.isSWTControlAvailable(peer))
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                	
                    // Kludge alert!
		    // Buttons on Windows do no support setting of the foreground color - so we have to
		    // draw it ourself. 
		    if (SwingWTUtils.isWindows() && pForeground != null && pText != null)
		        setColorTextImage(ppeer, pText, pForeground);
                }
            });
     }
 
     public void setForeground(Color foreground) {
     	  super.setForeground(foreground);
     	  handleWindowsForegroundKludge();
     }
    
     ==================================
     */
     
    /** 
     * Overridden to calculate non-realized  preferred size.
     *//*
    protected swingwt.awt.Dimension calculatePreferredSize() {

    	Dimension size = 
    		(null != pIcon) ?
    		    	// if there's an icon, use the icon's size    				
    				new Dimension(pIcon.getIconWidth(), pIcon.getIconHeight()) :
        
    				// Use the text height/width + 6 pixels for width
    			    // 10 for height button borders.
    				new Dimension( 
    						SwingWTUtils.getRenderStringWidth(pText) + 34, 
    						SwingWTUtils.getRenderStringHeight(pText) + 10);
    	
    	Insets i = this.getMargin();
    	if (null != i) {
    		size.height += i.top + i.bottom;
    		size.width += i.left + i.right;
    	}
        setSize(size);
        return size;
    }
    
    protected Dimension computePreferredSize() {
    	if (null != pIcon)
    		return calculatePreferredSize();
    	
    	return super.computePreferredSize();
    }*/
    
    /**
     * JButtons that have HTML in the text have an unlimited maximum width.
     *
     * @return
     */
    public Dimension getMaximumSize() {
    	Dimension d = super.getMaximumSize();
    	if (containsHTML)
    		return new Dimension(Integer.MAX_VALUE, d.height);
    	return d;
    }
    
    protected int getSWTFlags() {
        return (parent instanceof JToolBar ? SWT.FLAT : SWT.PUSH);
    }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        
        // If the Button's parent is a JToolbar, make it flat instead of
        // a push button
        ppeer = new Button(parent.getComposite(), getSWTFlags());
        peer = ppeer;

        // Refresh foreground color, if set
        if (pForeground != null) 
        	setForeground(pForeground);
        
        if (pFont != null) 
        	ppeer.setFont(pFont.getSWTFont());
        
        // @see AbstractButton#setVerticalAlignment(int)
        ppeer.setAlignment(
        //	SwingWTUtils.translateSwingAlignmentConstant(pVAlign) | 
            SwingWTUtils.translateSwingAlignmentConstant(pHAlign));
 		
        // renderText(); -- RRT 9 Aug 06 - No longer needed for SWT 3.2
        
        if (pSize != null)
            ppeer.setSize(pSize.width, pSize.height);
        
        this.parent = parent;
        
        // If this button is the default for a given container,
        // make sure it knows about it
        if (pDefaultButtonParent != null) {
            
            // Is the peer a shell?
            if (pDefaultButtonParent.getComposite() instanceof Shell) {
                ((Shell) pDefaultButtonParent.getComposite()).setDefaultButton(ppeer);    
            }
            // Some other type of container
            else {
                pDefaultButtonParent.getComposite().getShell().setDefaultButton(ppeer);    
            }
        }
    }
    
    public void setDefaultButtonParent(swingwt.awt.Container window) {
        pDefaultButtonParent = window;
    }
    
    public boolean isDefaultCapable() {
    	return true;
    }
}
