/*
 * SwingWT Copyright(c)2003-2008 Daniel Naab
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
 * ActionMap implementation... used by JComponent to define behavior on state
 * changes.
 * 
 * @author Naab
 * @version %I%, %G%
 * 
 */
public class ActionMap
{
    private HashMap hashMap = new HashMap();
    private ActionMap parent = null;
    public void setParent(ActionMap parent)
    {
        this.parent = parent;
    }
    public ActionMap getParent()
    {
        return parent;
    }
    public Action get(Object key)
    {
        Object value = null;
        if (hashMap.containsKey(key))
            value = hashMap.get(key);
        else if (parent != null)
            value = parent.get(key);
        return (Action) value;
    }
    public void put(Object key, Action action)
    {
        if (action != null)
        {
            if (key == null)
                remove(key);
            else
                hashMap.put(key, action);
        }
    }
    public void remove(Object key)
    {
        hashMap.remove(key);
    }
    public void clear()
    {
        hashMap.clear();
    }
    public Action[] keys()
    {
        return (Action[]) hashMap.keySet().toArray(new Action[0]);
    }
    public int size()
    {
        return hashMap.size();
    }
    // Package protected helpers for allKeys() method
    HashMap getHashMap()
    {
        return hashMap;
    }
    HashMap allKeyValues()
    {
        HashMap keyValues = (HashMap) hashMap.clone();
        if (parent != null)
            keyValues.putAll(parent.allKeyValues());
        return keyValues;
    }
    public Action[] allKeys()
    {
        return (Action[]) allKeyValues().keySet().toArray(new Action[0]);
    }
}
