/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */


package swingwtx.swing;

import java.beans.PropertyChangeListener;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import swingwt.awt.Component;
import swingwt.awt.Container;
import swingwt.awt.Dimension;
import swingwt.awt.Graphics;
import swingwt.awt.Insets;
import swingwt.awt.Point;
import swingwt.awt.Rectangle;
import swingwt.awt.event.ActionListener;
import swingwt.awt.event.FocusEvent;
import swingwt.awt.event.FocusListener;
import swingwt.awt.event.MouseEvent;
import swingwt.awt.event.MouseListener;
import swingwtx.accessibility.AccessibleExtendedComponent;
import swingwtx.swing.border.Border;
import swingwtx.swing.event.AncestorListener;
import swingwtx.swing.plaf.ComponentUI;


public class JComponent extends swingwt.awt.Container {

    /** The border for the component */
    protected swingwtx.swing.border.Border border = null;
    
    /** Whether or not subclasses are supplying a peer in their setSwingWTParent() call -
     *  if they don't, this component will supply a Composite peer (this is for Swing code
     *  that descends JComopnent and paints on it)
     */
    protected boolean descendantHasPeer = false;
    /** The table of client properties */
    protected Hashtable clientProperties;

    protected boolean opaque = true;

    protected InputMap focusInputMap = null;
    protected InputMap ancestorInputMap = null;
    protected InputMap windowInputMap = null;
    
    protected ActionMap actionMap = null;

    protected Vector propertyChangeListeners = new Vector();
    protected Vector ancestorListeners = new Vector();

    public static final int WHEN_FOCUSED = 0;
    public static final int WHEN_ANCESTOR_OF_FOCUSED_COMPONENT = 1;
    public static final int WHEN_IN_FOCUSED_WINDOW = 2;

    // The SwingWT's  UI-component  ( it does not do anything at this point )
    protected ComponentUI componentUI = null;
    
    /** Creates an empty JComponent which by default will use
     *  a Composite peer (allowing you to paint on it).
     */
    public JComponent() {  }
    
    /** Adds a PropertyChangeListener to the component */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeListeners.add(l);    
    }
    /** Adds a PropertyChangeListener to the component */
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener l) {
        propertyChangeListeners.add(l);    
    }    
    /** Removes a PropertyChangeListener from the component */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeListeners.remove(l);    
    }
    /** Removes a PropertyChangeListener from the component */
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener l) {
        propertyChangeListeners.remove(l);    
    }



    
    // Overriden add/remove methods - these are used to
    // attach a focus/mouse listener to the component if it is a child
    // of a JInternalFrame. When the component gains the focus or is
    // clicked, the setSelectedFrame() method of the JInternalFrame's
    // parent JDesktopPane is called with the frame to tell it to
    // become the active layer
    // =================================================================
    
    public void add(swingwt.awt.Component c, Object layoutModifier) {
	checkInternalFrameChild();
	super.add(c, layoutModifier);
    }
    
    public void add(swingwt.awt.Component c, Object layoutModifier, int index) {
	checkInternalFrameChild();
        super.add(c, layoutModifier, index);
    }

    public Component add(String name, swingwt.awt.Component c) {
	checkInternalFrameChild();
        return super.add(name, c);
    }
    
    public Component add(Component c) {
	checkInternalFrameChild();
        return super.add(c);
    }
    
    public Component add(Component c, int index) {
	checkInternalFrameChild();
        return super.add(c);    
    }

    public void remove(swingwt.awt.Component c) {
	if (internalFrameAdapter != null) {
	    removeFocusListener(internalFrameAdapter);
	    removeMouseListener(internalFrameAdapter);
	    internalFrameAdapter = null;
	}
	super.remove(c);
    }

    public void remove(int index) {
        Component c = (Component) comps.get(index);
        remove(c);
    }

    /** Checks whether this JComponent is a child of a JInternalFrame
     *  and if so, attaches a focus and mouse listener - when the
     *  component is clicked/gets the focus, the desktop pane of
     *  the JInternalFrame is called to tell it to make the frame
     *  active.
     *
     *  Of course, it only does this if the JDesktopPane is
     *  not using tabbed emulation (as you can't see other
     *  child components in tabbed emulation).
     */
    protected void checkInternalFrameChild() {
        Container c = this;
	while (c != null) {
	    if (c instanceof JInternalFrame) {
                internalFrameParent = (JInternalFrame) c;
                internalFrameAdapter = new InternalFrameSelectionListener();
                addMouseListener(internalFrameAdapter);
                addFocusListener(internalFrameAdapter);
                return;
	    }
	    c = c.getParent();
	}
    }

    /** Event adapter for managing internal frame selection if
     *  this component is an internal frame child. Fires when
     *  the mouseClicked or focusGained event hits.
     *
     *  There will never be more than once instance of this
     *  class per JComponent to make it easy to add/remove.
     */
    protected class InternalFrameSelectionListener implements
    						   MouseListener, FocusListener {
	public void focusGained(FocusEvent e) { selectFrame(); }
	public void focusLost(FocusEvent e) {}
	public void mouseClicked(MouseEvent e) { selectFrame(); }
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public void selectFrame() {
	    try {
                if (!internalFrameParent.getParentPane().useTabbedEmulation) {
                    internalFrameParent.getParentPane().setSelectedFrame(internalFrameParent);
                }
	    }
	    catch (Exception e) {
                e.printStackTrace();
	    }
	}
    }
    /** Event adapter for calling the setSelection on the JInternalFrame parent
     *  if there is one.
     */
    protected InternalFrameSelectionListener internalFrameAdapter = null;
    /** JInternalFrame parent if this component has one */
    protected JInternalFrame internalFrameParent = null;
    

    // ============= End of internal frame selection code
    
    
    
    /** Forces a repaint of the whole component */
    public void paintImmediately(int x,int y,int w, int h) {
        super.repaint();
    }
    
    /** Forces a repaint of the whole component. Calls the
     * Component.repaint() method in the superclass
     */
    public void paintImmediately(swingwt.awt.Rectangle r) {
        super.repaint();
    }
    
    /** Forces a repaint of a specified rectangle (actually
      * repaints the whole thing) */
    public void repaint(Rectangle r) {
        repaint(r.x,r.y,r.width,r.height);
    }
    
    /** 
     * Override in subclass
     */
    protected void paintComponent(swingwt.awt.Graphics g) {
        paint(g);
    }
    
    /**
     * NOT IMPLEMENTED
     */
    protected void paintChildren(swingwt.awt.Graphics g) {
    }

    public void paintBackground(int x, int y, int width, int height) {
         if (parent != null && !isOpaque()) {
             Dimension size = getSize();
             Point location = getLocation();
             //parent.paintBackground(location.x, location.y, size.width, size.height);
         }
    }

    /**
     * Paints this component and any associated borders
     * on it. Note that if no subclass overrides the
     * paint method, the component tells the native bits
     * to show a widget and nothing else happens
     */
    public void paint(swingwt.awt.Graphics g) {
        super.paint(g);
        paintBorder(g);
    }

    protected void paintBorder(Graphics g) {
        if (border != null) {
            border.paintBorder(this, g, 0, 0, getWidth(), getHeight());
        }
    }

    /** Returns the components border.  */
    public Border getBorder() { if (border == null) return BorderFactory.createEmptyBorder(); else return border; }
    /** Sets the components border */
    public void setBorder(Border b) { border = b; invalidate(); }
    
    /** NOT IMPLEMENTED */
    public void setRequestFocusEnabled(boolean b) {}
    /** NOT IMPLEMENTED */
    public void setAutoscrolls(boolean b) {}
    /** Returns the appropriate tooltip text for a given
     *  <code>MouseEvent</code>. Since platform lacks Swing's
     *  flexibility in this regard, always returns super.getToolTipText()
     */
    public String getToolTipText(swingwt.awt.event.MouseEvent e) {
        return getToolTipText();    
    }

    /** Retrieves the top-level ancestor of this component
     *  (submitted by Fernando Petrola) 
     **/
    public Container getTopLevelAncestor() {
        for (Container p= this; p != null; p= p.getParent())
            if (p instanceof swingwt.awt.Window || p instanceof swingwt.applet.Applet)
                return p;
        return null;
    }
    
    /** Returns the appropriate location for the tooltip text
     *  for a given mouse event. This will just read the X/Y location
     *  of the cursor from the mouse event and return that.
     */
    public Point getToolTipLocation(swingwt.awt.event.MouseEvent e) {
        return e.getPoint();
    }
    /** NOT IMPLEMENTED
      * @return true */
    public boolean getAutoscrolls() { return true; }
    /** Hardcoded to return true - there's no other way for
     *  platform widgets.
     */
    public boolean isOpaque() { return opaque; }
    /** Ignored - platform widgets are always opaque */
    public void setOpaque(boolean b) { this.opaque = b; updatePeerBackground(); }
    /** Whether or not this component is using double-buffering.
     *  Only applies to components with Canvas peers, as the
     *  platform is responsible for painting widgets
     */
    public boolean isDoubleBuffered() { return pDoubleBuffered; }
    /** Used to determine whether or not to double buffer the
     *  component. This only has meaning for Canvas peers, and if this
     *  is true, the subclass paint() method will receive a graphics
     *  context for a buffered image to paint on, which will then
     *  be painted onto the component. Otherwise, the paint() method
     *  will receive the component's graphics context to paint on.
     */
    public void setDoubleBuffered(boolean b) { pDoubleBuffered = b; }
    
    /** NOT IMPLEMENTED - used to scroll parent JViewPort containers
      * to the specified rectangle */
    public void scrollRectToVisible(Rectangle aRect) { 
    }
    
    /** Retrieves all client properties (Hashtable) as a Dictionary */
    private Dictionary getClientProperties() {
        if (clientProperties == null) {
            clientProperties = new Hashtable(2);
        }
        return clientProperties;
    }
   
    public boolean isRequestFocusEnabled() {
          //return !this.getFlag(REQUEST_FOCUS_DISABLED);
      return true;
    }

    public void updateUI() {}

    /** 
     *  Retrieves a property from the client, or null if
     *  the key does not exist.
     */
    public final Object getClientProperty(Object key) {
    	getClientProperties();
        if (clientProperties == null) {
            return null;
        }
        else {
            return getClientProperties().get(key);
        }
    }
    
    /** 
     *  Not implemented correctly - rather than returning
     *  the visible portion of the component, this returns
     *  a Rectangle sized to match the component
     */
    public swingwt.awt.Rectangle getVisibleRect() {
        return new swingwt.awt.Rectangle(0, 0, getWidth(), getHeight());
    }
    
    /** Sets a property against the component */
    public final void putClientProperty(Object key, Object value) {
    	getClientProperties();
        if (value == null && clientProperties == null) {
            return;
        }
        getClientProperties().put(key, value);
    }
    
    /** 
     *  Used to determine whether this component is a top level container
     *  (overriden in subclasses), so validation code knows to work up
     *  the tree to that point before validating down.
     */
    public boolean isValidateRoot() {
        return false;    
    }
    
    /** Always returns true - we're using native widgets! */
    public boolean isOptimizedDrawingEnabled() { 
        return true;
    }
    
    /** NOT IMPLEMENTED */
    public void setNextFocusableComponent(Component acomponent) {
    }

    /** Returns the Insets for the components (taking into account any Border) */
    public Insets getInsets() {
        if (border != null)
            return border.getBorderInsets(this);
        return super.getInsets();
    }

    protected Dimension computePreferredSize() {
        Dimension size = super.computePreferredSize();
        Insets in = getInsets();
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        return size;
    }

    /** Sets the InputMap for this component */
    public final void setInputMap(int condition, InputMap inputMap) {
        if (condition == WHEN_FOCUSED) { focusInputMap = inputMap; }
        else if (condition == WHEN_ANCESTOR_OF_FOCUSED_COMPONENT) { ancestorInputMap = inputMap; }
        else if (condition == WHEN_IN_FOCUSED_WINDOW) { windowInputMap = inputMap; }
        else { throw new IllegalArgumentException("Illegal InputMap type!"); }
    }

    public final InputMap getInputMap() {
    	return getInputMap(WHEN_FOCUSED, true);
    }
    
    public final InputMap getInputMap(int condition) {
    	return getInputMap(condition, true);
    }
    
    /** Gets the InputMap for this component */
    public final InputMap getInputMap(int condition, boolean create) {
        InputMap inputMap = null;

        if (condition == WHEN_FOCUSED)
        {
            if (focusInputMap == null && create) focusInputMap = new InputMap();
            if (focusInputMap != null) { inputMap = focusInputMap; }
        }
        else if (condition == WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
        {
            if (ancestorInputMap == null && create) ancestorInputMap = new InputMap();
            if (ancestorInputMap != null) { inputMap = ancestorInputMap; }
        }
        else if (condition == WHEN_IN_FOCUSED_WINDOW)
        {
            if (windowInputMap == null && create) windowInputMap = new InputMap();
            if (windowInputMap != null) { inputMap = windowInputMap; }
        }
        else
        {
            throw new IllegalArgumentException("Illegal InputMap type!");
        }

        return inputMap;
    }
    
    public final void setActionMap(ActionMap actionMap) {
        this.actionMap = actionMap;
    }

    public final ActionMap getActionMap() {
        return getActionMap(true);
    }
    
    private final ActionMap getActionMap(boolean create) {
        ActionMap map = null;
        if (actionMap != null) map = actionMap;
        else if (create) map = new ActionMap();
        
        this.actionMap = map;
        return map;
    }
    
    /** Destroys the component */
    public void dispose() {
	// Use dispose() from Container, which destroys child
	// components and finally the peer itself
        super.dispose();	
    }

     public JRootPane getRootPane() {
        JRootPane rootPane = null;
        Container parent2 = getParent();
        while (parent2 != null) {
            if (parent2 instanceof JRootPane) {
                rootPane = (JRootPane)parent2;
                break;
            }
            parent2 = parent2.getParent();
         }
         return rootPane;
     }
							 

    /**
     * Callback for when this JComponent is added to a container.
     */
    public void setSwingWTParent(Container parent) throws Exception {
	
	// Ensure child components are created where necessary (if
        // this JComponent is a container)
        super.setSwingWTParent(parent);

	// We need to identify whether a subclass is providing it's
	// own peer, and if not, create a Composite peer.
	if (!descendantHasPeer) {
	    ppeer = new org.eclipse.swt.widgets.Composite(parent.getComposite(), 0);
            peer = ppeer;
            this.parent = parent;
        }
    }
	/** Requests the component receives the focus */
	public void requestFocus() 
	{
		super.requestFocus();
	}
	/** Requests the component receives the focus */
	public void requestFocusInWindow() 
	{
		super.requestFocus();
	}
	// Compability dummy
	public void print(Graphics g){} 
	public Rectangle getBounds(Rectangle rv)
	{
		return getBounds();
	}
	//TODO: fix me
	// I don't know how to do this stuff
	public void registerKeyboardAction(ActionListener anAction,String aCommand,KeyStroke aKeyStroke,int aCondition) 
	{
    }
	public void registerKeyboardAction(ActionListener anAction,KeyStroke aKeyStroke,int aCondition) {registerKeyboardAction(anAction,null,aKeyStroke,aCondition);}
	
	public void setInputVerifier(InputVerifier inputVerifier)
	{
	}
	public InputVerifier getInputVerifier()
	{
		return null;
	}
	protected  void setUI(ComponentUI newUI) 
	{
		
	}
	// Just an add to the listener
	// TODO: register the listener to the ancestors and notify
	public void addAncestorListener(AncestorListener listener) 
	{
		ancestorListeners.add(listener);
    }
	// TODO: remove the listener from the ancestors and notify
    public void removeAncestorListener(AncestorListener listener) 
    {
    	
    	try{ancestorListeners.remove(listener);}catch(Exception e){}
    }
    public AncestorListener[] getAncestorListeners() 
    {
    	int s = ancestorListeners.size();
    	AncestorListener[] f = new AncestorListener[s];
		for (int i = 0;i<ancestorListeners.size();i++)
		{
			f[i]=(AncestorListener)ancestorListeners.elementAt(i);
		}
		return f;
    }
    
    /**
     * 
     * TODO Comment!!
     * 
     * @author Thiago Tonelli Bartolomei
     */
    protected class AccessibleJComponent extends AccessibleAWTContainer implements AccessibleExtendedComponent {

    	public void addFocusListener(FocusListener l) {
			// TODO Auto-generated method stub
		}
    }
    

    // DEBUG SUPPORT
	
  	/**
  	 * Sub-types can override this to provide a better identifier during debug. 
  	 *
  	 * @return
  	 */
  	public String debugId() {
  		String info = (null != border) ? border.debugId() + " / " : "";
  		
  		if (null != layout) {
  			info += layout.getClass().getName();//.getSimpleName();
  		}
  		
  		String name = super.debugId();
  		
  		if (info.length() > 0) {
  			return name + " [" + info + "]";
  		} else {
  			return name;
  		}
  	}
}
