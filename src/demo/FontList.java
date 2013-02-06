package demo;

import swingwtx.swing.*;
import swingwt.awt.*;
import swingwt.awt.event.*;
import java.util.*;

public class FontList implements Runnable {
	private static String MESSAGE = "The quick brown fox jumps over the lazy dog.";

	public void run() {
	    GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    String[] fontnames = e.getAvailableFontFamilyNames();
	    Arrays.sort(fontnames);
	    JPanel b = new JPanel();
	    b.setLayout(new BoxLayout(b, BoxLayout.Y_AXIS));
	    for (String f : fontnames) {
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
	    SwingUtilities.invokeLater(new FontList());
	}
}

