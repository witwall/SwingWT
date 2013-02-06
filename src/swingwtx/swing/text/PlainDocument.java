/*
   SwingWT
   Copyright(c)2003-2008 Robin Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.text;

/**
 * Plain Document implementation (default for JTextField, JPasswordField and
 * JTextArea)
 *
 * @author  Robin Rawson-Tetley
 */
public class PlainDocument extends AbstractDocument {
    
    public PlainDocument() { content = new GapContent(); }
    public PlainDocument(AbstractDocument.Content c) { content = c; }

    public Element getDefaultRootElement() {
        return null;
    }
    
    public Element getParagraphElement(int pos) {
        return null;
    }
    
}
