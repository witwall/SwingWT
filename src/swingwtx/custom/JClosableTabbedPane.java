/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.custom;

import org.eclipse.swt.custom.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import swingwtx.swing.*;
import swingwtx.swing.event.*;
import swingwtx.custom.event.*;

import java.util.*;

/**
 * Not really a swing component, but I map it here because I use it
 * for JInternalFrame/JDesktopPane. It's basically JTabbedPane, but with
 * closable buttons - something that Swing really ought to offer!
 */
public class JClosableTabbedPane extends JComponent implements SwingConstants {
    
    protected CTabFolderSwingWT ppeer = null;
    
    /** Tab item cache until added to parent */
    protected Vector tabs = new Vector();
    protected Vector tabCloseListeners = new Vector();
    protected int pTabPlacement = TOP;
    protected Vector changeListeners = new Vector();
    protected int pSelectedIndex = -1;
    
    /** Thread safe accessor */
    private int iRetVal = 0;
    private String sRetVal = "";
    private Object retval;
    
    public JClosableTabbedPane() {    }
    
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }
    
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }
    
    public void addTab(String title, swingwt.awt.Component component) {
        addTab(title, null, component);
    }
    
    public void addTab(String title, Icon icon, swingwt.awt.Component component) {
        addTab(title, icon, component, null, true);
    }
    
    public void addTab(final String title, final Icon icon, final swingwt.awt.Component component, final String tip) {
        addTab(title, icon, component, tip, true);
    }
    
    public void addTab(final String title, final Icon icon, final swingwt.awt.Component component, final String tip, final boolean enabled) {
        addTab(title, icon, component, tip, enabled, false);
    }
    
    public void addTab(final String title, final Icon icon, final swingwt.awt.Component component, final String tip, final boolean enabled, boolean fromcache) {
        
        if (!fromcache) {
            // Create the cache entry
            TabItemCache t = new TabItemCache();
            t.title = title;
            t.icon = icon;
            t.component = component;
            t.tip = tip;
            t.enabled = enabled;
            tabs.add(t);
            t = null;
        }
        
        final JClosableTabbedPane me = this;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return;

                CTabItem t = new CTabItem(ppeer, SWT.NONE);

                // Make sure the component contained by this
                // TabItem gets created now
                try {
                    component.setSwingWTParent(me);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                if (title != null) t.setText(title);
                if (icon != null) t.setImage(SwingWTUtils.getSWTImageFromSwingIcon(me, icon));
                
                // Only display the component if it's enabled
                if (component != null && enabled) t.setControl(component.getSWTPeer());
                
                if (tip != null) t.setToolTipText(tip);
                t.setData("enabled", (enabled ? "true" : "false"));

                // Display the thing
                postponeLayout();
            }
        });
        
    }
    
    public Icon getIconAt(final int index) {
        if (ppeer == null) {
            TabItemCache t = (TabItemCache) tabs.get(index);
            return t.icon;
        }
        else {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    swingwt.awt.Image image = new swingwt.awt.Image();
                    image.image = ppeer.getItem(index).getImage();
                    retval = new ImageIcon(image);
                }
            });
            return (Icon) retval;
        }
    }
    public void setIconAt(final int index, final Icon icon) { 
        TabItemCache t = (TabItemCache) tabs.get(index);
        t.icon = icon;
        final JClosableTabbedPane pthis = this;
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).setImage(SwingWTUtils.getSWTImageFromSwingIcon(pthis, icon)); 
                }
            });
    }
    
    public void addTabCloseListener(TabCloseListener l) {
        tabCloseListeners.add(l);
    }
    
    public void removeTabCloseListener(TabCloseListener l) {
        tabCloseListeners.remove(l);
    }

    public void setEnabledAt(final int index, final boolean b) { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).setData("enabled", (b ? "true" : "false"));
                    // If we're disabled - hide the tab's control. This is about all we
                    // can do with SWT.
                    if (!b)
                        ppeer.getItem(index).setControl(null);
                    else
                        ppeer.getItem(index).setControl( ((TabItemCache) tabs.get(index)).component.getSWTPeer() );
                }
            });
        }
        else {
            TabItemCache t = (TabItemCache) tabs.get(index);
            t.enabled = b;
        }
    }
    
    public int getTabCount() { 
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) 
            return tabs.size(); 
        else {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    iRetVal = ppeer.getItemCount(); 
                }
            });
            return iRetVal;
        }
    }

    public int getSelectedIndex() {
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    iRetVal = ppeer.getSelectionIndex(); 
                }
            });
        }
        else
            iRetVal = -1;
        return iRetVal;
    }
    
    public swingwt.awt.Component getSelectedComponent() { 
        int i = getSelectedIndex();
        if (i != -1)
            return ((TabItemCache) tabs.get(i)).component;
        else
            return null;
    }
    
    public void setSelectedIndex(final int index) { 
        pSelectedIndex = index;
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.setSelection(index); 
                }
            });
    }
    
    public void removeTabAt(final int index) { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    tabs.removeElementAt(index);
                    ppeer.getItem(index).dispose(); 
                }
            });
        }
        else
            tabs.removeElementAt(index);  
    }
    
    public String getTitleAt(final int index) { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    sRetVal = ppeer.getItem(index).getText(); 
                }
            });
            return sRetVal;
        }
        else {
            TabItemCache t = (TabItemCache) tabs.get(index);
            return t.title;
        }
    }
    public void setTitleAt(final int index, final String title) { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).setText(title); 
                }
            });
        }
        else {
            TabItemCache t = (TabItemCache) tabs.get(index);
            t.title = title;
        }
    }
    public int getTabPlacement() { return pTabPlacement; }
    public void setTabPlacement(int place) { pTabPlacement = place; }
    
    public Control getSWTPeer() { return ppeer; }
    
    protected void processTabClosing(CTabFolderEvent e) {
        // See which one matches!
        CTabItem[] items = ppeer.getItems();
        for (int i = 0; i < items.length; i++) {
            if (e.item.equals(items[i])) {
                Iterator iter = tabCloseListeners.iterator();
                while (iter.hasNext()) {
                    TabCloseListener l = (TabCloseListener) iter.next();
                    e.doit = l.tabClosed(i);
                }
            }
        }
    }
    
    /**
     * Process change events - fired when the tab changes
     */
    protected void processChangeEvent(ChangeEvent e) {
        Iterator i = changeListeners.iterator();
        while (i.hasNext()) {
            ChangeListener l = (ChangeListener) i.next();
            l.stateChanged(e);
        }
    }
    
    protected void postponeLayout() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                layoutTab();    
            }
        });
    }
    
    protected void layoutTab() {
        
        int i = ppeer.getSelectionIndex();
        if (i < 0 || i >= ppeer.getItems().length) return;
        
        Control p = ppeer.getItem(ppeer.getSelectionIndex()).getControl();
        if (p instanceof Composite)
            ((Composite) p).layout(true);
        else
            p.redraw();
    }
    
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception { 
        descendantHasPeer = true;
        ppeer = new CTabFolderSwingWT(parent.getComposite(), SWT.NO_RADIO_GROUP | SwingWTUtils.translateSwingAlignmentConstant(pTabPlacement));
        peer = ppeer;
        composite = ppeer;
        this.parent = parent;
        
        // Add any cached items
        if (tabs.size() > 0) {
            Object[] tabso = tabs.toArray();
            for (int i = 0; i < tabso.length; i++) {
                TabItemCache t = (TabItemCache) tabso[i];
                addTab(t.title, t.icon, t.component, t.tip, t.enabled, true);
            }
        }
        
        // Make sure the selected index is right
        if (pSelectedIndex != -1)
            ppeer.setSelection(pSelectedIndex);
        
        ppeer.setUnselectedCloseVisible(true);
        ppeer.setUnselectedImageVisible(true);
        
        // Events
        
        // -- Tab closing
        
        /* -- Correct code, but it doesn't work because the close
         *    button doesn't appear. VERY frustrating as we get
         *    deprecated warnings on compile.
         *
        ppeer.addCTabFolder2Listener(new CTabFolder2Listener() {
            public void showList(CTabFolderEvent event) {}
            public void restore(CTabFolderEvent event) {}
            public void maximize(CTabFolderEvent event) {}
            public void minimize(CTabFolderEvent event) {}
            public void close(CTabFolderEvent event) { processTabClosing(event); }
        });*/
        
        ppeer.addCTabFolderListener(new CTabFolderAdapter() {
		public void itemClosed(CTabFolderEvent event) {
			processTabClosing(event);
		}
	});

        // -- Tab is selected, may want to block it if we have it
        // flagged as disabled.
        final JClosableTabbedPane me = this;
        ppeer.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                // We don't send events if the tab is disabled
                if (checkTabEnabled(e)) {    
                    postponeLayout(); // Update layout of tab if we went to a new one
                    // Send change event
                    ChangeEvent ce = new ChangeEvent(me);
                    processChangeEvent(ce);
                }   
            }
            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {}
        });
        
        // Make sure whatever is selected gets displayed
        if (ppeer.getItemCount() > 0)
            postponeLayout();
        
    }
    
    /** Returns true if the tab is enabled according to the data set on it.
     *  This should be called from a selection event, determining whether
     *  the user is allowed to go to it or not.
     */
    protected boolean checkTabEnabled(org.eclipse.swt.events.SelectionEvent e) {
        e.doit = (e.item.getData("enabled").toString().equals("true"));
        return e.doit;
    }
    
}

