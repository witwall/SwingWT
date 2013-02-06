/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.font;

import java.text.BreakIterator;
import java.text.AttributedCharacterIterator;
import swingwt.awt.font.FontRenderContext;

/**
 * 
 * A stub for  print-lines used in  swingwt.awt.print package
 * 
 * @author Niklas Gustafsson
 *
 */
public final class LineBreakMeasurer {
    
    public LineBreakMeasurer(AttributedCharacterIterator text, FontRenderContext frc) 
    {

    }
    public LineBreakMeasurer(AttributedCharacterIterator text,
                             BreakIterator breakIter,
                             FontRenderContext frc) 
    {
    	
    }
    public int nextOffset(float wrappingWidth) 
    {
    	return 0;
    }
    public int nextOffset(float wrappingWidth, int offsetLimit,
                          boolean requireNextWord) 
    {
        return 0;
    }
    public TextLayout nextLayout(float wrappingWidth) 
    {
    	return null;
    }
    public TextLayout nextLayout(float wrappingWidth, int offsetLimit,
                                 boolean requireNextWord) 
    {
    	return null;
    }
    public int getPosition() 
    {
        return 0;
    }
    public void setPosition(int newPosition) 
    {
    }
    public void insertChar(AttributedCharacterIterator newParagraph,
                           int insertPos) 
    {
    }
    public void deleteChar(AttributedCharacterIterator newParagraph,
                           int deletePos) 
    {
    }
}

