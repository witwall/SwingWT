/*
   SwingWT
   Copyright(c)2003-2005, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   $Log: Events.java,v $
   Revision 1.2  2005/01/05 09:22:38  bobintetley
   Updated copyright year on source

   Revision 1.1  2004/05/12 10:18:35  bobintetley
   Unit test work, Mauve bridge and new tests


 */

package test.swingwtx.swing.JComponent;

import test.common.TestHarness;
import test.common.*;

import swingwtx.swing.*;
import swingwt.awt.*;
import swingwt.awt.event.*;

/**
 * Tests events common to all Swing compoennts
 * that descend JComponent
 *
 * @author Robin Rawson-Tetley
 */
public class Events extends MauveBridge {
    
    JLabel label = null;
    JFrame container = null;
    
    public Events(java.lang.String testName) {
        super(testName);
    }

    public void setup() {
        label = new JLabel();
        container = CommonTest.getContainer();
    }
    
    public void finish() {
        label.dispose();
        label = null;
    }
    
    public void runTest(TestHarness harness) {
    
        setup();
        
        // -- Paint events
        CommonTest.signalStartTest();
        JComponent jc = new JComponent() {
            public void paint(Graphics g) {
                CommonTest.signalReceived();
            }
        };
        container.add(jc);
        harness.check(CommonTest.signalWaitFor());
        
        // -- Action events
        CommonTest.signalStartTest();
        label.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CommonTest.signalReceived();
            }
        });
        label.processActionEvent(0);
        harness.check(CommonTest.signalWaitFor());
        
        // -- Key events
        CommonTest.signalStartTest();
        label.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                CommonTest.signalReceived();
            }
            public void keyTyped(KeyEvent e) {
                CommonTest.signalReceived();
            }
            public void keyReleased(KeyEvent e) {
                CommonTest.signalReceived();
            }
        });
        label.processKeyEvent(new KeyEvent(label, KeyEvent.KEY_PRESSED));
        harness.check(CommonTest.signalWaitFor());
        
        // -- Mouse events
        CommonTest.signalStartTest();
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                CommonTest.signalReceived();
            }
        });
        label.processMouseEvent(new MouseEvent(label, MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, 1, false));
        harness.check(CommonTest.signalWaitFor());
        
        // Focus events
        CommonTest.signalStartTest();
        label.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                CommonTest.signalReceived();
            }
        });
        label.processFocusEvent(new FocusEvent(label, FocusEvent.FOCUS_GAINED));
        harness.check(CommonTest.signalWaitFor());
        
        // clean up
        finish();
    }

}