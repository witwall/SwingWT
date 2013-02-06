/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.font;



import java.text.AttributedCharacterIterator;
import java.util.Map;

import swingwt.awt.*;

public class TextLayout implements Cloneable {
	// Probably this class is best implemented with the draw2d text
	// features
	
	public TextLayout(AttributedCharacterIterator text, FontRenderContext frc)  {
		
	}
	
	public TextLayout(String string, Font font, FontRenderContext frc) {
		
	}
	
	public TextLayout(String string, Map attributes, FontRenderContext frc) {
		
	}
	
	public byte getCharacterLevel(int index) {
		return 0;
	}
	// Dummy method that is always returning 0;
	public float getDescent()
	{
		return 0;
	}
	// Dummy method that is always returning 0;
	public float getAscent()
	{
		return 0;
	}
	// Dummy method that is always returning 0;
	public float getLeading()
	{
		return 0;
	}
	// Not implemented now
	public void draw(Graphics2D g2, float x, float y)
	{
		
	}
	
	
}
