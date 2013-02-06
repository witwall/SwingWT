/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Vector;
import java.util.WeakHashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.widgets.Display;

import swingwt.awt.Component;
import swingwt.awt.Image;
import swingwt.awt.Shape;
import swingwt.awt.geom.PathIterator;

/**
 * Utilities required for SwingWT. Handles management
 * of SWT display, SwingWT event pump and some handy stuff
 * for calculating renderered text size, determining
 * platform, etc.
 *
 * @author Robin Rawson-Tetley
 */
public abstract class SwingWTUtils {

	/**
	 * Whether or not to output information about SwingWT to stdout when
	 * the library is loaded.
	 */
    private static boolean isDebug = false;//true;
    
    /** SwingWT version */
    private final static String VERSION = "0.91 (20120206)";
    
    /**
     * Whether or not SWTExceptions thrown on the event loop are
     * output to stdout.
     */
    public static boolean showInternalSWTExceptions = false;
    
    /**
     * Set this flag to true if you are compiling SwingWT natively for use
     * with GCJ. It forces the getPixmap() call to look for files on the
     * file system instead of as compiled in resources.
     */
    public static boolean useFileSystemPixmaps = false;
    /**
     * If we are using file system pixmaps, this is the path to the
     * pixmaps directory. This should be correct for most *nix flavours
     * but you might need to change it if your distribution keeps
     * pixmaps in a funky place. It SHOULD have a trailing slash.
     */
    public static String PIXMAP_DIRECTORY = "/usr/share/pixmaps/";
    /**
     * If the app is natively compiled, this is the subdirectory of
     * PIXMAP_DIRECTORY that SwingWT's internal components (tree, filechooser, etc.)
     * will look in to find images. It should NOT have a trailing or leading slash
     */
    public static String SWINGWT_PIXMAP_SUBDIR = "swingwt"; 
    
    /**
     * The copyright message output on startup
     */
    private final static String COPYRIGHT = "This is SwingWT (http://swingwt.sourceforge.net)\n" +
                                            "Version: " + VERSION + "[SWT " + SWT.getPlatform() + " " + SWT.getVersion() + "]\n" +
                                            "Copyright(c)2003-2008, R.Rawson-Tetley and other contributors.\n\n" +
                                            "This library is distributed in the hope that it will be useful,\n" +
                                            "but WITHOUT ANY WARRANTY; without even the implied warranty of\n" + 
                                            "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU\n" +
                                            "Lesser General Public Licence for more details.";
    
    /** The SWT display */
    private static Display display = null;
    /** Whether we have a dispatch thread going */
    private static boolean eventsDispatching = false;
    /** Who owns the event dispatcher */
    private static Object eventsDispatchOwner = null;
    /** Have we set a shutdown hook to remove temp files? */
    private static boolean setDeleteTempOnShutdown = false;
    /** List of temporary files created in this session */
    private static Vector tempFileList = new Vector();
    /** Dispatch thread retarding for performance */
    private static boolean retardEventDispatch = false;
    private static int retardationms = 2000;
    /** A reference to the currently focused component */
    private static swingwt.awt.Component focusedComponent = null;
    /** Window reference count. If it reaches 0 then we end the dispatch thread */
    private static int windowReferenceCount = 0;
    /** 
     * Whether or not to use SWT fast virtuals. 
     * NOTE: As at M8 there is an issue with Win32 environments 
     * and virtual tables so it defaults to off for Windows.
     */
    private static boolean useSWTFastVirtualTables = !isWindows();
    /** A temp directory SwingWT can use (not used at present) */
    private static String tempDirectory = System.getProperty("user.home") + File.separator + "tmp" + File.separator + "swingwt";
    /** 
     * The height of the MacOS X menubar in pixels - this is used by awt.Window as calling setLocation(0,0)
     * on MacOSX can end up hiding the window decoration. This value determines where SwingWT thinks
     * the MacOSX screen actually starts. 
     * */
    public static int MACOSX_MENUBAR_BOTTOM = 20;
    
    public static String getVersion() { return VERSION; }
    
    public static synchronized void incrementWindowReferences() {
        windowReferenceCount++;    
    }
    
    public static synchronized void decrementWindowReferences() {
        windowReferenceCount--;
        // TODO: (Dan) This is not the best solution, but killing the dispatch thread
        // causes problems if another window is created after this.  Commenting out for now.
        //if (windowReferenceCount == 0) stopEventDispatchRunning();
	// (Rob) This is actually what real Swing does and once you've created
	//       a window and dispatch, you are forced to use System.exit() to
	//       terminate the VM - so don't worry about it :-)
    }
    
    public static void setShowSwingWTInfoOnStartup(boolean b) {
        isDebug = b;    
    }
    
    /** Determines whether the event dispatch thread is retarded for extra performance */
    public static synchronized void setRetardDispatchThread(boolean b) {
        retardEventDispatch = b;    
    }
    /** Determines whether the event dispatch thread is retarded for extra performance */
    public static synchronized void setRetardDispatchThread(boolean b, int ms) {
        retardEventDispatch = b;    
        retardationms = ms;
    }
    
    /** 
     * If your code is an Eclipse plugin (ie. Something outside
     * of SwingWT and your program has started using SWT components
     * in the current VM and an event dispatcher is already going), 
     * Call this routine with (true) to have SwingWT use the other
     * program's event dispatcher (rather than it's own thread).
     */
    public synchronized static void setEclipsePlugin(boolean b) {
        if (b) {
            eventsDispatching = true;
	    display = Display.getDefault();
	}
	else {
            eventsDispatching = false;
	}
    }
    
    /** Returns whether SwingWT is using fast SWT virtual tables */
    public static boolean isUseSWTFastVirtualTables() { return useSWTFastVirtualTables; }
    /** Set this to true to use fast SWT Virtual tables (off by default) */
    public static void setUsesSWTFastVirtualTables(boolean b) { useSWTFastVirtualTables = b; }
    
    /**
     * Startup routine for MacOSX programs. This isn't very pretty, but the threading
     * model for java under MacOSX is fundamentally broken and requires the event
     * pump to run on the main thread.
     *
     * @param run The startup code to run (this call will block)
     */
    public static void initialiseMacOSX(Runnable run) {
	
	// Shove it onto the event pump
        getDisplay().asyncExec(run);

        // Start the event pump running
	checkEventDispatcher();
	
    }
    
    /** 
     *  Checks whether the event dispatch thread is running, and starts it
     *  if it isn't.
     */
    public static void checkEventDispatcher() {
    	synchronized (SwingWTUtils.class) {
    		if(eventsDispatching) return;
    		
    		eventsDispatching = true;
    		
            // Make sure there is a VM hook to dump the temp directory when we're finished
            // and do any necessary cleaning up
            checkShutdownHook();            
    	}
    	
    	// Event dispatch thread itself
    	Thread pump = new Thread() {
    		public void run() {

    			// Make sure display is set
    			display = Display.getDefault();

    			// Debug info
    			if (isDebug)
    				System.out.println(COPYRIGHT);

    			while (eventsDispatching) {
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
    				catch (org.eclipse.swt.SWTException e) {

    					// If we have a "Device is disposed" error, and
    					// we're on MacOS X, then somebody has hit the
    					// Quit button from the app menu and we need to
    					// stop the dispatcher running so the program
    					// can exit gracefully.
    					if (e.getMessage().startsWith("Device is disposed") &&
    							isMacOSX()) {
    						stopEventDispatchRunning();
    						return;
    					}

    					// If we are showing exceptions, dump the stack
    					// trace out to the console.
    					if (showInternalSWTExceptions)
    						e.printStackTrace();

    				}
    				catch (Error e) {
    					e.printStackTrace();
    				}
    				catch (Exception e) {
    					e.printStackTrace();    
    				}
    			}
    		}
    	};

    	pump.setName("SwingWT-Dispatch");
        	

	// SWTSwing should have our dispatch thread being the
	// Swing dispatch thread
	if (isSwing()) {
		try {
			Class c = Class.forName("org.eclipse.swt.widgets.Display");
			Method m = c.getMethod("swtExec", new Class[] { Runnable.class });
			m.invoke(null, new Object[] { pump });
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
			// Fall through to spawning new thread instead
		}
	}
        
	// MacOSX/Carbon needs to be on the main thread, so this 
	// call should block. Other platforms can spawn a new thread 
	// to handle the pump like Swing.
	if (isMacOSX()) {
		pump.run();
		return;
	}

	// Normal platforms - spawn a new thread for dispatch
	pump.start();
    }
    
    /** @return true if the dispatch thread is currently retarded */
    public static boolean isRetardDispatchThread() { return retardEventDispatch; }
    /** @return The dispatch thread retardation in milliseconds */
    public static int getRetardationInterval() { return retardationms; }
    
    public static synchronized Display getDisplay() {
        
        // For MacOSX, we need to grab the display on this
	// thread and return it
	if (isMacOSX()) {
	    display = Display.getDefault();
	    return display;
	}
	    
        // Make sure event dispatch is running (since this
        // creates the display).
        checkEventDispatcher();
        
        // Wait for the display to be set
        while (display == null) {
            try {
                Thread.sleep(50);
            }
            catch (InterruptedException e) {}
        }
        
        return display;
    }
    
    /** Checks to see if this users temp directory is there
     *  and creates it if it isn't. 
     *  @throws IOException if the directory couldn't be created.
     */
    private static void checkForTempDirectory() throws IOException {
        File f = new File(tempDirectory);
        if (!f.exists())
            f.mkdirs();
    }

    /** 
     * @return a reference to the currently focused SwingWT
     *  component, or null if there isn't one
     */
    public static swingwt.awt.Component getFocusedComponent() {
        return focusedComponent;
    }

    /** 
     * Used by focus events to set the reference to the currently
     * focused component.
     * @see swingwt.awt.Component.SWTEventAdapter
     */
    public static synchronized void setFocusedComponent(swingwt.awt.Component c) {
	focusedComponent = c;
    }
    
    /** Determines if an SWT peer is available for use
     */
    public static boolean isSWTControlAvailable(org.eclipse.swt.widgets.Control c) {
        if (c == null) return false;
        if (c.isDisposed()) return false;
        return true;
    }
    
    /** Determines if an SWT menu peer is available for use
     */
    public static boolean isSWTMenuControlAvailable(org.eclipse.swt.widgets.MenuItem c) {
        if (c == null) return false;
        if (c.isDisposed()) return false;
        return true;
    }
    
    public synchronized static boolean isEventDispatchRunning() {
        return eventsDispatching;    
    }
    
    /**
     * Stops the event dispatch thread running (if it isn't running.
     * does nothing).
     */
    public static synchronized void stopEventDispatchRunning() {
        eventsDispatching = false;
        if (null != display)
        	display.wake();
    }

    private static class InvalidateQueue implements Runnable {
        private LinkedList invalidateQueue;
        private HashSet invalidateSet;
        private boolean invalidateInProgress;

        public InvalidateQueue() {
            invalidateQueue = new LinkedList();
            invalidateSet = new HashSet();
        }

        private void addToQueueRecursively(Component c) {
            Component parent = c.getParent();
            if (parent != null && !(parent instanceof swingwtx.swing.JViewport)) {
                addToQueueRecursively(parent);
            }
            if (invalidateSet.add(c)) {
                invalidateQueue.add(c);
            }
        }

        private Component dequeue() {
            Component c = null;
            if (!invalidateQueue.isEmpty()) {
                c = (Component) invalidateQueue.removeFirst();
                invalidateSet.remove(c);
            }
            return c;
        }

        public void queue(Component c) {
            synchronized (this) {
                addToQueueRecursively(c);
                if (invalidateInProgress)
                    return;
                invalidateInProgress = true;
            }
            SwingUtilities.invokeSync(this);
        }

        public void run() {
            while (true) {
                Component c;
                synchronized (this) {
                    c = dequeue();
                    if (c == null) {
                        invalidateInProgress = false;
                        return;
                    }
                }
                c.invalidateImpl();
            }
        }
    }

    private static InvalidateQueue invalidateQueue = new InvalidateQueue();

    public static void invalidate(Component c) {
        invalidateQueue.queue(c);
    }

    /**
     * Used for retrieving images in a friendly way to both GCJ and
     * VMs. If the useFileSystemPixmaps flag is set (for a native build),
     * this will retrieve the image from the file system, looking in
     * the pixmapSubdir of PIXMAP_DIRECTORY. If the flag is not set,
     * then the resourcePath is used to look on the classpath for the
     * image.
     *
     * @param filename The name of the image file
     * @param pixmapSubdir The subdirectory of /usr/share/pixmaps/ to look for the image in
     * @param resourcePath The URL to the classpath resource (eg: /com/mydomain/)
     * @return An ImageIcon containing the pixmap
     */
    public static ImageIcon getPixmap(String filename, String pixmapSubdir, String resourcePath) {
    	if (useFileSystemPixmaps) {
    		if (!pixmapSubdir.endsWith("/")) pixmapSubdir += "/";
    		return new ImageIcon(PIXMAP_DIRECTORY + pixmapSubdir + filename);
    	}
    	else {
    		if (!resourcePath.endsWith("/")) resourcePath += "/";
    		return new ImageIcon(filename.getClass().getResource(resourcePath + filename));
    	}
	}
    
    /**
     * Similar to the other getPixmap, this overloaded version uses a
     * class for context and retrieves the resource through a relative
     * link if we aren't using filesystem pixmaps.
     * 
     * @param context The class to use to load the resource from
     * @param filename The resource's filename
     * @return An ImageIcon containing the pixmap
     */
    public static ImageIcon getPixmap(Class context, String filename) {
    	if (useFileSystemPixmaps) {
    		return new ImageIcon(PIXMAP_DIRECTORY + SWINGWT_PIXMAP_SUBDIR + "/" + filename);
    	}
    	else
    		return new ImageIcon(context.getResource(filename));
    }
    
    /**
     * @param url The path to inspect.
     * @return TRUE if the passed URL is contained in a JAR file.
     * @throws IOException
     */
    public static boolean isUrlInJar(URL url) throws IOException {
        return url == null ? false : url.toString().startsWith("jar:");
    }
    
    /**
     * Given a byte array of content, writes it to a temporary file and then
     * returns the path to it as a URL
     * @param contents The content of the file
     * @param type The file extension to use
     * @throws IOException if an error occurs
     */
    public static URL stringToTempFile(byte[] contents, String type) throws IOException {
        
        // Make sure we have a temp directory
        checkForTempDirectory();
        
        // Generate a random file name and keep doing it until we get a unique one
        File f = null;
        String fName = null;
        do {
            fName = tempDirectory + File.separator +
                ((int) (Math.random() * 10000000)) + "." + type;
            f = new File(fName);
        } while (f.exists());
        
        System.out.println("TEMP: Creating temp file " + fName);
        FileOutputStream out = new FileOutputStream(f);
        out.write(contents);
        out.close();
        
        // Remember this file for later deletion
        tempFileList.add(fName);
        
        return new URL("file://" + fName);
    }
    
    public static void clearTempDirectory() {
        // Abandon if we created no files in this session
        if (tempFileList.size() == 0) return;
        Iterator i = tempFileList.iterator();
        while (i.hasNext()) {
            try {
                File f = new File((String) i.next());
                f.delete();
            }
            catch (Exception e) { e.printStackTrace(); }
        }    
    }
    
    public static void checkShutdownHook() {
        if (!setDeleteTempOnShutdown) {
            Runtime.getRuntime().addShutdownHook( new Thread() {
                public void run() {
                    
                    clearTempDirectory();  
                    
                    // Unfortunately, we use some finalizer based
                    // cleanup for manually created Color/Font/Graphics (only way really)
                    // so we want to make sure they run before closing so we don't 
                    // lose OS resources. Might as well do a garbage collection too :-)
                    
                    // This is a real "damned if you do, damned if you don't" situation -
                    // the problem is that we have to use finalizers to kill the native
                    // resources because we know they should run at some point when the
                    // object is finished with. In practice, the best you can hope for
                    // is "on shutdown, but maybe before". The other option would be to
                    // manage the damn things myself, but by keeping a list, I ensure
                    // the objects won't be collected until I destroy them, and I have
                    // no way of knowing whether they are being used in on-screen 
                    // components - I can guarantee it at shutdown however, but ideally
                    // I want them to run before that! So I'm stuck with fucking
                    // finalizers. Best thing to do is call System.runFinalization()
                    // yourself in your app when you've finished with awt.Color/Graphics/Font
                    // (or, each of those AWT mapped objects has a dispose() method to
                    // kill off the native resource).
                    
                    // Another option could have been the use of WeakReferences, however
                    // this would be very difficult to code, and not all VMs support them
                    // (we care more about free VMs than Sun's)
                    System.runFinalization();
                    System.gc();
                    
                }
            });
            setDeleteTempOnShutdown = true;
        }
    }
    
    /** Returns true if this is a windows SWT kit */
    public static boolean isWindows() {
        if (platform == -1) checkOS();
        return platform == WIN32;
    }
    /** Returns true if this is a MacOS X SWT kit */
    public static boolean isMacOSX() {
        if (platform == -1) checkOS();
        return platform == MACOSX;
    }
    /** Returns true if this is a GTK SWT kit */
    public static boolean isGTK() {
        if (platform == -1) checkOS();
        return platform == GTK;
    }
    /** Returns true if this is a Swing SWT kit */
    public static boolean isSwing() {
        if (platform == -1) checkOS();
        return platform == SWING;
    }
    /** Returns true if the underlying OS is Mac OS irrespective of SWT */
    public static boolean osIsMacOSX() {
        return System.getProperty("os.name").indexOf("Mac OS") != -1;
    }

    private static final int WIN32 = 0;
    private static final int MACOSX = 1;
    private static final int GTK = 2;
    private static final int SWING = 3;
    private static int platform = -1;

    private static void checkOS() {
        String p = SWT.getPlatform();
        if (p.equals("swing")) platform = SWING;
        if (p.equals("gtk")) platform = GTK;
        if (p.equals("carbon") || p.equals("cocoa")) platform = MACOSX;
        if (p.equals("win32")) platform = WIN32;
    }

    
    /**
     * Saves an image to a JPEG. Not sure where to put this
     * since the only support Java has for this, is in a hidden
     * com.sun.jpeg.JPEGEncoder class which is not part of the
     * formal interface.
     * Anyway, I'm sticking it here.
     */
    public static void saveImageToJPG(swingwt.awt.Image image, OutputStream stream) {
        org.eclipse.swt.graphics.ImageLoader il = new org.eclipse.swt.graphics.ImageLoader();
        il.data = new org.eclipse.swt.graphics.ImageData[] { image.image.getImageData() };
        il.save(stream, org.eclipse.swt.SWT.IMAGE_JPEG);
        il = null;
    }
    
    /**
     * Saves an image to a GIF. Not sure where to put this
     * since the only support Java has for this, is in a hidden
     * com.sun.gif.GIFEncoder class which is not part of the
     * formal interface.
     * Anyway, I'm sticking it here.
     */
    public static void saveImageToGIF(swingwt.awt.Image image, OutputStream stream) {
        org.eclipse.swt.graphics.ImageLoader il = new org.eclipse.swt.graphics.ImageLoader();
        il.data = new org.eclipse.swt.graphics.ImageData[] { image.image.getImageData() };
        il.save(stream, org.eclipse.swt.SWT.IMAGE_GIF);
        il = null;
    }
    
        /**
     * Saves an image to a JPEG. Not sure where to put this
     * since the only support Java has for this, is in a hidden
     * com.sun.png.PNGEncoder class which is not part of the
     * formal interface.
     * Anyway, I'm sticking it here.
     */
    public static void saveImageToPNG(swingwt.awt.Image image, OutputStream stream) {
        org.eclipse.swt.graphics.ImageLoader il = new org.eclipse.swt.graphics.ImageLoader();
        il.data = new org.eclipse.swt.graphics.ImageData[] { image.image.getImageData() };
        il.save(stream, org.eclipse.swt.SWT.IMAGE_PNG);
        il = null;
    }
    
    /** Used as a return val from renderStringWidth routines */
    private static int intretval = 0;
    /** 
     * Given a piece of text, this routine will evaluate how many pixels wide it will
     * be when renderered in the default system font. This is used by JTable and
     * JList to determine the column widths
     */
    public static int getRenderStringWidth(final String text) {
        SwingUtilities.invokeSync( new Runnable() {
            public void run() {
                org.eclipse.swt.graphics.GC gc = null;
                org.eclipse.swt.graphics.Image im = null;
                if (getDisplay().getActiveShell() == null) {
                    im = new org.eclipse.swt.graphics.Image(getDisplay(), getDisplay().getBounds()); 
                    gc = new org.eclipse.swt.graphics.GC(im);
                }
                else
                    gc = new org.eclipse.swt.graphics.GC(getDisplay().getActiveShell());

                // We need to calculate differently for Windows platforms. Isn't it always the
                // way? This is because Windows shows the 3 dots - even if the text just fits! So we
                // have to make it even larger. What a piece of shit Windows is
                org.eclipse.swt.graphics.Point p = null;
                if (isWindows())
                    p = gc.stringExtent(text + "WWW");
                else
                    p = gc.stringExtent(text + "W");
                int width = p.x;
                gc.dispose();
                gc = null;
                if (im != null) im.dispose();
                im = null;
                intretval = width;
            }
        });
        return intretval;
    }
    
   /** 
     * Given a piece of text, this routine will evaluate how many pixels high it will
     * be when renderered in the default system font. This is used by JPanel to
     * determine the extra height needed on the insets for laying out purposes
     */
    public static int getRenderStringHeight(final String text) {
        SwingUtilities.invokeSync( new Runnable() {
            public void run() {
                org.eclipse.swt.graphics.GC gc = null;
                org.eclipse.swt.graphics.Image im = null;
                if (getDisplay().getActiveShell() == null) {
                    im = new org.eclipse.swt.graphics.Image(getDisplay(), getDisplay().getBounds()); 
                    gc = new org.eclipse.swt.graphics.GC(im);
                }
                else
                    gc = new org.eclipse.swt.graphics.GC(getDisplay().getActiveShell());

                org.eclipse.swt.graphics.Point p = gc.stringExtent(text + "W");
		
                int height = p.y;
		
		// MacOSX gets it wrong
		if (isMacOSX()) {
	            double dh = (double) height;
		    double nh = dh * 1.5;
		    height = (int) nh;
		}

                gc.dispose();
                gc = null;
                if (im != null) im.dispose();
                im = null;
                intretval = height;
            }
        });
        return intretval;
    }
    
    /**
     * Because GCJ/GIJ Classpath doesn't support StringBuffer.indexOf, we have
     * to have a replacement that uses Strings instead.
     * @param buffer The StringBuffer to find in
     * @param string The String to find
     * @return The index of the string or -1 if it wasn't found
     */
    public static int getStringBufferIndexOf(StringBuffer buffer, String string) {
        return buffer.toString().indexOf(string);
    }
    /**
     * Because GCJ/GIJ Classpath doesn't support StringBuffer.indexOf, we have
     * to have a replacement that uses Strings instead.
     * @param buffer The StringBuffer to find in
     * @param string The String to find
     * @param fromIndex The char index to search from
     * @return The index of the string or -1 if it wasn't found
     */
    public static int getStringBufferIndexOf(StringBuffer buffer, String string, int fromIndex) {
        return buffer.toString().indexOf(string, fromIndex);    
    }
    
    /**
     * Renders a Swing Icon onto an SWT image. Used by SwingWT to
     * render all images onto components.
     * 
     * @author Robin Rawson-Tetley
     *
     * @param component the SwingWT component this icon is being rendered for
     * @param icon The icon to render
     * @return An SWT image to assign to the component
     */
    public static org.eclipse.swt.graphics.Image getSWTImageFromSwingIcon(swingwt.awt.Component c, swingwtx.swing.Icon icon) {
        
        if (icon == null) return null;
        
        // Is it an ImageIcon? If so, we can cheat since it already
        // has an SWT image
        if (icon instanceof ImageIcon) {
            Image image = ((ImageIcon) icon).getImage();
            return image != null ? image.image : null;
        }

        // Otherwise, we need to render the icon onto an image -
        
        // create the image and drawing context
        org.eclipse.swt.graphics.Image img = new org.eclipse.swt.graphics.Image(getDisplay(), icon.getIconWidth(), icon.getIconHeight());
        org.eclipse.swt.graphics.GC gc = new org.eclipse.swt.graphics.GC(img);
        swingwt.awt.SWTGraphics2DRenderer g = new swingwt.awt.SWTGraphics2DRenderer(gc, false);
        
        // Tell the icon to paint itself
        icon.paintIcon(c, g, 0, 0);
        
        // Destroy the graphics context
        g.dispose();
        
        // return the renderered image
        return img;
        
    }
    
    /** Translates the alignment part of the Swing constants */
    public static int translateSwingAlignmentConstant(int constant) {
        int ret = 0;
        switch (constant) {
            case (SwingConstants.CENTER): ret = SWT.CENTER; break;
            case (SwingConstants.TOP): ret = SWT.TOP; break;
            case (SwingConstants.LEFT): ret = SWT.LEFT; break;
            case (SwingConstants.BOTTOM): ret = SWT.BOTTOM; break;
            case (SwingConstants.RIGHT): ret = SWT.RIGHT; break;
            case (SwingConstants.LEADING): ret = SWT.LEFT; break;
            case (SwingConstants.TRAILING): ret = SWT.RIGHT; break;
        }
        return ret;
    }
    
    /** Translates the orientation part of the Swing constants */
    public static int translateSwingOrientationConstant(int constant) {
        int ret = 0;
        switch (constant) {
            case (SwingConstants.HORIZONTAL): ret = SWT.HORIZONTAL; break;
            case (SwingConstants.VERTICAL): ret = SWT.VERTICAL; break;
        }
        return ret;
    }
    
    /*** Removes HTML tags from a string
     * @param s The string to remove HTML tags from
     */
    public static String removeHTML(final String text, boolean replaceNewlines) {

    	if (null == text)
    		return null;
    	
    	String s = text;
    	
        // If we have an opener and no closer, then
        // it ain't HTML and we shouldn't break it
        int opener = s.indexOf("<");
        int closer = s.indexOf(">");
        if (opener != -1 && closer == -1)
            return s;
	    
        int i = opener;
        while (i != -1) {
            // Find end pos
            int e = s.indexOf(">", i);
            if (e == -1) e = s.length();
            String tag = s.substring(i + 1, e);
            // Strip from the string
            s = s.substring(0, i) + (replaceNewlines && tag.equals("br") ? "\n" : "") +
                ( e < s.length() ? s.substring(e + 1, s.length())
                                 : "" );
                
            // Find again
            i = s.indexOf("<");
        }
        
        // Replace known sequences with characters
        s = replace(s, "&nbsp;", " ");
        s = replace(s, "&nbsp", " ");
        s = replace(s, "&amp;", " ");
        s = replace(s, "&lt;", "<");
        s = replace(s, "&gt;", ">");
        
        // Only compress whitespace if HTML was found
        if (text.equals(s)) 
        	return s;
        
        // Compress whitespace
        s = s.trim();
        StringBuffer o = new StringBuffer();
        boolean lastWasSpace = false;
        for (i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                if (!lastWasSpace) {
                    lastWasSpace = true;
                    o.append(" ");
                }
            }
            else {
                o.append(s.substring(i, i + 1));
                lastWasSpace = false;
            }
        }
        s = o.toString();

        return s;
    }
    public static String removeHTML(String s) {
        return removeHTML(s, false);
    }

    
    /** Looks in findin for all occurrences of find and replaces them with replacewith 
     * @param findin The string to find occurrences in
     * @param find The string to find
     * @param replacewith The string to replace found occurrences with
     * @return A string with all occurrences of find replaced.
     */
    public static String replace(String findin, String find, String replacewith) {
        
        StringBuffer sb = new StringBuffer(findin);
        int i = 0;
        try {
            while (i <= sb.length() - find.length()) {
                if (sb.substring(i, i + find.length()).equalsIgnoreCase(find)) {
                    sb.replace(i, i + find.length(), replacewith);
                }
                i++;
            }
        }
        catch (StringIndexOutOfBoundsException e) {
            // We hit the end of the string - do nothing and carry on
        }
            
        return sb.toString();
    }
    
    public static Path createPath(Shape s) {
    	PathIterator path = s.getPathIterator(null);
    	float [] segment = new float [6];
    	 	
    	Path swtPath = new Path(swingwtx.swing.SwingWTUtils.getDisplay());
    	
    	while(!path.isDone()) {
    		int type = path.currentSegment(segment);
    		switch(type) {
    		case PathIterator.SEG_MOVETO:
    			swtPath.moveTo(segment[0], segment[1]);
    			break;
    		case PathIterator.SEG_LINETO:
    			swtPath.lineTo(segment[0],segment[1]);
    			break;
    		case PathIterator.SEG_QUADTO:
    			swtPath.quadTo(segment[0], segment[1], segment[2], segment[3]);
    			break;
    		case PathIterator.SEG_CUBICTO:
    			swtPath.cubicTo(segment[0], segment[1], segment[2], segment[3],
    					segment[4],segment[5]);
    			break;
    		case PathIterator.SEG_CLOSE:
    			swtPath.close();
    			break;
    		}    		
    	}

    	return swtPath;
    }
    // Conveniant method to load images  instead of using BeanInfo
    public static Image loadImage(String imageName)
    {
    	Image ico = null;
    	Object obj = new Object();
    	ClassLoader cl = obj.getClass().getClassLoader();
    	ico = new ImageIcon(cl.getResource(imageName)).getImage();
    	return ico;
    }
    
    /**
     * A very simplistic wrapping identity map
     */
    protected static Map map = new WeakHashMap();
    
    /**
     * Register that this SwingWT wrapper wraps this peer.
     *
     * @param wrapper
     * @param peer
     */
    public static void registerWrapper(Object wrapper, Object peer) {
    	map.put(peer, wrapper);
    }
    
    /**
     * Gets the SwingWT wrapper for this peer.
     *
     * @param peer
     * @return null if there is no wrapper registered.
     */
    public static Object wrap(Object peer) {
    	if (map.containsKey(peer))
    		return map.get(peer);
    	return null;
    }
}


