/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 */


package swingwtx.swing;

import java.util.Vector;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.*;

import swingwt.awt.*;
import swingwt.awt.event.ActionEvent;

/**
 * This class is now not only fully 'swing-y',
 * but also mirrors correctly to SWT.  Also,
 * this class's peer, JToolBar has the option
 * to be contained within a CoolBar.
 *
 * Rob: Unfortunately, this class can never support
 * embedded components the way that Swing does, so we can't use
 * this as the "main" Toolbar implementation. Instead, this allows
 * Swing functionality with actions to the platform toolbar, regular
 * JToolBar will behave like Swing.
 *
 * Daniel: Au cantrare, my pessimistic friend! :)
 * I have done what you say this class can never do.
 * This class really is now fully swing-y and does
 * support placing strange components within itself.
 * The key to the hack is not having the JToolbar class
 * control a ToolBar peer, but a Composite peer.  Then
 * we add ToolBar(s) and Composite(s) as needed to
 * give the native look within the CoolBar or without
 * the CoolBar if nessissary.  You have to admit, this
 * class has earned the right to be the default implementation.
 * Perhaps we could do a JToolBarNonNative?  ;)
 *
 * Rob: Very funny - I'll hack on this a bit and we'll use it
 * as the main implmentation (you win!)
 * 
 * Daniel: I made some minor changes to this class, adding
 * the overloaded methods to add(JCheckBox) and add(JRadioButton)
 * so as to enable the corresponding SWT widget functionality in
 * SwingWT.
 *
 * @author Daniel Spiewak
 * @author Robin Rawson-Tetley
 */
public class JToolBar extends JComponent implements SwingConstants {
    
    /** Last added component - used to determine when to use
     *  ToolBar/Composite */
    protected org.eclipse.swt.widgets.Composite lastAdd;
    
    protected int orientation = HORIZONTAL;
    protected String titleString = "";
    protected boolean coolbar = false;
    protected boolean rollover = false;
    protected Container parent = null;
    protected org.eclipse.swt.widgets.Composite ppeer = null;
    
    private boolean cached = false;
    private Vector cache = new Vector();
    
    /** Used for returning objects in a thread safe manner */
    private Object threadSafeObjectRetValue = null;
    private boolean threadSafeBoolean = false;
    
    public JToolBar() {
        this("", SwingConstants.HORIZONTAL);
    }
    
    public JToolBar(String titleString) {
        this(titleString, SwingConstants.HORIZONTAL);
    }
    
    public JToolBar(int orientation) {
        this("", orientation);
    }
    
    public JToolBar(String titleString, int orientation) {
        this.titleString = titleString;
        this.orientation = orientation;

        // MacOS X default LAF doesn't use toolbar rollovers
	if (!SWT.getPlatform().equals("carbon")) rollover = true;
	
    }
    
    public int getOrientation() {
        return orientation;
    }
    
    /**
     *
     * @param orientation
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }
    
    public boolean isRollover() {
        return rollover;
    }

    /**
     * WARNING: MacOS X by default doesn't use
     * toolbar rollovers.  For the sake of a consistant
     * LAF, don't use them on that platform.
     * 
     * @param b
     */
    public void setRollover(boolean b) {
        rollover = b;
    }

    protected Dimension computePreferredSize() {
        org.eclipse.swt.graphics.Point p = peer.computeSize(-1, -1);
        return new Dimension(p.x, p.y);
    }

    /**
     * @param index
     * @return
     */
    public Component getComponentAtIndex(int index) {
        return (Component) comps.get(index);
    }
    
    /**
     * @param c
     * @return
     */
    public int getComponentIndex(Component c) {
        
        for (int i = 0; i < comps.size(); i++) {
            if (comps.get(i).equals(c)) return i;
        }
        
        return 0;
    }
    
    /**
     * @return insets	The margin of the Composite peer in Insets form.
     */
    public Insets getMargin() {
        org.eclipse.swt.graphics.Rectangle bounds = ppeer.getBounds();
        org.eclipse.swt.graphics.Rectangle client = ppeer.getClientArea();
        Insets insets = new Insets();
        
        insets.left = client.x;
        insets.top = client.y;
        insets.right = bounds.width - (client.width + client.x);
        insets.bottom = bounds.height - (client.height + client.y);
        
        return insets;
    }
    
    /**
     * Sorry, controlled by the native widgets.
     *
     * @param insets
     */
    public void setMargin(Insets insets) {}
    
    /**
     * Overrides normal component invalidation as we are using
     * an SWT layout.
     */
    public void invalidate() {
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                ppeer.layout();
                parent.invalidate();
            }
        });
    }
    
    /*
     * Actually, I want to include these methods to allow for compilation.
     * No one said that they actually have to do anything.  They just have
     * to be here.
     */
    public ToolBarUI getUI() {
        return null;
    }
    
    public void setUI(ToolBarUI ui) {}
    
    /**
     * Controlled by the native widget.  Implemented
     * solely to allow compilation.
     *
     * @param b
     */
    public void setBorderPainted(boolean b) {}
    
    /**
     * I have no clue which platforms support
     * border painting and which don't.
     *
     * @return
     */
    public boolean isBorderPainted() {
        return false;
    }
    
    public boolean isFloatable() {
        return false;
    }
    
    /**
     * I've dropped these. The toolbar simply can't support it, and
     * the one-coolbar per shell thing is too inflexible. The Swing-y
     * way is to use JCoolBar - even though it isn't a real Swing thing.
     * After all, who uses floatable toolbars????
     * 
     * Daniel: Actually, I use floatable ToolBars and I need this
     * functionality.  Since we don't want to put it in JToolBar, we
     * might want to leave the JCoolBar class in place.
     */
    public void setFloatable(final boolean b) {
    }
    
    public void addSeparator() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    cache.add(new JSeparator());
                }
                else {
                    ToolBar peer;
                    if (lastAdd instanceof ToolBar) 
                        peer = (ToolBar) lastAdd;
                    else 
                        peer = new ToolBar(ppeer, orientation);
                    new ToolItem(peer, SWT.SEPARATOR);
                    // We added to a toolbar last
                    lastAdd = peer;
                }
            }
        });
    }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        
        this.parent = parent;
        
        ppeer = new org.eclipse.swt.widgets.Composite(parent.getComposite(), SWT.NO_RADIO_GROUP);        
        lastAdd = ppeer;
        peer = ppeer;
        composite = ppeer;

        RowLayout lay = null;
        if (orientation == SwingConstants.HORIZONTAL)
            lay = new RowLayout(SWT.HORIZONTAL);
        else
            lay = new RowLayout(SWT.VERTICAL);
        lay.fill = true;
        lay.wrap = false;
        ppeer.setLayout(lay);
        
        if (cached) {
            for (int i = 0; i < cache.size(); i++) {
                if (cache.get(i) instanceof Action)
                    add((Action) cache.get(i));
                else if (cache.get(i) instanceof JSeparator)
                    addSeparator();
                else
                    add((Component) cache.get(i));
            }
            cached = false;
        }
    }
    
    /** 
     * This handy overload will map JButtons to Actions
     * for you - making your buttons look ace :)
     */
    public Component add(JButton b) { 
    	return add(b, false);
    }

    public Component add(JButton b, boolean forcePushButton) {      
        
        // If the flag's set, force a push button instead of a 
	// toolbar mapped action
	if (forcePushButton) {
            add((Component) b);
            return b;
	}

        /*
	 * This was a hack for mnemonic buttons that used to work with
	 * SWT 3.0, but no longer does. It forced a push button if we had
	 * a mnemonic but no text.
        if (b.pText != null && (b.pText.length() == 2) && b.pText.startsWith("&")) {
            add((Component) b);
            return b;
        }
	*/
        
        // If we're using MacOSX, and the button requires text, then the platform toolbar 
        // buttons are the nastiest thing I've ever seen - don't use them!
        if (SwingWTUtils.isMacOSX() && (!b.getText().equals(""))) {
            add((Component) b);
            return b;
        }

	// If the toolbar is vertical, the Mac doesn't like it
	// so we have to use a real button instead of a toolitem
	// (effectively a composite with SWT RowLayout)
	if (SwingWTUtils.isMacOSX() && orientation == VERTICAL) {
	    add((Component) b);
	    return b;
	}
        
        // Map the JButton to a fake action to use the platform toolbars
        JButtonMappedAction x = new JButtonMappedAction(b);
        x.putValue(Action.NAME, b.getText());
        x.putValue(Action.SHORT_DESCRIPTION, b.getToolTipText());
        x.putValue(Action.SMALL_ICON, b.getIcon());
        x.putValue(Action.DISABLED_ICON, b.getIcon());
        x.putValue(Action.MNEMONIC_KEY, new Integer(b.getMnemonic()));
        b.setAction(x, false); // Don't make the button add the action as a listener, since the button is never created
        
	// Add the JButton given to the component list anyway for enumeration
        if (!comps.contains(b)) 
		comps.add(b);
        add(x);
        return b;
    }
    
    /**
     * map to a SWT.CHECK style ToolItem
     * 
     * .. but in the meantime lets not break it as a container eh?
     * 
     * @param checkBox
     */
    public Component add(JCheckBox checkBox) {
        add((Component) checkBox);
    	return checkBox;
    }
    
    /**
     * map to a SWT.RADIO style ToolItem
     * 
     * .. but in the meantime lets not break it as a container eh?
     *
     * @param radioButton
     */
    public Component add(JRadioButton radioButton) {
        add((Component) radioButton);
    	return radioButton;
    }
    
    /**
     * We can add a component through
     * this method to the JToolBar and have it go side
     * by side with the ToolItems.
     */
    public Component add(final Component c) {
        
        comps.add(c);
        
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) {
            cached = true;
            cache.add(c);
            return c;
        }
        
        final Container me = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                
                // No need to mess about with toolbars here - 
                // we know this component is mapped to a composite, 
                // so just add the damn thing to that. 
                
                // We could have used the standard container routine
                // here if we didn't need the cache mechanism.
                
                try {
                    // Assign the component onto the container,
                    // set it's base properties and register all its events
                    c.setSwingWTParent(me);
                    c.assertCachedProperties();
                    c.registerEvents();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                
                // This reminds us that we added a component last, and that
                // a new ToolBar will need to be created for any actions.
                lastAdd = ppeer;
            }
        });
        
        return c;
    }
    
    /**
     * Adds an action to the toolbar by creating an SWT ToolItem
     * from the action.
     *
     * @param a
     */
    public JButton add(final Action a) {
        
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) {
            cached = true;

            // If we were already given a JButtonMappedAction,
	    // just add it and return it's mapped button instead
	    if (a instanceof JButtonMappedAction) {
                cache.add(a);
		return ((JButtonMappedAction) a).getJButton();
	    }
	    
	    // Otherwise, create a JButton wrapper for this action
	    // and return that instead
	    JButton b = new JButton(a,true);
	    JButtonMappedAction x = new JButtonMappedAction(b);
	    x.putValue(Action.NAME, b.getText());
	    x.putValue(Action.SHORT_DESCRIPTION, b.getToolTipText());
	    x.putValue(Action.SMALL_ICON, b.getIcon());
	    x.putValue(Action.DISABLED_ICON, b.getIcon());
	    x.putValue(Action.MNEMONIC_KEY, new Integer(b.getMnemonic()));
	    x.setOrigAction(a); 
	    b.setAction(x, false); // Don't make the button add the action as a listener, since the button is never created
	    cache.add(x);
	    return b;
        }
        
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
        
                ToolBar toolbar;	// Create a new toolbar where necessary
                final ToolItem peer;
                
                // If the Action was created from a JButton, then we only need to return
                // that, rather than waste resources creating a new one
                JButton btnWrapper;
                if (a instanceof JButtonMappedAction) {
                    btnWrapper = ((JButtonMappedAction) a).getJButton();
		    btnWrapper.setAction(((JButtonMappedAction) a).getOrigAction());
		}
                else {
                    // Create a new JButton wrapper for the action
                    btnWrapper = new JButton(a, false);
                                                    // ^ Since this wrapper will also never be on screen, don't map action events 
                                                    // (see AbstractButton.setAction(Action, bool) for more info)
		}

                if (lastAdd instanceof ToolBar) {
                    toolbar = (ToolBar) lastAdd;		// if there is a previous toolbar, use it!
		}
                else {
                    toolbar = new ToolBar(ppeer, 
                        ((orientation == SwingConstants.HORIZONTAL ? SWT.HORIZONTAL : SWT.VERTICAL) | SWT.FLAT)); // create a new one if we need to
		}

                if (a.getValue(Action.DROP_MENU) != null) {	
                    peer = new ToolItem(toolbar, SWT.DROP_DOWN);
                } else {
                    peer = new ToolItem(toolbar, SWT.PUSH);
                }
                
                // Set it against the JButton wrapper
                btnWrapper.pSWTToolButton = peer;
                btnWrapper.pSWTToolButton.setEnabled(btnWrapper.isEnabled());

                if (a.getValue(Action.SMALL_ICON) != null)
                    peer.setHotImage(SwingWTUtils.getSWTImageFromSwingIcon(btnWrapper, ((Icon) a.getValue(Action.SMALL_ICON))));
                if (a.getValue(Action.NAME) != null)
                    peer.setText((String) a.getValue(Action.NAME));
                if (a.getValue(Action.SHORT_DESCRIPTION) != null)
                    peer.setToolTipText((String) a.getValue(Action.SHORT_DESCRIPTION));

                if (rollover)
	            if (a.getValue(Action.DISABLED_ICON) != null)
		        peer.setImage(SwingWTUtils.getSWTImageFromSwingIcon(btnWrapper, 
			    ((Icon) a.getValue(Action.DISABLED_ICON))));
		
                if (!SwingWTUtils.isMacOSX())		// OS X tool items don't use grey disabled items
                        if (a.getValue(Action.DISABLED_ICON) != null)
                            peer.setImage(SwingWTUtils.getSWTImageFromSwingIcon(btnWrapper, ((Icon) a.getValue(Action.DISABLED_ICON))));

                if (a.getValue(Action.DROP_MENU) != null) {
                    final JPopupMenu jmenu = (JPopupMenu) a.getValue(Action.DROP_MENU);

                    // we have to create a new pop-up menu with the specified components in the current shell
                    try {
                    	jmenu.setSwingWTParent(ppeer.getShell());
                        
                        // Rob: Add components to popup and map their events here?
                        
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // this is how to display a pop-up menu (sort of)
                    peer.addListener(SWT.Selection, new Listener() {
                        public void handleEvent(org.eclipse.swt.widgets.Event e) {
                            if (e.detail == SWT.ARROW) {
                                Menu menu = jmenu.menu;
                                org.eclipse.swt.graphics.Rectangle rect = peer.getBounds();
                                org.eclipse.swt.graphics.Point pt = new org.eclipse.swt.graphics.Point(rect.x, rect.y + rect.height);
                                pt = ppeer.toDisplay(pt);
                                menu.setLocation(pt);
                                menu.setVisible(true);
                            }
                        }
                    });
                }
               
	        final JButton btnWrapper2 = btnWrapper;
                peer.addSelectionListener(new SelectionAdapter() {
                    public void widgetSelected(SelectionEvent e) {
                        a.actionPerformed(new ActionEvent(btnWrapper2, 0));
                    }
                });

                threadSafeObjectRetValue = btnWrapper;
                
                lastAdd = toolbar;
            }
        });
        
        return (JButton) threadSafeObjectRetValue;
    }
    
    /**
     * Temparary storage for the ToolBarUI class.
     * THIS ISN'T PERMANENT!
     *
     * @author Daniel Spiewak
     */
    public class ToolBarUI {
        
    }
}

