/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


 */


package swingwtx.swing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import swingwt.awt.Dimension;
import swingwt.awt.Graphics;
import swingwt.awt.Insets;

public class JPanel extends JComponent {

	/** The panel's peer */
	protected Composite ppeer = null;

	protected boolean opaque = true;

	/** 
	 * Creates a new JPanel. 
	 */
	public JPanel() {
		this(false);
	}

	/**
	 * Creates a new JPanel with the specified buffering scheme
	 * 
	 * @param isDoubleBuffered Whether or not to double buffer paint callbacks
	 */
	public JPanel(boolean isDoubleBuffered) {
		this(new swingwt.awt.FlowLayout(), false);
	}

	/**
	 * Creates a new JPanel with the specified layout
	 * 
	 * @param layout The layout manager to use
	 */
	public JPanel(swingwt.awt.LayoutManager layout) {
		setLayout(layout);
	}

	/**
	 * Creates a new JPanel with the specified layout and buffering scheme
	 * 
	 * @param layout The layout manager to use
	 * @param isDoubleBuffered Whether or not to double buffer paint callbacks
	 */
	public JPanel(swingwt.awt.LayoutManager layout, boolean isDoubleBuffered) {
		setLayout(layout);
	}

	/**
	 * Creates a new JPanel with the specified layout
	 * 
	 * @param layout The layout manager to use
	 */
	public JPanel(swingwt.awt.LayoutManager2 layout) {
		setLayout(layout);
	}

	/**
	 * Creates a new JPanel with the specified layout and buffering scheme
	 * 
	 * @param layout The layout manager to use
	 * @param isDoubleBuffered Whether or not to double buffer paint callbacks
	 */
	public JPanel(swingwt.awt.LayoutManager2 layout, boolean isDoubleBuffered) {
		setLayout(layout);
	}

	/**
	 * Overriden to calculate non-realised preferred size.
	 */
	protected swingwt.awt.Dimension calculatePreferredSize() {
		// Default 300x200
		swingwt.awt.Dimension size = new swingwt.awt.Dimension(300, 200);
		setSize(size);
		return size;
	}
    
    private boolean usePlatformBorder() {
        boolean usePlatformBorder = 
            (border instanceof swingwtx.swing.border.EtchedBorder) ||
            (border instanceof swingwtx.swing.border.LineBorder) ||
            (border instanceof swingwtx.swing.border.BevelBorder) ||
            (border instanceof swingwtx.swing.border.SoftBevelBorder);
        return usePlatformBorder;
    }

    public Insets getInsets() {
        if (border != null && !usePlatformBorder())
            return border.getBorderInsets(this);
        return new Insets();
    }

    protected void paintBorder(Graphics g) {
        if (usePlatformBorder())
            return;
        super.paintBorder(g);
    }

    /** Callback for when this is added to a Container to create the
     *  peer and load cached values.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;

        ppeer = new Composite(parent.getComposite(), SWT.NO_RADIO_GROUP | (usePlatformBorder() ? SWT.BORDER : SWT.NONE) );
        peer = ppeer;
        composite = ppeer;
        this.parent = parent;
        super.setSwingWTParent(parent);
    }

    /**
     *  Return how large this panel would like to be if it's inside a ScrollPane, rather
     *  than how large it actually is. This is used by layout managers when determining
     *  how to layout the components. If you need the actual size of a JPanel in
     *  a scroll pane, use getPeerSize() which always return the
     *  correct values.
     */
    public swingwt.awt.Dimension getSize() {
        if (parent instanceof JScrollPane)
            return getPreferredSize();
        else
            return super.getSize();
    }
}
