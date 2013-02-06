/*
   SwingWT
   Copyright(c)2003-2008 Robin Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.text;

import swingwtx.swing.undo.*;

/**
 * This class is used to represent document content
 * with a StringBuffer. Hopefully it should be efficient enough.
 *
 * @author  Robin Rawson-Tetley
 */
public class StringContent implements AbstractDocument.Content {
    
    /** The content */
    private StringBuffer s = null;
    
    public StringContent() {
        s = new StringBuffer();    
    }
    
    public StringContent(int initialSize) {
        s = new StringBuffer(initialSize);    
    }

    /** Creates an anonymous class implementing <code>Position</code> for the
     *  offset given.
     */
    public Position createPosition(final int offset) throws BadLocationException {
        return new Position() {
            public int getOffset() { return offset; }
        };
    }

    /** Returns the content length */
    public int length() { return s.length(); }

    /** 
     * Inserts a string into the content at the specified position.
     * @throws BadLocationException if the position is invalid
     * FIXME: UNDO needs implementing 
     */
    public UndoableEdit insertString(int where, String str) throws BadLocationException {
        if (where < 0 || where > s.length() + 1)
            throw new BadLocationException("Out of bounds", where);
        if (where > s.length())
        	s.append(str);
        else
        	s.insert(where, str);
	return null;
    }

    /** 
     * Removes nitems chars from the content at the specified position.
     * @throws BadLocationException if the position is invalid
     * FIXME: UNDO needs implementing 
     */
    public UndoableEdit remove(int where, int nitems) throws BadLocationException {
        if (where < 0 || where > s.length() || where + nitems > s.length())
            throw new BadLocationException("Out of bounds", where);
        s.replace(where, where + nitems, "");
	return null;
    }

    /** 
     * Retrieves a string from the content
     * @param where The offset
     * @param len The length of the string
     * @return The substring specified
     * @throws BadLocationException if the position is invalid
     */
    public String getString(int where, int len) throws BadLocationException {
        if (where < 0 || where > s.length() || where + len > s.length())
            throw new BadLocationException("Out of bounds", where);
        return s.toString().substring(where, where + len);
    }

    /** 
     * Assigns the specified substring to the passed Segment
     * @param where The offset
     * @param len The length
     * @param txt The segment object to put the substring in
     */
    public void getChars(int where, int len, Segment txt) throws BadLocationException {
        txt.array = getString(where, len).toCharArray();
    }
    
    public String toString() {
    	return s.toString();
    }
        
}
