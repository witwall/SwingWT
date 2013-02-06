/*
   SwingWT
   Copyright(c)2003-2008, Tomer Barletz

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: tomerb@users.sourceforge.net

*/

package swingwt.awt.datatransfer;

import java.io.IOException;

public interface Transferable {
  public DataFlavor[] getTransferDataFlavors();
  public boolean isDataFlavorSupported(DataFlavor flavor);
  public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException;
}
