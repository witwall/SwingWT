/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwt.awt.geom;

public abstract class Dimension2D implements Cloneable {
    protected Dimension2D() {
    }
    public abstract double getWidth();
    public abstract double getHeight();
    public abstract void setSize(double width, double height);
    public void setSize(Dimension2D d) {
        setSize(d.getWidth(), d.getHeight());
    }
    public Object clone() {
	    try {
	        return super.clone();
	    } catch (CloneNotSupportedException e) { throw new InternalError(); }
    }
}
