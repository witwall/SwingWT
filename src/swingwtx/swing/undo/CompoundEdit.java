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
 * Implementation of UndoableEdit that acts as a container for multiple edits.
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class CompoundEdit extends AbstractUndoableEdit
{
    /* List of edits */
    protected Vector edits = new Vector();

    public CompoundEdit()
    {
        super();
    }

    public boolean canUndo() { return !isInProgress() && super.canUndo(); }
    public void undo() throws CannotUndoException
    {
        super.undo();

        // Undo each edit
        for (int i=(edits.size()-1); i >= 0; i--)
        {
            ((UndoableEdit)edits.elementAt(i)).undo();
        }
    }

    public boolean canRedo() { return !isInProgress() && super.canRedo(); }
    public void redo() throws CannotRedoException
    {
        super.redo();

        // Redo each edit
        Iterator iterator = edits.iterator();
        while (iterator.hasNext())
        {
            UndoableEdit undoableEdit = (UndoableEdit) iterator.next();
            undoableEdit.redo();
        }
    }

    protected UndoableEdit lastEdit()
    {
        return edits.size() > 0 ? (UndoableEdit)edits.lastElement() : null;
    }

    private boolean inProgress = true;
    public boolean isInProgress() { return inProgress; }
    public void end() { inProgress = false; }

    public void die()
    {
        // Tell each edit to "die"
        Iterator iterator = edits.iterator();
        while (iterator.hasNext())
        {
            UndoableEdit undoableEdit = (UndoableEdit) iterator.next();
            undoableEdit.die();
        }

        super.die();
    }

    public boolean addEdit(UndoableEdit undoableEdit)
    {
        boolean success = false;

        // If this compound edit is complete, don't allow adding new edits.
        do if (isInProgress())
        {
            UndoableEdit lastEdit = lastEdit();

            success = true;
            // If this is the first, add it
            if (edits.size() == 0)
            {
                edits.add(undoableEdit);
                break;
            }
            // If last is a CompoundEdit, add to it.
            else if (lastEdit.addEdit(undoableEdit))
            {
                break;
            }
            // If adding failed, try popping the last one onto this one.
            else if (undoableEdit.replaceEdit(lastEdit))
            {
                edits.remove(edits.size()-1);
                break;
            }

            // If we get this far, something failed, so just add to the end of this CompoundEdit
            edits.add(undoableEdit);
        } while (false);

        return success;
    }

    public boolean isSignificant()
    {
        boolean isSignificant = false;

        // For a compound edit, we are "significant" if any of our children are "significant"
        Iterator iterator = edits.iterator();
        while (iterator.hasNext())
        {
            UndoableEdit undoableEdit = (UndoableEdit) iterator.next();
            if (undoableEdit.isSignificant())
            {
                isSignificant = true;
                break;
            }
        }

        return isSignificant;
    }

    public String getPresentationName()
    {
        String presentationName;

        UndoableEdit lastEdit = lastEdit();
        // If there are no edits, let AbstractUndoableEdit return a default.
        if (lastEdit == null)
            presentationName = super.getPresentationName();
        // Presentation name should be the name of the most recent edit
        else
            presentationName = lastEdit.getPresentationName();

        return presentationName;
    }

    public String getUndoPresentationName()
    {
        UndoableEdit lastEdit = lastEdit();
        // If there are no edits, let AbstractUndoableEdit return a default.
        if (lastEdit == null)
            return super.getUndoPresentationName();
        // Presentation name should be the name of the most recent edit
        else
            return lastEdit.getUndoPresentationName();
    }

    public String getRedoPresentationName()
    {
        UndoableEdit lastEdit = lastEdit();
        // If there are no edits, let AbstractUndoableEdit return a default.
        if (lastEdit == null)
            return super.getUndoPresentationName();
        // Presentation name should be the name of the most recent edit
        else
            return lastEdit.getUndoPresentationName();
    }
}
