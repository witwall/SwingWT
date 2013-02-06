/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.border;

import swingwt.awt.*;

public class SoftBevelBorder extends BevelBorder {
	
	public SoftBevelBorder(int bevelType) {
		this.bevelType = bevelType;
	}

	public SoftBevelBorder(int bevelType, Color highlight, Color shadow) {
		this(bevelType);
	}

	public SoftBevelBorder(int bevelType, Color highlightOuterColor,
			Color highlightInnerColor, Color shadowOuterColor,
			Color shadowInnerColor) {
		this(bevelType);
	}
}
