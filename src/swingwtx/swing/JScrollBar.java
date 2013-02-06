/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   
*/

package swingwtx.swing;

import org.eclipse.swt.SWT;

import swingwt.awt.*;
import swingwt.awt.event.*;

import java.util.*;

public class JScrollBar extends JComponent implements Adjustable {
    
    protected org.eclipse.swt.widgets.Slider ppeer = null;
    
    public final static int VERTICAL = 0;
    public final static int HORIZONTAL = 1;
    
    protected int orientation = VERTICAL;
    protected int value = 0;
    protected int extent = 100;
    protected int blockIncrement = 10;
    protected int increment = 5;
    protected int min = 0;
    protected int max = 100;
    protected Vector adjustmentListeners = new Vector();

    public JScrollBar() { this(VERTICAL); }
    public JScrollBar(int orientation) { this(orientation, 0, 5, 0, 100); }
    public JScrollBar(int orientation, int value, int extent, int min, int max) { 
        this.orientation = orientation;
        this.value = value;
        this.extent = extent;
        this.min = min;
        this.max = max;
    }
    
        
    public void addAdjustmentListener(AdjustmentListener l) {
        adjustmentListeners.add(l);    
    }
    public void removeAdjustmentListener(AdjustmentListener l) {
        adjustmentListeners.remove(l);    
    }
    
    public int getOrientation() { return orientation; }
    public void setOrientation(int orientation) { 
        this.orientation = orientation;
    }
    public int getUnitIncrement(int direction) { return increment; }
    public void setUnitIncrement(int unitIncrement) { increment = unitIncrement; updateBar(); }
    public int getBlockIncrement(int direction) { return blockIncrement; }
    public void setBlockIncrement(int blockIncrement) { this.blockIncrement = blockIncrement; updateBar();}
    public int getUnitIncrement() { return increment; }
    public int getBlockIncrement() { return blockIncrement; }
    
    private int retval = 0;
    public int getValue() {
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return value;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                retval = ppeer.getSelection();    
            }
        });
        return retval;
    }
    
    public void setValue(int value) { this.value = value; updateBar(); }
    public int getVisibleAmount() { return extent; }
    public void setVisibleAmount(int extent) { this.extent = extent; updateBar(); }
    public int getMinimum() { return min; }
    public void setMinimum(int minimum) {}
    public int getMaximum() { return max; }
    public void setMaximum(int maximum) { max = maximum; updateBar(); }
    public boolean getValueIsAdjusting() { return false;}
    public void setValueIsAdjusting(boolean b) { }
    
    public void setValues(int newValue, int newExtent, int newMin, int newMax) {
        extent = newExtent;
        value = newValue;
        min = newMin;
        max = newMax;
        updateBar();
    }
    
    protected void updateBar() {
        SwingUtilities.invokeSync( new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    ppeer.setIncrement(increment);
                    ppeer.setPageIncrement(blockIncrement);
                    ppeer.setThumb(increment);
                    ppeer.setMaximum(max);
                    ppeer.setMinimum(min);
                    ppeer.setSelection(value);
                }
            }
        });
    }

    /**
     * This is actually a special case - for some reason when we use vertical
     * scrollbars with a border layout, they appear at half the size they
     * should on MacOSX. I'm not sure if this is because the SWT peer.computeSize() 
     * call is returning the wrong value for Slider controls or the container
     * is reporting a size that's too large. Either way, this fixes it, but needs
     * to be flagged in case the SWT boys fix things in the meantime.
     * QUIRK:
     */
    public Dimension getPreferredSize() {
        if (SwingWTUtils.osIsMacOSX() &&
            orientation == VERTICAL &&
            (parent instanceof JScrollPane)) {
                Dimension d = super.getPreferredSize();
                d.width = d.width + 70;
                return d;
        }
        else
            return super.getPreferredSize();
    }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new org.eclipse.swt.widgets.Slider(parent.getComposite(), orientation == HORIZONTAL ? SWT.HORIZONTAL : SWT.VERTICAL);
        peer = ppeer;
        this.parent = parent;
        updateBar();
        registerBarEvents();
    }
    
    protected void registerBarEvents() {
        final JScrollBar me = this;
        ppeer.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                AdjustmentEvent ev = new AdjustmentEvent(me, 0, AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED, ppeer.getSelection());
                processAdjustmentEvent(ev);
            }
        });
    }
    
    protected void processAdjustmentEvent(AdjustmentEvent e) {
        for (int i = 0; i < adjustmentListeners.size(); i++) {
            ((AdjustmentListener) adjustmentListeners.get(i)).adjustmentValueChanged(e);    
        }
    }
    
}
