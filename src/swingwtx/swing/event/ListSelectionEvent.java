/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.event;

import java.util.EventObject;

public class ListSelectionEvent extends EventObject {
    private int firstIndex;
    private int lastIndex;
    private boolean isAdjusting;
    public ListSelectionEvent(Object source, int firstIndex, int lastIndex, boolean isAdjusting) {
	super(source);
	this.firstIndex = firstIndex;
	this.lastIndex = lastIndex;
	this.isAdjusting = isAdjusting;
    }
    public int getFirstIndex() { return firstIndex; }
    public int getLastIndex() { return lastIndex; }
    public boolean getValueIsAdjusting() { return isAdjusting; }

}
