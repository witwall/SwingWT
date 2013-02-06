
package swingwt.awt;

import swingwt.awt.geom.Point2D;
import swingwt.awt.geom.Rectangle2D;
import swingwt.awt.geom.AffineTransform;
import swingwt.awt.image.ColorModel;

/**
 * 
 * TODO: Implement PaintContext   ( this class is not working . Just stub )
 *
 */
public class GradientPaint implements Paint 
{
    Point2D.Float point1;
    Point2D.Float point2;
    Color color1;
    Color color2;
    boolean cyclic;

    public GradientPaint(float x1,
			 float y1,
			 Color color1,
			 float x2,
			 float y2,
			 Color color2) 
    {
        
    
        point1 = new Point2D.Float(x1, y1);
        point2 = new Point2D.Float(x2, y2);
        this.color1 = color1;
        this.color2 = color2;
    }

    public GradientPaint(Point2D pt1,
			 Color color1,
			 Point2D pt2,
			 Color color2) 
    {
        point1 = new Point2D.Float((float)pt1.getX(), (float)pt1.getY());
        point2 = new Point2D.Float((float)pt2.getX(), (float)pt2.getY());
        this.color1 = color1;
        this.color2 = color2;
    }
    public GradientPaint(float x1,
			 float y1,
			 Color color1,
			 float x2,
			 float y2,
			 Color color2,
			 boolean cyclic) 
    {
    	this (x1, y1, color1, x2, y2, color2);
    	this.cyclic = cyclic;
    }
    public GradientPaint(Point2D pt1,Color color1,Point2D pt2,Color color2,boolean cyclic) 
    {
    	this (pt1, color1, pt2, color2);
    	this.cyclic = cyclic;
    }
    public Point2D getPoint1() {
	return new Point2D.Float(point1.x, point1.y);
    }
    public Color getColor1() 
    {
    	return color1;
    }
    public Point2D getPoint2() 
    {
    	return new Point2D.Float(point2.x, point2.y);
    }
    public Color getColor2() 
    {
    	return color2;
    }
    public boolean isCyclic() 
    {
    	return cyclic;
    }
    public PaintContext createContext(ColorModel cm,
				      Rectangle deviceBounds,
				      Rectangle2D userBounds,
				      AffineTransform xform,
                                      RenderingHints hints) 
    {

        return null;
    }
    public int getTransparency() 
    {
	
    	return 0;
    }

}
