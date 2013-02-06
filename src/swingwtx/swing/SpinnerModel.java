/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import swingwtx.swing.event.*;

public interface SpinnerModel 
{
    Object getValue();
    void setValue(Object value);
    Object getNextValue();
    Object getPreviousValue();
    void addChangeListener(ChangeListener l);
    void removeChangeListener(ChangeListener l);
}
