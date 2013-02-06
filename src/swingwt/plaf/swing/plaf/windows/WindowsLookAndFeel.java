package swingwt.plaf.swing.plaf.windows;
import swingwtx.swing.JComponent;
import swingwtx.swing.plaf.basic.*;


public class WindowsLookAndFeel extends BasicLookAndFeel
{
	public String getName(){return "Windows";}
    public String getDescription(){return "The Microsoft Windows Look and Feel";}
    public String getID(){return "Windows";}
    public boolean isSupportedLookAndFeel(){return false;}
    public static void installColors(JComponent c, String defaultBgName, String defaultFgName) {}
    public static void installColorsAndFont(JComponent c, String defaultBgName, String defaultFgName, String defaultFontName) {}
    public static void installBorder(JComponent c, String defaultBorderName) {}
    public static void uninstallBorder(JComponent c) {}
    public static Object makeIcon(final Class baseClass, final String gifFile) {return null;}
    public void provideErrorFeedback(swingwt.awt.Component component) {}
    public static Object getDesktopPropertyValue(String systemPropertyName, Object fallbackValue) {return null;}
    public boolean getSupportsWindowDecorations() {return true;}
    public boolean isNativeLookAndFeel(){return false;}
    public void initialize() {}
    public void uninitialize() {}
}
