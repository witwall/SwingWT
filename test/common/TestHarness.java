/*
   SwingWT
   Copyright(c)2003-2005, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   $Log: TestHarness.java,v $
   Revision 1.2  2005/01/05 09:22:38  bobintetley
   Updated copyright year on source

   Revision 1.1  2004/05/12 10:19:11  bobintetley
   Mauve bridge


 */

package test.common;

import junit.framework.*;

/**
 * Implementation of Mauve TestHarness that
 * maps useful methods to JUnit calls
 *
 * @author  Robin Rawson-Tetley
 */
public class TestHarness {
    public void check(boolean b) { TestCase.assertTrue(b); }
    public void check(Object a, Object b) { TestCase.assertEquals(a, b); }
    public void check(int a, int b) { TestCase.assertEquals(a, b); }
}