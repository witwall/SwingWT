/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwt.awt;

public interface Transparency {
    public final static int OPAQUE = 1;
    public final static int BITMASK = 2;
    public final static int TRANSLUCENT = 3;
    public int getTransparency();
}
