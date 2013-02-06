/*
 * SwingWT Copyright(c)2004, Daniel Naab
 * 
 * For more information on distributing and using this program, please see the
 * accompanying "COPYING" file.
 * 
 * Contact me by electronic mail: dannaab@users.sourceforge.net
 * 
 */

package swingwtx.swing;

import java.util.HashMap;

/**
 * This is kind of lame... same functionality as InputMap, different class name.
 * @author Dan
 */
public class InputMap
{
    private HashMap hashMap = new HashMap();
    private InputMap parent = null;

    public void setParent(InputMap parent) { this.parent = parent; }
    public InputMap getParent() { return parent; }

    public Object get(KeyStroke keyStroke)
    {
        Object value = null;

        if (hashMap.containsKey(value))
            value = hashMap.get(keyStroke);
        else if (parent != null)
            value = parent.get(keyStroke);

        return value;
    }

    public void put(KeyStroke keyStroke, Object inputMapKey)
    {
        if (inputMapKey != null)
        {
            if (inputMapKey == null) remove(keyStroke);
            else hashMap.put(keyStroke, inputMapKey);
        }
    }

    public void remove(KeyStroke key) { hashMap.remove(key); }
    public void clear() { hashMap.clear(); }
    public KeyStroke[] keys() { return (KeyStroke[]) hashMap.keySet().toArray(new KeyStroke[0]); }
    public int size() { return hashMap.size(); }

    // Package protected helpers for allKeys() method
    HashMap getHashMap() { return hashMap; }
    HashMap allKeyValues()
    {
        HashMap keyValues = (HashMap) hashMap.clone();
        if (parent != null) keyValues.putAll(parent.allKeyValues());
        return keyValues;
    }

    public KeyStroke[] allKeys()
    {
        return (KeyStroke[]) allKeyValues().keySet().toArray(new KeyStroke[0]);
    }
}
