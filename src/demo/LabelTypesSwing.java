package demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LabelTypesSwing implements Runnable {
    private static final int V[] = new int[] { SwingConstants.TOP, SwingConstants.CENTER, SwingConstants.BOTTOM };
    private static final String V_NAMES[] = new String[] { "TOP", "CENTER", "BOTTOM" };
    private static final int H[] = new int[] { SwingConstants.LEFT, SwingConstants.CENTER, SwingConstants.RIGHT };
    private static final String H_NAMES[] = new String[] { "LEFT", "CENTER", "RIGHT" };

    private static class Type {
        private int v, h;
        public Type(int v, int h) {
            this.v = v;
            this.h = h;        
        }

        public void apply(JLabel label) {
            label.setVerticalTextPosition(V[v]);
            label.setHorizontalTextPosition(H[h]);
        }

        public String toString() {
            return V_NAMES[v] + "," + H_NAMES[h];
        }
    }

    public void run() {
        JFrame f = new JFrame();
        final JLabel label = new JLabel();
        label.setText("Computer II");
        label.setIcon(new ImageIcon(getClass().getResource("Computer.png")));

        ArrayList<Type> types = new ArrayList<Type>();
        for (int v = 0; v < 3; v++)
            for (int h = 0; h < 3; h++)
                types.add(new Type(v, h));

        final JComboBox selector = new JComboBox(types.toArray());
        selector.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                Type t = (Type) selector.getSelectedItem();
                if (t != null)
                    t.apply(label);
            }
        });


        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(selector, BorderLayout.NORTH);
        f.getContentPane().add(label, BorderLayout.CENTER);
        f.setSize(600, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LabelTypesSwing());
    }
}
