/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwt.awt;

import org.eclipse.swt.graphics.FontData;
import swingwtx.swing.SwingWTUtils;
import swingwtx.swing.SwingUtilities;

/** 
 * @author Robin Rawson-Tetley 
 */
public class GraphicsEnvironment {
    
    private static GraphicsEnvironment instance = new GraphicsEnvironment();
    private Object retval = null;
    
    protected GraphicsEnvironment() { }
    
    /**
     * @return The singleton local graphics environment instance
     */
    public static GraphicsEnvironment getLocalGraphicsEnvironment() {
        return instance;
    }
    
    /**
     * Native platforms are never headless.
     * @return false
     */
    public static boolean isHeadless() {
        return false;
    }
    
    /**
     * Never headless
     * @return false
     */
    public boolean isHeadlessInstance() {
        return false;
    }
    
    /**
     * @return a Graphics2D object for drawing into the
     * specified image
     */
    public Graphics2D createGraphics(swingwt.awt.image.BufferedImage img) {
        return img.createGraphics();
    }
    
    /**
     * @return A list of all available fonts
     */
    public Font[] getAllFonts() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                FontData[] f = SwingWTUtils.getDisplay().getFontList(null, true);
                Font[] fn = new Font[f.length];
                for (int i = 0; i < f.length; i++) {
                    fn[i] = new Font(f[i]);
                }
                retval = fn;
            }
        });
        return (Font[]) retval;
    }
    
    /**
     * @return The available font family names (eg: Helvetica, Arial)
     */
    public String[] getAvailableFontFamilyNames() {
        Font[] fonts = getAllFonts();
        java.util.Vector s = new java.util.Vector();
        String lastfont = "";
        for (int i = 0; i < fonts.length; i++) {
            // Prevent duplicates
            if (!lastfont.trim().equals(fonts[i].getFontName().trim())) {
                s.add(fonts[i].getFontName());
                lastfont = fonts[i].getFontName();
            }
        }
        String[] r = new String[s.size()];
        for (int i = 0; i < s.size(); i++) {
            r[i] = s.get(i).toString();
        }
        return r;
    }
    
    /**
     * @return The results of getAvailableFontFamilyNames()
     */
    public String[] getAvailableFontFamilyNames(java.util.Locale l) {
       return getAvailableFontFamilyNames(); 
    }
    
    /** 
     * @return The point at which windows should be centred.
     * @throws HeadlessException if isHeadless return strue.
     */
    public Point getCenterPoint() throws HeadlessException {
        if (isHeadless()) throw new HeadlessException();
        return new Point( Toolkit.getDefaultToolkit().getScreenSize().width /2,
                          Toolkit.getDefaultToolkit().getScreenSize().height /2);
    }
    
    /**
     * @return The default GraphicsDevice. Currently stubbed with no implementation.
     */
    public GraphicsDevice getDefaultScreenDevice() {
        return new GraphicsDevice();
    }
    
    /**
     * Returns the maximum size of a centred Window, taking into account platform
     * task bars, etc.
     * @return The maximum window bounds
     * @throws HeadlessException if isHeadless returns true
     */
    public Rectangle getMaximumWindowBounds() throws HeadlessException {
        if (isHeadless()) throw new HeadlessException();
        return new Rectangle(0, 0, 
                            Toolkit.getDefaultToolkit().getScreenSize().width, 
                            Toolkit.getDefaultToolkit().getScreenSize().height);
    }
    
    /**
     * Returns all available graphics devices. Stubbed - returns default device.
     */
    public GraphicsDevice[] getScreenDevices() throws HeadlessException {
        if (isHeadless()) throw new HeadlessException();
        return new GraphicsDevice[] {getDefaultScreenDevice()};
    }

}
