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

public class SWTGridLayout extends Layout
{
    private int _hgap;
    private int _vgap;
    private int _rows;
    private int _columns;

    public SWTGridLayout()
    {
        this(1, 0);
    }

    public SWTGridLayout(int rows, int columns)
    {
        this(rows, columns, 0, 0);
    }

    public SWTGridLayout(int rows, int columns, int hgap, int vgap)
    {
        if (rows == 0 && columns == 0)
            throw new IllegalArgumentException("rows and cols cannot both be zero");
        if (rows != 0)
            setRows(rows);
        setColumns(columns);
        setHgap(hgap);
        setVgap(vgap);
    }

    protected void layout(Composite target, boolean flushCache)
    {
        Rectangle r = target.getClientArea();
        Control children[] = target.getChildren();
        int ncomponents = children.length;
        int nrows = getRows();
        int ncols = getColumns();
        if (ncomponents == 0)
            return;
        if (nrows > 0)
            ncols = (ncomponents + nrows - 1) / nrows;
        else
            nrows = (ncomponents + ncols - 1) / ncols;
        int w = r.width;
        int h = r.height;
        w = (w - (ncols - 1) * getHgap()) / ncols;
        h = (h - (nrows - 1) * getVgap()) / nrows;
        int col = 0;
        int x = 0;
        while (col < ncols)
        {
            int row = 0;
            int y = 0;
            while (row < nrows)
            {
                int i = row * ncols + col;
                if (i < ncomponents)
                    children[i].setBounds(x, y, w, h);
                row++;
                y += h + getVgap();
            }
            col++;
            x += w + getHgap();
        }
    }

    protected Point computeSize(Composite composite, int wHint, int hHint, boolean flushCache)
    {
        Control children[] = composite.getChildren();
        int ncomponents = children.length;
        int nrows = getRows();
        int ncols = getColumns();
        if (nrows > 0)
            ncols = (ncomponents + nrows - 1) / nrows;
        else
            nrows = (ncomponents + ncols - 1) / ncols;
        int w = 0;
        int h = 0;
        for (int i = 0; i < ncomponents; i++)
        {
            Control comp = children[i];
            Point d = comp.computeSize(-1, -1);
            if (w < d.x)
                w = d.x;
            if (h < d.y)
                h = d.y;
        }
        return new Point(ncols * w + (ncols - 1) * getHgap(), nrows * h + (nrows - 1) * getVgap());
    }

    public String toString()
    {
        return "[" + paramString() + "]";
    }

    protected String paramString()
    {
        return getHgap() + ",vgap=" + getVgap() + ",rows=" + getRows() + ",cols=" + getColumns();
    }

    public int getRows()
    {
        return _rows;
    }

    public void setRows(int rows)
    {
        if (rows == 0 && getColumns() == 0)
            throw new IllegalArgumentException("rows and cols cannot both be zero");
        _rows = rows;
    }

    public int getColumns()
    {
        return _columns;
    }

    public void setColumns(int columns)
    {
        if (columns == 0 && getRows() == 0)
            throw new IllegalArgumentException("rows and cols cannot both be zero");
        _columns = columns;
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
}
