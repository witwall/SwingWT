/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */
package swingwt.awt;

import swingwt.awt.event.*;



/**
 * 
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public class AWTEventMulticaster implements ComponentListener, ContainerListener, FocusListener, KeyListener,
                                            MouseListener, MouseMotionListener, WindowListener, 
                                            WindowFocusListener, WindowStateListener, ActionListener, 
                                            ItemListener, AdjustmentListener, TextListener
                                            {

    public void actionPerformed(ActionEvent e) {
    }

    public void adjustmentValueChanged(AdjustmentEvent e) {
    }

    public void componentAdded(ContainerEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentRemoved(ContainerEvent e) {
    }

    public void componentResized(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void focusGained(FocusEvent e) {
    }

    public void focusLost(FocusEvent e) {
    }

    public void itemStateChanged(ItemEvent e) {
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void textValueChanged(TextEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowGainedFocus(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowLostFocus(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
    // TODO: fix me
    public static FocusListener add(FocusListener a, FocusListener b){return a;}
    public static KeyListener add(KeyListener a, KeyListener b){return a;}
    public static MouseListener add(MouseListener a, MouseListener b){return a;}
    public static MouseMotionListener add(MouseMotionListener a, MouseMotionListener b){return a;}
    // TODO: fix me
    public static FocusListener remove(FocusListener a, FocusListener b){return a;}
    public static KeyListener remove(KeyListener a, KeyListener b){return a;}
    public static MouseListener remove(MouseListener a, MouseListener b){return a;}
    public static MouseMotionListener remove(MouseMotionListener a, MouseMotionListener b){return a;}
                                                                                                                                                    public void windowStateChanged(WindowEvent e) {
                                                                                                                                                    }

}
