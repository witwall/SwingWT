/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
   
 */


package swingwtx.swing;
import swingwt.awt.*;

/**
 * Dummy JApplet class for Swing applet descendants.
 * Doesn't do anything as Applets are pretty
 * pointless under SwingWT (and unsupported).
 *
 * This is for those apps that have an Applet
 * and Frame interface (so they compile).
 *
 * @author Robin Rawson-Tetley
 */
public class JApplet extends swingwt.applet.Applet implements RootPaneContainer{

    public swingwt.awt.Container getContentPane() {
	return this;
    }

    public void setContentPane(swingwt.awt.Container pane) {	    
    }
//050629 Niklas Start
    /** * <p>Dummy to be able to compile due to dual start-class </p>*/
    public void setJMenuBar(JMenuBar menuBar){}
    /** * <p>Always returns null</p> * <p>@return JMenuBar  (null)</p>*/
    public JMenuBar getJMenuBar(){return null;}
    
    public void setLayeredPane(JLayeredPane pane)
    {
    	
    }
    public void setGlassPane(Component comp){}
    public Component getGlassPane()
    {
    	return null;
    }
    public JLayeredPane getLayeredPane()
    {
    	return null;
    }
}
