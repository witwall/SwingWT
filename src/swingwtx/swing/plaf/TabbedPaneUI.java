/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.plaf;

import swingwtx.swing.*;

import swingwt.awt.*;

public abstract class TabbedPaneUI extends ComponentUI implements SwingConstants
{
	public abstract int tabForCoordinate(JTabbedPane pane,int x,int y);
	public abstract Rectangle getTabBounds(JTabbedPane pane,int index);
	public abstract int getTabRunCount(JTabbedPane pane);
}
