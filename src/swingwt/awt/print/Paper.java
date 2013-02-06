/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwt.awt.print;

/**
 * @author David Jung 
 */
public class Paper implements Cloneable
{
  public Paper()
  {
    // letter page with 1 inch margins
    //  (note that the Java docs don't say if the x,y,imgWidth,imgHeight are also in units of 1/72 inch
    //   like width & height - we assume so here)
    set(1*72,1*72,8.5*72,11*72,(8.5-2)*72,(11-2)*72);
  }

  
  private void set(double x, double y, double width, double height, double imgWidth, double imgHeight)
  {
    _x = x;
    _y = y;
    _width = width;
    _height = height;
    _imgWidth = imgWidth;
    _imgHeight = imgHeight;
  }
  


  public Object	clone()
  {
    Paper p = new Paper();
    p.set(_x,_y,_width,_height,_imgWidth,_imgHeight);
    return p;
  }

  public double	getHeight()
  {
    return _height;
  }

  public double	getImageableHeight()
  {
    return _imgHeight;
  }

  public double	getImageableWidth()
  {
    return _imgWidth;
  }

  public double	getImageableX()
  {
    return _x;
  }

  public double	getImageableY()
  {
    return _y;
  }

  public double	getWidth()
  {
    return _width;
  }

  public void setImageableArea(double x, double y, double width, double height)
  {
    _x=x; _y=y; _imgWidth=width; _imgHeight=height;
  }

  public void setSize(double width, double height)
  {
    _width=width; _height=height;
  }

 
  private double _x;
  private double _y;
  private double _width;
  private double _height;
  private double _imgWidth;
  private double _imgHeight;
}
