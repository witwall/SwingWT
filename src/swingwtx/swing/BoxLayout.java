/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwtx.swing;

import swingwt.awt.*;

/**
 * An implementation of the BoxLayout.
 * 
 * This layout positions components in the X or Y axis.
 * 
 * @author R. Rawson-Tetley
 * @author Thiago Tonelli Bartolomei
 */
public class BoxLayout implements LayoutManager2 {
    
	/**
	 * Indicates components should be laid out from left to right.
	 */
    public static final int X_AXIS = 0;
    
    /**
     * Indicates components should be laid out from top to bottom. 
     */
    public static final int Y_AXIS = 1;
    
    /**
     * Indicates components should be laid out in the direction of a line.
     * 
     * @see ComponentOrientation
     */
    public static final int LINE_AXIS = 2;
    
    /**
     * Indicates components should be laid out in the direction of a page.
     * 
     * @see ComponentOrientation
     */
    public static final int PAGE_AXIS = 3;
    
    /**
     * The current layout axis.
     */
    protected final int axis;
    
    /**
     * The container been managed.
     */
    protected final Container target;
    
	// If any of the following fields is null, it means that the layout is
	// invalid and must be recalculated.

    /**
     * The computed size requirements for the width of the target's children.
     */
    protected SizeRequirements[] childrenWidth = null;
    
    /**
     * The computed size requirements for the height of the target's children.
     */
    protected SizeRequirements[] childrenHeight = null;
    
    /**
     * The computed size requirements for the target container's width.
     */
    protected SizeRequirements totalWidth = null;
    
    /**
     * The computed size requirements for the target container's heigth.
     */
    protected SizeRequirements totalHeight = null;
    
	/**
	 * Keeps track of the visibility flag of children.
	 * 
	 * @see #visibilityChanged()
	 */
	protected boolean[] visibility = null;
	
	
    /**
     * Creates a box layout that will manage this container.
     *
     * @param target
     * @param axis
     */
    public BoxLayout(Container target, int axis) {
    	// TODO - There was a constructor that received Component instead of Container, which 
    	// does not seem to make sense. There's probably a reason for that, so that is in fact
    	// needed, we can easily create a new constructor here.
        if (axis != X_AXIS && axis != Y_AXIS && axis != LINE_AXIS && axis != PAGE_AXIS)
            throw new Error("Invalid axis");
        
        this.axis = axis;
        this.target = target;
    }
   
    // LayoutManager implementation
    
	/**
	 * Adds the component to this layout, identifying it by this string.
	 *
	 * @param name
	 * @param component
	 */
    public void addLayoutComponent(String name, Component component) {
    	invalidate();
    }
    
    /**
     * Removes the component from this layout.
     *
     * @param component
     */
    public void removeLayoutComponent(Component component) {
    	invalidate();
    }
    
    /**
     * Lays out the container.
     *
     * @param container
     */
    public void layoutContainer(Container container) {
    	//System.out.println("Laying out " + target.debugId());
    	
    	if (needsRecalculation())
			calculate();
    	
        Dimension alloc = container.getSize();
        
        Insets in = container.getInsets();
        alloc.width -= in.left + in.right;
        alloc.height -= in.top + in.bottom;
        
        int absoluteAxis = getAbsoluteAxis(container); 
        		        
        boolean leftToRight = (absoluteAxis != axis) ? container.getComponentOrientation().isLeftToRight() : true;
        
        int childrenCount = childrenWidth.length;
        int[] xOffsets = new int[childrenCount];
        int[] xSpans = new int[childrenCount];
        int[] yOffsets = new int[childrenCount];
        int[] ySpans = new int[childrenCount];

        if (absoluteAxis == X_AXIS) {
            SizeRequirements.calculateAlignedPositions(alloc.height, totalHeight, childrenHeight, yOffsets, ySpans);
            SizeRequirements.calculateTiledPositions(alloc.width, totalWidth, childrenWidth, xOffsets, xSpans, leftToRight);
        } else {
            SizeRequirements.calculateTiledPositions(alloc.height, totalHeight, childrenHeight, yOffsets, ySpans);
            SizeRequirements.calculateAlignedPositions(alloc.width, totalWidth, childrenWidth, xOffsets, xSpans, leftToRight);
        }
        
        for (int i = 0; i < childrenCount; i++) {
            Component c = container.getComponent(i);
            c.setBounds(
            		(int) Math.min(
            				(long) in.left + (long) xOffsets[i], 
            				Integer.MAX_VALUE),
            		(int) Math.min( 
            				(long) in.top + (long) yOffsets[i], 
            				Integer.MAX_VALUE),
            		xSpans[i],
            		ySpans[i]);
        }
    }
    
    /**
     * Computes the size this container should have based on the
     * components contained in it.
     *
     * @param container
     * @return
     */
    public Dimension preferredLayoutSize(Container container) {
    	
    	if (needsRecalculation())
    		calculate();
    	
    	return getBoundDimension(container.getInsets(), totalWidth.preferred, totalHeight.preferred);
    }
        
    /**
     * Computes the minimum size of this container, based on the
     * components contained in it.
     *
     * @param container
     * @return
     */
    public Dimension minimumLayoutSize(Container container) {
    	
    	if (needsRecalculation())
    		calculate();

		return getBoundDimension(container.getInsets(), totalWidth.minimum, totalHeight.minimum);
    }
    
    // LayoutManager2 implementation
    
    /**
	 * Computes the maximum size of this container, based on the 
	 * components in it.
	 *
	 * @param container
	 * @return
	 */
	public Dimension maximumLayoutSize(Container container) {
		
		if (needsRecalculation())
			calculate();
		
		return getBoundDimension(container.getInsets(), totalWidth.maximum, totalHeight.maximum);
	}
	
    /**
	 * Adds this component to the manager with this corresponding
	 * constraint object.
	 *
	 * @param component
	 * @param constraints
	 */
	public void addLayoutComponent(Component component, Object constraints) {
		
		invalidate();
	}
	
	/**
	 * A value between 0 (origin) and 1 (infinite) that specifies the
	 * position this container should be aligned with respect to other
	 * components, in the X axis. Note that 0.5 means center. 
	 *
	 * @param container
	 * @return
	 */
	public float getLayoutAlignmentX(Container container) {
		
		if (needsRecalculation())
			calculate();
		
        return totalWidth.alignment;
	}
	
	/**
	 * A value between 0 (origin) and 1 (infinite) that specifies the
	 * position this container should be aligned with respect to other
	 * components, in the Y axis. Note that 0.5 means center
	 *
	 * @param container
	 * @return
	 */
	public float getLayoutAlignmentY(Container container) {
		
		if (needsRecalculation())
			calculate();
		
		return totalHeight.alignment;	
	}
	
	/**
	 * The layout manager should invalidate the layout, i.e., it
	 * should dischard any cached information.
	 *
	 * @param container
	 */
	public void invalidateLayout(Container container) {
		
        this.invalidate();
    }
   
	/**
	 * Removed cached values to force recalculation.
	 */
	protected void invalidate() {
		childrenWidth = null;
        childrenHeight = null;
        totalWidth = null;
        totalHeight = null;
        visibility = null;
	}
    
	/**
	 * Checks whether the layout must be recalculated.
	 * 
	 * We use this to avoid recalculating the layout when nothing important changed.
	 * Currently this means that we need recalculation if the layout was invalidated
	 * or if any child changed its visibility.
	 *
	 * @return
	 * @see #visibilityChanged()
	 */
	protected boolean needsRecalculation() {
		return (null == childrenWidth || visibilityChanged());
	}
	
	/**
	 * Sets the visibility flag for the child with this index.
	 *
	 * @param childIndex
	 * @param isVisible
	 */
	protected final void setVisibility(int childIndex, boolean isVisible) {
		visibility[childIndex] = isVisible;
	}
	
	/**
	 * Checks whether some child changed its visibility flag since the last
	 * time the layout was calculated.
	 * 
	 * The algorithm and data structure here is poor, but works for now. We are keeping
	 * an array of booleans to represent the visibility flags of children. That is bad
	 * because i) we may need a large array and ii) we may need to iterate the whole
	 * array to check if any boolean changed.
	 * 
	 * A better approach would be to use an array of int, and then use each of the 31
	 * available bits to represent the flag of a child. This would speed up the check
	 * (because we can simply perform a bit mask check for all blocks of 31 children)
	 * and use less space (because we need one int for each 31 children). But this is
	 * trickier to implement, so I am leaving it for now.
	 *
	 * @return
	 */
	protected final boolean visibilityChanged() {
		if (null == visibility)
			return true;
		
		int count = target.getComponentCount();
		if (count != visibility.length) 
			return true;
		
		for (int i = 0; i < count; i++)
			if (visibility[i] != target.getComponent(i).isVisible())
				return true;
		
		return false;
	}
	
	/**
	 * Computes the size requirements for each child component plus for
	 * the target container.
	 */
    protected void calculate() {
    	//System.out.println("Calculating layout " + target.debugId());
    	
    	// Compute the size requirements for each children
        int childrenCount = target.getComponentCount();
        childrenWidth = new SizeRequirements[childrenCount];
        childrenHeight = new SizeRequirements[childrenCount];
        
        visibility = new boolean[childrenCount];
        
        for (int i = 0; i < childrenCount; i++) {
            Component child = target.getComponent(i);
            
            boolean isVisible = child.isVisible();
            setVisibility(i, isVisible);

            if (! isVisible) {

            	childrenWidth[i] = new SizeRequirements(0, 0, 0, child.getAlignmentX());
                childrenHeight[i] = new SizeRequirements(0, 0, 0, child.getAlignmentY());
                
            } else {
            	
            	Dimension pref = child.getPreferredSize();
                Dimension min = child.getMinimumSize();
                Dimension max = child.getMaximumSize();

				childrenWidth[i] = new SizeRequirements(min.width, pref.width, max.width, child.getAlignmentX());
				childrenHeight[i] = new SizeRequirements(min.height, pref.height, max.height, child.getAlignmentY());
            }
        }

        // Compute the size requirement for the container
        if (X_AXIS == getAbsoluteAxis(target)) {
            totalWidth = SizeRequirements.getTiledSizeRequirements(childrenWidth);
            totalHeight = SizeRequirements.getAlignedSizeRequirements(childrenHeight);
        } else {
            totalWidth = SizeRequirements.getAlignedSizeRequirements(childrenWidth);
            totalHeight = SizeRequirements.getTiledSizeRequirements(childrenHeight);
        }
    }
    
    /**
     * Computes the absolute axis for this container.
     * 
     * The absolute axis may depend on the component orientation. 
     *
     * @param container
     * @return
     */
    protected final int getAbsoluteAxis(Container container) {
         if (axis == LINE_AXIS)
             return container.getComponentOrientation().isHorizontal() ? X_AXIS : Y_AXIS;
         
         if (axis == PAGE_AXIS)
             return container.getComponentOrientation().isHorizontal() ? Y_AXIS : X_AXIS;
         
         return axis;
    }
    
    /**
     * Gets the dimension that represents this width and height plus the insets, but 
     * bound by the maximum integer value.
     *
     * @param insets
     * @param width
     * @param height
     * @return
     */
    protected final Dimension getBoundDimension(Insets insets, long width, long height) {
    	
    	long w = width + insets.left + insets.right;
		long h = height + insets.top + insets.bottom;
				
		return new Dimension(
				(int) Math.min(w, Integer.MAX_VALUE), 
				(int) Math.min(h, Integer.MAX_VALUE));
    }
}
