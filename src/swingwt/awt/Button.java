/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt;

import swingwtx.swing.JButton;

public class Button extends AWTSwingWrapper {
    
    public Button() { swingPeer = new JButton(); }
    public Button(String title) { swingPeer = new JButton(title); }
    
    private JButton getSwingPeer() { return (JButton) swingPeer; }
    
    public String getLabel() { return getSwingPeer().getText(); }
    public void setLabel(String label) { getSwingPeer().setText(label); }
}
