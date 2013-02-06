/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */

package swingwtx.swing.event;

import swingwtx.swing.table.*;

import java.util.*;

public class TableColumnModelEvent extends EventObject {

    protected int fromIndex;
    protected int toIndex;

    public TableColumnModelEvent(TableColumnModel source, int from, int to) {
	super(source);
	fromIndex = from;
	toIndex = to;
    }
    public int getFromIndex() { return fromIndex; };
    public int getToIndex() { return toIndex; };
}

