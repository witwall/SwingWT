/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */

package swingwtx.swing.text;

import java.util.Enumeration;

public interface AttributeSet {
    
    public static final Object NameAttribute = StyleConstants.NameAttribute;
    public static final Object ResolveAttribute = StyleConstants.ResolveAttribute;
    
    public interface FontAttribute {
    }
    public interface ColorAttribute {
    }
    public interface CharacterAttribute {
    }
    public interface ParagraphAttribute {
    }
    public int getAttributeCount();
    public boolean isDefined(Object attrName);
    public boolean isEqual(AttributeSet attr);
    public AttributeSet copyAttributes();
    public Object getAttribute(Object key);
    public Enumeration getAttributeNames();
    public boolean containsAttribute(Object name, Object value);
    public boolean containsAttributes(AttributeSet attributes);
    public AttributeSet getResolveParent();
}
