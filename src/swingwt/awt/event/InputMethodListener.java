/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwt.awt.event;

import java.util.EventListener;

/**
 * @author Dan Naab
 */
public interface InputMethodListener extends EventListener
{
    void inputMethodTextChanged(InputMethodEvent event);
    void caretPositionChanged(InputMethodEvent event);
}
