package swingwtx.swing;

import swingwt.awt.Dimension;
import swingwt.awt.Rectangle;

/**
 * @author Dan
 *
 */
public interface Scrollable
{
    Dimension getPreferredScrollableViewportSize();
    int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction);
    boolean getScrollableTracksViewportHeight();
    boolean getScrollableTracksViewportWidth();
    int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction);
}
