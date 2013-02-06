package swingwt.awt;


public abstract class FocusTraversalPolicy {
    public abstract Component getComponentAfter(Container aContainer,Component aComponent);
    public abstract Component getComponentBefore(Container aContainer,Component aComponent);
    public abstract Component getDefaultComponent(Container aContainer);
    public abstract Component getFirstComponent(Container aContainer);
    public Component getInitialComponent(Window window) 
    {
    	Component comp = null;
    	try
    	{
    		comp = getDefaultComponent(window);
    		if (comp == null)
    		{
    			comp = window;
    		}
    	}
    	catch(Exception e){}
        return comp;
    }
    public abstract Component getLastComponent(Container aContainer);
}
