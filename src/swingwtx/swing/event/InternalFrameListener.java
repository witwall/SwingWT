/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/
package swingwtx.swing.event;


public interface InternalFrameListener {

    public void internalFrameActivated(InternalFrameEvent e);
    public void internalFrameClosed(InternalFrameEvent e);
    public void	internalFrameClosing(InternalFrameEvent e);
    public void	internalFrameDeactivated(InternalFrameEvent e);
    public void internalFrameDeiconified(InternalFrameEvent e);
    public void internalFrameIconified(InternalFrameEvent e);
    public void internalFrameOpened(InternalFrameEvent e);
    
}
