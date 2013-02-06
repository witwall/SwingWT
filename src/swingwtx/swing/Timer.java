/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;

import swingwt.awt.event.*;

import java.util.*;

public class Timer {
    
    protected Vector actionListeners = new Vector();
    protected int delay = 100;
    protected int initialDelay = 100;
    protected boolean running = false;
    protected boolean coalesce = true;
    protected boolean repeats = true;
    
    protected TimerThread timerThread = null;
    
    public Timer(int delay, ActionListener l) {
        this.delay = delay;
        this.initialDelay = delay;
        addActionListener(l);
    }
    
    public synchronized void addActionListener(ActionListener l) {
        actionListeners.add(l);    
    }
    
    public synchronized void removeActionListener(ActionListener l) {
        actionListeners.remove(l);    
    }
    
    protected synchronized void fireActionPerformed(ActionEvent e) {
        Iterator i = actionListeners.iterator();
        while (i.hasNext()) {
            ((ActionListener) i.next()).actionPerformed(e);
        }
    }
    
    public synchronized int getDelay() { return delay; }
    public synchronized int getInitialDelay() { return initialDelay; }
    public synchronized boolean isCoalesce() { return coalesce; }
    public synchronized boolean isRepeats() { return repeats; }
    public synchronized boolean isRunning() { return running; }
    public synchronized void restart() { delay = initialDelay; stop(); start(); }
    public synchronized void setCoalesce(boolean b) { coalesce = b; }
    public synchronized void setDelay(int delay) { this.delay = delay; }
    public synchronized void setInitialDelay(int delay) { this.initialDelay = delay; }
    public synchronized void setRepeats(boolean b) { repeats = b; }
    protected synchronized void setRunning(boolean b) { running = b; }
    
    public synchronized void start() {
        if (!isRunning()) {
            setRunning(true);
            timerThread = new TimerThread(this);
            timerThread.start();
	}
    }
    
    public synchronized void stop() {
        setRunning(false);
        timerThread = null;
    }
    
}

class TimerThread extends Thread {
    Timer timer = null;
    public TimerThread(Timer t) { this.timer = t; }
    public void run() {
        while (timer.isRunning()) {
            try {
                Thread.sleep(timer.getDelay());
            }
            catch (Exception e) {
            }
            // Fire the event on the dispatch thread
            SwingUtilities.invokeSync( new Runnable() {
                public void run() {
                    timer.fireActionPerformed(new ActionEvent(timer, 0));
                }
            });
            if (!timer.isRepeats()) break;
        }
    }
}
