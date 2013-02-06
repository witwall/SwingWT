/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.event;

import swingwtx.swing.undo.UndoableEdit;

public class UndoableEditEvent extends java.util.EventObject {
    private UndoableEdit myEdit;

    public UndoableEditEvent(Object source, UndoableEdit edit) {
        super(source);
        myEdit = edit;
    }

    public UndoableEdit getEdit() {
	    return myEdit;
    }
}
