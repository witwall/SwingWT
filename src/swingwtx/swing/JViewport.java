/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;

import java.util.Vector;

import swingwt.awt.*;
import swingwtx.swing.event.ChangeEvent;
import swingwtx.swing.event.ChangeListener;

/**
 * Simple JViewport class. This could be expanded at a
 * later date.
 *
 * @author  Robin Rawson-Tetley
 */
public class JViewport extends JPanel {
    
    Vector changeListeners = null;
    
    public JViewport() {
        super(new BorderLayout());
    }

    public Component getView() {
        return getComponents()[0];
    }
    
    public void setView(Component view) {
        
        // Remove all components
        Component[] components = super.getComponents();
        for (int i=0; i<components.length; i++) {
            super.remove(components[i]);
        }
        
        if (view != null) add(view, BorderLayout.CENTER);
    }
    
    public Point getViewPosition() {
        return getLocation();
    }
    
    public void setViewPosition(Point position) {
        setLocation(position);
    }
    
    public Rectangle getViewRect() {
        return getBounds();    
    }
    
    public Dimension getViewSize() {
        return getSize();    
    }
    
    public void addChangeListener(ChangeListener changeListener) {
        if (changeListeners == null) changeListeners = new Vector();
        changeListeners.add(changeListener);
    }
    
    public void removeChangeListener(ChangeListener changeListener) {
        if (changeListeners != null)
            changeListeners.remove(changeListener);
    }
    
    protected void fireStateChanged()
    {
        if (changeListeners != null) {
	        ChangeEvent changeEvent = new ChangeEvent(this);
	        for (int i = 0; i < changeListeners.size(); i++) {
	            ((ChangeListener)changeListeners.get(i)).stateChanged(changeEvent);
	        }
        }
    }

    public Dimension getExtentSize() {
        // TODO
        return getSize();
    }
}
