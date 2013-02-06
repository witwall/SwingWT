/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   

*/


package swingwtx.swing;

import swingwt.awt.*;

public class JWindow extends swingwt.awt.Window implements WindowConstants, RootPaneContainer {
    
    public JWindow() { this((Frame)null); }
    public JWindow(GraphicsConfiguration gc) { this(null, gc); }
    public JWindow(Frame owner) { super(owner); }
    public JWindow(Window owner) { super(owner); }
    public JWindow(Window owner, GraphicsConfiguration gc) { super(owner, gc); }
    
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
    
}
