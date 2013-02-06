/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.border;

import org.eclipse.swt.SWT;

import swingwt.awt.*;
import swingwtx.swing.SwingWTUtils;

public class TitledBorder extends AbstractBorder implements Border {

    public static final int DEFAULT_POSITION = 0;
    public static final int ABOVE_TOP = 1;
    public static final int TOP = 2;
    public static final int BELOW_TOP = 3;
    public static final int ABOVE_BOTTOM = 4;
    public static final int BOTTOM = 5;
    public static final int BELOW_BOTTOM = 6;
    public static final int DEFAULT_JUSTIFICATION = 0;
    public static final int LEFT = 1;
    public static final int CENTER = 2;
    public static final int RIGHT = 3;
    public static final int LEADING = 4;
    public static final int TRAILING = 5;
    
    private static int VERTICAL_INSET = SwingWTUtils.getRenderStringHeight("W");
		
    protected String title = "";
    protected Font font = null;
    protected Color color = null;
    protected Border border = null;
    protected int titlePosition;

    public TitledBorder(String title) {this.title = title; this.border = new EmptyBorder(0, 0, 0, 0);}
    public TitledBorder(Border border) {this.border = border;}
    public TitledBorder(Border border, String title) {this.title = title; this.border = border;}
    public TitledBorder(Border border, String title, int titleJustification, int titlePosition) {this.title = title; this.border = border;}
    public TitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont) {this.title = title; font = titleFont; this.border = border; }
    public TitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont, Color titleColor) {this.title = title; font = titleFont; color = titleColor; this.border = border;}
    
    /** Getter for property title.
     * @return Value of property title.
     *
     */
    public java.lang.String getTitle() {
        return title;
    }    
    
    /** Setter for property title.
     * @param title New value of property title.
     *
     */
    public void setTitle(java.lang.String title) {
        this.title = title;
    }    
    
    /** Getter for property font.
     * @return Value of property font.
     *
     */
    public swingwt.awt.Font getFont() {
        return font;
    }
    
    /** Setter for property font.
     * @param font New value of property font.
     *
     */
    public void setFont(swingwt.awt.Font font) {
        this.font = font;
    }
    
    /** Getter for property color.
     * @return Value of property color.
     *
     */
    public swingwt.awt.Color getColor() {
        return color;
    }
    
    /** Setter for property color.
     * @param color New value of property color.
     *
     */
    public void setColor(swingwt.awt.Color color) {
        this.color = color;
    }
    
    /** Getter for property border.
     * @return Value of property border.
     *
     */
    public swingwtx.swing.border.Border getBorder() {
        return border;
    }
    
    /** Setter for property border.
     * @param border New value of property border.
     *
     */
    public void setBorder(swingwtx.swing.border.Border border) {
        this.border = border;
    }

    // If you prefer this design, you may want to fix the getBorderInsets() method to add more
    // space to the top
    public void paintBorderOldDesign(Component c, Graphics g, int x, int y, int width, int height) {
        /**
         * This is a bit of a hack (and doesn't create perfect Swing-like behavior),
         * but IMO looks nicer than drawing a solid black line.  If the SWT control happens to have
         * the SWT.BORDER style bit set (automatically done in JPanels if the border is an instance of
         * TitledBorder), then don't draw the border.  Instead, we just draw the title offset slighly.
         */
        
        g.setColor( SystemColor.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW) );

        if (font != null) g.setFont( font );
        FontMetrics metrics = c.getFontMetrics(g.getFont());
        int titleWidth = title != null ? metrics.stringWidth(title) : 0;
        int padding = 1;

        g.drawLine(2, 10, 8 - padding, 10);
        g.drawLine(8 + titleWidth + padding, 10, width - 3, 10);
        g.drawLine(2, 10, 2, height - 3);
        g.drawLine(2, height - 3, width - 2, height - 3);
        g.drawLine(width - 3, 10, width - 3, height - 3);
        
        if (color != null) g.setColor( color );
        else g.setColor( SystemColor.getSystemColor(SWT.COLOR_WIDGET_BORDER) );

        if (title != null)
            g.drawString(title, 8, metrics.getHeight()/2 + 9);
    }

    /**
     * Size of the left, bottom and right parts of the border, in pixels.
     */
    public static final int EXTERNAL_SIZE = 4;
    
    /**
     * Padding between the outside of the border and the place were the line is drawn.
     */
    public static final int EXTERNAL_PADDING = 3;
    
    /**
     * Padding between the small top line and starting the title, as well as after the
     * title and the continuation of the line.
     */
    public static final int TITLE_PADDING = 2;
    
    /**
     * How many pixels bellow the top line should the title be drawn.
     */
    public static final int BELLOW_TOP_LINE = 4;
    
    
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        
    	/**
         * This is a bit of a hack (and doesn't create perfect Swing-like behavior),
         * but IMO looks nicer than drawing a solid black line.  If the SWT control happens to have
         * the SWT.BORDER style bit set (automatically done in JPanels if the border is an instance of
         * TitledBorder), then don't draw the border.  Instead, we just draw the title offset slighly.
         */
        g.setColor( SystemColor.getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW) );
        if (font != null)
        	g.setFont( font );
        FontMetrics metrics = c.getFontMetrics(g.getFont());
        
        // Compute corners and paddings
    	int left_x = EXTERNAL_PADDING + x;
    	int right_x = width - EXTERNAL_PADDING + x;
    	int top_y = 2 * EXTERNAL_PADDING + y;
    	int bottom_y = height - EXTERNAL_PADDING + y;
    	
    	int smallTopLine = 5;
        int titleWidth = title != null ? metrics.stringWidth(title) : 0;
               
        // top line, with some space for the title
        g.drawLine(left_x, top_y, left_x + smallTopLine, top_y);
        g.drawLine(left_x + smallTopLine + TITLE_PADDING + titleWidth + TITLE_PADDING, top_y, right_x, top_y);
        
        // left
        g.drawLine(left_x, top_y, left_x, bottom_y);
        
        // bottom
        g.drawLine(left_x, bottom_y, right_x, bottom_y);
        
        // right
        g.drawLine(right_x, top_y, right_x, bottom_y);
        
        if (title != null) {
            // The title goes in the selected (or default) color
            if (color != null) 
            	g.setColor( color );
            else 
            	g.setColor(SystemColor.getSystemColor(SWT.COLOR_WIDGET_BORDER));

            g.drawString(title, left_x + smallTopLine + TITLE_PADDING, top_y + BELLOW_TOP_LINE);
        }
    }
    
    
    public Insets getBorderInsets(Component c) {
        return getBorderInsets(c, new Insets());
    }

    public Insets getBorderInsets(Component c, Insets insets) {
    	
    	insets.top = VERTICAL_INSET;
        insets.left = insets.right = insets.bottom = EXTERNAL_SIZE;
        return insets;
    }
    
    public void setTitleJustification( int titleJustification) {}
    public int getTitleJustification() { return DEFAULT_JUSTIFICATION; }
    
    public Font getTitleFont() {
        Font font = getFont();
        if (font == null) {
            font = Font.getStubFont();
        }
        return font;
    }
    
    public void setTitleFont(Font font2) {
        setFont(font2);
    }
    
    public void setTitleColor(Color color) {
        setColor(color);
    }
    
    public Color getTitleColor() {
        return getColor();
    }

    public void setTitlePosition(int titlePosition) {
        this.titlePosition = titlePosition;
    }

    public int getTitlePosition() {
        return titlePosition;
    }
    
    /**
  	 * Sub-types can implement this to provide a better identifier during debug. 
  	 *
  	 * @return
  	 */
  	public String debugId() {
  		return title;
  	}
}
