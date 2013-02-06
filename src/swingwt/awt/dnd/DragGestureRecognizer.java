/*
   SwingWT
   Copyright(c)2003-2008, Tomer Barletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net
 */

package swingwt.awt.dnd;

import swingwt.awt.event.InputEvent;
import swingwt.awt.Component;
import swingwt.awt.Point;

import java.util.TooManyListenersException;
import java.util.ArrayList;

import org.eclipse.swt.dnd.DragSource;

public abstract class DragGestureRecognizer {
    
    protected DragGestureRecognizer(DragSource ds, Component c, int sa, DragGestureListener dgl) {
        
        super();
        
        dragSource = ds;
        component = c;
        sourceActions = sa &
        (DnDConstants.ACTION_COPY_OR_MOVE | DnDConstants.ACTION_LINK);
        
        try {
            if (dgl != null) addDragGestureListener(dgl);
        }
        catch (Exception e) {}
    }
    
    protected DragGestureRecognizer(DragSource ds, Component c, int sa) {
        this(ds, c, sa, null);
    }
    
    protected DragGestureRecognizer(DragSource ds, Component c) {
        this(ds, c, DnDConstants.ACTION_NONE);
    }
    
    protected DragGestureRecognizer(DragSource ds) {
        this(ds, null);
    }
    
    protected abstract void registerListeners();
    
    protected abstract void unregisterListeners();
    
    public DragSource getDragSource() {
        return dragSource;
    }
    
    public synchronized Component getComponent() {
        return component;
    }
    
    public synchronized void setComponent(Component c) {
        if (component != null && dragGestureListener != null)
            unregisterListeners();
        
        component = c;
        
        if (component != null && dragGestureListener != null)
            registerListeners();
    }
    
    public synchronized int getSourceActions() {
        return sourceActions;
    }
    
    public synchronized void setSourceActions(int actions) {
        sourceActions = actions &
        (DnDConstants.ACTION_COPY_OR_MOVE | DnDConstants.ACTION_LINK);
    }
    
    public InputEvent getTriggerEvent() {
        return events.isEmpty() ? null : (InputEvent) events.get(0);
    }
    
    public void resetRecognizer() {
        events.clear();
    }
    
    public synchronized void addDragGestureListener(DragGestureListener dgl) throws
    TooManyListenersException {
        if (dragGestureListener != null)
            throw new TooManyListenersException();
        else {
            dragGestureListener = dgl;
            
            if (component != null) registerListeners();
        }
    }
    
    public synchronized void removeDragGestureListener(DragGestureListener dgl) {
        if (dragGestureListener == null || !dragGestureListener.equals(dgl))
            throw new IllegalArgumentException();
        else {
            dragGestureListener = null;
            
            if (component != null) unregisterListeners();
        }
    }
    
    protected synchronized void fireDragGestureRecognized(int dragAction, Point p) {
        try {
            if (dragGestureListener != null) {
                dragGestureListener.dragGestureRecognized(new DragGestureEvent(this,
                dragAction, p, events));
            }
        }
        finally {
            events.clear();
        }
    }
    
    protected synchronized void appendEvent(InputEvent awtie) {
        events.add(awtie);
    }
    
    protected DragSource dragSource;
    
    protected Component component;
    
    protected transient DragGestureListener dragGestureListener;
    
    protected int sourceActions;
    protected ArrayList events = new ArrayList(1);
    
}
