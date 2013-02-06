/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.print;

public abstract class PrinterJob {
    
    public void print() throws PrinterException {};
    public static PrinterJob getPrinterJob()
    {
    	return null;
    }
    public abstract void setPageable(Pageable document);
    public abstract PageFormat defaultPage();
    public abstract PageFormat pageDialog(PageFormat page);
    public abstract boolean printDialog();
    public abstract void setPrintable(Printable printable,PageFormat format );
    public abstract void defaultPage(PageFormat format);
}
