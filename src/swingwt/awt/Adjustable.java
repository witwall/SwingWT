/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net

 */
package swingwt.awt;

import swingwt.awt.event.*;

/**
 * An interface for objects which have an adjustable numeric value
 * contained within a bounded range of values.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public interface Adjustable {
	public static final int HORIZONTAL=0; 
	public static final int VERTICAL=1;    
	public static final int NO_ORIENTATION=2;    
	int getOrientation();
	void setMinimum(int min);
	int getMinimum();
	void setMaximum(int max);
	int getMaximum();
	void setUnitIncrement(int u);
	int getUnitIncrement();
	void setBlockIncrement(int b);
	int getBlockIncrement();
	void setVisibleAmount(int v);
	int getVisibleAmount();
	void setValue(int v);
	int getValue();
	void addAdjustmentListener(AdjustmentListener l);
	void removeAdjustmentListener(AdjustmentListener l);

}
