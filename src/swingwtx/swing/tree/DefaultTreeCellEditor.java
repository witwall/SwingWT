/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


   
*/


package swingwtx.swing.tree;

import java.util.EventObject;

import swingwt.awt.Component;
import swingwtx.swing.JTree;
import swingwtx.swing.event.CellEditorListener;
import swingwtx.swing.tree.DefaultTreeCellRenderer;
import swingwtx.swing.tree.TreeCellEditor;

import swingwtx.swing.DefaultCellEditor;
import swingwtx.swing.JTextField;

/*
 * @author David Green
 * Copyright (c) 2005 Make Technologies Inc.
 */
public class DefaultTreeCellEditor implements TreeCellEditor {

    protected JTree tree;
    protected DefaultTreeCellRenderer renderer;
    protected TreeCellEditor realEditor;
    
    public DefaultTreeCellEditor(JTree tree,
                 DefaultTreeCellRenderer renderer) {
        this(tree,renderer,null);
    }
    

    public DefaultTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer,
                 TreeCellEditor editor) {
        this.tree = tree;
        this.renderer = renderer;
        this.realEditor = editor;
        if (this.realEditor == null) {
            this.realEditor = createTreeCellEditor();
        }
    }


    protected TreeCellEditor createTreeCellEditor() {
        return new DefaultCellEditor(new JTextField());
    }
    
    public void addCellEditorListener(CellEditorListener l) {
        realEditor.addCellEditorListener(l);
    }

    public void cancelCellEditing() {
        realEditor.cancelCellEditing();
    }

    public Object getCellEditorValue() {
        return realEditor.getCellEditorValue();
    }

    public boolean isCellEditable(EventObject anEvent) {
        return realEditor.isCellEditable(anEvent);
    }

    public void removeCellEditorListener(CellEditorListener l) {
        realEditor.removeCellEditorListener(l);
    }

    public boolean shouldSelectCell(EventObject anEvent) {
        return realEditor.shouldSelectCell(anEvent);
    }


    public boolean stopCellEditing() {
        return realEditor.stopCellEditing();
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        return realEditor.getTreeCellEditorComponent(tree,value,isSelected,expanded,leaf,row);
    }

    
    
}
