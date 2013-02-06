/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   

*/

package swingwt.awt;

import swingwtx.swing.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.Shell;

public class Dialog extends Window {

    protected boolean isModal = true;
    protected boolean isResizable = true;

    public Dialog() { this((Frame) null, "", true); }
    public Dialog(String title) { this((Frame) null, title, true); }
    public Dialog(swingwt.awt.Dialog owner) { this(owner, "", true); }
    public Dialog(swingwt.awt.Dialog owner, boolean modal) { this(owner, "", modal); }
    public Dialog(swingwt.awt.Dialog owner, String title) { this(owner, title, true); }
    public Dialog(swingwt.awt.Dialog owner, String title, boolean modal) { this(owner, title, true, null); }
    public Dialog(swingwt.awt.Dialog owner, String title, boolean modal, GraphicsConfiguration gc) { super(owner, gc); setModal(modal); setTitle(title); }
    public Dialog(Frame owner) { this(owner, "", true); }
    public Dialog(Frame owner, boolean modal) { this(owner, "", modal); }
    public Dialog(Frame owner, String title) { this(owner, title, true); }
    public Dialog(Frame owner, String title, boolean modal) { this(owner, title, modal, null); }
    public Dialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) { super(owner, gc); setModal(modal); setTitle(title); }

    public void setResizable(boolean b) { this.isResizable = b; }
    public boolean isResizable() { return isResizable; }
    public void setModal(boolean b) { isModal = b; }
    public boolean isModal() { return isModal; }
    
    private static int customStyle = 0;
    public static void setCustomSWTShellStyle(int style) {
        customStyle = style;
    }
    
    protected int getSWTFrameType() {
        int SWTFrameType = SWT.DIALOG_TRIM | customStyle;
        SWTFrameType |= isModal ? SWT.APPLICATION_MODAL : SWT.MODELESS;
        SWTFrameType |= isResizable ? SWT.RESIZE : 0;
        return SWTFrameType;
    }

    /** Just an overridable method allowing subclasses to 
     *  execute code immediately after display.
     */
    protected void initDialog() {
        //override to make initial settings for a modal dialog
    }	

	/** 
	 * Overridden from Window to force private events dispatching for modality 
	 */
	public void setVisible(final boolean visible) {
		
		if (visible) {
			// note that create() will be executed in a sync call so it does not have
			// to be inside the subsequent invokeSync
			if (! hasPeer())
				create();
			
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
						// Display the peer
						((Shell) peer).open();

						// Assign focus
						peer.setFocus();

						// Make sure it displays ok
						repaintFix();

						// Overridable initial settings
						initDialog();

						// If it's a modal dialog, run an event loop
						// to cause blocking. The nice thing is this works
						// even if you're called on the existing dispatch thread.
						if (isModal)
							dispatchEvents();
				}
			});
		
		} else {
			// If the Window is already closing because the user hit the
			// X button, don't do anything
			if (isClosed)
				return;
			
			if (hasPeer())
				SwingUtilities.invokeSync(new Runnable() {
					public void run() {
						((Shell) peer).close();
					}
				});
		}
	}
    
	public void setUndecorated(boolean undecorated) {
	}
}