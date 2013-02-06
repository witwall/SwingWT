/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   
*/

package swingwt.applet;

import swingwt.awt.*;
import swingwt.sound.*;


import java.net.URL;
import java.util.Locale;

/**
 * Dummy applet container. Doesn't actually do anything but provide
 * fake applet services to subclasses.
 *
 * This is for Java applications that can project both a regular
 * AWT interface and an Applet (and obviously only the regular
 * AWT one will work).
 *
 * If enough people hassle me I'll implement AppletInfo
 * and AppletContext - but you really shouldn't need them :-)
 * 
 * getAudioClip/newAudioClip  implemented with new AudioClipPlayer ( Niklas Gustafsson 060814 )
 * 
 * @author Robin Rawson-Tetley
 */
public class Applet extends swingwt.awt.Panel {

    AppletStub stub = null;
    
	
    public void destroy() {}

    public String getAppletInfo() {
        return "SwingWT dummy applet container.";
    }
    
    public AudioClip getAudioClip(URL url) {
        return new AudioClipPlayer(url);   
    }
    
    public AudioClip getAudioClip(URL url, String name) {
        return getAudioClip(url);    
    }

    public URL getCodeBase() {
        return null;
    }

    public URL getDocumentBase() {
        return null;
    }

    public Image getImage(URL image) {
        return new swingwtx.swing.ImageIcon(image).getImage();
    }

    public Image getImage(URL image, String name) {
        return getImage(image);
    }

    public Locale getLocale() {
        return null;
    }
    
    public String getParameter(String name) {
        return null;
    }
    
    public String[] getParameterInfo() {
	return null;
    }

    public void init() {
    }

    public boolean isActive() {
	return false; 
    }
    
    public static AudioClip newAudioClip(URL url) 
    {
        return new AudioClipPlayer(url); 
    }

    public void play(URL url) {
    }

    public void play(URL url, String name) {
    }

    public void resize(Dimension d) {
    }
    
    public void resize(int width, int height) {
    }

    public void showStatus(String message) {
    }

    public void start() {
    }

    public void stop() {
    }	
    public void setStub(AppletStub stub)
    {
    	this.stub = stub;
    }
    public AppletContext getAppletContext()
    {
    	return stub.getAppletContext();
    }
    
}
