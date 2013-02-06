/**
 * SwingWT
 *
 * This program is dual licenced under the terms of the Common Public 
 * License v1.0 and the GNU Lesser Public License (LGPL) version 2 or
 * later. Please choose whichever is most applicable to you.
 */
package swingwt.awt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import swingwt.awt.event.InputEvent;
import swingwt.awt.image.BufferedImage;
import swingwtx.swing.SwingWTUtils;

/**
 * SWT does not provide a direct counterpart to Robot, but
 * we can try to simulate it by posting low level events
 * in the display's queue.
 * 
 * @author Thiago Tonelli Bartolomei
 */
public class Robot {
	
	protected static Runnable NON_EVENT = new Runnable() {
		public void run() {
		}
	};
	
	public Robot() throws AWTException {
	}

	public Robot(GraphicsDevice screen) throws AWTException {
	}
	
	/**
	 * Moves the mouse to the screen position x,y.
	 *
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public synchronized void mouseMove(int x, int y) {
		org.eclipse.swt.widgets.Event event = createEvent(SWT.MouseMove);
		event.x = x;
		event.y = y;
		post(event);
	}

	public synchronized void mousePress(int buttons) {
		int flag = SWT.NONE;
		if (buttons == InputEvent.BUTTON1_MASK)
			flag = 1;
		// TODO - other buttons
		org.eclipse.swt.widgets.Event event = createEvent(SWT.MouseDown);
		event.button = flag;
		post(event);
	}

	public synchronized void mouseRelease(int buttons) {
		/*
		int flag = SWT.NONE;
		if (buttons == InputEvent.BUTTON1_MASK)
			flag = 1;
		// TODO - other buttons
		
		org.eclipse.swt.widgets.Event event = createEvent(SWT.MouseUp);
		event.button = flag;
		post(event);*/
	}

	public synchronized void mouseWheel(int wheelAmt) {
	}

	public synchronized void keyPress(int keycode) {
		/*
		org.eclipse.swt.widgets.Event event = createEvent(SWT.KeyDown);
		event.keyCode = KeyEvent.translateAWTKey(keycode);
		post(event);*/
	}

	public synchronized void keyRelease(int keycode) {
		/*
		org.eclipse.swt.widgets.Event event = createEvent(SWT.KeyUp);
		event.keyCode = KeyEvent.translateAWTKey(keycode);
		post(event);*/
	}

	public synchronized Color getPixelColor(int x, int y) {
		return null;
	}

	public synchronized BufferedImage createScreenCapture(Rectangle screenRect) {
		return null;
	}

	public synchronized boolean isAutoWaitForIdle() {
		return false;
	}

	public synchronized void setAutoWaitForIdle(boolean isOn) {
	}

	public synchronized int getAutoDelay() {
		return 0;
	}

	public synchronized void setAutoDelay(int ms) {
	}

	public synchronized void delay(int ms) {
	}

	public synchronized void waitForIdle() {
	}

	public synchronized String toString() {
		return getClass().getName();
	}
	
	/**
	 * Posts this event to the default display.
	 * 
	 * For internal use only.
	 *
	 * @param event
	 */
	protected final void post(final org.eclipse.swt.widgets.Event event) {
	
		final Display d = SwingWTUtils.getDisplay();
		event.display = d;
		d.asyncExec(new Runnable() {
			public void run() {
				d.post(event);
			}
		});
	}
	
	/**
	 * Creates a low level event of this type.
	 * 
	 * For internal use only.
	 *
	 * @param type
	 * @return
	 */
	protected final org.eclipse.swt.widgets.Event createEvent(int type) {
		org.eclipse.swt.widgets.Event event = new org.eclipse.swt.widgets.Event();
		event.type = type;
		return event;
	}
}
