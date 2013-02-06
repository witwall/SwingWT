/*
 * SwingWT demo. Demonstrates how SwingWT deals with MDI
 * interfaces.
 *
 * @author R. Rawson-Tetley
 *
 *
 */


package demo;

import swingwt.awt.*;
import swingwtx.swing.*;
import swingwt.awt.event.*;
import swingwtx.swing.event.*;

public class MDI extends JFrame {
    
    public MDI() {
        
        final JDesktopPane jdp = new JDesktopPane();
        this.setLayout(new BorderLayout());
        this.add(jdp, BorderLayout.CENTER);
        
        JMenuBar bar = new JMenuBar();
        JMenu mnuTest = new JMenu("Test");
        JMenuItem mnuTestAdd = new JMenuItem("Add Window");
        mnuTestAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jdp.add(new SDIForm("Test"));
            }
        });
        mnuTest.add(mnuTestAdd);
        bar.add(mnuTest);
        setJMenuBar(bar);
        
        show();
        
    }
    
    public static void main(String[] args) {
       if (SwingWTUtils.isMacOSX()) {
	    SwingWTUtils.initialiseMacOSX(new Runnable() {
		public void run() {
                    new MDI();
		}
	    });
	}
	else
	    new MDI();
    }
    
}

class SDIForm extends JInternalFrame {
    private static int counter = 1;
    public SDIForm(String title) { this(); setTitle(title); }
    public SDIForm() {
        this.setTitle("An SDI Form");
        this.setFrameIcon(new ImageIcon(getClass().getResource("/demo/pic.gif")));
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints(0, 0, 100, 100, 1, 1, GridBagConstraints.PAGE_END, 52, new Insets(0, 0, 10, 20), 5, 5);
        getContentPane().add(new JLabel(getTitle() + " " + counter), c);
        counter++;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addInternalFrameListener(new InternalFrameListener() {
                public void internalFrameActivated(InternalFrameEvent e) {}
                public void internalFrameClosed(InternalFrameEvent e) {}
                public void internalFrameClosing(InternalFrameEvent e) {
                    checkClose();
                }
                public void internalFrameDeactivated(InternalFrameEvent e) {}
                public void internalFrameDeiconified(InternalFrameEvent e) {}
                public void internalFrameIconified(InternalFrameEvent e) {}
                public void internalFrameOpened(InternalFrameEvent e) {}
        });
    }
    
    public void checkClose() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Sure you wish to close?"))
            dispose();
    }
    
}
