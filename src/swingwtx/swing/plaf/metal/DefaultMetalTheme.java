/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net



*/

package swingwtx.swing.plaf.metal;

import swingwtx.swing.plaf.*;
import swingwt.awt.*;

public class DefaultMetalTheme extends MetalTheme {
    
    private static final String[] fontNames = {
        "Dialog", "Dialog", "Dialog", "Dialog", "Dialog", "Dialog"
    };
    
    private static final int[] fontStyles = {
        Font.BOLD, Font.PLAIN, Font.PLAIN, Font.BOLD, Font.BOLD, Font.PLAIN
    };
    
    private static final int[] fontSizes = {
        12, 12, 12, 12, 12, 10
    };
    private static final String[] defaultNames = {
        "swing.plaf.metal.controlFont",
        "swing.plaf.metal.systemFont",
        "swing.plaf.metal.userFont",
        "swing.plaf.metal.controlFont",
        "swing.plaf.metal.controlFont",
        "swing.plaf.metal.smallFont"
    };
    
    static String getDefaultFontName(int key) {
        return fontNames[key];
    }
    static int getDefaultFontSize(int key) {
        return fontSizes[key];
    }
    static int getDefaultFontStyle(int key) {
        return fontStyles[key];
    }
    static String getDefaultPropertyName(int key) {
        return defaultNames[key];
    }
    private static final ColorUIResource primary1 = new ColorUIResource(
    102, 102, 153);
    private static final ColorUIResource primary2 = new ColorUIResource(153,
    153, 204);
    private static final ColorUIResource primary3 = new ColorUIResource(
    204, 204, 255);
    private static final ColorUIResource secondary1 = new ColorUIResource(
    102, 102, 102);
    private static final ColorUIResource secondary2 = new ColorUIResource(
    153, 153, 153);
    private static final ColorUIResource secondary3 = new ColorUIResource(
    204, 204, 204);

    public String getName() { return "Steel"; }
    
    public DefaultMetalTheme() {
        install();
    }
    protected ColorUIResource getPrimary1() { return primary1; }
    protected ColorUIResource getPrimary2() { return primary2; }
    protected ColorUIResource getPrimary3() { return primary3; }
    protected ColorUIResource getSecondary1() { return secondary1; }
    protected ColorUIResource getSecondary2() { return secondary2; }
    protected ColorUIResource getSecondary3() { return secondary3; }
    
    
    public FontUIResource getControlTextFont() {
        return getFont(CONTROL_TEXT_FONT);
    }
    
    public FontUIResource getSystemTextFont() {
        return getFont(SYSTEM_TEXT_FONT);
    }
    
    public FontUIResource getUserTextFont() {
        return getFont(USER_TEXT_FONT);
    }
    
    public FontUIResource getMenuTextFont() {
        return getFont(MENU_TEXT_FONT);
    }
    
    public FontUIResource getWindowTitleFont() {
        return getFont(WINDOW_TITLE_FONT);
    }
    
    public FontUIResource getSubTextFont() {
        return getFont(SUB_TEXT_FONT);
    }
    
    private FontUIResource getFont(int key) {
        return null;
    }
    
    void install() {
    }
    boolean isSystemTheme() {
        return (getClass() == DefaultMetalTheme.class);
    }
}
