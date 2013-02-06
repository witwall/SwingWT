/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

   

*/

package swingwtx.swing.undo;

/**
 * UndoableEdit
 *
 * @author  Naab
 * @version %I%, %G%
 */
public interface UndoableEdit
{
    public void undo() throws CannotUndoException;
    public boolean canUndo();
    public void redo() throws CannotRedoException;
    public boolean canRedo();
    public void die();
    public boolean addEdit(UndoableEdit anEdit);
    public boolean replaceEdit(UndoableEdit anEdit);
    public boolean isSignificant();
    public String getPresentationName();
    public String getUndoPresentationName();
    public String getRedoPresentationName();
}
