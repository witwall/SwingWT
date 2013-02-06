/*
   SwingWT
   Copyright(c)2003-2008, Tomer Barletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net
 */

package swingwt.awt.dnd;

import swingwt.awt.Component;
import swingwt.awt.Cursor;

import swingwt.awt.Image;
import swingwt.awt.Point;

import swingwt.awt.event.InputEvent;

import swingwt.awt.datatransfer.Transferable;

import java.util.EventObject;
import java.util.List;
import java.util.Iterator;

import org.eclipse.swt.dnd.DragSource;

public class DragGestureEvent extends EventObject {
    
    private List events;
    private DragSource dragSource;
    private Component component;
    private Point origin;
    private int action;
    
    public DragGestureEvent(DragGestureRecognizer dgr, int act, Point ori, List evs) {
        super(dgr);
        events = evs;
        action = act;
        origin = ori;
    }
    
    public DragGestureRecognizer getSourceAsDragGestureRecognizer() {
        return (DragGestureRecognizer) getSource();
    }
    
    public Component getComponent() {
        return component;
    }
    
    public DragSource getDragSource() {
        return dragSource;
    }
    
    public Point getDragOrigin() {
        return origin;
    }
    
    public Iterator iterator() {
        return events.iterator();
    }
    
    public Object[] toArray() {
        return events.toArray();
    }
    
    public Object[] toArray(Object[] array) {
        return events.toArray(array);
    }
    
    public int getDragAction() {
        return action;
    }
    
    public InputEvent getTriggerEvent() {
        return getSourceAsDragGestureRecognizer().getTriggerEvent();
    }
    
    public void startDrag(Cursor dragCursor, Transferable transferable) throws
    InvalidDnDOperationException {
        /**
         * @todo implement the "real" DragSource
         */
        //dragSource.startDrag(this, dragCursor, transferable, null);
    }
    
    public void startDrag(Cursor dragCursor, Transferable transferable, swingwt.awt.dnd.DragSourceListener dsl) 
        throws InvalidDnDOperationException {
        /**
         * @todo implement the "real" DragSource
         */
        //dragSource.startDrag(this, dragCursor, transferable, dsl);
    }
    
    public void startDrag(Cursor dragCursor, Image dragImage, Point imageOffset,
        Transferable transferable, swingwt.awt.dnd.DragSourceListener dsl) throws InvalidDnDOperationException {
        /**
         * @todo implement the "real" DragSource
         */
        //dragSource.startDrag(this, dragCursor, dragImage, imageOffset, transferable, dsl);
    }
    
    
}
