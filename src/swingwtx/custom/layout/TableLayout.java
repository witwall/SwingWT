/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.custom.layout;

import swingwt.awt.*;

/**
 * Well here's a shocker - I didn't expect to be implementing
 * an SWT layout in AWT!
 *
 * @author  Robin Rawson-Tetley
 */
public class TableLayout implements LayoutManager, java.io.Serializable {

	protected int hgap = 4;
	protected int vgap = 4;
	protected int margin = 0;
	protected int rows;
	protected int cols;
        /** 
         *  Lays out left to right by default, otherwise lays out top
         *  to bottom going left to right (like newspaper).
         */
        protected boolean leftToRight = true;

	public TableLayout() {
		this(1, 0, 4, 4);
	}

	public TableLayout(int rows, int cols) {
		this(rows, cols, 4, 4, true);
	}
        
        public TableLayout(int rows, int cols, boolean leftToRight) {
		this(rows, cols, 4, 4, leftToRight);
	}

        public TableLayout(int rows, int cols, int hgap, int vgap) {
                this(rows, cols, hgap, vgap, true);
        }
        
	public TableLayout(int rows, int cols, int hgap, int vgap, boolean leftToRight) {
		if ((rows == 0) && (cols == 0)) {
			throw new IllegalArgumentException("rows and cols cannot both be zero");
		}
		this.rows = rows;
		this.cols = cols;
		this.hgap = hgap;
		this.vgap = vgap;
                this.leftToRight = leftToRight;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		if ((rows == 0) && (this.cols == 0)) {
			throw new IllegalArgumentException("rows and cols cannot both be zero");
		}
		this.rows = rows;
	}

	public int getColumns() {
		return cols;
	}

	public void setColumns(int cols) {
		if ((cols == 0) && (this.rows == 0)) {
			throw new IllegalArgumentException("rows and cols cannot both be zero");
		}
		this.cols = cols;
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

	public void setMargin(int margin) {
		this.margin = margin;
	}

	public int getMargin() {
		return margin;
	}

	public void addLayoutComponent(String name, Component comp) {
	}

	public void removeLayoutComponent(Component comp) {
	}

	public Dimension preferredLayoutSize(Container parent) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();
			int nrows = rows;
			int ncols = cols;

			if (nrows > 0) {
				ncols = (ncomponents + nrows - 1) / nrows;
			} else {
				nrows = (ncomponents + ncols - 1) / ncols;
			}
			int w = 0;
			int h = 0;
			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getPreferredSize();
				if (w < d.width) {
					w = d.width;
				}
				if (h < d.height) {
					h = d.height;
				}
			}
			return new Dimension(
				insets.left + insets.right + ncols * w + (ncols - 1) * hgap,
				insets.top + insets.bottom + nrows * h + (nrows - 1) * vgap);
	}

	public Dimension minimumLayoutSize(Container parent) {
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();
			int nrows = rows;
			int ncols = cols;

			if (nrows > 0) {
				ncols = (ncomponents + nrows - 1) / nrows;
			} else {
				nrows = (ncomponents + ncols - 1) / ncols;
			}
			int w = 0;
			int h = 0;
			for (int i = 0; i < ncomponents; i++) {
				Component comp = parent.getComponent(i);
				Dimension d = comp.getMinimumSize();
				if (w < d.width) {
					w = d.width;
				}
				if (h < d.height) {
					h = d.height;
				}
			}
			return new Dimension(
				insets.left + insets.right + ncols * w + (ncols - 1) * hgap,
				insets.top + insets.bottom + nrows * h + (nrows - 1) * vgap);
	}

	public void layoutContainer(Container parent) {
                    
			Insets insets = parent.getInsets();
			int ncomponents = parent.getComponentCount();
			int nrows = rows;
			int ncols = cols;

			if (ncomponents == 0) {
				return;
			}
			
                        if (leftToRight) {
                            // Scan the list of components in the container and find the widest
                            // one for each colum
                            int[] maxWidths = new int[(cols)];

                            int cc = 0; // current column
                            for (int i = 0; i < ncomponents; i++) {
                                int cw = parent.getComponent(i).getPreferredSize().width;
                                if (cw > maxWidths[cc]) 
                                    maxWidths[cc] = cw;
                                cc++;
                                if (cc == cols) cc = 0;
                            }

                            // Now generate a list of starting points for the columns, based
                            // on the hgap and the widest values for each.
                            int[] colPoints = new int[(cols)];
                            for (int i = 0; i < cols; i++) {
                                if (i != 0)
                                    colPoints[i] = colPoints[i-1] + hgap + maxWidths[i-1];
                                else
                                    colPoints[i] = hgap + margin;
                            }

                            cc = 0;
                            int highestInRow = 0;
                            int curRowPos = vgap + margin;
                            for (int i = 0; i < parent.getComponentCount(); i++) {
                                parent.getComponent(i).setBounds(
                                    colPoints[cc], 
                                    curRowPos,
                                    parent.getComponent(i).getPreferredSize().width,
                                    parent.getComponent(i).getPreferredSize().height
                                    );
                                if (parent.getComponent(i).getPreferredSize().height > highestInRow)
                                    highestInRow = parent.getComponent(i).getPreferredSize().height;
                                cc++;
                                if (cc == cols) {
                                    cc = 0;
                                    // Start laying out at the tallest component on our
                                    // row plus the HGap
                                    curRowPos += highestInRow + vgap;
                                    highestInRow = 0;
                                }
                            }
                    }
                    else {
                        
                        // We are laying the objects out top to bottom going left to
                        // right instead of left to right top to bottom.

                        // Work out how many rows to use based on the size of the
                        // container and the height of the first object (and vgap)
                        // hacky I know, but good enough
                        int totRows = parent.getHeight() / (parent.getComponent(0).getPreferredSize().height + vgap);

                        // Scan the list of components in the container and find the widest
                        // one for each colum
                        int[] maxWidths = new int[(cols * 20)];

                        int cr = 0; // current row
                        int cc = 0; // current col
                        for (int i = 0; i < ncomponents; i++) {
                            int ch = parent.getComponent(i).getPreferredSize().width;
                            if (ch > maxWidths[cc]) 
                                maxWidths[cc] = ch;
                            cr++;
                            if (cr == totRows) { cr = 0; cc++; }
                        }
                        // If there are more components than fit in the columns given
                        // screen width, then ramp up the cols to match
                        if (cols < cc) cols = cc;

                        // Now generate a list of starting points for the columns, based
                        // on the hgap and the widest values for each.
                        int[] colPoints = new int[(cols * 2)];
                        for (int i = 0; i < cols; i++) {
                            if (i != 0)
                                colPoints[i] = colPoints[i-1] + hgap + maxWidths[i-1];
                            else
                                colPoints[i] = hgap + margin;
                        }

                        cc = 0;
                        int curRowPos = vgap + margin;
                        cr = 0;
                        for (int i = 0; i < parent.getComponentCount(); i++) {
                            parent.getComponent(i).setBounds(
                                colPoints[cc], 
                                curRowPos,
                                parent.getComponent(i).getPreferredSize().width,
                                parent.getComponent(i).getPreferredSize().height
                                );
                            cr++;

                            // Vert pos
                            curRowPos += parent.getComponent(i).getPreferredSize().height + vgap;

                            if (cr == totRows) {
                                cr = 0;
                                curRowPos = 0;
                                cc++;
                            }
                        }
                    }
            }
}
