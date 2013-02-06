/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwt.awt.swtcustomlayout;



import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class SWTBorderLayout extends Layout
{
    private boolean _cached;
    private int _hgap;
    private int _vgap;
    private int _north;
    private int _west;
    private int _east;
    private int _south;
    private int _center;
    private int _firstLine;
    private int _lastLine;
    private int _firstItem;
    private int _lastItem;
    protected static final int INT_NONE = -1;
    protected static final int INT_NORTH = 0;
    public static final Integer NORTH = new Integer(0);
    protected static final int INT_SOUTH = 1;
    public static final Integer SOUTH = new Integer(1);
    protected static final int INT_EAST = 2;
    public static final Integer EAST = new Integer(2);
    protected static final int INT_WEST = 3;
    public static final Integer WEST = new Integer(3);
    protected static final int INT_CENTER = 4;
    public static final Integer CENTER = new Integer(4);
    protected static final int INT_BEFORE_FIRST_LINE = 5;
    public static final Integer BEFORE_FIRST_LINE = new Integer(5);
    protected static final int INT_AFTER_LAST_LINE = 6;
    public static final Integer AFTER_LAST_LINE = new Integer(6);
    protected static final int INT_BEFORE_LINE_BEGINS = 7;
    public static final Integer BEFORE_LINE_BEGINS = new Integer(7);
    protected static final int INT_AFTER_LINE_ENDS = 8;
    public static final Integer AFTER_LINE_ENDS = new Integer(8);

    static 
    {
    }

    public SWTBorderLayout()
    {
        this(0, 0);
    }

    public SWTBorderLayout(int hgap, int vgap)
    {
        setHgap(hgap);
        setVgap(vgap);
    }

    public int getHgap()
    {
        return _hgap;
    }

    public void setHgap(int hgap)
    {
        _hgap = hgap;
    }

    public int getVgap()
    {
        return _vgap;
    }

    public void setVgap(int vgap)
    {
        _vgap = vgap;
    }

    protected void determineControlPositions(Control controls[])
    {
        setCenter(-1);
        setNorth(-1);
        setSouth(-1);
        setEast(-1);
        setWest(-1);
        setFirstLine(-1);
        setLastLine(-1);
        setFirstItem(-1);
        setLastItem(-1);
        for (int i = 0; i < controls.length; i++)
        {
            int position;
            Control control = controls[i];
            Object layoutData = control.getLayoutData();
            if (layoutData != null && !(layoutData instanceof Integer))
                throw new IllegalArgumentException("Bad control layout data");
            Integer positionAsInteger = (Integer)layoutData;
            if (positionAsInteger == null)
                position = 4;
            else
                position = positionAsInteger.intValue();
            switch (position)
            {
            case 4:
                setCenter(i);
                break;

            case 0:
                setNorth(i);
                break;

            case 1:
                setSouth(i);
                break;

            case 2:
                setEast(i);
                break;

            case 3:
                setWest(i);
                break;

            case 5:
                setFirstLine(i);
                break;

            case 6:
                setLastLine(i);
                break;

            case 7:
                setFirstItem(i);
                break;

            case 8:
                setLastItem(i);
                break;

            default:
                throw new IllegalArgumentException(new StringBuffer("cannot add to layout: unknown constraint: ").append(position).toString());
            }
            setCached(true);
        }
    }

    protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache)
    {
        Control children[] = composite.getChildren();
        if (!isCached() || flushCache)
            determineControlPositions(children);
        Point size = new Point(0, 0);
        boolean ltr = true;
        Control c = null;
        if ((c = getChild(children, 2, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            size.x += d.x + getHgap();
            size.y = Math.max(d.y, size.y);
        }
        if ((c = getChild(children, 3, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            size.x += d.x + getHgap();
            size.y = Math.max(d.y, size.y);
        }
        if ((c = getChild(children, 4, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            size.x += d.x;
            size.y = Math.max(d.y, size.y);
        }
        if ((c = getChild(children, 0, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            size.x = Math.max(d.x, size.x);
            size.y += d.y + getVgap();
        }
        if ((c = getChild(children, 1, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            size.x = Math.max(d.x, size.x);
            size.y += d.y + getVgap();
        }
        return size;
    }

    protected void layout(Composite composite, boolean flushCache)
    {
        Control children[] = composite.getChildren();
        if (!isCached() || flushCache)
            determineControlPositions(children);
        Rectangle r = composite.getClientArea();
        int count = children.length;
        if (count == 0)
            return;
        boolean ltr = true;
        Control c = null;
        if ((c = getChild(children, 0, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            c.setBounds(r.x, r.y, r.width, d.y);
            int delta = d.y + getVgap();
            r.y += delta;
        }
        if ((c = getChild(children, 1, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            c.setBounds(r.x, r.height - d.y, r.width, d.y);
            r.height -= d.y + getVgap();
        }
        if ((c = getChild(children, 2, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            c.setBounds(r.width - d.x, r.y, d.x, r.height - r.y);
            r.width -= d.x + getHgap();
        }
        if ((c = getChild(children, 3, ltr)) != null)
        {
            Point d = c.computeSize(-1, -1);
            c.setBounds(r.x, r.y, d.x, r.height - r.y);
            int delta = d.x + getHgap();
            r.x += delta;
        }
        if ((c = getChild(children, 4, ltr)) != null)
            c.setBounds(r.x, r.y, r.width - r.x, r.height - r.y);
    }

    protected Control getChild(Control children[], int key, boolean ltr)
    {
        int result = -1;
        switch (key)
        {
        case 0:
            result = (getFirstLine() != -1) ? getFirstLine() : getNorth();
            break;

        case 1:
            result = (getLastLine() != -1) ? getLastLine() : getSouth();
            break;

        case 3:
            result = ltr ? getFirstItem() : getLastItem();
            if (result == -1)
                result = getWest();
            break;

        case 2:
            result = ltr ? getLastItem() : getFirstItem();
            if (result == -1)
                result = getEast();
            break;

        case 4:
            result = getCenter();
            break;
        }
        if (result != -1 && !children[result].isVisible())
            result = -1;
        if (result == -1)
            return null;
        else
            return children[result];
    }

    public String toString()
    {
        return "[" + paramString() + "]";
    }

    protected String paramString()
    {
        return getHgap() + ",vgap=" + getVgap();
    }

    protected int getWest()
    {
        return _west;
    }

    protected void setWest(int west)
    {
        _west = west;
    }

    protected int getSouth()
    {
        return _south;
    }

    protected void setSouth(int south)
    {
        _south = south;
    }

    protected int getNorth()
    {
        return _north;
    }

    protected void setNorth(int north)
    {
        _north = north;
    }

    protected int getLastLine()
    {
        return _lastLine;
    }

    protected void setLastLine(int lastLine)
    {
        _lastLine = lastLine;
    }

    protected int getLastItem()
    {
        return _lastItem;
    }

    protected void setLastItem(int lastItem)
    {
        _lastItem = lastItem;
    }

    protected int getFirstLine()
    {
        return _firstLine;
    }

    protected void setFirstLine(int firstLine)
    {
        _firstLine = firstLine;
    }

    protected int getFirstItem()
    {
        return _firstItem;
    }

    protected void setFirstItem(int firstItem)
    {
        _firstItem = firstItem;
    }

    protected int getEast()
    {
        return _east;
    }

    protected void setEast(int east)
    {
        _east = east;
    }

    protected int getCenter()
    {
        return _center;
    }

    protected void setCenter(int center)
    {
        _center = center;
    }

    protected boolean isCached()
    {
        return _cached;
    }

    protected void setCached(boolean cached)
    {
        _cached = cached;
    }
}
