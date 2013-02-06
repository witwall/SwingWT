/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwt.awt;

/**
 * An extension to the basic layout manager that allows components
 * to be added with constraint objects.
 * 
 * @author Daniel Spiewak 
 * @author Thiago Tonelli Bartolomei
 */
public interface LayoutManager2 extends LayoutManager {

	/**
	 * Adds this component to the manager with this corresponding
	 * constraint object.
	 *
	 * @param component
	 * @param constraints
	 */
	public void addLayoutComponent(Component component, Object constraints);
	
	/**
	 * Computes the maximum size of this container, based on the 
	 * components in it.
	 *
	 * @param container
	 * @return
	 */
	public Dimension maximumLayoutSize(Container container);
	
	/**
	 * A value between 0 (origin) and 1 (infinite) that specifies the
	 * position this container should be aligned with respect to other
	 * components, in the X axis. Note that 0.5 means center. 
	 *
	 * @param container
	 * @return
	 */
	public float getLayoutAlignmentX(Container container);
	
	/**
	 * A value between 0 (origin) and 1 (infinite) that specifies the
	 * position this container should be aligned with respect to other
	 * components, in the Y axis. Note that 0.5 means center
	 *
	 * @param container
	 * @return
	 */
	public float getLayoutAlignmentY(Container container);
	
	/**
	 * The layout manager should invalidate the layout, i.e., it
	 * should dischard any cached information.
	 *
	 * @param container
	 */
	public void invalidateLayout(Container container);
}
