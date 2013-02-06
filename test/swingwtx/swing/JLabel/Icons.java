/*
   SwingWT
   Copyright(c)2003-2005, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   $Log: Icons.java,v $
   Revision 1.3  2005/01/05 09:22:38  bobintetley
   Updated copyright year on source

   Revision 1.2  2004/10/30 20:11:59  bobintetley
   Code cleanup

   Revision 1.1  2004/05/12 10:18:35  bobintetley
   Unit test work, Mauve bridge and new tests


 */

package test.swingwtx.swing.JLabel;

import test.common.TestHarness;
import test.common.*;

import swingwtx.swing.*;

/**
 * Test class for JLabel Icons
 *
 * @author Robin Rawson-Tetley
 */
public class Icons extends MauveBridge {
    
    public Icons(java.lang.String testName) {
        super(testName);
    }

    // Tests =============================================

    private JLabel label = null;
    private JFrame container = null;
    
    /**
     * Verifies that the label can store and
     * return icons in both a realised and unrealised state.
     */
    public void runTest(TestHarness harness) {
        
        setup();
        
        final Icon ICON = new ImageIcon(getClass().getResource("/demo/pic.gif"));
        
        label.setIcon(ICON);
        harness.check(label.getIcon(), ICON);
        container.add(label);
        harness.check(label.getIcon(), ICON);
        
        finish();
    }
    
    public void setup() {
        label = new JLabel();
        container = CommonTest.getContainer();
    }
    
    protected void finish() {
        label.dispose();
        label = null;
    }    
    
}
