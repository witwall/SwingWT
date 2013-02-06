/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.colorchooser;

import swingwt.awt.Color;
import swingwtx.swing.event.ChangeListener;

public interface ColorSelectionModel {
    Color getSelectedColor();
    void setSelectedColor(Color color);
    void addChangeListener(ChangeListener listener);
    void removeChangeListener(ChangeListener listener);
}
