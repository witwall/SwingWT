/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
*/

package swingwt.awt;

/**
 * 
 * TODO Comment!!
 * 
 * @author R. Rawson-Tetley
 * @author Thiago Tonelli Bartolomei
 */
public class Insets implements Cloneable {

    public int top;
    public int left;
    public int bottom;
    public int right;
    
    public Insets() { 
    	this.top = 0;
        this.left = 0;
        this.bottom = 0;
        this.right = 0;
    }
    
    public Insets(int top, int left, int bottom, int right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }
  
    public Insets(Insets i) {
    	this.top = i.top;
        this.left = i.left;
        this.bottom = i.bottom;
        this.right = i.right;
    }
    
    public final boolean equals(Insets i) {
    	// TODO - write hashCode()
    	return null != i && i.top == top && i.left == left && i.bottom == bottom && i.right == right;
    }
    
    public String toString() {
        return getClass().getName()+"[top="+top+",left="+left+",bottom="+bottom+",right="+right+"]";
    }

    /**
     * Returns a new Insets whose values are the sum of the values in this
     * object and the insets passed as argument.
     *
     * @param i
     * @return
     */
    public final Insets add(Insets i) {
    	return new Insets(top + i.top, left + i.left, bottom + i.bottom, right + i.right);
    }

    /**
     * Returns a new Insets object with the same values as this one.
     *
     * @return
     */
    public final Object clone() {
    	return new Insets(top, left, bottom, right);
    }
}

