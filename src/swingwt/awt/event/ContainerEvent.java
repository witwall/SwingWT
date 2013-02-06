/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */
package swingwt.awt.event;

import swingwt.awt.Container;
import swingwt.awt.Component;

/**
 * An event that indicates a container movement/resizing/visibility etc.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public class ContainerEvent extends ComponentEvent {
	public static final int CONTAINER_FIRST=300;
	public static final int CONTAINER_LAST=301;
	public static final int COMPONENT_ADDED=CONTAINER_FIRST;
	public static final int COMPONENT_REMOVED=1+CONTAINER_FIRST;

	Component child; //The component that is being added or removed from the container

	/**
	 * 
	 * @param source the Component that originated the event
	 * @param id type of event
	 * @param child the component that was added or removed
	 */
	public ContainerEvent(Component source, int id, Component child) {
		super(source, id);
		this.child = child;
	}

	/**
	 * Returns the originator of the event.
	 *
	 * @return the Container object that originated the event
	 */
	public Container getContainer() {
		return (Container)source; // cast should always be OK, type was checked in constructor
	}

	/**
	 *
	 * @return the Component object that was added or removed
	 */
	public Component getChild() {
		return child;
	}
}
