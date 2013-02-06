/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import swingwtx.swing.event.*;

import java.util.*;

public abstract class AbstractSpinnerModel implements SpinnerModel {

    protected Vector listeners = new Vector();

    public void addChangeListener(ChangeListener l) {
        listeners.add(l);
    }
    
    public void removeChangeListener(ChangeListener l) {
        listeners.remove(l);
    }

    public ChangeListener[] getChangeListeners() {
        ChangeListener[] cl = new ChangeListener[listeners.size()];
        for (int i = 0; i < listeners.size(); i++)
            cl[i] = (ChangeListener) listeners.get(i);
        return cl;
    }


    protected void fireStateChanged() {
        for (int i = 0; i < listeners.size(); i++)
            ((ChangeListener) listeners.get(i)).stateChanged(new ChangeEvent(this));    
    }   

}


