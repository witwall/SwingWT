/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.text;

import java.io.*;
import swingwtx.swing.*;

public abstract class EditorKit implements Cloneable, Serializable {

    public EditorKit() {
    }
    public Object clone() {
	Object o;
	try {
	    o = super.clone();
	} catch (CloneNotSupportedException cnse) {
	    o = null;
	}
	return o;
    }
    public void install(JEditorPane c) {
    }
    public void deinstall(JEditorPane c) {
    }
    public abstract String getContentType();
    //public abstract ViewFactory getViewFactory();
    public abstract Action[] getActions();
    //public abstract Caret createCaret();
    public abstract Document createDefaultDocument();
    public abstract void read(InputStream in, Document doc, int pos) 
	throws IOException, BadLocationException;
    public abstract void write(OutputStream out, Document doc, int pos, int len) 
	throws IOException, BadLocationException;
    public abstract void read(Reader in, Document doc, int pos) 
	throws IOException, BadLocationException;
    public abstract void write(Writer out, Document doc, int pos, int len) 
	throws IOException, BadLocationException;

}
