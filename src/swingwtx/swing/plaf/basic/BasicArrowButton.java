/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   

 
 */

package swingwtx.swing.plaf.basic;

import swingwtx.swing.*;
import swingwt.awt.*;

/**
 * 
 * Just a wrapper for a standard JButton   for now
 * TODO:  implement the arrow-stuff
 */
public class BasicArrowButton extends JButton implements SwingConstants
{

	protected int direction = 0;
	
	public BasicArrowButton(int direction, Color background, Color shadow,
			 Color darkShadow, Color highlight) 
	{
	    super();
	    this.direction=direction;
	}   

       public BasicArrowButton(int direction) 
       {
    	   super();
       }

       public int getDirection() 
       { 
    	   return direction; 
       }

       public void setDirection(int direction) 
       { 
    	   this.direction = direction; 
       }
        public Dimension getPreferredSize() 
        {
            return new Dimension(16, 16);
        }

        public Dimension getMinimumSize() 
        {
            return new Dimension(5, 5);
        }

        public Dimension getMaximumSize() 
        {
            return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
    
    	public boolean isFocusTraversable() 
    	{
    		return false;
    	}

	
}

