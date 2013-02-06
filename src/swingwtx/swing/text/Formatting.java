/*
	SwingWT
	Copyright(c)2003-2008, R. Rawson-Tetley
	
	For more information on distributing and using this program, please
	see the accompanying "COPYING" file.
	
	Contact me by electronic mail: bobintetley@users.sourceforge.net

*/
package swingwtx.swing.text;

/**
 * A formatting representation - an offset, length and set of attributes
 */
public class Formatting {
	public int offset, length;
	public AttributeSet attrs;
	public String toString() {
		return "[Formatting: offset=" + offset + ", length=" + length + ", Attrs: " + attrs.toString() + "]";
	}
}
