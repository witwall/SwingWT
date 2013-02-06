/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net

 */
package swingwt.awt;

import swingwt.awt.event.ItemListener;

/**
 * The interface for objects which contain a set of items for
 * which zero or more can be selected.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public interface ItemSelectable {

	public Object[] getSelectedObjects();
	public void addItemListener(ItemListener l);
	public void removeItemListener(ItemListener l);
}
