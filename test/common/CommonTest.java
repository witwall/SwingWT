/*
   SwingWT
   Copyright(c)2003-2005, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   $Log: CommonTest.java,v $
   Revision 1.3  2005/01/05 09:22:38  bobintetley
   Updated copyright year on source

   Revision 1.2  2004/10/30 20:11:59  bobintetley
   Code cleanup

   Revision 1.1  2004/05/12 10:19:11  bobintetley
   Mauve bridge


 */

package test.common;

/**
 * Useful class containing various routines to assist
 * with test cases.
 *
 * @author  Robin Rawson-Tetley
 */
public abstract class CommonTest {

    
    
    /// Signal switching --- this stuff is related to allowing
    /// a test case to put a callback in (eg: test an event or
    /// overridden callback method) and then block until it
    /// is called, or a certain amount of time has elapsed.
    /// -------------------------------------------------------
    
    private static boolean signal = false;
    private static synchronized boolean getSignal() { return signal; }
    private static synchronized void setSignal(boolean b) { signal = b; }
    
    /** Tells this class that we are starting a test that requires
     *  signals. */
    public static void signalStartTest() {
        setSignal(false);
    }
    /** Tells this class that we got the signal successfully, this
     *  is so a success can be returned to the test.
     */
    public static void signalReceived() {
        setSignal(true);
    }
    /** Blocks and waits for a successful signal. If 2 seconds have
     *  elapsed and no signal received, returns false.
     */
    public static boolean signalWaitFor() {
        long now = System.currentTimeMillis();
        long stopAt = now + 2000; // 2 Seconds to work - plenty of time
        while (true) {
            
            // We got the signal - return that all is ok
            if (getSignal() == true) {
                return true;
            }
            
            // Ran out of time - return false
            if (System.currentTimeMillis() > stopAt) {
                return false;
            }
            
            // Wait a bit
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {}
            
        }
    }
    
    /// Stuff for managing a single frame container for all
    /// tests - prevents all the destroying/creating done with
    /// a setup/teardown.
    /// Will also add a shutdown hook to destroy the container
    /// when the VM closes
    private static swingwtx.swing.JFrame frame = null;
    /** Returns the shared JFrame for use with test cases.
     *  If it hasn't been created, creates it and adds a VM
     *  shutdown hook to destroy it.
     */
    public static swingwtx.swing.JFrame getContainer() {
        if (frame == null) {
            frame = new swingwtx.swing.JFrame();
            frame.setLocation(0, 0);
            frame.setSize(32, 32);
            frame.show();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    frame.dispose();
                    frame = null;
                }
            });
        }
        return frame;
    }
    
    
}
