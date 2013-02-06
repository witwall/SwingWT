/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   
 
 
 */
package swingwtx.swing;

import java.beans.*;
import java.io.*;
import java.util.*;

import swingwtx.swing.event.*;

/**
 * @author Daniel Spiewak
 */
public abstract class AbstractAction implements Action, Cloneable, Serializable {
    
    protected boolean enabled = true;
    
    private transient ArrayTable arrayTable;
    
    public AbstractAction() {
    }
    
    public AbstractAction(String name) {
        putValue(Action.NAME, name);
    }
    
    public AbstractAction(String name, Icon icon) {
        this(name);
        putValue(Action.SMALL_ICON, icon);
    }
    
    public Object getValue(String key) {
        if (arrayTable == null) {
            return null;
        }
        return arrayTable.get(key);
    }
    
    public void putValue(String key, Object newValue) {
        Object oldValue = null;
        if (arrayTable == null) {
            arrayTable = new ArrayTable();
        }
        if (arrayTable.containsKey(key))
            oldValue = arrayTable.get(key);
        if (newValue == null) {
            arrayTable.remove(key);
        } else {
            arrayTable.put(key, newValue);
        }
        firePropertyChange(key, oldValue, newValue);
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean newValue) {
        boolean oldValue = this.enabled;
        
        if (oldValue != newValue) {
            this.enabled = newValue;
            firePropertyChange(
            "enabled",
            new Boolean(oldValue),
            new Boolean(newValue));
        }
    }
    
    public Object[] getKeys() {
        if (arrayTable == null) {
            return null;
        }
        Object[] keys = new Object[arrayTable.size()];
        arrayTable.getKeys(keys);
        return keys;
    }
    
    protected SwingPropertyChangeSupport changeSupport;
    
    protected void firePropertyChange(
    String propertyName,
    Object oldValue,
    Object newValue) {
        if (changeSupport == null
        || (oldValue != null
        && newValue != null
        && oldValue.equals(newValue))) {
            return;
        }
        changeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }
    
    public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            changeSupport = new SwingPropertyChangeSupport(this);
        }
        changeSupport.addPropertyChangeListener(listener);
    }
    
    public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {
        if (changeSupport == null) {
            return;
        }
        changeSupport.removePropertyChangeListener(listener);
    }
    
    public synchronized PropertyChangeListener[] getPropertyChangeListeners() {
        if (changeSupport == null) {
            return new PropertyChangeListener[0];
        }
        return changeSupport.getPropertyChangeListeners();
    }
    
    protected Object clone() throws CloneNotSupportedException {
        AbstractAction newAction = (AbstractAction) super.clone();
        synchronized (this) {
            if (arrayTable != null) {
                newAction.arrayTable = (ArrayTable) arrayTable.clone();
            }
        }
        return newAction;
    }
    
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        
        ArrayTable.writeArrayTable(s, arrayTable);
    }
    
    private void readObject(ObjectInputStream s)
    throws ClassNotFoundException, IOException {
        s.defaultReadObject();
        for (int counter = s.readInt() - 1; counter >= 0; counter--) {
            putValue((String) s.readObject(), s.readObject());
        }
    }
    
    static class ArrayTable implements Cloneable {
        private Object table = null;
        private static final int ARRAY_BOUNDARY = 8;
        
        public void put(Object key, Object value) {
            if (table == null) {
                table = new Object[] { key, value };
            } else {
                int size = size();
                if (size < ARRAY_BOUNDARY) {
                    if (containsKey(key)) {
                        Object[] tmp = (Object[]) table;
                        for (int i = 0; i < tmp.length - 1; i += 2) {
                            if (tmp[i].equals(key)) {
                                tmp[i + 1] = value;
                                break;
                            }
                        }
                    } else {
                        Object[] array = (Object[]) table;
                        int i = array.length;
                        Object[] tmp = new Object[i + 2];
                        System.arraycopy(array, 0, tmp, 0, i);
                        
                        tmp[i] = key;
                        tmp[i + 1] = value;
                        table = tmp;
                    }
                } else {
                    if ((size == ARRAY_BOUNDARY) && isArray()) {
                        grow();
                    }
                    ((Hashtable) table).put(key, value);
                }
            }
        }
        
        public Object get(Object key) {
            Object value = null;
            if (table != null) {
                if (isArray()) {
                    Object[] array = (Object[]) table;
                    for (int i = 0; i < array.length - 1; i += 2) {
                        if (array[i].equals(key)) {
                            value = array[i + 1];
                            break;
                        }
                    }
                } else {
                    value = ((Hashtable) table).get(key);
                }
            }
            return value;
        }
        
        public int size() {
            int size;
            if (table == null)
                return 0;
            if (isArray()) {
                size = ((Object[]) table).length / 2;
            } else {
                size = ((Hashtable) table).size();
            }
            return size;
        }
        
        public boolean containsKey(Object key) {
            boolean contains = false;
            if (table != null) {
                if (isArray()) {
                    Object[] array = (Object[]) table;
                    for (int i = 0; i < array.length - 1; i += 2) {
                        if (array[i].equals(key)) {
                            contains = true;
                            break;
                        }
                    }
                } else {
                    contains = ((Hashtable) table).containsKey(key);
                }
            }
            return contains;
        }
        
        public Object remove(Object key) {
            Object value = null;
            if (key == null) {
                return null;
            }
            if (table != null) {
                if (isArray()) {
                    int index = -1;
                    Object[] array = (Object[]) table;
                    for (int i = array.length - 2; i >= 0; i -= 2) {
                        if (array[i].equals(key)) {
                            index = i;
                            value = array[i + 1];
                            break;
                        }
                    }
                    
                    if (index != -1) {
                        Object[] tmp = new Object[array.length - 2];
                        System.arraycopy(array, 0, tmp, 0, index);
                        if (index < tmp.length)
                            System.arraycopy(
                            array,
                            index + 2,
                            tmp,
                            index,
                            tmp.length - index);
                        table = (tmp.length == 0) ? null : tmp;
                    }
                } else {
                    value = ((Hashtable) table).remove(key);
                }
                if (size() == 7 && !isArray()) {
                    shrink();
                }
            }
            return value;
        }
        
        public void clear() {
            table = null;
        }
        
        public Object clone() {
            ArrayTable newArrayTable = new ArrayTable();
            if (isArray()) {
                Object[] array = (Object[]) table;
                for (int i = 0; i < array.length - 1; i += 2) {
                    newArrayTable.put(array[i], array[i + 1]);
                }
            } else {
                Hashtable tmp = (Hashtable) table;
                Enumeration keys = tmp.keys();
                while (keys.hasMoreElements()) {
                    Object o = keys.nextElement();
                    newArrayTable.put(o, tmp.get(o));
                }
            }
            return newArrayTable;
        }
        
        public Object[] getKeys(Object[] keys) {
            if (table == null) {
                return null;
            }
            if (isArray()) {
                Object[] array = (Object[]) table;
                if (keys == null) {
                    keys = new Object[array.length / 2];
                }
                for (int i = 0, index = 0;
                i < array.length - 1;
                i += 2, index++) {
                    keys[index] = array[i];
                }
            } else {
                Hashtable tmp = (Hashtable) table;
                Enumeration e = tmp.keys();
                int counter = tmp.size();
                if (keys == null) {
                    keys = new Object[counter];
                }
                while (counter > 0) {
                    keys[--counter] = e.nextElement();
                }
            }
            return keys;
        }
        
        private boolean isArray() {
            return (table instanceof Object[]);
        }
        
        private void grow() {
            Object[] array = (Object[]) table;
            Hashtable tmp = new Hashtable(array.length / 2);
            for (int i = 0; i < array.length; i += 2) {
                tmp.put(array[i], array[i + 1]);
            }
            table = tmp;
        }
        
        private void shrink() {
            Hashtable tmp = (Hashtable) table;
            Object[] array = new Object[tmp.size() * 2];
            Enumeration keys = tmp.keys();
            int j = 0;
            
            while (keys.hasMoreElements()) {
                Object o = keys.nextElement();
                array[j] = o;
                array[j + 1] = tmp.get(o);
                j += 2;
            }
            table = array;
        }
        
        static void writeArrayTable(ObjectOutputStream s, ArrayTable table)
        throws IOException {
            Object keys[];
            
            if (table == null || (keys = table.getKeys(null)) == null) {
                s.writeInt(0);
            } else {
                int validCount = 0;
                
                for (int counter = 0; counter < keys.length; counter++) {
                    if ((keys[counter] instanceof Serializable)
                    && (table.get(keys[counter]) instanceof Serializable)) {
                        validCount++;
                    } else {
                        keys[counter] = null;
                    }
                }
                s.writeInt(validCount);
                if (validCount > 0) {
                    for (int counter = 0; counter < keys.length; counter++) {
                        if (keys[counter] != null) {
                            s.writeObject(keys[counter]);
                            s.writeObject(table.get(keys[counter]));
                            if (--validCount == 0) {
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}

/*
 *****************************************************
 * Copyright 2003 Completely Random Solutions *
 *                                												*
 * DISCLAMER:                                 					*
 * We are not responsible for any damage      		*
 * directly or indirectly caused by the usage 			*
 * of this or any other class in assosiation  			*
 * with this class.  Use at your own risk.   			*
 * This or any other class by CRS is not   			*
 * certified for use in life support systems, 			*
 * the Space Shuttle, in use or developement  		*
 * of nuclear reactors, weapons of mass       		*
 * destruction, or in interplanitary conflict.				*
 * (Unless otherwise specified)               				*
 *****************************************************
 */
