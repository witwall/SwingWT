/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import java.util.*;

public class DefaultListModel extends AbstractListModel {
    private Vector v = new Vector();
    public int getSize() {
	return v.size();
    }
    public Object getElementAt(int index) {
	return v.elementAt(index);
    }
    public void copyInto(Object anArray[]) {
	v.copyInto(anArray);
    }
    public void trimToSize() {
	v.trimToSize();
    }
    public void ensureCapacity(int minCapacity) {
	v.ensureCapacity(minCapacity);
    }
    public void setSize(int newSize) {
	int oldSize = v.size();
	v.setSize(newSize);
	if (oldSize > newSize) {
	    fireIntervalRemoved(this, newSize, oldSize-1);
	}
	else if (oldSize < newSize) {
	    fireIntervalAdded(this, oldSize, newSize-1);
	}
    }
    public int capacity() {
	return v.capacity();
    }
    public int size() {
	return v.size();
    }
    public boolean isEmpty() {
	return v.isEmpty();
    }
    public Enumeration elements() {
	return v.elements();
    }
    public boolean contains(Object elem) {
	return v.contains(elem);
    }
    public int indexOf(Object elem) {
	return v.indexOf(elem);
    }
     public int indexOf(Object elem, int index) {
	return v.indexOf(elem, index);
    }
    public int lastIndexOf(Object elem) {
	return v.lastIndexOf(elem);
    }
    public int lastIndexOf(Object elem, int index) {
	return v.lastIndexOf(elem, index);
    }
    public Object elementAt(int index) {
	return v.elementAt(index);
    }
    public Object firstElement() {
	return v.firstElement();
    }
    public Object lastElement() {
	return v.lastElement();
    }
    public void setElementAt(Object obj, int index) {
	v.setElementAt(obj, index);
	fireContentsChanged(this, index, index);
    }
    public void removeElementAt(int index) {
	v.removeElementAt(index);
	fireIntervalRemoved(this, index, index);
    }
    public void insertElementAt(Object obj, int index) {
	v.insertElementAt(obj, index);
	fireIntervalAdded(this, index, index);
    }
    public void addElement(Object obj) {
	int index = v.size();
	v.addElement(obj);
	fireIntervalAdded(this, index, index);
    }
    public boolean removeElement(Object obj) {
	int index = indexOf(obj);
	boolean rv = v.removeElement(obj);
	if (index >= 0) {
	    fireIntervalRemoved(this, index, index);
	}
	return rv;
    }
    public void removeAllElements() {
	int index1 = v.size()-1;
	v.removeAllElements();
	if (index1 >= 0) {
	    fireIntervalRemoved(this, 0, index1);
	}
    }
    public String toString() {
	return v.toString();
    }
    public Object[] toArray() {
	Object[] rv = new Object[v.size()];
	v.copyInto(rv);
	return rv;
    }
    public Object get(int index) {
	return v.elementAt(index);
    }
    public Object set(int index, Object element) {
	Object rv = v.elementAt(index);
	v.setElementAt(element, index);
	fireContentsChanged(this, index, index);
	return rv;
    }
    public void add(int index, Object element) {
	v.insertElementAt(element, index);
	fireIntervalAdded(this, index, index);
    }
    public Object remove(int index) {
	Object rv = v.elementAt(index);
	v.removeElementAt(index);
	fireIntervalRemoved(this, index, index);
	return rv;
    }
    public void clear() {
	int index1 = v.size()-1;
	v.removeAllElements();
	if (index1 >= 0) {
	    fireIntervalRemoved(this, 0, index1);
	}
    }
    public void removeRange(int fromIndex, int toIndex) {
	for(int i = toIndex; i >= fromIndex; i--) {
	    v.removeElementAt(i);
	}
	fireIntervalRemoved(this, fromIndex, toIndex);
    }
}
