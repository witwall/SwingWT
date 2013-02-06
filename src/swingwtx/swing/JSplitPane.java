/*
 * SwingWT Copyright(c)2003-2008, R. Rawson-Tetley
 *
 * For more information on distributing and using this program, please see the
 * accompanying "COPYING" file.
 *
 * Contact me by electronic mail: bobintetley@users.sourceforge.net
 *
 * 
 *
 */

package swingwtx.swing;

import org.eclipse.swt.custom.*;
import org.eclipse.swt.*;

import swingwt.awt.*;

/**
 * JSplitPane kindly contributed by Jack Park. Couple of changes here and there
 * by me to fit existing standards and things.
 *
 * 29/11/2003 - Thanks to Diane Trout for skeletons of additional methods
 * required.
 *
 * @author Jack Park
 * @author Diane Trout
 * @author Robin Rawson-Tetley
 */
public class JSplitPane extends JComponent {
    
    public final static int VERTICAL_SPLIT = 0;
    public final static int HORIZONTAL_SPLIT = 1;

    public final static String LEFT = "left";
    public final static String RIGHT = "right";
    
    protected int pOrientation = HORIZONTAL_SPLIT;
    protected double pResizeWeight = 0;
    
    protected swingwt.awt.Component leftComponent = null;
    protected swingwt.awt.Component rightComponent = null;
    
    private SashForm ppeer = null;
    
    public JSplitPane() {
    }
    public JSplitPane(int newOrientation) {
        this(newOrientation, false);
    }
    public JSplitPane(int newOrientation, boolean newContinuousLayout) {
        this(newOrientation, newContinuousLayout, null, null);
    }
    public JSplitPane(
    int newOrientation,
    swingwt.awt.Component newLeftComponent,
    swingwt.awt.Component newRightComponent) {
        this(newOrientation, false, newLeftComponent, newRightComponent);
    }
    
    public JSplitPane(
    int newOrientation,
    boolean newContinuousLayout,
    swingwt.awt.Component newLeftComponent,
    swingwt.awt.Component newRightComponent) {
        
        pOrientation = newOrientation;
        if (pOrientation != HORIZONTAL_SPLIT && pOrientation != VERTICAL_SPLIT)
            throw new IllegalArgumentException("Invalid orientation.");
        if (newLeftComponent != null)
            setLeftComponent(newLeftComponent);
        if (newRightComponent != null)
            setRightComponent(newRightComponent);
        
    }
    
    public void setLeftComponent(swingwt.awt.Component comp) {
        if (leftComponent != null) {
            leftComponent.componentOnlyDispose();
        }
        leftComponent = comp;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            addLeft();
        }
    }
    
    public void setRightComponent(swingwt.awt.Component comp) {
        if (rightComponent != null) {
            rightComponent.componentOnlyDispose();
        }
        rightComponent = comp;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            addRight();
        }
    }
   
    public void add(Component c, Object layoutModifier) {
        if (layoutModifier != null) {
	    if (layoutModifier.equals(LEFT)) {
		setLeftComponent(c);
		return;
	    }
	    else if (layoutModifier.equals(RIGHT)) {
		setRightComponent(c);
		return;
	    }
	}
	super.add(c, layoutModifier);
    }
    
    void addLeft() {
        final JSplitPane me = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                try {
                    leftComponent.setSwingWTParent(me);
                    leftComponent.getSWTPeer().moveAbove(null);
                    getComposite().layout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    void addRight() {
        final JSplitPane me = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                try {
                    rightComponent.setSwingWTParent(me);
                    rightComponent.getSWTPeer().moveBelow(null);
                    getComposite().layout();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new SashForm(parent.getComposite(), SWT.NO_RADIO_GROUP | SWT.BORDER);
        peer = ppeer;
        composite = ppeer;
        this.parent = parent;
        ppeer.setOrientation( this.pOrientation == VERTICAL_SPLIT ? SWT.VERTICAL : SWT.HORIZONTAL );
        if (leftComponent != null) {
            addLeft();
            if (rightComponent != null)
                addRight();
            // No point adding right if we couldn't add left, since
            // SWT identifies left/right, top/bottom from parental order.
        }
        if (pResizeWeight != 0)
            setResizeWeight(pResizeWeight);
    }
    
    public swingwt.awt.Component getLeftComponent() {
        return leftComponent;
    }
    public void setTopComponent(swingwt.awt.Component comp) {
        setLeftComponent(comp);
    }
    public swingwt.awt.Component getTopComponent() {
        return leftComponent;
    }
    public swingwt.awt.Component getRightComponent() {
        return rightComponent;
    }
    public void setBottomComponent(swingwt.awt.Component comp) {
        setRightComponent(comp);
    }
    public swingwt.awt.Component getBottomComponent() {
        return rightComponent;
    }
    public int getOrientation() {
        return pOrientation;
    }
    public void setContinuousLayout(boolean b) {
    }
    public int getDividerSize() {
        return 2;
    }
    /** 
     * Unlike "real" Swing, we return the dividers actual
     * location since that's far more useful.
     * @return The divider's location
     */
    public int getDividerLocation() {
        if (leftComponent != null) {
            return leftComponent.getWidth();
        }
        return 0;
    }

    /**
     * Cannot set - it's fixed according to OS widgets.
     * This method doesn't do anything.
     */
    public void setDividerSize(int newSize) {
    }
    public void setOrientation(final int newOrientation) {
        if (pOrientation != HORIZONTAL_SPLIT && pOrientation != VERTICAL_SPLIT)
            throw new IllegalArgumentException("Invalid orientation.");
        pOrientation = newOrientation;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.setOrientation(newOrientation == VERTICAL_SPLIT ? SWT.VERTICAL : SWT.HORIZONTAL);
            }
        });
    }
   
    /** Again, we deviate slightly from Swing here - the resize weight does set
     *  the initial weighting of the splitpane, however it seems to do something
     *  odd in Swing I can't fathom (it limits the resizability of the split)
     */
    public void setResizeWeight(final double value) {
        pResizeWeight = value;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer) && leftComponent != null && rightComponent != null) {
                    int leftWeight = (int) (value * 10);
                    int rightWeight = 10 - leftWeight;
                    ppeer.setWeights(new int[] { leftWeight, rightWeight });
                }
            }
        });
    }
    public double getResizeWeight() {
        return pResizeWeight;
    }
    
    /**
     * Enable one touch expand collapse of the splitter
     */
    public void setOneTouchExpandable(boolean newValue) {
    }
    
    /**
     * reset splitter to sizes based on child window sizes
     */
    public void resetToPreferredSizes() {
        if (SwingWTUtils.isSWTControlAvailable(ppeer) && leftComponent != null && rightComponent != null) {
            setPreferredSize(
            new Dimension(
            leftComponent.getPreferredSize().width
            + rightComponent.getPreferredSize().width,
            leftComponent.getPreferredSize().height
            + rightComponent.getPreferredSize().height));
        }
    }
    
    /**
     * Set the current location of the splitter proportional
     * to the width as a percentage.
     */
    public void setDividerLocation(double proportionalLocation) {
        if (SwingWTUtils.isSWTControlAvailable(ppeer) && leftComponent != null) {
            setResizeWeight(proportionalLocation);
        }
    }
    
    public void setDividerLocation(int location) {
        if (SwingWTUtils.isSWTControlAvailable(ppeer) && leftComponent != null) {
            setResizeWeight( ((double)location) / ((double)getWidth()) );
        }
    }
    
}
