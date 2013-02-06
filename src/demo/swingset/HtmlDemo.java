/*
 * Copyright (c) 2003 Sun Microsystems, Inc. All  Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * -Redistributions of source code must retain the above copyright
 *  notice, this list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduct the above copyright
 *  notice, this list of conditions and the following disclaimer in
 *  the documentation and/or other materials provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN AND ITS LICENSORS SHALL NOT
 * BE LIABLE FOR ANY DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT
 * OF OR RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE SOFTWARE, EVEN
 * IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that Software is not designed, licensed or intended for
 * use in the design, construction, operation or maintenance of any nuclear
 * facility.
 */

/*
 * @(#)HtmlDemo.java	1.8 03/01/23
 */
package demo.swingset;

import swingwtx.swing.*;
import swingwtx.swing.event.*;

import swingwt.awt.*;
import java.io.*;
import java.net.*;

/**
 * Html Demo
 * 
 * @version 1.8 03/01/23
 * @author Jeff Dinkins
 */
public class HtmlDemo extends DemoModule {

	JEditorPane html;

	/**
	 * main method allows us to run as a standalone demo.
	 */
	public static void main(String[] args) {
		HtmlDemo demo = new HtmlDemo(null);
		demo.mainImpl();
	}

	/**
	 * HtmlDemo Constructor
	 */
	public HtmlDemo(SwingSet2 swingset) {
		// Set the title for this demo, and an icon used to represent this
		// demo inside the SwingSet2 app.
		super(swingset, "HtmlDemo", "toolbar/JEditorPane.gif");

		try {
			URL url = null;
			// System.getProperty("user.dir") +
			// System.getProperty("file.separator");
			String path = null;
			try {
				// Whilst this works just beautifully on Linux,
				// IE for Windows does not support jar:file://
				// hyperlinks, so we need to take this stuff
				// outside the swingres.jar and hack things a bit
				// to find it.
				//
				//path = "/resources/index.html";
				//url = getClass().getResource(path);

				File file = new File("");
				String link = file.getAbsolutePath();
				System.out.println("Working with: " + link);

				// Kaffe and Classpath based projects return absolutePath() with
				// a separator on the end.
				if (link.endsWith(File.separator))
					link = link.substring(0, link.length() - 1);

				// Throw away the bin directory
				link = link.substring(0, link.lastIndexOf(File.separator));

				// Path to the htmldemo in SwingWT/demo/swingset/htmldemo
				link += File.separator + "src" + File.separator + "demo" + File.separator + "swingset" + File.separator + "htmldemo" + File.separator + "index.html";
				String surl = "file://" + link;
				System.out.println("Using: " + surl);
				url = new URL(surl);

			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("Failed to open " + path);
				url = null;
			}

			if (url != null) {
				html = new JEditorPane(url);
				html.setEditable(false);
				html.addHyperlinkListener(createHyperLinkListener());
				getDemoPanel().add(html, BorderLayout.CENTER);
			}
		} catch (MalformedURLException e) {
			System.out.println("Malformed URL: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
	}

	public HyperlinkListener createHyperLinkListener() {
		return new HyperlinkListener() {
			public void hyperlinkUpdate(HyperlinkEvent e) {
				if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
					try {
						html.setPage(e.getURL());
					} catch (IOException ioe) {
						System.out.println("IOE: " + ioe);
					}
				}
			}
		};
	}

}
