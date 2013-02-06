/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.custom.validation;

import swingwtx.swing.*;
import swingwtx.swing.event.*;

import java.util.*;

public class ValidatableJComboBox extends ValidatableComponent {

    protected JComboBox ccomp = null;
    
    public ValidatableJComboBox() { ccomp = new JComboBox(); comp = ccomp;  setupComponent();}
    public ValidatableJComboBox(Object[] items) { ccomp = new JComboBox(items); comp = ccomp; setupComponent();}
    public ValidatableJComboBox(Vector items) { ccomp = new JComboBox(items); comp = ccomp;  setupComponent();}
    public ValidatableJComboBox(ComboBoxModel model) { ccomp = new JComboBox(model); comp = ccomp; setupComponent();}
    
    public void addPopupMenuListener(PopupMenuListener l) { ccomp.addPopupMenuListener(l); }
    public void removePopupMenuListener(PopupMenuListener l) { ccomp.removePopupMenuListener(l); }
    public Object getSelectedItem() { return ccomp.getSelectedItem(); }
    public void setSelectedItem(final Object text) { ccomp.setSelectedItem(text); }
    public ComboBoxModel getModel() { return ccomp.getModel(); }
    public void setModel(ComboBoxModel model) { ccomp.setModel(model); }
    public void addItem(Object item) { ccomp.addItem(item); }
    public void insertItemAt(final Object item, final int index) { ccomp.insertItemAt(item, index); }
    public int getSelectedIndex() { return ccomp.getSelectedIndex(); }
    public Object getItemAt(int index) {  return ccomp.getItemAt(index); }    
    public int getItemCount() { return ccomp.getItemCount(); }
    public void setSelectedIndex(final int index) { ccomp.setSelectedIndex(index); }
    public void removeAllItems() { ccomp.removeAllItems(); }
    public void removeItem(final Object item) { ccomp.removeItem(item); }
    public void removeItemAt(final int index) { ccomp.removeItemAt(index); }
    public void setEditable(boolean b) { ccomp.setEditable(b); }
    public boolean getEditable() { return ccomp.getEditable(); }
    public int getMaximumRowCount() { return ccomp.getMaximumRowCount(); }
    public void setMaximumRowCount(int max) { ccomp.setMaximumRowCount(max); }
}
