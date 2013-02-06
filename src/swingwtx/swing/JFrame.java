/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import org.eclipse.swt.widgets.Shell;

import swingwt.awt.Component;
import swingwt.awt.Container;
import swingwt.awt.GraphicsConfiguration;

public class JFrame extends swingwt.awt.Frame implements WindowConstants, RootPaneContainer {


	public static final int EXIT_ON_CLOSE = WindowConstants.EXIT_ON_CLOSE;
    protected int closeOperation = WindowConstants.DISPOSE_ON_CLOSE;
    
    
    public JFrame() { super();}
    public JFrame(String title) {super(title); }
    public JFrame(GraphicsConfiguration gc) { super(gc); }
    
    public void assertCachedProperties() {
        super.assertCachedProperties();
        setExtendedState(state);
    }
    /** Handy for layout problems. Toggles the window between
     *  max/norm according to what is set and guarantees 
     *  relayout of components
     */
    public void toggleWindowState() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
               Shell shell = (Shell) peer;
               if (isMax) {
            	   shell.setMaximized(false);
            	   shell.setMaximized(true);
               }
               else
               {
            	   shell.setMaximized(true);
            	   shell.setMaximized(false);
               }
            }
        });
    }
    
    public int getDefaultCloseOperation() { 
        return closeOperation;
    }
    
    public void setDefaultCloseOperation(int operation) {
        closeOperation = operation;
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

    protected org.eclipse.swt.events.ShellListener createShellListener() {
        // Overriden here so we can manage default close operations.
        return new WindowShellListener() {
            public void shellClosed(org.eclipse.swt.events.ShellEvent e) {
                super.shellClosed(e);
                // See what's set for the default close operation and handle it
                switch (closeOperation) {
                    case WindowConstants.DISPOSE_ON_CLOSE: e.doit = true; SwingWTUtils.decrementWindowReferences(); break;
                    case WindowConstants.DO_NOTHING_ON_CLOSE: e.doit = false; break;
                    case WindowConstants.EXIT_ON_CLOSE: e.doit = true; SwingWTUtils.decrementWindowReferences(); System.exit(0); break;
                    case WindowConstants.HIDE_ON_CLOSE: e.doit = false; peer.setVisible(false); break;
                }
            }
        };
    }
}
