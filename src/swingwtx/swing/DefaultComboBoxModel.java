package swingwtx.swing;

import java.util.*;

public class DefaultComboBoxModel extends AbstractListModel implements MutableComboBoxModel {
    
    protected Vector objects;
    protected Object selectedObject;

    public DefaultComboBoxModel() {objects = new Vector(); }
    public DefaultComboBoxModel(Object items[]) {
        
        objects = new Vector(items.length);
        for (int i = 0; i < items.length; i++) {
            objects.add(items[i]);    
        }
        if ( objects.size() > 0 )
            selectedObject = objects.get(0);
    }

    public DefaultComboBoxModel(Vector v) {
        objects = v;
        if (objects.size() > 0)
            selectedObject = objects.get(0);
    }

    public void setSelectedItem(Object anObject) {
        if ((selectedObject != null && !selectedObject.equals( anObject )) || selectedObject == null && anObject != null) {
	    selectedObject = anObject;
	    fireContentsChanged(this, -1, -1);
        }
    }

    public Object getSelectedItem() {
        return selectedObject;
    }

    public int getSize() {
        return objects.size();
    }

    public Object getElementAt(int index) {
        if (index >= 0 && index < objects.size())
            return objects.get(index);
        else
            return null;
    }

    public int getIndexOf(Object anObject) {
        return objects.indexOf(anObject);
    }

    public void addElement(Object anObject) {
        objects.add(anObject);
        fireIntervalAdded(this,objects.size()-1, objects.size()-1);
        if (objects.size() == 1 && selectedObject == null && anObject != null)
            setSelectedItem( anObject );
    }

    public void insertElementAt(Object anObject,int index) {
        objects.insertElementAt(anObject,index);
        fireIntervalAdded(this, index, index);
    }

    public void removeElementAt(int index) {
        if (objects.get(index) == selectedObject) {
            if (index == 0) 
                setSelectedItem(objects.size() == 1 ? null : objects.get( index + 1 ) );
            else 
                setSelectedItem(objects.get(index - 1 ));
        }

        objects.removeElementAt(index);

        fireIntervalRemoved(this, index, index);
    }
    
    public void removeElement(Object anObject) {
        int index = getIndexOf(anObject);
        removeElementAt(index);
    }

    public void removeAllElements() {
        if ( objects.size() > 0 ) {
            int firstIndex = 0;
            int lastIndex = objects.size() - 1;
            objects.removeAllElements();
	    selectedObject = null;
            fireIntervalRemoved(this, firstIndex, lastIndex);
        } 
        else
	    selectedObject = null;
    }
}

