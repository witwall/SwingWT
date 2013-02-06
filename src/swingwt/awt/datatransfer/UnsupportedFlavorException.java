/*
   SwingWT
   Copyright(c)2003-2008, Tomer Barletz

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: tomerb@users.sourceforge.net
*/

package swingwt.awt.datatransfer;

public class UnsupportedFlavorException extends Exception {

  public UnsupportedFlavorException(DataFlavor flavor) {
    super( (flavor != null) ? flavor.getHumanPresentableName() : null);
  }
}
