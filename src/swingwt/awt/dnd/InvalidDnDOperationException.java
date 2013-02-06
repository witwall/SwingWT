/*
   SwingWT
   Copyright(c)2003-2008, Tomer Barletz

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: tomerb@users.sourceforge.net
*/

package swingwt.awt.dnd;

public class InvalidDnDOperationException extends IllegalStateException {
  static private String dft_msg = "The operation requested cannot be performed by the DnD system since it is not in the appropriate state";

  public InvalidDnDOperationException() { super(dft_msg); }

  public InvalidDnDOperationException(String msg) { super(msg); }

}
