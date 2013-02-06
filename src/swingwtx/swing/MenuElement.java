/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

 
*/
package swingwtx.swing;

import swingwt.awt.*;
import swingwt.awt.event.*;

public interface MenuElement {
    public void menuSelectionChanged(boolean isIncluded);
    public MenuElement[] getSubElements();
    public Component getComponent();
    public void processMouseEvent(MouseEvent event,MenuElement path[],MenuSelectionManager manager);
    public void processKeyEvent(KeyEvent event,MenuElement path[],MenuSelectionManager manager);
}
