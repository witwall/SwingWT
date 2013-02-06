/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

 */

package swingwtx.accessibility;

import swingwt.awt.IllegalComponentStateException;

import java.beans.PropertyChangeListener;
import java.util.Locale;

/**
 * TODO: Implement
 * @author  Naab
 * @version %I%, %G%
 */
public abstract class AccessibleContext
{
    /** abstract method definitions */
    public abstract int getAccessibleIndexInParent();
    public abstract int getAccessibleChildrenCount();
    public abstract Accessible getAccessibleChild(int i);
    public abstract Locale getLocale() throws IllegalComponentStateException;

    public String getAccessibleName() { return null; }
    public void setAccessibleName(String s) {}
    public String getAccessibleDescription() { return null; }
    public void setAccessibleDescription(String s) {}
    public abstract AccessibleRole getAccessibleRole();
    public abstract AccessibleStateSet getAccessibleStateSet();
    public Accessible getAccessibleParent() { return null; }
    public void setAccessibleParent(Accessible a) {}

    /** TODO: Implement PropertyChange support */
    public void addPropertyChangeListener(PropertyChangeListener listener) {}
    public void removePropertyChangeListener(PropertyChangeListener listener) {}
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {}

    public AccessibleAction getAccessibleAction() { return null; }
    public AccessibleComponent getAccessibleComponent() { return null; }
    public AccessibleSelection getAccessibleSelection() { return null; }
    public AccessibleText getAccessibleText() { return null; }
    public AccessibleEditableText getAccessibleEditableText() { return null; }
    public AccessibleValue getAccessibleValue() { return null; }
    public AccessibleIcon[] getAccessibleIcon() { return null; }
    public AccessibleRelationSet getAccessibleRelationSet() { return null; }
    public AccessibleTable getAccessibleTable() { return null; }
}
