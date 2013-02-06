/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import swingwt.awt.Color;
import swingwt.awt.event.KeyEvent;
import swingwtx.swing.text.*;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.*;

public class JTextArea extends swingwtx.swing.text.JTextComponent {

    protected boolean pWordWrap = false;
    
    /** The default number of columns used if the app doesn't specify */
    private final static int DEFAULT_COLS = 8;
    /** The default number of rows used if the app doesn't specify */
    private final static int DEFAULT_ROWS = 4;
    
    protected int pRows = DEFAULT_ROWS;
    protected int pCols = DEFAULT_COLS;
    
    /** Return value for thread safe accessors */
    private String retVal = "";
    private int iRetVal = 0;
    
    public JTextArea() {
        this(null, "", DEFAULT_ROWS, DEFAULT_COLS);
    }
    public JTextArea(String text) {
        this(null, text, DEFAULT_ROWS, DEFAULT_COLS);
    }
    public JTextArea(int rows, int columns) {
        this(null, "", rows, columns);
    }
    public JTextArea(String text, int rows, int columns) {
        this(null, text, rows, columns);
    }
    public JTextArea(Document doc) {
        this(doc, "", DEFAULT_ROWS, DEFAULT_COLS);
    }
    public JTextArea(Document doc, String text, int rows, int columns) {
        super(); 
        if (doc != null) setDocument(doc);
        if (text != null) {
            setText(text);
            view.updateModelFromComponent(getText());
        }
        pRows = rows;
        pCols = columns;
        calculateFromRowsCols();
    }

    // TODO
    public void setHorizontalScrollPane(boolean scroll) {}
    public void setVerticalScrollPane(boolean scroll) {}
    public boolean getHorizontalScrollPane() { return false; }
    public boolean getVerticalScrollPane() { return false; }
    public void setScrollPane(JScrollPane pane) {}

    // TODO: implement sizing by row/columns
    public void setColumns(int columns) {}
    public void setRows(int rows) {}
    public int getColumns() { return 0;}
    public int getRows() { return 0; }
    
    /**
     * Calculates the component's preferred size based on how
     * many rows/columns the user chose.
     */
    protected void calculateFromRowsCols() {
        if (pRows != 0 && pCols != 0)
            setPreferredSize( new swingwt.awt.Dimension((SwingWTUtils.getRenderStringWidth("W") * pCols), ((SwingWTUtils.getRenderStringHeight("W")) * pRows) + 6 ));
        else if (pRows == 0 && pCols != 0)
            setPreferredSize( new swingwt.awt.Dimension((SwingWTUtils.getRenderStringWidth("W") * pCols), (SwingWTUtils.getRenderStringHeight("W")) + 6 ));
    }
    
    
    public void append(final String text) { 
        pText += text;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) ppeer.append(text); 
            }
        });
    }
    
    public boolean getLineWrap() { return true; }
    public void setLineWrap(boolean b) { }
    public boolean getWrapStyleWord() { return pWordWrap; }
    public void setWrapStyleWord(final boolean b) { pWordWrap = b; } 
    
    /** Overriden to calculate non-realised
     *  preferred size.
     */
    protected swingwt.awt.Dimension calculatePreferredSize() {
    	swingwt.awt.Dimension size = new swingwt.awt.Dimension(
    			(SwingWTUtils.getRenderStringWidth("W") * pCols), 
    			6 + (pRows * (SwingWTUtils.getRenderStringHeight("W")))
				);
        setSize(size);
        return size;
    }
    
    /** Overriden so we can make sure that text areas aren't
     *  too small to match Swing.
     */
    protected swingwt.awt.Dimension computePreferredSize() {
        swingwt.awt.Dimension size = new swingwt.awt.Dimension(
            SwingWTUtils.getRenderStringWidth(pText), 
            SwingWTUtils.getRenderStringHeight(pText) + 4);
        int oneChar = SwingWTUtils.getRenderStringHeight("W") + 4;
        if (size.height <= oneChar)
            size.height = oneChar * 4;
        int smallest = SwingWTUtils.getRenderStringWidth("WWWWWWWWW");
        if (size.width < smallest)
            size.width = smallest;
        return size;
    }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        
        int swtFlags = SWT.BORDER | SWT.MULTI;
        swtFlags |= pWordWrap ? SWT.WRAP : SWT.NONE;
        
        if (parent instanceof JScrollPane) {
            JScrollPane scrollPane = (JScrollPane) parent;
            boolean useHorizontal = false, useVertical = false;
            switch (scrollPane.getHorizontalScrollBarPolicy()) {
                case JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS:
                    useHorizontal = true;
                    break;
                case JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED:
                    useHorizontal = true;
                    break;
                case JScrollPane.HORIZONTAL_SCROLLBAR_NEVER:
                    break;
                default:
                    useHorizontal = false;
                    break;
            }
            switch (scrollPane.getVerticalScrollBarPolicy()) {
                case JScrollPane.VERTICAL_SCROLLBAR_ALWAYS:
                    useVertical = true;
                    break;
                case JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED:
                    useVertical = true;
                    break;
                case JScrollPane.VERTICAL_SCROLLBAR_NEVER:
                    break;
                default:
                    useVertical = false;
                    break;
            }
            swtFlags |= useHorizontal ? SWT.H_SCROLL : SWT.NONE;
            swtFlags |= useVertical ? SWT.V_SCROLL : SWT.NONE;
        }
        
        ppeer = new Text(parent.getComposite(), swtFlags);
        ppeer.setText(pText);
        ppeer.setEditable(pEditable);
        ppeer.setTabs(0);
        peer = ppeer;

        ppeer.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
            public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
                
                // This is so that CTRL+A selects everything in the text field
                if ((e.keyCode == 97 ) && ((e.stateMask & SWT.CTRL) > 0)) {
                    selectAll();
                    e.doit = false;
                }
                
                // TAB navigation
                if ((e.keyCode == SWT.TAB) && ((e.stateMask & SWT.SHIFT) > 0)) {
                    ppeer.traverse(SWT.TRAVERSE_TAB_PREVIOUS);
                    e.doit = false;
                }
                else if (e.keyCode == SWT.TAB) {
                    ppeer.traverse(SWT.TRAVERSE_TAB_NEXT);
                    e.doit = false;
                }
                
            }    
        });
        
        this.parent = parent;
        ppeer.addVerifyListener(new TextEventHandler());

        ppeer.addMouseListener(new MouseListener() {
            public void mouseDoubleClick(MouseEvent arg0) {}
            public void mouseDown(MouseEvent arg0) {
            }
            public void mouseUp(MouseEvent arg0) {
                // Generate keypress from middle-click for X paste
                if (arg0.button == 2)
                    processKeyEvent(new KeyEvent(JTextArea.this, KeyEvent.KEY_PRESSED));
            }
             
        });

    }
    
    public void setDisabledTextColor(Color c)
    {
        // TODO
    }

    public void setTabSize(int size)
    {
        // TODO	
    }
    
    public int getLineCount()
    {
        int count = 0;
        String text = getText();
        int l = text.length();
        if (l > 0) {
	        for (int i = 0; i < l; i++) {
	            if (text.charAt(i) == '\n') {
	                count++;
	            }
	        }
	        if (text.charAt(l-1) != '\n') {
	            count++;
	        }
        }
        return count;
    }
}
