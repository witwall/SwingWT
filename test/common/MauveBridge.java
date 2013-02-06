/*
   SwingWT
   Copyright(c)2003-2005, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   $Log: MauveBridge.java,v $
   Revision 1.2  2005/01/05 09:22:38  bobintetley
   Updated copyright year on source

   Revision 1.1  2004/05/12 10:19:11  bobintetley
   Mauve bridge


 */


package test.common;

import junit.framework.*;

/**
 * This class is a bridge that allows us to write classes that
 * look pretty much like Mauve test classes, but to execute
 * them with JUnit.
 *
 * The reason for this is that I want to be able to contribute
 * our tests back to the Mauve project, but their runtime is
 * a bit of a pain for running SwingWT and incorporating into
 * our build process.
 *
 * @author  Robin Rawson-Tetley
 */
public abstract class MauveBridge extends TestCase {
    
    public MauveBridge(java.lang.String testName) {
        super(testName);
    }
    
    /*
    public static Test suite() {
        TestSuite suite = new TestSuite(MauveBridge.class);
        return suite;
    }*/
    
    /** The test that JUnit will execute */
    public void test() {
        runTest(new TestHarness());
    }
    
    public abstract void runTest(TestHarness harness);
    
}
