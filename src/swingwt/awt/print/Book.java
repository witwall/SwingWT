/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.awt.print;
import java.util.*;
/**
 * 
 * A dummy implementation of a Book.
 * The Pageable/Printable classes is just dummy stubs for compiling
 * Replace this.
 *
 */
public class Book implements Pageable
{
  private Vector pages = new Vector();
  public Book()
  {
  }
  public int getNumberOfPages()
  {
	  return pages.size();
  }
  public PageFormat getPageFormat(int index){return getPage(index).getPageFormat();}
  public Printable getPrintable(int index){return getPage(index).getPrintable();}
  private Page getPage(int index){return (Page) pages.elementAt(index);}
  public void append(Printable printable,PageFormat format)
  {
	  pages.add(new Page(printable,format));
  }
  // Inner dummy class to handle a sigle page
  private class Page 
  {
		private PageFormat format;
		private Printable printable;
		Page(Printable printable, PageFormat format) 
		{
		    this.format = format;
		    this.printable = printable;
		}
		Printable getPrintable(){return printable;}
		PageFormat getPageFormat(){return format;}
   }
  

}
