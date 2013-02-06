/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import swingwt.awt.Component;
import swingwt.awt.Container;
import swingwt.awt.Dimension;

/**
 * @author Submitted by a kind developer who remains anonymous
 * Fixed up by Rob to subclass JPanel so it can be used as a Swing
 * container (SwingSet does this)
 */
public class Box extends JPanel {
    
    public Box(int axis) { 
        this(null, axis);
    }
    
    public Box(Container target, int axis) { 
        
        if (target != null) {
            target.add(this);
            setLayout(new BoxLayout(target, axis));
        }
        else
            setLayout(new BoxLayout(this, axis));
    }
                              
    public static Box createHorizontalBox() {
        return new Box(BoxLayout.X_AXIS);    
    }
    
    public static Box createVerticalBox() {
        return new Box(BoxLayout.Y_AXIS);    
    }
    
    public static Box createHorizontalBox(Container target) {
            return new Box(target, BoxLayout.X_AXIS);
    }
                                                                                                                        
    public static Box createVerticalBox(Container target) {
            return new Box(target, BoxLayout.Y_AXIS);
    }
                                                                                                                             
    public static Component createRigidArea(Dimension d) {
        return new Filler(d, d, d);
    }
                                                                                                                             
    public static Component createHorizontalStrut(int width) {
        // PENDING(jeff) change to Integer.MAX_VALUE. This hasn't been done
        // to date because BoxLayout alignment breaks.
        return new Filler(new Dimension(width,0), new Dimension(width,0), new Dimension(width, Short.MAX_VALUE));
    }
                                                                                                                             
    public static Component createVerticalStrut(int height) {
        // PENDING(jeff) change to Integer.MAX_VALUE. This hasn't been done
        // to date because BoxLayout alignment breaks.
        return new Filler(new Dimension(0,height), new Dimension(0,height), new Dimension(Short.MAX_VALUE, height));
    }
                                                                                                                             
    public static Component createGlue() {
        // PENDING(jeff) change to Integer.MAX_VALUE. This hasn't been done
        // to date because BoxLayout alignment breaks.
        return new Filler(new Dimension(0,0), new Dimension(0,0), new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
    }
                                                                                                                             
    public static Component createHorizontalGlue() {
        // PENDING(jeff) change to Integer.MAX_VALUE. This hasn't been done 
        // to date because BoxLayout alignment breaks.
        return new Filler(new Dimension(0,0), new Dimension(0,0), new Dimension(Short.MAX_VALUE, 0));
    }
                                                                                                                             
    public static Component createVerticalGlue() {
        // PENDING(jeff) change to Integer.MAX_VALUE. This hasn't been done
        // to date because BoxLayout alignment breaks.
        return new Filler(new Dimension(0,0), new Dimension(0,0), new Dimension(0, Short.MAX_VALUE));
    }                                                                                                                    

  	/**
  	 * Sub-types can override this to provide a better identifier during debug. 
  	 *
  	 * @return
  	 */
  	public String debugId() {
  		return "Box";
  	}
  	
    public static class Filler extends JPanel {
                                                                                                                             
        public Filler(Dimension min, Dimension pref, Dimension max) {
            reqMin = min;
            reqPref = pref;
            reqMax = max;
        }
                                                                                                                             
        public void changeShape(Dimension min, Dimension pref, Dimension max) {
            reqMin = min;
            reqPref = pref;
            reqMax = max;
            //invalidate();
        }
                                                                                                                             
        public Dimension getMinimumSize() {
            return reqMin;
        }
                                                                                                                             
        public Dimension getPreferredSize() {
            return reqPref;
        }
                                                                                                                             
        public Dimension getMaximumSize() {
            return reqMax;
        }
                                                                                                                             
        private Dimension reqMin;
        private Dimension reqPref;
        private Dimension reqMax;
    }
}
