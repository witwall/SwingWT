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
import org.eclipse.swt.browser.*;
import org.eclipse.swt.*;

import java.util.*;
import java.net.*;
import java.io.*;

/**
 * Implementation of a JEditorPane based on a platform component.
 * 
 * Under GTK2, the platform component is Mozilla - you can safely
 * remove this class from the build if you want to remove the
 * dependency on Mozilla.
 *
 * @author Robin Rawson-Tetley
 */
public class PlatformBrowserEditorPane extends JTextComponent implements EditorPane {
    
    protected Document doc = new PlatformBrowserEditorPane.EditorPaneHTMLDocument();
    protected Vector hyperListeners = new Vector();
    protected Color selectionColor = Color.BLACK;
    protected URL pUrl = null;
    protected Browser ppeer = null;
    
    public void addHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) {
        hyperListeners.add(l);
    }
    
    public swingwt.awt.Dimension calculatePreferredSize() {
        // Default 300x200
        swingwt.awt.Dimension size = new swingwt.awt.Dimension(300, 200);
        setSize(size);
        return size;
    }    
    
    public int getCaretPosition() {
        return 0;
    }
    
    public String getContentType() {
        return "text/html";
    }
    
    public swingwtx.swing.text.Document getDocument() {
        return doc;
    }
    
    public swingwtx.swing.text.EditorKit getEditorKit() {
        return new HTMLEditorKit();
    }
    
    public swingwtx.swing.JComponent getJComponent() {
        return this;
    }
    
    public swingwt.awt.Color getSelectionColor() {
        return selectionColor;
    }
    
    public int getSelectionEnd() {
        return 0;
    }
    
    public int getSelectionStart() {
        return 0;
    }
    
    public String getText() {
        return getPageText(pUrl);
    }
    
    public boolean isEditable() {
        return false;
    }
    
    public void removeHyperlinkListener(swingwtx.swing.event.HyperlinkListener l) {
        hyperListeners.remove(l);
    }
    
    public void setCaretPosition(int pos) {
    }
    
    public void setContentType(String contentType) {
    }
    
    public void setDocument(swingwtx.swing.text.Document newdoc) {
        throw new RuntimeException("PlatformBrowserEditorPane does not support setDocument");
    }
    
    public void setEditable(boolean b) {
    }
    
    public void setEditorKit(swingwtx.swing.text.EditorKit k) {
    }
    
    public void setPage(java.net.URL url) throws java.io.IOException {
        // Do nothing if the URL is the same
        if (pUrl != null && pUrl.equals(url)) return;
        pUrl = url;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.setUrl(pUrl.toString());
                }
            });
        }
    }
    
    public void setPage(String url) throws java.io.IOException {
        setPage(new URL(url));
    }
    
    public void setSelectionColor(swingwt.awt.Color color) {
        selectionColor = color;
    }
    
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        try {
            
            ppeer = new Browser(parent.getComposite(), SWT.NONE);
            peer = ppeer;

            // Cached properties
            if (pText != null && !pText.equals("")) setText(pText);
            if (pUrl != null && !pUrl.toString().equals("")) ppeer.setUrl(pUrl.toString());

            registerHyperLinkEvents();
            this.parent = parent;
        }
        catch (SWTError e) {
            if (!SwingWTUtils.isMacOSX() && !SwingWTUtils.isWindows()) {
                System.out.println("SwingWT JEditorPane/SWT Browser requires at least Mozilla 1.4 to operate.");
                System.out.println("If you have it installed, then you need to set the following");
                System.out.println("environment variables before starting this application:\n");
                System.out.println("export MOZILLA_FIVE_HOME=/usr/lib/mozilla");
                System.out.println("export LD_LIBRARY_PATH=/usr/lib/mozilla\n");
                System.out.println("Where /usr/lib/mozilla is the path to your mozilla installation.\n");
                System.out.println("Error was: " + e.getMessage());
            }
            else
                e.printStackTrace();
        }
    }
    
    public void setText(String text) {
        pText = text;
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    ppeer.setText(pText);
                }
            });
        }
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
    
    /**
     * Maps Swing HyperLinkListener events to SWT
     * equivalents for browsers.
     */
    protected void registerHyperLinkEvents() {
       ppeer.addLocationListener(new LocationListener() {
            public void changed(LocationEvent event) {
                processHyperlinkEvent(event.location, swingwtx.swing.event.HyperlinkEvent.EventType.ACTIVATED);
            }
            public void changing(LocationEvent event) {}
        }); 
        ppeer.addStatusTextListener(new StatusTextListener() {
            public void changed(StatusTextEvent event) {
                processHyperlinkEvent(event.text, swingwtx.swing.event.HyperlinkEvent.EventType.ENTERED);
            }
        });
    }
    
    protected void processHyperlinkEvent(String location, swingwtx.swing.event.HyperlinkEvent.EventType eventType) {
        // Create a hyperlink event from the SWT location event
        URL url = null;
        try { url = new URL(location); } catch (Exception e) {}
        swingwtx.swing.event.HyperlinkEvent e = new swingwtx.swing.event.HyperlinkEvent(this, eventType, url, location);
        Iterator i = hyperListeners.iterator();
        while (i.hasNext()) {
            ((swingwtx.swing.event.HyperlinkListener) i.next()).hyperlinkUpdate(e);
        }
    }
    
    public Control getSWTPeer() {
        return ppeer;
    }    
    
    /** Cannot work - not workable through platform browser */
    public void scrollToReference(String reference) {
    }
    
    /**
     * Basic class to handle document positions in an HTML doc
     * @author Robin Rawson-Tetley
     */
    private class EditorPaneHTMLDocPosition implements Position {
        private int offset = 0;
        public EditorPaneHTMLDocPosition(int pos) { offset = pos; }
        public int getOffset() { return offset; }
    }
    
    /**
     * HTML representative document that allows modification
     * integrated by Rob.
     *
     * NB: Marco, you don't need to check whether there is a peer to decide
     *     whether or not to modify text - by changing pText, that will be
     *     loaded into the peer once it is created anyway, so just call
     *     handleText() and forget about it.
     *
     * @author Marco Hennings
     */
    private class EditorPaneHTMLDocument implements Document {
        
        String docText="";
        
        public Position getEndPosition() {
            return new EditorPaneHTMLDocPosition(docText.length());
        }
        public Position getStartPosition() {
            return new EditorPaneHTMLDocPosition(0);
        }
        
        /* (non-Javadoc)
         * @see swingwtx.swing.text.Document#remove(int, int)
         */
        public void remove(int i, int j) {
            StringBuffer buffer = new StringBuffer(docText);
            buffer.replace(i, j, "");
            docText = buffer.toString();
            convertText();
        }

        private void convertText() {
            pText = "<HTML><BODY>"+docText+"<A name=\"1\"/></BODY></HTML>";
            setText(pText);
        }

        /* (non-Javadoc)
         * @see swingwtx.swing.text.Document#insertString(int, java.lang.String, swingwtx.swing.text.AttributeSet)
         */
        public void insertString(int i, String string, AttributeSet as) {
            
            StringBuffer buffer = new StringBuffer(docText);
            StringBuffer nb = new StringBuffer(string);
            Color foreground=(Color) as.getAttribute(StyleConstants.ColorConstants.Foreground);
            int index = SwingWTUtils.getStringBufferIndexOf(nb, ">");
            while (index >-1) {
                nb.replace(index, index+1, "&gt;");
                index = SwingWTUtils.getStringBufferIndexOf(nb, ">");
            }
            index = SwingWTUtils.getStringBufferIndexOf(nb, "<");
            while (index >-1){
                nb.replace(index, index+1, "&lt;");
                SwingWTUtils.getStringBufferIndexOf(nb, "<");
            }

            SwingWTUtils.getStringBufferIndexOf(nb, "http://");
            while (index > -1) {
                int end = Math.min(SwingWTUtils.getStringBufferIndexOf(nb, " ", index) ,SwingWTUtils.getStringBufferIndexOf(nb, "\n", index) );
                if(end==-1) 
                    end =nb.length()-1;
                String url=nb.substring(index, end);
                nb.replace(index, end, "<A HREF=\""+url+"\" " +
                        //"target=\"_blank\"" +
                        ">"+url+"</A> ");
                index = SwingWTUtils.getStringBufferIndexOf(nb, "http://", end + (end-index) + 32 );
            }

            if (foreground!=null){
                nb.insert(0, "<font color=\""+encodeColor(foreground)+"\">");
                nb.append("</font> ");
            }
            index = SwingWTUtils.getStringBufferIndexOf(nb, "\n");
            while (index >-1){
                nb.replace(index, index+1, "<br> ");
                SwingWTUtils.getStringBufferIndexOf(nb, "\n");
            }

            if(i<=0)
                buffer.append(nb.toString());
            else
                buffer.insert(i,nb.toString());
            docText = buffer.toString();
            convertText();
            
        }

        private String encodeColor(Color foreground) {
            return "#"+Integer.toHexString(foreground.getRed())+Integer.toHexString(foreground.getGreen())+Integer.toHexString(foreground.getBlue());
        }
         
        public void addDocumentListener(swingwtx.swing.event.DocumentListener listener) { }
        public void addUndoableEditListener(swingwtx.swing.event.UndoableEditListener listener) { }        
        public Position createPosition(int offs) throws BadLocationException { return new EditorPaneHTMLDocPosition(offs); }
        public Element getDefaultRootElement() { return null; }
        public int getLength() {return docText.length(); }
        public Object getProperty(Object key) { return null; }
        public Element[] getRootElements() { return null; }
        public String getText(int offset, int length) throws BadLocationException { 
            String ret = null;
            try {
                ret = docText.substring(offset, offset+length); 
            }
            catch (StringIndexOutOfBoundsException e) { throw new BadLocationException("Invalid range", offset); }
            return ret;
        }
        public void getText(int offset, int length, Segment txt) throws BadLocationException { 
            String ret = null;
            try {
                ret = docText.substring(offset, offset+length); 
            }
            catch (StringIndexOutOfBoundsException e) { throw new BadLocationException("Invalid range", offset); }
	    txt.array = docText.toCharArray();
            txt.offset = offset;
            txt.count = length;
        }
        public void putProperty(Object key, Object value) { }
        public void removeDocumentListener(swingwtx.swing.event.DocumentListener listener) { }
        public void removeUndoableEditListener(swingwtx.swing.event.UndoableEditListener listener) { }
        public void render(Runnable r) { }
        
    }
}
