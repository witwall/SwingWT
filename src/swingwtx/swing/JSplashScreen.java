package swingwtx.swing;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import swingwt.awt.Image;

/**
 * In swing, it's a relatively easy process to create a
 * splash screen.  Simply override the paintComponent
 * method of the JFrame and don't call the superclass's 
 * implementation of paintComponent.  Then draw your
 * splash screen on the JFrame.  Simple.  Unfortunately,
 * SwingWT cannot use the same aproach to splash
 * screens because SwingWT doesn't actually paint the
 * components, it's the native widget set that does that.
 * Because of this, the code that used to work to create
 * a splash screen no longer works (and cannot work).
 * To make up for this, I have created a JSplashScreen
 * component which creates a standard SWT splash 
 * screen.
 * 
 * Rob: This class isn't really necessary as I've now added
 * support for Java2D to all components/frames so you can
 * now intercept the paint event and draw your own stuff
 * on a frame! I've left this in as it is quite a useful
 * convenience class.
 *
 * @author Daniel Spiewak
 */
public class JSplashScreen {
	public static final int TEXT_LOWER_RIGHT = 0;
	public static final int TEXT_LOWER_LEFT = 1;
	public static final int TEXT_UPPER_RIGHT = 2;
	public static final int TEXT_UPPER_LEFT = 3;
	public static final int NO_TEXT = 4;
	
	private String text = "";
	private int style = 4;
	private Shell shell;
	private Label label;
	private ImageData data;
	private org.eclipse.swt.graphics.Image image;
	
	public JSplashScreen(Image img, int style) {
		this.style = style;
		image = img.image;
		data = image.getImageData();
		
		shell = new Shell(SwingWTUtils.getDisplay(), SWT.NO_TRIM);
		shell.setSize(data.width, data.height);
		// register the shell
        SwingWTUtils.registerWrapper(this, shell);
        
		Rectangle rect = SwingWTUtils.getDisplay().getBounds();
		int shellX = (rect.width - data.width) / 2;
		int shellY = (rect.height - data.height) / 2;
		shell.setLocation(shellX, shellY);
		
		shell.open();
		new GC(shell).drawImage(image, 0, 0);
		
		if (style == TEXT_LOWER_RIGHT) {
			label = new Label(shell, SWT.RIGHT);
			label.setText(text);
			label.setLocation(data.width - label.getBounds().width, data.height - label.getBounds().height);
		} else if (style == TEXT_LOWER_LEFT) {
			label = new Label(shell, SWT.LEFT);
			label.setText(text);
			label.setLocation(0, data.height - label.getBounds().height);
		} else if (style == TEXT_UPPER_RIGHT) {
			label = new Label(shell, SWT.RIGHT);
			label.setText(text);
			label.setLocation(data.width - label.getBounds().width, 0);
		} else if (style == TEXT_UPPER_LEFT) {
			label = new Label(shell, SWT.LEFT);
			label.setText(text);
			label.setLocation(0, 0);
		}
	}
	
	public JSplashScreen(Image img) {
		this(img, NO_TEXT);
	}
	
	public void setText(String text) {
		this.text = text;
		
		if (style == TEXT_LOWER_RIGHT) {
			label.setText(text);
			label.setLocation(data.width - label.getBounds().width, data.height - label.getBounds().height);
		} else if (style == TEXT_LOWER_LEFT) {
			label.setText(text);
			label.setLocation(0, data.height - label.getBounds().height);
		} else if (style == TEXT_UPPER_RIGHT) {
			label.setText(text);
			label.setLocation(data.width - label.getBounds().width, 0);
		} else if (style == TEXT_UPPER_LEFT) {
			label.setText(text);
			label.setLocation(0, 0);
		}
	}
	
	public void takeDown() {
		image.dispose();
		shell.dispose();
	}
}

/*
 *****************************************************
 * Copyright 2003 Completely Random Solutions *
 *                                												*
 * DISCLAMER:                                 					*
 * We are not responsible for any damage      		*
 * directly or indirectly caused by the usage 			*
 * of this or any other class in assosiation  			*
 * with this class.  Use at your own risk.   			*
 * This or any other class by CRS is not   			*
 * certified for use in life support systems, 			*
 * the Space Shuttle, in use or developement  		*
 * of nuclear reactors, weapons of mass       		*
 * destruction, or in interplanitary conflict.				*
 * (Unless otherwise specified)               				*
 *****************************************************
 */
