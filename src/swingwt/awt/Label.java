/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt;

public class Label extends swingwtx.swing.JLabel {
    public final static int CENTER = swingwtx.swing.JLabel.CENTER;
    public final static int LEFT = swingwtx.swing.JLabel.LEFT;
    public final static int RIGHT = swingwtx.swing.JLabel.RIGHT;
    public Label() { super(); }
    public Label(String text) { super(text); }
    public Label(String text, int align) { super(text); super.setHorizontalAlignment(align); }
    public void setAlignment(int alignment) { super.setHorizontalAlignment(alignment); };
    public int getAlignment() { return super.getHorizontalAlignment(); };
}
