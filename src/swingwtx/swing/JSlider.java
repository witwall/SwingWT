/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Scale;

import swingwtx.accessibility.Accessible;
import swingwtx.swing.event.ChangeEvent;
import swingwtx.swing.event.ChangeListener;


public class JSlider extends JComponent implements SwingConstants, Accessible {
    
    protected int orientation = SwingConstants.HORIZONTAL;
    protected int min = 0;
    protected int max = 100;
    protected int value = 50;
    protected int thumb = 10;
    protected Dictionary labelTable = new Hashtable();
    
    protected Vector changeListeners = new Vector();
    protected Scale ppeer = null;
    
    private int threadSafeInt = 0;
    
    public JSlider() { this(SwingConstants.HORIZONTAL, 0, 100, 50); }
    public JSlider(int orientation) { this(orientation, 0, 100, 50); }
    public JSlider(int min, int max) { this(SwingConstants.HORIZONTAL, min, max, (min + max) / 2); }
    public JSlider(int min, int max, int value) { this(SwingConstants.HORIZONTAL, min, max, value); }
    public JSlider(int orientation, int min, int max, int value) {
        this.orientation = orientation;
        this.min = min;
        this.max = max;
        this.value = value;
    }

    /**
     * 
     * TODO Comment!!
     *
     * @param brm
     */
    public JSlider(BoundedRangeModel brm) {
    	
    }
    public void setMinimum(final int minimum){
        this.min=minimum;
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
            SwingUtilities.invokeSync(new Runnable() { 
                public void run() {
                    //Handles SWT's lack of setting negative
                    //values
                    if (min<0){
                        ppeer.setMinimum(0);
                    }
                    else{
                        ppeer.setMinimum(min);
                    }
                }
            });    
    }    

    public void setMaximum(final int maximum){
        this.max=maximum;
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
            SwingUtilities.invokeSync(new Runnable() { 
                public void run() {
                    //Handles SWT's lack of setting negative
                    //values
                    if (min<0){
                        ppeer.setMaximum(max-min);
                    }
                    else{
                        ppeer.setMaximum(max);
                    }
                }
            });    
    }    

    public int getValue() {
        if (!SwingWTUtils.isSWTControlAvailable(ppeer))
            return value;
        SwingUtilities.invokeSync(new Runnable() { 
            public void run() {
                threadSafeInt = ppeer.getSelection();
            }
        });     
        //Handles SWT's lack of setting negative
        //values
        if (min<0){
            return threadSafeInt + min;
        }
        return threadSafeInt;
    }
    
    public void setValue(final int value) {
        this.value = value;
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
            SwingUtilities.invokeSync(new Runnable() { 
                public void run() {
                    //Handles SWT's lack of setting negative
                    //values
                    if (min<0){
                        ppeer.setSelection(value-min);
                    }
                    else{
                        ppeer.setSelection(value);
                    }
                }
            });
        fireStateChanged();
    }

    public Hashtable createStandardLabels(int increment) {
        Hashtable h = new Hashtable(10);
        for (int i = 0; i <= max; i += increment)
            h.put( new Integer(i), new Integer(i) );    
        return h;
    }
    
    public Hashtable createStandardLabels(int increment, int start) {
        Hashtable h = new Hashtable(10);
        for (int i = start; i <= max; i += increment)
            h.put( new Integer(i), new Integer(i) );    
        return h;
    }
    
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }
    
    protected void fireStateChanged() {
        for (int i = 0; i < changeListeners.size(); i++) {
            ((ChangeListener) changeListeners.get(i)).stateChanged(new ChangeEvent(this));
        }
    }
      
    public int getExtent() { return thumb; }
    public void setExtent(int extent) { 
        thumb = extent;
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
            SwingUtilities.invokeSync(new Runnable() { 
                public void run() {
                    ppeer.setPageIncrement(thumb);
                }
            });    
    }
    public boolean getInverted() { return false; }
    public boolean isInverted() { return false; }
    public void setInverted(boolean b) {}
    public int getMajorTickSpacing() { return getExtent(); }
    public void setMajorTickSpacing(int spacing) { setExtent(spacing); }
    public int getMinorTickSpacing() { return getExtent() / 2; }
    public void setMinorTickSpacing(int spacing) { }
    public boolean getSnapToTicks() { return true; }
    public boolean getSnapToValue() { return true; }
    public boolean isSnapToTicks() { return true; }
    public boolean isSnapToValue() { return true; }
    public void setSnapToTicks(boolean b) {}
    public void setSnapToValue(boolean b) {}
    public boolean getPaintTicks() { return true; }
    public boolean isPaintTicks() { return true; }
    public void setPaintTicks(boolean b) {}
    public boolean getPaintTrack() { return true; }
    public boolean isPaintTrack() { return true; }
    public void setPaintTrack(boolean b) {}
    public boolean getPaintLabels() { return true; }
    public boolean isPaintLabels() { return true; }
    public void setPaintLabels(boolean b) {}
    public Dictionary getLabelTable() { return labelTable; }
    public void setLabelTable(Dictionary table) { labelTable = table; }
    
    /** Overriden to calculate non-realised
     *  preferred size.
     */
    protected swingwt.awt.Dimension calculatePreferredSize() {
        swingwt.awt.Dimension size = new swingwt.awt.Dimension(200, 25);
        setSize(size);
        return size;
    }
    
    public swingwt.awt.Dimension getMaximumSize() {
        if (isMaximumSizeSet())
            return super.getMaximumSize();
        swingwt.awt.Dimension size = getPreferredSize();
        if (orientation == SwingConstants.HORIZONTAL)
            size.width = 4096;
        else
            size.height = 4096;
        return size;
    }

    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new Scale(parent.getComposite(), 
            (border instanceof swingwtx.swing.border.EmptyBorder ? SWT.NONE : SWT.BORDER ) |
            (orientation == SwingConstants.HORIZONTAL ? SWT.HORIZONTAL : SWT.VERTICAL)
            );
        setMaximum(max);
        setMinimum(min);
        setValue(value);
        ppeer.setPageIncrement(thumb);
        
        // It's a bit small by default
        if (orientation == SwingConstants.HORIZONTAL && pPrefSize == null)
            setPreferredSize(new swingwt.awt.Dimension(200, 20));
        if (orientation == SwingConstants.VERTICAL && pPrefSize == null)
            setPreferredSize(new swingwt.awt.Dimension(20, 200));
        
        // Events for slider being dragged
        ppeer.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                fireStateChanged();    
            }
        });
        
        peer = ppeer;
        this.parent = parent;
    }
    
}
