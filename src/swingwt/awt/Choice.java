/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   

*/

package swingwt.awt;

import swingwt.awt.event.ItemEvent;
import swingwt.awt.event.ItemListener;

import swingwtx.swing.DefaultComboBoxModel;

public class Choice extends AWTSwingWrapper {

    public Choice() { 
        swingPeer = new swingwtx.swing.JComboBox(new DefaultComboBoxModel());
    }
    public Choice(Object[] items) {
        swingPeer = new swingwtx.swing.JComboBox(items);
    }
    public Choice(java.util.Vector items) {
        swingPeer = new swingwtx.swing.JComboBox(items);
    }
    
    private final swingwtx.swing.JComboBox getSwingPeer() { return (swingwtx.swing.JComboBox) swingPeer; }
    
    public void add(String item) { getSwingPeer().addItem(item); }
    public void addItem(String item) { getSwingPeer().addItem(item); }
    public void addItemListener(ItemListener l) { getSwingPeer().addItemListener(l); }
    public int countItems() { return getItemCount(); }
    public String getItem(int index) { return getSwingPeer().getItemAt(index).toString(); }
    public int getItemCount() { return getSwingPeer().getItemCount(); }
    public ItemListener[] getItemListeners() { return getSwingPeer().getItemListeners(); }
    public int getSelectedIndex() { return getSwingPeer().getSelectedIndex(); }
    public String getSelectedItem() { return getSwingPeer().getSelectedItem().toString(); }
    public Object[] getSelectedObjects() { return getSwingPeer().getSelectedObjects(); }
    public void insert(String item, int index) { getSwingPeer().insertItemAt(item, index); }
    protected void processEvent(AWTEvent e) { getSwingPeer().processEvent(e); }
    protected void processItemEvent(ItemEvent e) { getSwingPeer().processItemEvent(e); }
    public void remove(int position) { getSwingPeer().remove(position); }
    public void remove(String item) { getSwingPeer().removeItem(item); }
    public void removeAll() { getSwingPeer().removeAll(); }
    public void removeItemListener(ItemListener l) { getSwingPeer().removeItemListener(l); }
    public void select(int pos) { getSwingPeer().setSelectedIndex(pos); }
    public void select(String str) { getSwingPeer().setSelectedItem(str); } 
}
