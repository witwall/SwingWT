/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 */

package swingwt.awt;

import java.util.*;

public class CardLayout implements LayoutManager2 {
    
    int currentCard = 0;
    int hgap;
    int vgap;
        
    public CardLayout() {
        this(0, 0);
    }

    public CardLayout(int hgap, int vgap) {
        this.hgap = hgap;
        this.vgap = vgap;
    }

    public int getHgap() {
        return hgap;
    }

    public void setHgap(int hgap) {
        this.hgap = hgap;
    }

    public int getVgap() {
        return vgap;
    }

    public void setVgap(int vgap) {
        this.vgap = vgap;
    }

    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints instanceof String) {
            addLayoutComponent((String) constraints, comp);
        } else {
            System.out.println(comp.toString());
        }
    }

    public void addLayoutComponent(String name, Component comp) {
        if (!cards.isEmpty()) {
            comp.setVisible(false);
        }
        for (int i = 0; i < cards.size(); i++) {
            if (((Card) cards.get(i)).name.equals(name)) {
                ((Card) cards.get(i)).comp = comp;
                return;
            }
        }
        cards.add(new Card(name, comp));
    }

    public void removeLayoutComponent(Component comp) {
        for (int i = 0; i < cards.size(); i++) {
            if (((Card) cards.get(i)).comp == comp) {
                if (comp.isVisible() && (comp.getParent() != null)) {
                    next(comp.getParent());
                }

                cards.remove(i);
                if (currentCard > i) {
                    currentCard--;
                }
                break;
            }
        }
    }

    public Dimension preferredLayoutSize(Container parent) {
        Insets insets = parent.getInsets();
        int ncomponents = parent.getComponentCount();
        int w = 0;
        int h = 0;

        for (int i = 0; i < ncomponents; i++) {
            Component comp = parent.getComponent(i);
            Dimension d = comp.getPreferredSize();
            if (d.width > w) {
                w = d.width;
            }
            if (d.height > h) {
                h = d.height;
            }
        }
        return new Dimension(
        insets.left + insets.right + w + hgap * 2,
        insets.top + insets.bottom + h + vgap * 2);
    }

    public Dimension minimumLayoutSize(Container parent) {
        Insets insets = parent.getInsets();
        int ncomponents = parent.getComponentCount();
        int w = 0;
        int h = 0;

        for (int i = 0; i < ncomponents; i++) {
            Component comp = parent.getComponent(i);
            Dimension d = comp.getMinimumSize();
            if (d.width > w) {
                w = d.width;
            }
            if (d.height > h) {
                h = d.height;
            }
        }
        return new Dimension(
        insets.left + insets.right + w + hgap * 2,
        insets.top + insets.bottom + h + vgap * 2);
    }

    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public float getLayoutAlignmentX(Container parent) {
        return (float) 0.5;
    }

    public float getLayoutAlignmentY(Container parent) {
        return (float) 0.5;
    }

    public void invalidateLayout(Container target) {
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int ncomponents = parent.getComponentCount();
        Component comp = null;
        boolean currentFound = false;

        for (int i = 0; i < ncomponents; i++) {
            comp = parent.getComponent(i);
            comp.setBounds(
            hgap + insets.left,
            vgap + insets.top,
            parent.getWidth() - (hgap * 2 + insets.left + insets.right),
            parent.getHeight() - (vgap * 2 + insets.top + insets.bottom));
            if (comp.isVisible()) {
                currentFound = true;
            }
        }

        if (!currentFound && ncomponents > 0) {
            parent.getComponent(0).setVisible(true);
        }
    }

    void checkLayout(Container parent) {
    }

    public void first(Container parent) {
        int ncomponents = parent.getComponentCount();
        for (int i = 0; i < ncomponents; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                comp.setVisible(false);
                break;
            }
        }
        if (ncomponents > 0) {
            currentCard = 0;
            parent.getComponent(0).setVisible(true);
            parent.validate();
        }
    }

    public void next(Container parent) {
        int ncomponents = parent.getComponentCount();
        for (int i = 0; i < ncomponents; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                comp.setVisible(false);
                currentCard = (i + 1) % ncomponents;
                comp = parent.getComponent(currentCard);
                comp.setVisible(true);
                parent.validate();
                return;
            }
        }
        showFirstAvailableComponent(parent);
    }

    public void previous(Container parent) {
        int ncomponents = parent.getComponentCount();
        for (int i = 0; i < ncomponents; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                comp.setVisible(false);
                currentCard = ((i > 0) ? i - 1 : ncomponents - 1);
                comp = parent.getComponent(currentCard);
                comp.setVisible(true);
                parent.validate();
                return;
            }
        }
        showFirstAvailableComponent(parent);
    }

    public void last(Container parent) {
        int ncomponents = parent.getComponentCount();
        for (int i = 0; i < ncomponents; i++) {
            Component comp = parent.getComponent(i);
            if (comp.isVisible()) {
                comp.setVisible(false);
                break;
            }
        }
        if (ncomponents > 0) {
            currentCard = ncomponents - 1;
            parent.getComponent(currentCard).setVisible(true);
            parent.validate();
        }
    }
    
    public void show(Container parent, String name) {
        Component next = null;
        int ncomponents = cards.size();
        for (int i = 0; i < ncomponents; i++) {
            Card card = (Card) cards.get(i);
            if (card.name.equals(name)) {
                next = card.comp;
                currentCard = i;
                break;
            }
        }
        if ((next != null) && !next.isVisible()) {
            ncomponents = parent.getComponentCount();
            for (int i = 0; i < ncomponents; i++) {
                Component comp = parent.getComponent(i);
                if (comp.isVisible()) {
                    comp.setVisible(false);
                    break;
                }
            }
            next.setVisible(true);
            parent.validate();
        }
    }
    
    
    
    private void showFirstAvailableComponent(Container parent) {
        if (parent.getComponentCount() > 0) {
            currentCard = 0;
            parent.getComponent(0).setVisible(true);
            parent.validate();
        }
    }
    
    Vector cards = new Vector();
    
    private class Card {
        public String name;
        public Component comp;
        public Card(String cardName, Component cardComponent) {
            name = cardName;
            comp = cardComponent;
        }
    }

    public String toString() {
        return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
    }
}
