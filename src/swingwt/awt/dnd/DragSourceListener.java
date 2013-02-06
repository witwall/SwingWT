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
public interface DragSourceListener extends EventListener {
    void dragEnter(DragSourceDragEvent dsde);
    void dragOver(DragSourceDragEvent dsde);
    void dropActionChanged(DragSourceDragEvent dsde);
    void dragExit(DragSourceEvent dse);
    void dragDropEnd(DragSourceDropEvent dsde);
}
