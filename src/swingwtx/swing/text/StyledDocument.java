/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import swingwt.awt.*;

public interface StyledDocument extends Document {

    public Style addStyle(String nm, Style parent);
    public void removeStyle(String nm);
    public Style getStyle(String nm);
    public void setCharacterAttributes(int offset, int length, AttributeSet s, boolean replace);
    public void setParagraphAttributes(int offset, int length, AttributeSet s, boolean replace);
    public void setLogicalStyle(int pos, Style s);
    public Style getLogicalStyle(int p);
    public Element getParagraphElement(int pos);
    public Element getCharacterElement(int pos);
    public Color getForeground(AttributeSet attr);
    public Color getBackground(AttributeSet attr);
    public Font getFont(AttributeSet attr);

}

