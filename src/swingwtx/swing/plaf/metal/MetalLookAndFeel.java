/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.plaf.metal;

import java.util.Hashtable;
import swingwt.awt.Component;
import swingwtx.swing.plaf.*;
import swingwtx.swing.UIDefaults;
import swingwtx.swing.plaf.basic.*;

public class MetalLookAndFeel extends BasicLookAndFeel {

    private static MetalTheme metalTheme = new DefaultMetalTheme();

    /**
     * Returns true if running on Windows. (spit spit)
     */
    static boolean isWindows() {
        return false;
    }

    static boolean useSystemFonts() {
        return false;
    }

    public String getName() {
        return "Metal (NOT!)";
    }

    public String getID() {
        return "Metal (NOT!)";
    }

    public String getDescription() {
        return "Your native platform Look and Feel!";
    }

    
    public boolean isNativeLookAndFeel() {
        return false;
    }


    public boolean isSupportedLookAndFeel() {
        return true;
    }

    public boolean getSupportsWindowDecorations() {
        return true;
    }

    protected void initClassDefaults(Hashtable table) {
    }
    
    protected void initSystemColorDefaults(Hashtable table) {
    }

    protected void initComponentDefaults(Hashtable table) {
    }

    protected void createDefaultTheme() {
        getCurrentTheme();
    }

    public Hashtable getDefaults() {
        UIDefaults table = new UIDefaults();
	initClassDefaults(table);
	initSystemColorDefaults(table);
	initComponentDefaults(table);
	return table;
    }
    
    public void provideErrorFeedback(Component component) {
    }

    public static void setCurrentTheme(MetalTheme theme) {
	metalTheme = theme;
    }

    private static MetalTheme getCurrentTheme() {
        return metalTheme;
    }

    public static FontUIResource getControlTextFont() { return getCurrentTheme().getControlTextFont();}
    public static FontUIResource getSystemTextFont() { return getCurrentTheme().getSystemTextFont();}
    public static FontUIResource getUserTextFont() { return getCurrentTheme().getUserTextFont();}
    public static FontUIResource getMenuTextFont() { return getCurrentTheme().getMenuTextFont();}
    public static FontUIResource getWindowTitleFont() { return getCurrentTheme().getWindowTitleFont();}
    public static FontUIResource getSubTextFont() { return getCurrentTheme().getSubTextFont();}

    public static ColorUIResource getDesktopColor() { return getCurrentTheme().getDesktopColor(); }
    public static ColorUIResource getFocusColor() { return getCurrentTheme().getFocusColor(); }

    public static ColorUIResource getWhite() { return getCurrentTheme().getWhite(); }
    public static ColorUIResource getBlack() { return getCurrentTheme().getBlack(); }
    public static ColorUIResource getControl() { return getCurrentTheme().getControl(); }
    public static ColorUIResource getControlShadow() { return getCurrentTheme().getControlShadow(); }
    public static ColorUIResource getControlDarkShadow() { return getCurrentTheme().getControlDarkShadow(); }
    public static ColorUIResource getControlInfo() { return getCurrentTheme().getControlInfo(); } 
    public static ColorUIResource getControlHighlight() { return getCurrentTheme().getControlHighlight(); }
    public static ColorUIResource getControlDisabled() { return getCurrentTheme().getControlDisabled(); }

    public static ColorUIResource getPrimaryControl() { return getCurrentTheme().getPrimaryControl(); }  
    public static ColorUIResource getPrimaryControlShadow() { return getCurrentTheme().getPrimaryControlShadow(); }  
    public static ColorUIResource getPrimaryControlDarkShadow() { return getCurrentTheme().getPrimaryControlDarkShadow(); }  
    public static ColorUIResource getPrimaryControlInfo() { return getCurrentTheme().getPrimaryControlInfo(); } 
    public static ColorUIResource getPrimaryControlHighlight() { return getCurrentTheme().getPrimaryControlHighlight(); }  

    public static ColorUIResource getSystemTextColor() { return getCurrentTheme().getSystemTextColor(); }
    public static ColorUIResource getControlTextColor() { return getCurrentTheme().getControlTextColor(); }  
    public static ColorUIResource getInactiveControlTextColor() { return getCurrentTheme().getInactiveControlTextColor(); }  
    public static ColorUIResource getInactiveSystemTextColor() { return getCurrentTheme().getInactiveSystemTextColor(); }
    public static ColorUIResource getUserTextColor() { return getCurrentTheme().getUserTextColor(); }
    public static ColorUIResource getTextHighlightColor() { return getCurrentTheme().getTextHighlightColor(); }
    public static ColorUIResource getHighlightedTextColor() { return getCurrentTheme().getHighlightedTextColor(); }

    public static ColorUIResource getWindowBackground() { return getCurrentTheme().getWindowBackground(); }
    public static ColorUIResource getWindowTitleBackground() { return getCurrentTheme().getWindowTitleBackground(); }
    public static ColorUIResource getWindowTitleForeground() { return getCurrentTheme().getWindowTitleForeground(); }
    public static ColorUIResource getWindowTitleInactiveBackground() { return getCurrentTheme().getWindowTitleInactiveBackground(); }
    public static ColorUIResource getWindowTitleInactiveForeground() { return getCurrentTheme().getWindowTitleInactiveForeground(); }

    public static ColorUIResource getMenuBackground() { return getCurrentTheme().getMenuBackground(); }
    public static ColorUIResource getMenuForeground() { return getCurrentTheme().getMenuForeground(); }
    public static ColorUIResource getMenuSelectedBackground() { return getCurrentTheme().getMenuSelectedBackground(); }
    public static ColorUIResource getMenuSelectedForeground() { return getCurrentTheme().getMenuSelectedForeground(); }
    public static ColorUIResource getMenuDisabledForeground() { return getCurrentTheme().getMenuDisabledForeground(); }
    public static ColorUIResource getSeparatorBackground() { return getCurrentTheme().getSeparatorBackground(); }
    public static ColorUIResource getSeparatorForeground() { return getCurrentTheme().getSeparatorForeground(); }
    public static ColorUIResource getAcceleratorForeground() { return getCurrentTheme().getAcceleratorForeground(); }
    public static ColorUIResource getAcceleratorSelectedForeground() { return getCurrentTheme().getAcceleratorSelectedForeground(); }

}
