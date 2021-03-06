/*
   SwingWT
   Copyright(c)2003-2006, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.event;

import java.util.*;
import java.lang.reflect.*;

public class EventListenerList {
    
    protected Vector listenerList = new Vector();

    public Object[] getListenerList() 
    {
    	return listenerList.toArray();
    }

    public synchronized void add(Class t, EventListener l) {
        listenerList.add(l);
    }

    public synchronized void remove(Class t, EventListener l) {

        listenerList.remove(l);
    }

    /*
    public synchronized void add(Class t, EventListener l) {
        Iterator iterator = listenerList.iterator();
        while (iterator.hasNext())
        {
            Class listenerClass = (Class) iterator.next();
            EventListener eventListener = (EventListener) iterator.next();
            if (listenerClass == t && eventListener == l)
            {
                iterator.remove();
                listenerList
            }

        }
        for (int i = 0; i < listeners.length; i +=2 ) {
            if ( listeners[i] == t &&
                 listeners[i+1] == l )
            {

            }
        }
        listenerList.add(t);
        listenerList.add(l);
    }

    public synchronized void remove(Class t, EventListener l) {
        int index = listenerList.indexOf(l);
        listenerList.remove(index);
        listenerList.remove(index);
    }
    */

    	public <T extends EventListener> T[] getListeners(Class<T> t) {	
        int n = getListenerCount(t);
        T[] listeners = (T[])Array.newInstance(t, n); 
        int listenersIndex = 0;
        for (int i = listenerList.size() -2; i>=0; i-=2) {
            if (listenerList.get(i) == t) {
                listeners[listenersIndex] = listeners[i+1];
            listenersIndex++;
            }
        }
        return listeners;
    }

    public int getListenerCount() {
	return listenerList.size();
    }

    public int getListenerCount(Class t) {
        int listenerCount = 0;
        Object[] listeners = getListenerList();
        for (int i = 0; i < listeners.length; i +=2 ) {
            if (t == listeners[i])
                listenerCount++;
        }
        return listenerCount;
    }

    public String toString() {
	Object[] list = listenerList.toArray();
	String s = "EventListenerList: ";
	s += list.length + " listeners: ";
	for (int i = 0 ; i < list.length ; i++) {
	    s += " type " + ((Class)list[i]).getName();
	    s += " listener " + list[i].toString();
	}
	return s;
    }
}

