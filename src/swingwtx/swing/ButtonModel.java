/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */
package swingwtx.swing;

import swingwt.awt.ItemSelectable;
import swingwt.awt.event.ActionListener;
import swingwt.awt.event.ItemListener;
import swingwtx.swing.event.ChangeListener;

/**
 * State Model for buttons.
 * This model is used for check boxes and radio buttons, which are
 * special kinds of buttons, as well as for normal buttons.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public interface ButtonModel extends ItemSelectable {
	boolean isArmed();     
	boolean isSelected();
	boolean isEnabled();
	boolean isPressed();
	boolean isRollover();
	public void setArmed(boolean b);
	public void setSelected(boolean b);
	public void setEnabled(boolean b);
	public void setPressed(boolean b);
	public void setRollover(boolean b);
	public void setMnemonic(int key);
	public int  getMnemonic();
	public void setActionCommand(String s);
	public String getActionCommand();
	public void setGroup(ButtonGroup group);
	void addActionListener(ActionListener l);
	void removeActionListener(ActionListener l);
	void addItemListener(ItemListener l);
	void removeItemListener(ItemListener l);
	void addChangeListener(ChangeListener l);
	void removeChangeListener(ChangeListener l);
}
