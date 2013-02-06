/**
 * SwingWT
 *
 * This program is dual licenced under the terms of the Common Public 
 * License v1.0 and the GNU Lesser Public License (LGPL) version 2 or
 * later. Please choose whichever is most applicable to you.
 */
package swingwtx.swing;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import swingwtx.swing.event.ChangeEvent;
import swingwtx.swing.event.ChangeListener;

/**
 * A default implementation of a SwingWT model that supports at most one indexed selection.
 * 
 * @author Thiago Tonelli Bartolomei
 */
public class DefaultSingleSelectionModel implements SingleSelectionModel {

	/**
	 * The index of currently selected element, or -1 if none.
	 */
	protected int selectedIndex = -1;
	
	/**
	 * A lazy list of change listeners.
	 */
	protected List/*<ChangeListener>*/ changeListeners = null;
	
	//@Override
	public int getSelectedIndex() {
		return selectedIndex;
	}

	//@Override
	public void setSelectedIndex(int index) {
		selectedIndex = index;
		processChangeEvent(new ChangeEvent(this));
	}

	//@Override
	public void clearSelection() {
		this.setSelectedIndex(-1);
	}

	//@Override
	public boolean isSelected() {
		return selectedIndex != -1;
	}

	//@Override
	public void addChangeListener(ChangeListener listener) {
		this.getChangeListeners().add(listener);
	}

	//@Override
	public void removeChangeListener(ChangeListener listener) {
		this.getChangeListeners().remove(listener);
	}
	
	/**
	 * Gets the list of change listeners, creating a new one if needed.
	 *
	 * @return
	 */
	protected final List/*<ChangeListener>*/ getChangeListeners() {
		if (null == changeListeners)
			changeListeners = new Vector/*<ChangeListener>*/();
		return changeListeners;
	}
	
	/**
     * Process change events
     */
    protected final void processChangeEvent(ChangeEvent e) {
    	if (null != changeListeners) {
    		Iterator i = changeListeners.iterator();
    		while(i.hasNext())
    			((ChangeListener) i.next()).stateChanged(e);
    	}
    		/*
    		for (ChangeListener l : changeListeners)
    			l.stateChanged(e);
    			*/
    }
}
