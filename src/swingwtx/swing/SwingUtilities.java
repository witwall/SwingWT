/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
*/

package swingwtx.swing;

import org.eclipse.swt.widgets.Control;

import swingwt.awt.Component;
import swingwt.awt.Container;
import swingwt.awt.FontMetrics;
import swingwt.awt.Point;
import swingwt.awt.Window;
import swingwtx.accessibility.Accessible;

public abstract class SwingUtilities implements SwingConstants {
    
    public static void invokeAndWait(Runnable run) throws InterruptedException, java.lang.reflect.InvocationTargetException {
        invokeSync(run);
    }
    
    public static void invokeLater(final Runnable run) {
        invokeAsync(new Runnable() {
            public void run() {
                try {
                    run.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Indicate if the calling thread is the event dispatch thread.
     * 
     * @return true if the calling thread is the event dispatch thread.
     */
     public static boolean isEventDispatchThread() {
	 SwingWTUtils.checkEventDispatcher();
	 return Thread.currentThread().equals(SwingWTUtils.getDisplay().getThread());
     }
    
    /**
     * Invokes a call asynchronously.
     */
    public static void invokeAsync(Runnable run) {
        SwingWTUtils.checkEventDispatcher();
        SwingWTUtils.getDisplay().asyncExec(run);
    }
    
    /**
     * Invokes a call synchronously. Since we use this a lot for SwingWT
     * internals, we check to see if we are already on the dispatch thread
     * (which is likely in many places) - if we are, we can just run
     * the code right off without queuing it.
     */
    public static void invokeSync(Runnable run) {
        SwingWTUtils.checkEventDispatcher();
        if (Thread.currentThread().equals( SwingWTUtils.getDisplay().getThread() ))
            run.run();
        else
            SwingWTUtils.getDisplay().syncExec(run);
    }

    /**
     * This class is used by invokeSyncReturn so synchronized
     * tasks can return objects
     * 
     * @author scott
     *
     */
    public static abstract class SwingWTRunnable implements Runnable {
    	
    	/**
    	 * The value to be returned.
    	 */
    	private Object retValue;
    	
    	/**
    	 * Simply keeps the value returned by exec.
    	 */
    	public void run(){
    		retValue = exec();
    	}

    	/**
    	 * Implementations should return by this method the object
    	 * they wish to be returned by sync().
    	 *
    	 * @see SwingUtilities#sync(SwingWTRunnable)
    	 * @return
    	 */
    	public abstract Object exec();    	
    }
    
    /**
     * This method can be used so synchronized tasks can return objects.
     * This makes it possible remove the temporary object fields that need
     * to be used otherwise. Those fields are not thread safe.
     * 
     * @param task a SwingWTRunnable object.
     * @return the object returned by the task's exec() method.
     */
    public static Object sync(SwingWTRunnable task) {
    	invokeSync(task);
    	return task.retValue;
    }
    
    
    public static int computeStringWidth(FontMetrics fontMetrics, String string) {
	if (string == null || string.length() == 0) {
	    return 0;
	}
	return fontMetrics.stringWidth(string);
    }
    
    /**
     * Invokes a call in a specified interval of time
     */
    public static void invokeIn(Runnable run, int milliseconds) {
        SwingWTUtils.checkEventDispatcher();
        SwingWTUtils.getDisplay().timerExec(milliseconds, run);
    }
    
    public static void updateComponentTreeUI(Component c) {
        if (c instanceof Container) ((Container) c).invalidate();    
        c.repaint();
    }
    
    /** 
     *  Searches the component hierarchy above comp for the first
     *  instance of class type c
     */
    public static Container getAncestorOfClass(Class c, Component comp) {
        if(comp == null || c == null)
            return null;
        Container parent = comp.getParent();
        while(parent != null && !(c.isInstance(parent)))
            parent = parent.getParent();
        return parent;
    }
    
    // TODO: Implement Accessible support
    public static int getAccessibleChildrenCount(Component component) { return 0; }
    public static Accessible getAccessibleChild(Component component, int i) { 
        return component.getAccessibleContext().getAccessibleChild(i); 
    }

    public static void convertPointToScreen(Point p, Component c)
    {
	        // No conversion 
    }
    // Always return null
    public static InputMap getUIInputMap(JComponent component,int condition)
    {
    	
    	InputMap iMap = new InputMap();
    	return iMap;
    }
//  Always return point (0,0)
    public static Point convertPoint(Component source, int x, int y, Component destination)
    {
    	return new Point(0,0);
    }
    // Always return point (0,0)
    public static Point convertPoint(Component source, Point aPoint, Component destination)
    {
    	return new Point(0,0);
    }
    
    public static Component getDeepestComponentAt(Container parent, int x, int y)
    {
    	return null;
    }
    public static Window getWindowAncestor(Component c) 
    {
    	Window w = null;
    	for(Container cont = c.getParent(); cont != null; cont = cont.getParent()) {
            if (cont instanceof Window) 
            {
                w = (Window)cont;
                break;
            }
        }
        return w;
    }
    public static Window windowForComponent(Component c) 
    {
        return getWindowAncestor(c);
    }
    public static void convertPointFromScreen(Point p, Component c)
    {
    	
    }
    


}
