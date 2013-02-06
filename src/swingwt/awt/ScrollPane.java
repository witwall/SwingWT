/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt;

public class ScrollPane extends swingwtx.swing.JScrollPane {
    
    public static final int SCROLLBARS_AS_NEEDED = 0;
    public static final int SCROLLBARS_AS_ALWAYS = 1;
    public static final int SCROLLBARS_AS_NEVER = 2;
    public ScrollPane() { super(); }
    public ScrollPane(Component component) { super(component); }
    public ScrollPane(int displayPolicy) { super(); }
    public ScrollPane(int vsbPolicy, int hsbPolicy) { super(vsbPolicy, hsbPolicy); }
    public ScrollPane(Component component, int vsbPolicy, int hsbPolicy) { super(component, vsbPolicy, hsbPolicy); }
}
