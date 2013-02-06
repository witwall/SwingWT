/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   
*/

package swingwt.awt.event;

import swingwt.awt.*;

public class ItemEvent extends AWTEvent {

    public static final int SELECTED = 0;
    public static final int DESELECTED = 1;
    
    public static final int ITEM_STATE_CHANGED = 701;

    protected Object item;
    protected int stateChange;
    
    public ItemEvent(Object source, int id, Object item, int stateChange) {
        super(source, id);
	this.item = item;
        this.stateChange = stateChange;
    }

    public Object getItemSelectable() {
        return source;
    }

    public Object getItem() {
        return item;
    }

    public int getStateChange() {
        return stateChange;
    }
    
}
