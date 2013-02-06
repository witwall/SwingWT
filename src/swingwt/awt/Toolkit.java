/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.*;




import org.eclipse.swt.graphics.ImageData;

import swingwt.awt.datatransfer.Clipboard;
import swingwt.awt.event.KeyEvent;
import swingwt.awt.image.ImageProducer;
import swingwtx.swing.SwingWTUtils;
import swingwt.awt.Toolkit;
import swingwt.awt.event.AWTEventListener;

public abstract class Toolkit {
    
	private static Toolkit toolkit = null;
    private static Object retval;
    private static Hashtable desktopProperties = new Hashtable();
    
    protected Toolkit() {}
    
    public static synchronized Toolkit getDefaultToolkit() 
    {
    	if (toolkit==null)toolkit = createToolkit();
        return toolkit;
    }
    
    public static synchronized Toolkit getToolkit() 
    {
        return getDefaultToolkit();
    }
    private static Toolkit createToolkit()
    {
    	return new SwingWTToolkit();
    }
    public Image getImage(String filename) {
        return new swingwtx.swing.ImageIcon(filename).getImage();
    }
    
    public Image getImage(URL url) {
        return new swingwtx.swing.ImageIcon(url).getImage();
    }
    
    public int getScreenResolution() {
        return swingwtx.swing.SwingWTUtils.getDisplay().getDepth();
    }
    
    public Insets getScreenInsets(GraphicsConfiguration gc) throws HeadlessException {
        if (this != Toolkit.getDefaultToolkit())
            return Toolkit.getDefaultToolkit().getScreenInsets(gc);
        else
            return new Insets(0,0,0,0);
    }
    
    public FontMetrics getFontMetrics(Font f) {
        return new FontMetrics(f);
    }

    private int isMac = 0;
    private boolean isMac() {
        
        if (isMac == 0) {
	        String vers = System.getProperty("os.name").toLowerCase();
	        if (vers.indexOf("mac") != -1)
	            isMac = 1;
	        else
	            isMac = -1;
        }
        
        return (isMac > 0);
    }
    
    public int getMenuShortcutKeyMask() throws HeadlessException {
        if (isMac())
            return KeyEvent.META_MASK;
        else
            return KeyEvent.CTRL_MASK;
    }
    
    public Dimension getScreenSize() {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                retval = new Dimension(
                    swingwtx.swing.SwingWTUtils.getDisplay().getBounds().width, 
                    swingwtx.swing.SwingWTUtils.getDisplay().getBounds().height);
            }
        });
        return (Dimension) retval;
    }
    
    public Image createImage(ImageProducer producer) {
        SWTImageConsumer tki = new SWTImageConsumer();
        producer.startProduction(tki);
        ImageData imageData = tki.getImageData();
        Image image = new Image();
        image.image = new org.eclipse.swt.graphics.Image(SwingWTUtils.getDisplay(), imageData);
        return image;        
    }

    public Image createImage(byte[] imagedata) {
	return new swingwtx.swing.ImageIcon(imagedata).getImage();
    }

    public Image createImage(byte[] imagedata, int imageoffset, int imagelength) {
        byte[] im2 = new byte[imagelength];
	for (int i = imageoffset; i < imagelength; i++)
	    im2[i - imageoffset] = imagedata[i];
        return new swingwtx.swing.ImageIcon(im2).getImage();	    
    }
    
    public Image createImage(String filename) {
        return getImage(filename);
    }
    
    public Image createImage(URL url) {
        return getImage(url);    
    }
    
    public Clipboard getSystemClipboard() {
        return new swingwt.awt.datatransfer.Clipboard();
    }
    
    public void beep() {
    }
    
    public final Object getDesktopProperty(String propertyName) {
        return desktopProperties.get(propertyName);
    }
    
    protected final void setDesktopProperty(String propertyName, Object value) {
        desktopProperties.put(propertyName, value);    
    }
    
    public static String getProperty(String key,
                                     String defaultValue) {
        return defaultValue;
    }

    public EventQueue getSystemEventQueue() {
        return EventQueue.singleton;
    }

    public void addPropertyChangeListener(String property, PropertyChangeListener listener) {
        // TODO
    }
    
    public Dimension getBestCursorSize(int preferredWidth,
            int preferredHeight)
     throws HeadlessException {
    	return new Dimension(0,0);
    }
    
    public boolean getLockingKeyState(int keyCode)
    throws UnsupportedOperationException {
    	throw new UnsupportedOperationException();
    }
    //TODO: implement this when print is available
    public PrintJob getPrintJob(Frame frame, String jobtitle, Properties props)
    {
    	return null;
    }
    // TODO: add this listener
    public void addAWTEventListener(AWTEventListener listener, long eventMask) 
    {
    	
    }
    // TODO: remove this listener
    public void removeAWTEventListener(AWTEventListener listener) 
    {
    
    }
    public abstract void sync();
}
	class SwingWTToolkit extends Toolkit
{
	// probably not nessesary to run this in SWT	
	public void sync()
	{
		
	}
}
