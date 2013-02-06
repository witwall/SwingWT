/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import swingwt.awt.event.KeyEvent;
import swingwtx.swing.text.*;
import swingwt.awt.event.*;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class JTextField extends swingwtx.swing.text.JTextComponent implements SwingConstants {
    
    /** The default number of columns used if the app doesn't specify */
    private final static int DEFAULT_COLS = 8;
    
    protected int pCols = DEFAULT_COLS;
    
    /** Used for thread safe property accessors */
    private String retVal = "";
    
    public JTextField() { this(null, "", DEFAULT_COLS); }
    public JTextField(int columns) { this(null, "", columns); }
    public JTextField(String text) { this(null, text, DEFAULT_COLS); }
    public JTextField(String text, int columns) { this(null, text, columns); }
    public JTextField(Document doc, String text, int columns) {
        super();
        if (doc != null) setDocument(doc);
        pText = text; 
        pCols = columns; 
        if (pCols != 0) calculateFromCols();
        if (pText != null)
            if (!pText.equals(""))
                view.updateModelFromComponent(pText);
    }
    
    /**
     * Calculates the component's preferred size based on how
     * many columns the user chose.
     */
    protected void calculateFromCols() {
        setPreferredSize(new swingwt.awt.Dimension(
		(SwingWTUtils.getRenderStringWidth("W") * pCols),
		(SwingWTUtils.getRenderStringHeight("W") + 6)));
    }
    
    public int getColumns() { return pCols; }
    public void setColumns(int columns) { pCols = columns; if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.setTextLimit(pCols); }
    public void setEditable(boolean b) { pEditable = b; if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.setEditable(b); }
    public boolean isEditable() { return pEditable; }
    
    /** Overriden to calculate non-realised
     *  preferred size.
     */
    protected swingwt.awt.Dimension calculatePreferredSize() {
	swingwt.awt.Dimension size = null;    
    	size = new swingwt.awt.Dimension(
                            (SwingWTUtils.getRenderStringWidth("W") * pCols), 
                            (SwingWTUtils.getRenderStringHeight("W") + 6));
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
        ppeer = new Text(parent.getComposite(), SWT.BORDER | SWT.SINGLE);
        if (pText == null)
            ppeer.setText("");
        else
            ppeer.setText(pText);
        ppeer.setEditable(pEditable);
        if (pCols > 1 && pCols != DEFAULT_COLS) ppeer.setTextLimit(pCols);
        peer = ppeer;
        this.parent = parent;
        ppeer.addVerifyListener(new TextEventHandler());
        ppeer.addMouseListener(new MouseListener() {
            public void mouseDoubleClick(MouseEvent arg0) {}
            public void mouseDown(MouseEvent arg0) {
            }
            public void mouseUp(MouseEvent arg0) {
                // Generate keypress from middle-click for X paste
                if (arg0.button == 2)
                    processKeyEvent(new KeyEvent(JTextField.this, KeyEvent.KEY_PRESSED));
            }
             
        });
    }

     public void setHorizontalAlignment(int alignment) {}
     public ActionListener[] getActionListeners()
 	{
 		int s = actionListeners.size();
 		ActionListener[] f = new ActionListener[s];
 		for (int i = 0;i<actionListeners.size();i++)
 		{
 			f[i]=(ActionListener)actionListeners.elementAt(i);
 		}
 		return f;
 	}
}
