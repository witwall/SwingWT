/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwtx.swing.event;

import java.beans.*;

/**
 * @author Daniel Spiewak
 * @author Daniel Naab
 */
 public class SwingPropertyChangeSupport
 {
     protected EventListenerList listenerList = new EventListenerList();
 
     // Source object responsible for managing property changes
     private Object source;
 
     /**
      * @param source
      */
     public SwingPropertyChangeSupport(Object source)
     {
         this.source = source;
     }
 
     /**
      * @param propertyName
      * @param oldValue
      * @param newValue
      */
     public void firePropertyChange(String propertyName, Object oldValue, Object newValue)
     {
         PropertyChangeEvent propertyChangeEvent = new PropertyChangeEvent(source, propertyName, oldValue, newValue);
         PropertyChangeListener[] listeners = (PropertyChangeListener[]) listenerList.
                 getListeners(PropertyChangeListener.class);
         for (int i = 0; i < listeners.length; i += 2)
         {
             listeners[i].propertyChange(propertyChangeEvent);
         }
     }
 
     /**
      * @param listener
      */
     public void addPropertyChangeListener(PropertyChangeListener listener)
     {
         listenerList.add(PropertyChangeListener.class, listener);
     }
 
     /**
      * @param listener
      */
     public void removePropertyChangeListener(PropertyChangeListener listener)
     {
         listenerList.remove(PropertyChangeListener.class, listener);
     }
 
     /**
      * @return
      */
     public PropertyChangeListener[] getPropertyChangeListeners()
     {
         return (PropertyChangeListener[]) listenerList.getListeners(PropertyChangeListener.class);
     }
     
 }
