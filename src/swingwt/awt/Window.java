/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwt.awt;

import java.util.Iterator;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swingwtx.swing.JButton;
import swingwtx.swing.JMenuBar;
import swingwtx.swing.JRootPane;
import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingUtilities.SwingWTRunnable;
import swingwtx.swing.SwingWTUtils;

public class Window extends Container {
    
    //protected Shell peer = null;
    protected Display display = null;
    protected Vector windowListeners = new Vector();
    protected boolean isClosed = false;
    protected boolean hasBeenOpened = false;
    protected boolean isIcon = false;
    
    protected JRootPane rootPane = null;
    
    protected String title;
    protected Image iconImage;
    protected JMenuBar pMenu;
    protected MenuBar pMenuBar;
    protected Dimension delayPack;
    protected int frameWidth, frameHeight;
    
    /** Number of pixels to allow for frame decoration during pack() */
    protected final static int FRAME_DECORATION_HEIGHT = 34;
    /** Number of pixels to allow for frame decoration during pack() */
    protected final static int FRAME_DECORATION_WIDTH = 8;
    
    /** Does nothing */
    GraphicsConfiguration graphicsConfig = null;
    
    public Window(Frame parent) {
        this.parent = parent;
        init();
    }
    public Window(Window parent) {
        this.parent = parent;
        init();
    }
    public Window(Window parent, GraphicsConfiguration gc) {
        this.parent = parent;
        init();
    }
    
    private void init() {
        graphicsConfig = (parent == null ? null : parent.getGraphicsConfiguration());
        rootPane = new JRootPane(this);
        rootPane.parent = this;
    }
    
    // Default frame type for direct Window instances
    protected int getSWTFrameType() {
        return SWT.NO_TRIM;
    }
    
    public void createSWTWindow(){
    	create();
    }
    
    protected void create() {
        create(getSWTFrameType());
    }
    
    /**
     * Actually does the work of creating the Window peer
     * All the descendants of Window call this with an appropriate SWT type
     * as part of their constructor.
     *
     * A bit cheaty, but we use the Swing JRootPane stuff here as well
     * so it only needs doing once and subclasses can use it if they want it.
     */
    protected void create(final int SWTFrameType) {
        SwingWTUtils.incrementWindowReferences();
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                createImpl(SWTFrameType);
                queuedValidate();
            }
        });
    }
    
    /**
     * The actual create implementation. This is just so we don't
     * have to muck about inside anonymous inner classes.
     *
     * This routine creates the peer and rootpane ready for use.
     *
     * @param SWTFrameType The SWT constant to use when creating the Shell
     */
    private void createImpl(int SWTFrameType) {
        display = SwingWTUtils.getDisplay();
        
        if (parent == null)
        	peer = new Shell(display, SWTFrameType);
        else
        	peer = new Shell( (Shell)parent.getSWTPeer(), SWTFrameType);
        
        // register the shell
        SwingWTUtils.registerWrapper(this, peer);
        
        Shell shell = (Shell) peer;
        composite = shell;
        shell.setLayout(new swingwt.awt.swtcustomlayout.SWTBorderLayout());
        shell.setLayoutData(swingwt.awt.swtcustomlayout.SWTBorderLayout.CENTER);
        shell.addShellListener(createShellListener());
        
        assertCachedProperties();
        
		try {
	        
			// This will trigger the whole GUI creation on the root pane.
			rootPane.setSwingWTParent(this);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        isClosed = false;
    }
    

    public void assertCachedProperties() {
    	// TODO - should we call super.assertCachedProperties() ?
        if (hasPeer()) {
        	Shell shell = (Shell) peer;
	        if (title != null) shell.setText(title);
	        if (iconImage != null) shell.setImage(iconImage.image);
	        if (pMenu != null) setJMenuBar(pMenu);
	        if (pLocation != null) 
	        	setPeerLocation(pLocation);
	        else
	        	setPeerLocation(new Point(0, 0));
	        if (pSize != null) setSizeImpl(pSize);
        }
    }

    /*
     * Thiago - I fixed Component.getLocationOnScreen, so I am not sure
     * this code is necessary.
     * TODO - remove this if it proves to be unneeded.
    public Point getLocationOnScreen() {
        return getLocation();
    }
      */  
    
    
    /** Overridden to point to getContentPane() rather than throwing an error */
    public Component add(swingwt.awt.Component c) { rootPane.getContentPane().add(c); return c; }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void add(swingwt.awt.Component c, Object layoutModifier) { rootPane.getContentPane().add(c, layoutModifier); }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void remove(swingwt.awt.Component c) { rootPane.getContentPane().remove(c); }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public LayoutManager getLayout() {
        return rootPane.getContentPane().getLayout();
    }
    /** Overridden to add mouse listeners to the content pane instead */
    public void addMouseListener(swingwt.awt.event.MouseListener l) {
        rootPane.getContentPane().addMouseListener(l);
    }
    /** Overridden to remove mouse listeners from the content pane instead */
    public void removeMouseListener(swingwt.awt.event.MouseListener l) {
        rootPane.getContentPane().removeMouseListener(l);
    }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void setLayout(LayoutManager l) {
        rootPane.getContentPane().setLayout(l);
    }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void setLayout(LayoutManager2 l) {
        rootPane.getContentPane().setLayout(l);
    }
    
    public swingwt.awt.Color getBackground() { return rootPane.getBackground(); }
    public void setBackground(swingwt.awt.Color c) { rootPane.setBackground(c); }
    public boolean isBackgroundSet() { return rootPane.isBackgroundSet(); }
    public swingwt.awt.Color getForeground() { return rootPane.getForeground(); }
    public void setForeground(swingwt.awt.Color c) { rootPane.setForeground(c); }
    public boolean isForegroundSet() { return rootPane.isForegroundSet(); }

    
    public void pack() {
    	
    	final Dimension d = rootPane.getContentPane().getPreferredSize();
        rootPane.setSize(d);
        rootPane.getContentPane().setSize(d);
    	
        if (hasPeer()) {
        	SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
	            	pSize = new Dimension(d.width + frameWidth, d.height + frameHeight);
	            	peer.setSize(pSize.width, pSize.height);
	            }
	        });
        } else {
            // Set pSize to some valid, but slightly bogus value - since we don't know what the actual
            // frameWidth and frameHeight will be once SWT makes the window. This will be adjusted when
            // the window gets created via delayPack.
            pSize = new Dimension(d.width + FRAME_DECORATION_WIDTH, d.height + FRAME_DECORATION_HEIGHT);
            // Save the content pane preferred size for when the shell is created
            delayPack = new Dimension(d);
        }
    }

    public void setLocationRelativeTo(Component c) {
        // Center the Window
        Dimension paneSize = getSize();
        Dimension screenSize = getToolkit().getScreenSize();
        setLocation((screenSize.width - paneSize.width) / 2,
        (screenSize.height - paneSize.height) / 2);
    }

    public void setLocation(int x, int y) {
        setLocation(new Point(x, y));
    }
    public void setLocation(Point p) {
        if (SwingWTUtils.isMacOSX() && (p.y == 0))
            p.y = SwingWTUtils.MACOSX_MENUBAR_BOTTOM;
        
        if (! p.equals(pLocation))
            setPeerLocation(p);
        
        pLocation = p;
    }
    
    /**
     * Sets the peer location, without changing the local cache.
     *
     * @param p
     */
    private void setPeerLocation(final Point p) {
        if (hasPeer())
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    peer.setLocation(p.x, p.y);
                }
            });
    }
    public Point getLocation() {
        return getBounds().getLocation();
    }

    public void setSize(final int width, final int height) {
        setSize(new Dimension(width, height));
    }
    
    public void setSize(Dimension d) {
        if (!d.equals(pSize)) {
            // If setSize() is called after pack() and the size differs, reset the delayed pack()
            delayPack = null;
            setSizeImpl(d);
        }
    }
    private void setSizeImpl(Dimension d) {
        pSize = d;
        
        if (hasPeer())
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    peer.setSize(pSize.width, pSize.height);
                }
            });
    }
    
    public Dimension getSize() {
        return getBounds().getSize();
    }

    public void setBounds(final Rectangle r) {
        setLocation(r.x, r.y);
        setSize(r.width, r.height);
    }
    public void setBounds(int x, int y, int width, int height) {
        setLocation(x, y);
        setSize(width, height);
    }
    
    
    public boolean isActive() {
    	
    	return hasPeer() && ((Boolean) SwingUtilities.sync(new SwingWTRunnable() {
			public Object exec() {
				return Boolean.valueOf(SwingWTUtils.getDisplay().getActiveShell() == peer);
			}
		})).booleanValue();
    }

    public boolean isFocused() {
        return isActive();
    }

    public void show() { invalidate(); setVisible(true); }
    public void hide() { setVisible(false); }
    public void addWindowListener(swingwt.awt.event.WindowListener l) { windowListeners.add(l); }
    public void removeWindowListener(swingwt.awt.event.WindowListener l) { windowListeners.remove(l); }
    
    public void setCursor(final swingwt.awt.Cursor c) {
        // We have to override this because peer in Component is
    	// not this Shell component
        pCursor = c;
        
        if (hasPeer())
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					try {
						if (c != null) {
							peer.setCursor(c.getSWTCursor());
						} else {
							peer.setCursor(null);
						}
					} catch (IllegalArgumentException e) {
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
    }

    public void setVisible(final boolean visible) {
    	
    	if (visible) {
			// note that create() will be executed in a sync call so it does not have
			// to be inside the subsequent invokeSync
			if (! hasPeer())
				create();
			
			SwingUtilities.invokeSync(new Runnable() {
				public void run() {
					if (!hasBeenOpened) {
						((Shell) peer).open();
                    	processWindowEvent(swingwt.awt.event.WindowEvent.WINDOW_OPENED);
                        repaintFix();
                    	hasBeenOpened = true;
                    }
                    else {
                    	peer.setVisible(true);
                    }				}
			});
		
		} else {
			if (hasBeenOpened)
				SwingUtilities.invokeSync(new Runnable() {
					public void run() {
						peer.setVisible(false);
					}
				});
		}
    }
    
    /** Closes the Window and releases all components */
    public void dispose() {
    	if (! isClosed && hasPeer()) 
	    	SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
	            	peer.dispose();
	            }
	    	});
    	
    	super.dispose();
    }
    
    public String getTitle() {
    	if (hasPeer())
    		return (String) SwingUtilities.sync(new SwingWTRunnable() {
                public Object exec() {
                    return ((Shell) peer).getText();
                }
            });
        
    	return title;
    }
    
    public void setTitle(final String title) {
        this.title = title;
        
        if (hasPeer())
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                	((Shell) peer).setText(title);
                }
            });
    }
    
    public Image getIconImage() {
    	if (hasPeer())
    		return (Image) SwingUtilities.sync(new SwingWTRunnable() {
                public Object exec() {
                    return new Image(((Shell) peer).getImage());
                }
            });
        
    	return iconImage;
    }
    
    public void setIconImage(final Image icon) {
        this.iconImage = icon;
        
        if (hasPeer())
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                	((Shell) peer).setImage(icon.image);
                }
            });
    }
    
    public void setJMenuBar(JMenuBar menu) {
        this.pMenu = menu;
        
        if (hasPeer())
            rootPane.setJMenuBar(menu);
    }
    
    public JMenuBar getJMenuBar() {
    	if (hasPeer())
    		return rootPane.getJMenuBar();
        
    	return pMenu;
    }
    
    public void setMenuBar(MenuBar menu) {
        this.pMenuBar = menu;
        
        if (hasPeer())
            rootPane.setMenuBar(menu);
    }
    
    public MenuBar getMenuBar() {
    	
    	if (hasPeer())
    		return rootPane.getMenuBar();
    	
    	return pMenuBar;
    }
    
    public void setDefaultButton(JButton button) {
        rootPane.setDefaultButton(button);
    }
    
    public JButton getDefaultButton() {
        return rootPane.getDefaultButton();
    }

    protected class WindowShellListener implements ShellListener {
        public void shellActivated(ShellEvent e) {
            frameWidth = peer.getSize().x - ((Shell) peer).getClientArea().width;
            frameHeight = peer.getSize().y - ((Shell) peer).getClientArea().height;
            if (delayPack != null) {
                pSize = new Dimension(delayPack.width + frameWidth, delayPack.height + frameHeight);
                peer.setSize(pSize.width, pSize.height);
                delayPack = null;
            }
            processWindowEvent(swingwt.awt.event.WindowEvent.WINDOW_ACTIVATED);
        }
        public void shellClosed(ShellEvent e) {
            isClosed = true;
            processWindowEvent(swingwt.awt.event.WindowEvent.WINDOW_CLOSING);
            processWindowEvent(swingwt.awt.event.WindowEvent.WINDOW_CLOSED);
            SwingWTUtils.decrementWindowReferences();
        }
        public void shellDeactivated(ShellEvent e) {
            processWindowEvent(swingwt.awt.event.WindowEvent.WINDOW_DEACTIVATED);
        }
        public void shellDeiconified(ShellEvent e) {
            isIcon = false;
            processWindowEvent(swingwt.awt.event.WindowEvent.WINDOW_DEICONIFIED);
        }
        public void shellIconified(ShellEvent e) {
            isIcon = true;
            processWindowEvent(swingwt.awt.event.WindowEvent.WINDOW_ICONIFIED);
        }
    }

    protected ShellListener createShellListener() {
        return new WindowShellListener();
    }

    protected void processWindowEvent(int id) {
        swingwt.awt.event.WindowEvent we = new swingwt.awt.event.WindowEvent(this, id);
        Iterator i = windowListeners.iterator();
        while (i.hasNext()) {
            swingwt.awt.event.WindowListener l = (swingwt.awt.event.WindowListener) i.next();
            switch(id) {
                case (swingwt.awt.event.WindowEvent.WINDOW_ACTIVATED): l.windowActivated(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_CLOSED): l.windowClosed(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_CLOSING): l.windowClosing(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_DEACTIVATED): l.windowDeactivated(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_DEICONIFIED): l.windowDeiconified(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_ICONIFIED): l.windowIconified(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_OPENED): l.windowOpened(we); break;
            }
        }
    }
    
    protected void processWindowEvent(swingwt.awt.event.WindowEvent e) {
        swingwt.awt.event.WindowEvent we = e;
        Iterator i = windowListeners.iterator();
        while (i.hasNext()) {
            swingwt.awt.event.WindowListener l = (swingwt.awt.event.WindowListener) i.next();
            switch(e.getID()) {
                case (swingwt.awt.event.WindowEvent.WINDOW_ACTIVATED): l.windowActivated(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_CLOSED): l.windowClosed(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_CLOSING): l.windowClosing(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_DEACTIVATED): l.windowDeactivated(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_DEICONIFIED): l.windowDeiconified(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_ICONIFIED): l.windowIconified(we); break;
                case (swingwt.awt.event.WindowEvent.WINDOW_OPENED): l.windowOpened(we); break;
            }
        }
    }
    
    protected void dispatchEvents() {
        while (null != peer && ! peer.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    // Send this thread to sleep to allow other
                    // processes a go :)
                    display.sleep();
                }
                // If thread retardation is on, sleep
                if (SwingWTUtils.isRetardDispatchThread()) {
                    try {
                        Thread.sleep(SwingWTUtils.getRetardationInterval());
                    }
                    catch (InterruptedException e) {}
                }
            }
            // By catching all exceptions and errors here, we
            // can prevent the app going down for small event
            // related problems. User code can still catch where
            // necessary.
            catch (Error e) {
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /** Sends the window to the front */
    public void toFront() {
    	if (hasPeer())
	        SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
	            	((Shell) peer).forceActive();
	            }
	        });
    }
    
    /** Sends the window to the back */
    public void toBack() {
    	if (hasPeer())
	        SwingUtilities.invokeSync(new Runnable() {
	            public void run() {
                    // Find another shell to make active
                    Shell[] s = SwingWTUtils.getDisplay().getShells();
                    for (int i = 0; i < s.length; i++) {
                        if (!s[i].equals(peer))
                            s[i].forceActive();
                    }
	            }
	        });
    }
    
    /**
     * Under GTK2 and MacOSX, SWT has some trouble issuing repaints,
     * the only way around it seems to be to tell SWT to relayout
     * the top-level container again after creation and child
     * components have been added.
     */
    protected void repaintFix() {
    	
        SwingUtilities.invokeIn(new Runnable() {
            public void run() {
            	if (hasPeer())
            		((Shell) peer).layout(true);
                
                SwingUtilities.invokeIn(new Runnable() {
                    public void run() {
                        if (hasPeer()) {
                            
                            // This is an interesting GTK bug - sometimes the size is
                            // reported as < 40, the peer doesn't paint or layout
                            // and everything goes pear-shaped. If we detect this, and
                            // reapply the Shell's size, all becomes well again
                            // in GTK land. This bug was a BITCH to find and fix.
                            if (peer.getSize().x < 40 && peer.getSize().y < 40) {
                                peer.setSize(pSize.width, pSize.height);
                            }
                            
                            // This code is no longer needed - the painting only fails
                            // when the size drops below the 40 threshold. This is one
                            // weird SWT/GTK bug.
                            
                            //else
                            //	peer.setSize(peer.getSize().x + 1, peer.getSize().y);
                        }
                    }
                }, 75);
            }
        }, 10);
    }
    
	public Control getSWTPeer() {
		return peer;
	}

	public void setSwingWTParent(Composite parent) throws Exception {
		throw new Exception("Window can't have parent.");
	}

	public final void setAlwaysOnTop(boolean alwaysOnTop)
			throws SecurityException {
		toFront();
	}

	public swingwt.awt.Component getFocusOwner() {
		return SwingWTUtils.getFocusedComponent();
	}

	public Window getOwner() {
		Window win = null;
		try {
			if (this.parent != null) {
				if (parent instanceof Window)
					win = (Window) parent;
			}
		} catch (Exception e) {
		}
		return win;
	}

	public Component findComponentAt(int x, int y) {
		return rootPane.findComponentAt(x, y);
	}
	
   /*
    public Window(Shell peer) {
    	this.peer = peer;
    }
    
    public Window[] getOwnedWindows() {
    	final java.util.List<Window> windows = new ArrayList<Window>();
    	SwingUtilities.invokeSync(new Runnable() {
            public void run() {
            	//org.eclipse.swt.widgets.Dialog[] dialogs = 
				Shell[] dialogs = peer.getShells();
				for (int i = 0; i < dialogs.length; i++)
					windows.add(new Window(dialogs[i]));
				
            }
    	});
    	return windows.toArray(new Window[windows.size()]);
    }
    */
}

