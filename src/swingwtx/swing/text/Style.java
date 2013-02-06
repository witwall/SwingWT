/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import swingwtx.swing.event.*;

public interface Style extends MutableAttributeSet {

    public String getName();
    public void addChangeListener(ChangeListener l);
    public void removeChangeListener(ChangeListener l);

}

