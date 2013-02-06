package demo;

import javax.swing.*;

/**
 * A Simple frame that demonstrates dynamic conversion of 
 * Swing to SwingWT on the fly using code kindly donated by
 * Fernando Petrola from the WebOnSwing (http://webonswing.sf.net)
 * project.
 *
 * @author  Robin Rawson-Tetley
 */
public class SimpleFrame extends JFrame {
    
    /** Creates a new instance of SimpleFrame */
    public SimpleFrame() {
        
        this.setTitle("Simple Frame");
        this.setBounds(10, 10, 200, 200);
        
        this.getContentPane().add(new JLabel("Test:"), java.awt.BorderLayout.WEST);
        this.getContentPane().add(new JButton("Test"));
        this.getContentPane().add(new JLabel("Dynamically converted to SwingWT at runtime"), java.awt.BorderLayout.SOUTH);
        
        this.show();
        
    }
    
    public static void main(String[] args) {
        new SimpleFrame();
    }
    
}
