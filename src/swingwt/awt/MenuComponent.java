/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net


*/
package swingwt.awt;

import swingwtx.swing.JComponent;

/**
 * @author Dan
 *
 */
public class MenuComponent
{
    protected JComponent swingPeer = null;
    
    protected Font font;
    protected String name;
    MenuContainer parent;
    
    public MenuContainer getParent() { return null; }
	//public boolean postEvent(Event evt) { return false; }
    //public swingwt.awt.peer.MenuComponentPeer getPeer() { return null; }
    
    public void dispatchEvent(AWTEvent e) {  }
	protected void processEvent(AWTEvent e) { swingPeer.processEvent(e); }
	public void removeNotify() { swingPeer.removeNotify(); }
    
    public Font getFont() { return font; }
	public void setFont(Font f) { this.font = f; }
    public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
