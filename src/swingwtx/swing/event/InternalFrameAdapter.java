/**
* SwingWT
* Copyright(c)2003-2008, R. Rawson-Tetley
*/

package swingwtx.swing.event;

/**
 * 
 * 
 * @author Niklas Gustafsson
 */
public abstract class InternalFrameAdapter implements InternalFrameListener {
   
    public void internalFrameOpened(InternalFrameEvent e) {}
    public void internalFrameClosing(InternalFrameEvent e) {}
    public void internalFrameClosed(InternalFrameEvent e) {}
    public void internalFrameIconified(InternalFrameEvent e) {}
    public void internalFrameDeiconified(InternalFrameEvent e) {}
    public void internalFrameActivated(InternalFrameEvent e) {}
    public void internalFrameDeactivated(InternalFrameEvent e) {}
}
