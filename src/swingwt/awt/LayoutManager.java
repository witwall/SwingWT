/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwt.awt;

/**
 * Layout managers describe how components are positioned in containers.
 * 
 * @author Daniel Spiewak 
 * @author Thiago Tonelli Bartolomei
 */
public interface LayoutManager {
    
	/**
	 * Adds the component to this layout, identifying it by this string.
	 *
	 * @param name
	 * @param component
	 */
	public void addLayoutComponent(String name, Component component);
    
    /**
     * Removes the component from this layout.
     *
     * @param component
     */
    public void removeLayoutComponent(Component component);
    
    /**
     * Computes the size this container should have based on the
     * components contained in it.
     *
     * @param container
     * @return
     */
    public Dimension preferredLayoutSize(Container container);
    
    /**
     * Computes the minimum size of this container, based on the
     * components contained in it.
     *
     * @param container
     * @return
     */
    public Dimension minimumLayoutSize(Container container);
    
    /**
     * Lays out the container.
     *
     * @param container
     */
    public void layoutContainer(Container container);
}
