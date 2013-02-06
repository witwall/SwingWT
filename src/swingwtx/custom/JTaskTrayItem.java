/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net



*/

package swingwtx.custom;

import swingwtx.swing.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

/**
 * Wraps up an item on the system task tray.
 * For Win32 - the task tray, for GTK2/XFCE4 the
 * GNOME notification area. 
 *
 * Additionally, allows a JPopupMenu to be assigned
 * to popup when the user right clicks on the item.
 * 
 * An ActionEvent is fired for a left click on the
 * tray item.
 *
 * @author  Robin Rawson-Tetley
 */
public class JTaskTrayItem extends JComponent {
    
    protected TrayItem ppeer = null;
    protected Icon pIcon = null;
    protected JPopupMenu popup = null;
    
    public JTaskTrayItem() { this(null); }
    public JTaskTrayItem(Icon icon) { this(icon, ""); }
    public JTaskTrayItem(Icon icon, String tooltiptext) { this(icon, tooltiptext, null); }
    public JTaskTrayItem(Icon icon, String tooltiptext, JPopupMenu menu) {
         super();    
         pIcon = icon;
         pToolTipText = tooltiptext;
         popup = menu;
    }
    
    /**
     * Sets the image used for the tray item
     * @param icon The icon to display in the tray
     */
    public void setIcon(Icon icon) {
        pIcon = icon;
        final JTaskTrayItem pthis = this;
        // Not a widget, so can't use SwingWTUtils.isSWTControlAvailable()
        if (ppeer != null)
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.setImage( SwingWTUtils.getSWTImageFromSwingIcon(pthis, pIcon) );
                }
            });
    }
    
    
    /**
     * Sets the tooltip used on the tray icon
     * @param text The tooltip text
     */
    public void setToolTipText(String text) {
        pToolTipText = text;    
        // Not a widget, so can't use SwingWTUtils.isSWTControlAvailable()
        if (ppeer != null)
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.setToolTipText(pToolTipText);
                }
            });
    }
    
    /**
     * Returns the text used by the tray icon
     * @return The tray icon tooltip text
     */
    public String getToolTipText() {
        return pToolTipText;    
    }
    
    /**
     * Sets the popup menu used by the tray icon.
     * @param menu The popup
     */
    public void setPopupMenu(JPopupMenu menu) {
        popup = menu;    
    }
    
    /**
     * Returns the popup menu
     * @return The popup menu used by the tray icon
     */
    public JPopupMenu getPopupMenu() {
        return popup;    
    }
    
    /**
     * Determines whether the tray item is visible.
     * @param b Whether to show the tray item or not
     */
    public void setVisible(final boolean b) {
        
        final JTaskTrayItem pthis = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {

                if (b) {
                    if (ppeer != null)
                        ppeer.dispose();
                    Tray tray = SwingWTUtils.getDisplay().getSystemTray();
                    if(tray == null) return;
                    ppeer = new TrayItem(tray, SWT.NONE);
                    if (pIcon != null) ppeer.setImage( SwingWTUtils.getSWTImageFromSwingIcon(pthis, pIcon) );
                    if (!pToolTipText.equals("")) ppeer.setToolTipText(pToolTipText);
                    registerEvents();
                }
                else
                {
                    if (ppeer != null) {
                        ppeer.dispose();    
                        ppeer = null;
                    }
                }
                
            }
        });
        
    }
    
    public void registerEvents() {
        
        // Mapped to action listener
        ppeer.addListener (SWT.Selection, new Listener () {
            public void handleEvent (Event event) {
                processActionEvent(0);
            }
	});

        // Used to display any popup menu we have assigned
	ppeer.addListener (SWT.MenuDetect, new Listener () {
            public void handleEvent (Event event) {
                
                // Work out whether we need to create the popup
                boolean needToCreate = popup == null;
                if (popup.getSWTMenu() == null)
                    needToCreate = true;
                if (needToCreate)
                    // Create the popup (use the dummy shared frame for
                    // dialogs as the parent).
                    popup.setSwingWTParent( JOptionPane.getSharedFrame() );
                
                // Show the menu
                popup.getSWTMenu().setVisible(true);
            }
	});
        
    }
    
    /** Display the tray item */
    public void show() { setVisible(true); }
    /** Hide the tray item */
    public void hide() { setVisible(false); }
    
}
