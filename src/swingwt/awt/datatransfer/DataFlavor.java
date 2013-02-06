/*
   SwingWT
   Copyright(c)2003-2008, Tomer Barletz

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: tomerb@users.sourceforge.net
*/

package swingwt.awt.datatransfer;

import java.io.Reader;

public class DataFlavor {
  
  private String humanPresentableName;
  public static DataFlavor stringFlavor = new DataFlavor("application-x-serialized-java-object");
  public static DataFlavor javaFileListFlavor = new DataFlavor("javaFileListFlavor");

  /** NOT IMPLEMENTED */
  public DataFlavor(String mimetype) {
  }

  public DataFlavor(Class representationClass, String humanPresentableName) {
    this.humanPresentableName = humanPresentableName;
  }

  public String getHumanPresentableName() { return humanPresentableName; }

  public void setHumanPresentableName(String humanPresentableName) {
    this.humanPresentableName = humanPresentableName;
  }

  public boolean isRepresentationClassReader() {
    return false;
  }

  public Reader getReaderForText(Transferable transferable) throws UnsupportedFlavorException {
    throw new UnsupportedFlavorException(this);
  }
}
