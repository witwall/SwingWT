/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net



*/

package swingwtx.swing.editorpanes;

import swingwtx.swing.text.*;
import swingwtx.swing.JComponent;
import swingwt.awt.Color;
import java.net.*;
import java.io.*;
import org.eclipse.swt.widgets.Control;

/**
 * Some distributions of Free OSes don't like the reliance on
 * Mozilla that use of the browser component brings (well, Debian
 * so far), so this interface allows us to abstract away the
 * implementation of the editorpane.
 *
 * This works well for us since we use a StyledText for editable
 * content anyway.
 *
 * @author  Robin Rawson-Tetley
 *
 */
public interface EditorPane {
  
    void setSwingWTParent(swingwt.awt.Container parent) throws Exception;
    swingwt.awt.Dimension calculatePreferredSize();
    String getText();
    void setText(String text);
    String getContentType();
    void setContentType(String contentType);
    boolean isEditable();
    void setEditable(boolean b);
    public void setDocument(Document newdoc);
    public Document getDocument();
    void setPage(URL url) throws IOException;
    void setPage(String url) throws IOException;
    void addHyperlinkListener(swingwtx.swing.event.HyperlinkListener l);
    void removeHyperlinkListener(swingwtx.swing.event.HyperlinkListener l);
    void setCaretPosition(int pos);
    int getCaretPosition();
    int getSelectionStart();
    int getSelectionEnd();
    EditorKit getEditorKit();
    void setEditorKit(EditorKit k);
    void setSelectionColor(Color color);
    Color getSelectionColor();
    void scrollToReference(String reference);
    Control getSWTPeer();
    JComponent getJComponent();
}
