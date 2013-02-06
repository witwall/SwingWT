/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwt.awt.peer;

import swingwt.awt.AWTEvent;
import swingwt.awt.AWTException;
import swingwt.awt.BufferCapabilities;
import swingwt.awt.Color;
import swingwt.awt.Component;
import swingwt.awt.Dimension;
import swingwt.awt.Font;
import swingwt.awt.FontMetrics;
import swingwt.awt.Graphics;
import swingwt.awt.GraphicsConfiguration;
import swingwt.awt.Image;
import swingwt.awt.Point;
import swingwt.awt.Toolkit;
//import swingwt.awt.event.PaintEvent;
import swingwt.awt.image.ColorModel;
import swingwt.awt.image.ImageObserver;
import swingwt.awt.image.ImageProducer;
import swingwt.awt.image.VolatileImage;

/**
 * ComponentPeer interface - not implemented anywhere,
 * just here for compile compatibility with Swing (for now).
 * 
 * @author Dan
 */
public interface ComponentPeer
{
    void setVisible(boolean visible);
    void setEnabled(boolean enabled);

    boolean canDetermineObscurity();
    boolean isObscured();
    
    void handleEvent(AWTEvent awtEvent);
    
    Point getLocationOnScreen();
    Dimension getPreferredSize();
    Dimension getMinimumSize();
    void setBounds(int x, int y, int width, int height);
    ColorModel getColorModel();
    
    Toolkit getToolkit();
    Graphics getGraphics();

    void paint(Graphics g);
    void repaint(long tm, int x, int y, int width, int height);
    void print(Graphics g);
    //void coalescePaintEvent(PaintEvent paintEvent);
    
    FontMetrics getFontMetrics(FontPeer fontPeer);
    void setFont(Font f);
    
    void dispose();
    void setForeground(Color c);
    void setBackground(Color c);
    
    void updateCursorImmediately();
    
    boolean requestFocus(Component lightweightChild, boolean temporary, boolean focusedWindowChangeAllowed, long time);
    boolean isFocusable();

    Image createImage(ImageProducer imageProducer);
    Image createImage(int width, int height);
    VolatileImage createVolatileImage(int width, int height);
    boolean prepareImage(Image img, int w, int h, ImageObserver imageObserver);
    int checkImage(Image img, int w, int h, ImageObserver imageObserver);
    
    GraphicsConfiguration getGraphicsConfiguration();
    
    boolean handlesWheelScrolling();
    
    void flip(BufferCapabilities.FlipContents flipAction);
    void createBuffers(int numBuffers, BufferCapabilities bufferCapabilities) throws AWTException;
    Image getBackBuffer();
    void destroyBuffers();

    // Depreciated methods...
    Dimension preferredSize();
    Dimension minimumSize();
    void show();
    void hide();
    void enable();
    void disable();
    void reshape(int x, int y, int width, int height);

}
