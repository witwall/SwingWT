/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwtx.swing;

import java.util.*;
import swingwt.awt.*;
import swingwt.awt.event.*;
import swingwtx.swing.table.*;
import swingwtx.swing.tree.*;
import swingwtx.swing.event.*;

/**
 * Default editing class for JTree and JTable
 *
 * @author  Robin Rawson-Tetley
 */
public class DefaultCellEditor extends AbstractCellEditor 
                               implements TableCellEditor, TreeCellEditor { 

    /** The component being edited. */
    protected JComponent editorComponent;
    protected int clickCountToStart = 1;
    /** The handling class */
    protected SWTEditor handler = null;

    public DefaultCellEditor(final JTextField textField) {
        editorComponent = textField;
	this.clickCountToStart = 2;
        handler = new SWTEditor() {
            public void setValue(Object value) {
		textField.setText((value != null) ? value.toString() : "");
            }
	    public Object getCellEditorValue() {
		return textField.getText();
	    }
        };
        textField.addFocusListener(handler);
    }

    public DefaultCellEditor(final JCheckBox checkBox) {
        editorComponent = checkBox;
        handler = new SWTEditor() {
            public void setValue(Object value) { 
            	boolean selected = false; 
		if (value instanceof Boolean) {
		    selected = ((Boolean)value).booleanValue();
		}
		else if (value instanceof String) {
		    selected = value.equals("true");
		}
		checkBox.setSelected(selected);
            }

	    public Object getCellEditorValue() {
		return new Boolean(checkBox.isSelected());
	    }
        };
        checkBox.addFocusListener(handler);
    }

    public DefaultCellEditor(final JComboBox comboBox) {
        editorComponent = comboBox;
        handler = new SWTEditor() {
	    public void setValue(Object value) {
		comboBox.setSelectedItem(value);
            }

	    public Object getCellEditorValue() {
		return comboBox.getSelectedItem();
	    }
                
            public boolean shouldSelectCell(EventObject anEvent) { 
                return true;
            }
	    public boolean stopCellEditing() {
		return super.stopCellEditing();
	    }
        };
        comboBox.addChangeListener(handler);
    }

    public SWTEditor getHandler() {
        return handler;    
    }
    
    public Component getComponent() {
	return editorComponent;
    }

    public void setClickCountToStart(int count) {
	clickCountToStart = count;
    }
    
    public int getClickCountToStart() {
	return clickCountToStart;
    }

    public Object getCellEditorValue() {
        return handler.getCellEditorValue();
    }

    public boolean isCellEditable(EventObject anEvent) { 
	return handler.isCellEditable(anEvent); 
    }

    public boolean shouldSelectCell(EventObject anEvent) { 
	return handler.shouldSelectCell(anEvent); 
    }

    public boolean stopCellEditing() {
	return handler.stopCellEditing();
    }
    
    public void cancelCellEditing() {
	handler.cancelCellEditing();
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value,
						boolean isSelected,
						boolean expanded,
						boolean leaf, int row) {
                                                    
	handler.setValue(value);
	return editorComponent;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
						 boolean isSelected,
						 int row, int column) {
        handler.setValue(value);
	return editorComponent;
    }
    
    
    
    /**
     * Handling class that maps details of the widget being
     * used for editing. Different component types override
     * the setValue/getCellEditorValue methods to return
     * the correct stuff.
     */
    protected abstract class SWTEditor implements FocusListener, ChangeListener {

        public abstract Object getCellEditorValue();

        public abstract void setValue(Object value);

        public boolean isCellEditable(EventObject anEvent) {
	    if (anEvent instanceof MouseEvent) { 
		return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
	    }
	    return true;
	}
        
        public boolean shouldSelectCell(EventObject anEvent) { 
            return true; 
        }

        public boolean startCellEditing(EventObject anEvent) {
	    return true;
	}

        public boolean stopCellEditing() {
	    fireEditingStopped();                // Tell listeners
            editorComponent.getSWTPeer().dispose(); // Destroy the editor component
	    return true;
	}

        public void cancelCellEditing() { 
	   fireEditingCanceled(); 
        }
        
        public void focusGained(FocusEvent e) {
        }
        
        public void focusLost(FocusEvent e) {
            stopCellEditing();
        }

        public void stateChanged(ChangeEvent e) {
            stopCellEditing();
        }
        
    }

}
