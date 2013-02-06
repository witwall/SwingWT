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
public class PageFormat implements Cloneable
{
  public static int LANDSCAPE = 0;
  public static int PORTRAIT = 1;
  public static int REVERSE_LANDSCAPE = 2;

  public PageFormat()
  {
    _paper = new Paper();
    _orientation = PORTRAIT;
    _matrix = new double[6];
    for(int i=0; i<6; i++) _matrix[i] = 0;
    _matrix[0] = 1;
    _matrix[3] = 1;
  }


  public Object clone()
  {
    PageFormat pf = new PageFormat();
    pf._paper = (Paper)_paper.clone();

    pf._orientation = _orientation;
    pf._matrix = new double[_matrix.length];
    for(int i=0; i<_matrix.length;i++) pf._matrix[i] = _matrix[i];
    
    return pf;
  }

  
  public double getHeight() 
  {
    return _paper.getHeight();
  }

  public double getImageableHeight()
  {
    return _paper.getImageableHeight();
  }

  public  double getImageableWidth()
  {
    return _paper.getImageableWidth();
  }

  public  double getImageableX()
  {
    return _paper.getImageableX();
  }

  public double	getImageableY()
  {
    return _paper.getImageableY();
  }

  public double[] getMatrix()
  {
    return _matrix;
  }

  public int getOrientation()
  {
    return _orientation;
  }

  public Paper getPaper()
  {
    return _paper;
  }

  public double getWidth()
  {
    return _paper.getWidth();
  }

  public void setOrientation(int orientation)
  {
    _orientation = orientation;
  }

  public void setPaper(Paper paper)
  {
    _paper = paper;
  }


  private Paper _paper;
  private int _orientation;
  private double[] _matrix;
}
