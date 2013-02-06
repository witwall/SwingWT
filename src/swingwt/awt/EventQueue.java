/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */


package swingwt.awt;

import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingWTUtils;

/**
 * Dumb wrapper around other functions we have. Implements
 * a bunch of statics that some code may rely on.
 *
 * The real "meat" of what this class is supposed to do
 * is taken care of inside SwingWTUtils (and mapped over
 * SWT dispatch mechanisms)
 *
 * @author  Robin Rawson-Tetley
 */
public class EventQueue {
    
    public class EventDispatchRunnable implements Runnable {
        private final AWTEvent event;
        
        public EventDispatchRunnable(AWTEvent event) {
            super();
            this.event = event;
        }
        
        public void run() {
            Object src = event.getSource();
            
            // TODO
            // e.g. ActiveEvents?
            if (src instanceof Component) {
                ((Component)src).dispatchEvent(event);
            } else if (src instanceof MenuComponent) {
                ((MenuComponent)src).dispatchEvent(event);
            }
        }
    }
    
    static EventQueue singleton = new EventQueue();
    
    public EventQueue() {
    }
    
    public static boolean isDispatchThread(Thread t) {
        return t.equals(SwingWTUtils.getDisplay().getThread());
    }
    
    public static AWTEvent getCurrentEvent() {
        return null;
    }
    
    public static long getMostRecentEventTime() {
        return System.currentTimeMillis();
    }
    
    public static void invokeAndWait(Runnable run) throws InterruptedException, java.lang.reflect.InvocationTargetException {
        SwingUtilities.invokeAndWait(run);
    }
    
    public static void invokeLater(Runnable run) {
        SwingUtilities.invokeLater(run);
    }
    
    public static boolean isDispatchThread() {
        return isDispatchThread(Thread.currentThread());
    }
    
    public void postEvent(AWTEvent awtEvent) {
        SwingUtilities.invokeLater(new EventDispatchRunnable(awtEvent));
    }
    
    public Object peekEvent(int i) {
        // TODO
        return null;
    }
    
    public AWTEvent getNextEvent() {
        // TODO
        return null;
    }
    
    public Object peekEvent() {
        // TODO
        return null;
    }
    
}
