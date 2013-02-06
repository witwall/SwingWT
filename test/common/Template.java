/*
   SwingWT
   Copyright(c)2003-2005, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   $Log: Template.java,v $
   Revision 1.3  2005/01/05 09:22:38  bobintetley
   Updated copyright year on source

   Revision 1.2  2004/10/30 20:11:59  bobintetley
   Code cleanup

   Revision 1.1  2004/05/12 10:19:11  bobintetley
   Mauve bridge


 */

package test.common;

//import gnu.testlet.*; // This line will be uncommented when converted to Mauve

/**
 * This is a template class to show how to write a unit test
 * for SwingWT.
 * 
 * We actually write our tests in the style of Mauve, but use
 * a bridge class to run them with JUnit. This is because
 * 
 * 1. I like how Mauve lays out tests
 * 2. I want to submit our tests back to Mauve
 * 3. JUnit is a better runtime for SwingWT as we need 
 *    to modify the classpath and do other tricky stuff
 *    where Mauve is testing the base JFC
 * 
 * The general style is that you make a package for each
 * SwingWT class you want to test with the same name
 * as the class, you then create test classes inside
 * it for individual methods/functional areas of the class.
 *
 * The constructor is necessary for JUnit, however it will
 * be ignored when the test case is converted to Mauve.
 *
 * The runTest(TestHarness harness) method, will be converted
 * to have the name "test" instead when converted to Mauve,
 * the reason it can't be "test" here is that JUnit would
 * execute it and we don't want that. We want JUnit to
 * execute the generic test method in the MauveBridge
 * superclass that calls runTest() with a TestHarness
 * instance.
 *
 * @author  Robin Rawson-Tetley
 */
public class Template extends MauveBridge {
    
    public Template(String testName) {
        super(testName);
    }
    
    // When converted to mauve, this method signature
    // will become public void test(TestHarness harness)
    // the reason it isn't here is because we don't
    // want JUnit to execute this - it should execute
    // the test method in the bridge that calls this.
    public void runTest(TestHarness harness) {
        
        // some stuff
        // harness.check( boolean assertion )
        //
        // ..
        //
        // harness.check( obj1, obj2 )
        // .. etc ..
        
    }
    
}
