/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab, Robin Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net
                                  bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import swingwtx.swing.event.DocumentEvent;
import swingwtx.swing.event.DocumentListener;
import swingwtx.swing.event.UndoableEditListener;
import swingwtx.swing.tree.TreeNode;
import swingwtx.swing.undo.*;

import java.io.*;
import java.util.*;

/**
 * Main implementation of document support.
 *
 * @author  Robin Rawson-Tetley
 * @author  Naab
 */
public abstract class AbstractDocument implements Document, Serializable {
    
    /** The document content */
    protected Content content = null;    
    /** Document listeners */
    protected Vector documentListeners = new Vector();
    /** Undo listeners */
    protected Vector undoListeners = new Vector();
    /** Properties */
    protected Hashtable props = new Hashtable(2);
    /** Document filter */
    private DocumentFilter documentFilter = null;
    /** Filter-bypass for filters accessing document */
    private DocumentFilter.FilterBypass filterBypass = null;
    
    public abstract Element getParagraphElement(int pos);
    
    /** @return the content length */
    public int getLength() { return content.length(); }

    public void addDocumentListener(DocumentListener listener) { documentListeners.add(listener); }
    public void removeDocumentListener(DocumentListener listener)  { documentListeners.remove(listener); }
    public void addUndoableEditListener(UndoableEditListener listener) { undoListeners.add(listener); }
    public void removeUndoableEditListener(UndoableEditListener listener) { undoListeners.remove(listener); }
    public Object getProperty(Object key) { return props.get(key); }
    public void putProperty(Object key, Object value) { props.put(key, value); }
    /** FIXME: Need to work with UndoManager ? */
    public void remove(int offs, int len) throws BadLocationException { 
        DocumentFilter docFilter = getDocumentFilter();
        if (docFilter != null) { docFilter.remove(getFilterBypass(), offs, len); }
        else doRemove(offs, len, true);
    }
    private void doRemove(int offs, int len, boolean fireEvent) throws BadLocationException { 
        content.remove(offs, len); 
        if (fireEvent)
            fireDocumentEvent(new DefaultDocumentEvent(offs, len, DocumentEvent.EventType.REMOVE));
    }

    public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (length != 0) {
            remove(offset, length);
        }
        insertString(offset, text, attrs);
    }   

    /** FIXME: Need to work with UndoManager ? */
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
        DocumentFilter docFilter = getDocumentFilter();
        if (docFilter != null) { docFilter.insertString(getFilterBypass(), offset, str, a); }
        else doInsertString(offset, str, a, true);
    }
    private void doInsertString(int offset, String str, AttributeSet a, boolean fireEvent) throws BadLocationException {
        content.insertString(offset, str);
        if (fireEvent)
            fireDocumentEvent(new DefaultDocumentEvent(offset, str.length(), DocumentEvent.EventType.INSERT));
    }
    
    public String getText(int offset, int length) throws BadLocationException { return content.getString(offset, length); }
    public void getText(int offset, int length, Segment txt) throws BadLocationException { content.getChars(offset, length, txt);  }

    
    public Position getStartPosition() {
	try {
	    return content.createPosition(0); 
	}
	catch (BadLocationException e) {
	}
	return null;
    }
    public Position getEndPosition() { 
	try {		
	    return content.createPosition(content.length() - 1); 
	}
	catch (BadLocationException e) {
	}
	return null;
    }
    public Position createPosition(int offs) throws BadLocationException { return content.createPosition(offs); }

    /** FIXME: NOT IMPLEMENTED */
    public Element[] getRootElements() {
        return null;
    }

    /** FIXME: What the hell is this routine for? */
    public void render(Runnable r) {
    }
    
    public DocumentFilter getDocumentFilter() { return documentFilter; }
    public void setDocumentFilter(DocumentFilter documentFilter) { this.documentFilter = documentFilter; }
    
    public DocumentFilter.FilterBypass getFilterBypass() {
        if (filterBypass == null) filterBypass = new DefaultFilterBypass();
        return filterBypass;
    }
    
    /** Fires document change events to listeners */
    protected void fireDocumentEvent(DocumentEvent e) {
        for (int i = 0; i < documentListeners.size(); i++) {
                if (e.getType().equals(DocumentEvent.EventType.INSERT)) 
                    ((DocumentListener) documentListeners.get(i)).insertUpdate(e);
                if (e.getType().equals(DocumentEvent.EventType.CHANGE)) 
                    ((DocumentListener) documentListeners.get(i)).changedUpdate(e);
                if (e.getType().equals(DocumentEvent.EventType.REMOVE)) 
                    ((DocumentListener) documentListeners.get(i)).removeUpdate(e);
        }
    }

    
    /**
     * Sub-classes
     */
    public abstract class AbstractElement implements Element, MutableAttributeSet, Serializable, TreeNode {
        public AbstractElement(Element parent, AttributeSet a) { }
        public void dump(PrintStream psOut, int indentAmount) {}
        public int getAttributeCount() { return 0; }
        public boolean isDefined(Object attrName) { return false; }
        public boolean isEqual(AttributeSet attr) { return false; }
        public AttributeSet copyAttributes() { return null; }
        public Object getAttribute(Object attrName) { return null; }
        public Enumeration getAttributeNames() { return null; }
        public boolean containsAttribute(Object name, Object value) { return false; }
        public boolean containsAttributes(AttributeSet attrs) { return false; }
        public AttributeSet getResolveParent() { return null; }
        public void addAttribute(Object name, Object value) {}
        public void addAttributes(AttributeSet attr) {}
        public void removeAttribute(Object name) {}
        public void removeAttributes(Enumeration names) {}
        public void removeAttributes(AttributeSet attrs) {}
        public void setResolveParent(AttributeSet parent) {}
        public Document getDocument() { return null; }
        public Element getParentElement() { return null; }
        public AttributeSet getAttributes() { return null; }
        public String getName() { return ""; }
        public TreeNode getChildAt(int childIndex) { return null; }
        public int getChildCount() { return 0; }
        public TreeNode getParent() { return null; }
        public int getIndex(TreeNode node) { return 0; }

        /**
         * Abstract methods
         */
        public abstract int getStartOffset();
        public abstract int getEndOffset();
        public abstract Element getElement(int index);
        public abstract int getElementCount();
        public abstract int getElementIndex(int offset);
        public abstract boolean isLeaf();
        public abstract boolean getAllowsChildren();
        public abstract Enumeration children();
    }

    public class LeafElement extends AbstractElement {
        public LeafElement(Element parent, AttributeSet a, int offs0, int offs1)  {  super(parent, a); }
        public String toString() { return ""; }
        public int getStartOffset() { return 0; }
        public int getEndOffset() { return 0; }
        public String getName() { return ""; }
        public int getElementIndex(int pos) { return 0; }
        public Element getElement(int index) { return null; }
        public int getElementCount() { return 0; }
        public boolean isLeaf() { return false; }
        public boolean getAllowsChildren() { return false; }
        public Enumeration children() { return null; }
    }

    public static class ElementEdit extends AbstractUndoableEdit implements DocumentEvent.ElementChange {
        public ElementEdit(Element e, int index, Element[] removed, Element[] added)  { super(); }
        public Element getElement() { return null; }
        public int getIndex() { return 0; }
        public Element[] getChildrenRemoved() { return null; }
        public Element[] getChildrenAdded() { return null; }
        public void redo() throws CannotRedoException {}
        public void undo() throws CannotUndoException {}
    }

    public class DefaultDocumentEvent extends CompoundEdit implements DocumentEvent {
        
        protected DocumentEvent.EventType type = null;
        protected int offset = 0;
        protected int len = 0;
        
        public DefaultDocumentEvent(int offs, int len, DocumentEvent.EventType type) { 
            super(); 
            this.type = type;
            this.offset = offs;
            this.len = len;
        }
        public String toString() { return edits.toString(); }
        public boolean addEdit(UndoableEdit anEdit) { return false; }
        public void redo() throws CannotRedoException {}
        public void undo() throws CannotUndoException {}
        public boolean isSignificant() { return true; }
        public String getPresentationName() { return ""; }
        public String getUndoPresentationName() { return ""; }
        public String getRedoPresentationName() { return ""; }
        public DocumentEvent.EventType getType() { return type; }
        public int getOffset() { return offset; }
        public int getLength() { return len; }
        public Document getDocument() { return AbstractDocument.this; }
        public DocumentEvent.ElementChange getChange(Element elem) { return null; }
    }

    public class BranchElement extends AbstractElement {
        public BranchElement(Element parent, AttributeSet attributeSet) { super(parent, attributeSet); }
        public Element positionToElement(int pos) { return null; }
        public void replace(int offset, int length, Element[] elems) { }
        public String getName() { return ""; }
        public int getStartOffset() { return 0; }
        public int getEndOffset() { return 0; }
        public Element getElement(int index) { return null; }
        public int getElementCount() { return 0; }
        public int getElementIndex(int offset) { return 0; }
        public boolean isLeaf() { return false; }
        public boolean getAllowsChildren() { return true; }
        public Enumeration children() { return null; }
    }

    /**
     * Interfaces:
     */
    public interface Content {
        public Position createPosition(int offset) throws BadLocationException;
        public int length();
        public UndoableEdit insertString(int where, String str) throws BadLocationException;
        public UndoableEdit remove(int where, int nitems) throws BadLocationException;
        public String getString(int where, int len) throws BadLocationException;
        public void getChars(int where, int len, Segment txt) throws BadLocationException;
    }

    public interface AttributeContext {
        public AttributeSet addAttribute(AttributeSet old, Object name, Object value);
        public AttributeSet addAttributes(AttributeSet old, AttributeSet attr);
        public AttributeSet removeAttribute(AttributeSet old, Object name);
        public AttributeSet removeAttributes(AttributeSet old, Enumeration names);
        public AttributeSet removeAttributes(AttributeSet old, AttributeSet attrs);
        public AttributeSet getEmptySet();
        public void reclaim(AttributeSet a);
    }
    
    private class DefaultFilterBypass extends DocumentFilter.FilterBypass {
        public Document getDocument() { return AbstractDocument.this; }
        public void remove(int offset, int length) throws BadLocationException { doRemove(offset, length, false); }
        public void insertString(int offset, String string, AttributeSet attr) throws BadLocationException { doInsertString(offset, string, attr, false); }
        public void replace(int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            doRemove(offset, length, false);
            doInsertString(offset, text, attrs, false);
        }
    }
}
