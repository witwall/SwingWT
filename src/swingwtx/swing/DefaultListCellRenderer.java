/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwtx.swing;

public class DefaultListCellRenderer extends swingwtx.swing.JLabel implements ListCellRenderer {
    
    public DefaultListCellRenderer() {
        //setOpaque(true);
    }
     
    public swingwt.awt.Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        String s = value.toString();   
        setText(s);
        return this;
     }
 }
