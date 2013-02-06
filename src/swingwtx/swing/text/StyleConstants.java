/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/
package swingwtx.swing.text;

import swingwt.awt.Color;

/**
 * @author Robin Rawson-Tetley
 */
public class StyleConstants {
    
    public static final Object NameAttribute = "name";
    public static final Object ResolveAttribute = "resolve";
    
    public static void setAlignment(MutableAttributeSet a, int align) {
    	a.addAttribute(FontConstants.Alignment, new Integer(align));
    }    
    
    public static void setItalic(MutableAttributeSet a, boolean italic) {
    	a.addAttribute(FontConstants.Italic, new Boolean(italic));
    }
    
    public static void setBold(MutableAttributeSet a, boolean bold) {
    	a.addAttribute(FontConstants.Bold, new Boolean(bold));
    }
    
    public static void setFontSize(MutableAttributeSet a, int size) {
    	a.addAttribute(FontConstants.Size, new Integer(size));
    }
    
    public static void setFontFamily(MutableAttributeSet a, String family) {
    	a.addAttribute(FontConstants.Family, family);
    }
    
    public static void setBackground(MutableAttributeSet a, Color c) {
    	a.addAttribute(ColorConstants.Background, c);
    }
    
    public static void setForeground(MutableAttributeSet a, Color c) {
    	a.addAttribute(ColorConstants.Foreground, c);
    }
    
    
    public static boolean isItalic(MutableAttributeSet a) {
    	Boolean bool = (Boolean) a.getAttribute(FontConstants.Italic);
    	if (bool != null) 
    		return bool.booleanValue();
    	else
    		return false;
    }
    
    public static boolean isBold(MutableAttributeSet a) {
    	Boolean bool = (Boolean) a.getAttribute(FontConstants.Bold);
    	if (bool != null) 
    		return bool.booleanValue();
    	else
    		return false;
    }

    public static int getAlignment(MutableAttributeSet a) {
    	Integer i = (Integer) a.getAttribute(FontConstants.Alignment);
    	if (i != null)
    		return i.intValue();
    	else
    		return 0;
    }
    
    public static int getFontSize(MutableAttributeSet a) {
    	Integer i = (Integer) a.getAttribute(FontConstants.Size);
    	if (i != null)
    		return i.intValue();
    	else
    		return 0;
    }
    
    public static String getFontFamily(MutableAttributeSet a) {
    	return (String) a.getAttribute(FontConstants.Family);
    }
    
    public static Color getBackground(MutableAttributeSet a) {
    	return (Color) a.getAttribute(ColorConstants.Background);
    }
    
    public static Color getForeground(MutableAttributeSet a) {
    	return (Color) a.getAttribute(ColorConstants.Foreground);
    }
    
    public static int ALIGN_LEFT = 0;
    public static int ALIGN_RIGHT = 1;
    public static int ALIGN_CENTER = 2;
    public static int ALIGN_JUSTIFIED = 3;
    
    public static class FontConstants {
    	public static final Object Alignment = "alignment";
        public static final Object Family = "family";
        public static final Object Size ="size";
        public static final Object Bold = "bold";
        public static final Object Italic = "italic";
    }
    
    public static class ColorConstants {
        public static final Object Foreground = "foreground";
        public static final Object Background = "background";
    }
    
}
