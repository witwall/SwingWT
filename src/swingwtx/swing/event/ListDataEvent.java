/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.event;

public class ListDataEvent extends java.util.EventObject {
    
    private int type;
    private int index0;
    private int index1;
    
    public static final int CONTENTS_CHANGED = 0;
    public static final int INTERVAL_ADDED = 1;
    public static final int INTERVAL_REMOVED = 2;
    
    public int getType() { return type; }
    public int getIndex0() { return index0; }
    public int getIndex1() { return index1; }
    
    public ListDataEvent(Object source, int type, int index0, int index1) {
        super(source);
	this.type = type;
	this.index0 = index0;
	this.index1 = index1;
    }

}



