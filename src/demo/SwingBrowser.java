/*
 * Swing demo. Uses the HTMLEditorKit to show a simple
 * browser for comparison with SwingWT
 *
 * @author R. Rawson-Tetley
 *
 */

package demo;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;

/** Very simplistic "Web browser" using Swing. Supply a URL on the 
 *  command line to see it initially, and to set the destination
 *  of the "home" button.
 */

public class SwingBrowser extends JFrame implements HyperlinkListener, 
                                               ActionListener {
  public static void main(String[] args) {
    if (args.length == 0)
      new SwingBrowser();
    else
      new SwingBrowser(args[0]);
  }

  private JButton homeButton;
  private JTextField urlField;
  private JEditorPane htmlPane;
  private String initialURL;

  public SwingBrowser() { this(""); }
  
  public SwingBrowser(String initialURL) {
    super("Simple Swing Browser");
    this.initialURL = initialURL;

    JPanel topPanel = new JPanel();
    topPanel.setLayout(new BorderLayout());
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout());
    topPanel.add(buttonPanel, BorderLayout.WEST);
    
    homeButton = new JButton();
    homeButton.setIcon(new ImageIcon(getClass().getResource("/demo/Homepage.png")));
    homeButton.addActionListener(this);
    urlField = new JTextField(100);
    urlField.setText(initialURL);
    topPanel.add(urlField, BorderLayout.CENTER);
    
    urlField.addKeyListener(new KeyListener() {
        public void keyPressed(KeyEvent k) {
           if (k.getKeyCode() == KeyEvent.VK_ENTER) {
                String url = urlField.getText();
                try {
                  htmlPane.setPage(new URL(url));
                  urlField.setText(url);
                } catch(IOException ioe) {
                  warnUser("Can't follow link to " + url + ": " + ioe);
                }    
            }
        }
        public void keyReleased(KeyEvent k) {}
        public void keyTyped(KeyEvent k) {
        }
    });
    
    buttonPanel.add(homeButton);
    
    getContentPane().add(topPanel, BorderLayout.NORTH);

    String sample = "<html><body><h2>Test HTML</h2><p>This is a simple browser test. Type in a URL at the top to go to a new location, or hit the home button to go back to the URL you supplied.</p></body></html>";
    
    try {
        if (initialURL.equals(""))
            htmlPane = new JEditorPane("text/html", sample);
        else
            htmlPane = new JEditorPane(initialURL);
        htmlPane.setEditable(false);
        htmlPane.addHyperlinkListener(this);
        JScrollPane scrollPane = new JScrollPane(htmlPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    } catch(IOException ioe) {
       warnUser("Can't build HTML pane for " + initialURL 
                + ": " + ioe);
    }

    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width * 8 / 10;
    int height = screenSize.height * 8 / 10;
    setBounds(width/8, height/8, width, height);
    setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    String url = initialURL;
    if (url.equals("")) {
        warnUser("You didn't supply an initial home URL!");
        return;
    }
    try {
      htmlPane.setPage(new URL(url));
      urlField.setText(url);
    } catch(IOException ioe) {
      warnUser("Can't follow link to " + url + ": " + ioe);
    }
  }

  public void hyperlinkUpdate(HyperlinkEvent event) {
    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
      try {
        htmlPane.setPage(event.getURL());
        urlField.setText(event.getURL().toExternalForm());
      } catch(IOException ioe) {
        warnUser("Can't follow link to " 
                 + event.getURL().toExternalForm() + ": " + ioe);
      }
    }
  }

  private void warnUser(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", 
                                  JOptionPane.ERROR_MESSAGE);
  }
}
    
