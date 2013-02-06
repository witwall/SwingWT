/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import swingwt.awt.*;

/**
 * RootPane implementation. Handles containing of all other components
 * for windows and internal frames. Manages default buttons and a 
 * few other bits and bobs. Ok, so the hierarchy has a bit more in
 * it than "real" swing, but it shouldn't cause any problems.
 *
 * @author  Robin Rawson-Tetley
 */
public class JRootPane extends JPanel {
    
    public static final int NONE = 0;
    public static final int FRAME = 1;
    public static final int PLAIN_DIALOG = 2;
    public static final int INFORMATION_DIALOG = 3;
    public static final int ERROR_DIALOG = 4;
    public static final int COLOR_CHOOSER_DIALOG = 5;
    public static final int FILE_CHOOSER_DIALOG = 6;
    public static final int QUESTION_DIALOG = 7;
    public static final int WARNING_DIALOG = 8;
    
    protected int windowDecorationStyle = 0;
    protected Container contentPane = null;
    protected JMenuBar jMenuBar = null;
    protected MenuBar menuBar = null;
    protected JButton defaultButton = null;
    protected Component glassPane = null;    // Here for completeness, but SWT doesn't need transparent
                                             // component overlay for catching keystrokes since we're already native
    protected JLayeredPane layeredPane = null;
    protected Window parentWindow = null;
    
    public JRootPane() {
        this(null);
    }
    
    public JRootPane(Window parentWindow) {
        this.parentWindow = parentWindow;
        setLayout(new JRootPane.RootPaneLayout());
        setGlassPane(createGlassPane());
        setLayeredPane(createLayeredPane());
        setContentPane(createContentPane());
        setOpaque(true);
    }
    
    protected Container createContentPane() {
        JPanel content = new JPanel();
        content.setLayout(new BorderLayout()); // Default BorderLayout for root panes
        return content;
    }
    
    protected Component createGlassPane() {
        return new JPanel();    
    }
    
    protected JLayeredPane createLayeredPane() {
        return new JLayeredPane();    
    }
    
    public int getWindowDecorationStyle() {
        return windowDecorationStyle;
    }
    
    public void setWindowDecorationStyle(int windowStyle) {
        windowDecorationStyle = windowStyle;
    }
    
    public Container getContentPane() {
        return contentPane;    
    }
    
    public void setContentPane(Container content) {
        if (contentPane != null)
            this.removeAll();
        contentPane = content;
        this.add(contentPane);
    }
    
    public Component getGlassPane() {
        return glassPane;
    }
    
    public JLayeredPane getLayeredPane() {
        return layeredPane;
    }
    
    public void setGlassPane(Component glassPane) {
        this.glassPane = glassPane;
    }
    
    public void setLayeredPane(JLayeredPane layeredPane) {
        this.layeredPane = layeredPane;
    }
    
    public void setJMenuBar(final JMenuBar menu) { 
        this.jMenuBar = menu; 
        try { 
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    try { 
                        menu.setSwingWTParent(((org.eclipse.swt.widgets.Shell)parentWindow.getSWTPeer())); 
                    } 
                    catch (Exception e) { 
                        e.printStackTrace(); 
                    }
                }
            });
        } 
        catch (Exception e) {
            e.printStackTrace(); 
        }
    }
    
    public void setMenuBar(MenuBar menu) { setJMenuBar(menu.getJMenuBar()); this.menuBar = menu; }
    public MenuBar getMenuBar() { return menuBar; }
    public JMenuBar getJMenuBar() { return jMenuBar; }
    
    public JButton getDefaultButton() { return defaultButton; }
    
    public void setDefaultButton(final JButton defaultButton) {
        this.defaultButton = defaultButton;
        if (defaultButton == null) {
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    if (SwingWTUtils.isSWTControlAvailable(peer))
                        peer.getShell().setDefaultButton(null);
                }
            });
        } else {
            defaultButton.setDefaultButtonParent(this);
            SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    if (SwingWTUtils.isSWTControlAvailable(peer) && defaultButton.getSWTPeer() != null)
                        peer.getShell().setDefaultButton((org.eclipse.swt.widgets.Button) defaultButton.getSWTPeer());
                }
            });
        }
    }
    
    /** 
     *  Since components live in this on all frames, returns true as the
     *  root for which validation down should be performed when laying
     *  out components 
     */
    public boolean isValidateRoot() {
        return true;    
    }
    
    /** Returns true - native widgets! */
    public boolean isOptimizedDrawingEnabled() { 
        return true;
    }

    public Dimension getPreferredSize() {
        return contentPane.getPreferredSize();
    }
    public Dimension getMinimumSize() {
        return contentPane.getMinimumSize();
    }
    public Dimension getMaximumSize() {
        return contentPane.getMaximumSize();
    }

    /** NOT IMPLEMENTED */
    public void addNotify() { }
    /** NOT MIMPLEMENTED */
    public void removeNotify() { }
    
    /** RootPane layout - one component and it fills the whole damn thing */
    protected class RootPaneLayout extends FillLayout { }
    
}
