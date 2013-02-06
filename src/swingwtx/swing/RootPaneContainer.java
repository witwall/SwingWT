/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import swingwt.awt.*;

public interface RootPaneContainer {
    
    JRootPane getRootPane();
    void setContentPane(Container contentPane);
    Container getContentPane();
    void setLayeredPane(JLayeredPane layeredPane);
    JLayeredPane getLayeredPane();
    void setGlassPane(Component glassPane);
    Component getGlassPane();

}
