/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.text;

/**
 * Default implementation of the Caret interface for
 * descendants of JTextComponent
 *
 * @author Robin Rawson-Tetley
 */
public class DefaultCaret implements Caret {

    JTextComponent text = null;

    public DefaultCaret() {}
    public DefaultCaret(JTextComponent text) { this.text = text; }

    public void addChangeListener(swingwtx.swing.event.ChangeListener l) {}        
    public void deinstall(swingwtx.swing.text.JTextComponent c) {}
    public int getBlinkRate() { return 0; }
    public int getDot() { return text.getCaretPosition(); }
    public swingwt.awt.Point getMagicCaretPosition() { return new swingwt.awt.Point(0, 0); }
    public int getMark() { return 0; }
    public void install(swingwtx.swing.text.JTextComponent c) {}
    public boolean isSelectionVisible() { return true; }
    public boolean isVisible() { return true; }
    public void moveDot(int dot) { }
    public void paint(swingwt.awt.Graphics g) { }
    public void removeChangeListener(swingwtx.swing.event.ChangeListener l) {}
    public void setBlinkRate(int rate) {}
    public void setDot(int dot) { text.setCaretPosition(dot); }
    public void setMagicCaretPosition(swingwt.awt.Point p) { }
    public void setSelectionVisible(boolean v) { }
    public void setVisible(boolean v) { }

}
