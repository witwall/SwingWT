/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwt.awt;

import swingwtx.swing.SwingWTUtils;


public class BorderLayout implements LayoutManager2 {

    private int hgap = 0;
    private int vgap = 0;
    private Component north;
    private Component west;
    private Component east;
    private Component south;
    private Component center;
    private Component firstLine;
    private Component lastLine;
    private Component firstItem;
    private Component lastItem;
    
    public static final String NORTH = "North";
    public static final String SOUTH = "South";
    public static final String EAST = "East";
    public static final String WEST = "West";
    public static final String CENTER = "Center";
    public static final String BEFORE_FIRST_LINE = "First";
    public static final String AFTER_LAST_LINE = "Last";
    public static final String BEFORE_LINE_BEGINS = "Before";
    public static final String AFTER_LINE_ENDS = "After";
    public static final String PAGE_START = BEFORE_FIRST_LINE;
    public static final String PAGE_END = AFTER_LAST_LINE;
    public static final String LINE_START = BEFORE_LINE_BEGINS;
    public static final String LINE_END = AFTER_LINE_ENDS;

    public BorderLayout() {
    }
    
    public BorderLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }
    
    public int getHgap() {
        return hgap;
    }
    
    public void setHgap(int hgap) {
        this.hgap = hgap;
    }
    
    public int getVgap() {
        return vgap;
    }
    
    public void setVgap(int vgap) {
        this.vgap = vgap;
    }
    
    public void addLayoutComponent(Component comp, Object constraints) {
	if ((constraints == null) || (constraints instanceof String)) {
	    addLayoutComponent((String) constraints, comp);
	} else {
	    throw new IllegalArgumentException("constraint must be a string");
	}
    }
    
    public void addLayoutComponent(String name, Component comp) {
        
            if (name == null) {
                name = "Center";
            }
            if ("Center".equals(name)) {
                center = comp;
            }
            else if ("North".equals(name)) {
                north = comp;
            }
            else if ("South".equals(name)) {
                south = comp;
            }
            else if ("East".equals(name)) {
                east = comp;
            }
            else if ("West".equals(name)) {
                west = comp;
            }
            else if (BEFORE_FIRST_LINE.equals(name)) {
                firstLine = comp;
            }
            else if (AFTER_LAST_LINE.equals(name)) {
                lastLine = comp;
            }
            else if (BEFORE_LINE_BEGINS.equals(name)) {
                firstItem = comp;
            }
            else if (AFTER_LINE_ENDS.equals(name)) {
                lastItem = comp;
            }
            else {
                throw new IllegalArgumentException(
                "unknown constraint: " + name);
            }
        
    }
    
    public void removeLayoutComponent(Component comp) {
        
            if (comp == center) {
                center = null;
            }
            else if (comp == north) {
                north = null;
            }
            else if (comp == south) {
                south = null;
            }
            else if (comp == east) {
                east = null;
            }
            else if (comp == west) {
                west = null;
            }
            if (comp == firstLine) {
                firstLine = null;
            }
            else if (comp == lastLine) {
                lastLine = null;
            }
            else if (comp == firstItem) {
                firstItem = null;
            }
            else if (comp == lastItem) {
                lastItem = null;
            }
        
    }
    
    public Dimension minimumLayoutSize(Container target) {

            Dimension dim = new Dimension(0, 0);
            
            boolean ltr = target.getComponentOrientation().isLeftToRight();
            Component c = null;
            
            if ((c = getChild(EAST, ltr)) != null) {
                Dimension d = c.getMinimumSize();
                dim.width += d.width + hgap;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c = getChild(WEST, ltr)) != null) {
                Dimension d = c.getMinimumSize();
                dim.width += d.width + hgap;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c = getChild(CENTER, ltr)) != null) {
                Dimension d = c.getMinimumSize();
                dim.width += d.width;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c = getChild(NORTH, ltr)) != null) {
                Dimension d = c.getMinimumSize();
                dim.width = Math.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }
            if ((c = getChild(SOUTH, ltr)) != null) {
                Dimension d = c.getMinimumSize();
                dim.width = Math.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }
            
            Insets insets = target.getInsets();
            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom;
            
            return dim;
        
    }
    
    public Dimension preferredLayoutSize(Container target) {
        
            Dimension dim = new Dimension(0, 0);
            
            boolean ltr = target.getComponentOrientation().isLeftToRight();
            Component c = null;
            
            if ((c = getChild(EAST, ltr)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width += d.width + hgap;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c = getChild(WEST, ltr)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width += d.width + hgap;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c = getChild(CENTER, ltr)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width += d.width;
                dim.height = Math.max(d.height, dim.height);
            }
            if ((c = getChild(NORTH, ltr)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width = Math.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }
            if ((c = getChild(SOUTH, ltr)) != null) {
                Dimension d = c.getPreferredSize();
                dim.width = Math.max(d.width, dim.width);
                dim.height += d.height + vgap;
            }
            
            Insets insets = target.getInsets();
            dim.width += insets.left + insets.right;
            dim.height += insets.top + insets.bottom;
            
            return dim;
        
    }
    
    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    public float getLayoutAlignmentX(Container parent) {
        return (float) 0.5;
    }
    
    public float getLayoutAlignmentY(Container parent) {
        return (float) 0.5;
    }
    
    public void invalidateLayout(Container target) {
    }
    
    public void layoutContainer(Container target) {
        Insets insets = target.getInsets();
        int top = insets.top;
        int bottom = target.getHeight() - insets.bottom;
        int left = insets.left;
        int right = target.getWidth() - insets.right;

        boolean ltr = target.getComponentOrientation().isLeftToRight();
        Component c = null;

        if ((c = getChild(NORTH, ltr)) != null) {
            c.setSize(right - left, c.getHeight());
            Dimension d = c.getPreferredSize();
            c.setBounds(left, top, right - left, d.height);
            top += d.height + vgap;
        }
        if ((c = getChild(SOUTH, ltr)) != null) {
            c.setSize(right - left, c.getHeight());
            Dimension d = c.getPreferredSize();
            c.setBounds(left, bottom - d.height, right - left, d.height);
            bottom -= d.height + vgap;
        }
        if ((c = getChild(EAST, ltr)) != null) {
            c.setSize(c.getWidth(), bottom - top);
            Dimension d = c.getPreferredSize();
            c.setBounds(right - d.width, top, d.width, bottom - top);
            right -= d.width + hgap;
        }
        if ((c = getChild(WEST, ltr)) != null) {
            c.setSize(c.getWidth(), bottom - top);
            Dimension d = c.getPreferredSize();
            c.setBounds(left, top, d.width, bottom - top);
            left += d.width + hgap;
        }
        if ((c = getChild(CENTER, ltr)) != null) {
            c.setBounds(left, top, right - left, bottom - top);
        }
    }
    
    private Component getChild(String key, boolean ltr) {
        Component result = null;
        
        if (key == NORTH) {
            result = (firstLine != null) ? firstLine : north;
        } else if (key == SOUTH) {
            result = (lastLine != null) ? lastLine : south;
        } else if (key == WEST) {
            result = ltr ? firstItem : lastItem;
            if (result == null) {
                result = west;
            }
        } else if (key == EAST) {
            result = ltr ? lastItem : firstItem;
            if (result == null) {
                result = east;
            }
        } else if (key == CENTER) {
            result = center;
        }
        if (result != null && !result.isVisible()) {
            result = null;
        }
        return result;
    }
    
}
