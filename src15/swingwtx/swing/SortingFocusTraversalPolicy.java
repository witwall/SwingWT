package swingwtx.swing;

import swingwt.awt.*;
import java.util.*;
import java.lang.*;

public class SortingFocusTraversalPolicy extends InternalFrameFocusTraversalPolicy
{
    protected SortingFocusTraversalPolicy() {
    }
    public SortingFocusTraversalPolicy(Comparator<? super Component> comparator) 
    {
    }
    public Component getComponentAfter(Container aContainer, Component aComponent) 
    {
    	return null;
    }
    public Component getComponentBefore(Container aContainer, Component aComponent) {
    	return null;
    }
    public Component getFirstComponent(Container aContainer) {
        return null;
    }
    public Component getLastComponent(Container aContainer) {
        return null;
    }
    public Component getDefaultComponent(Container aContainer) {
        return getFirstComponent(aContainer);
    }
    public void setImplicitDownCycleTraversal(boolean implicitDownCycleTraversal) {
    }
    public boolean getImplicitDownCycleTraversal() {
	return false;
    }
    protected void setComparator(Comparator<? super Component> comparator) 
    {
    }
    protected Comparator<? super Component> getComparator() {
		return null;
    }
    protected boolean accept(Component aComponent) 
    {
    	return false;
    }
}

