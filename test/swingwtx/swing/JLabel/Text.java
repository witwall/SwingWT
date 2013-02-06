/*
   SwingWT
   Copyright(c)2003-2005, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   $Log: Text.java,v $
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
 * Test class for JLabel Text
 *
 * @author Robin Rawson-Tetley
 */
public class Text extends MauveBridge {
    
    public Text(java.lang.String testName) {
        super(testName);
    }

    // Tests =============================================

    private JLabel label = null;
    private JFrame container = null;

    
    /**
     * Verifies that the label can store and
     * return text in both a realised and unrealised state.
     */
    public void runTest(TestHarness harness) {
        
        setup();
        
        final String TEST = "Test Text";
        
        label.setText(TEST);
        harness.check(label.getText(), TEST);
        container.add(label);
        harness.check(label.getText(), TEST);
        
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
