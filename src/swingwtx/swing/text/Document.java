/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/
package swingwtx.swing.text;

import swingwtx.swing.event.*;

public interface Document {

    public static final String StreamDescriptionProperty = "stream";
    public static final String TitleProperty = "title";
    
    public int getLength();
    public void addDocumentListener(DocumentListener listener);
    public void removeDocumentListener(DocumentListener listener);
    public void addUndoableEditListener(UndoableEditListener listener);
    public void removeUndoableEditListener(UndoableEditListener listener);
    public Object getProperty(Object key);
    public void putProperty(Object key, Object value);
    public void remove(int offs, int len) throws BadLocationException;
    public void insertString(int offset, String str, AttributeSet a) throws BadLocationException;
    public String getText(int offset, int length) throws BadLocationException;
    public void getText(int offset, int length, Segment txt) throws BadLocationException;
    public Position getStartPosition();
    public Position getEndPosition();
    public Position createPosition(int offs) throws BadLocationException;
    public Element[] getRootElements();
    public Element getDefaultRootElement();
    public void render(Runnable r);
}
