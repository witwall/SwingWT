/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.border;

import swingwt.awt.*;
import swingwtx.swing.*;

public class MatteBorder extends AbstractBorder implements Border {
    public MatteBorder(int top, int left, int bottom, int right, Color matteColor) {}
    public MatteBorder(Insets borderInsets, Color matteColor) {}
    public MatteBorder(int top, int left, int bottom, int right, Icon tileIcon) {}
    public MatteBorder(Insets borderInsets, Icon tileIcon) {}
    public MatteBorder(Icon tileIcon) {}
}
