/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/

package swingwtx.swing.undo;

import swingwtx.swing.event.EventListenerList;
import swingwtx.swing.event.UndoableEditEvent;
import swingwtx.swing.event.UndoableEditListener;

/**
 * UndoableEditSupport
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class UndoableEditSupport
{
    protected CompoundEdit compoundEdit = null;
    protected Object realSource = null;

    public UndoableEditSupport()
    {
        this(null);
    }

    public UndoableEditSupport(Object source)
    {
        if (source == null) realSource = this;
        else realSource = source;
    }

    protected int updateLevel = 0;
    public int getUpdateLevel()
    {
        return updateLevel;
    }

    private void fireUndoableEditHappened(UndoableEdit undoableEdit)
    {
        UndoableEditEvent undoableEditEvent = new UndoableEditEvent(realSource, undoableEdit);
        Object[] listeners = listenerList.getListenerList();
        for (int i=0; i<listeners.length; i++)
        {
            ((UndoableEditListener)listeners[i]).undoableEditHappened(undoableEditEvent);
        }
    }

    public synchronized void postEdit(UndoableEdit undoableEdit)
    {
        // If not updating
        if (getUpdateLevel() == 0)
            fireUndoableEditHappened(undoableEdit);
        // otherwise add this edit to the compound edit queue
        else
            compoundEdit.addEdit(undoableEdit);
    }

    protected CompoundEdit createCompoundEdit()
    {
        return new CompoundEdit();
    }

    public synchronized void beginUpdate()
    {
        // Create a new compound edit if we don't have one
        if (getUpdateLevel() == 0)
            compoundEdit = createCompoundEdit();

        // increment the count of edits
        updateLevel++;
    }

    public synchronized void endUpdate()
    {
        // decrement the counter
        updateLevel--;

        if (getUpdateLevel() == 0)
        {
            // Finish the edit
            compoundEdit.end();

            // Notify listeners
            fireUndoableEditHappened(compoundEdit);
        }
    }

    /**
     * Listener support
     */
    protected EventListenerList listenerList = new EventListenerList();

    public synchronized void addUndoableEditListener(UndoableEditListener undoableEditListener)
    {
        listenerList.add(UndoableEditListener.class, undoableEditListener);
    }

    public synchronized void removeUndoableEditListener(UndoableEditListener undoableEditListener)
    {
        listenerList.remove(UndoableEditListener.class, undoableEditListener);
    }

    public synchronized UndoableEditListener[] getUndoableEditListeners()
    {
        return (UndoableEditListener[]) listenerList.getListenerList();
    }
}
