/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 

 
 */
package swingwtx.swing;

import java.beans.PropertyChangeListener;

import swingwt.awt.event.ActionListener;

/**
 * 
 * @author Daniel Spiewak
 */
public interface Action extends ActionListener {
	
	public static final String DEFAULT = "Default";
	
	public static final String NAME = "Name";
	
	public static final String SHORT_DESCRIPTION = "ShortDescription";
	
	public static final String LONG_DESCRIPTION = "LongDescription";
	
	public static final String SMALL_ICON = "SmallIcon";

	public static final String ACTION_COMMAND_KEY = "ActionCommandKey";

	public static final String ACCELERATOR_KEY = "AcceleratorKey";

	public static final String MNEMONIC_KEY = "MnemonicKey";
        
        public static final String SWT_MAPPED_TOOLITEM = "SWTToolItem";
	
	/*
	 * Added for better capibility and compatibility with SWT
	 */
	public static final String DISABLED_ICON = "DisabledIcon";
	
	/*
	 * Please note that if the value of this property is not null, then it will be assumed that it is a drop menu toolbar item
	 */
	public static final String DROP_MENU = "DropMenu";

	public Object getValue(String key);

	public void putValue(String key, Object value);

	public void setEnabled(boolean b);

	public boolean isEnabled();

	public void addPropertyChangeListener(PropertyChangeListener listener);

	public void removePropertyChangeListener(PropertyChangeListener listener);
}

/*
 *****************************************************
 * Copyright 2003 Completely Random Solutions *
 *                                												*
 * DISCLAMER:                                 					*
 * We are not responsible for any damage      		*
 * directly or indirectly caused by the usage 			*
 * of this or any other class in assosiation  			*
 * with this class.  Use at your own risk.   			*
 * This or any other class by CRS is not   			*
 * certified for use in life support systems, 			*
 * the Space Shuttle, in use or developement  		*
 * of nuclear reactors, weapons of mass       		*
 * destruction, or in interplanitary conflict.				*
 * (Unless otherwise specified)               				*
 *****************************************************
 */
