/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt.image;

public class Raster {
    private int width;
    private int height;

    Raster(int width, int height) {
        this.width = width;
        this.height = height; 
   }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
