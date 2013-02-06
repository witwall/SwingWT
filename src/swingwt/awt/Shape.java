/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import swingwt.awt.geom.*;

public interface Shape {
    
    public Rectangle getBounds();
    public Rectangle2D getBounds2D();
    public boolean contains(double x, double y);
    public boolean contains(Point2D p);
    public boolean intersects(double x, double y, double w, double h);
    public boolean intersects(Rectangle2D r);
    public boolean contains(double x, double y, double w, double h);
    public boolean contains(Rectangle2D r);
    public PathIterator getPathIterator(AffineTransform at);
    public PathIterator getPathIterator(AffineTransform at, double flatness);
    
}
