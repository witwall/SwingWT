package swingwt.awt;

/**
 * Simple layout manager that allows one component and it
 * fills the whole container.
 *
 * @author  Robin Rawson-Tetley
 */
public class FillLayout implements LayoutManager {

    protected Component theComponent = null;
    public void addLayoutComponent(String name, Component comp) {
        theComponent = comp;
    }
    public void removeLayoutComponent(Component comp) {
        if (comp == theComponent)
            theComponent = null;
    }
    public Dimension preferredLayoutSize(Container parent) {
        if (theComponent == null) return new Dimension(0, 0);
        return theComponent.getPreferredSize();
    }
    public Dimension minimumLayoutSize(Container parent) {
        return preferredLayoutSize(parent);    
    }
    public void layoutContainer(Container parent) {
        if (theComponent == null) return;
        theComponent.setBounds(0, 0, parent.getWidth(), parent.getHeight());
    }
    
}
