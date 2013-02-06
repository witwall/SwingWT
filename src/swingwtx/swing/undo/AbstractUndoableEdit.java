/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/

package swingwtx.swing.undo;

/**
 * AbstractUndoableEdit
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class AbstractUndoableEdit implements UndoableEdit
{
    /** Indicates whether or not this edit is still active and can be undone/redone */
    private boolean alive = true;
    /** Indicates if this edit has been completed. */
    private boolean completed = true;

    public AbstractUndoableEdit()
    {
        super();
    }

    public void undo() throws CannotUndoException
    {
	    if (!canUndo())
	        throw new CannotUndoException();
	    completed = false;
    }
    public void redo() throws CannotRedoException
    {
        if (!canRedo())
            throw new CannotRedoException();
        completed = true;
    }

    /** Kill off the edit... can no longer undo */
    public void die()
    {
        alive = false;
    }

    public boolean canUndo()
    {
        return completed && alive;
    }

    public boolean canRedo()
    {
        return !completed && alive;
    }

    protected static final String UndoName = "Undo";
    protected static final String RedoName = "Redo";

    public String getUndoPresentationName()
    {
        return UndoName;
    }

    public String getRedoPresentationName()
    {
        return RedoName;
    }

    /** Default implementations do nothing.  Override in implementing classes */
    public boolean addEdit(UndoableEdit anEdit) { return false; }
    public boolean replaceEdit(UndoableEdit anEdit) { return false; }
    public boolean isSignificant() { return true; }
    public String getPresentationName() { return ""; }
}
