/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import swingwt.awt.*;
import swingwtx.swing.event.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

import java.beans.PropertyVetoException;
import java.util.*;

/**
 * A JInternalFrame implementation. It is capable of drawing window decorations
 * and behaving like real MDI, or can represent a tabbed container if JDesktopPane
 * is using tabbed emulation
 */
public class JInternalFrame extends JComponent implements WindowConstants, RootPaneContainer {
    
    protected org.eclipse.swt.widgets.Composite ppeer = null;
    protected Vector internalFrameListeners = new Vector();
    protected JDesktopPane parentpane = null;
    protected Icon pImage = null;
    protected String pTitle = "";
    protected int pCloseOperation = DISPOSE_ON_CLOSE;
    protected JButton defaultButton = null;
    protected boolean disposed = true;
    protected JRootPane rootPane = null;
    /** Set to true if the decoration (border, buttons, title, icon, etc.) need drawing around the JInternalFrame */
    protected boolean drawDecoration = true;
    /** Our special rootpane, cast to it's correct type */
    protected DecoratedRootPane decoration = null;
    
    public JInternalFrame() { this("", true, true, true, true); }
    public JInternalFrame(String title) {  this(title, true, true, true, true); }
    public JInternalFrame(String title, boolean resizable) { this(title, resizable, true, true, true); }
    public JInternalFrame(String title, boolean resizable, boolean closable) { this(title, resizable, closable, true, true); }
    public JInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable) { this(title, resizable, closable, maximizable, true); }
    
    public JInternalFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) { 
        
        // Create our decorated rootpane descendant and set it
        setLayoutImpl(new FillLayout());
        decoration = new DecoratedRootPane();
        rootPane = decoration;
        doAdd(rootPane);
        
        setTitle(title);
        
    }
    
    /** Overridden to point to getContentPane() rather than throwing an error */
    public Component add(swingwt.awt.Component c) { rootPane.getContentPane().add(c); return c; }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void add(swingwt.awt.Component c, Object layoutModifier) { rootPane.getContentPane().add(c, layoutModifier); }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void remove(swingwt.awt.Component c) { rootPane.getContentPane().remove(c); }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public LayoutManager getLayout() {
        return rootPane.getContentPane().getLayout();
    }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void setLayout(LayoutManager l) {
        rootPane.getContentPane().setLayout(l);
    }
    /** Overridden to point to getContentPane() rather than throwing an error */
    public void setLayout(LayoutManager2 l) {
        rootPane.getContentPane().setLayout(l);
    }
    
    public void dispose() {
        if (!disposed) {
            // Destroy the container
            super.dispose();
            // Destroy the tab
            parentpane.removeFrame(this);
            processInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSED);
            disposed = true;
        }
    }
    
    public void addInternalFrameListener(InternalFrameListener l) { internalFrameListeners.add(l); }
    public int getDefaultCloseOperation() { return pCloseOperation; }
    public void setDefaultCloseOperation(int operation) { pCloseOperation = operation; }
    /**
     *  
     * @return  this Component's JDesktopPane  ( parent )
     */
    public JDesktopPane getDesktopPane() { return parentpane; }
    /** @deprecated  This is here for thoose that is using this faulty named method */
    public JDesktopPane getJDesktopPane() { return parentpane; }
    public Icon getFrameIcon() { return pImage; }
    public void setFrameIcon(Icon icon) { pImage = icon; refreshFrame(); }
    public String getTitle() { return pTitle; }
    public void setTitle(String title) { pTitle = title; refreshFrame(); }
    public Control getSWTPeer() { return ppeer; }
    public void show() { }
    public void hide() { }
    public void pack() { if (ppeer != null) ppeer.pack(); }
    protected void setParentPane(JDesktopPane jdp) { parentpane = jdp; }
    protected JDesktopPane getParentPane() { return parentpane; }
    public boolean isClosable() { return false; }
    public boolean isClosed()
    {
    	// TODO:   probably map to some propery in ppeer
    	return false;
    }
    public boolean isResizable() { return false; }
    public boolean isMaximizable() { return true; }
    public boolean isIconifiable() { return false; }
    public boolean isMaximum() { return true; }
    
    public void setClosed(boolean b) throws PropertyVetoException { if (b) dispose(); }
    public void setClosable(boolean b) {}
    public void setResizable(boolean b) {}
    public void setMaximizable(boolean b) {}
    public void setIconifiable(boolean b) {}
    public void setMaximum(boolean b) throws PropertyVetoException {}
    public void setIcon(boolean b) { try { setSelected(false); } catch (java.beans.PropertyVetoException e) {}}
    public void toFront() { try { setSelected(true); } catch (java.beans.PropertyVetoException e) {} }
    public void toBack() {try { setSelected(false);  } catch (java.beans.PropertyVetoException e) {}}
    public void doDefaultCloseAction() 
    {
    	// TODO:  do not dispose just close
        dispose();
    }
    public void reshape(int x, int y, int width, int height)
    {
    	setLocation(x,y);
    	setSize(width,height);
    }

    /**
     * Finds the component representing this JInternalFrame
     * and updates all it's properties based on what we have stored
     * here. Just delegates the job to the desktop pane
     */
    protected void refreshFrame() {
        // if we are using tabbed emulation, need to refresh
        // ourselves with the parent.
        if (parentpane != null) parentpane.refreshFrame(this);
        // Update frame decorations
        decoration.updateDecorationFromFrame();
    }
    
    public Dimension getSize() { 
        if (ppeer == null) 
            return new Dimension(0, 0); 
        else 
            return new Dimension(ppeer.getBounds().width, ppeer.getBounds().height); 
    }
    public void setSize(Dimension d) { }
    public void setSize(int width, int height) { }
    public void setLocation(int x, int y) {}
    public boolean isSelected() { if (parentpane != null) return (parentpane.getSelectedFrame() == this); else return false; }
    public void setSelected(boolean b) throws java.beans.PropertyVetoException { 
        if (parentpane != null) if (b) parentpane.setSelectedFrame(this); 
    }
    
    public void registerEvents() {
        super.registerEvents();
    }
    
    /**
     * Called when the user has closed the window - 
     * returns true if the user is allowed to close it
     */
    protected boolean processFrameClosing() {
        processInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSING);
        try {
            switch (pCloseOperation) {
                case DO_NOTHING_ON_CLOSE: return false;
                case DISPOSE_ON_CLOSE: if (!disposed) dispose(); return true;
                case EXIT_ON_CLOSE: if (!disposed) dispose(); return true;
                case HIDE_ON_CLOSE: if (!disposed) dispose(); return true; 
                default:
                    return true;
            }
        }
        finally {
            // Make sure we fire the closed event
            processInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_CLOSED);    
        }
    }
    
    public Container getContentPane() {
        return rootPane.getContentPane();
    }
    
    public Component getGlassPane() {
        return rootPane.getGlassPane();
    }
    
    public JLayeredPane getLayeredPane() {
        return rootPane.getLayeredPane();
    }
    
    public JRootPane getRootPane() {
        return rootPane;
    }
    
    public void setContentPane(Container contentPane) {
        rootPane.setContentPane(contentPane);
    }
    
    public void setGlassPane(Component glassPane) {
        rootPane.setGlassPane(glassPane);
    }
    
    public void setLayeredPane(JLayeredPane layeredPane) {
        rootPane.setLayeredPane(layeredPane);
    }
    
    public void setJMenuBar(JMenuBar menu) {
        rootPane.setJMenuBar(menu);    
    }
    
    public JMenuBar getJMenuBar() {
        return rootPane.getJMenuBar();    
    }
    
    public void setMenuBar(MenuBar menu) {
        rootPane.setMenuBar(menu);    
    }
    
    public MenuBar getMenuBar() {
        return rootPane.getMenuBar();    
    }
    
    public void setDefaultButton(JButton button) {
        rootPane.setDefaultButton(button);    
    }
    
    public JButton getDefaultButton() {
        return rootPane.getDefaultButton();    
    }
    
    /** Allows firing of internal frame events */
    public void processInternalFrameEvent(int eventID) {     
        InternalFrameEvent e = new InternalFrameEvent(this, eventID);
        Iterator i = internalFrameListeners.iterator();
        while (i.hasNext()) {
            InternalFrameListener l = (InternalFrameListener) i.next();
            switch (eventID) {
                case (InternalFrameEvent.INTERNAL_FRAME_ACTIVATED): l.internalFrameActivated(e); break;
                case (InternalFrameEvent.INTERNAL_FRAME_CLOSED): l.internalFrameClosed(e); break;
                case (InternalFrameEvent.INTERNAL_FRAME_CLOSING): l.internalFrameClosing(e); break;
                case (InternalFrameEvent.INTERNAL_FRAME_DEACTIVATED): l.internalFrameDeactivated(e); break;
                case (InternalFrameEvent.INTERNAL_FRAME_DEICONIFIED): l.internalFrameDeiconified(e); break;
                case (InternalFrameEvent.INTERNAL_FRAME_ICONIFIED): l.internalFrameDeiconified(e); break;
                case (InternalFrameEvent.INTERNAL_FRAME_OPENED): l.internalFrameOpened(e); break;
            }
        }
    }
    
    /** SwingWT specific - if false, prevents JInternalFrame's drawing their window decorations */
    public void setDrawDecoration(boolean b) {
        drawDecoration = b;
        if (!b)
            decoration.removeDecorations();
    }
    
    public void setSwingWTParent(Container parent) throws Exception {
        descendantHasPeer = true;
        disposed = false;
        ppeer = new org.eclipse.swt.widgets.Composite(parent.getComposite(), SWT.NO_RADIO_GROUP);
        peer = ppeer;
        composite = ppeer;
        this.parent = parent;
        super.setSwingWTParent(parent);
        processInternalFrameEvent(InternalFrameEvent.INTERNAL_FRAME_OPENED);
    }
    
    
    /**
     * This is a JRootPane descendant that is capable of drawing the border around the contents
     * of the JInternalFrame. It can also draw the title, icon and buttons for the top
     * of the frame, and handles events to allow it to be dragged around it's parent
     * window.
     *
     * @author Robin Rawson-Tetley
     */
    protected class DecoratedRootPane extends JRootPane {
        
        /** The outer panel that holds the content pane */
        protected JPanel decorationPane = null;
        /** The label containing the frame's title */
        protected JLabel lblTitle = null;
        /** The label containing the frame's icon */
        protected JLabel lblIcon = null;
        /** Container for the frame icon, title and buttons */
        protected JPanel pnlTopBar = null;
        /** Whether or not to actually include the decorations around the contentpane or not */
        protected boolean showDecorations = true;
        
        public DecoratedRootPane() { this(null); }
        public DecoratedRootPane(Window parentWindow) { 
            this.showDecorations = JInternalFrame.this.drawDecoration;
            this.parentWindow = parentWindow;
            setLayout(new JRootPane.RootPaneLayout());
            setGlassPane(createGlassPane());
            setLayeredPane(createLayeredPane());
            setContentPane(createContentPane());
        }
        
        public Container createContentPane() {
            
            // Create the decorated pane and add it to us
            decorationPane = new JPanel();
            decorationPane.setLayout(new BorderLayout());
            decorationPane.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            add(decorationPane);
            
            // Create the decorations for the window if necessary
            if (showDecorations) createDecorations();
            
            // Create the content pane
            JPanel content = new JPanel();
            content.setLayout(new BorderLayout());
            return content;
        }
        
        /** Creates all of the decorations for the internal frame.
         *  These sit in the decorationPane, which is a JPanel
         *  with a BorderLayout. The decoration pane sits inside
         *  the JRootPane and contains the content pane (which is
         *  added to decorationPane with BorderLayout.CENTER
         */
        public void createDecorations() {
            
  	    SwingUtilities.invokeSync(new Runnable() {
		public void run() {
		
                    // The panel containing the title, frame icon and buttons at
                    // the top of the frame. It uses a borderlayout and contains
                    // 3 components, aligned WEST, CENTER and EAST:
                    // WEST: JLabel containing Icon
                    // CENTER: JLabel containing Title
                    // EAST: JPanel containing min, max and close buttons
            	    pnlTopBar = new JPanel();
                    pnlTopBar.setLayout(new BorderLayout());
            
                    lblIcon = new JLabel();
                    lblIcon.setBackground( SystemColor.controlHighlight );
                    pnlTopBar.add(lblIcon, BorderLayout.WEST);
            
                    lblTitle = new JLabel();
                    lblTitle.setBackground( SystemColor.controlHighlight );
                    lblTitle.setForeground( Color.white );
		    lblTitle.setCursor( Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) );
		    // Dragging cursor on title moves internal frame around
		    lblTitle.addMouseMotionListener(new swingwt.awt.event.MouseMotionAdapter() {
		        public void mouseDragged(swingwt.awt.event.MouseEvent e) {
			    JInternalFrame.this.ppeer.setLocation( e.getX(), e.getY() );
			}
		    });
            
                    pnlTopBar.add(lblTitle, BorderLayout.CENTER);
            
                    // Set initial values
                    updateDecorationFromFrame();
            
                    // Create buttons and set events for dragging around,
                    // hitting close, etc.
                    JToolBar buttons = new JToolBar();
            
                    // Close button
                    JButton btnClose = new JButton("x");
                    btnClose.setToolTipText("Close Window");
                    btnClose.addActionListener(new swingwt.awt.event.ActionListener() {
                        public void actionPerformed(swingwt.awt.event.ActionEvent e) {
                            JInternalFrame.this.dispose(); 
                        }
                    });
                    buttons.add(btnClose);
            
                    pnlTopBar.add(buttons, BorderLayout.EAST);
            
            
                    // Add stuff to the decoration panel
                    decorationPane.add(pnlTopBar, BorderLayout.NORTH);

		}
	    });
        }
        
        /** Removes window decorations from the component */
        public void removeDecorations() {
            decorationPane.remove(pnlTopBar);
        }
        
        /** Updates the frame decoration from the JInternalFrame's properties.
         *  Call this from the JInternalFrame when the icon or title is
         *  changed
         */
        public void updateDecorationFromFrame() {
            lblIcon.setIcon(JInternalFrame.this.getFrameIcon());
            lblTitle.setText(JInternalFrame.this.getTitle());
        }
        
        public void setContentPane(Container content) {
            if (contentPane != null)
                decorationPane.remove(contentPane);
            contentPane = content;
            decorationPane.add(contentPane, BorderLayout.CENTER);
        }
        
        
    }
    
    
    public boolean isIcon() {
        // TODO
        return false;
    }
    
    public void setLayer(int layer) {
        // TODO
    }
    
    public void moveToFront() {
        toFront();
    }

    public void moveToBack() {
    	toBack();
    }
    /**
     * 
     * @return  null
     * TODO fix this
     */
    public InternalFrameListener[] getInternalFrameListeners() 
    {
        return null;
    } 


}
