/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.text;

import swingwtx.swing.*;

/**
 * View superclass that maps Document models between viewable
 * components. Basically assumes your component is a descendant
 * of JTextComponent (why it's vitally important for subclasses
 * to override functionality, even if they aren't that similar).
 *
 * @author Robin Rawson-Tetley
 */
public abstract class View implements SwingConstants {
    
    protected Document doc;
    
    public View(Document doc) { this.doc = doc; }

    public boolean isVisible() { return true; }
    public float getAlignment(int axis) { return (float) 0.5; }
    public int getViewCount() { return 0; }
    public View getView(int n) { return null; }

}
