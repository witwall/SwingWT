/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingUtilities.SwingWTRunnable;
import swingwtx.swing.SwingWTUtils;

/**
 * note: MenuContainer methods are actually implemented in Window, contrary to AWT/Swing.
 * Still, add the interface here.
 */
public class Frame extends Window implements MenuContainer {
    
    public final static int NORMAL = 0;
    public final static int ICONIFIED = 1;
    public final static int MAXIMIZED_HORIZ = 2;
    public final static int MAXIMIZED_VERT = 4;
    public final static int MAXIMIZED_BOTH = MAXIMIZED_HORIZ | MAXIMIZED_VERT;
    
    protected boolean isResizable = true;
    protected int state = NORMAL;
    protected boolean isMax = false;
    
    public Frame() {
        this("");
    }
    public Frame(GraphicsConfiguration gc) { this(); }
    public Frame(String title) { super((Frame)null); setTitle(title); }
    
    protected int getSWTFrameType()
    {
        int SWTFrameType = SWT.SHELL_TRIM;
        if (isResizable)
            SWTFrameType |= SWT.RESIZE;
        else
            SWTFrameType &= ~SWT.RESIZE;
        return SWTFrameType;
    }
    
    public void setResizable(boolean b) { this.isResizable = b; }
    public boolean isResizable() { return isResizable; }
    
    public void remove(MenuComponent menuComponent) { pMenuBar.remove(menuComponent); }
    
    
    public void setExtendedState(final int state) {
        this.state = state;
        
        if (hasPeer()) 
	        SwingUtilities.invokeSync(new Runnable() {
	           public void run() {
	                if ( ((state & MAXIMIZED_BOTH) > 0) ||
	                    ((state & MAXIMIZED_HORIZ) > 0) ||
	                    ((state & MAXIMIZED_VERT) > 0) ) {
	                	((Shell) peer).setMaximized(true);
	                    isMax = true;
	                }
	                else {
	                	((Shell) peer).setMaximized(false);
	                    isMax = false;
	                }
	
	                if ((state & ICONIFIED) > 0) {
	                	((Shell) peer).setMinimized(true);
	                    isIcon = true;
	                }
	                else {
	                	((Shell) peer).setMinimized(false);
	                    isIcon = false;
	                }
	           }
	        });
    }
    
    public int getExtendedState() {
    	if (hasPeer())
    		return ((Integer) SwingUtilities.sync(new SwingWTRunnable() {
	            public Object exec() {
		            if (((Shell) peer).getMaximized())
		                return Integer.valueOf(MAXIMIZED_BOTH);
		            
		            if (((Shell) peer).getMinimized())
		                return Integer.valueOf(ICONIFIED);
		            
		            return Integer.valueOf(NORMAL);
	            }
	        })).intValue();
        
    	return state;
    }
    
    public void setUndecorated(boolean undecorated){}
    
    /**
     * Returns all frames known to be created (even if they are not opened).
     *
     * @return
     */
    public static Frame[] getFrames() {
    	final java.util.List framesList = new ArrayList();
    	
    	SwingUtilities.invokeSync(new Runnable() {
    		public void run() {
		    	Shell[] shells = SwingWTUtils.getDisplay().getShells();
		    	
		    	// Wrap the shels into frames
		    	
		    	for (int i = 0; i < shells.length; i++) {
		    		Object o = SwingWTUtils.wrap(shells[i]);
		    		if (o instanceof Frame)
		    			framesList.add(o);
		    	}
    		}
    	});
    	
    	// Compute the frames array
    	return (Frame[]) framesList.toArray(new Frame[0]);
    }
}
