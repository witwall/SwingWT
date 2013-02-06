/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/

package swingwtx.swing.undo;

import java.util.Hashtable;

/**
 * StateEditable
 *
 * @author  Naab
 * @version %I%, %G%
 */
public interface StateEditable
{
    public void storeState(Hashtable state);
    public void restoreState(Hashtable state);
}
