/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.editorpanes;

import swingwt.awt.Color;
import swingwt.awt.Font;
import swingwtx.swing.event.DocumentListener;
import swingwtx.swing.event.DocumentEvent;
import swingwtx.swing.text.*;
import swingwtx.swing.*;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.*;

import java.util.*;

/**
 * An implementation of an editor pane that supports text entry and
 * StyledDocuments.
 *
 * @author  Robin Rawson-Tetley
 */
public class TextEditorPane extends JTextComponent implements EditorPane, DocumentListener {


    protected Document doc = null;
    protected Color selectionColor = Color.BLACK; 
    protected StyledText ppeer = null;
    protected EditorKit kit = null;
    protected Object retval = null;
    
    public static TextEditorPane activePane = null;

    public TextEditorPane() {
    	activePane = this;
    	setEditorKit(new StyledEditorKit());
    }
    
    public void addHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) {
    }
    
    public void removeHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) {
    }
    
    public swingwt.awt.Dimension calculatePreferredSize() {
        // Default 300x200
        swingwt.awt.Dimension size = new swingwt.awt.Dimension(300, 200);
        setSize(size);
        return size;
    }    
    
    public int getCaretPosition() {
    	int val = 0;
    	if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
	    	SwingUtilities.invokeSync(new Runnable() {
	    		public void run() {
	    			retval = new Integer(((StyledText)getSWTPeer()).getCaretOffset());
	    		}
	    	});
    	}
    	val = ((Integer) retval).intValue();
    	return val;
    }
    
    public String getContentType() {
        return "text/plain";
    }
    
    public swingwtx.swing.text.Document getDocument() {
        return doc;
    }
    
    public swingwtx.swing.text.EditorKit getEditorKit() {
        return kit;
    }
    
    public swingwtx.swing.JComponent getJComponent() {
        return this;
    }
    
    public swingwt.awt.Color getSelectionColor() {
        return selectionColor;
    }
    
    public int getSelectionEnd() {
        return super.getSelectionEnd();
    }
    
    public int getSelectionStart() {
        return super.getSelectionStart();
    }
    
    public Control getSWTPeer() {
        return ppeer;
    }    
    
    public String getText() {
    	try {
    		return doc.getText(0, doc.getLength());
    	}
    	catch (BadLocationException e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public boolean isEditable() {
        return super.isEditable();
    }
    
    public void setCaretPosition(final int pos) {
    	if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
	    	SwingUtilities.invokeSync(new Runnable() {
	    		public void run() {
	    			((StyledText)getSWTPeer()).setCaretOffset(pos);
	    		}
	    	});
    	}
    }
    
    public void setContentType(String contentType) {
    }
    
    public void setDocument(swingwtx.swing.text.Document newdoc) {
        doc = newdoc;
        doc.addDocumentListener(this);
    }
    
    public void setEditable(boolean b) {
        super.setEditable(b);
    }
    
    public void setEditorKit(swingwtx.swing.text.EditorKit k) {
        if (doc == null)
        	setDocument(k.createDefaultDocument());
    }
    
    public void setPage(java.net.URL url) throws java.io.IOException {
        throw new RuntimeException("Cannot set page in TextEditorPane");
    }
    
    public void setPage(String url) throws java.io.IOException {
        throw new RuntimeException("Cannot set page in TextEditorPane");
    }
    
    public void setText(String text) {
        
    	// Update the document
    	try {
	    	doc.remove(0, doc.getLength());
	        doc.insertString(0, text, null);
    	}
    	catch (BadLocationException e) {
    		e.printStackTrace();
    	}
    	
    	updatePeerTextFromDocument();
        
    }
    
    /**
     * Updates the peer's text from the document object
     */
    public void updatePeerTextFromDocument() {
        // Load the document into the peer
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))  {
                    ppeer.setText(getDocumentText());
                }
            }
        });
    }
    
    /**
     * Returns the content of the document as a string.
     * @return
     */
    protected String getDocumentText() {
    	try {
    		String content = doc.getText(0, doc.getLength());
    		return content;
    	}
    	catch (BadLocationException e) {
    		return "";
    	}
    }
    
    public void setSelectionColor(swingwt.awt.Color color) {
        selectionColor = color;
    }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        
    	int swtFlags = SWT.BORDER;
        swtFlags |= pWordWrap ? SWT.WRAP : SWT.NONE;
        
        if (parent instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) parent;
            boolean useHorizontal = false, useVertical = false;
            switch (scrollPane.getHorizontalScrollBarPolicy()) {
                case JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS:
                    useHorizontal = true;
                    break;
                case JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED:
                    useHorizontal = true;
                    break;
                case JScrollPane.HORIZONTAL_SCROLLBAR_NEVER:
                    break;
                default:
                    useHorizontal = false;
                    break;
            }
            switch (scrollPane.getVerticalScrollBarPolicy()) {
                case JScrollPane.VERTICAL_SCROLLBAR_ALWAYS:
                    useVertical = true;
                    break;
                case JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED:
                    useVertical = true;
                    break;
                case JScrollPane.VERTICAL_SCROLLBAR_NEVER:
                    break;
                default:
                    useVertical = false;
                    break;
            }
            swtFlags |= useHorizontal ? SWT.H_SCROLL : SWT.NONE;
            swtFlags |= useVertical ? SWT.V_SCROLL : SWT.NONE;
        }
    	
        ppeer = new StyledText(parent.getComposite(), swtFlags);
        //ppeer.setEditable(pEditable); -- This stops content being inserted. *sigh*
        peer = ppeer;

        // Load the document in
        updatePeerTextFromDocument();
        applyAllFormattings();
        
        // Capture all keystrokes sent to the peer so they can update
        // the document
        ppeer.addKeyListener(new KeyAdapter() {
        	public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
        		
        		int c = e.keyCode;
        		int ch = e.character;
        		
        		// Allow through certain control characters to perform
        		// actions without firing this stuff
        		if (
        				(c == 16777217) ||
        				(c == 16777218) ||
        				(c == 16777219) ||
        				(c == 16777220) ||
        				(c == SWT.TAB) ||
        				(c == SWT.PAGE_DOWN) ||
        				(c == SWT.PAGE_UP) ||
        				(c == SWT.HOME) ||
        				(c == SWT.ALT) ||
        				(c == SWT.CTRL) ||
        				(c == SWT.SHIFT) ||
        				(c == SWT.CAPS_LOCK) ||
        				(c == SWT.END)
						) 
        		{
        			e.doit = true;
        			return;
        		}		
        		
				// Handle deletes and backspaces
                if (e.character == SWT.DEL) {
                	handleDelete(false);
                	e.doit = false;
                	return;
                }
                if (e.character == SWT.BS) {
                	handleDelete(true);
                	e.doit = false;
                	return;
                }
						
				// Assuming it's adding a char
                e.doit = false;
        		addKeyStrokeToDocument(e.character);
        		
        	}
        });
        
    }
    
    /**
     * When the user presses a key in the editable area, it is immediately
     * displayed by the SWT peer, but we add it to the document object
     * here to keep that upto date.
     * 
     * @param c
     */
    protected void addKeyStrokeToDocument(char c) {
    	
    	try {

    		int pos = getCaretPosition();
    		
    		// Look for a current attributeset that is in our caret position
    		// so we can reapply it to the currently typed char - we do that
    		// by extending the length
    		extendAttributeSetForCaret(pos);
    		
    		// Scan through all of the existing formattings, and if their 
    		// offsets are greater than where our caret currently is, we shunt
    		// them on one position so formatting behaves like Swing
    		if (getFormattings() != null) {
	    		for (int i = 0; i < getFormattings().size(); i++) {
	    			Formatting f = (Formatting) getFormattings().get(i);
	    			if (f != null)
	    				if (f.offset > pos)
	    					f.offset++;
	    		}
    		}
    		
    		// Insert the keystroke into our document. This forces the
    		// peer to update to match
    		doc.insertString(pos - 1 , new String(new char[] { c } ), null);
    		
    		// Move the caret back
    		setCaretPosition(pos);
    		
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /** What happens when the delete/backspace key is pressed */
    protected void handleDelete(boolean backspace) {
    	try {
	    	
    		int pos = getCaretPosition();
    		
    		// Check the formattings to make sure that
    		// if the caret is in their range, that
    		// we shorten the length. If their offset
    		// is after us, we reduce it as well
    		if (getFormattings() != null) {
	    		for (int i = 0; i < getFormattings().size(); i++) {
	    			Formatting f = (Formatting) getFormattings().get(i);
	        		if (f != null) {
	        			
	        			// Is the character to be deleted in the range of this
	        			// formatting?
	        			if ((pos > f.offset) && 
	        				(pos < (f.offset + f.length))
	    					) {
	        				f.length--;
	        			}
	        			
	        			// If the offset is after the end of the
	        			// document, then 
	        			if (f.offset > doc.getLength() - 1) {
	        				f.length--;
	        			}
	        			
	        			// If the offset is after our caret, reduce
	        			// it by one
	        			if (f.offset > pos) {
	        				f.offset--;
	        			}
	        		}
	    		}
    		}
    		
	    	doc.remove(pos, 1);
	    	
	    	// Move the caret
	    	if (backspace)
	    		setCaretPosition(pos);
	    	else
	    		setCaretPosition(pos);

	    }
		catch (BadLocationException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Locates the AttributeSet used to format a particular char
     * position in the document and extends it's length by
     * one char. This is handy so newly typed chars in an
     * existing range are given the same formatting.
     * 
     * @param offset
     * @param length
     * @param s
     */
    protected void extendAttributeSetForCaret(int caret) {
    	
    	// Don't do anything if it's not a DefaultStyledDocument
    	if (getFormattings() == null) return;
    	
    	for (int i = 0; i < getFormattings().size(); i++) {
    		Formatting f = (Formatting) getFormattings().get(i);
    		if (f != null)
    			if ((caret > f.offset) && 
    				(caret < (f.offset + f.length))
					) {
    				f.length++;
    			}
    	}
    }
    
    /**
     * Applies an AttributeSet to to the right portion of the SWT peer
     * 
     * @param offset Where to start the formatting
     * @param length The number of chars it applies to
     * @param s The AttributeSet to apply
     */
    public void setFormattingAttributes(final int offset, final int length, final AttributeSet s) {
    
    	// Bail if we have no peer
    	if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return;
    	
    	// Nothing to do if there are no attributes
    	if (s == null) return;
    	
    	// Don't bother if we have no formattings
    	if (getFormattings() == null) return;
    	
    	SwingUtilities.invokeSync(new Runnable() {
    		public void run() {
    		
    			StyleRange range = new StyleRange();
    			
    			range.start = offset;
    			range.length = length;
    			
    			// If the offset is outside the document, don't apply
    			if (offset > doc.getLength()) return;
    			
    			// If the length puts us outside the document, don't apply
    			if (offset + length > doc.getLength()) return;
    			
    			// Colours
    			Color bg = (Color) s.getAttribute(StyleConstants.ColorConstants.Background);
    			if (bg != null) {
    				range.background = bg.getSWTColor();
    			}
    			Color fg = (Color) s.getAttribute(StyleConstants.ColorConstants.Foreground);
    			if (fg != null) {
    				range.foreground = fg.getSWTColor();
    			}
    			
    			// Font
    			int style = 0;
    			Boolean bold = (Boolean) s.getAttribute(StyleConstants.FontConstants.Bold);
    			Boolean italic = (Boolean) s.getAttribute(StyleConstants.FontConstants.Italic);
    			boolean b = false, i = false;
    			if (bold != null)
    				b = bold.booleanValue();
    			if (italic != null)
    				i = italic.booleanValue();
    			if (b) style += SWT.BOLD;
    			if (i) style += SWT.ITALIC;
    			range.fontStyle = style;
    			
    			try {
    				ppeer.setStyleRange(range);
    			}
    			catch (Exception e) {
    				if (getFormattings() == null) return;
    				System.out.println("Failed applying style to: " + offset + ":" + (offset + length) );
    				System.out.println("Current formattings in effect:");
    				for (int z = 0; z < getFormattings().size(); z++) {
    					Formatting f = (Formatting) getFormattings().get(z);
    					System.out.println(f.toString());
    				}
    			}
    			
    		}
    	});
    }
    

    /**
     * Loops through the set of formattings applied and re-applies them
     */
    public void applyAllFormattings() {
    	clearPeerFormatting();
    	for (int i = 0; i < getFormattings().size(); i++) {
    		Formatting f = (Formatting) getFormattings().get(i);
    		setFormattingAttributes(f.offset, f.length, f.attrs);
    	}
    }
    /**
     * Removes all formattings from the peer
     */
    protected void clearPeerFormatting() {
    	SwingUtilities.invokeSync(new Runnable() {
    		public void run() {
    			if (SwingWTUtils.isSWTControlAvailable(ppeer))
    				ppeer.setStyleRanges(new StyleRange[0]);
    		}
    	});
    }
    
    /**
     * This is hackery - I store a vector of formattings inside the
     * DefaultStyledDocument. This is because there is no way of 
     * conveying the style inside the DocumentEvent and there doesn't
     * seem to be any way of reading it from the Document object.
     * 
     * @return The list of formattings. If the document isn't an
     * instance of DefaultStyledDocument, it returns null and the
     * calling routines should bail. This means you can't use
     * formatting with anything that isn't DefaultStyledDocument.
     * Ideas anyone?
     */
    public Vector getFormattings() {
    	if (doc instanceof DefaultStyledDocument)
    		return ((DefaultStyledDocument) doc).getFormattings();
    	else
    		return null;
    }  
    
    /** When document is updated */
	public void changedUpdate(DocumentEvent e) {
		updatePeer();
	}
	
	/** When document is inserted */
	public void insertUpdate(DocumentEvent e) {
		// If it's only 1 char, we don't need to update the peer - just
        // reapply the formatting since it's from a user keystroke and will
        // already be visible
        if (e.getLength() > 1)
        	updatePeer();
        else
        	applyAllFormattings();
	}

	/** When document is removed */
	public void removeUpdate(DocumentEvent e) {
		// If it's only 1 char, we don't need to update the peer - just
        // reapply the formatting since it's from a user keystroke and will
        // already be visible
        if (e.getLength() > 1)
        	updatePeer();
        else
        	applyAllFormattings();
	}
	
    protected void updatePeer() {
    	
    	// Update the peer
    	updatePeerTextFromDocument();
        
        // Reapply formattings
    	applyAllFormattings();
    }
    
    /** Cannot work - applies only to HTML */
    public void scrollToReference(String reference) {
    }    
    
}
