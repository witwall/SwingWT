/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwt.awt.dnd;

import java.util.EventListener;

/** @author Laurent Martell */
public interface DropTargetListener extends EventListener {
    void dragEnter(DropTargetDragEvent event);
    void dragOver(DropTargetDragEvent event);
    void dropActionChanged(DropTargetDragEvent event);
    void dragExit(DropTargetEvent event);
    void drop(DropTargetDropEvent event);
}
