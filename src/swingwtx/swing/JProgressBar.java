/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class JProgressBar extends swingwtx.swing.JComponent implements SwingConstants {

    private ProgressBar ppeer = null;
    private int pMin = 0;
    private int pMax = 0;
    private int pValue = 0;
    private int pOrientation = HORIZONTAL;
    private boolean pIndeterminate = false; // Whether the progress bar actually counts
    
    public JProgressBar() {}
    public JProgressBar(int orient) { pOrientation = orient; }
    public JProgressBar(int min, int max) { pMin = min; pMax = max; }
    public JProgressBar(int orient, int min, int max) { pMin = min; pMax = max; pOrientation = orient; }
    
    public void setMaximum(final int max) {
        pMax = max;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeAsync(new Runnable() {
                public void run() {
                    ppeer.setMaximum(max);
                }
            });
        }
    }
    
    public void setMinimum(final int min) {
        pMin = min;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeAsync(new Runnable() {
                public void run() {
                    ppeer.setMinimum(min);
                }
            });
        }
    }
    
    public int getOrientation() { return pOrientation; }
    public void setOrientation(int orientation) { pOrientation = orientation; }
    public boolean isIndeterminate() { return pIndeterminate; }
    /** Set to true if you don't want the progress meter to count */
    public void setIndeterminate(boolean b) {
        if (pIndeterminate != b) {
            pIndeterminate = b;
            // Re-create the peer with the new style if necessary.
            if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                SwingUtilities.invokeSync(new Runnable() {
                    public void run() {
                        org.eclipse.swt.graphics.Rectangle rect = ppeer.getBounds();
                        componentOnlyDispose();
                        try {
                            setSwingWTParent(getParent());
                            ppeer.setBounds(rect);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public void setValue(final int value) {
        pValue = value;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeAsync(new Runnable() {
                public void run() {
                    ppeer.setSelection(value);
                }
            });
        }
    }

    public int getMaximum() { return pMax; }
    public int getMinimum() { return pMin; }
    public int getValue() { return pValue; }
    public double getPercentComplete() { return ( ((double) (getValue() - getMinimum())) / ((double) (getMaximum() - getMinimum())) * 100); }
    public void setStringPainted(boolean b) { }
    public boolean getStringPainted() { return false; }
    public void setString(String s) {}
    public String getString() { return ""; }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new ProgressBar(parent.getComposite(), SWT.BORDER |
	    (pIndeterminate ? SWT.INDETERMINATE : SWT.NONE) |
	    (!SWT.getPlatform().equals("win32") ? SWT.SMOOTH : SWT.NONE) |
	    (pOrientation == HORIZONTAL ? SWT.HORIZONTAL : SWT.VERTICAL));

        ppeer.setMaximum(pMax);
        ppeer.setMinimum(pMin);
        ppeer.setSelection(pValue);

        peer = ppeer;
        this.parent = parent;
    }

    public void setBorderPainted(boolean p) {
        // Nothing to do here
    }
}
