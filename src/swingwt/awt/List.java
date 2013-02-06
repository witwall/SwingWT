/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

public class List extends swingwtx.swing.JList {

    public List() { super(); }
    public List(Object[] items) { super(items); }
    public List(java.util.Vector items) { super(items); }
    public List(int rows) { super(); }
    public List(int rows, boolean multipleMode) { super(); }
    
    public void add(String item) {
        super.addItem(item);
    }
    
    public int countItems() {
        return super.getItemCount();    
    }
    
    public String getItem(int index) {
        return super.getItemAt(index).toString();    
    }
    
    public void select(int index) {
        super.setSelectedIndex(index);
    }
    
    public void remove(String item) {
        super.removeItem(item);
    }
    
    public void remove(int index) {
        super.removeItemAt(index);
    }
    
    public void removeAll() {
        super.removeAllItems();
    }
    
    public void replaceItem(String item, int index) {
        super.replaceItemAt(item, index);
    }

}
