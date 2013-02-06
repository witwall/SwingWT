/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

import java.util.*;

public interface MutableAttributeSet extends AttributeSet {

    public void addAttribute(Object name, Object value);
    public void addAttributes(AttributeSet attributes);
    public void removeAttribute(Object name);
    public void removeAttributes(Enumeration names);
    public void removeAttributes(AttributeSet attributes);
    public void setResolveParent(AttributeSet parent);

}
