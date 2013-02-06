/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import swingwtx.accessibility.AccessibleContext;
import swingwtx.swing.JTextArea;
import swingwtx.swing.text.BadLocationException;

public class TextArea extends TextComponent {
    
    public final static int SCROLLBARS_NONE = 0;
    public final static int SCROLLBARS_VERTICAL_ONLY = 1;
    public final static int SCROLLBARS_HORIZONTAL_ONLY = 2;
    public final static int SCROLLBARS_BOTH = 3;
    
    public TextArea() { this(""); }
    public TextArea(String text) { this(text, 0, 0); }
    public TextArea(int rows, int columns) { this("", rows, columns); }
    public TextArea(String text, int rows, int columns) { this(text, rows, columns, 0); }
    public TextArea(String text, int rows, int columns, int scrollbars) {
        swingPeer = new JTextArea(text, rows, columns);
        setScrollbarVisibility(scrollbars);
    }
    
    private final JTextArea getSwingPeer() { return (JTextArea) swingPeer; }
    
    private void setScrollbarVisibility(int scrollbars) {
        boolean horizontal = (scrollbars | SCROLLBARS_HORIZONTAL_ONLY) != 0;
        boolean vertical = (scrollbars | SCROLLBARS_VERTICAL_ONLY) != 0;
        getSwingPeer().setHorizontalScrollPane(horizontal);
        getSwingPeer().setVerticalScrollPane(vertical);
    }
    
    public int getScrollbarVisibility() {
        int scrollbar = 0;
        scrollbar |= getSwingPeer().getHorizontalScrollPane() ? SCROLLBARS_HORIZONTAL_ONLY : 0;
        scrollbar |= getSwingPeer().getVerticalScrollPane() ? SCROLLBARS_VERTICAL_ONLY : 0;
        return scrollbar;
    }
    
    public void setScrollPane(swingwtx.swing.JScrollPane container) { getSwingPeer().setScrollPane(container); }
    public AccessibleContext getAccessibleContext() { return getSwingPeer().getAccessibleContext(); }
    public int getColumns() { return getSwingPeer().getColumns(); }
    public int getRows() { return getSwingPeer().getRows(); }

    // TODO: Once JTextArea has row/column sizing methods, fix this to use them
    public Dimension getMinimumSize(int rows, int columns) { return getSwingPeer().getMinimumSize(); }
    public Dimension getPreferredSize(int rows, int columns) { return getSwingPeer().getPreferredSize(); }
    
    public void append(String str) { appendText(str); }
    public void appendText(String str) {
        String text = getText();
        String newStr = getText() + str;
        setText(newStr);
        setCaretPosition(newStr.length()+1);
    }
    
    public void insert(String str, int pos) { insertText(str, pos); }
    public void insertText(String str, int pos) {
        try {
            getSwingPeer().getDocument().insertString(pos, str, null);
        }
        catch (BadLocationException e) {
            e.printStackTrace();
        }
	}
    
    public void replaceRange(String str, int start, int end) { replaceText(str, start, end); }
    public void replaceText(String str, int start, int end) {
        String text = getText();
        String newStr = "";
        if (start != 0) newStr = text.substring(0,start-1);
        newStr += str;
        newStr += text.subSequence(start,end);
        setText(newStr);
        
        int newPos = newStr.length() - text.length() + end;
        setCaretPosition(newPos);
    }
    
    public void setColumns(int columns) { getSwingPeer().setColumns(columns); }
    public void setRows(int rows) { getSwingPeer().setRows(rows); }
}
