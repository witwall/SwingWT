/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwt.awt.dnd;

/** @author Laurent Martell */
public class  DragSourceDragEvent extends DragSourceEvent {

    public DragSourceDragEvent(DragSourceContext dsc, int dropAction, 
                               int targetActions, int gestureModifiers) 
    { 
        super(dsc);
        this.targetActions = targetActions;
        this.gestureModifiers = gestureModifiers;
        this.dropAction = dropAction;
    }

    public DragSourceDragEvent(DragSourceContext dsc, int dropAction, 
                               int targetActions, int gestureModifiers, 
                               int x, int y) 
    {
        super(dsc,x,y);
        this.targetActions = targetActions;
        this.gestureModifiers = gestureModifiers;
        this.dropAction = dropAction;
    }

    int targetActions;
    public int getTargetActions() {
        return targetActions;
    }

    int dropAction;
    public int getUserAction() { 
        return dropAction; 
    }

    int gestureModifiers;
    public int getGestureModifiers() {
        return gestureModifiers;
    }
    public int getGestureModifiersEx() {
        return gestureModifiers;
    }
    
    public int getDropAction() {
        return dropAction 
            & targetActions 
            & getDragSourceContext().getSourceActions();
    }
}
