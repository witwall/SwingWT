package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class FontListSwing implements Runnable {
	private static String MESSAGE = "The quick brown fox jumps over the lazy dog.";

	public void run() {
	    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    String[] fontnames = e.getAvailableFontFamilyNames();
	    Arrays.sort(fontnames);
	    JPanel b = new JPanel();
	    b.setLayout(new BoxLayout(b, BoxLayout.Y_AXIS));
	   /* for (String f : fontnames) {*/
	    for(int i=0;i<fontnames.length;i++){
	    	String f=fontnames[i];
	    	Font font = new Font(f, Font.BOLD, 14);
	    	JLabel l = new JLabel(MESSAGE + " (" + f + ")");
	    	l.setFont(font);
	    	b.add(l);
	    }
	    JScrollPane p = new JScrollPane(b);
	    p.setPreferredSize(new Dimension(800, 600));
	    JFrame f = new JFrame("Font List");
	    f.setContentPane(p);
	    f.pack();
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setLocationRelativeTo(null);
	    f.setVisible(true);
	}

	public static void main(String[] args) {
	    SwingUtilities.invokeLater(new FontListSwing());
	}
}

