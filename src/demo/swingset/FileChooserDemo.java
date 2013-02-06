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
 * @(#)FileChooserDemo.java	1.13 03/01/23
 */
package demo.swingset;

import swingwtx.swing.*;
import swingwtx.swing.border.*;

import swingwt.awt.*;
import swingwt.awt.event.*;
import java.beans.*;
import java.io.*;

/**
 * JFileChooserDemo
 *
 * @version 1.1 07/16/99
 * @author Jeff Dinkins
 */
public class FileChooserDemo extends DemoModule {
    JLabel theImage;
    Icon jpgIcon; 
    Icon gifIcon;

    /**
     * main method allows us to run as a standalone demo.
     */
    public static void main(String[] args) {
	FileChooserDemo demo = new FileChooserDemo(null);
	demo.mainImpl();
    }

    /**
     * FileChooserDemo Constructor
     */
    public FileChooserDemo(SwingSet2 swingset) {
	// Set the title for this demo, and an icon used to represent this
	// demo inside the SwingSet2 app.
	super(swingset, "FileChooserDemo", "toolbar/JFileChooser.gif");
	createFileChooserDemo();
    }

    public void createFileChooserDemo() {
	theImage = new JLabel("");
	jpgIcon = createImageIcon("filechooser/jpgIcon.jpg", "jpg image");
	gifIcon = createImageIcon("filechooser/gifIcon.gif", "gif image");

	JPanel demoPanel = getDemoPanel();
	demoPanel.setLayout(new BoxLayout(demoPanel, BoxLayout.Y_AXIS));

	JPanel innerPanel = new JPanel();
	innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS));

	demoPanel.add(Box.createRigidArea(VGAP20));
	demoPanel.add(innerPanel);
	demoPanel.add(Box.createRigidArea(VGAP20));

	innerPanel.add(Box.createRigidArea(HGAP20));

	// Create a panel to hold buttons
	JPanel buttonPanel = new JPanel() {
	    public Dimension getMaximumSize() {
		return new Dimension(getPreferredSize().width, super.getMaximumSize().height);
	    }
	};
	buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

	buttonPanel.add(Box.createRigidArea(VGAP15));
	buttonPanel.add(createPlainFileChooserButton());
	buttonPanel.add(Box.createRigidArea(VGAP15));
	buttonPanel.add(createPreviewFileChooserButton());
	buttonPanel.add(Box.createRigidArea(VGAP15));
	buttonPanel.add(createCustomFileChooserButton());
	buttonPanel.add(Box.createVerticalGlue());

	// Create a panel to hold the image
	JPanel imagePanel = new JPanel();
	imagePanel.setLayout(new BorderLayout());
	imagePanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
	JScrollPane scroller = new JScrollPane(theImage);
        scroller.getVerticalScrollBar().setUnitIncrement(10);
        scroller.getHorizontalScrollBar().setUnitIncrement(10);
	imagePanel.add(scroller, BorderLayout.CENTER);

	// add buttons and image panels to inner panel
	innerPanel.add(buttonPanel);
	innerPanel.add(Box.createRigidArea(HGAP30));
	innerPanel.add(imagePanel);
	innerPanel.add(Box.createRigidArea(HGAP20));
    }

    public JFileChooser createFileChooser() {
	// create a filechooser
	JFileChooser fc = new JFileChooser();
	
	// set the current directory to be the images directory
	File swingFile = new File("resources/images/About.jpg");
	if(swingFile.exists()) {
	    fc.setCurrentDirectory(swingFile);
	    fc.setSelectedFile(swingFile);
	}
	
	return fc;
    }


    public JButton createPlainFileChooserButton() {
	Action a = new AbstractAction(getString("FileChooserDemo.plainbutton")) {
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = createFileChooser();

		// show the filechooser
                    int result = fc.showOpenDialog(getDemoPanel());
                    // if we selected an image, load the image
                    if(result == JFileChooser.APPROVE_OPTION) {
                        loadImage(fc.getSelectedFile().getPath());
                    }
		

	    }
	};
	return createButton(a);
    }

    public JButton createPreviewFileChooserButton() {
	Action a = new AbstractAction(getString("FileChooserDemo.previewbutton")) {
	    public void actionPerformed(ActionEvent e) {
		JFileChooser fc = createFileChooser();

		// Add filefilter & fileview
		ExampleFileFilter filter = new ExampleFileFilter(
		    new String[] {"jpg", "gif"}, getString("FileChooserDemo.filterdescription")
		);
		ExampleFileView fileView = new ExampleFileView();
		fileView.putIcon("jpg", jpgIcon);
		fileView.putIcon("gif", gifIcon);
		//fc.setFileView(fileView);
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);
		
		// add preview accessory
		fc.setAccessory(new FilePreviewer(fc));

		// show the filechooser
                    int result = fc.showOpenDialog(getDemoPanel());
                    // if we selected an image, load the image
                    if(result == JFileChooser.APPROVE_OPTION) {
                        loadImage(fc.getSelectedFile().getPath());
                    }
		

	    }
	};
	return createButton(a);
    }

    JDialog dialog;
    JFileChooser fc;

    public JButton createCustomFileChooserButton() {
	Action a = new AbstractAction(getString("FileChooserDemo.custombutton")) {
	    public void actionPerformed(ActionEvent e) {
		fc = createFileChooser();

		// Add filefilter & fileview
		ExampleFileFilter filter = new ExampleFileFilter(
		    new String[] {"jpg", "gif"}, getString("FileChooserDemo.filterdescription")
		);
		ExampleFileView fileView = new ExampleFileView();
		fileView.putIcon("jpg", jpgIcon);
		fileView.putIcon("gif", gifIcon);
		//fc.setFileView(fileView);
		fc.addChoosableFileFilter(filter);

		// add preview accessory
		fc.setAccessory(new FilePreviewer(fc));

		// remove the approve/cancel buttons
		fc.setControlButtonsAreShown(false);

		// make custom controls
		//wokka
		JPanel custom = new JPanel();
		custom.setLayout(new BorderLayout());
		JLabel description = new JLabel(getString("FileChooserDemo.description"));
		description.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		custom.add(description, BorderLayout.NORTH);
		custom.add(fc, BorderLayout.CENTER);

		Action okAction = createOKAction();
		fc.addActionListener(okAction);

		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createImageButton(createFindAction()));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createButton(createAboutAction()));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createButton(okAction));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createButton(createCancelAction()));
		buttons.add(Box.createRigidArea(HGAP10));
		buttons.add(createImageButton(createHelpAction()));
		buttons.add(Box.createRigidArea(HGAP10));

		custom.add(buttons, BorderLayout.SOUTH);
		
		Frame parent = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, getDemoPanel());
		dialog = new JDialog(parent, getString("FileChooserDemo.dialogtitle"), true);
                dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.getContentPane().add(custom, BorderLayout.CENTER);
		dialog.setSize(640, 480);
		dialog.setLocationRelativeTo(getDemoPanel());
		dialog.show();
	    }
	};
	return createButton(a);
    }

    public Action createAboutAction() {
	return new AbstractAction(getString("FileChooserDemo.about")) {
	    public void actionPerformed(ActionEvent e) {
		File file = fc.getSelectedFile();
		String text;
		if(file == null) {
		    text = getString("FileChooserDemo.nofileselected");
		} else {
		    text = "<html>" + getString("FileChooserDemo.thefile");
		    text += "<br><font color=green>" + file.getName() + "</font><br>";
		    text += getString("FileChooserDemo.isprobably") + "</html>";
		}
		JOptionPane.showMessageDialog(getDemoPanel(), text);
	    }
	};
    }

    public Action createOKAction() {
	return new AbstractAction(getString("FileChooserDemo.ok")) {
	    public void actionPerformed(ActionEvent e) {
		dialog.dispose();
		if (!e.getActionCommand().equals(JFileChooser.CANCEL_SELECTION)
		    && fc.getSelectedFile() != null) {

		    loadImage(fc.getSelectedFile().getPath());
		}
	    }
	};
    }

    public Action createCancelAction() {
	return new AbstractAction(getString("FileChooserDemo.cancel")) {
	    public void actionPerformed(ActionEvent e) {
		dialog.dispose();
	    }
	};
    }

    public Action createFindAction() {
	Icon icon = createImageIcon("filechooser/find.gif", getString("FileChooserDemo.find"));
	return new AbstractAction("", icon) {
	    public void actionPerformed(ActionEvent e) {
                String result = JOptionPane.showInputDialog(getDemoPanel(), getString("FileChooserDemo.findquestion"));
		if (result != null) {
		    JOptionPane.showMessageDialog(getDemoPanel(), getString("FileChooserDemo.findresponse"));
		}
	    }
	};
    }

    public Action createHelpAction() {
	Icon icon = createImageIcon("filechooser/help.gif", getString("FileChooserDemo.help"));
	return new AbstractAction("", icon) {
	    public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(getDemoPanel(), getString("FileChooserDemo.helptext"));
	    }
	};
    }


    public void loadImage(String filename) {
	theImage.setIcon(new ImageIcon(filename));
    }

    public JButton createButton(Action a) {
	JButton b = new JButton(a) {
	    public Dimension getMaximumSize() {
		int width = Short.MAX_VALUE;
		int height = super.getMaximumSize().height;
		return new Dimension(width, height);
	    }
	};
	return b;
    }

    public JButton createImageButton(Action a) {
	JButton b = new JButton(a);
	b.setMargin(new Insets(0,0,0,0));
	return b;
    }
}

class FilePreviewer extends JLabel implements PropertyChangeListener {
    
    public FilePreviewer(JFileChooser fc) {
	setPreferredSize(new Dimension(100, 50));
	fc.addPropertyChangeListener(this);
	setBorder(new BevelBorder(BevelBorder.LOWERED));
    }
    
    public void loadImage(File f) {
        if (f == null) {
            setIcon(null);
        } else {
	    ImageIcon tmpIcon = new ImageIcon(f.getPath());
	    if(tmpIcon.getIconWidth() > 90) {
		setIcon(new ImageIcon(
		    tmpIcon.getImage().getScaledInstance(90, -1, Image.SCALE_DEFAULT)));
	    } else {
		setIcon(tmpIcon);
	    }
	}
    }
    
    public void propertyChange(PropertyChangeEvent e) {
	String prop = e.getPropertyName();
	if(prop == JFileChooser.SELECTED_FILE_CHANGED_PROPERTY) {
	    if(isShowing()) {
                loadImage((File) e.getNewValue());
		repaint();
	    }
	}
    }
}

