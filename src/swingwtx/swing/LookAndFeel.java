/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;

/**
 * Dumb skeleton of a look and feel class - should be enough for existing
 * ones to compile, but not interfere with native looks
 */
public abstract class LookAndFeel {
    public LookAndFeel() {}
    public static void installColors(JComponent c, String defaultBgName, String defaultFgName) {}
    public static void installColorsAndFont(JComponent c, String defaultBgName, String defaultFgName, String defaultFontName) {}
    public static void installBorder(JComponent c, String defaultBorderName) {}
    public static void uninstallBorder(JComponent c) {}
    public static Object makeIcon(final Class baseClass, final String gifFile) {return null;}
    public void provideErrorFeedback(swingwt.awt.Component component) {}
    public static Object getDesktopPropertyValue(String systemPropertyName, Object fallbackValue) {return null;}
    public abstract String getName();
    public abstract String getID();
    public abstract String getDescription();
    public boolean getSupportsWindowDecorations() {return true;}
    public abstract boolean isNativeLookAndFeel();
    public abstract boolean isSupportedLookAndFeel();
    public void initialize() {}
    public void uninitialize() {}
}
