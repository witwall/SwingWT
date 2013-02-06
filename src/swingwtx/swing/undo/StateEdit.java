/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/

package swingwtx.swing.undo;

import java.util.*;

/**
 * StateEdit
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class StateEdit extends AbstractUndoableEdit
{
    protected StateEditable object;

    /** Track before/after values */
    protected Hashtable preState = new Hashtable();
    protected Hashtable postState = new Hashtable();

    protected String undoRedoName;
    public String getPresentationName() { return undoRedoName; }

    public StateEdit(StateEditable stateEditable)
    {
        super();
        init(stateEditable, null);
    }

    public StateEdit(StateEditable stateEditable, String undoRedoName)
    {
        super();
        init(stateEditable, undoRedoName);
    }

    protected void init(StateEditable stateEditable, String undoRedoName)
    {
        object = stateEditable;
        this.undoRedoName = undoRedoName;
        object.storeState(preState);
    }

    public void undo()
    {
        super.undo();

        // Undo to state saved off on init
        object.restoreState(preState);
    }

    public void redo()
    {
        super.redo();

        // Redo to state saved off on initial completion
        object.restoreState(postState);
    }

    public void end()
    {
        object.storeState(postState);
        removeRedundantState();
    }

    /** Called by end() to clean up duplicate and unnecessary key/values */
    protected void removeRedundantState()
    {
        ArrayList duplicates = new ArrayList();

        /** Find duplicates - equal values in both preState and postState */
        Iterator iterator = preState.keySet().iterator();
        while (iterator.hasNext())
        {
            Object key = iterator.next();
            if (postState.containsKey(key))
            {
                if (preState.get(key).equals(postState.get(key)))
                {
                    duplicates.add(key);
                }
            }
        }

        /** Remove each duplicate from the postState */
        iterator = duplicates.iterator();
        while (iterator.hasNext())
        {
            Object key = iterator.next();
            postState.remove(key);
	        preState.remove(key);
        }
    }
}
