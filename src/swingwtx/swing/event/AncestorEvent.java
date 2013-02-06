/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

 
 */

package swingwtx.swing.event;

import swingwt.awt.*;
import swingwtx.swing.*;
public class AncestorEvent extends AWTEvent 
{
    Container ancestor;
    Container ancestorParent;
    public static final int ANCESTOR_ADDED = 1;
    public static final int ANCESTOR_REMOVED = 2;
    public static final int ANCESTOR_MOVED = 3;
    
    public AncestorEvent(JComponent sourceComponent, int id, Container ancestorCont, Container ancestorParentCont) 
    {
        super(sourceComponent, id);
        this.ancestorParent = ancestorParentCont;
        this.ancestor = ancestorCont;
    }
    public Container getAncestor() 
    {
        return ancestor;
    }
    public Container getAncestorParent() 
    {
        return ancestorParent;
    }
    public JComponent getComponent() 
    {
        return (JComponent)getSource();
    }
}
