/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.plaf;

import swingwtx.swing.JComponent;
import swingwtx.swing.SwingUtilities;
import swingwtx.accessibility.Accessible;

import swingwt.awt.Graphics;
import swingwt.awt.Dimension;

/**
 * @author  Naab
 * @version %I%, %G%
 */
public abstract class ComponentUI
{
    /**
     * createUI - must be implemented by subclasses
     * Not sure why Sun didn't make this abstract.
     * @param component
     * @return
     */
    public static ComponentUI createUI(JComponent component)
    {
        throw new UnsupportedOperationException(
                "createUI(JComponent) must be implemented by final ComponentUI implementation!");
    }

    // Default ComponentUI install/uninstall - do nothing.
    public void installUI(JComponent c) {}
    public void uninstallUI(JComponent c) {}

    public void paint(Graphics g, JComponent c) {}
    public void update(Graphics g, JComponent c)
    {
        if (c.isOpaque())
        {
            g.setColor(c.getBackground());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        paint(g, c);
    }

    /** Default component size methods - return null */
    public Dimension getPreferredSize(JComponent component) { return null; }
    public Dimension getMinimumSize(JComponent component) { return getPreferredSize(component); }
    public Dimension getMaximumSize(JComponent component) { return getPreferredSize(component); }

    /**
     * Should return true if coordinates are inside this component's screen real-estate.
     * Currently, always returns false because SWT doesn't have this notion.
     * @param component
     * @param x
     * @param y
     * @return always returns false
     */
    // TODO: implement
    //public boolean contains(JComponent component, int x, int y) { return component.inside(x, y); }
    public boolean contains(JComponent component, int x, int y) { return false; }

    /** Accessible methods - currently just stubbed out in SwingUtilities */
    public int getAccessibleChildrenCount(JComponent c) { return SwingUtilities.getAccessibleChildrenCount(c); }
    public Accessible getAccessibleChild(JComponent c, int i) { return SwingUtilities.getAccessibleChild(c, i); }
}
