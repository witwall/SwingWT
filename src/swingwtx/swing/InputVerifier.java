/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;
public abstract class InputVerifier 
{
  public abstract boolean verify(JComponent input);
  public boolean shouldYieldFocus(JComponent input){return verify(input);}
}
