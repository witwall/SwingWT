/*
	SwingWT
	Copyright(c)2003-2008, R. Rawson-Tetley
	
	For more information on distributing and using this program, please
	see the accompanying "COPYING" file.
	
	Contact me by electronic mail: bobintetley@users.sourceforge.net
	
*/
package demo;

import swingwt.awt.event.*;
import swingwt.awt.*;
import swingwtx.swing.*;
import swingwtx.swing.text.*;

/**
 *  Styled Text Demo
 */
public class StyledTextDemo extends JFrame {

  public JTextPane textPane = null;
	
  public StyledTextDemo() {
        
  	super("Styled Text Demo");

        //first we'll create a JEditorPane and populate it
        JEditorPane editor = new JEditorPane();
        //set the content type to HTML
        editor.setContentType("text/html");
        //set the editable to false to hide the head and title tags
        editor.setEditable(false);
        //let's create a simple html formatted document to put in the JEditorPane
        String editorText = "<html><body>";
        //we can do just about any styling that we want...
        editorText += "<h1>Test</h1><p>Test test. This is formatted HTML</p>";
        //you get the idea, so we'll close it off
        editorText += "</body></html>";
        //all we need to do now is set the text into our pane
        editor.setText(editorText);

        //now let's create a JTextPane, some AttributeSets, etc
        textPane = new JTextPane();
        textPane.setEditable(false);
        //here's an example of some AttributeSets
        SimpleAttributeSet boldItalicRedText = new SimpleAttributeSet();
        StyleConstants.setForeground(boldItalicRedText, Color.red);
        StyleConstants.setBold(boldItalicRedText, true);
        StyleConstants.setItalic(boldItalicRedText, true);

        SimpleAttributeSet centeredBlueText = new SimpleAttributeSet();
        StyleConstants.setForeground(centeredBlueText, Color.blue);
        StyleConstants.setBold(centeredBlueText, true);
        StyleConstants.setAlignment(centeredBlueText, StyleConstants.ALIGN_CENTER);

        SimpleAttributeSet largeBlackText = new SimpleAttributeSet();
        StyleConstants.setForeground(largeBlackText, Color.black);
        StyleConstants.setFontSize(largeBlackText, 32);
        
        //now let's use them
        try {
            Document doc = textPane.getDocument();
            doc.insertString(doc.getLength(), "This is bold, blue text...\n", centeredBlueText);
            textPane.setParagraphAttributes(centeredBlueText, true);
            doc.insertString(doc.getLength(), "This is bold, italic, red text...\n", boldItalicRedText);
            doc.insertString(doc.getLength(), "This is black text.\n", largeBlackText);
            doc.insertString(doc.getLength(), "\nHow SwingWT implements this component means you are limited to one font and no alignment at the moment. \nIt's fast, native and the interface is complete though, so stop complaining!", centeredBlueText);
        }
        catch(BadLocationException exp) {
            exp.printStackTrace();
        }

        //all we need to do now is display our components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.add(new JScrollPane(editor));
        panel.add(new JScrollPane(textPane));
        panel.setPreferredSize(new Dimension(400,400));

        //now we'll set the panel as our content pane
        setContentPane(panel);
        
        //just to be nice, we'll shut down when the window is closed
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	try {
            		System.out.println(textPane.getDocument().getText(0, textPane.getDocument().getLength()));
            	}
            	catch (Exception ex) { ex.printStackTrace(); }
                System.exit(0);
            }
        });

        show();
  }

  public static void main(String[] args) {
  	
  	new StyledTextDemo();
    
  }
}



