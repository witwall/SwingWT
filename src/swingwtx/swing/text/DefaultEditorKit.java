/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import swingwt.awt.event.*;
import swingwtx.swing.*;

public class DefaultEditorKit extends EditorKit {

    public static final String forwardAction = "caret-forward";
    public static final String backwardAction = "caret-backward";
    public static final String beginAction = "caret-begin";
    public static final String endAction = "caret-end";
    public static final String insertTabAction = "insert-tab";
    
    public Document createDefaultDocument() {
        return null;
    }    
    
    public swingwtx.swing.Action[] getActions() {
        return null;
    }
    
    public String getContentType() {
        return "text/plain";
    }
    
    public void read(java.io.InputStream in, Document doc, int pos) throws java.io.IOException, BadLocationException {
    }
    
    public void read(java.io.Reader in, Document doc, int pos) throws java.io.IOException, BadLocationException {
    }
    
    public void write(java.io.Writer out, Document doc, int pos, int len) throws java.io.IOException, BadLocationException {
    }
    
    public void write(java.io.OutputStream out, Document doc, int pos, int len) throws java.io.IOException, BadLocationException {
    }
    public static class InsertTabAction extends TextAction {
        public InsertTabAction() 
        {
            // SwingWT issure
        	super(insertTabAction);
        }
        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getTextComponent(e);
            if (target != null) {
		if ((! target.isEditable()) || (! target.isEnabled())) {
		    UIManager.getLookAndFeel().provideErrorFeedback(target);
		    return;
		}
                target.replaceSelection("\t");
            }
        }
    }
    
}
