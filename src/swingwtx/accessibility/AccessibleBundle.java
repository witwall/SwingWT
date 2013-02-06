/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.accessibility;

import java.util.*;

/**
 * TODO: Not implemented.
 *
 * @author  naab
 * @version %I%, %G%
 */
public abstract class AccessibleBundle
{
    protected String toDisplayString(String resourceBundleName, Locale locale) { return super.toString(); }
    public String toDisplayString(Locale locale) { return super.toString(); }
    public String toDisplayString() { return super.toString(); }
    public String toString() { return toDisplayString(); }
}
