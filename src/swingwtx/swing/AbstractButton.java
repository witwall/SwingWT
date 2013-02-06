/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;

import java.util.List;
import java.util.Vector;

import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.widgets.Button;

import swingwt.awt.Dimension;
import swingwt.awt.Insets;
import swingwt.awt.event.ActionEvent;
import swingwt.awt.event.ActionListener;
import swingwt.awt.event.ItemEvent;
import swingwt.awt.event.ItemListener;
import swingwtx.swing.SwingUtilities.SwingWTRunnable;
import swingwtx.swing.event.ChangeEvent;
import swingwtx.swing.event.ChangeListener;

/**
 * Swing Abstract Button superclass for improved
 * swingy-ness (and correct ActionEvent command)
 * Written by Diane Trout, few changes and fixes up by me 
 * to provide a better hierarchy and prevent ActionEvents
 * being fired twice.
 *
 * This class also implements ButtonModel so that by default we do not have to create 2 objects
 * to wrap a Button.
 *
 * @author Diane Trout
 * @author Robin Rawson-Tetley
 * @author Brian Sant
 * @author Thiago Tonelli Bartolomei
 */
 public abstract class AbstractButton extends JComponent implements SwingConstants, ButtonModel {
     
	 /**
	  * The SWT Button implemnenting this AbstractButton.
	  */
	 protected Button ppeer = null;
     
     /**
      * Contains always the text to be presented by the Buttons, with *no* mnemonic.
      * 
      * Note that the ppeer's text will be a composition of this text plus the mnemonic.
      */
     protected String pText = null;
     
     /**
      * The mnemonic char for this button.
      */
     protected char pMnemonic = ' ';
     
     /**
      * A model to carry some state about this button.
      */
     protected ButtonModel pModel;
     
     /**
      * The group to which this button belongs.
      */
     protected ButtonGroup pGroup;
     
     /**
      * Cache for selection. This value should be kept synchronized with 
      * the ppeer value even after it is created.
      */
     protected boolean pSelection = false;
     
     /**
      * Cache for rollover
      * 
      * TODO - currently rollover (as well as isRolloverEnabled) has no effect.
      * We have to pay attention to what happens with the icons when these methods
      * are used.
      */
     protected boolean pRollover = false;
     
     // TODO - these boolean values can be described by a single int with masks
     /**
      * Value for armed, unavailable in SWT.
      */
     protected boolean pArmed = false;
     
     /**
      * Value for pressed, unavailable in SWT.
      */
     protected boolean pPressed = false;
     
     /**
      * The last time this button was clicked.
      * 
      * This is needed to avoid triggering events twice.
      */
     protected int lastClick = -1;
     
     /**
      * True if pText contains HTML tags.
      */
     protected boolean containsHTML = false;
     
     /**
      * The margin around the button, if any.
      */
     protected Insets pMargin = null;
     
	/**
	 * Contains the icons that can be added to a button.
	 * 
	 * The array is only instantiated when an icon is added. The different icons
	 * can be accessed using the following constants as index in this array.
	 */
     protected Icon[] icons = null;
     
     // Constants for additionalIcons indexes
     public static final int DEFAULT_ICON = 0;
     public static final int PRESSED_ICON = 1;
     public static final int DISABLED_ICON = 2;
     public static final int SELECTED_ICON = 3;
     public static final int DISABLED_SELECTED_ICON = 4;
     public static final int ROLLOVER_ICON = 5;
     public static final int ROLLOVER_SELECTED_ICON = 6;
          
     protected int pHTextPosition = LEFT;
     protected int pVTextPosition = TOP;
     protected int pHAlign = LEFT;
     protected int pVAlign = TOP;
     
     protected Action pAction = null;
     
     protected List/*<ChangeListener>*/ changeListeners = null;
     protected List/*<ItemListener>*/ itemListeners = null;
     protected List/*<ActionListener>*/ actionListeners = null;
     
     /**
      * By default this object is its own model.
      */
     public AbstractButton() {
    	 pModel = this;
    	 
     	// will currently set to null, until UIDefaults is properly implemented
     	setBorder(UIManager.getBorder("Button.border"));
     }

     /**
      * Sets the action to be executed when this button is clicked.
      * 
      * This method will replace a previous action, if any, and will use
      * attributes of the action to use as icon, text, tooltip, and mnemonic.
      *
      * @param a
      */
     public void setAction(Action a) {
		if (a == null || a.equals(pAction))
			return;

		if (pAction != null)
			removeActionListener(pAction);
		
		if (a instanceof ActionListener)
			addActionListener(a);
		

		if (a.getValue(Action.SMALL_ICON) != null)
			setIcon((Icon) a.getValue(Action.SMALL_ICON));
		
		if (a.getValue(Action.NAME) != null)
			setText((String) a.getValue(Action.NAME));
		
		if (a.getValue(Action.SHORT_DESCRIPTION) != null)
			setToolTipText((String) a.getValue(Action.SHORT_DESCRIPTION));
		
		if (a.getValue(Action.MNEMONIC_KEY) != null)
			setMnemonic(((Integer) a.getValue(Action.MNEMONIC_KEY)).intValue());

		// LONG_DESCRIPTION is not used in the orginal Swing implementation.
		// The javadoc mentions that the LONG_DESCRIPTION may be used in
		// an application's "context sensitive help", but Swing components
		// don't use it directly.

		setEnabled(a.isEnabled());
		pAction = a;
	}

     /** 
      * Works like setAction.
      * @param a The action to set
      * @param addAsListener Whether or not to add the action as a listener to the button.
      *        This method is used by JToolBar with wrappers and prevents running out of
      *        stack space by getting into an endless loop of actions that fire events.
      */
     protected void setAction(Action a, boolean addAsListener) {
          setAction(a);
          removeActionListener(a);
     }
     
    public Action getAction() { 
    	return pAction; 
    }
    
    // ICON HANDLING
    
	/**
	 * Actually sets the icon that should be displayed by the peer.
	 *
	 * @param icon
	 */
	protected final void setPeerImage(final Icon icon) {
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
	                ppeer.setImage(SwingWTUtils.getSWTImageFromSwingIcon(AbstractButton.this, icon));
	            }
	        });
	}
	
	/**
	 * Lazy accessor to the additionalIcons array.
	 * 
	 * @return
	 */
	protected final Icon[] getIcons() {
		if (null == icons)
			icons = new Icon[7];
		return icons;
	}
	
	/**
	 * Checks whether this button has defined a default icon.
	 *
	 * @return
	 */
	protected final boolean hasDefaultIcon() {
		return null != icons && null != icons[DEFAULT_ICON];
	}
	
	/**
	 * Returns the appropriate icon to display based on the state of the button (like
	 * isEnabled() and isSelected() and on these two parameters.
	 * 
	 * Note that this method may return null if there is no icon set for the current
	 * state. Clients should probably use the default button if that happens.
	 *
	 * @param isPressed
	 * @param isRollOver
	 * @return
	 */
	protected final Icon selectIcon(boolean isPressed, boolean isRollOver) {
		
		if (isPressed)
			return getIcons()[PRESSED_ICON];
		
		// assuming not pressed
		if (this.isEnabled()) {
			if (this.isSelected()) {
				return isRollOver? getIcons()[ROLLOVER_SELECTED_ICON] : getIcons()[SELECTED_ICON]; 
			} else {
				return isRollOver? getIcons()[ROLLOVER_ICON] : getIcons()[DEFAULT_ICON];
			}
		}
		
		// assuming not enabled
		return isSelected() ? getIcons()[DISABLED_SELECTED_ICON] : getIcons()[DISABLED_ICON];
	}
	
	/**
	 * Updates the icon with the peer.
	 * 
	 * The icon to display is selected with selectIcon. If this button does not
	 * have the appropriate icon, then the default icon is used.
	 *
	 * @param isPressed
	 * @param isRollOver
	 * @see #selectIcon(boolean, boolean)
	 */
	protected final void updateIcon(boolean isPressed, boolean isRollOver) {
		Icon icon = this.selectIcon(isPressed, isRollOver);
		this.setPeerImage(null == icon ? getIcon() : icon);
	}
	
    /**
     * Gets the button's default icon.
     *
     * @return
     */
    public Icon getIcon() {
    	if (null == icons)
    		return null;
    	return getIcons()[DEFAULT_ICON];
    }
    
    /**
     * Sets the default icon to show in the button.
     *
     * @param icon
     */
	public void setIcon(Icon icon) {
		getIcons()[DEFAULT_ICON] = icon;
	}
	
	/**
	 * Sets the icon to show when the button is pressed.
	 *
	 * @param icon
	 */
	public void setPressedIcon(Icon icon) {
		getIcons()[PRESSED_ICON] = icon;
	}
	
	/**
	 * Sets the icon to show when the button is disabled.
	 *
	 * @param icon
	 */
	public void setDisabledIcon(Icon icon) {
		getIcons()[DISABLED_ICON] = icon;
	}

	/**
	 * Sets the icon to show when the button is selected,
	 * but not pressed.
	 *
	 * @param icon
	 */
	public void setSelectedIcon(Icon icon) {
		getIcons()[SELECTED_ICON] = icon;
	}
	
	/**
	 * Sets the icon to show when the button is not selected
	 * and there is a mouse over it.
	 *
	 * @param icon
	 */
	public void setRolloverIcon(Icon icon) {
		getIcons()[ROLLOVER_ICON] = icon;
	}
	
	/**
	 * Sets the icon to show when the button is selected and there
	 * is a mouse over it.
	 *
	 * @param icon
	 */
	public void setRolloverSelectedIcon(Icon icon) {
		getIcons()[ROLLOVER_SELECTED_ICON] = icon;
	}
	
	/**
	 * Sets the icon to show when the button is selected and disabled.
	 *
	 * @param icon
	 */
	public void setDisabledSelectedIcon(Icon icon) {
		getIcons()[DISABLED_SELECTED_ICON] = icon;
	}
	
	
	public void setRolloverEnabled(boolean b) {
	}

	public boolean isRolloverEnabled() {
		return true;
	}

	public void setBorderPainted(boolean b) {
	}

	public void setFocusPainted(boolean b) {
	}

	public void setContentAreaFilled(boolean b) {
	}

	
    
    /** Overridden as we use the Button selection for action events */
    protected void registerActionEvents() {
    	
    	// TODO - use this to generate rollover callbacks
    	ppeer.addMouseTrackListener(new MouseTrackListener() {
			
			public void mouseHover(MouseEvent e) {
			//	System.out.println("Mouse hover " + e);
			}
			
			public void mouseExit(MouseEvent e) {
			//	System.out.println("Mouse exit " + e);
				updateIcon(false, false);
			}
			
			public void mouseEnter(MouseEvent e) {
			//	System.out.println("Mouse enter " + e);
				updateIcon(false, true);
			}
		});

    	// When the button is clicked, we generate down() and up() events 
    	ppeer.addMouseListener(new MouseListener() {
    		public void mouseDown(MouseEvent e) {
    			try {
    				updateIcon(true, false);
    				down();
    			} catch (Exception exception) {
    				exception.printStackTrace();
    			}
			}
    		public void mouseUp(MouseEvent e) {
    			// record last click before processing event
				lastClick = e.time;
				try {
					up();
					updateIcon(false, false);
    			} catch (Exception exception) {
    				exception.printStackTrace();
    			}
			}
			public void mouseDoubleClick(MouseEvent e) {}
		});
    	
        // When the button is clicked with the keyboard, we use a key listener
    	// for the down() event, and a selection listener for the up()
    	ppeer.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				updateIcon(true, false);
				down();
			}
			public void keyPressed(KeyEvent e) {}
		});
        ppeer.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
            	if (e.time != lastClick) {
            		// avoid trigger up() when the selection event is part of a mouse click,
            		// which occur at the same time
            		up();
            		updateIcon(false, false);
            	}            
            }
            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {}
        });
    }
    
    /**
     * Sets the horizontal alignment of icon and text in the button.
     * 
     * SwingWT supports all horizontal constants (LEFT, LEADING, CENTER, TRAILING, RIGHT).  
     *
     * @param align
     */
	public void setHorizontalAlignment(final int align) {
		// only update if needed
		if (align != pHAlign) {
			pHAlign = align;
			
			if (hasPeer())
				SwingUtilities.invokeSync(new Runnable() {
					public void run() {
						ppeer.setAlignment(SwingWTUtils.translateSwingAlignmentConstant(align));
							// since SWT does not support vertical alignment, do not even try..
							// (vertical "center" was causing problems with horizontal alignment)
							//| SwingWTUtils.translateSwingAlignmentConstant(pVAlign)); 
					}
				});
		}
	}

	/**
	 * Gets the horizontal alignment of icon and text with respect to the button.
	 *
	 * @return
	 */
	public int getHorizontalAlignment() {
		return pHAlign;
	}

	
	/**
	 * Sets the vertical alignment of icon and text.
	 * 
	 * Swing supports BOTTOM, CENTER and TOP. However, SWT does not support vertical
	 * alignments. Therefore, this method will only keep the constant to return in
	 * the getter, but there is no effect to the GUI.
	 *
	 * @param align
	 */
	public void setVerticalAlignment(final int align) {
		pVAlign = align;
		/*
		SwingUtilities.invokeSync(new Runnable() {
			public void run() {
				if (SwingWTUtils.isSWTControlAvailable(ppeer))
					ppeer.setAlignment(SwingWTUtils.translateSwingAlignmentConstant(align)
							| SwingWTUtils.translateSwingAlignmentConstant(pHAlign));
			}
		});*/
	}

	/**
	 * Gets the constant representing vertical alignment.
	 * 
	 * Note that SwingWT does not support vertical alignments.
	 *
	 * @see #setVerticalAlignment(int)
	 * @return
	 */
	public int getVerticalAlignment() {
		return pVAlign;
	}

	public void setHorizontalTextPosition(int textpos) {
		setHorizontalAlignment(textpos);
	}

	public void setVerticalTextPosition(int textpos) {
		setVerticalAlignment(textpos);
	}

	public int getHorizontalTextPosition() {
		return getHorizontalAlignment();
	}

	public int getVerticalTextPosition() {
		return getVerticalAlignment();
	}
  
   /**
    *  Sends action events to listeners.
    */
     public void processActionEvent(int id) {
         
    	 if (null != actionListeners) {
	         ActionEvent ae = new ActionEvent(this, id, this.getActionCommand());
	         for (int i = 0; i < actionListeners.size(); i++) {
	             ActionListener al = (ActionListener) actionListeners.get(i);
	             al.actionPerformed(ae);
	         }
    	 }
         
         // Send the actions as changes
    	 if (null != changeListeners) {
	         ChangeEvent ce = new ChangeEvent(this);
	         for (int i = 0; i < changeListeners.size(); i++) {
	            ChangeListener cl = (ChangeListener) changeListeners.get(i);
	            cl.stateChanged(ce);
	         }
    	 }
    	 
         // Send item events too
         processItemEvent();
     }
     
    /**
     * Handles firing of Item events for when selection changes
     */
    public void processItemEvent() {
        
        boolean isSelected = this.isSelected();
        
        if (null != itemListeners) {
	        ItemEvent e = new ItemEvent(this, 0, this, (isSelected ? ItemEvent.SELECTED : ItemEvent.DESELECTED));
	        
	        for (int i = 0; i < itemListeners.size(); i++) {
	            ItemListener il = (ItemListener) itemListeners.get(i);
	            il.itemStateChanged(e);
	        }
        }
    }

    /**
     * Creates the default margin for buttons.
     * 
     * This is based on JButton...
     *
     * @return
     */
    protected Insets createDefaultMargin() {
    	// This is the default margin with the standard L&F in Java 1.6, GTK for Linux.
		// It may vary depending on platforms and L&F 
		return new Insets(5, 17, 5, 17);
    }
    
    /**
     * Sets the margin around this button.
     *
     * @param i
     */
    public void setMargin(Insets i) {
    	
    	if (null == i) {
    		this.pMargin = createDefaultMargin();
    	} else {
    		this.pMargin = i;
    	}

    	this.revalidate();
    }
    
    /**
     * Gets this button's margin, if any.
     *
     * @return
     */
    public Insets getMargin() {
        return (null == pMargin) ? 
        		null : 
        		new Insets(pMargin);
    }
    
    /**
     * Gets a new dimension object that represents the margin around this button.
     * 
     * The margin is either the user-set margin or the default margin of 
     * this object.
     *
     * @return
     */
    protected final Dimension getMarginSpace() {
    	Insets i = pMargin;
    	if (null == i) 
    		i = createDefaultMargin();

    	return new Dimension(i.left + i.right, i.top + i.bottom);
    }
    
    /**
     * Returns getText();
     *
     * @return 
     * @see #getText()
     */
	public String getLabel() {
		return getText();
	}

	/**
	 * Sets this button's text.
	 *
	 * @param text
	 * @see #setText(String)
	 */
	public void setLabel(String text) {
		setText(text);
	}
    
	/**
	 * Gets the text to be presented in the button.
	 * 
	 * This text does not contain the mnemonic.
	 *
	 * @return
	 */
	public String getText() {
		return pText;
	}
	
	/**
	 * Sets this button's text. Should be a string without mnemonics.
	 *
	 * @param text
	 */
	public void setText(String text) {
		pText = text;
		setPeerText();
	}
    
    /**
     * Sets the mnemonic character for this button.
     *
     * @param mnemonic
     */
    public void setMnemonic(char mnemonic) { 
    	pMnemonic = mnemonic; 
    	setPeerText(); 
    }
    
    /**
     * Sets the text configured in this button to its peer.
     * 
     * This method assumes that pText and pMnemonic contain the text
     * and mnemonic, respectively. It will then compute the appropriate
     * text to be set in SWT's Button.
     */
    protected final void setPeerText() {
    	
    	// only proceed if there is actually a peer
    	if (! hasPeer())
    		return;
    		
        SwingUtilities.invokeSync(new Runnable() {
        	public void run() {
                
        		
        		String mnemonic = pMnemonic == ' ' ? null : "&" + new String(new char[] { pMnemonic }).toLowerCase();
        				
        		String text = SwingWTUtils.removeHTML(pText);
        		
        		// if pText was modified, it means it has some HTML in it
        		if (null == pText || pText.equals(text))
        			containsHTML = false;
        		else
        			containsHTML = true;
        		
        		if (null == pText && null == mnemonic) {
        			// no mnemonic, no text
        			text = "";
        			
        		} else if (mnemonic == null) {
        			// no mnemonic, but text, defaults to pText
        			
        		} else if (null == pText) {
        			// mnemonic, but no text
        			text = mnemonic;
        			
        		} else {
        			// both mnemonic and text

        			// first cleanup text off mnemonics, if it has any
        			if (text.indexOf("&") != -1)
                        text = SwingWTUtils.replace(text, "&", "");
                    
        			// now find the mnemonic in the text; use lower case for case insensitive match
        			int pos = text.toLowerCase().indexOf(mnemonic);
        			if (pos != -1)
        				text = text.substring(0, pos) + "&" + text.substring(pos, text.length());
        		}
        		
        		// Now finally set the text
        		ppeer.setText(text);
        		
        		// And if there is a mnemonic, make sure that the tooltip is set
        		// TODO - this code should probably not be here, we do not need to always set the tooltip when setting text.
        		if (null != mnemonic)
        			setToolTipText(pToolTipText);
            }
        });
    }
    
    /**
     * Returns the string that will actually be showing in the button.
     *
     * This code should be kept in sync with {@link #setPeerText()}. 
     * 
     * @return
     */
    protected final String getShowingText() {
    	String text = SwingWTUtils.removeHTML(pText);
    	if (null == text)
    		return "";
    	return SwingWTUtils.replace(text, "&", "");
    }
    
    /**
     * Is the button showing any text at all?
     *
     * @return
     */
    protected final boolean hasShowingText() {
    	return ! isEmpty(getShowingText());
    }
    
    /**
     * Simply checks whether this text is empty or not.
     *
     * @param text
     * @return
     */
    protected final boolean isEmpty(String text) {
    	return null == text || text.length() == 0;
    }

    /** Sets the tooltip text used on the component - overridden here,
      * so that if we have a mnemonic, we can tack it on the end. Only applies
      * to JButton and JToggleButton */
    public void setToolTipText(final String text) { 
        pToolTipText = text; 
        
        if (this instanceof JToggleButton || this instanceof JButton)
            if ( pMnemonic != ' ')
                if (pToolTipText.indexOf("(ALT+") == -1)
                    pToolTipText += " (ALT+" + new String(new char[] { pMnemonic }).toUpperCase()+ ")";
                
        if (hasPeer())
	        SwingUtilities.invokeSync(new Runnable() { 
	            public void run() { 
	            	peer.setToolTipText(pToolTipText);
	            }
	        }); 
        
    }

    /**
     * Sets a new model for this button.
     *
     * @param m
     */
	public void setModel(ButtonModel m) {
		pModel = m;
	}

	/**
	 * Gets this button's model
	 *
	 * @return
	 */
	public ButtonModel getModel() {
		return pModel;
	}

    /**
     * Simulates a mouse clicking this button for a standard amount
     * of time.
     */
    public void doClick() {
    	this.doClick(68);
    }
    
    /**
     * Simulates a mouse clicking this button and waiting pressTime miliseconds
     * between pressing and releasing the mouse button.
     *
     * @param pressTime
     */
    public void doClick(int pressTime) {
    	this.down();
    	
    	try {
            Thread.sleep(pressTime);
        } catch(InterruptedException ie) {
        }
    	
    	// change state
    	this.changeState();
    	
    	this.up();
    }
    
    /**
     * This method is called by the button listeners when a mouse button is clicked
     * or a key is pressed. At this point the button still has its "past"
     * state, i.e., it was not selected/unselected yet.
     */
    protected void down() {
    	pModel.setArmed(true);
        pModel.setPressed(true);
    }
    
    /**
     * This method is called by the button listeners when a mouse button or a 
     * key is released. At this point the button already has its "new" state,
     * i.e., it was already selected/unselected.
     */
    protected void up() {
    	
    	this.assertGroupSelection();
    	
    	// processes listeners
    	this.processActionEvent(0);
    	    	
        pModel.setPressed(false);
        pModel.setArmed(false);
    }
    
    /**
     * This method is called by doClick() so that sub-types simulate the state
     * change performed by SWT. When a mouse is clicked, for example, SWT automatically
     * changes the state of the peer. But at doClick() we need to change it
     * manually.
     */
    protected void changeState() {
    }
    
    /**
     * Make sure that the group this button is participating in knows about its
     * state (i.e, its change of state).
     * 
     * @return true if the group selection was asserted because this button participates
     * in a group; false otherwise.
     */
    protected final boolean assertGroupSelection() {
    	if (null != pGroup) {
    		pGroup.setSelected(pModel, this.isSelected());
    		this._setSelected(pGroup.isSelected(pModel));
    		return true;
    	}
    	return false;
    }
    
    //@Override
    public void assertCachedProperties() {
    	super.assertCachedProperties();
    	this.setPeerText();
        this._setSelected(pSelection);
        updateIcon(false, false);
        assertGroupSelection();
    }
    
    /**
     * Gets an array with the action listeners registered to this button.
     *
     * @return
     */
    public ActionListener[] getActionListeners() {
    	if (null == actionListeners)
    		return new ActionListener[0];
    	
    	int size = actionListeners.size();
    	ActionListener[] ret = new ActionListener[size];
    	for (int i = 0; i < size; i++) {
    		ret[i] = (ActionListener) actionListeners.get(i);
    	}
    	return ret;
 	}

    // ButtonModel implementation
    
	/**
	 * Checks the selected flag of this button.
	 *
	 * @return
	 */
    public boolean isSelected() {
    	// Not only get the value, but also update pSelection
    	if (hasPeer())
	    	SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					pSelection = ppeer.getSelection();
				}
			});
    	
        return pSelection;
    }
        
    /**
     * Sets the button's selected property.
     *
     * @param b
     */
    public void setSelected(final boolean b) { 
        this._setSelected(b);
        this.processItemEvent();
    }
    
    /**
     * This implementation should be used internally to
     * avoid calling listeners. This is useful, for example,
     * when setting the selected flag in constructors.
     *
     * @param b
     */
    protected void _setSelected(final boolean b) {
    	pSelection = b;
    	
    	if (hasPeer())
	        SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
	               	ppeer.setSelection(b);
	            }
	        });
    }
    
	/**
	 * Gets the mnemonic character set for this button.
	 *
	 * @return
	 */
    public int getMnemonic() { 
    	return pMnemonic; 
    }
    
    /**
     * Sets the mnemonic character for this button.
     *
     * @param mnemonic
     */
    public void setMnemonic(int mnemonic) {
    	this.setMnemonic((char) mnemonic);
    }
	
	/**
	 * Sets this button's group.
	 *
	 * @param g
	 */
	public void setGroup(ButtonGroup g) {
		pGroup = g;
	}
    
	public boolean isArmed() {
		return pArmed;
	}
	
	public boolean isPressed() {
		return pPressed;
	}
	
	/**
	 * Check whether the mouse is located on top of the button.
	 *
	 * @return
	 */
	public boolean isRollover() {
		if (hasPeer())
			return ((Boolean) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {
					return Boolean.valueOf(ppeer.getBounds().contains(ppeer.getDisplay().getCursorLocation()));
				}
			})).booleanValue();
		
		return pRollover;
	}
	
	public void setArmed(boolean b) {
		this.pArmed = b;
	}
	
	public void setPressed(boolean b) {
		this.pPressed = b;
	}
	
	public void setRollover(boolean b) {
		this.pRollover = b;
	}
	
	/**
	 * Returns an array with the label of this button, or null if
	 * the button is not selected.
	 *
	 * @return
	 */
    public Object[] getSelectedObjects() {
        if (! isSelected())
            return null;
        
        return new String[] { getText() };
    }
	
	// inherited
	//public void setActionCommand(String s);
	
	/** 
	 * If the action command is null, return the button text like Swing 
	 */
	public String getActionCommand() {
		if (actionCommand == null || "".equals(actionCommand))
			return getText();
		else
			return actionCommand;
	}
	
	public void addChangeListener(ChangeListener l) {
		_getChangeListeners().add(l);
	}

	public void removeChangeListener(ChangeListener l) {
		_getChangeListeners().remove(l);
	}
	
	public List/*<ChangeListener>*/ _getChangeListeners() {
		if (null == changeListeners)
			changeListeners = new Vector/*<ChangeListener>*/();
		return changeListeners;
	}

	public void addItemListener(ItemListener l) {
		_getItemListeners().add(l);
	}

	public void removeItemListener(ItemListener l) {
		_getItemListeners().remove(l);
	}
	
	public List/*<ItemListener>*/ _getItemListeners() {
		if (null == itemListeners)
			itemListeners = new Vector/*<ItemListener>*/();
		return itemListeners;
	}
	
	public void addActionListener(ActionListener l) {
		_getActionListeners().add(l);
	}
	
	public void removeActionListener(ActionListener l) {
		_getActionListeners().remove(l);
	}
	
	public List/*<ActionListener>*/ _getActionListeners() {
		if (null == actionListeners)
			actionListeners = new Vector/*<ActionListener>*/();
		return actionListeners;
	}
    
	
	// SIZE
	
	/** 
     * Overridden to calculate non-realized  preferred size.
     */
    protected Dimension calculatePreferredSize() {
    	Insets i = pMargin;
    	if (null == i) 
    		i = createDefaultMargin();

    	Dimension size = new Dimension(i.left + i.right, i.top + i.bottom);
    	
    	// Uses only the default icon in the calculation
    	// TODO - perhaps use the "icon in use"?
		if (hasDefaultIcon()) {
			int heightPadding = Math.max(size.height, 10);
	    	int widthPadding = Math.max(size.width, 10);
	    	Icon icon = getIcon();
    		size.height = icon.getIconHeight() + heightPadding;
    		size.width = icon.getIconWidth() + widthPadding;
    	}
    	
    	String text = getShowingText();
    	boolean hasText = ! isEmpty(text);
    	
    	if (hasText) {
    		// this code assumes that the text is side-by-side with the icon...
    		size.height = Math.max(size.height, SwingWTUtils.getRenderStringHeight(text) + Math.max(i.top + i.bottom, 2));
    		size.width += SwingWTUtils.getRenderStringWidth(text);
    	}
    	
    	adjust(size, hasText, hasDefaultIcon());
    	
        //setSize(size);
        return size;
    }
    
    /**
     * Allow sub-types to perform some adjustments to the calculated preferred size.
     */
    protected void adjust(Dimension size, boolean hasText, boolean hasIcon) {
    	if (hasText) {
    		if (hasIcon)
    			size.width -= 2;
    		else
    			size.width += 2;	
    	}
    }
    
    /**
     * Override computing the preferred size, because we'd rather calculate
     * an estimate ourselves when we have an icon.
     *
     * @return
     */
    protected Dimension computePreferredSize() {
    	return calculatePreferredSize();
    }
    
	// HELPERS
	
	/**
	 * Simple toString to help debugging.
	 *
	 * @return
	 */
	public String toString() {
		return pText;
	}
	
    // ACCESSIBILITY
    
    /**
     * 
     * TODO Comment!!
     * 
     * @author Thiago Tonelli Bartolomei
     */
    protected abstract class AccessibleAbstractButton extends AccessibleJComponent { 
    	//implements AccessibleAction, AccessibleValue, AccessibleText, AccessibleExtendedComponent {
    	
    }
    
 // DEBUG SUPPORT
	
   	/**
   	 * Sub-types can override this to provide a better identifier during debug. 
   	 *
   	 * @return
   	 */
   	public String debugId() {
   		//StringBuilder b = new StringBuilder(super.debugId());
   		StringBuffer b=new StringBuffer(super.debugId());
   		
   		String text = getShowingText();
   		
   		if (null != icons) {
   			b.append(" [*");
   			if (text.length() > 0)
   				b.append(" \"").append(text).append("\"");
   			return b.append("]").toString();
   		}
   		
   		if (text.length() > 0)
   			b.append(" [\"").append(text).append("\"]");
   		
   		return b.toString();
   	}
 }
