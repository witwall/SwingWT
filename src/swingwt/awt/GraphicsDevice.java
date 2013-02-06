/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

 */
package swingwt.awt;

/**
 * @author Daniel Spiewak
 */
public class GraphicsDevice {
	GraphicsConfiguration defaultConfig = new GraphicsConfiguration();

	// FIXME specify which device
	public GraphicsDevice() {
	}

	public GraphicsConfiguration getDefaultConfiguration(){
		return defaultConfig;
	}

	public DisplayMode getDisplayMode() {
		// FIXME specify which mode
		return new DisplayMode();
	}
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
