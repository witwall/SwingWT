/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwt.awt;

/**
 * @author Dan
 *
 */
public interface MenuContainer
{
    Font getFont();
 	//boolean postEvent(Event evt);
    void remove(MenuComponent comp);
}
