/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.awt.print;

import swingwt.awt.Graphics;

/**
 * @author David Jung 
 */
public interface Printable
{
  public static int NO_SUCH_PAGE = 1;
  public static int PAGE_EXISTS = 0;

  public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException;

}
