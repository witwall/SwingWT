/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import swingwt.awt.*;
import swingwtx.accessibility.*;

public class JDialog extends swingwt.awt.Dialog implements WindowConstants, Accessible, RootPaneContainer {
    
    protected int closeOperation = WindowConstants.DISPOSE_ON_CLOSE;
    
    public JDialog() {super(); }
    public JDialog(swingwt.awt.Dialog owner) { super(owner);  }
    public JDialog(swingwt.awt.Dialog owner, boolean modal) { super(owner, modal); }
    public JDialog(swingwt.awt.Dialog owner, String title) { super(owner, title); }
    public JDialog(swingwt.awt.Dialog owner, String title, boolean modal) { super(owner, title, modal); }
    public JDialog(swingwt.awt.Dialog owner, String title, boolean modal, GraphicsConfiguration gc) { super(owner, title, modal, gc); }
    public JDialog(Frame owner) { super(owner);  }
    public JDialog(Frame owner, boolean modal) { super(owner, modal); }
    public JDialog(Frame owner, String title) { super(owner, title); }
    public JDialog(Frame owner, String title, boolean modal) { super(owner, title, modal); }
    public JDialog(Frame owner, String title, boolean modal, GraphicsConfiguration gc) { super(owner, title, modal, gc); }

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

    // Just here, to be able to compile
    public static void setDefaultLookAndFeelDecorated(boolean defaultLookAndFeelDecorated)
    {
    	
    }
    public static boolean isDefaultLookAndFeelDecorated()
    {
    	return true;
    }
    
}
