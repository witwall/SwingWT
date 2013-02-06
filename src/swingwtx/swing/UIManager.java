/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import swingwt.awt.*;
import swingwtx.swing.plaf.*;
import swingwtx.swing.border.*;

import java.util.*;

/**
 * Dumb skeleton of UI manager that should be good enough to fool
 * any programs attempting to change the Swing L&F. Note that I have
 * no intention of implementation the plaf package as that is just
 * too damn far ;-)
 * @author  Robin Rawson-Tetley
 */
public class UIManager {
    
    public UIManager() {}

    public static class LookAndFeelInfo {
        private String name;
        private String className;
        public LookAndFeelInfo(String name, String className) { this.name = name; this.className = className; }
        public String getClassName() { return className; }
        public String getName() { return name; }
    }
    
    public static Object get(Object key) { return null; }
    public static Object put(Object key, Object value) { return null;}
    public static Font getFont(Object key) { return null; }
    public static Color getColor(Object key) { return null; }

    public static LookAndFeelInfo[] getInstalledLookAndFeels() {
        return new LookAndFeelInfo[]{ new LookAndFeelInfo("SWTNative", "swingwtx.swing.UIManager") };
    }
    public static UIDefaults getLookAndFeelDefaults() { return new UIDefaults(); }
    public static UIDefaults getDefaults() { return getLookAndFeelDefaults(); }
    public static void setLookAndFeelDefaults(UIDefaults defaults) { }
    public static void setInstalledLookAndFeels(LookAndFeelInfo[] infos) throws SecurityException {}
    public static void installLookAndFeel(LookAndFeelInfo info) {}
    public static void installLookAndFeel(String name, String className) {}
    public static LookAndFeel getLookAndFeel() { return null; }
    public static void setLookAndFeel(LookAndFeel newLookAndFeel) throws UnsupportedLookAndFeelException {}
    public static void setLookAndFeel(String className) throws ClassNotFoundException, InstantiationException,  IllegalAccessException, UnsupportedLookAndFeelException {} 
    public static String getSystemLookAndFeelClassName() { return "swingwtx.swing.UIManager"; }
    public static String getCrossPlatformLookAndFeelClassName() { return "swingwtx.swing.UIManager"; }
    public static String getString(Object key) {return getDefaults().getString(key);}
    public static String getString(Object key, java.util.Locale l) {return getDefaults().getString(key,l);}
    static String getString(Object key, Component c) 
    {
        java.util.Locale l = (c == null) ? java.util.Locale.getDefault() : c.getLocale();
        return getString(key, l);
    }
    //TODO: return the actual icon
    public static Icon getIcon(String iconName)
    {
    	return null;
    }
    public static ComponentUI getUI(JComponent target)
    {
    	ComponentUI ui = null;
    	try{ui = getDefaults().getUI(target);}catch(Exception e){}
    	return ui;
    }
    public static Border getBorder(Object key, Locale l) 
    { 
        return getDefaults().getBorder(key,l); 
    }
    public static Border getBorder(Object key) 
    { 
        return getDefaults().getBorder(key);
    }
}
