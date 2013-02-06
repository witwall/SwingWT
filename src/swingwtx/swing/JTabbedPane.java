/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import swingwt.awt.Component;
import swingwt.awt.Container;
import swingwt.awt.Dimension;
import swingwtx.swing.SwingUtilities.SwingWTRunnable;
import swingwtx.swing.event.ChangeEvent;
import swingwtx.swing.event.ChangeListener;
import swingwtx.swing.plaf.TabbedPaneUI;


public class JTabbedPane extends JComponent implements SwingConstants {
    
    protected TabFolder ppeer = null;
    protected int pTabPlacement = TOP;
    protected Vector changeListeners = new Vector();
    
    /** Tab item cache until added to parent */
    protected Vector tabs = new Vector();
    
    /** The selection model */
    protected SingleSelectionModel model = null;

    
    /**
     * Creates an empty tabbed pane with a default single selection model and TOP placement.
     */
    public JTabbedPane() {    
    	this(TOP);
    }
    
    /**
     * Creates an empty <code>TabbedPane</code> with the specified tab placement
     * of either: <code>JTabbedPane.TOP</code>, <code>JTabbedPane.BOTTOM</code>,
     * <code>JTabbedPane.LEFT</code>, or <code>JTabbedPane.RIGHT</code>.
     *
     * @param tabPlacement the placement for the tabs relative to the content

     */
    public JTabbedPane(int tabPlacement) {    
    	setTabPlacement(tabPlacement);
    	setModel(new DefaultSingleSelectionModel());
    }

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
    
    public void add(swingwt.awt.Component component, Object modifiers) {
        addTab("", component);
    }
    public swingwt.awt.Component add(swingwt.awt.Component component) {
        addTab("", component);    
        return component;
    }
    public swingwt.awt.Component add(String title, swingwt.awt.Component component) {
        addTab(title, component);
        return component;
    }
    
    /** Warning: index is ignored. */
    public swingwt.awt.Component add(swingwt.awt.Component component, int index) {
        addTab("", component);
        return component;
    }
    
    /** Warning: index is ignored. */
    public void add(swingwt.awt.Component component, Object modifiers, int index) {
        addTab("", component);
    }
    
    public void addTab(String title, Icon icon, swingwt.awt.Component component, String tip, boolean enabled, boolean fromcache) {
        insertTab(title, icon, component, tip, tabs.size(), enabled, fromcache);
    }
    
    public void insertTab(String title, Icon icon, Component component, String tip, int index)
    {
        insertTab(title, icon, component, tip, index, true, false);
    }
    
    private void insertTab(final String title, final Icon icon, final Component component, final String tip, int index, final boolean enabled, boolean fromcache)
    {        
        // Create the cache entry
        if (! fromcache) {
            TabItemCache t = new TabItemCache();
            t.title = title;
            t.icon = icon;
            t.component = component;
            t.tip = tip;
            t.enabled = enabled;
            tabs.insertElementAt(t,index);
        	comps.add(component);
            
        } else {
        
	        final JTabbedPane me = this;
	        SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
	                if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return;
	
	                TabItem t = new TabItem(ppeer, SWT.NONE);
	                
	                // Make sure the component contained by this
	                // TabItem gets created now.
	                try {
	                    component.setSwingWTParent(me);
	                }
	                catch (Exception e) {
	                    e.printStackTrace();
	                }
	
	                if (title != null) t.setText(SwingWTUtils.removeHTML(title));
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
        
        // Swing always selects the first tab when a new tab is added
        if (this.getTabCount() > 0 && this.getSelectedIndex() != 0)
        	this.setSelectedIndex(0);
    }

	public Icon getIconAt(final int index) {
		if (!SwingWTUtils.isSWTControlAvailable(ppeer)) {
			TabItemCache t = (TabItemCache) tabs.get(index);
			return t.icon;
		}

		return (Icon) SwingUtilities.sync(new SwingWTRunnable() {
			public Object exec() {
				swingwt.awt.Image image = new swingwt.awt.Image();
				image.image = ppeer.getItem(index).getImage();
				return new ImageIcon(image);
			}
		});
	}    
    
    public void setIconAt(final int index, final Icon icon) { 
        final JTabbedPane pthis = this;
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) {
            TabItemCache t = (TabItemCache) tabs.get(index);
            t.icon = icon;
        }
        else
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).setImage(
                        SwingWTUtils.getSWTImageFromSwingIcon(pthis, icon)); 
                }
            });
    }

	public boolean isEnabledAt(final int index) {
		if (SwingWTUtils.isSWTControlAvailable(ppeer)) {

			return ((Boolean) SwingUtilities.sync(new SwingWTRunnable() {
				public Object exec() {
					return Boolean.valueOf(ppeer.getItem(index).getData("enabled").equals("true"));
				}
			})).booleanValue();
		}
		return ((TabItemCache) tabs.get(index)).enabled;
	} 
    
    public void setEnabledAt(final int index, final boolean b) { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).setData("enabled", (b ? "true" : "false"));
                    // If we're disabled - hide the tab's control. This is about all we
                    // can do with SWT (without writing our own tabbed interface of course).
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

		return ((Integer) SwingUtilities.sync(new SwingWTRunnable() {
			public Object exec() {
				return Integer.valueOf(ppeer.getItemCount());
			}
		})).intValue();
	}
    
    /**
     * Gets the index of the selected tab or -1 if no
     * tab is selected.
     *
     * @return
     */
    public int getSelectedIndex() {
    	return model.getSelectedIndex();
    }
    
    /**
     * Sets the selected tab to be the one with this index.
     *
     * @param index
     */
    public void setSelectedIndex(final int index) {
        if (SwingWTUtils.isSWTControlAvailable(ppeer))
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.setSelection(index);
                }
            });
        selectionChanged(index);
    }
    
    public swingwt.awt.Component getSelectedComponent() { 
        int i = getSelectedIndex();
        if (i != -1)
        	//return (Component) comps.get(i);
            return ((TabItemCache) tabs.get(i)).component;
        else
            return null;
    }
    
    

    public void setSelectedComponent(Component panel) {
        setSelectedIndex(indexOfComponent(panel));
    }

    public void removeTabAt(final int index) { 
    	
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).dispose(); 
                }
            });
        }
        comps.removeElementAt(index);
        tabs.removeElementAt(index);  
    }

	public String getTitleAt(final int index) {

		if (!SwingWTUtils.isSWTControlAvailable(ppeer))
			return ((TabItemCache) tabs.get(index)).title;

		return (String) SwingUtilities.sync(new SwingWTRunnable() {
			public Object exec() {
				return ppeer.getItem(index).getText();
			}
		});
	}
    
    public void setTitleAt(final int index, final String title) { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).setText(SwingWTUtils.removeHTML(title)); 
                }
            });
        }
        else {
            TabItemCache t = (TabItemCache) tabs.get(index);
            t.title = title;
        }
    }
    
    public String getToolTipTextAt(final int index) { 
    	
    	if (!SwingWTUtils.isSWTControlAvailable(ppeer))
			return ((TabItemCache) tabs.get(index)).tooltip;

		return (String) SwingUtilities.sync(new SwingWTRunnable() {
			public Object exec() {
				return ppeer.getItem(index).getToolTipText();
			}
		});
    }
    
    public void setToolTipTextAt(final int index, final String text) { 
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.getItem(index).setToolTipText(text); 
                }
            });
        }
        else {
            TabItemCache t = (TabItemCache) tabs.get(index);
            t.tooltip = text;
        }
    }
    
    public int indexOfComponent(final Component c) {
        final int[] ret = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    // Search items    
                    org.eclipse.swt.widgets.TabItem[] tabs = ppeer.getItems();
                    for (int i = 0; i < tabs.length; i++) {
                        if (tabs[i].getControl().equals(c.getSWTPeer())) {
                            ret[0] = i;
                            break;
                        }
                    }
                }
                else {
                    // Search cache
                    for (int i = 0; i < tabs.size(); i++) {
                        if (((TabItemCache) tabs.get(i)).component.equals(c)) {
                            ret[0] = i;
                            break;
                        }
                    }
                }
            }
        });
        return ret[0];
    }
    
    public int indexOfTab(final String name) {
        final int[] ret = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    // Search items    
                    org.eclipse.swt.widgets.TabItem[] tabs = ppeer.getItems();
                    for (int i = 0; i < tabs.length; i++) {
                        if (tabs[i].getText().equals(name)) {
                            ret[0] = i;
                            break;
                        }
                    }
                }
                else {
                    // Search cache
                    for (int i = 0; i < tabs.size(); i++) {
                        if (((TabItemCache) tabs.get(i)).title.equals(name)) {
                            ret[0] = i;
                            break;
                        }
                    }
                }
            }
        });
        return ret[0];   
    }
    
    public int indexOfTab(final Icon icon) {
        final int[] ret = new int[1];
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                // Search cache - we can only check the cache for Icons
                // as the objects are turned into something else for
                // use on the SWT peer.
                for (int i = 0; i < tabs.size(); i++) {
                    if (((TabItemCache) tabs.get(i)).icon.equals(icon)) {
                        ret[0] = i;
                        break;
                    }
                }
            }
        });
        return ret[0];   
    }
    
    public Component getComponentAt(int index) {
        return ((TabItemCache) tabs.get(index)).component;
    }
    
    public int getTabPlacement() { return pTabPlacement; }
    public void setTabPlacement(int place) { pTabPlacement = place; }
    
    public Control getSWTPeer() { return ppeer; }
    
    protected void postponeLayout() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                 layoutTab();    
            }
        });
    }
    
    protected void layoutTab() {
        Control p = ppeer.getItem(ppeer.getSelectionIndex()).getControl();
        if (p instanceof Composite && null != ((Composite) p).getLayout())
            ((Composite) p).layout(true);
        else
        	this.getSelectedComponent().invalidate();
    }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception { 
        descendantHasPeer = true;
        ppeer = new TabFolder(parent.getComposite(), SWT.NO_RADIO_GROUP | SwingWTUtils.translateSwingAlignmentConstant(pTabPlacement));
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
        
        // -- Tab is selected, may want to block it if we have it
        // flagged as disabled.
        ppeer.addSelectionListener(new org.eclipse.swt.events.SelectionListener() {
            public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
                // We don't send events if the tab is disabled
                if (checkTabEnabled(e)) {
                	
                	// Update layout of tab if we went to a new one
                	postponeLayout(); 
                    
                	// Update model and fire events
                	selectionChanged(ppeer.getSelectionIndex());
                	
                    // With Swing, components on a JTabbedPane receive a repaint
                    // event on focus gain.  For compatibility with code that relies on
                    // this, fire off a show() call here:
                    cachedComputedPreferredSize = null;
                    Component selected = getSelectedComponent();
                    selected.invalidate();
                    selected.show();
                   // ((Container) selected).debug_showContainmentTree();
                }   
            }
            public void widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent e) {}
        });
        
        // Make sure first one gets displayed
        if (ppeer.getItemCount() > 0)
            postponeLayout();
    }
    
    /**
     * Forces laying out of this container's child components again.
     */
    //@Override
    /*
    public void invalidateImpl() {
    	Component c = getSelectedComponent();
    	Dimension d = c.getPreferredSize();
    	c.setBounds(0, 20, d.width, d.height);
    }*/
    
    /**
     * Lets the model know about the new index and fires an event to change
     * listeners, if needed. 
     *
     * @param newIndex the newly selected tab index.
     */
    protected final void selectionChanged(int newIndex) {
    	// Update model
    	model.setSelectedIndex(newIndex);
        
        // Send change event if there are listeners
    	if (! changeListeners.isEmpty()) {
	        ChangeEvent ce = new ChangeEvent(model);
	        Iterator i = changeListeners.iterator();
	        while (i.hasNext()) {
	            ChangeListener l = (ChangeListener) i.next();
	            l.stateChanged(ce);
	        }
    	}
    }
    
    /** Returns true if the tab is enabled according to the data set on it.
     *  This should be called from a selection event, determining whether
     *  the user is allowed to go to it or not.
     */
    protected boolean checkTabEnabled(org.eclipse.swt.events.SelectionEvent e) {
        e.doit = (e.item.getData("enabled").toString().equals("true"));
        return e.doit;
    }

    public Component getComponent(int i) {
        return ((TabItemCache)tabs.get(i)).component;
    }

    public Component[] getComponents() {
        
        TabItemCache[] comp = (TabItemCache[]) tabs.toArray(new TabItemCache[0]);
        Component[] cps = new Component[comp.length];
        for (int i = 0; i < comp.length; i++) {
            cps[i] = comp[i].component;
        }
        return cps;
    }

    public void setComponentAt(int index, Component panel)
    {
        if (index < tabs.size()) {
	        TabItemCache oldItem = (TabItemCache)tabs.get(index);
	        oldItem.component = panel;
        }
        else {
            insertTab("Tab "+(index+1), null, panel, null, index, true, false);
        }
    }

    /**
     * Use the parent's preferred size if it was set by the
     * user; otherwise, use the standard preferred size
     * calculation.
     *
     * @return
     */
    //@Override
    public Dimension calculatePreferredSize() {
    	Container parent = getParent();
    	if (null != parent && parent.isPreferredSizeSet())
    		return parent.getPreferredSize();
    	
    	return super.calculatePreferredSize();
    }
    
    /**
     * Use the parent's preferred size if it was set by the
     * user; otherwise, compute from the peer.
     *
     * @return
     */
    //@Override
    protected Dimension computePreferredSize() {
    	Container parent = getParent();
    	if (null != parent && parent.isPreferredSizeSet())
    		return parent.getPreferredSize();
    	
    	return (Dimension) SwingUtilities.sync(new SwingWTRunnable() {
			public Object exec() {
				Component content = getSelectedComponent();
		        Dimension size = (content != null ? content.getPreferredSize() : new Dimension());
		        org.eclipse.swt.graphics.Point p = peer.computeSize(-1, -1);
		        size.width = Math.max(size.width, p.x); 
		        size.height += p.y;
		        return size;
			}
		});
    }

    public Dimension getMaximumSize() {
        if (isMaximumSizeSet())
            return super.getMaximumSize();
        return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
    }

    /**
     * Returns the tabbedpane's SingleSelectionModel.associated with this tabbedpane.
     *
     *
     */
    public SingleSelectionModel getModel() {
        return model;
    }

    /**
     * Sets the model to be used with this tabbedpane.
     * 
     * @param model the model to be used
     * 
     * description: The tabbedpane's SingleSelectionModel.
     *  
     */
    public void setModel(SingleSelectionModel model) {
    	// TODO: Fix the old model
        this.model = model;
        repaint();
    }
    
    public TabbedPaneUI getUI() {
        return (TabbedPaneUI)componentUI;
    }
    
    public void setUI(TabbedPaneUI ui) {
        componentUI = ui;
    }
}
