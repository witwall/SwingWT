/*
	SwingWT
	Copyright(c)2003-2008, R. Rawson-Tetley
	
	For more information on distributing and using this program, please
	see the accompanying "COPYING" file.
	
	Contact me by electronic mail: bobintetley@users.sourceforge.net
	
	
*/
package swingwtx.swing.text;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * An attribute set implementation using a Hashtable
 * 
 * @author Robin Rawson-Tetley
 */
public class SimpleAttributeSet implements MutableAttributeSet, Cloneable {

	private Hashtable t = new Hashtable();

	public void addAttribute(Object name, Object value) {
		t.put(name, value);
	}

	public void addAttributes(AttributeSet attributes) {
		Enumeration names = attributes.getAttributeNames();
		while (names.hasMoreElements()) {
			Object name = names.nextElement();
			addAttribute(name, attributes.getAttribute(name));
		}
	}

	public void removeAttribute(Object name) {
		t.remove(name);
	}

	public void removeAttributes(AttributeSet attributes) {
		Enumeration names = attributes.getAttributeNames();
		while (names.hasMoreElements()) {
			Object name = names.nextElement();
			removeAttribute(name);
		}
	}

	public void removeAttributes(Enumeration names) {
		while (names.hasMoreElements()) {
			Object name = names.nextElement();
			removeAttribute(name);
		}
	}

	public void setResolveParent(AttributeSet parent) {
	}

	public boolean containsAttribute(Object name, Object value) {
		return value.equals(getAttribute(name));
	}

	public boolean containsAttributes(AttributeSet attributes) {
		boolean result = true;
		Enumeration names = attributes.getAttributeNames();
		while (result && names.hasMoreElements()) {
			Object name = names.nextElement();
			result = attributes.getAttribute(name).equals(getAttribute(name));
		}
		return result;
	}

	public AttributeSet copyAttributes() {
		return (AttributeSet) clone();
	}

	public Object getAttribute(Object key) {
		return t.get(key);
	}

	public int getAttributeCount() {
		return t.size();
	}

	public Enumeration getAttributeNames() {
		return t.keys();
	}

	public AttributeSet getResolveParent() {
		return null;
	}

	public boolean isDefined(Object attrName) {
		return t.containsKey(attrName);
	}

	public boolean isEqual(AttributeSet attr) {
		return ((getAttributeCount() == attr.getAttributeCount()) && containsAttributes(attr));
	}

	public Object clone() {
		SimpleAttributeSet a = null;
		try {
			a = (SimpleAttributeSet) super.clone();
			a.t = (Hashtable) t.clone();
		} 
		catch (CloneNotSupportedException cnse) {
		}
		return a;
	}
}
