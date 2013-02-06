/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import swingwtx.swing.*;
import swingwt.awt.event.*;
import swingwt.awt.Color;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

import java.util.*;

/**
 * Superclass for all textual components. 
 *
 * Note that we have to use a few tricks here as Swing can do a whole
 * bunch of stuff we can't because of the native peer widgets. 
 * 
 * Basically, we make this work like a JTextField and leave it upto
 * subclasses to override whatever they need to.
 *
 * @author  Robin Rawson-Tetley
 */
public class JTextComponent extends swingwtx.swing.JComponent {
    
    protected Text ppeer = null;
    protected String pText = "";
    protected boolean pEditable = true;
    protected boolean pWordWrap = false;
    protected int pRows = 0;
    protected int pCols = 0;
    protected int selStart = -1;
    protected int selEnd = -1;
    protected Caret caret = new DefaultCaret(this);
    protected Document doc = null;
    protected PlainView view = null;
    protected Keymap keymap = null;
    
    /** Return value for thread safe accessors */
    protected String retVal = "";
    protected int iRetVal = 0;
    
    public JTextComponent() { 
        doc = new PlainDocument();
        view = new PlainView(doc, this);
    }
    
    public Document getDocument() {
        return doc;    
    }
    
    public void setDocument(Document doc) {
        this.doc = doc;
        view.setDocument(doc);   
    }
    
    public String getText() { 
        retVal=""; 
        SwingUtilities.invokeSync(new Runnable() { 
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) 
                    retVal = ppeer.getText(); 
                else retVal = pText;
            }
        }); return retVal; 
    }
    
    public void setText(String text) { 
        if (text == null) 
            pText = "";
        else
            pText = text; 
        SwingUtilities.invokeSync(new Runnable() { 
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) 
                    ppeer.setText(pText);
                
                // XXX: This should handle updated the document without triggering an infinite loop back into here.
                // However, what if the document isn't extended from AbstractDocument?
                if (getDocument() instanceof AbstractDocument)
					try {
						((AbstractDocument)getDocument()).getFilterBypass().replace(0, getDocument().getLength(), pText, null);
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
            }
        }); 
    }
    
    public void setEditable(boolean b) { pEditable = b; if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.setEditable(b); }
    public boolean isEditable() { return pEditable; }
    
    public int getCaretPosition() { 
        iRetVal = 0;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer)) iRetVal = -1; else iRetVal = ppeer.getCaretPosition(); 
            }
        });
        return iRetVal;
    }
    
    public void setCaretPosition(final int pos) { 
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    ppeer.setSelection(pos); 
                    ppeer.showSelection();
                }
            }
        });
    }
    
    public void copy() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.copy(); 
            }
        });
    }
    
    public void cut() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.cut(); 
            }
        });
    }
    
    public void paste() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.paste(); 
            }
        });
    }
    
    public Caret getCaret() {
        return caret;
    }
    
    public void setCaret(Caret caret) {
        this.caret = caret;
    }
    
    /** NOT IMPLEMENTED **/
    public void replaceSelection(String content) {
        // TODO: Implement correctly!  assume caret is at the end with no selection
        setText(getText()+content);
    }
    
    
    public void select(final int selectionStart, final int selectionEnd) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.setSelection(selectionStart, selectionEnd);
            }
        });
    }
    
    public void selectAll() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.selectAll(); 
            }
        });
    }
    
    public void setSelectionStart(int start) {    
        selStart = start;
        if (selStart >= 0 && selEnd >= 0)
            select(selStart, selEnd);
    }
    
    public void setSelectionEnd(int end) {
        selEnd = end;
        if (selStart > 0 && selEnd > 0)
            select(selStart, selEnd);
    }
    
    private void refreshCachedSelection() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    org.eclipse.swt.graphics.Point point = ppeer.getSelection();
                    JTextComponent.this.selStart = point.x;
                    JTextComponent.this.selEnd = point.y;
                }
            }
        });
    }
    
    public int getSelectionStart() {
        refreshCachedSelection();
        return selStart;
    }
    
    public int getSelectionEnd() {
        refreshCachedSelection();
        return selEnd;
    }
    
    /** NOT IMPLEMENTED - Has no meaning in native plaf **/
    public void setSelectedTextColor(swingwt.awt.Color c) { }
    /** NOT IMPLEMENTED - Has no meaning in native plaf **/
    public void setSelectionColor(swingwt.awt.Color c) { }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        ppeer = new Text(parent.getComposite(), SWT.BORDER | SWT.SINGLE);
        ppeer.setText(pText);
        ppeer.setEditable(pEditable);
        peer = ppeer;
        this.parent = parent;
        ppeer.addVerifyListener(new TextEventHandler());
    }

    public void addInputMethodListener() {

    }

    /**
     * TODO: Fix this
     */
    static final JTextComponent getFocusedComponent() {
    	// SwingWT issure return null because 
        //return (JTextComponent)AppContext.getAppContext().
        //    get(FOCUSED_COMPONENT);
    	return null;
    }
    /**
     *  TODO: Fix this 
     * @return always null
     */
    public swingwtx.swing.Action[] getActions() {
        return null;
    }
    public void setKeymap(Keymap map) {
        Keymap old = keymap;
        keymap = map;
    // TODO Fix this    
    //firePropertyChange("keymap", old, keymap);
	//updateInputMap(old, map);
    }
    public Keymap getKeymap() {
        return keymap;
    }
    public static Keymap addKeymap(String nm, Keymap parent) {
        /*Keymap map = new DefaultKeymap(nm, parent); 
        if (nm != null) {
            getKeymapTable().put(nm, map);
        }
        return map;
	*/
	return parent;
    }
    private static HashMap getKeymapTable() 
    {
/*  	
        AppContext appContext = AppContext.getAppContext();
        HashMap<String,Keymap> keymapTable = 
            (HashMap<String,Keymap>)appContext.get(KEYMAP_TABLE);
        if (keymapTable == null) {
            keymapTable = new HashMap<String,Keymap>(17);
            appContext.put(KEYMAP_TABLE, keymapTable);
            //initialize default keymap
            Keymap binding = addKeymap(DEFAULT_KEYMAP, null);
            binding.setDefaultAction(new 
                                     DefaultEditorKit.DefaultKeyTypedAction());
        }
        return keymapTable;
*/
	return null;
    }
    //TODO: fix this , always return white  
    public Color getSelectedTextColor()
    {
    	return Color.white;
    }
    //TODO: fix this , always return white  
    public Color getSelectionColor()
    {
    	return Color.white;
    }

    /**
     * 
     * TODO Comment!!
     *
     * @param b
     */
    public void setDragEnabled(boolean b) {
    	
    }
    
    protected class TextEventHandler implements org.eclipse.swt.events.VerifyListener {
        public TextEventHandler() {}
        /*
         * @see org.eclipse.swt.events.VerifyListener#verifyText(org.eclipse.swt.events.VerifyEvent)
         */
        public void verifyText(VerifyEvent event) {
            if (doc instanceof AbstractDocument) {
                AbstractDocument document = (AbstractDocument) doc;
                DocumentFilter filter = document.getDocumentFilter();

                /**
                 * SWT works differently than Swing... Swing doesn't have a concept of
                 * "allowing" a modification - all modifications are actions that the DocumentFilter
                 * either acts on or doesn't.  We get around this by not allowing SWT to manage any
                 * inserts - we do it all programatically via the Swing method.
                 */
                event.doit = true;
                if (filter != null) {
	                if (event.start == event.end) {
	                    try {
	                        int perfectInsertLength = document.getLength() + event.text.length();
	                        
	                        document.insertString(event.start, event.text, null);

	                        int insertDiff = perfectInsertLength - document.getLength();
	                        if (insertDiff == 0) {
		                        int lastIndex = event.start + event.text.length();
		                        if (lastIndex <= document.getLength())
		                            event.text = document.getText(0, document.getLength()).substring(event.start, lastIndex);
		                        else
		                            event.doit = false;
	                        }
	                        else
	                            event.doit = false;
	                    } catch (BadLocationException e) {
	                        // Unexpected state... tell SWT not to do anything
	                        event.doit = false;
	                    }
	                }
	                else {
	                    try {
	                        int perfectInsertLength = document.getLength() - (event.end - event.start) + event.text.length();
	                        
	                        document.remove(event.start, event.end - event.start);
	                        document.insertString(event.start, event.text, null);
	                        
	                        int insertDiff = perfectInsertLength - document.getLength();
	                        if (insertDiff > 0) {
	                            event.end -= insertDiff;
	                            event.text = document.getText(0, document.getLength()).substring(event.start, event.end);
	                        }
	                        else if (insertDiff < 0)
	                            event.doit = false;
	                    } catch (BadLocationException e) {
	                        // Unexpected state... tell SWT not to do anything
	                        event.doit = false;
	                    }
	                }
                }
            }
        }
    }
}
