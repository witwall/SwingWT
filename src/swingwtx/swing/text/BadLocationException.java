/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwtx.swing.text;

public class BadLocationException extends Exception {
    public BadLocationException(String s, int offs) {
        super(s);
        this.offs = offs;
    }
    public int offsetRequested() {
        return offs;
    }
    private int offs;
}

