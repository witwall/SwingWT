/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net



*/

package swingwtx.swing;

import java.util.*;
import swingwtx.swing.event.*;

public abstract class AbstractListModel implements ListModel {
    
    protected Vector listenerList = new Vector();

    public void addListDataListener(ListDataListener l) {
	listenerList.add(l);
    }

    public void removeListDataListener(ListDataListener l) {
	listenerList.remove(l);
    }

    public Object[] getListDataListeners() {
        return listenerList.toArray();
    }
    
    protected void fireContentsChanged(Object source, int index0, int index1) {
	ListDataEvent e = new ListDataEvent(source, ListDataEvent.CONTENTS_CHANGED, index0, index1);
	for (int i = 0; i < listenerList.size(); i++) {
            ((ListDataListener) listenerList.get(i)).contentsChanged(e);       
	}
    }

    protected void fireIntervalAdded(Object source, int index0, int index1) {
	ListDataEvent e = new ListDataEvent(source, ListDataEvent.INTERVAL_ADDED, index0, index1);
	for (int i = 0; i < listenerList.size(); i++) {
            ((ListDataListener) listenerList.get(i)).intervalAdded(e);       
	}
    }

    protected void fireIntervalRemoved(Object source, int index0, int index1) {
	ListDataEvent e = new ListDataEvent(source, ListDataEvent.INTERVAL_REMOVED, index0, index1);
	for (int i = 0; i < listenerList.size(); i++) {
            ((ListDataListener) listenerList.get(i)).intervalRemoved(e);       
	}
    }

    public Object[] getListeners(Class listenerType) { 
	return listenerList.toArray(); 
    }
}

