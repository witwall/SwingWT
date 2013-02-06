/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwt.awt;

import java.util.*;

public class GridBagLayout implements LayoutManager2 {
    
    protected GridBagConstraints defaultConstraints = null;
    protected GridBagLayoutInfo layoutInfo = null;
    protected Hashtable components = null;;
    
    public int columnWidths[] = null;
    public int rowHeights[] = null;
    public double columnWeights[] = null;
    public double rowWeights[] = null;
    
    public GridBagLayout() {
        components = new Hashtable();
        defaultConstraints = new GridBagConstraints();
    }
    
    public void setConstraints(Component comp, GridBagConstraints constraints) {
        components.put(comp, constraints.clone());
    }
    
    public GridBagConstraints getConstraints(Component comp) {
        GridBagConstraints con = (GridBagConstraints) ((GridBagConstraints) components.get(comp));
        if (con != null)        
            return (GridBagConstraints) con.clone();
        else {
            setConstraints(comp, defaultConstraints);
            return (GridBagConstraints) defaultConstraints.clone();
        }
    }
    
    private void removeConstraints(Component comp) {
        components.remove(comp);
    }
    
    public Point getLayoutOrigin() {
        Point origin = new Point(0, 0);
        if (layoutInfo != null) {
            origin.x = layoutInfo.startx;
            origin.y = layoutInfo.starty;
        }
        return origin;
    }
    
    public int[][] getLayoutDimensions() {
        return new int[2][0];
    }
    
    public double[][] getLayoutWeights() {
        return new double[2][0];
    }
    
    public Point location(int x, int y) {
        return new Point(x, y);
    }
    
    public void addLayoutComponent(String name, Component comp) {
    }
    
    public void addLayoutComponent(Component comp, Object constraints) {
        // Assume if the constraints are null that the developers
        // code already called setConstraints() separately.
        if (constraints != null)
            setConstraints(comp, (GridBagConstraints) constraints);
    }
    
    public void removeLayoutComponent(Component comp) {
        removeConstraints(comp);
    }
    
    public Dimension preferredLayoutSize(Container parent) {
        GridBagLayoutInfo info = getLayoutInfo(parent, PREFERREDSIZE);
        return getSmallestLayoutSize(parent, info);
    }
    
    public Dimension minimumLayoutSize(Container parent) {
        GridBagLayoutInfo info = getLayoutInfo(parent, MINSIZE);
        return getSmallestLayoutSize(parent, info);
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
    
    public void layoutContainer(Container parent) {
        
        Component comp;
        int compindex;
        GridBagConstraints constraints;
        Insets insets = parent.getInsets();
        Component components[] = parent.getComponents();
        Dimension d;
        Rectangle r = new Rectangle();
        int i, diffw, diffh;
        double weight;
        GridBagLayoutInfo info;
        
        rightToLeft = !parent.getComponentOrientation().isLeftToRight();
        
        // Bomb out if there aren't any components to display
        if (components.length == 0) return;
        
        info = getLayoutInfo(parent, PREFERREDSIZE);
        d = getSmallestLayoutSize(parent, info);
        
        if (parent.getWidth() < d.width || parent.getHeight() < d.height) {
            info = getLayoutInfo(parent, MINSIZE);
            d = getSmallestLayoutSize(parent, info);
        }
        
        layoutInfo = info;
        r.width = d.width;
        r.height = d.height;
        
        diffw = parent.getWidth() - r.width;
        if (diffw != 0) {
            weight = 0.0;
            for (i = 0; i < info.width; i++)
                weight += info.weightX[i];
            if (weight > 0.0) {
                for (i = 0; i < info.width; i++) {
                    int dx =
                    (int) ((((double) diffw) * info.weightX[i]) / weight);
                    info.minWidth[i] += dx;
                    r.width += dx;
                    if (info.minWidth[i] < 0) {
                        r.width -= info.minWidth[i];
                        info.minWidth[i] = 0;
                    }
                }
            }
            diffw = parent.getWidth() - r.width;
        } else {
            diffw = 0;
        }
        
        diffh = parent.getHeight() - r.height;
        if (diffh != 0) {
            weight = 0.0;
            for (i = 0; i < info.height; i++)
                weight += info.weightY[i];
            if (weight > 0.0) {
                for (i = 0; i < info.height; i++) {
                    int dy =
                    (int) ((((double) diffh) * info.weightY[i]) / weight);
                    info.minHeight[i] += dy;
                    r.height += dy;
                    if (info.minHeight[i] < 0) {
                        r.height -= info.minHeight[i];
                        info.minHeight[i] = 0;
                    }
                }
            }
            diffh = parent.getHeight() - r.height;
        } else {
            diffh = 0;
        }
        
        info.startx = diffw / 2 + insets.left;
        info.starty = diffh / 2 + insets.top;
        
        for (compindex = 0; compindex < components.length; compindex++) {
            comp = components[compindex];
            if (!comp.isVisible())
                continue;
            constraints = lookupConstraints(comp);
            
            if (!rightToLeft) {
                r.x = info.startx;
                for (i = 0; i < constraints.tempX; i++)
                    r.x += info.minWidth[i];
            } else {
                r.x = parent.getWidth() - (diffw / 2 + insets.right);
                for (i = 0; i < constraints.tempX; i++)
                    r.x -= info.minWidth[i];
            }
            
            r.y = info.starty;
            for (i = 0; i < constraints.tempY; i++)
                r.y += info.minHeight[i];
            
            r.width = 0;
            for (i = constraints.tempX;
            i < (constraints.tempX + constraints.tempWidth);
            i++) {
                r.width += info.minWidth[i];
            }
            
            r.height = 0;
            for (i = constraints.tempY;
            i < (constraints.tempY + constraints.tempHeight);
            i++) {
                r.height += info.minHeight[i];
            }
            
            moveComponentInsets(constraints, r);
            
            if (r.x < 0) {
                r.width -= r.x;
                r.x = 0;
            }
            
            if (r.y < 0) {
                r.height -= r.y;
                r.y = 0;
            }
            
            if ((r.width <= 0) || (r.height <= 0)) {
                comp.setBounds(0, 0, 0, 0);
            } else {
                if (comp.getX() != r.x
                || comp.getY() != r.y
                || comp.getWidth() != r.width
                || comp.getHeight() != r.height) {
                    comp.setBounds(r.x, r.y, r.width, r.height);
                }
            }
        }
        
    }    
        
    protected GridBagConstraints lookupConstraints(Component comp) {
        GridBagConstraints con = (GridBagConstraints) components.get(comp);
        if (con != null) 
            return con;
        else {
            setConstraints(comp, defaultConstraints);
            return (GridBagConstraints) components.get(comp);
        }
    }
    
    protected GridBagLayoutInfo getLayoutInfo(Container parent, int sizeflag) {
        
        GridBagLayoutInfo r = new GridBagLayoutInfo();
        Component comp;
        GridBagConstraints constraints;
        Dimension d;
        Component components[] = parent.getComponents();

        int compindex, i, j, k, px, py, pixels_diff, nextSize;
        int curX, curY, curWidth, curHeight, curRow, curCol;
        double weight_diff, weight, start, size;
        int xMax[], yMax[];

        r.width = r.height = 0;
        curRow = curCol = -1;
        xMax = new int[MAXGRIDSIZE];
        yMax = new int[MAXGRIDSIZE];

        for (compindex = 0; compindex < components.length; compindex++) {
            comp = components[compindex];
            if (!comp.isVisible())
                continue;
            constraints = lookupConstraints(comp);

            curX = constraints.gridx;
            curY = constraints.gridy;
            curWidth = constraints.gridwidth;
            if (curWidth <= 0)
                curWidth = 1;
            curHeight = constraints.gridheight;
            if (curHeight <= 0)
                curHeight = 1;

            if (curX < 0 && curY < 0) {
                if (curRow >= 0)
                    curY = curRow;
                else if (curCol >= 0)
                    curX = curCol;
                else
                    curY = 0;
            }
            if (curX < 0) {
                px = 0;
                for (i = curY; i < (curY + curHeight); i++)
                    px = Math.max(px, xMax[i]);

                curX = px - curX - 1;
                if (curX < 0)
                    curX = 0;
            } else if (curY < 0) {
                py = 0;
                for (i = curX; i < (curX + curWidth); i++)
                    py = Math.max(py, yMax[i]);

                curY = py - curY - 1;
                if (curY < 0)
                    curY = 0;
            }

            for (px = curX + curWidth; r.width < px; r.width++);
            for (py = curY + curHeight; r.height < py; r.height++);

            for (i = curX; i < (curX + curWidth); i++) {
                yMax[i] = py;
            }
            for (i = curY; i < (curY + curHeight); i++) {
                xMax[i] = px;
            }

            if (sizeflag == PREFERREDSIZE)
                d = comp.getPreferredSize();
            else
                d = comp.getMinimumSize();
            
            constraints.minWidth = d.width;
            constraints.minHeight = d.height;

            if (constraints.gridheight == 0 && constraints.gridwidth == 0)
                curRow = curCol = -1;

            if (constraints.gridheight == 0 && curRow < 0)
                curCol = curX + curWidth;

            else if (constraints.gridwidth == 0 && curCol < 0)
                curRow = curY + curHeight;
        }

        if (columnWidths != null && r.width < columnWidths.length)
            r.width = columnWidths.length;
        if (rowHeights != null && r.height < rowHeights.length)
            r.height = rowHeights.length;

        curRow = curCol = -1;
        xMax = new int[MAXGRIDSIZE];
        yMax = new int[MAXGRIDSIZE];

        for (compindex = 0; compindex < components.length; compindex++) {
            comp = components[compindex];
            if (!comp.isVisible())
                continue;
            constraints = lookupConstraints(comp);

            curX = constraints.gridx;
            curY = constraints.gridy;
            curWidth = constraints.gridwidth;
            curHeight = constraints.gridheight;

            if (curX < 0 && curY < 0) {
                if (curRow >= 0)
                    curY = curRow;
                else if (curCol >= 0)
                    curX = curCol;
                else
                    curY = 0;
            }

            if (curX < 0) {
                if (curHeight <= 0) {
                    curHeight += r.height - curY;
                    if (curHeight < 1)
                        curHeight = 1;
                }

                px = 0;
                for (i = curY; i < (curY + curHeight); i++)
                    px = Math.max(px, xMax[i]);

                curX = px - curX - 1;
                if (curX < 0)
                    curX = 0;
            } else if (curY < 0) {
                if (curWidth <= 0) {
                    curWidth += r.width - curX;
                    if (curWidth < 1)
                        curWidth = 1;
                }

                py = 0;
                for (i = curX; i < (curX + curWidth); i++)
                    py = Math.max(py, yMax[i]);

                curY = py - curY - 1;
                if (curY < 0)
                    curY = 0;
            }

            if (curWidth <= 0) {
                curWidth += r.width - curX;
                if (curWidth < 1)
                    curWidth = 1;
            }

            if (curHeight <= 0) {
                curHeight += r.height - curY;
                if (curHeight < 1)
                    curHeight = 1;
            }

            px = curX + curWidth;
            py = curY + curHeight;

            for (i = curX; i < (curX + curWidth); i++) {
                yMax[i] = py;
            }
            for (i = curY; i < (curY + curHeight); i++) {
                xMax[i] = px;
            }

            if (constraints.gridheight == 0 && constraints.gridwidth == 0)
                curRow = curCol = -1;
            if (constraints.gridheight == 0 && curRow < 0)
                curCol = curX + curWidth;
            else if (constraints.gridwidth == 0 && curCol < 0)
                curRow = curY + curHeight;

            constraints.tempX = curX;
            constraints.tempY = curY;
            constraints.tempWidth = curWidth;
            constraints.tempHeight = curHeight;
        }

        if (columnWidths != null)
            System.arraycopy(columnWidths, 0, r.minWidth, 0, columnWidths.length);
        if (rowHeights != null)
            System.arraycopy(rowHeights, 0, r.minHeight, 0, rowHeights.length);
        if (columnWeights != null)
            System.arraycopy(columnWeights, 0, r.weightX, 0, columnWeights.length);
        if (rowWeights != null)
            System.arraycopy(rowWeights, 0, r.weightY, 0, rowWeights.length);

        nextSize = Integer.MAX_VALUE;

        for (i = 1; i != Integer.MAX_VALUE; i = nextSize, nextSize = Integer.MAX_VALUE) {
            for (compindex = 0;
            compindex < components.length;
            compindex++) {
                comp = components[compindex];
                if (!comp.isVisible())
                    continue;
                constraints = lookupConstraints(comp);

                if (constraints.tempWidth == i) {
                    px = constraints.tempX + constraints.tempWidth;

                    weight_diff = constraints.weightx;
                    for (k = constraints.tempX; k < px; k++)
                        weight_diff -= r.weightX[k];
                    if (weight_diff > 0.0) {
                        weight = 0.0;
                        for (k = constraints.tempX; k < px; k++)
                            weight += r.weightX[k];
                        for (k = constraints.tempX;
                        weight > 0.0 && k < px;
                        k++) {
                            double wt = r.weightX[k];
                            double dx = (wt * weight_diff) / weight;
                            r.weightX[k] += dx;
                            weight_diff -= dx;
                            weight -= wt;
                        }
                        r.weightX[px - 1] += weight_diff;
                    }

                    pixels_diff =
                    constraints.minWidth
                    + constraints.ipadx
                    + constraints.insets.left
                    + constraints.insets.right;

                    for (k = constraints.tempX; k < px; k++)
                        pixels_diff -= r.minWidth[k];
                    if (pixels_diff > 0) {
                        weight = 0.0;
                        for (k = constraints.tempX; k < px; k++)
                            weight += r.weightX[k];
                        for (k = constraints.tempX;
                        weight > 0.0 && k < px;
                        k++) {
                            double wt = r.weightX[k];
                            int dx =
                            (int) ((wt * ((double) pixels_diff))
                            / weight);
                            r.minWidth[k] += dx;
                            pixels_diff -= dx;
                            weight -= wt;
                        }
                        r.minWidth[px - 1] += pixels_diff;
                    }
                } else if (
                constraints.tempWidth > i
                && constraints.tempWidth < nextSize)
                    nextSize = constraints.tempWidth;

                if (constraints.tempHeight == i) {
                    py = constraints.tempY + constraints.tempHeight;

                    weight_diff = constraints.weighty;
                    for (k = constraints.tempY; k < py; k++)
                        weight_diff -= r.weightY[k];
                    if (weight_diff > 0.0) {
                        weight = 0.0;
                        for (k = constraints.tempY; k < py; k++)
                            weight += r.weightY[k];
                        for (k = constraints.tempY;
                        weight > 0.0 && k < py;
                        k++) {
                            double wt = r.weightY[k];
                            double dy = (wt * weight_diff) / weight;
                            r.weightY[k] += dy;
                            weight_diff -= dy;
                            weight -= wt;
                        }
                        r.weightY[py - 1] += weight_diff;
                    }

                    pixels_diff =
                    constraints.minHeight
                    + constraints.ipady
                    + constraints.insets.top
                    + constraints.insets.bottom;
                    for (k = constraints.tempY; k < py; k++)
                        pixels_diff -= r.minHeight[k];
                    if (pixels_diff > 0) {
                        weight = 0.0;
                        for (k = constraints.tempY; k < py; k++)
                            weight += r.weightY[k];
                        for (k = constraints.tempY;
                        weight > 0.0 && k < py;
                        k++) {
                            double wt = r.weightY[k];
                            int dy =
                            (int) ((wt * ((double) pixels_diff))
                            / weight);
                            r.minHeight[k] += dy;
                            pixels_diff -= dy;
                            weight -= wt;
                        }
                        r.minHeight[py - 1] += pixels_diff;
                    }
                } else if (
                constraints.tempHeight > i
                && constraints.tempHeight < nextSize)
                    nextSize = constraints.tempHeight;
            }
        }

        return r;
    }
    
    protected void moveComponentInsets(GridBagConstraints constraints, Rectangle r) {
        
        int diffx, diffy;
        
        if (!rightToLeft) {
            r.x += constraints.insets.left;
        } else {
            r.x -= r.width - constraints.insets.right;
        }
        r.width -= (constraints.insets.left + constraints.insets.right);
        r.y += constraints.insets.top;
        r.height -= (constraints.insets.top + constraints.insets.bottom);
        
        diffx = 0;
        if ((constraints.fill != GridBagConstraints.HORIZONTAL
        && constraints.fill != GridBagConstraints.BOTH)
        && (r.width > (constraints.minWidth + constraints.ipadx))) {
            diffx = r.width - (constraints.minWidth + constraints.ipadx);
            r.width = constraints.minWidth + constraints.ipadx;
        }
        
        diffy = 0;
        if ((constraints.fill != GridBagConstraints.VERTICAL
        && constraints.fill != GridBagConstraints.BOTH)
        && (r.height > (constraints.minHeight + constraints.ipady))) {
            diffy = r.height - (constraints.minHeight + constraints.ipady);
            r.height = constraints.minHeight + constraints.ipady;
        }
        
        switch (constraints.anchor) {
            case GridBagConstraints.CENTER :
                r.x += diffx / 2;
                r.y += diffy / 2;
                break;
            case GridBagConstraints.PAGE_START :
            case GridBagConstraints.NORTH :
                r.x += diffx / 2;
                break;
            case GridBagConstraints.NORTHEAST :
                r.x += diffx;
                break;
            case GridBagConstraints.EAST :
                r.x += diffx;
                r.y += diffy / 2;
                break;
            case GridBagConstraints.SOUTHEAST :
                r.x += diffx;
                r.y += diffy;
                break;
            case GridBagConstraints.PAGE_END :
            case GridBagConstraints.SOUTH :
                r.x += diffx / 2;
                r.y += diffy;
                break;
            case GridBagConstraints.SOUTHWEST :
                r.y += diffy;
                break;
            case GridBagConstraints.WEST :
                r.y += diffy / 2;
                break;
            case GridBagConstraints.NORTHWEST :
                break;
            case GridBagConstraints.LINE_START :
                if (rightToLeft) {
                    r.x += diffx;
                }
                r.y += diffy / 2;
                break;
            case GridBagConstraints.LINE_END :
                if (!rightToLeft) {
                    r.x += diffx;
                }
                r.y += diffy / 2;
                break;
            case GridBagConstraints.FIRST_LINE_START :
                if (rightToLeft) {
                    r.x += diffx;
                }
                break;
            case GridBagConstraints.FIRST_LINE_END :
                if (!rightToLeft) {
                    r.x += diffx;
                }
                break;
            case GridBagConstraints.LAST_LINE_START :
                if (rightToLeft) {
                    r.x += diffx;
                }
                r.y += diffy;
                break;
            case GridBagConstraints.LAST_LINE_END :
                if (!rightToLeft) {
                    r.x += diffx;
                }
                r.y += diffy;
                break;
            default :
                throw new IllegalArgumentException("illegal anchor value");
        }
    }
    
    protected Dimension getSmallestLayoutSize(Container parent, GridBagLayoutInfo info) {
        Dimension d = new Dimension();
        int i, t;
        Insets insets = parent.getInsets();
        
        t = 0;
        for (i = 0; i < info.width; i++)
            t += info.minWidth[i];
        d.width = t + insets.left + insets.right;
        
        t = 0;
        for (i = 0; i < info.height; i++)
            t += info.minHeight[i];
        d.height = t + insets.top + insets.bottom;
        
        return d;
    }
    
    boolean rightToLeft = false;
    protected static final int MAXGRIDSIZE = 512;
    protected static final int MINSIZE = 1;
    protected static final int PREFERREDSIZE = 2;
}

class GridBagLayoutInfo {
    
    int width;
    int height;
    int startx;
    int starty;
    int minWidth[];
    int minHeight[];
    double weightX[];
    double weightY[];
    
    GridBagLayoutInfo() {
        minWidth = new int[GridBagLayout.MAXGRIDSIZE];
        minHeight = new int[GridBagLayout.MAXGRIDSIZE];
        weightX = new double[GridBagLayout.MAXGRIDSIZE];
        weightY = new double[GridBagLayout.MAXGRIDSIZE];
    }
}
