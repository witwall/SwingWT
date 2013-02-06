/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net

 */
package swingwt.awt.event;

import swingwt.awt.AWTEvent;
import swingwt.awt.Component;

/**
 * An event that indicates a component movement/resizing/visibility etc.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public class ComponentEvent extends AWTEvent {
	public static final int COMPONENT_FIRST=100;
	public static final int COMPONENT_LAST=103;
	public static final int COMPONENT_MOVED=COMPONENT_FIRST;
	public static final int COMPONENT_RESIZED=1+COMPONENT_FIRST;
	public static final int COMPONENT_SHOWN=2+COMPONENT_FIRST;
	public static final int COMPONENT_HIDDEN=3+COMPONENT_FIRST;

	public ComponentEvent(Component source) {
        super(source);
    }

	/**
	 *
	 * @param the component that originated the event
	 * @param id an integer indicating the type of event
	 */
	public ComponentEvent(Component source, int id) {
		super(source, id);
	}

	/**
	 *
	 * @return the Component that originated the event
	 */
	public Component getComponent() {
		return (source instanceof Component) ? (Component)source : null;
	}

}
