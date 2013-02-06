package swingwt.awt.print;

/**
 *
 *  A simple empty interface  to be able to compile
 *  //050705 Niklas
 */
public interface Pageable 
{

    int UNKNOWN_NUMBER_OF_PAGES = -1;
    int getNumberOfPages();
    PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException;
    Printable getPrintable(int pageIndex)	throws IndexOutOfBoundsException;
}

