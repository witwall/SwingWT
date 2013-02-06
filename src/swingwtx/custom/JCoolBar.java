/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.custom;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import swingwt.awt.Dimension;

import swingwtx.swing.*;

/**
 * Not a real Swing component, but since Swing doesn't do them and SWT does, I
 * could hardly leave them out, could I?
 *
 * Just add components to this thing - for each component you add (JToolBar
 * for example), it will get it's own CoolItem drag handle to move it around.
 */
public class JCoolBar extends JComponent {
    
    protected CoolBar ppeer = null;
    protected boolean pFloatable = true;

    public JCoolBar() {}
    
    public Control getSWTPeer() { return ppeer; }
    
    /** 
     *  Overridden from awt.Container so we can create CoolItems
     *  for each added component
     */
    public swingwt.awt.Component add(swingwt.awt.Component c) {
        
        // Do the normal add stuff
        super.add(c);
        
        // For each component we add to the coolbar
        // we need to create a CoolItem and associate it with it.
        // =====
        // This is nasty to get your head round, but if neither component
        // has been created yet, the components will be stored in a buffer
        // by the superclass. It will call down to this version of the
        // add method when the time comes to add the components
        // for real (and the following code becomes active).
        if (SwingWTUtils.isSWTControlAvailable(c.getSWTPeer())) {
            CoolItem cool = new CoolItem(ppeer, SWT.NONE);
            cool.setControl(c.getSWTPeer());
            cool.setMinimumSize(c.getSWTPeer().computeSize(-1, -1));
            cool.setSize(c.getSWTPeer().computeSize(-1, -1));
        }
        
        return c;
    }
    
    public boolean isFloatable() { return pFloatable; }
    public void setFloatable(final boolean b) {
        pFloatable = b;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.setLocked(!b);   
                }
            });
        }
    }

    protected Dimension computePreferredSize() {
        org.eclipse.swt.graphics.Point p = peer.computeSize(-1, -1);
        return new Dimension(p.x, p.y);
    }

    /** Overridden - layouts are not allowed */
    public void setLayout(swingwt.awt.LayoutManager l) {  }
    public void setLayout(swingwt.awt.LayoutManager2 l) {  }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception { 
        descendantHasPeer = true;
        ppeer = new CoolBar(parent.getComposite(), SWT.NO_RADIO_GROUP | SWT.BORDER);
        peer = ppeer;
        composite = ppeer;
        this.parent = parent;
        ppeer.setLocked(!pFloatable);
        super.setSwingWTParent(parent);
    }
    
}
