/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net



*/

package swingwtx.swing;

import swingwt.awt.*;
import swingwtx.accessibility.*;



public class JToolTip extends JComponent implements Accessible
{
	private Dimension prefeeredSize = new Dimension(30,10);
	private String tipText;
	public void setTipText(String text)
	{
		tipText=text;
	}
	public String getTipText()
	{
		return tipText;
	}
	public Dimension getPreferredSize()
	{
		return prefeeredSize;
	}
	public void setPreferredSize(Dimension d)
	{
		prefeeredSize=d;
	}
}
