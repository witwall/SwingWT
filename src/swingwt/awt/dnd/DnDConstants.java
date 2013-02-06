/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net

   
 */
package swingwt.awt.dnd;

/**
 * Type of actions to be performed by a Drag and Drop operation
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public class DnDConstants {

	public static final int ACTION_NONE=0x0; //no action
	public static final int ACTION_COPY=0x1; //copy action
	public static final int ACTION_MOVE=0x2; //move action
	public static final int ACTION_COPY_OR_MOVE=ACTION_COPY | ACTION_MOVE; //copy or move
	public static final int ACTION_LINK=0x40000000; //link action
	public static final int ACTION_REFERENCE=ACTION_LINK; //reference action

	private DnDConstants() {}
}
