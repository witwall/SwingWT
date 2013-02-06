/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net



*/
package swingwtx.swing.editorpanes;

import swingwt.awt.Color;
import swingwtx.swing.text.*;
import swingwtx.swing.*;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.*;

import java.net.*;
import java.io.*;

/**
 * An implementation of an editor pane that behaves like a browser,
 * but is implemented purely with SWT widgets. This is so that
 * on platforms without a browser, they can still see something.
 *
 * At the moment, the HTML is just dumped to a text window - hopefully
 * someone will flesh this out, as I don't really have time at the minute.
 *
 * @author  Robin Rawson-Tetley
 */
public class FakeBrowserEditorPane extends JTextComponent implements EditorPane {
    
    protected Document doc = null;
    protected Color selectionColor = Color.BLACK;
    protected StyledText ppeer = null;
    protected Object retval = null;
    
    public void addHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) {
    }    
    
    public swingwt.awt.Dimension calculatePreferredSize() {
        // Default 300x200
        swingwt.awt.Dimension size = new swingwt.awt.Dimension(300, 200);
        setSize(size);
        return size;
    }    
    
    public int getCaretPosition() {
        return super.getCaretPosition();
    }
    
    public String getContentType() {
        return "text/plain";
    }
    
    public swingwtx.swing.text.Document getDocument() {
        return doc;
    }
    
    public swingwtx.swing.text.EditorKit getEditorKit() {
        return new StyledEditorKit();
    }
    
    public swingwtx.swing.JComponent getJComponent() {
        return this;
    }
    
    public swingwt.awt.Color getSelectionColor() {
        return selectionColor;
    }
    
    public int getSelectionEnd() {
        return super.getSelectionEnd();
    }
    
    public int getSelectionStart() {
        return super.getSelectionStart();
    }
    
    public Control getSWTPeer() {
        return ppeer;
    }    
    
    public String getText() {
        if (!SwingWTUtils.isSWTControlAvailable(ppeer)) return pText;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                retval = ppeer.getText();
            }
        });
        return retval.toString();
    }
    
    public boolean isEditable() {
        return super.isEditable();
    }
    
    public void removeHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) {
    }
    
    public void setCaretPosition(int pos) {
        super.setCaretPosition(pos);
    }
    
    public void setContentType(String contentType) {
    }
    
    public void setDocument(swingwtx.swing.text.Document newdoc) {
        doc = newdoc;
    }
    
    public void setEditable(boolean b) {
        super.setEditable(b);
    }
    
    public void setEditorKit(swingwtx.swing.text.EditorKit k) {
    }
    
    public void setPage(java.net.URL url) throws java.io.IOException {
        setText(getPageText(url));
    }
    
    public void setPage(String url) throws java.io.IOException {
        setPage(new URL(url));
    }
    
    public void setSelectionColor(swingwt.awt.Color color) {
        selectionColor = color;
    }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        
        ppeer = new StyledText(parent.getComposite(), SWT.V_SCROLL);
        peer = ppeer;

        // Cached properties
        if (pText != null && !pText.equals("")) ppeer.setText(pText);
        doc = null;
        
    }
    
    public void setText(String text) {
        pText = text;
        SwingUtilities.invokeAsync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))  {
                    ppeer.setText(pText);
                }
            }
        });
    }
    
    /**
     * Reads the HTML from a given URL. This is handy if the dev calls getText()
     * on a browser component.
     */
    private String getPageText(URL url) {
        try {
            String pageText = "";
            Reader in = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buf = new StringBuffer();
            int ch;
            while ((ch = in.read()) > -1) {
                buf.append((char)ch);
            } 
            in.close();
            pageText = buf.toString();
            return pageText;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    /** Cannot work - applies only to HTML */
    public void scrollToReference(String reference) {
    }    

    
}
