/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */
package swingwt.awt.dnd;

import swingwt.awt.Component;
import swingwt.awt.HeadlessException;
import swingwt.awt.datatransfer.FlavorMap;


/** @author Laurent Martell */
public class DropTarget {
    public DropTarget() {
    }

    public DropTarget(Component c,
                      int ops,
                      DropTargetListener dtl,
                      boolean act,
                      FlavorMap fm)
        throws HeadlessException
    {
        /* TODO */
    }

    public DropTarget(Component c,
                      int ops,
                      DropTargetListener dtl,
                      boolean act)
        throws HeadlessException
    {
        /* TODO */
    }

    public DropTarget(Component c,
                      int ops,
                      DropTargetListener dtl)
        throws HeadlessException
    {
        /* TODO */
    }

    public DropTarget(Component c,
                      DropTargetListener dtl)
        throws HeadlessException
    {
        /* TODO */
    }

    public void addDropTargetListener(DropTargetListener listener) throws java.util.TooManyListenersException {
    }
}
