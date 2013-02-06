/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.plaf.metal;

import swingwtx.swing.plaf.ComponentUI;
import swingwtx.swing.JComponent;

import java.util.HashMap;

/**
 * This is a generic ComponentUI wrapper for use with SwingWT.
 *
 * The createUI(Class) method creates a new ComponentUI instance intended for wrapping the SWT component
 * of the given type.
 *
 * Why?  This allows us to create SWT components that can be custom drawn with Swing code, a la L&F.
 * This part still has some work.  <g>
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class GenericComponentUI extends ComponentUI
{
    private static HashMap defaultComponentUIs = new HashMap();

    private Class componentClass;

    public static ComponentUI createUI(JComponent component)
    {
        if (component == null)
            throw new NullPointerException();

        return createUI(component.getClass());
    }

    public static ComponentUI createUI(Class componentClass)
    {
        if (componentClass == null)
            throw new NullPointerException();

        GenericComponentUI componentUI = null;
        if (defaultComponentUIs.containsKey(componentClass))
        {
            componentUI = (GenericComponentUI) defaultComponentUIs.get(componentClass);
        }
        else
        {
            componentUI = new GenericComponentUI(componentClass);
            defaultComponentUIs.put(componentClass, componentUI);
        }
        return componentUI;
    }

    public GenericComponentUI(Class componentClass)
    {
        super();
        this.componentClass = componentClass;
    }
}
