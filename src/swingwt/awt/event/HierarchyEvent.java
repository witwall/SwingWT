/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.event;

import swingwt.awt.*;

public class HierarchyEvent extends AWTEvent {

    /* TODO - check what values these should have */
    public static final int HIERARCHY_FIRST = 0;
    public static final int HIERARCHY_CHANGED = 1;
    public static final int ANCESTOR_MOVED = 2;
    public static final int ANCESTOR_RESIZED = 3;
    public static final int HIERARCHY_LAST = 4;
    public static final int PARENT_CHANGED = 5;
    public static final int DISPLAYABILITY_CHANGED = 6;
    public static final int SHOWING_CHANGED = 7;

    private Component source;
    private Component changed;
    private Container changedParent;
    private long changeFlags;

    public HierarchyEvent(Component source,
                      int id,
                      Component changed,
                      Container changedParent) {
        super(source, id);
        this.source = source;
        this.changed = changed;
        this.changedParent = changedParent;
    }

    public HierarchyEvent(Component source,
                      int id,
                      Component changed,
                      Container changedParent,
                      long changeFlags) {
        super(source, id);
        this.source = source;
        this.changed = changed;
        this.changedParent = changedParent;
        this.changeFlags = changeFlags;
    }

    public Component getComponent() {
        return source;
    }

    public Component getChanged() {
        return changed;
    }

    public Container getChangedParent() {
        return changedParent;
    }

    public long getChangeFlags() {
        return changeFlags;
    }

}
