package swingwt.plaf.swing.plaf.motif;
import swingwtx.swing.JComponent;
import swingwtx.swing.plaf.basic.*;


public class MotifLookAndFeel extends BasicLookAndFeel
{
	public String getName() {return "CDE/Motif";}
    public String getID() {return "Motif";}
    public String getDescription() {return "The CDE/Motif Look and Feel";}
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
