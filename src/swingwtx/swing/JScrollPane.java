/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import swingwt.awt.*;
import swingwt.awt.event.*;
import swingwtx.swing.border.*;

public class JScrollPane extends JPanel implements AdjustmentListener {

    public final static int VERTICAL_SCROLLBAR_ALWAYS = 0;
    public final static int VERTICAL_SCROLLBAR_AS_NEEDED = 1;
    public final static int VERTICAL_SCROLLBAR_NEVER = 2;
    public final static int HORIZONTAL_SCROLLBAR_ALWAYS = 3;
    public final static int HORIZONTAL_SCROLLBAR_AS_NEEDED = 4;
    public final static int HORIZONTAL_SCROLLBAR_NEVER = 5;
    
    public final static String VIEWPORT = "VIEWPORT";
    public final static String VERTICAL_SCROLLBAR = "VERTICAL_SCROLLBAR";
    public final static String HORIZONTAL_SCROLLBAR = "HORIZONTAL_SCROLLBAR";
    public final static String ROW_HEADER = "ROW_HEADER";
    public final static String COLUMN_HEADER = "COLUMN_HEADER";
    public final static String LOWER_LEFT_CORNER = "LOWER_LEFT_CORNER";
    public final static String LOWER_RIGHT_CORNER = "LOWER_RIGHT_CORNER";
    public final static String UPPER_LEFT_CORNER = "UPPER_LEFT_CORNER";
    public final static String UPPER_RIGHT_CORNER = "UPPER_RIGHT_CORNER";
    public final static String LOWER_LEADING_CORNER = "LOWER_LEADING_CORNER";
    public final static String LOWER_TRAILING_CORNER = "LOWER_TRAILING_CORNER";
    public final static String UPPER_LEADING_CORNER = "UPPER_LEADING_CORNER";
    public final static String UPPER_TRAILING_CORNER = "UPPER_TRAILING_CORNER";
    public final static String VERTICAL_SCROLLBAR_POLICY = "VERTICAL_SCROLLBAR_POLICY";
    public final static String HORIZONTAL_SCROLLBAR_POLICY = "HORIZONTAL_SCROLLBAR_POLICY";
    
    protected final static int BAR_SCALE_FACTOR = 10; // The difference between the bar scale and pixels
    
    protected int verticalPolicy = VERTICAL_SCROLLBAR_AS_NEEDED;
    protected int horizontalPolicy = HORIZONTAL_SCROLLBAR_AS_NEEDED;
    
    protected JScrollBar hbar = new JScrollBar(JScrollBar.HORIZONTAL);
    protected JScrollBar vbar = new JScrollBar(JScrollBar.VERTICAL);
    
    protected swingwt.awt.Component childComponent = null;
    
    protected JPanel northPanel = new JPanel();
    protected JPanel westPanel = new JPanel();
    protected JPanel southPanel = new JPanel();
    
    protected Component columnView = null;
    protected Component rowView = null;
    
    protected int ypix = 0; // The number of vertical pixels down the scroller we are
    protected int xpix = 0; // The number of horizontal pixels across the scroller we are
    
    protected int showY = 0; // Visible pixel height
    protected int realY = 0; // Workspace height
    protected int diffY = 0; // Difference
    protected int showX = 0; // Visible pixel width
    protected int realX = 0; // Workspace width
    protected int diffX = 0; // Difference
    
    protected boolean deactivated = false; // Whether the scrollpane contains a component that can handle it's own
                                           // scrollbars. This prevents flickers and strange behaviour later on
    

    protected JViewport view = null;
    
    public JScrollPane() { this(null, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED); }
    public JScrollPane(swingwt.awt.Component component) { this(component, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED); }
    public JScrollPane(int vsbPolicy, int hsbPolicy) { this(null, vsbPolicy, hsbPolicy);}
    
    public JScrollPane(swingwt.awt.Component component, int vsbPolicy, int hsbPolicy) {
        super(new ScrollPaneLayout());
        verticalPolicy = vsbPolicy; 
        horizontalPolicy = hsbPolicy; 
        
        layoutScrollPane();
        setViewport(new JViewport());
        if (!(component instanceof JList))
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(component);
        
        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                calculateBarSize();
            }
            public void componentMoved(ComponentEvent e) {
                calculateBarSize();
            }
            public void componentShown(ComponentEvent e) {
                calculateBarSize();
            }
            public void componentHidden(ComponentEvent e) {
                calculateBarSize();
            }
        });
        addContainerListener(new ContainerListener() {
            public void componentAdded(ContainerEvent e) {calculateBarSize();}
            public void componentRemoved(ContainerEvent e) {calculateBarSize();}
        });
    }

    public void setLayout(LayoutManager layout) {
        if (!(layout instanceof ScrollPaneLayout))
            throw new ClassCastException("JScrollPane layout must be a ScrollPaneLayout");
        super.setLayout(layout);
    }

    /**
     * Lays out the scrollpane and puts the bars on
     * where necessary.
     */
    protected void layoutScrollPane() {
        
        setLayout(new ScrollPaneLayout());

        // Setup bars
        hbar.setUnitIncrement(1);
        vbar.setUnitIncrement(1);
        hbar.setBlockIncrement(5);
        vbar.setBlockIncrement(5);

        // Bordered panels
        northPanel.setLayout(new BorderLayout());
        westPanel.setLayout(new BorderLayout());

        super.add(northPanel, BorderLayout.NORTH);
        super.add(westPanel, BorderLayout.WEST);

        super.add(hbar, swingwt.awt.BorderLayout.SOUTH);
        super.add(vbar, swingwt.awt.BorderLayout.EAST);

        handleBarVisibility();
        hbar.addAdjustmentListener(this);
        vbar.addAdjustmentListener(this);

    }
    
    protected void handleBarVisibility() {
        
        // If we aren't dealing with a subclass of JPanel or a JLabel,
        // don't use the scrollbars. This is because SWT widgets handle
        // their own.
        if (deactivated) {
            vbar.setVisible(false);
            hbar.setVisible(false);
            return;
        }
        
        boolean vbarWasVisible = vbar.isVisible();
        boolean hbarWasVisible = hbar.isVisible();

        if (verticalPolicy == VERTICAL_SCROLLBAR_ALWAYS) {
            vbar.setVisible(true);
        }
        else if (verticalPolicy == VERTICAL_SCROLLBAR_NEVER) {
            vbar.setVisible(false);
        }
        else if (diffY > 0) {
            vbar.setVisible(true);
        }
        else {
            vbar.setVisible(false);
        }
            
        if (horizontalPolicy == HORIZONTAL_SCROLLBAR_ALWAYS) {
            hbar.setVisible(true);
        }
        else if (horizontalPolicy == HORIZONTAL_SCROLLBAR_NEVER) {
            hbar.setVisible(false);
        }
        else if (diffX > 0) {
            hbar.setVisible(true);
        }
        else {
            hbar.setVisible(false);
        }
        
        if (hbarWasVisible != hbar.isVisible() || vbarWasVisible != vbar.isVisible()) {
            invalidate();
            repaint();
        }    
    }
    
    protected void calculateBarSize() {
        
        if (view != null) {
            
            // If we're dealing with a component that can manage it's
            // own scrollbars, resize the inner component to match
            // us
            if (deactivated) {
                view.getComponent(0).setSize(getSize());
            }
        }
        
        // It's possible the caller set the component into the viewport
        // bypassing these routines - grab the child if we couldn't
        // find one
        if (childComponent == null && view.getComponentCount() > 0)
            childComponent = view.getComponent(0);
        
        // Do nothing if we have no component or we're deactivated
        if (childComponent == null || deactivated) return;
        
        // Get the vertical height of the actual pane, less any
        // decorations
        showY = this.getSize().height - hbar.getPeerSize().height;
        if (columnView != null) showY -= columnView.getPeerSize().height;
        
        Dimension childSize = calcChildSize();
        
        // Get the height of the contents
        realY = childSize.height;
        diffY = realY - showY;
        
        vbar.setMinimum(0);
        vbar.setMaximum(diffY / BAR_SCALE_FACTOR);
        vbar.setValue(0);
        
        // Get the horizontal width of the actual pane
        showX = this.getSize().width - vbar.getPeerSize().width;
        if (rowView != null) showX -= rowView.getPeerSize().width;
        
        // Get the width of the contents
        realX = childSize.width;
        diffX = realX - showX;
        
        hbar.setMinimum(0);
        hbar.setMaximum(diffX / BAR_SCALE_FACTOR);
        hbar.setValue(0);
        
        // Do we need to show/hide those bars?
        handleBarVisibility();
        
    }

    public JViewport getViewport() { return view; }
    public void setViewport(JViewport view) {
        this.view = view;
        doAdd(view);
    }
    
    public void setViewportBorder(Border b) { setBorder(b); }
    public Border getViewportBorder() { return getBorder(); }
    
    public Component add(Component c) { 

        if (c == null) return null;
		
        // Check if the component can do its own scrollbars
        checkIfComponentCanHandleItself(c);
	
        // Tie this component up to the scrollpane so we can
        // manipulate scrollbars on components where we are using
        // this class to wrap them if necessary
        childComponent = (swingwt.awt.Component) c;
        
        // Create the viewport if we don't have one and assign it's child
        if (!deactivated) {
            if (getViewport() == null) setViewport(new JViewport());
            getViewport().setView(c);
        }
        else {
            // Otherwise, just put the child component straight into
            // this, whilst still allowing it to be accessed through
            // a fake viewport.
            // We do this because it gets too tricksy and starts
            // blowing up for cases where it's just not needed.
            this.view = new JViewport();
            view.setView(c);
            doAdd(c);
        }
        
        return c;
        
    }
    
    public void add(Component c, Object layoutmodifier) {
        add(c);
    }
    
    public void setViewportView(Component component) {
        add(component);
    }
    
    public int getHorizontalScrollBarPolicy() { return horizontalPolicy; }
    public void setHorizontalScrollBarPolicy(int policy) { horizontalPolicy = policy; calculateBarSize(); }
    public int getVerticalScrollBarPolicy() { return verticalPolicy; }
    public void setVerticalScrollBarPolicy(int policy) { verticalPolicy = policy; calculateBarSize(); }
    public void setHorizontalScrollBar(JScrollBar bar) { hbar = bar; layoutScrollPane(); calculateBarSize(); }
    public void setVerticalScrollBar(JScrollBar bar) { vbar = bar; layoutScrollPane(); calculateBarSize(); }
    public JScrollBar getHorizontalScrollBar() { return hbar; }
    public JScrollBar getVerticalScrollBar() { return vbar; }
    
    public swingwt.awt.Component[] getComponents() { return new swingwt.awt.Component[] { childComponent }; }
    
    public void setCorner(String key, Component corner) {
        if (key.equals(JScrollPane.UPPER_LEFT_CORNER) || key.equals(JScrollPane.UPPER_LEADING_CORNER))
            northPanel.add(corner, BorderLayout.WEST);
        if (key.equals(JScrollPane.UPPER_RIGHT_CORNER) || key.equals(JScrollPane.UPPER_TRAILING_CORNER))
            northPanel.add(corner, BorderLayout.EAST);
    }
    public void setColumnHeaderView(Component view) { columnView = view; northPanel.add(view, BorderLayout.CENTER); }
    public void setRowHeaderView(Component view) { rowView = view; westPanel.add(view, BorderLayout.CENTER); }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        
        // Create the peer as usual
        super.setSwingWTParent(parent);
        
        // Do we need to show/hide those bars in light
        // of child component?
        handleBarVisibility();
        
        // Make sure we are all set up
        if (!deactivated) 
            SwingUtilities.invokeIn(new Runnable() {
                public void run() {
                    calculateBarSize();
                    adjustmentValueChanged(null);
                }
            }, 500);
    }
    
    private Dimension calcChildSize() {
    	Dimension childSize;
        
        // If we don't have a child component, check the viewport
        // to see if it has one we can use
        if (childComponent == null) {
            childComponent = getViewport().getComponent(0);
        }
        
    	if (childComponent instanceof Scrollable) {
    		childSize = ((Scrollable)childComponent).getPreferredScrollableViewportSize();
    	}
    	else {
    		childSize = childComponent.getSize();
    	}
    	return childSize;
    }
    
    /** 
     *  Sets the deactivated flag if the contained component is not
     *  a JPanel or JLabel (the only things you'd want to scroll around
     *  really).
     */
    protected void checkIfComponentCanHandleItself(Component c) {
        deactivated = !(c instanceof JPanel) && !(c instanceof JLabel);
    }

    public Dimension getMinimumSize() {
        if (isMinimumSizeSet())
            return super.getMinimumSize();
        return new Dimension(0, 0);
    }

    public Dimension getPreferredSize() {
        if (isPreferredSizeSet())
            return super.getPreferredSize();
        return childComponent.getPreferredSize();
    }

    public Dimension getMaximumSize() {
        if (isMaximumSizeSet())
            return super.getMaximumSize();
        return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
    }

    /** Called when the scrollbar is dragged. We simply move the container
     *  holding the components around, relying on the platform widgets/SWT
     *  to handle clipping. I really wouldn't want it any other way :-)
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e != null) 
            if (e.getSource() == vbar) {
                // Calculate pixel offset from bar pos
                ypix = e.getValue() * BAR_SCALE_FACTOR;
            }
            else {
                // Calculate pixel offset from bar pos
                xpix = e.getValue() * BAR_SCALE_FACTOR;
            }
        
        Dimension d = calcChildSize(); // will return desired size
        childComponent.setBounds(xpix * -1, ypix * -1, d.width, d.height);
        
        // Update the child component
        if (childComponent instanceof Container)
            ((Container) childComponent).invalidate();
        else
            childComponent.repaint();
        
        // Since we're letting SWT handle the viewport, for compatibility, notify
        // JViewport ChangeListeners that a view change has happened.
        view.fireStateChanged();
    }
}
