/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import swingwt.awt.event.*;
import swingwtx.swing.event.*;

/**
 * Mapped view descendant that allows translation
 * of a Document to a plain SWT Text component (works
 * for JTextField, JPasswordField and JTextArea - ie.
 * Is used by JTextComponent).
 *
 * Since it takes text either en-masse, this is appallingly
 * inefficient. It will do for now (and since JTextField/
 * JPasswordField and JTextArea generally don't contain that
 * much changeable text, it should be ok - we'll use
 * something better for JEditorPane/JTextPane).
 *
 * @author  Robin Rawson-Tetley
 */
public class PlainView extends View {
    
    protected JTextComponent comp = null;
    
    private boolean componentJustChanged = false;
    
    public PlainView(Document doc, JTextComponent component) { 
        super(doc); this.comp = component;
        
        // Subscribe to key presses so we know when to mark the document
        // as updated
        comp.addKeyListener(new KeyAdapter() {
            public void KeyTyped(KeyEvent e) {
                updateModelFromComponent(comp.getText());    
            }
        });
        
        // Subscribe to Document events so we know when to update
        // the component from the model.
        doc.addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updateComponentFromModel(); }
            public void removeUpdate(DocumentEvent e) { updateComponentFromModel(); }
            public void changedUpdate(DocumentEvent e) { updateComponentFromModel(); }
        });
    }
    
    /** Copies the text passed in from the component to the model, 
     *  replacing any content already there entirely. */
    public void updateModelFromComponent(String newText) {
        try {
            doc.remove(0, doc.getLength());    
            doc.insertString(0, newText, null);
            componentJustChanged = true; // Let the twin routine know not to run
                                         // as a result of this change (no need to
                                         // sync component/model twice).
        }
        catch (BadLocationException e) {
            // Shouldn't ever happen since we're replacing the whole lot
            e.printStackTrace();    
        }
    }
    
    private boolean noUpdateNextViewChange = false;
    
    /**
     * Reads the text from the model and replaces the text in the
     * component.
     */
    public void updateComponentFromModel() {
        
        // We set this flag when a keypress fires a model
        // change - this ensures that if the model was just
        // updated from the component, the resulting Document
        // change doesn't fire a component update (losing
        // the caret position and looking weird to the user).
        // Besides - if this flag is set, the two have just
        // been synced so there's no need to do it again.
        if (componentJustChanged) {
            componentJustChanged = false;
            return;
        }
        
        try {
            comp.setText( doc.getText( 0, doc.getLength()));
        }
        catch (BadLocationException e) {
            // Shouldn't ever happen
            e.printStackTrace();
        }
    }

    /** Sets a replacement Document - this routine calls
     *  updateComponentFromModel to load the model's
     *  content into the component
     */
    public void setDocument(Document doc) {
        this.doc = doc;
        updateComponentFromModel();
    }
    
}
