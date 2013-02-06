/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

 import swingwt.awt.*;
 import swingwtx.swing.border.*;
 import swingwtx.swing.event.*;
 import swingwtx.swing.plaf.*;
 
 import java.beans.PropertyChangeListener;
 import java.util.Locale;
 import java.util.Vector;
 import java.util.Enumeration;
 
 /**
  * This UIDefaults implementation is functionally read-only - changing values with put()
  * has no real effect.  It is useful for code that needs to read UIDefaults to create derivative
  * UIResources and acts as a stub for code that attempts to change default resources.
  *
  * If possible with SWT, put() could be modified to recognize set keys and call appropriate methods to
  * update the SWT defaults.  However, since SWT uses native controls this is probably difficult.
  *
  * @author  Naab
  * @version %I%, %G%
  */
 public class UIDefaults extends java.util.Hashtable {
     
     private SwingPropertyChangeSupport swingPropertyChangeSupport = new SwingPropertyChangeSupport(this);
 
     private Object getKeyAsType(Object key, Class type)
     {
         Object value = get(key);
         if (value != null && value.getClass().equals(type)) return value;
         else return null;
     }
 
     private Object getKeyAsType(Object key, Locale locale, Class type)
     {
         // TODO: Handle locales?
         return getKeyAsType(key, type);
     }

     public Enumeration keys() { 
	 return new Vector().elements();
     }
     
     public Font getFont(Object key) { return (Font) getKeyAsType(key, Font.class); }
     public Font getFont(Object key, Locale locale) { return (Font) getKeyAsType(key, locale, Font.class); }
     public Color getColor(Object key) { return (Color) getKeyAsType(key, Color.class); }
     public Color getColor(Object key, Locale locale) { return (Color) getKeyAsType(key, locale, Color.class); }
     public Icon getIcon(Object key) { return (Icon) getKeyAsType(key, Icon.class); }
     public Icon getIcon(Object key, Locale locale) { return (Icon) getKeyAsType(key, locale, Icon.class); }
     public Border getBorder(Object key) { return (Border) getKeyAsType(key, Border.class); }
     public Border getBorder(Object key, Locale locale) { return (Border) getKeyAsType(key, locale, Border.class); }
     public String getString(Object key) { return (String) getKeyAsType(key, String.class); }
     public String getString(Object key, Locale locale) { return (String) getKeyAsType(key, locale, String.class); }
     public Integer getInt(Object key) { return (Integer) getKeyAsType(key, Integer.class); }
     public Integer getInt(Object key, Locale locale) { return (Integer) getKeyAsType(key, locale, Integer.class); }
     public Boolean getBoolean(Object key) { return (Boolean) getKeyAsType(key, Boolean.class); }
     public Boolean getBoolean(Object key, Locale locale) { return (Boolean) getKeyAsType(key, locale, Boolean.class); }
     public Insets getInsets(Object key) { return (Insets) getKeyAsType(key, Insets.class); }
     public Insets getInsets(Object key, Locale locale) { return (Insets) getKeyAsType(key, locale, Insets.class); }
     public Dimension getDimension(Object key) { return (Dimension) getKeyAsType(key, Dimension.class); }
     public Dimension getDimension(Object key, Locale locale) { return (Dimension) getKeyAsType(key, locale, Dimension.class); }
 
     public synchronized void addPropertyChangeListener(PropertyChangeListener listener) { swingPropertyChangeSupport.addPropertyChangeListener(listener); }
     public synchronized void removePropertyChangeListener(PropertyChangeListener listener) { swingPropertyChangeSupport.removePropertyChangeListener(listener); }
     public synchronized PropertyChangeListener[] getPropertyChangeListeners() { return swingPropertyChangeSupport.getPropertyChangeListeners(); }
 
     /* TODO: Implement L&F ComponentUI, ResourceBundle (Locale) support */
     public Class getUIClass(String uiClassID, ClassLoader uiClassLoader) { return null; }
     public Class getUIClass(String uiClassID) { return null; }
     public ComponentUI getUI(JComponent target) { return null; }
     public synchronized void addResourceBundle(String bundleName) {}
     
 }
