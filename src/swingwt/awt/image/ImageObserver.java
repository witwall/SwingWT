/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt.image;

import swingwt.awt.Image;

public interface ImageObserver {

    public static final int WIDTH = 1;
    public static final int HEIGHT = 2;
    public static final int PROPERTIES = 4;
    public static final int SOMEBITS = 8;
    public static final int FRAMEBITS = 16;
    public static final int ALLBITS = 32;
    public static final int ERROR = 64;
    public static final int ABORT = 128;
    
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height);
}
