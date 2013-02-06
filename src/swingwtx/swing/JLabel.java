/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing;

import org.eclipse.swt.*;

import swingwt.awt.*;

public class JLabel extends JPanel implements SwingConstants {

    private JPanel innerPanel;
    private JComponent gap;
    private TextLabel textLabel;
    private IconLabel iconLabel;

    /** A cache of the label's text */
    protected String pText = "";
    /** A cache of the label's icon */
    protected Icon pImage = null;
    /** The label's horizontal text position */
    protected int pHTextPosition = LEFT;
    /** The label's vertical text position */
    protected int pVTextPosition = TOP;
    /** The label's horizontal alignment */
    protected int pHAlign = LEFT;
    /** The label's vertical alignment */
    protected int pVAlign = TOP;
    /** If this is the label for a particular component, then show the one it's tied to */
    protected Component labelFor = null;
    
    /** Creates a new JLabel with empty text and no icon */
    public JLabel() {
        init(null, null, RIGHT, CENTER);
    }

    /** Creates a new JLabel with the specified text 
     *  @param text The text of the label
     */
    public JLabel(String text) {
        init(text, null, LEFT, TOP);
    }

    /** Creates a new JLabel with the specified text and alignment 
     *  @param text The text of the label
     *  @param align The label's horizontal alignment
     */
    public JLabel(String text, int align) {
        init(text, null, align, TOP);
    }

    /** Creates a new JLabel with the specified icon 
     *  @param icon The label's icon
     */
    public JLabel(Icon icon) {
        init(null, icon, LEFT, TOP);
    }

    public JLabel(String text, Icon icon, int align) {
        init(text, icon, align, TOP);
    }

    public JLabel(Icon icon, int alignment) {
        init(null, icon, alignment, TOP);
    }

    private void init(String text, Icon icon, int hAlign, int vAlign) {
        pText = text;
        pImage = icon;
        pHAlign = hAlign;
        pVAlign = vAlign;
        gap = new JComponent();
        gap.setPreferredSize(new Dimension(4, 4));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        innerPanel = new JPanel();
        add(innerPanel);
        updateComponents();
    }

    private TextLabel createTextLabel() {
        if (pText == null)
            return null;
        TextLabel label = new TextLabel(pText);
        label.setFont(getFont());
        if (pVAlign == BOTTOM)
            label.setAlignmentY(BOTTOM_ALIGNMENT);
        else if (pVAlign == TOP)
            label.setAlignmentY(TOP_ALIGNMENT);
        else
            label.setAlignmentY(CENTER_ALIGNMENT);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setToolTipText(getToolTipText());
        return label;
    }

    private IconLabel createIconLabel() {
        if (pImage == null)
            return null;
        IconLabel label = new IconLabel(pImage);
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setToolTipText(getToolTipText());
        return label;
    }

    public Dimension getMaximumSize() {
        if (isMaximumSizeSet())
            return super.getMaximumSize();
        return getPreferredSize();
    }

    private void updateComponents() {
        innerPanel.removeAll();

        textLabel = createTextLabel();
        iconLabel = createIconLabel();

        if (textLabel == null || iconLabel == null) {
            if (textLabel != null) {
                innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
                innerPanel.add(Box.createVerticalGlue());
                innerPanel.add(textLabel);
                innerPanel.add(Box.createVerticalGlue());
            } else if (iconLabel != null) {
                innerPanel.setLayout(new BorderLayout());
                innerPanel.add(iconLabel);             
            }
            return;
        }

        int orientation = (pHAlign == CENTER ? BoxLayout.Y_AXIS : BoxLayout.X_AXIS);
        innerPanel.setLayout(new BoxLayout(innerPanel, orientation));
        if (pHAlign == LEFT || (pHAlign == CENTER && pVAlign == TOP)) {
            innerPanel.add(textLabel);
            innerPanel.add(gap);
            innerPanel.add(iconLabel);
        } else {
            innerPanel.add(iconLabel);
            innerPanel.add(gap);
            innerPanel.add(textLabel);
        }
    }

    /**
     * @return The text on the label
     */
    public String getText() {
        return pText;
    }

    /**
     * Sets the label's text
     * @param text The new text
     */
    public void setText(String text) {
        pText = text;
        updateComponents();
    }
    
    /**
     * If this label is accompanying a component, the component
     * @param c The component this label is accompanying
     */
    public void setLabelFor(Component c) { labelFor = c; }
    /**
     * If this label is accompanying a component, the component
     * @return The component
     */
    public Component getLabelFor() { return labelFor; }
    
    /** NOT IMPLEMENTED */
    public void setDisplayedMnemonic(char c) {}
    /** NOT IMPLEMENTED */
    public void setDisplayedMnemonic(int c) {}
    
    /**
     * Sets the icon for this label
     * @param icon The icon to display in the label
     */
    public void setIcon(Icon icon) {
        pImage = icon;
        updateComponents();
    }

    /** Gets the icon for this label
     *  @return The icon
     */
    public Icon getIcon() { return pImage; }
    
    /** NOT IMPLEMENTED */
    public Icon getDisabledIcon() { return null; }
    /** NOT IMPLEMENTED */
    public void setDisabledIcon(Icon icon) { }

    public void setToolTipText(String text) {
        super.setToolTipText(text);
        if (textLabel != null)
            textLabel.setToolTipText(getToolTipText());
        if (iconLabel != null)
            iconLabel.setToolTipText(getToolTipText());
    }

    public void setFont(Font font) {
        super.setFont(font);
        if (textLabel != null)
            textLabel.setFont(font);
    }

     /**
      * Sets the horizontal alignment for this label. Use one of 
      * LEADING, LEFT, CENTER, RIGHT, TRAILING from SwingConstants
      * @param align The alignment
      */
    public void setHorizontalAlignment(int align) {
        pHAlign = align;
        updateComponents();
    }

     /**
      * Sets the vertical alignment for this label. Use one of 
      * LEADING, LEFT, CENTER, RIGHT, TRAILING from SwingConstants
      * @param align The alignment
      */
    public void setVerticalAlignment(int align) {
        pVAlign = align;
        updateComponents();
    }

    /**
     * Gets the horizontal alignment for the label
     * @return The horizontal alignment
     */
    public int getHorizontalAlignment() { return pHAlign; }
    /**
     * Gets the vertical alignment for the label
     * @return The vertical alignment
     */
    public int getVerticalAlignment() { return pVAlign; }
    /** No meaning in native, calls across to setHorizontalAlignment() */
    public void setHorizontalTextPosition(int textpos) { setHorizontalAlignment(textpos); }
    /** No meaning in native, calls across to setVerticalAlignment() */
    public void setVerticalTextPosition(int textpos) { setVerticalAlignment(textpos); }
    /** No meaning in native, calls across to getHorizontalAlignment() */
    public int getHorizontalTextPosition() { return getHorizontalAlignment(); }
    /** No meaning in native, calls across to getVerticalAlignment() */
    public int getVerticalTextPosition() { return getVerticalAlignment(); }

    private static class TextLabel extends JComponent implements Runnable {
        private org.eclipse.swt.widgets.Label ppeer;
        private String text;
        
        public TextLabel(String text) {
            this.text = text;
        }

        public void run() {
            ppeer.setText(SwingWTUtils.removeHTML(text, true));
        }

        public void setText(String text) {
            this.text = text;
            if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                SwingUtilities.invokeSync(this);
            }
        }

        protected swingwt.awt.Dimension calculatePreferredSize() {
            FontMetrics metrics = getFontMetrics(getFont());
            Dimension size = new Dimension(metrics.stringWidth(text), metrics.getHeight());
            setSize(size);
            return size;
        }
    
        public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
            descendantHasPeer = true;
            ppeer = new org.eclipse.swt.widgets.Label(parent.getComposite(), SWT.NONE);
            run();
            peer = ppeer;
            this.parent = parent;
        }
    }

    private static class IconLabel extends JComponent implements Runnable {
        private org.eclipse.swt.widgets.Label ppeer;
        private Icon icon;

        public IconLabel(Icon icon) {
            this.icon = icon;
        }

        public void run() {
            ppeer.setImage(SwingWTUtils.getSWTImageFromSwingIcon(this, icon));
            // Get the background of a parent that has it set
            Container c = this;
            while(c != null && ! c.isBackgroundSet())
            	c = c.getParent();
            if (null != c)
            	this.setBackground(c.getBackground());
        }

        public void setIcon(Icon icon) {
            this.icon = icon;
            if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                SwingUtilities.invokeSync(this);
            }
        }

        protected Dimension calculatePreferredSize() {
            Dimension size = new Dimension(icon.getIconWidth(), icon.getIconHeight());
            setSize(size);
            return size;
        }

        public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
            descendantHasPeer = true;
            ppeer = new org.eclipse.swt.widgets.Label(parent.getComposite(), SWT.CENTER);
            run();
            peer = ppeer;
            this.parent = parent;
        }
    }
    
	// DEBUG SUPPORT

	/**
	 * Sub-types can override this to provide a better identifier during debug.
	 * 
	 * @return
	 */
	public String debugId() {
		return "JLabel [\"" + this.getText() + "\"]";
	}
}
