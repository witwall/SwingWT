/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import swingwt.awt.*;
import swingwtx.swing.event.*;

public interface Caret {
    public void install(JTextComponent c);
    public void deinstall(JTextComponent c);
    public void paint(Graphics g);
    public void addChangeListener(ChangeListener l);
    public void removeChangeListener(ChangeListener l);
    public boolean isVisible();
    public void setVisible(boolean v);
    public boolean isSelectionVisible();
    public void setSelectionVisible(boolean v);
    public void setMagicCaretPosition(Point p);
    public Point getMagicCaretPosition();
    public void setBlinkRate(int rate);
    public int getBlinkRate();
    public int getDot();
    public int getMark();
    public void setDot(int dot);
    public void moveDot(int dot);
}
