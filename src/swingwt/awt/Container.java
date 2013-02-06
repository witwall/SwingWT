/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwt.awt;

import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Listener;

import swingwtx.accessibility.Accessible;
import swingwtx.accessibility.AccessibleContext;
import swingwtx.accessibility.AccessibleRole;
import swingwtx.accessibility.AccessibleStateSet;
import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingWTUtils;

/**
 * Any component that can contain other components.
 * This class maps the Swing layouts/placements to SWT
 */
public class Container extends Component {

    /** The SWT peer this container represents */
    public org.eclipse.swt.widgets.Composite composite = null;

    /** 
     * The layout manager being used to position child components.
     * 
     * Code should never refer directly to this field; getLayout() should
     * be used instead, because it allows sub-types to use other implementations.
     */
    protected LayoutManager layout = null;

    /**
     * Flag indicates that the container already hooked up with the composite
     * to get resize events.
     */
    protected boolean layoutListenerAdded = false;
    
    /** Cache of components waiting to be laid out */
    protected Vector comps = new Vector();

    protected Vector containerListeners = new Vector();
    
    
    // SIZE AND POSITIONING
    
    /**
     * Overrides to, if available, compute the minimum size using
     * the layout manager for this container (unless the user set
     * a minimum size value).
     *
     * @return
     */
    //@Override
	public Dimension getMinimumSize() {
		LayoutManager l = getLayout();
		
		if (null == l || isMinimumSizeSet())
			return super.getMinimumSize();
		
		return l.minimumLayoutSize(this);
	}

	/**
     * Overrides to, if available, compute the preferred size using
     * the layout manager for this container (unless the user set
     * a preferred size value).
     *
     * @return
     */
    //@Override
	public Dimension getPreferredSize() {
		LayoutManager l = getLayout();
		
		if (null == l || isPreferredSizeSet())
			return super.getPreferredSize();
		
		return l.preferredLayoutSize(this);
	}

	/**
     * Overrides to, if available, compute the preferred size using
     * the layout manager for this container.
     *
     * @return
     */
	//@Override
	protected Dimension computePreferredSize() {
		LayoutManager l = getLayout();
		
		if (null == l || null == getComposite())
			return super.computePreferredSize();

		return l.preferredLayoutSize(this);
	}
	
	/**
     * Overrides to, if available, compute the maximum size using
     * the layout manager for this container (unless the user set
     * a maximum size value).
     *
     * @return
     */
    //@Override
	public Dimension getMaximumSize() {
		LayoutManager l = getLayout();
		
		// only LayoutManager2 can compute the maximum size
		if (null == l || isMaximumSizeSet() || ! (l instanceof LayoutManager2))
			return super.getMaximumSize();
		
		return ((LayoutManager2) l).maximumLayoutSize(this);
	}

	/** 
     * Returns the layout used by this container.
     * 
     * Some sub-types may want to override this if a different layout is to
     * be used (e.g., for views or internal frames).
     */
    public LayoutManager getLayout() {
    	return layout;
    }
    
    /**
     * Adds an existing component to the layout manager
     * 
     * This is not part of AWT's API!
     */
    public void addComponentToLayout(Component c) {
    	LayoutManager l = getLayout();
    	
    	if (null != l) {
    		if (l instanceof LayoutManager2)  {
                ((LayoutManager2) l).addLayoutComponent(c, c.layoutModifier);
            } else {
                l.addLayoutComponent(c.getName(), c);
        	}
    	}
    }
  
    /**
     * Forces laying out of this container's child components again.
     */
    //@Override
    public void invalidateImpl() {
    	// Note that here we do not want to use getLayout() because that would
    	// probably cause an infinite loop...
    	if (null != layout && SwingWTUtils.isSWTControlAvailable(composite)) {
        	if (layout instanceof LayoutManager2)
                ((LayoutManager2) layout).invalidateLayout(this);
            
        	layout.layoutContainer(this);
        }
    }
  
    /**
     * I'm totally re-writing this method.  No longer
     * will it only mirror the layouts.  From now on,
     * it will actually use the layout managers to layout
     * the component, not the mirrored SWT layouts.
     * Two big advantages with this approach.  (a) It
     * allows custom layout managers.  (b) It means
     * less work since we don't have to port anything.
     * (yea!)
     *
     * @author Daniel Spiewak
     */
    public void setLayout(LayoutManager l) {
        setLayoutImpl(l);
    }

    /**
     * The actual code that handles assignment of the
     * layout manager.
     *
     * Separated out as setLayout will often get
     * subclassed and my head will explode if I don't
     * do something like this :-)
     */
    protected void setLayoutImpl(LayoutManager l) {
        layout = l;

        if (composite == null || layoutListenerAdded)
            return;

        layoutListenerAdded = true;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {

                // we don't want an SWT layout since we will control the layout ourselves
                composite.setLayout(null);

                // add a resize listener to let us know when to invalidate the layout
                composite.addListener(SWT.Resize, new Listener() {
                	public void handleEvent(org.eclipse.swt.widgets.Event e) {
                		invalidate();
                	}
                });
            }
        });
    }
	
    /*
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                invalidateImpl();
            }
        });
    }*/

    
    // LISTENERS AND EVENTS
    
    protected void processEvent(AWTEvent e) {
        // Can't think of any reason for this to be here other than compile compat
    }
    
    protected void processContainerEvent(swingwt.awt.event.ContainerEvent e) {
        if (containerListeners.size() == 0) return;
        for (int i = 0; i < containerListeners.size(); i++) {
            if (e.getID() == swingwt.awt.event.ContainerEvent.COMPONENT_ADDED)
                ((swingwt.awt.event.ContainerListener) containerListeners.get(i)).componentAdded(e);
            else
                ((swingwt.awt.event.ContainerListener) containerListeners.get(i)).componentRemoved(e);
        }
    }

    public void addContainerListener(swingwt.awt.event.ContainerListener l) {
        containerListeners.add(l);
    }

    public void removeContainerListener(swingwt.awt.event.ContainerListener l) {
        containerListeners.remove(l);
    }


    /** Forces laying out of this container's child components again. */
    public void doLayout() { invalidate(); }
    /** Forces laying out of this container's child components again. */
    public void validate() { invalidate(); }
    //Stubbed
    protected void validateTree() {}
    /** Forces laying out of this container's child components again. */
    public void revalidate() {
    	invalidate(); 
    }

    /**
     * Attempts to ensure that a request to layout a container is only
     * done once whilst adding many components to a container. What we do is
     * basically set a flag to say we need to relayout the container, and if further
     * requests come in the next 50ms, we ignore them. This should really take the heat
     * off the CPU whilst building containers with many objects.
     *
     * Since this generally gets called as we work down the tree doing setSwingWTParent
     * calls, that thread will basically block with no invalidation until all
     * components have been added (what we want) - this is just horribly complex to
     * get your head round, but it works :-)
     *
     * @author Robin Rawson-Tetley
     */
    public void queuedValidate() {
        if (queuedValidateRequest) return; // Already waiting - don't do anything
         queuedValidateRequest = true;
         SwingUtilities.invokeIn(new Runnable() {
             public void run() {
                 invalidate();
                 queuedValidateRequest = false;
             }
         }, 1);
    }
    protected boolean queuedValidateRequest = false;

    /**
     * Addition for layouts requiring object based modifiers
     */
    public void add(swingwt.awt.Component c, Object layoutModifier) {
        if (c == null) 
        	return;
        
        c.layoutModifier = layoutModifier;

        if (layoutModifier instanceof String)
            getLayout().addLayoutComponent((String)layoutModifier, c);

        doAdd(c);
    }
    
    public void add(swingwt.awt.Component c, Object layoutModifier, int index) {
        add(c, layoutModifier);    
    }

    public Component add(String name, swingwt.awt.Component c) {
        add(c,name);
        return c;
    }

    public void dispose() {

        // Dispose of all child components (and their components)
        if (comps != null) {
            for (int i = 0; i < comps.size(); i++) {
                try {
                    ((Component) comps.get(i)).dispose();
                }
                catch (Exception e) {}
            }
            // Ditch the cache
            comps.removeAllElements();
        }

        if (peer != null) super.dispose();
        
	composite = null;
	peer = null;
    }

    /**
     * Removes a component from the the container
     * by destroying the peer.
     */
    public void remove(swingwt.awt.Component c) {
        removeImpl(c);
        invalidate();
    }

    private void removeImpl(swingwt.awt.Component c) {
        comps.remove(c);

    	LayoutManager l = getLayout();
    	
    	if (null != l) 
        	l.removeLayoutComponent(c);
        
        c.componentOnlyDispose();
        c.setComponentRemoved();
        processContainerEvent(new swingwt.awt.event.ContainerEvent(this, swingwt.awt.event.ContainerEvent.COMPONENT_REMOVED, c));
    }

    public void remove(int index) {
        Component c = (Component) comps.get(index);
        remove(c);
    }

    public void removeAll() {
        for (int i = comps.size() - 1; i >= 0; i--) {
            Component c = (Component) comps.get(i);
            removeImpl(c);
        }
        invalidate();
    }

    /** Removes a cached component only */
    public void removeComponentFromCache(Component c) {
        comps.remove(c);
    }


    /** Returns the SWT peer being used for this container */
    public org.eclipse.swt.widgets.Composite getComposite() {
        return composite;
    }


    public ComponentOrientation getComponentOrientation() {
            return ComponentOrientation.getOrientation(Locale.getDefault());
    }

    public Insets getInsets() {
        return new Insets(0,0,0,0);
    }

    
    // COMPONENT HIERARCHY
    

    public Component add(Component c) {
        return doAdd(c);
    }
    
    public Component add(Component c, int index) {
        return add(c);    
    }

    /** Actually does the hard work of adding something to a container */
    public Component doAdd(final Component c) {

        if (c == null) return null;
        final Container me = this;

        // Don't add it to the cache again if this component is already cached
        // - this stops us adding the same component twice.
        if (comps.indexOf(c) == -1) {
            comps.add(c);
            // Register the comonent with the layout if needed
            addComponentToLayout(c);
        }
        
        // Cache us with the child component - this is just so that
        // the hierarchy is visible before the peers are realised
        c.parent = this;        
        
        if (! SwingWTUtils.isSWTControlAvailable(composite)) 
        	return c;

        // Ensure that the setSwingWTParent calls are on the dispatch
        // thread as they go down - object creation should always be
        // on this thread
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {

                try {
                    c.setSwingWTParent(me);
                    c.assertCachedProperties();
                    c.registerEvents();
                    processContainerEvent(new swingwt.awt.event.ContainerEvent(me, swingwt.awt.event.ContainerEvent.COMPONENT_ADDED, c));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                // avoid computing validation while building the GUI
               if (getParent().isVisible())
                	queuedValidate();
            }
        });
        return c;
    }

    
    public Container getParent() {
        return parent;
    }

    /** Called when this gets added. We can set layouts and things
     *  then if necessary. Note that subclasses are responsible
     *  for creating the actual peer and setting the protected
     *  "composite" variable */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {

    	setLayoutImpl(getLayout());

        if (comps.size() > 0) {
            Object[] obs = comps.toArray();
            for (int i = 0; i < obs.length; i++) {
                Component c = (Component) obs[i];
                doAdd(c);
            }
        }
    }
    
    public int getComponentCount() {
        return comps.size();
    }

    public Component getComponent(int i) {
    	if (i < comps.size())
            return (Component) comps.get(i);
    	else
    		return null;
    }

    public Component[] getComponents() {
        Object[] comp = comps.toArray();
        Component[] cps = new Component[comp.length];
        for (int i = 0; i < comp.length; i++) {
            cps[i] = (Component) comp[i];
        }
        return cps;
    }
    
    /**
     * Tries to find the most specific component in this container which
     * contains the point with coordinates x and y.
     *
     * @param x
     * @param y
     * @return null, if this container does not contain this point, or the
     * most specific component that contains the point (i.e., it can be a
     * child of this container).
     * 
     */
    public Component findComponentAt(int x, int y) {
    	if (! this.contains(x, y)) {
    		return null;
    	}
    	Component[] cps = this.getComponents();
    	for (int i = 0; i < cps.length; i ++) {
    		Component c = cps[i];
    		if (c.contains(x, y)) {
    			if (c instanceof Container) {
    				return ((Container) c).findComponentAt(x, y);
    			} else {
    				// the last component 
    				return c;
    			}
    		}
    	}
    	return this;
    }
    
    // FOCUS SUPPORT
    

	// public Set<AWTKeyStroke> getFocusTraversalKeys(int id) 1.5
	public Set getFocusTraversalKeys(int id) {
		// TODO: implement this
		return null;
	}

	// setFocusTraversalKeys(int id, Set<? extends AWTKeyStroke> keystrokes) 1.5
	public void getFocusTraversalKeys(int id, Set keyStrokes) {
		// TODO: implement this
	}

	public void setFocusTraversalPolicy(FocusTraversalPolicy policy) {
		// TODO: implement this
	}

	public FocusTraversalPolicy getFocusTraversalPolicy() {
		// TODO: implement this
		return null;
	}

    
    // ACCESSIBILITY
    
    /**
	 * Sub-types should override this method to create the appropriate
	 * accessible context for the object.
	 *
	 * @return a newly created accessible context
	 */
	protected AccessibleContext createAccessibleContext() {
		return new AccessibleAWTContainer();
	}
	
    /**
     * 
     * TODO Comment!!
     * 
     * @author Thiago Tonelli Bartolomei
     */
    protected class AccessibleAWTContainer extends AccessibleAWTComponent {

		/** 
		 * TODO Comment!!
		 *
		 * @return
		 */
		public int getAccessibleIndexInParent() {
			return 0;
		}

		/** 
		 * TODO Comment!!
		 *
		 * @return
		 */
		public int getAccessibleChildrenCount() {
			return 0;
		}

		/** 
		 * TODO Comment!!
		 *
		 * @param i
		 * @return
		 */
		public Accessible getAccessibleChild(int i) {
			return null;
		}

		/** 
		 * TODO Comment!!
		 *
		 * @return
		 * @throws IllegalComponentStateException
		 */
		public Locale getLocale() throws IllegalComponentStateException {
			return null;
		}

		/** 
		 * TODO Comment!!
		 *
		 * @return
		 */
		public AccessibleRole getAccessibleRole() {
			return null;
		}

		/** 
		 * TODO Comment!!
		 *
		 * @return
		 */
		public AccessibleStateSet getAccessibleStateSet() {
			return null;
		}
	}
    
    // DEBUG SUPPORT
    
	/**
	 * Useful for debugging purposes. Shows this container and all it's
	 * children.
	 */
	public void debug_showContainmentTree() {
		System.out.println("Containment Tree: ====================");
		System.out.println(debugId() + " bounds->" + getBounds().debugId() + " pref->" + getPreferredSize().debugId());
		xdebug_showChildren(this, 1);
	}

	private void xdebug_showChildren(Container c, int level) {
		final String SPACE = "                   ";
		for (int i = 0; i < c.comps.size(); i++) {
			Component d = (Component) c.comps.get(i);
			System.out.println(SPACE.substring(0, level * 2) + d.debugId() + " bounds->" + d.getBounds().debugId() + " pref->" + d.getPreferredSize().debugId());
			
			if (d instanceof Container)
				xdebug_showChildren((Container) d, level + 1);
		}
	}

}
