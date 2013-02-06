/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import swingwtx.swing.text.*;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class JPasswordField extends swingwtx.swing.JTextField {

    /** The peer */
    protected Text ppeer = null;
    /** Cache of the text to be loaded into the peer */
    protected String pText = "";
    /** The char to use instead of showing what's typed
     *  (defaults to a *)
     */
    protected char pEchoChar = '*';
    
    /** Return value for thread-safe property accessors */
    private String retVal = "";
    /** Return value for char array */
    private char[] retChars = null;

    /** Creates a new JPasswordField */
    public JPasswordField() {
        this(null, "", -1);
    }
    /** Creates a new JPasswordField 
     *  @param text The text to put in the field 
     */
    public JPasswordField(String text) {
        this(null, text, 0);
    }
    /** Creates a new JPasswordField
     *  @param columns The number of chars allowed
     */
    public JPasswordField(int columns) {
        this(null, "", columns);
    }
    /** Creates a new JPasswordField
     *  @param text The text for the field
     *  @param columns The number of chars allowed
     */
    public JPasswordField(String text, int columns) {
        this(null, text, columns);
    }
    /** Creates a new JPasswordField
     *  @param document The Document model to supply data to the component
     *  @param text The text to put in the field
     *  @param columns The number of chars allowed
     */
    public JPasswordField(Document document, String text, int columns) {
        super(); 
        if (document != null) setDocument(document);
        if (text != null) { setText(text); view.updateModelFromComponent(getText()); }
    }
    
    /** @return The char used to output instead of the typed character */
    public char getEchoChar() { if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return pEchoChar; else return ppeer.getEchoChar(); }
    /** @param c The char to output instead of what's typed */
    public void setEchoChar(char c) { if (!SwingWTUtils.isSWTControlAvailable(ppeer)) pEchoChar = c; else ppeer.setEchoChar(c); }
    /** @return true if the echo char is set. This class defaults * as the echo char, so it's always set */
    public boolean isEchoCharSet() { return true; }
    /** @return the text of the field */
    public String getText() { 
        retVal = ""; 
        SwingUtilities.invokeSync(new Runnable() { 
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) 
                    retVal = ppeer.getText(); 
                else retVal = pText; 
            }
        }); 
        return retVal; 
    }
    
    /** @return The text of the field as a char array */
    public char[] getPassword() { 
        retChars = null; 
        SwingUtilities.invokeSync(new Runnable() { 
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) 
                    retChars = ppeer.getText().toCharArray(); 
                else retChars = pText.toCharArray(); 
            }
        }); 
        return retChars; 
    }
    
    /** @param text The new text for the field */
    public void setText(final String text) { 
        pText = text; 
        SwingUtilities.invokeSync(new Runnable() { 
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) 
                    ppeer.setText(text);}
        }); 
    }
    
    /** Overriden to calculate non-realised
     *  preferred size.
     */
    protected swingwt.awt.Dimension calculatePreferredSize() {
        swingwt.awt.Dimension size = new swingwt.awt.Dimension( 
            SwingWTUtils.getRenderStringWidth(pText), 
            SwingWTUtils.getRenderStringHeight(pText) + 4);
        setSize(size);
        return size;
    }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new Text(parent.getComposite(), SWT.BORDER | SWT.SINGLE);
        ppeer.setEchoChar(pEchoChar);
        ppeer.setText(pText);
        peer = ppeer;
        this.parent = parent;
    }
    
}
