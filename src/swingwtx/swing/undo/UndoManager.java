/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net


*/

package swingwtx.swing.undo;

import swingwtx.swing.event.UndoableEditEvent;
import swingwtx.swing.event.UndoableEditListener;

import java.util.Iterator;

/**
 * UndoManager
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class UndoManager extends CompoundEdit
        implements UndoableEditListener
{
    int limit = 100;
    int indexOfNextAdd = 0;

    public UndoManager()
    {
        super();
    }

    public synchronized void setLimit(int limit) { this.limit = limit; }
    public synchronized int getLimit() { return limit; }

    /** Remove all the edits tracked by this UndoManager */
    public synchronized void discardAllEdits()
    {
        // Tell each edit to "die"
        Iterator iterator = edits.iterator();
        while (iterator.hasNext())
        {
            UndoableEdit undoableEdit = (UndoableEdit) iterator.next();
            undoableEdit.die();
        }

        // Clear the Vector of edits
        edits.clear();
    }

    protected void undoTo(UndoableEdit undoableEdit) throws CannotUndoException
    {
        do
        {
            // Decrement counter
            indexOfNextAdd--;

            // If we reached the end, throw an exception.
            if (indexOfNextAdd < 0)
            {
                indexOfNextAdd = 0;
                throw new CannotUndoException();
            }

            // Get the edit and undo it.
            UndoableEdit curUndoableEdit = (UndoableEdit) edits.get(indexOfNextAdd);
            curUndoableEdit.undo();

            // If this is the last to undo, exit out of loop.
            if (curUndoableEdit == undoableEdit)
                break;

        } while (true);
    }

    public synchronized void undo() throws CannotUndoException
    {
        if (isInProgress())
        {
            UndoableEdit edit = editToBeUndone();
            if (edit == null) throw new CannotUndoException();
            undoTo(edit);
        }
        else
        {
            super.undo();
        }
    }

    protected void redoTo(UndoableEdit undoableEdit) throws CannotRedoException
    {
        int size = edits.size();

        do
        {
            // Get the edit and redo it.
            UndoableEdit curUndoableEdit = (UndoableEdit) edits.get(indexOfNextAdd);
            curUndoableEdit.redo();

            // Increment counter
            indexOfNextAdd++;

            // If this is the last to redo, exit out of loop.
            if (curUndoableEdit == undoableEdit)
                break;

            // If we reached the top, throw an exception.
            if (indexOfNextAdd >= size)
            {
                indexOfNextAdd = 0;
                throw new CannotRedoException();
            }

        } while (true);
    }

    public synchronized void redo() throws CannotRedoException
    {
        if (isInProgress())
        {
            UndoableEdit edit = editToBeRedone();
            if (edit == null) throw new CannotRedoException();
            redoTo(edit);
        }
        else
        {
            super.redo();
        }
    }

    protected void trimEdits(int from, int to)
    {
        if (from <= to)
        {
            // Remove edits >= from, <= to
            int itemsToRemove = to - from + 1;
            while (itemsToRemove > 0)
            {
                // Kill current edit
                ((UndoableEdit)edits.get(from)).die();
                edits.remove(from);

                // Decrement counter
                itemsToRemove--;
            }

            // Update next index based on what we removed
            if (indexOfNextAdd > to)
                indexOfNextAdd = indexOfNextAdd - to + from - 1;
            else if (indexOfNextAdd >= from)
                indexOfNextAdd = from;
        }
    }

    protected void trimForLimit()
    {
        int size = edits.size();

        if (limit > 0 && size > limit)
        {
            // Get indices to bound on
            int middle = limit / 2;
            int high = Math.min(indexOfNextAdd+middle-1, size-1);

            trimEdits(high+1, size-1);
            trimEdits(0, high-limit);
        }
    }

    public synchronized void undoOrRedo() throws CannotRedoException, CannotUndoException
    {
        if (indexOfNextAdd == edits.size()) undo();
        else redo();
    }

    public synchronized boolean canUndoOrRedo()
    {
        return false;
    }

    public synchronized boolean canUndo()
    {
        boolean canUndo = false;

        // If edit is active, see if it's undoable.
        if (isInProgress())
        {
            UndoableEdit undoableEdit = editToBeUndone();
            if (undoableEdit != null) canUndo = undoableEdit.canUndo();
        }
        // Otherwise, get CompoundEdit's default value
        else
        {
            canUndo = super.canUndo();
        }

        return canUndo;
    }

    public synchronized boolean canRedo()
    {
        boolean canRedo = false;

        // If edit is active, see if it's redoable.
        if (isInProgress())
        {
            UndoableEdit undoableEdit = editToBeRedone();
            if (undoableEdit != null) canRedo = undoableEdit.canRedo();
        }
        // Otherwise, get CompoundEdit's default value
        else
        {
            canRedo = super.canRedo();
        }

        return canRedo;
    }

    public synchronized boolean addEdit(UndoableEdit undoableEdit)
    {
        boolean success;

        // Make room for this UndoableEdit, if necessary
        trimEdits(indexOfNextAdd, edits.size()-1);

        // Add this edit to the list and update next index counter
        success = super.addEdit(undoableEdit);
        indexOfNextAdd = edits.size();

        // Trim out first item if necessary
        trimForLimit();

        return success;
    }

    public synchronized void end()
    {
        super.end();
        trimEdits(indexOfNextAdd, edits.size()-1);
    }

    public synchronized String getUndoOrRedoPresentationName()
    {
        if (indexOfNextAdd == edits.size()) return getUndoPresentationName();
        else return getRedoPresentationName();
    }

    // TODO: presentation names aren't localized
    public synchronized String getUndoPresentationName()
    {
        String presentationName;

        // If we have an edit, get it
        if (isInProgress())
        {
            UndoableEdit undoableEdit = editToBeUndone();
            if (undoableEdit != null)
                presentationName = undoableEdit.getUndoPresentationName();
            else presentationName = "Undo";
        }
        else
            presentationName = super.getUndoPresentationName();

        return presentationName;
    }

    // TODO: presentation names aren't localized
    public synchronized String getRedoPresentationName()
    {
        String presentationName;

        // If we have an edit, get it
        if (isInProgress())
        {
            UndoableEdit undoableEdit = editToBeUndone();
            if (undoableEdit != null)
                presentationName = undoableEdit.getRedoPresentationName();
            else
                presentationName = "Redo";
        }
        else
            presentationName = super.getRedoPresentationName();

        return presentationName;
    }

    public void undoableEditHappened(UndoableEditEvent undoableEditEvent)
    {
        addEdit(undoableEditEvent.getEdit());
    }

    /** Returns the most recent significant UndoableEdit that can be undone */
    protected UndoableEdit editToBeUndone()
    {
        UndoableEdit undoableEdit = null;

        // Start at current location, going backwards.
        for (int i = indexOfNextAdd-1; i >= 0; i--)
        {
            UndoableEdit curUndoableEdit = (UndoableEdit) edits.get(i);

            // If edit is "significant", we can undo it.
            if (curUndoableEdit.isSignificant())
            {
                undoableEdit = curUndoableEdit;
                break;
            }
        }

        return undoableEdit;
    }

    /** Returns the most recent significant UndoableEdit that can be redone */
    protected UndoableEdit editToBeRedone()
    {
        UndoableEdit undoableEdit = null;

        // Start at current location, going forward.
        for (int i = indexOfNextAdd; i<edits.size(); i++)
        {
            UndoableEdit curUndoableEdit = (UndoableEdit) edits.get(i);

            // If edit is "significant", we can redo it.
            if (curUndoableEdit.isSignificant())
            {
                undoableEdit = curUndoableEdit;
                break;
            }
        }

        return undoableEdit;
    }
}
