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

import swingwt.awt.Container;

public class JSeparator extends JSWTMenuComponent implements SwingConstants {
    
    private MenuItem mpeer = null;
    private Label    lpeer = null;
    private Shell shell = null;
    
    public JSeparator() { }
    public JSeparator(int orientation) {}
    
    public int getOrientation() { return HORIZONTAL; }
    public void setOrientation(int orientation) {}
    
    public void setSwingWTParent(Menu parent, Shell shell) throws Exception { 
        this.shell = shell;
        mpeer = new MenuItem(parent, SWT.SEPARATOR);
        peer = mpeer;
    }
    
    /**
     * JSeparators can be added inside of other containers too
     * @param parent
     * @param shell
     * @throws Exception
     */
    public void setSwingWTParent(Container parent, Shell shell) throws Exception { 
        this.shell = shell;
        lpeer = new Label(parent.getComposite(), SWT.SEPARATOR );
    }
 
    public boolean isSelected() {
        return false;
    }
    
    public void setSelected(boolean b) {
    }

    public void registerEvents() {
    	if(peer != null) {
    		super.registerEvents();
    	}
    }
    
    public void assertCachedProperties() {
    	if(peer != null) {
    		super.assertCachedProperties();
    	}
    }
    public void dispose() { 
    	if(peer != null) {
    		super.dispose();
    	}
    	
    	if(lpeer != null) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    if (SwingWTUtils.isSWTMenuControlAvailable(peer)) 
                        { lpeer.dispose(); lpeer = null; }
                }
             });
    		
    	}    	
   }

}
