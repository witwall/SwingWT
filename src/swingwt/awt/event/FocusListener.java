/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwt.awt.event;

public interface FocusListener extends java.util.EventListener {
    public void focusGained(FocusEvent e);
    public void focusLost(FocusEvent e);
}
