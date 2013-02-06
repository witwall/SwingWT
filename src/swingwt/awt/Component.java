/*
 SwingWT
 Copyright(c)2003-2008, R. Rawson-Tetley

 For more information on distributing and using this program, please
 see the accompanying "COPYING" file.

 Contact me by electronic mail: bobintetley@users.sourceforge.net

 */

package swingwt.awt;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.Locale;
import java.util.Set;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import swingwt.awt.dnd.DropTarget;
import swingwt.awt.event.ActionEvent;
import swingwt.awt.event.ActionListener;
import swingwt.awt.event.ComponentEvent;
import swingwt.awt.event.ComponentListener;
import swingwt.awt.event.EventListener;
import swingwt.awt.event.FocusEvent;
import swingwt.awt.event.FocusListener;
import swingwt.awt.event.HierarchyListener;
import swingwt.awt.event.InputMethodEvent;
import swingwt.awt.event.InputMethodListener;
import swingwt.awt.event.KeyEvent;
import swingwt.awt.event.KeyListener;
import swingwt.awt.event.MouseEvent;
import swingwt.awt.event.MouseListener;
import swingwt.awt.event.MouseMotionListener;
import swingwt.awt.event.MouseWheelEvent;
import swingwt.awt.event.MouseWheelListener;
import swingwt.awt.font.TextHitInfo;
import swingwt.awt.image.ImageObserver;
import swingwt.awt.peer.ComponentPeer;
import swingwtx.accessibility.AccessibleContext;
import swingwtx.swing.JEditorPane;
import swingwtx.swing.JScrollPane;
import swingwtx.swing.JTable;
import swingwtx.swing.JTextArea;
import swingwtx.swing.JTree;
import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingUtilities.SwingWTRunnable;
import swingwtx.swing.SwingWTUtils;

/**
 * 
 * Tag class for AWT compatibility - subclass JSWTComponent has all the good
 * stuff.
 * 
 */
public class Component implements ImageObserver, MenuContainer {

	/** The SWT peer this component is representing */
	public Control peer = null;

	/**
	 * If a default instance of Component is used, then we map a paintable
	 * composite canvas as AWT does
	 */
	protected org.eclipse.swt.widgets.Composite ppeer = null;

	/** The name of this component */
	protected String componentName = this.getClass().getSimpleName();

	/** The graphics configuration */
	protected static GraphicsConfiguration graphicsConfig = new GraphicsConfiguration();

	/** The parent container */
	protected Container parent = null;

	/** Used when caching objects with layouts */
	public Object layoutModifier = null;

	/** Cache value */
	protected boolean pVisible = true;

	/** Cache value */
	protected boolean pEnabled = true;

	/** Cache value */
	protected String pToolTipText = "";

	/** Cache value */
	protected swingwt.awt.Color pBackground = null;

	/** Cache value */
	protected swingwt.awt.Color pForeground = null;

	/** Cache value */
	protected Dimension pSize = null;

	/** Cache value */
	protected Dimension pPrefSize = null;

	/** Cache value */
	protected Dimension pMinSize = null;

	/** Cache value */
	protected Dimension pMaxSize = null;

	protected Dimension cachedComputedPreferredSize = null;

	/** Cache value */
	protected swingwt.awt.Point pLocation = null;

	/** Cache value */
	protected swingwt.awt.Font pFont = null;

	/** Cache value */
	protected Object pLayoutData = null;

	/** Cache value */
	protected Cursor pCursor = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);

	/**
	 * Whether paint events give direct access to component or use an image
	 * buffer and render in one go
	 */
	protected boolean pDoubleBuffered = false;

	protected DropTarget dropTarget;

	protected float alignmentX = LEFT_ALIGNMENT;
	protected float alignmentY = CENTER_ALIGNMENT;

	/** Action Listener objects */
	protected Vector actionListeners = new Vector();

	/** Mouse Listener objects */
	protected Vector mouseListeners = new Vector();

	/** Mouse Wheel Listener objects */
	protected Vector mouseWheelListeners = new Vector();

	/** Mouse Motion Listener objects */
	protected Vector mouseMotionListeners = new Vector();

	/** Keyboard Listener objects */
	protected Vector keyListeners = new Vector();

	/** Focus Listener objects */
	protected Vector focusListeners = new Vector();

	/** Component Listener objects */
	protected Vector componentListeners = new Vector();

	/** Input Method Listeners objects */
	protected Vector inputMethodListeners = new Vector();

	/** Hierarchy Listeners objects */
	protected Vector hierarchyListeners = new Vector();

	/**
	 * Used with TableLayout to determine whether this component should use up
	 * extra width
	 */
	protected boolean consumeAvailableWidth = false;

	/**
	 * Used with TableLayout to determine whether this component should use up
	 * extra height
	 */
	protected boolean consumeAvailableHeight = false;

	/** Action command */
	protected String actionCommand = "";

	/** Whether a calling program has set the font for this component */
	protected boolean isUserSetFont = false;

	/** Whether this component can get the focus */
	protected boolean focusable = true;

	/** Default adapter for Action Events */
	protected org.eclipse.swt.events.MouseListener swingWTDefaultActionEventAdapter = null;

	/** All-in-one event adapter for components */
	protected Component.SWTEventAdapter swingWTEventAdapter = null;

	/** The accessible context */
	protected AccessibleContext accessibleContext = null;

	/** The component's locale */
	protected Locale locale = null;

	/** List of PopupMenus for this Component */
	Vector popupMenus;

	/** Constants */
	public static final float TOP_ALIGNMENT = 0.0f;

	public static final float CENTER_ALIGNMENT = 0.5f;

	public static final float BOTTOM_ALIGNMENT = 1.0f;

	public static final float LEFT_ALIGNMENT = 0.0f;

	public static final float RIGHT_ALIGNMENT = 1.0f;
	
	/** Override in subclasses to paint on the component */
	public void paint(Graphics g) {
	}

	/**
	 * Not normally in Component - member of JComponent, implemented here to
	 * save a load of class casting around and to call into paint(Graphics) -
	 * this is the first point of call for repainting..
	 */
	protected void paintComponent(Graphics g) {
		paint(g);
	}

	// abstract void paintBackground(int x, int y, int width, int height);

	/**
	 * Once a parent component receives an "add" call for a child, this being
	 * the child, this should be called to tell us to instantiate the peer and
	 * load in any cached properties.
	 */
	public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
		ppeer = new org.eclipse.swt.widgets.Canvas(parent.getComposite(), 0);
		peer = ppeer;
		this.parent = parent;
	}

	/** Returns the SWT peer */
	public Control getSWTPeer() {
		return peer;
	}

	public ComponentPeer getPeer() {
		return null;
	}

	public void add(PopupMenu popupMenu) {
		if (popupMenus == null)
			popupMenus = new Vector();
		popupMenus.add(popupMenu);

		// PopupMenus can only belong to one component
		if (popupMenu.parent != null)
			popupMenu.parent.remove(popupMenu);
		popupMenu.parent = this;
	}

	public void remove(MenuComponent menuComponent) {
		if (popupMenus != null)
			popupMenus.remove(menuComponent);
	}

	/** Makes the component visible */
	public void show() {
		setVisible(true);
	}

	/** Makes the component invisible */
	public void hide() {
		setVisible(false);
	}

	/** Change the visibility of the component */
	public void setVisible(final boolean b) {
		pVisible = b;
		
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.setVisible(b);
				}
			});
	}

	/** Returns true if the component is visible */
	public boolean isVisible() {
		if (hasPeer())
			return ((Boolean) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {	
					return Boolean.valueOf(peer.isVisible());
				}
			})).booleanValue();
		return pVisible;
	}

	/** Returns true if the component is visible */
	public boolean isShowing() {
		return isVisible(); // && null != peer && peer.isVisible();
	}
	
	public boolean isFocusOwner() {
		
		if (hasPeer())
			return ((Boolean) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {	
					return Boolean.valueOf(peer.isFocusControl());
				}
			})).booleanValue();
		
		return false;
	}

	/**
	 * Returns if the component is opaque or not - Opaque components paint their
	 * entire region, while non-Opaque components paint only the pixels they
	 * need and let the remainder show through This function is hardcoded to
	 * false to match default behaviour in java.awt, components should override
	 * this method if this isn't the case.
	 * 
	 * @return true if the component is opaque
	 */
	public boolean isOpaque() {
		return false;
	}

	/**
	 * @return The component's locale if it has one. If not, the parent's is
	 *         returned.
	 */
	public Locale getLocale() {
		if (locale != null) {
			return locale;
		}
		Container parent = this.parent;
		if (parent == null) {
			throw new IllegalComponentStateException(
					"This component must have a parent in order to determine its locale");
		} else {
			return parent.getLocale();
		}
	}

	/** Set the component's locale */
	public void setLocale(Locale l) {
		locale = l;
	}

	/** Change the enabled state of the component */
	public void setEnabled(final boolean b) {
		pEnabled = b;
		
		if (hasPeer())
			SwingUtilities.invokeAsync(new Runnable() {
				public void run() {
					peer.setEnabled(b);
				}
			});
	}

	/** Returns true if the component is enabled */
	public boolean isEnabled() {
		return pEnabled;
	}

	/** Requests the component receives the focus */
	public void requestFocus() {
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.setFocus();
				}
			});
	}

	/** Forces keyboard focus to the component */
	public void grabFocus() {
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.forceFocus();
				}
			});
	}

	public DropTarget getDropTarget() {
		return dropTarget;
	}

	public void setDropTarget(DropTarget dropTarget) {
		this.dropTarget = dropTarget;
	}

	public void repaint(int x, int y, int width, int height) {
		repaint(0, x, y, width, height);
	}

	public void repaint(long tm, int x, int y, int width, int height) {
		// TODO
		repaint();
	}

	/** Redraws the component */
	public void repaint() {
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					// If this component is drawable peer (ie. has
					// no descendant overriding it), then we assume the
					// developer
					// wanted to paint on it, so we call down to paint() again
					if (ppeer != null) {
						Graphics g = getGraphics();
						paintComponent(g);
						g.dispose();
					} else {
						// Otherwise, tell the SWT peer widget to redraw
						peer.redraw();
					}
				}
			});
	}
	
	// COLORS

	/** Returns true if the background was explicitly set */
	public boolean isBackgroundSet() {
		return pBackground != null;
	}

	/** Returns true if the foreground was explicitly set */
	public boolean isForegroundSet() {
		return pForeground != null;
	}

	private void updatePeerBackgroundSWT() {
		if (isOpaque() && isBackgroundSet())
			peer.setBackground(getBackground().getSWTColor());
		else if (parent instanceof swingwtx.swing.JRootPane && parent.isOpaque() && parent.isBackgroundSet())
			peer.setBackground(parent.getBackground().getSWTColor());
		else
			peer.setBackground(null);
	}

	protected void updatePeerBackground() {
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					updatePeerBackgroundSWT();
				}
			});
	}

	/** Returns the background colour of the component */
	public swingwt.awt.Color getBackground() {
		return pBackground;
	}

	/** Sets the background colour of the component */
	public void setBackground(final swingwt.awt.Color c) {
		pBackground = c;
		updatePeerBackground();
	}

	/** Returns the foreground colour of the component */
	public swingwt.awt.Color getForeground() {
		if (hasPeer())
			return (Color) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {
					return new Color(peer.getForeground());
				}
			});	
		
		if (null == pForeground)
			return Color.BLACK;
		
		return pForeground;
	}

	/** Sets the foreground colour of the component */
	public void setForeground(final swingwt.awt.Color c) {
		pForeground = c;
		
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.setForeground(c.getSWTColor());
				}
			});
	}
	
	/**
	 * Return a graphics context for drawing on this object. Note that only
	 * instances of JComponent are allowed
	 */
	public Graphics getGraphics() {
		if (hasPeer())
			return (Graphics) SwingUtilities.sync(new SwingWTRunnable() {
					public Object exec() {
						return new SWTGraphics2DRenderer(
								new org.eclipse.swt.graphics.GC(peer), true);
					}
			});
		return null;
	}

	
	
	// SIZES AND POSITIONING
	
	/**
	 * Returns the size of the peer.
	 * 
	 * The value comes directly from the peer if it has been realized, or it
	 * is computed from the cached size value.
	 *
	 * Not part of AWT's API!
	 * 
	 * @return
	 */
	public Dimension getPeerSize() {
		if (hasPeer())
    		return (Dimension) SwingUtilities.sync(new SwingWTRunnable() {
    			public Object exec() {
    				return new Dimension(peer.getSize());
    			}
    		});

    	// No peer, compute size...
		if (pSize != null)
			return (Dimension) pSize.clone();
		
		return new Dimension();
	}

	/**
	 * Returns the bounds of the peer.
	 * 
	 * The value comes directly from the peer if it has been realized, or it
	 * is computed from the cached location and size values.
	 *
	 * Not part of AWT's API!
	 *
	 * @return
	 */
    protected final Rectangle getPeerBounds() {
    	if (hasPeer())
    		return (Rectangle) SwingUtilities.sync(new SwingWTRunnable() {
    			public Object exec() {
    				return new Rectangle(peer.getLocation(), peer.getSize());
    			}
    		});

    	// No peer, compute bounds...
    	Rectangle rect = new Rectangle(0, 0, 0, 0);
    	if (pLocation != null)
    		rect.setLocation(pLocation);
			
		if (pSize != null)
			rect.setSize(pSize);
		
		return rect;
	}
	
    /**
     * Directly sets the bounds of the peer.
     * 
     * This method assumes that the peer exists and that it is executed in the SWT thread.
     *
     * @param x
     * @param y
     * @param width
     * @param height
     */
    protected final void setPeerBounds(int x, int y, int width, int height) {
    	//assert(null != peer);
		cachedComputedPreferredSize = null;
		peer.setBounds(x, y, width, height);
	}
    
	/**
	 * Returns the size of this component.
	 * 
	 * If the component is embedded inside a JScrollPane component, the larger of
	 * the preferredSize/peers actual size. This works rather nicely for
	 * handling layouts inside a scrollbar as we can simply offset a large
	 * composite according to the scrollbar positions inside JScrollPane.
	 * 
	 * One note - if the component is JTree, JTable, JTextArea or JEditorPane,
	 * then we return the size as usual since these components handle their own
	 * scrolling and we don't want the JScrollPane behaving weirdly.
	 * 
	 * @return {@link #getPeerSize()}, unless the component is embedded into a
	 * JScrollPane component, in which case a larger area may be returned.
	 * @see #getPeerSize()
	 * @see #getPreferredSize()
	 */
	public Dimension getSize() {
		
		if (parent instanceof JScrollPane 
				&& (! (this instanceof JTable))
				&& (! (this instanceof JTextArea)) 
				&& (!(this instanceof JTree))
				&& (! (this instanceof JEditorPane))) {
			
			Dimension returnDim = getPreferredSize();
			Dimension peers = getPeerSize();
			
			// Get max between preferred and peer sizes
			if (returnDim.width < peers.width)
				returnDim.width = peers.width;
			if (returnDim.height < peers.height)
				returnDim.height = peers.height;
			
			return returnDim;
			
		} else {
			
			return getPeerSize();
		}
	}

	/**
	 * Overloaded version of getSize() that uses the passed in dimension to put
	 * the size in to prevent allocation of another on the heap.
	 * 
	 * NB: Still creates a new dimension due to how SwingWT works so there is no
	 * benefit to using this method.
	 * 
	 * @Deprecated we should remove this method because it does not make much sense...
	 */
	public Dimension getSize(Dimension toFill) {
		Dimension d = getSize();
		toFill.width = d.width;
		toFill.height = d.height;
		return toFill;
	}
	
	/** 
	 * Simply returns {@link #getSize()}.
	 * 
	 * @see #getSize()
	 */
	public Dimension size() {
		return getSize();
	}

	/**
	 * Sets the actual size of the peer.
	 *
	 * This method interprets a value of -1 to mean "the current value".
	 * 
	 * @param width
	 * @param height
	 */
	public void setSize(final int width, final int height) {
		setSize(new Dimension(width, height));
	}

	/**
	 * Sets the actual size of the peer.
	 * 
	 * This method interprets a value of -1 in width or height to mean "the current value".
	 *
	 * @param d
	 */
	public void setSize(final Dimension d) {
		pSize = d;
		
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					cachedComputedPreferredSize = null;
					org.eclipse.swt.graphics.Rectangle rec = peer.getBounds();
					
					// a value of -1 is interpreted as "do not change the current value"
					// This allows for faster setWidth and setHeight
					if (d.width != -1)
						rec.width = d.width;
					if (d.height != -1)
						rec.height = d.height;
					
					// note that peer.setSize would reset the location to 0,0
					peer.setBounds(rec);
					/*
					 * It seems more efficient to call peer.getBounds than to use 2 getLocation() calls
					setPeerBounds(
							getLocation().x, 
							getLocation().y,
							d.width, 
							d.height);
							*/
				}
			});
	}
	
	/** 
	 * Returns the width of the component, as calculated by {@link #getSize()}.
	 * 
	 * @return the component's width
	 */
	public int getWidth() {
		return getSize().width;
	}

	/** 
	 * Returns the height of the component, as calculated by {@link #getSize()}.
	 * 
	 * @return the component's height
	 */
	public int getHeight() {
		return getSize().height;
	}

	/** 
	 * Sets the width of the component. 
	 */
	public void setWidth(int width) {
		setSize(width, -1);
	}

	/** 
	 * Sets the height of the component. 
	 */
	public void setHeight(int height) {
		setSize(getWidth(), height);
	}
	
    /**
	 * Returns the bounds of the component.
	 * 
	 * The default implementation of this method simply calls getPeerBounds(),
	 * which will return the bounds of the peer, if available, or a computed
	 * value if not.
	 * 
	 * @see #getPeerBounds()
	 */
	public Rectangle getBounds() {
		return getPeerBounds();
	}
	
	/**
	 * Sets the physical bounds of the component.
	 *
	 * @param r
	 * @see #setBounds(int, int, int, int)
	 */
	public void setBounds(Rectangle r) {
		setBounds(r.x, r.y, r.width, r.height);
	}
	
	/**
	 * Sets the physical bounds of the component. 
	 * 
	 * This method will move the component to the specified location (wrt its parent) and
	 * will resize it.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void setBounds(final int x, final int y, final int width, final int height) {

		pSize = new Dimension(width, height);
		pLocation = new Point(x, y);

		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					setPeerBounds(x, y, width, height);
				}
			});
	}
	
	/**
	 * Computes the position of this component in the screen.
	 * 
	 * If the component has not been created yet, the origin is returned.
	 */
	public Point getLocationOnScreen() {
		
		if (hasPeer())
			return (Point) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {
					// get location relative to parent
					org.eclipse.swt.graphics.Point parent = peer.getLocation();
					// translate from parent to "null", which represents the screen
					org.eclipse.swt.graphics.Point l = SwingWTUtils.getDisplay().map(peer.getParent(), null, parent.x, parent.y);
					return new Point(l.x, l.y);
				}
			});
		
		return new Point(0, 0);
	}

	/** 
	 * Returns the component's location with respect to its parent.
	 * 
	 * If the peer is not realized, use cached location or the origin.
	 */
	public Point getLocation() {
		if (hasPeer())
    		return (Point) SwingUtilities.sync(new SwingWTRunnable() {
    			public Object exec() {
    				return new Point(peer.getLocation());
    			}
    		});

		// No peer, compute location...
    	if (pLocation != null)
    		return new Point(pLocation);
		
		return new Point();
	}
	
	/** 
	 * Sets the component's location in it's container.
	 * 
	 * @see #setLocation(Point)
	 */
	public void setLocation(int x, int y) {
		setLocation(new Point(x, y));
	}
	
	/** 
	 * Sets the component's location in it's container 
	 */
	public void setLocation(final Point p) {
		pLocation = p;
		
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					cachedComputedPreferredSize = null;
					org.eclipse.swt.graphics.Rectangle rec = peer.getBounds();
					
					rec.x = p.x;
					rec.y = p.y;
					
					peer.setBounds(rec);
				}
			});
	}
	
	/** 
	 * Returns the component's X position in it's parent container.
	 * 
	 * @see #getLocation()
	 */
	public int getX() {
		return getLocation().x;
	}

	/** 
	 * Returns the component's Y position in it's parent container.
	 * 
	 * @see #getLocation()
	 */
	public int getY() {
		return getLocation().y;
	}

	/**
	 * Causes this component and its parents to be invalidated.
	 * 
	 * This method will cause all components that are ancestors of this component
	 * to be invalidated. This means that they will receive a {@link #invalidateImpl()}
	 * call each, from top to bottom.
	 */
	public void invalidate() {
		cachedComputedPreferredSize = null;
		SwingWTUtils.invalidate(this);
	}
	
	/**
	 * This is called when a sub-component is invalidated.
	 * 
	 * Container classes should override this method to recompute the layout
	 * of its children.
	 */
	public void invalidateImpl() {
	}

	/**
	 * Sets the preferred size of the object for layout purposes.
	 *
	 * @param d
	 */
	public void setPreferredSize(Dimension d) {
		pPrefSize = d;
	}
	
	/**
	 * Checks whether the user set a preferred size to this component.
	 *
	 * @return
	 */
	public boolean isPreferredSizeSet() {
		return null != pPrefSize;
	}
	
	/**
	 * Sets the maximum size of the object for layout purposes.
	 *
	 * @param d
	 */
	public void setMaximumSize(Dimension d) {
		pMaxSize = d;
	}
	
	/**
	 * Checks whether the user set a maximum size to this component.
	 *
	 * @return
	 */
	public boolean isMaximumSizeSet() {
		return null != pMaxSize;
	}

	/**
	 * Returns the maximum size of the component.
	 * 
	 * This will be the value set by the user as maximum size or
	 * the current value for {@link #getPreferredSize()}.
	 *
	 * @return
	 */
	public Dimension getMaximumSize() {
		if (null == pMaxSize)
			return getPreferredSize();
		return pMaxSize;
	}

	/**
	 * Returns the minimum size of the component.
	 * 
	 * This will be the value set by the user as minimum size or
	 * the current value for {@link #getPreferredSize()}.
	 *
	 * @return
	 */
	public Dimension getMinimumSize() {
		if (null == pMinSize)
			return getPreferredSize();
		return pMinSize;
	}

	/**
	 * Deprecated shorthand to {@link #getMinimumSize()}.
	 *
	 * @return
	 */
	public Dimension minimumSize() {
		return getMinimumSize();
	}

	/**
	 * Sets the minimum size of the object for layout purposes.
	 *
	 * @param d
	 */
	public void setMinimumSize(Dimension d) {
		pMinSize = d;
	}
	
	/**
	 * Checks whether the user set a minimum size to this component.
	 *
	 * @return
	 */
	public boolean isMinimumSizeSet() {
		return null != pMinSize;
	}
	
	/**
	 * Deprecated shorthand to {@link #getPreferredSize()}.
	 *
	 * @return
	 */
	public Dimension preferredSize() {
		return getPreferredSize();
	}
	
	/**
	 * Returns the preferred size the component would like to be displayed at -
	 * very important for proper layout managers.
	 * 
	 * @author Robin Rawson-Tetley
	 */
	public Dimension getPreferredSize() {
		
		// Since preferred size is fulfilling it's "real" role now, it needs to be
		// the height the component would prefer to be displayed at. This is either
		// what the user set, or (if not set), a computed estimate
		// 
		if (null != pPrefSize)
			return pPrefSize;
		
		if (hasPeer()) {
			if (null == cachedComputedPreferredSize)
				cachedComputedPreferredSize = computePreferredSize();
			return cachedComputedPreferredSize;
		}
			
		// The component has not yet been realised, so we are going to
		// have to make some guesses based on the type
		return calculatePreferredSize();
	}

	/**
	 * This is called by getPreferredSize() when we want the peer to compute
	 * it's own size. This base method delegates to the peer, but some
	 * components may want to override this value hence the reason we have a
	 * separate method here.
	 * 
	 * NOTE THAT THIS ROUTINE IS DIFFERENT FROM calculatePreferredSize() - this
	 * routine is called by getPreferredSize() when none has been set by the
	 * user and allows the peer to compute it's size, calculatePreferredSize()
	 * is used when no peer has been created and we need to guess an appropriate
	 * size.
	 */
	protected Dimension computePreferredSize() {
		return (Dimension) SwingUtilities.sync(new SwingWTRunnable() {
			public Object exec() {
				org.eclipse.swt.graphics.Point p = peer.computeSize(-1, -1);
				return new Dimension(p.x, p.y);
			}
		});
	}

	/**
	 * Override in subclasses to return the preferred size for a given
	 * (non-realised) component.
	 * 
	 * Note that you should use setSize() to whatever this routine returns once
	 * called to prevent inconsistency with null layout managers.
	 * 
	 * Also note that this is different from the computePreferredSize() method,
	 * which is used for calculating the preferredSize of a realised component
	 * (this one is for unrealised components)
	 */
	protected Dimension calculatePreferredSize() {
		Dimension size = new Dimension(150, 50);
		// Setting the size actually causes a lot of recalculation and loops. It 
		// does not make sense to do this here. 
		// TODO we have to experiment with "null layout managers" to see for workarounds 
		//setSize(size);
		return size;
	}

	/**
	 * Gets a number between 0 and 1 indicating the alignment of this component
	 * in the X axis of its container. A value of 0.5 means center.
	 *
	 * @return
	 */
	public float getAlignmentX() {
		return alignmentX;
	}

	/**
	 * Gets a number between 0 and 1 indicating the alignment of this component
	 * in the Y axis of its container. A value of 0.5 means center.
	 *
	 * @return
	 */
	public float getAlignmentY() {
		return alignmentY;
	}

	/**
	 * Sets the value of this component's X alignment.
	 * 
	 * The value must be between 0 and 1 and will be rounded if needed.
	 *
	 * @param val
	 * @see #getAlignmentX()
	 */
	public void setAlignmentX(float val) {
		if (val < 0)
			alignmentX = 0;
		else if (val > 1)
			alignmentX = 1;
		else
			alignmentX = val;
	}

	/**
	 * Sets the value of this component's Y alignment.
	 *
	 * The value must be between 0 and 1 and will be rounded if needed.
	 *
	 * @param val
	 * @see #setAlignmentY(float)
	 */
	public void setAlignmentY(float val) {
		if (val < 0)
			alignmentY = 0;
		else if (val > 1)
			alignmentY = 1;
		else
			alignmentY = val;
	}
	
	/**
	 * Checks whether the point identified by (x, y) lies inside this component's area.
	 * 
	 * Note that x and y are taken with respect to this component's origin (i.e., its
	 * parent's position).
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean contains(int x, int y) {
		Dimension d = this.getSize();
		return (x >= 0 && y >= 0) && (x < d.width && y < d.height);
	}

	/**
	 * Checks whether this point is located inside this component.
	 *
	 * @param point
	 * @return
	 * @see #contains(int, int)
	 */
	public boolean contains(Point point) {
		return contains(point.x, point.y);
	}

	/**
	 * A deprecated shorthand to {@link #contains(int, int)}.
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean inside(int x, int y) {
		return contains(x, y);
	}
	
	// END of SIZE AND POSITIONING
	
	
	
	
	/** FIXME: NOT IMPLEMENTED */
	public void setFocusTraversalKeysEnabled(boolean b) {
	}
	/** FIXME: NOT IMPLEMENTED */
	//TODO: Fix
	public void setFocusTraversalKeys(int id, Set keystrokes) {
	}

	/** Creates and returns an image */
	public Image createImage(int width, int height) {
		swingwt.awt.image.BufferedImage i = new swingwt.awt.image.BufferedImage(
				width, height, swingwt.awt.image.BufferedImage.TYPE_INT_RGB);
		return i;
	}

	/** Creates and returns a volatile image */
	public swingwt.awt.image.VolatileImage createVolatileImage(int width, int height) {
		swingwt.awt.image.VolatileImage i = new swingwt.awt.image.VolatileImage(
				width, height, swingwt.awt.image.BufferedImage.TYPE_INT_RGB);
		return i;
	}

	/** Returns the font metrics for the given font */
	public swingwt.awt.FontMetrics getFontMetrics(swingwt.awt.Font f) {
		return Toolkit.getDefaultToolkit().getFontMetrics(f);
	}

	/** Returns the Font used on the component */
	public swingwt.awt.Font getFont() {
		if (hasPeer())
			return (Font) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {
					return new Font(peer.getFont());
				}
			});
		//if (returnFont == null)
		//	returnFont = Font.getStubFont();
		return pFont;
	}

	/** Sets the font used by the component */
	public void setFont(final swingwt.awt.Font f) {
		this.isUserSetFont = (null != f);
		this.pFont = f;
		
		if (null != f && hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.setFont(f.getSWTFont());
				}
			});
	}

	/**
	 * Returns true if a calling process has set the font on this component. So
	 * other parts of SwingWT know when to override the standard font as part of
	 * your system theme.
	 */
	public boolean hasSetFont() {
		return isUserSetFont;
	}

	/** Returns the tooltip text used on the component */
	public String getToolTipText() {
		return pToolTipText;
	}

	/** Sets the tooltip text used on the component */
	public void setToolTipText(final String text) {
		pToolTipText = text;
		
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.setToolTipText(SwingWTUtils.removeHTML(text));
				}
			});
	}

	/** Sets the focus to the next component as if tab was pressed */
	public void transferFocus() {
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.traverse(SWT.TRAVERSE_TAB_NEXT);
				}
			});
	}

	/** Sets the focus to the next component as if shift+tab was pressed */
	public void transferFocusBackward() {
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.traverse(SWT.TRAVERSE_TAB_PREVIOUS);
				}
			});
	}

	/** NOT IMPLEMENTED: FIXME - focusCycleRoot not done */
	public void transferFocusUpCycle() {
	}

	/** NOT IMPLEMENTED */
	public void validate() {
	}

	/** NOT IMPLEMENTED */
	protected void enableEvents(long mask) {
	}

	/** NOT IMPLEMENTED */
	protected void firePropertyChange(String propertyName, Object oldVal, Object newVal) {
	}

	/** Returns the action command for this component */
	public String getActionCommand() {
		return actionCommand;
	}

	/** Sets the action command for this component */
	public void setActionCommand(String command) {
		actionCommand = command;
	}



	/** Returns the component's parent */
	public Container getParent() {
		return parent;
	}

	/** Returns the components name */
	public String getName() {
		return componentName;
	}

	/** Sets the components name */
	public void setName(String newName) {
		componentName = newName;
	}

	/** Sets the components cursor */
	public void setCursor(final swingwt.awt.Cursor c) {
		pCursor = c;
		
		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					try {
						if (c != null)
							peer.setCursor(c.getSWTCursor());
						else 
							peer.setCursor(null);
							
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
	}

	/** Returns true if the component can receive the focus */
	public boolean isFocusable() {
		return focusable;
	}

	/** Set whether the component can be focused */
	public void setFocusable(boolean b) {
		focusable = b;
	}

	/** Returns the components cursor */
	public swingwt.awt.Cursor getCursor() {
		return pCursor;
	}

	/** Gets the Toolkit for this component */
	public Toolkit getToolkit() {
		return Toolkit.getDefaultToolkit();
	}

	/** Gets the GraphicsConfiguration for this component */
	public GraphicsConfiguration getGraphicsConfiguration() {
		return graphicsConfig;
	}

	public ComponentOrientation getComponentOrientation() {
		return ComponentOrientation.LEFT_TO_RIGHT;
	}

	public void setComponentOrientation(ComponentOrientation o) {
	}

	/** Destroys the component and stops all listeners */
	public void dispose() {
		componentOnlyDispose();
	}

	/**
	 * Because dispose() can be overridden to do more, calling this routine
	 * guarantees that destruction of the component occurs (and nothing else).
	 */
	public void componentOnlyDispose() {

		if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					peer.dispose();
				}
			});

		peer = null;
		ppeer = null;

		mouseListeners.removeAllElements();
		mouseWheelListeners.removeAllElements();
		mouseMotionListeners.removeAllElements();
		actionListeners.removeAllElements();
		focusListeners.removeAllElements();
		keyListeners.removeAllElements();
		componentListeners.removeAllElements();
		inputMethodListeners.removeAllElements();
		hierarchyListeners.removeAllElements();
	}

	/** Add an <code>ActionListener</code> to this component */
	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}

	/** Remove an <code>ActionListener</code> from the component */
	public void removeActionListener(ActionListener l) {
		actionListeners.remove(l);
	}

	/** Add an <code>ComponentListener</code> to this component */
	public void addComponentListener(ComponentListener l) {
		componentListeners.add(l);
	}

	/** Remove an <code>ComponentListener</code> from the component */
	public void removeComponentListener(ComponentListener l) {
		componentListeners.remove(l);
	}

	/** Add a <code>MouseListener</code> to this component */
	public void addMouseListener(MouseListener l) {
		mouseListeners.add(l);
	}

	/** Remove an <code>MouseListener</code> from the component */
	public void removeMouseListener(MouseListener l) {
		mouseListeners.remove(l);
	}

	/** Add a <code>MouseWheelListener</code> to this component */
	public void addMouseWheelListener(MouseWheelListener l) {
		mouseWheelListeners.add(l);
	}

	/** Remove a <code>MouseWheelListener</code> to this component */
	public void removeMouseWheelListener(MouseWheelListener l) {
		mouseWheelListeners.remove(l);
	}

	/** Add a <code>MouseMotionListener</code> to this component */
	public void addMouseMotionListener(MouseMotionListener l) {
		mouseMotionListeners.add(l);
	}

	/** Remove an <code>MouseMotionListener</code> from the component */
	public void removeMouseMotionListener(MouseMotionListener l) {
		mouseMotionListeners.remove(l);
	}

	/** Add a <code>KeyListener</code> to this component */
	public void addKeyListener(KeyListener l) {
		keyListeners.add(l);
	}

	/** Remove an <code>KeyListener</code> from the component */
	public void removeKeyListener(KeyListener l) {
		keyListeners.remove(l);
	}

	/** Add a <code>KeyListener</code> to this component */
	public void addFocusListener(FocusListener l) {
		focusListeners.add(l);
	}

	/** Remove an <code>KeyListener</code> from the component */
	public void removeFocusListener(FocusListener l) {
		focusListeners.remove(l);
	}

	/** Add an <code>InputMethodListener</code> to this component */
	public void addInputMethodListener(InputMethodListener l) {
		inputMethodListeners.add(l);
	}

	/** Remove an <code>InputMethodListener</code> to this component */
	public void removeInputMethodListener(InputMethodListener l) {
		inputMethodListeners.remove(l);
	}

	/** Add a <code>HierarchyListener</code> to this component */
	public void addHierarchyListener(HierarchyListener l) {
		hierarchyListeners.add(l);
	}

	/** Remove a <code>HierarchyListener</code> to this component */
	public void removeHierarchyListener(HierarchyListener l) {
		hierarchyListeners.remove(l);
	}

	// TODO: handle event dispatching here?
	public void dispatchEvent(AWTEvent awtEvent) {
	}

	// TODO: do something here?
	public void addNotify() {
	}

	// TODO: do something here?
	public void removeNotify() {
	}

	public EventListener[] getListeners(Class listenerType) {
		if (listenerType.equals(ActionListener.class)) {
			return createListenerArrayFromVector(actionListeners);
		}
		if (listenerType.equals(MouseListener.class)) {
			return createListenerArrayFromVector(mouseListeners);
		}
		if (listenerType.equals(KeyListener.class)) {
			return createListenerArrayFromVector(keyListeners);
		}
		if (listenerType.equals(FocusListener.class)) {
			return createListenerArrayFromVector(focusListeners);
		}
		return null;
	}

	protected EventListener[] createListenerArrayFromVector(Vector listeners) {
		EventListener[] arr = new EventListener[listeners.size()];
		for (int i = 0; i < listeners.size(); i++) {
			arr[i] = (EventListener) listeners.get(i);
		}
		return arr;
	}

	/**
	 * Sends action events to listeners
	 */
	public void processActionEvent(int id) {
		ActionEvent ae = new ActionEvent(this, id, this.getActionCommand());
		for (int i = 0; i < actionListeners.size(); i++) {
			ActionListener al = (ActionListener) actionListeners.get(i);
			al.actionPerformed(ae);
		}
	}

	public void processComponentEvent(ComponentEvent e) {
		for (int i = 0; i < componentListeners.size(); i++) {
			if (e.getID() == ComponentEvent.COMPONENT_HIDDEN)
				((ComponentListener) componentListeners.get(i))
						.componentHidden(e);
			if (e.getID() == ComponentEvent.COMPONENT_MOVED)
				((ComponentListener) componentListeners.get(i))
						.componentMoved(e);
			if (e.getID() == ComponentEvent.COMPONENT_RESIZED)
				((ComponentListener) componentListeners.get(i))
						.componentResized(e);
			if (e.getID() == ComponentEvent.COMPONENT_SHOWN)
				((ComponentListener) componentListeners.get(i))
						.componentShown(e);
		}
	}

	/**
	 * Sends mouse events to component listeners
	 */
	public void processMouseEvent(MouseEvent e) {
		computeTarget(e);
		for (int i = 0; i < mouseListeners.size(); i++) {
			MouseListener ml = (MouseListener) mouseListeners.get(i);
			if (e.eventID == MouseEvent.MOUSE_CLICKED)
				ml.mouseClicked(e);
			else if (e.eventID == MouseEvent.MOUSE_ENTERED)
				ml.mouseEntered(e);
			else if (e.eventID == MouseEvent.MOUSE_EXITED)
				ml.mouseExited(e);
			else if (e.eventID == MouseEvent.MOUSE_PRESSED)
				ml.mousePressed(e);
			else if (e.eventID == MouseEvent.MOUSE_RELEASED)
				ml.mouseReleased(e);
		}
	}
	
	/**
	 * Sends mouse motion events to component listeners
	 */
	public void processMouseMotionEvent(MouseEvent e) {
		computeTarget(e);
		for (int i = 0; i < mouseMotionListeners.size(); i++) {
			MouseMotionListener ml = (MouseMotionListener) mouseMotionListeners
					.get(i);
			if (e.getButton() != 0) {
				ml.mouseDragged(e);
			} else {
				ml.mouseMoved(e);
			}
		}
	}

	/**
	 * Searches the available frames for the component
	 * which will be targeted by this mouse event.
	 * 
	 * This is important to generate callbacks for sub-types
	 * during the search, but we currently do not use the
	 * target object.
	 *
	 * @param e
	 */
	protected void computeTarget(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		Frame[] frames = Frame.getFrames();
		for (int i = 0; i < frames.length; i++) {
			Frame frame = frames[i];
			Object target = frame.findComponentAt(x, y);
			if (null != target) {
				return;
			}
		}
	}
	
	/**
	 * Sends mouse wheel events to component listeners
	 */
	protected void processMouseWheelEvent(MouseWheelEvent e) {
		for (int i = 0; i < mouseWheelListeners.size(); i++) {
			MouseWheelListener mwl = (MouseWheelListener) mouseWheelListeners
					.get(i);
			mwl.mouseWheelMoved(e);
		}
	}

	/**
	 * Sends KeyEvents to component listeners
	 */
	public void processKeyEvent(KeyEvent e) {
		for (int i = 0; i < keyListeners.size(); i++) {
			KeyListener ml = (KeyListener) keyListeners.get(i);
			if (e.getID() == KeyEvent.TYPED)
				ml.keyTyped(e);
			if (e.getID() == KeyEvent.RELEASED)
				ml.keyReleased(e);
			if (e.getID() == KeyEvent.PRESSED)
				ml.keyPressed(e);
		}
	}

	/**
	 * Sends Focus Events to component listeners
	 */
	protected void processFocusEvent(FocusEvent e) {
		for (int i = 0; i < focusListeners.size(); i++) {
			FocusListener fl = (FocusListener) focusListeners.get(i);
			if (e.getID() == FocusEvent.FOCUS_GAINED)
				fl.focusGained(e);
			if (e.getID() == FocusEvent.FOCUS_LOST)
				fl.focusLost(e);
		}
	}

	/**
	 * Sends Input Method Events to component listeners
	 */
	public void processInputMethodEvent(InputMethodEvent e) {
		for (int i = 0; i < inputMethodListeners.size(); i++) {
			InputMethodListener iml = (InputMethodListener) inputMethodListeners
					.get(i);
			if (e.getID() == InputMethodEvent.CARET_POSITION_CHANGED)
				iml.caretPositionChanged(e);
			if (e.getID() == InputMethodEvent.INPUT_METHOD_TEXT_CHANGED)
				iml.inputMethodTextChanged(e);
		}
	}

	public void processPaintEvent(org.eclipse.swt.events.PaintEvent e) {

		// If the double-buffered flag is set, give the component an image to
		// paint
		// on and we'll render that onto the GC instead.
		if (pDoubleBuffered) {
			org.eclipse.swt.graphics.Image img = new org.eclipse.swt.graphics.Image(
					SwingWTUtils.getDisplay(), e.width, e.height);
			org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC(
					img);
			// Call out to the paint method
			paintComponent(new SWTGraphics2DRenderer(gc));
			// Copy the buffered image onto the real gc
			e.gc.drawImage(img, 0, 0);
			// Destroy our temporary gc and image
			gc.dispose();
			img.dispose();
		} else {
			paintComponent(new SWTGraphics2DRenderer(e.gc));
		}
	}

	/**
	 * If the component has just been added to a container, but properties were
	 * set in the meantime, this routine gets called by JSWTComposite to set the
	 * cached properties.
	 */
	public void assertCachedProperties() {
		if (! hasPeer())
			return;
		
		peer.setToolTipText(pToolTipText);
		updatePeerBackgroundSWT();
		if (pForeground != null)
			peer.setForeground(pForeground.getSWTColor());
		if (pFont != null)
			peer.setFont(pFont.getSWTFont());
		if (pSize != null && pLocation != null) {
			setPeerBounds(pLocation.x, pLocation.y, pSize.width, pSize.height);
		} else if (pSize != null)
			setPeerBounds(getLocation().x, getLocation().y,
					pSize.width, pSize.height);
		else if (pLocation != null)
			setPeerBounds(pLocation.x, pLocation.y, getSize().width, getSize().height);
		if (pCursor != null
				&& !pCursor.equals(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR)))
			peer.setCursor(pCursor.getSWTCursor());
		if (pLayoutData != null)
			peer.setLayoutData(pLayoutData);
		peer.setVisible(pVisible);
		peer.setEnabled(pEnabled);
	}

	/**
	 * When the component has been added to a container, this routine gets
	 * called to tell it to set up SWT listeners for all its events. We can map
	 * these then to the AWT-style events
	 */
	public void registerEvents() {

		if (! hasPeer())
			return;

		if (swingWTEventAdapter == null)
			swingWTEventAdapter = new Component.SWTEventAdapter(this);

		registerActionEvents();
		registerComponentEvents();
		registerMouseEvents();
		registerKeyEvents();
		registerFocusEvents();
		registerTraversalEvents();
		registerPaintEvents();
		registerModifyEvents();
		registerExtendedModifyEvents();
	}

	/**
	 * Adapter class to map SWT events to SwingWT ones. We use this for 2
	 * reasons: 1. Less anonymous inner classes - only needs one to handle all
	 * events. 2. We can guarantee there will only be one SwingWT adapter
	 */
	private class SWTEventAdapter implements
			org.eclipse.swt.events.PaintListener,
			org.eclipse.swt.events.TraverseListener,
			org.eclipse.swt.events.FocusListener,
			org.eclipse.swt.events.MouseListener,
			org.eclipse.swt.events.MouseTrackListener,
			org.eclipse.swt.events.MouseMoveListener,
			org.eclipse.swt.events.KeyListener,
			org.eclipse.swt.events.ControlListener,
			org.eclipse.swt.custom.ExtendedModifyListener,
			org.eclipse.swt.events.ModifyListener {

		/** The component this class is acting as an event adapter for */
		private Component parent = null;

		public SWTEventAdapter(Component parent) {
			this.parent = parent;
		}

		public void paintControl(org.eclipse.swt.events.PaintEvent e) {
			processPaintEvent(e);
		}

		public void keyTraversed(org.eclipse.swt.events.TraverseEvent e) {
			processTraverseEvent(e);
		}

		public void focusGained(org.eclipse.swt.events.FocusEvent e) {

			// Store a reference to the component with the focus in
			// SwingWTUtils so it can be easily accessed by other code that
			// needs to look up the focused component
			SwingWTUtils.setFocusedComponent(parent);

			FocusEvent ev = new FocusEvent(parent, FocusEvent.FOCUS_GAINED);
			processFocusEvent(ev);
		}

		public void focusLost(org.eclipse.swt.events.FocusEvent e) {
			FocusEvent ev = new FocusEvent(parent, FocusEvent.FOCUS_LOST);
			processFocusEvent(ev);
		}

		public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
			int modifiers = MouseEvent.getModifiersFromButtons(e.button, e.stateMask);

			MouseEvent ev = new MouseEvent(parent);
			ev.clickCount = 1;
			ev.eventID = MouseEvent.MOUSE_PRESSED;
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			ev.setModifiers(modifiers);

			processMouseEvent(ev);
		}

		public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
			MouseEvent ev = new MouseEvent(parent);
			ev.clickCount = 2;
			ev.eventID = MouseEvent.MOUSE_CLICKED;
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			processMouseEvent(ev);
		}

		public void mouseUp(org.eclipse.swt.events.MouseEvent e) {

			/*
			 * This is crude and probably better not to have
			 * in - we should use swt dnd instead.
			MouseEvent ev = new MouseEvent(parent);
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			ev.eventID = MouseEvent.MOUSE_DRAGGED;
			ev.setModifiers(MouseEvent.getModifiersFromSWTMask(e.stateMask));
			processMouseMotionEvent(ev);
			*/

			MouseEvent ev = new MouseEvent(parent);
			ev.clickCount = 1;
			ev.eventID = MouseEvent.MOUSE_RELEASED;
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			ev.setModifiers(MouseEvent.getModifiersFromButtons(e.button, e.stateMask));
			processMouseEvent(ev);

			ev = new MouseEvent(parent);
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			ev.clickCount = 1;
			ev.setModifiers(MouseEvent.getModifiersFromButtons(e.button, e.stateMask));
			ev.eventID = MouseEvent.MOUSE_CLICKED;
			processMouseEvent(ev);
		}

		public void mouseEnter(org.eclipse.swt.events.MouseEvent e) {
			MouseEvent ev = new MouseEvent(parent);
			ev.clickCount = 0;
			ev.eventID = MouseEvent.MOUSE_ENTERED;
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			processMouseEvent(ev);
		}

		public void mouseExit(org.eclipse.swt.events.MouseEvent e) {
			MouseEvent ev = new MouseEvent(parent);
			ev.clickCount = 0;
			ev.eventID = MouseEvent.MOUSE_EXITED;
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			processMouseEvent(ev);
		}

		public void mouseHover(org.eclipse.swt.events.MouseEvent e) {
			MouseEvent ev = new MouseEvent(parent);
			ev.eventID = MouseEvent.MOUSE_ENTERED;
			ev.setX(e.x);
			ev.setY(e.y);
			ev.setButton(e.button);
			processMouseMotionEvent(ev);
		}

		public void mouseMove(org.eclipse.swt.events.MouseEvent e) {
			MouseEvent ev = new MouseEvent(parent);

			ev.setModifiers(MouseEvent.getModifiersFromSWTMask(e.stateMask));

			if ((e.stateMask & SWT.BUTTON_MASK) != 0) {
				ev.eventID = MouseEvent.MOUSE_DRAGGED;
				// awt expects the modifiers to be set to determine
				// which buttons are being pushed during the drag.
				// the call above will do this.
				// the button number is undefined in this case
				// however setting the button number was done before
				// by swingwt so just incase it is needed it is
				// set to 1 here
				ev.setButton(1);
			} else {
				ev.eventID = MouseEvent.MOUSE_MOVED;
				ev.setButton(0);
			}
			ev.setX(e.x);
			ev.setY(e.y);
			processMouseMotionEvent(ev);
		}

		public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
			KeyEvent ev = new KeyEvent(parent, KeyEvent.PRESSED, e.time,
					KeyEvent.translateSWTModifiers(e.stateMask), KeyEvent
							.translateSWTKey(e.keyCode), e.character);
			processKeyEvent(ev);

			// consumption
			if (e.doit)
				e.doit = !ev.isConsumed();
		}

		public void keyReleased(org.eclipse.swt.events.KeyEvent e) {
			KeyEvent ev = new KeyEvent(parent, KeyEvent.RELEASED, e.time,
					KeyEvent.translateSWTModifiers(e.stateMask), KeyEvent
							.translateSWTKey(e.keyCode), e.character);
			processKeyEvent(ev);

			// consumption
			if (e.doit)
				e.doit = !ev.isConsumed();
		}

		public void controlMoved(
				org.eclipse.swt.events.ControlEvent controlEvent) {
			processComponentEvent(new ComponentEvent(parent,
					ComponentEvent.COMPONENT_MOVED));
		}

		public void controlResized(
				org.eclipse.swt.events.ControlEvent controlEvent) {
			processComponentEvent(new ComponentEvent(parent,
					ComponentEvent.COMPONENT_RESIZED));
		}

		public void modifyText(ExtendedModifyEvent modifyEvent) {

			// Modified text uses the INPUT_METHOD_TEXT_CHANGED event. caret
			// change? not sure yet
			int id = InputMethodEvent.INPUT_METHOD_TEXT_CHANGED;

			if (id > 0) {

				String fullText = "";
				if (peer instanceof StyledText)
					fullText = ((StyledText) peer).getText();
				if (peer instanceof Text)
					fullText = ((Text) peer).getText();
				if (peer instanceof CCombo)
					fullText = ((CCombo) peer).getText();
				if (peer instanceof Combo)
					fullText = ((Combo) peer).getText();

				int caretPosition = modifyEvent.start + modifyEvent.length;

				AttributedString attributedString = new AttributedString(
						fullText);
				AttributedCharacterIterator text = attributedString
						.getIterator(null, modifyEvent.start, caretPosition);
				TextHitInfo textHitInfo = TextHitInfo.trailing(caretPosition);
				InputMethodEvent inputMethodEvent = new InputMethodEvent(
						parent, id, text, modifyEvent.length, textHitInfo,
						textHitInfo);
				processInputMethodEvent(inputMethodEvent);

			} else {
				throw new IllegalComponentStateException(
						"Unknown ExtendedModifyState error");
			}
		}

		public void modifyText(ModifyEvent modifyEvent) {
			// HTF do you use this event?
		}

	}

	protected void registerPaintEvents() {
		peer.removePaintListener(swingWTEventAdapter);
		peer.addPaintListener(swingWTEventAdapter);
	}

	protected void registerTraversalEvents() {
		peer.removeTraverseListener(swingWTEventAdapter);
		peer.addTraverseListener(swingWTEventAdapter);
	}

	protected void registerFocusEvents() {
		peer.removeFocusListener(swingWTEventAdapter);
		peer.addFocusListener(swingWTEventAdapter);
	}

	protected void registerComponentEvents() {
		peer.removeControlListener(swingWTEventAdapter);
		peer.addControlListener(swingWTEventAdapter);
	}

	protected void registerMouseEvents() {
		peer.removeMouseListener(swingWTEventAdapter);
		peer.removeMouseTrackListener(swingWTEventAdapter);
		peer.removeMouseMoveListener(swingWTEventAdapter);
		peer.addMouseListener(swingWTEventAdapter);
		peer.addMouseTrackListener(swingWTEventAdapter);
		peer.addMouseMoveListener(swingWTEventAdapter);
	}

	protected void registerKeyEvents() {
		peer.removeKeyListener(swingWTEventAdapter);
		peer.addKeyListener(swingWTEventAdapter);
	}

	protected void registerActionEvents() {
		/*
		 * Should this be here? It triggers action events on mouse clicks if
		 * (swingWTDefaultActionEventAdapter == null)
		 * swingWTDefaultActionEventAdapter = new
		 * org.eclipse.swt.events.MouseListener() { public void
		 * mouseDown(org.eclipse.swt.events.MouseEvent e) {
		 * processActionEvent(0); } public void
		 * mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
		 * processActionEvent(0); } public void
		 * mouseUp(org.eclipse.swt.events.MouseEvent e) { } };
		 */

		if (swingWTDefaultActionEventAdapter != null) {
			peer.removeMouseListener(swingWTDefaultActionEventAdapter);
			peer.addMouseListener(swingWTDefaultActionEventAdapter);
		}
	}

	/**
	 * Handle ModifyEvents (maps to ImputMethodEvents) by checking the final
	 * derivation of this Component. Unusual way to do it, but since
	 * InputMethodEvents are handled in the base Component, unlike SWT, it's the
	 * most effective way to maintain backwards compatibility.
	 * 
	 * This method is being used for speed - the alternatives are to use
	 * peer.getClass().getMethod(...) or peer.getClass().getMethods() and search
	 * for required methods. getMethod() has the problem of throwing an
	 * exception, which will eat up resources given how often this code will be
	 * called. getMethods(), likewise, will usually return a very large list,
	 * taking a long time to traverse.
	 * 
	 * This means if SWT is updated to have new components return ModifyEvents,
	 * it will need to be added here.
	 */
	protected void registerModifyEvents() {
		if (peer instanceof StyledText || peer instanceof Text
				|| peer instanceof CCombo || peer instanceof Combo) {
			boolean success = false;
			try {
				Method addMethod = peer.getClass().getMethod(
						"addModifyListener",
						new Class[] { ModifyListener.class });
				Method removeMethod = peer.getClass().getMethod(
						"removeModifyListener",
						new Class[] { ModifyListener.class });
				removeMethod.invoke(peer, new Object[] { swingWTEventAdapter });
				addMethod.invoke(peer, new Object[] { swingWTEventAdapter });

				success = true;
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}

			if (!success)
				throw new IllegalComponentStateException(
						"SWT peer missing required methods! (?)");
		}
	}

	/**
	 * Same as above, but for ExtendedModifyEvents
	 */
	protected void registerExtendedModifyEvents() {
		if (peer instanceof StyledText) {
			//boolean success = false;
			try {
				Method addMethod = peer.getClass().getMethod(
						"addExtendedModifyListener",
						new Class[] { ModifyListener.class });
				Method removeMethod = peer.getClass().getMethod(
						"removeExtendedModifyListener",
						new Class[] { ModifyListener.class });
				removeMethod.invoke(peer, new Object[] { swingWTEventAdapter });
				addMethod.invoke(peer, new Object[] { swingWTEventAdapter });

				//success = true;
			} catch (NoSuchMethodException e) {
			} catch (SecurityException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}

			// if (!success)
			// throw new IllegalComponentStateException("SWT peer missing
			// required methods! (?)");
		}
	}

	/**
	 * Called when a traversal key is pressed by the user. This routine
	 * determines whether we should use the custom focus manager if one is
	 * avaliable, or just let it go.
	 */
	protected void processTraverseEvent(org.eclipse.swt.events.TraverseEvent e) {

		// If it is ENTER that's pressed, then that is actually an ActionEvent
		// in the Swing world!
		if (e.keyCode == SWT.CR) {
			processActionEvent(0);
		}

		// If there is no focus manager - forget it - use whatever the
		// hell SWT uses (creation order I think)
		if (swingwtx.swing.FocusManager.getCurrentManager() == null) {
			return;
		}

		// Focus previous component
		if (((e.keyCode == SWT.TAB) && ((e.stateMask & SWT.SHIFT)) != 0)
				|| (e.detail == SWT.TRAVERSE_TAB_PREVIOUS)) {
			e.doit = false;
			swingwtx.swing.FocusManager.getCurrentManager()
					.focusPreviousComponent(this);
		}

		// Focus next component
		if (((e.keyCode == SWT.TAB) && ((e.stateMask & SWT.SHIFT)) == 0)
				|| (e.detail == SWT.TRAVERSE_TAB_NEXT)) {
			e.doit = false;
			swingwtx.swing.FocusManager.getCurrentManager().focusNextComponent(
					this);
		}

	}

	/**
	 * When a container removes a component, it calls this method. You can
	 * override this in your subclass to perform any specific action you need to
	 * take when that component is removed.
	 */
	protected void setComponentRemoved() {
	}

	/**
	 * Here for compatibility - I'm not sure what it's for. Doesn't do anything
	 * anyway. :-)
	 */
	public boolean imageUpdate(Image img, int infoflags, int x, int y,
			int width, int height) {
		// Graphics g = img.getGraphics();
		return false;
	}

	/** Returns true if this component has the focus */
	public boolean hasFocus() {
		if (hasPeer())
			return ((Boolean) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {
					return Boolean.valueOf(peer.isFocusControl());
				}
			})).booleanValue();
		return false;
	}

	/**
	 * For compatibility with 3rd party layout managers - SwingWT doesn't need
	 * this as all laying out is marshalled onto a queued single thread
	 */
	protected Object LOCK = new Object();

	/**
	 * For compatibility with 3rd party layout managers - SwingWT doesn't need
	 * this as all laying out is marshalled onto a queued single thread
	 */
	public Object getTreeLock() {
		return LOCK;
	}

	public boolean isAncestorOf(Component comp) {
		boolean returnValue = false;
		Container parent2 = comp.getParent();
		while (parent2 != null) {
			if (this == parent2) { // use equals?
				returnValue = true;
				break;
			}
			parent2 = parent2.getParent();
		}
		return returnValue;
	}
	// TODO: Implement this
	// Empty for the momement  ( until print in swingwt is implemented )
	public void printAll(Graphics g)
	{
		
	}
	// TODO: Implement this
	// Empty for the momement  ( until print in swingwt is implemented )
	public void print(Graphics g)
	{
		
	}
	public FocusListener[] getFocusListeners()
	{
		int s = focusListeners.size();
		FocusListener[] f = new FocusListener[s];
		for (int i = 0;i<focusListeners.size();i++)
		{
			f[i]=(FocusListener)focusListeners.elementAt(i);
		}
		return f;
	}
	public MouseListener[] getMouseListeners()
	{
		int s = mouseListeners.size();
		MouseListener[] f = new MouseListener[s];
		for (int i = 0;i<mouseListeners.size();i++)
		{
			f[i]=(MouseListener)mouseListeners.elementAt(i);
		}
		return f;
	}
	public KeyListener[] getKeyListeners()
	{
		int s = keyListeners.size();
		KeyListener[] f = new KeyListener[s];
		for (int i = 0;i<keyListeners.size();i++)
		{
			f[i]=(KeyListener)keyListeners.elementAt(i);
		}
		return f;
	}
	public MouseMotionListener[] getMouseMotionListeners()
	{
		int s = mouseMotionListeners.size();
		MouseMotionListener[] f = new MouseMotionListener[s];
		for (int i = 0;i<mouseMotionListeners.size();i++)
		{
			f[i]=(MouseMotionListener)mouseMotionListeners.elementAt(i);
		}
		return f;
	}
	
	
	public boolean isDisplayable() {
		if (hasPeer())
			return ((Boolean) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {
					return Boolean.valueOf(peer.isVisible());
				}
			})).booleanValue();
		return false;
	}

	/**
	 * A shorthand to check whether peer already carries an SWT control.
	 *
	 * @return
	 */
	public final boolean hasPeer() {
		return SwingWTUtils.isSWTControlAvailable(peer);
	}
	
	/**
	 * By default, return the debug info.
	 *
	 * @return
	 */
	public String toString() {
		return debugId();
	}
	
	// ACCESSIBILITY
	
	/** 
	 * Returns the component's accessible context.
	 */
	public AccessibleContext getAccessibleContext() {
		if (null == accessibleContext)
			accessibleContext = createAccessibleContext();
		return accessibleContext;
	}
	
	/**
	 * Sub-types should override this method to create the appropriate
	 * accessible context for the object.
	 *
	 * @return a newly created accessible context
	 */
	protected AccessibleContext createAccessibleContext() {
		return null;
	}
	
	/**
	 * An implementation of AccessibleContext, which should be extended by sub-types.
	 * 
	 * @author Thiago Tonelli Bartolomei
	 */
	protected abstract class AccessibleAWTComponent extends AccessibleContext {
	}
		
    // DEBUG SUPPORT
	
	/**
	 * Sub-types can override this to provide a better identifier during debug. 
	 *
	 * @return
	 */
	public String debugId() {
		return componentName;
	}
}

