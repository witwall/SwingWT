/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net


 */
package swingwt.awt.event;

import swingwt.awt.Adjustable;
import swingwt.awt.AWTEvent;

/**
 * The adjustment event emitted by Adjustable objects.
 * 
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version 0.1
 */
public class AdjustmentEvent extends AWTEvent {
	public static final int ADJUSTMENT_FIRST=601;
	public static final int ADJUSTMENT_LAST=601;
	public static final int ADJUSTMENT_VALUE_CHANGED=ADJUSTMENT_FIRST;
	public static final int UNIT_INCREMENT=1;
	public static final int UNIT_DECREMENT=2;
	public static final int BLOCK_DECREMENT=3;
	public static final int BLOCK_INCREMENT=4;
	public static final int TRACK=5;

	Adjustable adjustable; //The adjustable object that fired the event.

	int value; //the new value of the adjustable object.

	int adjustmentType; //describes how the adjustable object value has changed.

	boolean isAdjusting; //true if the event is one of the series of multiple adjustment events.

	 private static final long serialVersionUID = 5700290645205279921L;


	/**
	 *
	 * @param source the Adjustable object where the event originated
	 * @param id the event type
	 * @param type the adjustment type 
	 * @param value the current value of the adjustment
	 */
	public AdjustmentEvent(Adjustable source, int id, int type, int value) {
		this(source, id, type, value, false);
	}

	/**
	 * 
	 * @param source the Adjustable object where the event originated
	 * @param id the event type
	 * @param type the adjustment type 
	 * @param value the current value of the adjustment
	 * @param isAdjusting true if the event is one of a series of multiple adjusting events,
	 */
	public AdjustmentEvent(Adjustable source, int id, int type, int value, boolean isAdjusting) {
		super(source, id);
		adjustable = source;
		this.adjustmentType = type;
		this.value = value;
		this.isAdjusting = isAdjusting;
	}

	/**
	 *
	 * @return the Adjustable object where this event originated
	 */
	public Adjustable getAdjustable() {
		return adjustable;
	}

	/**
	 *
	 * @return the current value in the adjustment event
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 
	 * @return one of the adjustment values listed above
	 */
	public int getAdjustmentType() {
		return adjustmentType;
	}

	/**
	 *
	 * @return true if this is one of multiple adjustment events
	 */
	public boolean getValueIsAdjusting() {
		return isAdjusting;
	}

}
