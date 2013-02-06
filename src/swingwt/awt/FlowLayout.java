/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 */

package swingwt.awt;

public class FlowLayout implements LayoutManager {
    
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int LEADING = 3;
    public static final int TRAILING = 4;
    
    int align;
    int newAlign;
    int hgap;
    int vgap;
    
    public FlowLayout() {
        this(CENTER, 5, 5);
    }
    
    public FlowLayout(int align) {
        this(align, 5, 5);
    }
    
    public FlowLayout(int align, int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
        setAlignment(align);
    }
    
    public int getAlignment() {
        return newAlign;
    }
    
    public void setAlignment(int align) {
        this.newAlign = align;
        
        switch (align) {
            case LEADING :
                this.align = LEFT;
                break;
            case TRAILING :
                this.align = RIGHT;
                break;
            default :
                this.align = align;
                break;
        }
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
    
    public void addLayoutComponent(String name, Component comp) {
    }
    
    public void removeLayoutComponent(Component comp) {
    }
    
    public Dimension preferredLayoutSize(Container target) {
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();
        boolean firstVisibleComponent = true;

        for (int i = 0; i < nmembers; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                dim.height = Math.max(dim.height, d.height);
                if (firstVisibleComponent) {
                    firstVisibleComponent = false;
                } else {
                    dim.width += hgap;
                }
                dim.width += d.width;
            }
        }
        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + hgap * 2;
        dim.height += insets.top + insets.bottom + vgap * 2;
        return dim;
    }
    
    public Dimension minimumLayoutSize(Container target) {
        Dimension dim = new Dimension(0, 0);
        int nmembers = target.getComponentCount();

        for (int i = 0; i < nmembers; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getMinimumSize();
                dim.height = Math.max(dim.height, d.height);
                if (i > 0) {
                    dim.width += hgap;
                }
                dim.width += d.width;
            }
        }
        Insets insets = target.getInsets();
        dim.width += insets.left + insets.right + hgap * 2;
        dim.height += insets.top + insets.bottom + vgap * 2;
        return dim;
    }
    
    
    public void layoutContainer(Container target) {
        Insets insets = target.getInsets();
        int maxwidth =
        target.getWidth() - (insets.left + insets.right + hgap * 2);
        int nmembers = target.getComponentCount();
        int x = 0, y = insets.top + vgap;
        int rowh = 0, start = 0;

        boolean ltr = target.getComponentOrientation().isLeftToRight();

        for (int i = 0; i < nmembers; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                Dimension d = m.getPreferredSize();
                m.setSize(d.width, d.height);

                if ((x == 0) || ((x + d.width) <= maxwidth)) {
                    if (x > 0) {
                        x += hgap;
                    }
                    x += d.width;
                    rowh = Math.max(rowh, d.height);
                } else {
                    setComponentPositions(target, insets.left + hgap, y, maxwidth - x, rowh, start, i, ltr);
                    x = d.width;
                    y += vgap + rowh;
                    rowh = d.height;
                    start = i;
                }
            }
        }
        setComponentPositions(target, insets.left + hgap, y, maxwidth - x,  rowh, start, nmembers, ltr);
    }
    
    protected void setComponentPositions(
                                    Container target,
                                    int x,
                                    int y,
                                    int width,
                                    int height,
                                    int rowStart,
                                    int rowEnd,
                                    boolean ltr) {
        switch (newAlign) {
            case LEFT :
                x += ltr ? 0 : width;
                break;
            case CENTER :
                x += width / 2;
                break;
            case RIGHT :
                x += ltr ? width : 0;
                break;
            case LEADING :
                break;
            case TRAILING :
                x += width;
                break;
        }
        for (int i = rowStart; i < rowEnd; i++) {
            Component m = target.getComponent(i);
            if (m.isVisible()) {
                if (ltr) {
                    m.setBounds(x, y + (height - m.getPreferredSize().height) / 2, m.getPreferredSize().width, m.getPreferredSize().height);
                } else {
                    m.setBounds(target.getWidth() - x - m.getPreferredSize().width, y + (height - m.getPreferredSize().height) / 2, m.getPreferredSize().width, m.getPreferredSize().height);
                }
                x += m.getPreferredSize().width + hgap;
            }
        }
    }

    
}
