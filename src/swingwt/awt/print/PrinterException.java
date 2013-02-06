/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/
package swingwt.awt.print;

import java.lang.Exception;

/**
 * @author David Jung 
 */
public class PrinterException extends Exception
{
  public PrinterException()
  {}

  public PrinterException(String msg)
  {
    super(msg);
  }
}
