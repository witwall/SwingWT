package swingwtx.swing;
import swingwt.awt.*;
import java.io.*;
public class LayoutFocusTraversalPolicy extends SortingFocusTraversalPolicy implements Serializable
{
    public LayoutFocusTraversalPolicy() 
    {
    }
    public Component getComponentAfter(Container aContainer,Component aComponent) 
    {
        return super.getComponentAfter(aContainer, aComponent);
    }
    public Component getComponentBefore(Container aContainer,Component aComponent) 
    {
        return super.getComponentBefore(aContainer, aComponent);
    }
    public Component getFirstComponent(Container aContainer) 
    {
        return super.getFirstComponent(aContainer);
    }
    public Component getLastComponent(Container aContainer) 
    {
        return super.getLastComponent(aContainer);
    }
}
