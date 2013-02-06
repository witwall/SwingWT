/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.table;

import swingwtx.swing.*;
import swingwt.awt.*;

public class DefaultTableCellRenderer extends JLabel implements TableCellRenderer {
    
     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setIcon(null);
        setText("");
        setValue(value);
	return this;
    }
     
    protected void setValue(Object value) {
        if (value instanceof Icon)
            setIcon((Icon) value);
        else
            setText( (value == null) ? "" : value.toString() );
    }
    
}
