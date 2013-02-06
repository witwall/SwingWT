/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   

*/

package swingwtx.swing;

import java.io.*;

import swingwt.awt.*;
import swingwtx.swing.text.*;

public class JTextPane extends JEditorPane {
    
    public JTextPane() {
        loadTextEditorPane();
        setEditorKit(createDefaultEditorKit());
    }
    public JTextPane(StyledDocument doc) {
        this();
        setStyledDocument(doc);
    }
    public String getUIClassID() {
        return getClass().getName();
    }
    public void setDocument(Document doc) {
        if (doc instanceof StyledDocument) {
            super.setDocument(doc);
        } else {
            throw new IllegalArgumentException("Model must be StyledDocument");
        }
    }
    public void setStyledDocument(StyledDocument doc) {
        super.setDocument(doc);
    }
    public StyledDocument getStyledDocument() {
        return (StyledDocument) getDocument();
    } 
    public void replaceSelection(String content) {
        replaceSelection(content, null);
    }
    private void replaceSelection(String content, AttributeSet attr) {
        // TODO: this isn't right.. assume caret is at the end with no selection
        setText(getText() + content);
    }
    public void insertComponent(Component c) {
    }
    public void insertIcon(Icon g) {
    }
    public Style addStyle(String nm, Style parent) {
        StyledDocument doc = getStyledDocument();
        return doc.addStyle(nm, parent);
    }
    public void removeStyle(String nm) {
        StyledDocument doc = getStyledDocument();
        doc.removeStyle(nm);
    }
    public Style getStyle(String nm) {
        StyledDocument doc = getStyledDocument();
        return doc.getStyle(nm);
    }
    public void setLogicalStyle(Style s) {
        StyledDocument doc = getStyledDocument();
        doc.setLogicalStyle(getCaretPosition(), s);
    }
    public Style getLogicalStyle() {
        StyledDocument doc = getStyledDocument();
        return doc.getLogicalStyle(getCaretPosition());
    }
    public AttributeSet getCharacterAttributes() {
        StyledDocument doc = getStyledDocument();
        Element run = doc.getCharacterElement(getCaretPosition());
        if (run != null) {
            return run.getAttributes();
        }
        return null;
    }
    public void setCharacterAttributes(AttributeSet attr, boolean replace) {
        int p0 = getSelectionStart();
        int p1 = getSelectionEnd();
        if (p0 != p1) {
            StyledDocument doc = getStyledDocument();
            doc.setCharacterAttributes(p0, p1 - p0, attr, replace);
        } else {
            MutableAttributeSet inputAttributes = getInputAttributes();
            if (replace) {
                inputAttributes.removeAttributes(inputAttributes);
            }
            inputAttributes.addAttributes(attr);
        }
    }
    public AttributeSet getParagraphAttributes() {
        StyledDocument doc = getStyledDocument();
        Element paragraph = doc.getParagraphElement(getCaretPosition());
        if (paragraph != null) {
            return paragraph.getAttributes();
        }
        return null;
    }
    public void setParagraphAttributes(AttributeSet attr, boolean replace) {
        int p0 = getSelectionStart();
        int p1 = getSelectionEnd();
        StyledDocument doc = getStyledDocument();
        doc.setParagraphAttributes(p0, p1 - p0, attr, replace);
    }
    public MutableAttributeSet getInputAttributes() {
        return null;
        //return getStyledEditorKit().getInputAttributes();
    }
    protected final StyledEditorKit getStyledEditorKit() {
        return (StyledEditorKit) getEditorKit();
    }
    protected EditorKit createDefaultEditorKit() {
        return new StyledEditorKit();
    }
    public final void setEditorKit(EditorKit kit) {
    }

    public void setDisabledTextColor(Color c) {}

    public void read(BufferedReader fileInput, String string) {
	try {
            StringBuffer sb = new StringBuffer();
            String line = fileInput.readLine();
            while (line != null) {
                 sb.append(line);
                 sb.append('\n');
                 line = fileInput.readLine();
            }
            setText(sb.toString());
         }
         catch (Exception ex) {
             ex.printStackTrace(System.err);
         }
    }

    
}
