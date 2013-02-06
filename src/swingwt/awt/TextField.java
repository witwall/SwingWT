/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import swingwtx.swing.JTextField;

public class TextField extends TextComponent {
    
    public TextField() { this(0); }
    public TextField(int columns) { this("", columns); }
    public TextField(String text) { this(text, 0); }
    public TextField(String text, int columns) {
        swingPeer = new JTextField(text, columns);
    }
    
    private JTextField getSwingPeer() { return (JTextField) swingPeer; }
    
    public int getColumns() { return getSwingPeer().getColumns(); }
    public void setColumns(int columns) { getSwingPeer().setColumns(columns); }
    
    /**
     * TODO: Echo chars not currently supported as they don't map to JTextField.
     * Was removed in favor of the separate JPasswordField component.
     */
    public boolean echoCharIsSet() { return false; };
    public void setEchoChar(char c) {}
    public char getEchoChar() { return 0; }
    public void setEchoCharacter(char c) {}
    
    // TODO: Fill this out once TextField supports row/column methods
    public Dimension getMinimumSize(int columns) { return getMinimumSize(); }
    public Dimension getPreferredSize(int columns) { return getPreferredSize(); }
    public Dimension minimumSize(int columns) { return minimumSize(); }
    public Dimension preferredSize(int columns) { return preferredSize(); }
}
