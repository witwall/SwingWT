/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.applet;

import java.util.*;
import java.net.*;
import java.io.*;
import swingwt.awt.*;

public interface AppletContext {
    
    AudioClip getAudioClip(URL url);
    Image getImage(URL url);
    Applet getApplet(String name);
    Enumeration getApplets();
    void showDocument(URL url);
    public void showDocument(URL url, String target);
    void showStatus(String status);
    public void setStream(String key, InputStream stream)throws IOException;
    public InputStream getStream(String key);
    public Iterator getStreamKeys();
}

