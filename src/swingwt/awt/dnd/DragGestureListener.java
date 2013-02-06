/*
   SwingWT
   Copyright(c)2003-2008, Tomer Barletz

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: tomerb@users.sourceforge.net
*/

package swingwt.awt.dnd;

import java.util.EventListener;

public interface DragGestureListener extends EventListener {
  void dragGestureRecognized(DragGestureEvent dge);
}
