/**
 * SwingWT
 *
 * This program is dual licenced under the terms of the Common Public 
 * License v1.0 and the GNU Lesser Public License (LGPL) version 2 or
 * later. Please choose whichever is most applicable to you.
 */
package swingwtx.swing.text.html;

import java.net.URL;

import swingwtx.swing.event.HyperlinkEvent;

/**
 * TODO Comment!!
 * 
 * @author Thiago Tonelli Bartolomei
 */
public class HTMLFrameHyperlinkEvent extends HyperlinkEvent {

	/**
	 * TODO Comment!!
	 *
	 * @param source
	 * @param type
	 * @param u
	 */
	public HTMLFrameHyperlinkEvent(Object source, EventType type, URL u) {
		super(source, type, u);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * TODO Comment!!
	 *
	 * @param source
	 * @param type
	 * @param u
	 * @param description
	 */
	public HTMLFrameHyperlinkEvent(Object source, EventType type, URL u, String description) {
		super(source, type, u, description);
		// TODO Auto-generated constructor stub
	}
}
