/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import java.util.Iterator;
import java.util.Vector;

import swingwtx.swing.DefaultDesktopManager;
import swingwtx.swing.DesktopManager;

import swingwt.awt.BorderLayout;
import swingwt.awt.Component;
import swingwt.awt.Point;
import swingwtx.custom.JClosableTabbedPane;
import swingwtx.custom.event.TabCloseListener;
import swingwtx.swing.event.ChangeListener;
import swingwtx.swing.event.ChangeEvent;

public class JDesktopPane extends JLayeredPane {
    
	public static final int LIVE_DRAG_MODE = 0;
    public static final int OUTLINE_DRAG_MODE = 1;
    private int dragMode = 0;
    /**
     * Cache of JInternalFrames in the JDesktopPane
     */
    protected Vector frames = new Vector();
    
    /** This value determines how the JDesktopPane is displayed. If this is
     *  set to true, a series of closable tabs are used to represent the
     *  JInternalFrames.
     *  Otherwise, "real" MDI emulation is used.
     */
    protected boolean useTabbedEmulation = true;
    /** If we are using tabbed emulation, a reference to the closable
     *  tabbed pane we are using to contain JInternalFrames
     */
    protected JClosableTabbedPane jtp = null;
    /** Cache of the currently selected frame */
    protected JInternalFrame pSelectedFrame = null;

    public JDesktopPane() { super(); }
    /** SwingWT specific - allows you to choose whether to use "real" MDI or tabbed emulation of MDI */
    public JDesktopPane(boolean useTabbedEmulation) { super(); this.useTabbedEmulation = useTabbedEmulation; }
    
    /** Overridden add method to deal with JInternalFrames */
    public Component add(final Component c) {
        
        /** If it's not a JInternalFrame, or we aren't using tabbed emulation, just
          * add the component to us as normal. */
        if (!(c instanceof JInternalFrame)) {
            super.add(c);
            return c;
        }   
        else
        {
            // Add the frame to the cache of internal frames we
            // are holding and set a reference to us on the frame itself.
            frames.add((JInternalFrame) c);
            ((JInternalFrame) c).setParentPane(this);
            
            if (useTabbedEmulation) {
                return (Component) addInternalFrameToTabbedPane((JInternalFrame) c);
            }
            else {
                // Define initial size/location
                // Size should be overriden by the subclass anyway
                Point loc = getNextFrameLocation();
                c.setBounds(loc.x, loc.y, 320, 200);
                super.add(c);
                return c;
            }   
        }
    }
    
    /** Overridden add method to deal with JInternalFrames */
    public void add(Component c, Object layoutModifier) {
        add(c);
    }
    
    /** Overridden add method to deal with JInternalFrames */
    public Component add(String name, Component c) {
        c.setName(name);
        return add(c);
    }
    
    /**
      * TABBED EMULATION ONLY: 
      * Adds a JInternalFrame to the tabbed pane */
    protected JInternalFrame addInternalFrameToTabbedPane(final JInternalFrame frame) {
        
        frame.setDrawDecoration(false); // Tabbed pane emulation doesn't want the frame to draw
                                        // window decorations.
        if (jtp == null) return frame;
        
        final JDesktopPane me = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (jtp != null) {
                    jtp.addTab(frame.getTitle(), frame.getFrameIcon(), frame, frame.getTitle());
                    // Set focus to the added frame
                    jtp.setSelectedIndex(jtp.getTabCount() - 1);
                    // Selected frame has changed
		    newTabSelected(frame);
                }
            }
        });
        return frame;
    }
    
    /**
     * Changes the JDesktopPane to use tabbed emulation if set
     * to true. Tabbed emulation emulates an MDI interface by
     * using closable tabs to represent the JInternalFrame
     * objects inside.
     */
    public void setUseTabbedEmulation(boolean b) {
        useTabbedEmulation = b;
    }
    
    protected Point nextLocation = null;
    
    /** Returns the next location a frame should appear at. This
     *  is just a quick hack and it steps down 24 pixels in a cascade
     *  for 5 windows, before going back to 0, 0
     */
    public Point getNextFrameLocation() {
        if (nextLocation == null)
            nextLocation = new Point(0, 0);
        else if ((nextLocation.x) == 0)
            nextLocation.setLocation(24, 24);
        else if ((nextLocation.x) == 24)
            nextLocation.setLocation(48, 48);
        else if ((nextLocation.x) == 48)
            nextLocation.setLocation(72, 72);
        else if ((nextLocation.x) == 72)
            nextLocation.setLocation(0, 0);
        return nextLocation;
    }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception { 
        
        descendantHasPeer = true;
        
        // Create peer
        super.setSwingWTParent(parent);
        
        // If we are using the tabbed stuff, we need to create
        // a closable tabbed pane and add it as a single child
        // of this component, then fill it with the frames.
        if (useTabbedEmulation) {

            // Add items onto a tabbed pane, filling this
            // container.
            setLayout(new BorderLayout());
            jtp = new JClosableTabbedPane();
            jtp.setTabPlacement(SwingConstants.TOP);
            add(jtp, BorderLayout.CENTER);

            Iterator i = frames.iterator();
            while (i.hasNext()) {
                JInternalFrame frame = (JInternalFrame) i.next();
                jtp.addTab(frame.getTitle(), frame.getFrameIcon(), frame, frame.getTitle());
            }

            // Listen for close events
            jtp.addTabCloseListener(new TabCloseListener() {
                public boolean tabClosed(int index) {
                    return ((JInternalFrame) frames.get(index)).processFrameClosing();
                }
            });

            // Listen for tab changed events and call newTabSelected
	    // - can be overriden by subclasses.
	    jtp.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    newTabSelected(getSelectedFrame());
		}
	    });
            
        }
        else {
            
            // If we aren't using tabbed emulation, then
            // the JInternalFrames can draw themselves on the
            // JLayeredPane - this is already taken care of in
            // the call to super.setSwingWTParent() as they are
            // regular JComponents
            
        }
    }

    /**
     * TABBED EMULATION ONLY
     * Useful for subclasses - gets called when the user clicks
     * to change frame.
     */
    protected void newTabSelected(JInternalFrame j) {
    }
    
    /**
     * TABBED EMULATION ONLY:
     * Updates a frame's components from it's cached properties.
     */
    protected void refreshFrame(final JInternalFrame frame) {
        
        // If we aren't using tabbed emulation, stop now
        if (!useTabbedEmulation) return;
        
        // If no frame specified, drop out 
        if (frame == null) return;
        
        // Go onto event dispatch thread to prevent frames being
        // closed whilst we process
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                
                // Get all frames to find this one (drop out if none exist)
                final JInternalFrame[] frames = getAllFrames();
                if (frames == null) return;
                
                for (int i = 0; i < frames.length; i++)
                    if (frames[i] == frame)
                        updateFrameAt(i, frame);
            }
        });
    }
    
    /**
     * TABBED EMULATION ONLY:
     * Copies an internal frame's properties onto it's component
     */
    protected void updateFrameAt(int index, JInternalFrame frame) {
        jtp.setTitleAt(index, frame.getTitle());
        jtp.setIconAt(index, frame.getFrameIcon());
    }
    
    /** Removes an internal frame */
    protected void removeFrame(JInternalFrame frame) {
        if (useTabbedEmulation) {
            int i = frames.indexOf(frame);
            if (i != -1) {
                jtp.removeTabAt(i);
                frames.remove(frame);
            }
        }
        else {
            frames.remove(frame);
            remove(frame);
        }
    }
    
    public JInternalFrame[] getAllFrames() {
        JInternalFrame[] ret = new JInternalFrame[frames.size()];
        Object[] jf = frames.toArray();
        for (int i = 0; i < jf.length; i ++) {
            ret[i] = (JInternalFrame) jf[i];    
        }
        jf = null;
        return ret;
    }
    
    public JInternalFrame getSelectedFrame() {
        if (useTabbedEmulation) {
            if (jtp == null) return null;
            return (JInternalFrame) frames.get(jtp.getSelectedIndex());
        }
        else
        {
            // We cache the currently selected frame
            // in setSelectedFrame. If none is cached, we
            // return the first one if there is one, 
            // otherwise return null.
            if (pSelectedFrame != null)
                return pSelectedFrame;
            else if (frames.size() > 0)
                return (JInternalFrame) frames.get(0);
            else
                return null;
        }
    }
    
    public void setSelectedFrame(JInternalFrame frame) {
        if (useTabbedEmulation) {
            if (jtp == null) return;
            int i = frames.indexOf(frame);
            if (i != -1)
                jtp.setSelectedIndex(i);
        }
        else {
            // Mark the JInternalFrame as selected and
            // bring it to the front of the layered pane.
	    // 
	    // This is called back from events in JComponent
	    // when they detect they are added to a JInternalFrame
	    // that isn't using tabbed emulation
            pSelectedFrame = frame;
            moveToFront(frame);
        }
    }
    
    DesktopManager manager = new DefaultDesktopManager();
    
    public DesktopManager getDesktopManager()
    {
        return manager;
    }
    
    public void setDesktopManager(DesktopManager manager) {
    	// stubbed
    }
    /**
     * 
     * @param dragMode
     * TODO: Implement this correctly..... just now only stubb
     */
    public void setDragMode(int dragMode) 
    {
    	this.dragMode = dragMode;
     }
    /**
     * 
     * @return dragMode
     * TODO: Implement this correctly..... just now only stubb
     */
     public int getDragMode() 
     {
         return dragMode;
     }

}
