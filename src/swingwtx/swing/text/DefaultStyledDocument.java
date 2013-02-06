package swingwtx.swing.text;

import java.util.Hashtable;
import java.util.Vector;

import swingwt.awt.Color;
import swingwt.awt.Font;
import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingWTUtils;
import swingwtx.swing.editorpanes.TextEditorPane;
import swingwtx.swing.text.Formatting;
import swingwtx.swing.event.DocumentEvent;

/**
 * The default styled document implementation. Note that it needs knowledge of the
 * TextEditorPane class it's being used in.
 * 
 * @author Robin Rawson-Tetley
 */
public class DefaultStyledDocument extends AbstractDocument implements StyledDocument {
    
	protected Hashtable styles = new Hashtable();
	
	/** 
     *  The list of formattings applied to the peer. We store this as text and
     *  formattings are tracked independently. It means we can update the text
     *  via a document and then reapply all formattings with a single call.
     */
    protected Vector formattings = new Vector();
    
    /** Thread safe return value */
    protected Object retval = null;
	
	public DefaultStyledDocument() {
		content = new GapContent();
	}
	
	public Style addStyle(String nm, Style parent) {
		styles.put(nm, parent);
		return parent;
	}
	
	public Color getBackground(AttributeSet attr) {
		return (Color) attr.getAttribute(StyleConstants.ColorConstants.Background);
	}
	
	public Element getCharacterElement(int pos) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Font getFont(final AttributeSet attr) {
		SwingUtilities.invokeSync(new Runnable() {
    		public void run() {
    			
    			String family = (String) attr.getAttribute(StyleConstants.FontConstants.Family);
    			Integer size = ((Integer) attr.getAttribute(StyleConstants.FontConstants.Size));
    			int mods = 0;
    			Boolean bold = ((Boolean) attr.getAttribute(StyleConstants.FontConstants.Bold));
    			Boolean italic = ((Boolean) attr.getAttribute(StyleConstants.FontConstants.Italic));
    			boolean b = false;
    			boolean i = false;
    			if (bold != null)
    				b = bold.booleanValue();
    			if (italic != null)
    				i = italic.booleanValue();
    			if (b) mods += org.eclipse.swt.SWT.BOLD;
    			if (i) mods += org.eclipse.swt.SWT.ITALIC;
    			
    			// Create a new font from the SWT system font and apply our changes
    			org.eclipse.swt.graphics.Font f = SwingWTUtils.getDisplay().getSystemFont();
    			org.eclipse.swt.graphics.FontData fd = f.getFontData()[0];
    			
    			if (family != null)
    				fd.setName(family);
    			if (size != null)
    				fd.setHeight(size.intValue());
    			fd.setStyle(mods);
    			
    			// Generate a SwingWT font
    			retval = new swingwt.awt.Font(new org.eclipse.swt.graphics.Font(SwingWTUtils.getDisplay(), fd));
    		}
    	});
    	return (Font) retval;
	}
	
	public Color getForeground(AttributeSet attr) {
		return (Color) attr.getAttribute(StyleConstants.ColorConstants.Background);
	}
	
	public Style getLogicalStyle(int p) {
		return null;
	}
	
	public Style getStyle(String nm) {
		return (Style) styles.get(nm);
	}
	
	public void removeStyle(String nm) {
		styles.remove(nm);
	}
	
	public void setCharacterAttributes(int offset, int length, AttributeSet s, boolean replace) {
		addFormatting(offset, length, s);
		fireDocumentEvent(new DefaultDocumentEvent(offset, length, DocumentEvent.EventType.CHANGE));
	}

	public void setLogicalStyle(int pos, Style s) {
		// TODO Auto-generated method stub
	}

	public void setParagraphAttributes(int offset, int length, AttributeSet s, boolean replace) {
		addFormatting(offset, length, s);
		fireDocumentEvent(new DefaultDocumentEvent(offset, length, DocumentEvent.EventType.CHANGE));
	}
	
    public Element getDefaultRootElement() {
        return null;
    }
    
    public Element getParagraphElement(int pos) {
        return null;
    }
    
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {

        // Add the formatting info to a list
        addFormatting(offset, str.length(), a);
        
    	// Add it to the content
        super.insertString(offset, str, a);

    }
    
    public void remove(int offset, int length) throws BadLocationException {
    	super.remove(offset, length);
    }
    
    /** 
     * Adds a new formatting to the list
     */
    public void addFormatting(int offset, int length, AttributeSet attrs) {
    	if (attrs == null) return; // No point doing anything if there's no attributes
    	if (offset < 0 || length < 0) return; // Don't allow invalid ranges
    	Formatting f = new Formatting();
    	f.offset = offset;
    	f.length = length;
    	f.attrs = attrs;
    	formattings.add(f);
    }
    
    public Vector getFormattings() { return formattings; }

    
}
