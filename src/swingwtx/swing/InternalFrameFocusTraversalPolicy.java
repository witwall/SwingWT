package swingwtx.swing;

import swingwt.awt.FocusTraversalPolicy;
import swingwt.awt.Component;


public abstract class InternalFrameFocusTraversalPolicy extends FocusTraversalPolicy
{
    public Component getInitialComponent(JInternalFrame frame) 
    {
        return getDefaultComponent(frame);
    }
}
