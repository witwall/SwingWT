package swingwtx.swing;


import swingwt.awt.event.*;

import swingwtx.swing.event.*;


import java.io.Serializable;
import java.util.EventListener;


public class DefaultButtonModel implements ButtonModel, Serializable {

    protected transient ChangeEvent changeEvent = null;
    protected EventListenerList eventListenerList = new EventListenerList();
    
    protected String actionCommand = null;
    protected int mnemonic = 0;
    protected ButtonGroup buttonGroup = null;
    
    public DefaultButtonModel() 
    {
    }
    private boolean armed = false;
    private boolean selected = false;
    private boolean pressed = false;
    private boolean enabled = false;
    private boolean rollover = false;
    // not used
    public final static int ARMED = 1;
    public final static int SELECTED = 8;
    public final static int PRESSED = 4;
    public final static int ENABLED = 16;
    public final static int ROLLOVER = 2;
    public void setActionCommand(String actionCommand){this.actionCommand = actionCommand;}
    public String getActionCommand(){return actionCommand;}
    public boolean isArmed()    {return armed;}
    public boolean isSelected() {return selected;}
    public boolean isEnabled()  {return enabled;}
    public boolean isPressed()  {return pressed;}
    public boolean isRollover() {return rollover;}
    public void setArmed(boolean b) 
    {
    	armed=b;
        fireStateChanged();
    }
    public void setEnabled(boolean b) 
    {
    	enabled=b;
        fireStateChanged();
    }
    public void setSelected(boolean b) 
    {
    	selected = b;
        fireStateChanged();
        
    }
    public void setPressed(boolean b) 
    {
    	pressed=b;
        fireStateChanged();
    }   
    public void setRollover(boolean b) 
    {
    	rollover = b;
        fireStateChanged();
    }
    public void setMnemonic(int key) 
    {
    	mnemonic = key;
    	fireStateChanged();
    }
    public int getMnemonic(){return mnemonic;}
    public void addChangeListener(ChangeListener l){eventListenerList.add(ChangeListener.class, l);}
    public void removeChangeListener(ChangeListener l){eventListenerList.remove(ChangeListener.class, l);}
    public ChangeListener[] getChangeListeners() 
    {
        return (ChangeListener[])eventListenerList.getListeners(ChangeListener.class);
    }
    protected void fireStateChanged() {
        Object[] listeners = eventListenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChangeListener.class) {
                if (changeEvent == null)
                    changeEvent = new ChangeEvent(this);
                ((ChangeListener)listeners[i+1]).stateChanged(changeEvent);
            }          
        }
    }   
    public void addActionListener(ActionListener l){eventListenerList.add(ActionListener.class, l);}
    public void removeActionListener(ActionListener l){eventListenerList.remove(ActionListener.class, l);}
    public ActionListener[] getActionListeners(){return (ActionListener[])eventListenerList.getListeners(ActionListener.class);}
    protected void fireActionPerformed(ActionEvent e) {
        Object[] listeners = eventListenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ActionListener.class) {
                ((ActionListener)listeners[i+1]).actionPerformed(e);
            }          
        }
    }   
    public void addItemListener(ItemListener l) {eventListenerList.add(ItemListener.class, l);}
    public void removeItemListener(ItemListener l) {eventListenerList.remove(ItemListener.class, l);}
    public ItemListener[] getItemListeners() {return (ItemListener[])eventListenerList.getListeners(ItemListener.class);}

    
    protected void fireItemStateChanged(ItemEvent e) {
        Object[] listeners = eventListenerList.getListenerList();
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ItemListener.class) {
                ((ItemListener)listeners[i+1]).itemStateChanged(e);
            }          
        }
    }   

    public <T extends EventListener> T[] getListeners(Class<T> listenerType) {return eventListenerList.getListeners(listenerType);}

    public Object[] getSelectedObjects(){return null;}
    public void setGroup(ButtonGroup group) {this.buttonGroup = group;}

    public ButtonGroup getGroup() {return buttonGroup;}

}
