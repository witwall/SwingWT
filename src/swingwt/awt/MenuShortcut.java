/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net

 */

package swingwt.awt;

public class MenuShortcut {

    protected int key = 0;
    protected boolean useShiftModifier = false;
    
    public MenuShortcut(int key) {
        this(key, false);
    }
    
    public MenuShortcut(int key, boolean useShiftModifier) {
        this.key = key;
        this.useShiftModifier = useShiftModifier;
    }
    
    public boolean equals(MenuShortcut s) {
        return (s.getKey() == key && useShiftModifier == s.usesShiftModifier());
    }
    
    public boolean usesShiftModifier() { return useShiftModifier; }
    public int getKey() { return key; }
    
}
