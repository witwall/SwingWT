/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import swingwt.awt.Color;
import swingwtx.swing.text.*;
import swingwtx.swing.editorpanes.*;

import java.net.*;
import java.io.*;

public class JEditorPane extends swingwtx.swing.text.JTextComponent {

    /** The EditorPane implementation we are using */
    protected EditorPane editorPaneImpl = null;
    
    public JEditorPane() { 
    	loadBrowserEditorPane(); 
    }
    
    public JEditorPane(String url) throws IOException { 
        loadBrowserEditorPane(); 
        setPage(url);
    }
    
    public JEditorPane(String type, String text) { 
        if (type.equalsIgnoreCase("text/html"))
            loadBrowserEditorPane();
        else
            loadTextEditorPane();
        setText(text);
    }
    
    public JEditorPane(URL url)  throws IOException { 
        loadBrowserEditorPane();
        setPage(url); 
    }
    
    protected void loadTextEditorPane() {
        loadEditor("swingwtx.swing.editorpanes.TextEditorPane");
    }
    protected void loadBrowserEditorPane() {
        if (!loadEditor("swingwtx.swing.editorpanes.PlatformBrowserEditorPane"))
            loadEditor("swingwtx.swing.editorpanes.FakeBrowserEditorPane");
    }
    /**
     * Uses reflection to load an implementation of the class. Returns false if it
     * failed for some reason. This allows us to remove implementation classes
     * (for example, to get rid of the dependency on Mozilla for GTK2 users/builds
     */
    private boolean loadEditor(String clazz) {
        try {
	    // Don't replace an existing editor
	    if (editorPaneImpl != null) return true;
            Class c = Class.forName(clazz);
            editorPaneImpl = (EditorPane) c.getConstructors()[0].newInstance(null);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    /** Overriden to calculate non-realised
     *  preferred size.
     */
    protected swingwt.awt.Dimension calculatePreferredSize() {
        // Default 300x200
        swingwt.awt.Dimension size = new swingwt.awt.Dimension(300, 200);
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
        editorPaneImpl.setSwingWTParent(parent);
        peer = editorPaneImpl.getSWTPeer();
    }

    public String getText() { return editorPaneImpl.getText(); }
    public void setText(String text) { editorPaneImpl.setText(text); }
    public String getContentType() { return editorPaneImpl.getContentType(); }
    public void setContentType(String contentType) { editorPaneImpl.setContentType(contentType); }
    public boolean isEditable() { return editorPaneImpl.isEditable(); }
    public void setEditable(boolean b) { editorPaneImpl.setEditable(b); }
    public void setDocument(Document newdoc) { editorPaneImpl.setDocument(newdoc); }
    public Document getDocument() { return editorPaneImpl.getDocument(); }
    public void setPage(URL url) throws IOException {
        // If we haven't created a peer yet, force us to use a browser, since
        // setPage is really aimed at HTML.
        if (!SwingWTUtils.isSWTControlAvailable(editorPaneImpl.getSWTPeer())) {
            loadBrowserEditorPane();
        }
        editorPaneImpl.setPage(url); 
    }
    public void setPage(String url) throws IOException { setPage(new URL(url)); }
    public void addHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) { editorPaneImpl.addHyperlinkListener(l); }
    public void removeHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) { editorPaneImpl.removeHyperlinkListener(l); }
    public void setCaretPosition(int pos) { editorPaneImpl.setCaretPosition(pos); }
    public int getCaretPosition() { return editorPaneImpl.getCaretPosition(); }
    public int getSelectionStart() { return editorPaneImpl.getSelectionStart(); }
    public int getSelectionEnd() { return editorPaneImpl.getSelectionEnd(); }
    public EditorKit getEditorKit() { return editorPaneImpl.getEditorKit(); }
    public void setEditorKit(EditorKit k) { editorPaneImpl.setEditorKit(k); }
    public void setSelectionColor(Color color) { editorPaneImpl.setSelectionColor(color); }    
    public Color getSelectionColor(){ return editorPaneImpl.getSelectionColor(); }
    public void scrollToReference(String reference) { editorPaneImpl.scrollToReference(reference); }
}

