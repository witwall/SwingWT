/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

public class Scrollbar extends swingwtx.swing.JScrollBar {
    public Scrollbar() { super(); }
    public Scrollbar(int orientation) { super(orientation); }
    public Scrollbar(int orientation, int value, int extent, int min, int max) { super(orientation, value, extent, min, max); }
}
