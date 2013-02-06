/*
 * Swing demo. Displays most components, dialogs and things
 * as an example using Swing for comparison.
 *
 * @author R. Rawson-Tetley
 *
 */

package demo;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class EverythingSwing extends JFrame {
    
    public void test() {

        setTitle("Test");
        setSize(1000, 340);
        setLocation(200, 200);
        
        try {
            setIconImage(new ImageIcon(getClass().getResource("/demo/pic.gif")).getImage());
        }
        catch (Exception e) {}
        
        getContentPane().setLayout(new BorderLayout());
        
        // When the frame gets closed
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                checkClose();
            }
        });
        
        // Central panel to hold components
        JPanel comps = new JPanel();
        comps.setLayout(new FlowLayout());
        
        // Buttons and ActionEvents
        JButton btn = new JButton("Button");
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showConfirm();    
            }
        });
        btn.setMnemonic('B');
        btn.setToolTipText("Test tooltip!");
        comps.add(btn);
        
        comps.add(new JLabel("Quick test"));
        JButton btn2 = new JButton("This works!");
        btn2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessage();
            }
        });
        comps.add(btn2);
        
        
        // Popup menu on the image button
        final JPopupMenu jpop = new JPopupMenu();
        JMenu mnuP = new JMenu("Popup!");
        jpop.add(mnuP);
        JMenuItem mnuI = new JMenuItem("Test Item");
        mnuP.add(mnuI);
        
        // Images on buttons
        final JButton btn3 = new JButton("Image Button");
        try {
            btn3.setIcon(new ImageIcon(getClass().getResource("/demo/Computer.png")));
        }
        catch (Exception e) {}
        btn3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jpop.show(btn3, 0, 0);
            } 
        });
        comps.add(btn3);
        
        // Text boxes and focus events
        JTextField tb = new JTextField("Text box");
        tb.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                System.out.println("Text box got focus");
            }
            public void focusLost(FocusEvent e) {
            }
        });
        comps.add(tb);
        
        // Text areas, scrollpanes and key events
        JScrollPane jp = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JTextArea ta = new JTextArea("Text Area");
        ta.addKeyListener( new KeyListener() {
            public void keyTyped(KeyEvent e) {
                showMessage();    
            }
            public void keyPressed(KeyEvent e) {
                showMessage();  
            }
            public void keyReleased(KeyEvent e) {
            }
        });
        jp.getViewport().add(ta);
        comps.add(jp);
        
        comps.add(new JCheckBox("Test"));

        ButtonGroup bg = new ButtonGroup();
        JRadioButton rad1 = new JRadioButton("Radio1");
        JRadioButton rad2 = new JRadioButton("Radio2");
        bg.add(rad1);
        bg.add(rad2);
        bg.setSelected(rad1.getModel(), true);
        comps.add(rad1);
        comps.add(rad2);
        
        // Password fields
        JPasswordField pass = new JPasswordField();
        pass.setEchoChar('*');
        pass.setPreferredSize(new Dimension(100, 25));
        comps.add(pass);
        
        // Combo boxes
        JComboBox jc = new JComboBox();
        jc.setEditable(false);
        jc.addItem("Test 1");
        jc.addItem("Test 2");
        jc.addItem("Test 3");
        comps.add(jc);
        
        
        // Lists
        JList jl = new JList( new String[] { "Item 1", "Item 2", "Item 3" } );
        JScrollPane jlScr = new JScrollPane(jl);
        jlScr.setPreferredSize(new Dimension(50, 100));
        comps.add(jlScr);
        
        // Slider
        JSlider slid = new JSlider(JSlider.VERTICAL, 1, 100, 50);
        slid.setPreferredSize(new Dimension(30, 150));
        comps.add(slid);
        
        // Tables -- oooh! Yeah baby! You like that?
        Object[] cols = { "Column 1", "Column 2" };
        Object[][] data = new Object[2][2];
        data[0][0] = "Col 1 data";
        data[0][1] = "Col 2 data";
        data[1][0] = "Col 1.1 data";
        data[1][1] = "Col 2.1 data";
        DefaultTableModel dm = new DefaultTableModel(data, cols);
        JTable tab = new JTable();
        tab.setModel(dm);
        tab.setCellSelectionEnabled(false);
        tab.setRowSelectionAllowed(true);
        tab.setPreferredSize(new Dimension(150, 100));
        
        // Custom cell renderering - yeah!
        tab.getColumnModel().getColumn(0).setCellRenderer( new DefaultTableCellRenderer() {
            
            private Font renderFont = new Font("Dialog", 0, 12);
            
            public Component getTableCellRendererComponent(JTable table, Object value,
					    boolean isSelected, boolean hasFocus, 
					    int row, int column) {
             
                 setFont(renderFont);
                 if (isSelected) {
                     setBackground(table.getSelectionBackground());
                     setForeground(table.getSelectionForeground());
                 }
                 else
                 {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                 }
                 setIcon(new ImageIcon(getClass().getResource("/demo/pic.gif")));
                 setValue(value);
                 return this;
            }
            
        });
        JScrollPane tabScr = new JScrollPane(tab);
        tabScr.setPreferredSize(new Dimension(150, 100));
        comps.add(tabScr);
        
        // Trees... woohoo!
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("RootNode");
        
        DefaultMutableTreeNode node11   = new DefaultMutableTreeNode("Node 11");
        DefaultMutableTreeNode node12   = new DefaultMutableTreeNode("Node 12");
        
        DefaultMutableTreeNode book111 = new DefaultMutableTreeNode("Node 111");
        DefaultMutableTreeNode book112 = new DefaultMutableTreeNode("Node 112");
        
        DefaultMutableTreeNode book121  = new DefaultMutableTreeNode("Node 121");
        DefaultMutableTreeNode book122  = new DefaultMutableTreeNode("Node 122");
        
        DefaultMutableTreeNode book131 = new DefaultMutableTreeNode("Node 131");
        DefaultMutableTreeNode book132 = new DefaultMutableTreeNode("Node 132");
        
        DefaultMutableTreeNode book141 = new DefaultMutableTreeNode("Node 141");
        DefaultMutableTreeNode book142 = new DefaultMutableTreeNode("Node 142");
        
        DefaultMutableTreeNode book151 = new DefaultMutableTreeNode("Node 151");
        DefaultMutableTreeNode book152 = new DefaultMutableTreeNode("Node 152");
        
        
        rootNode.add(node11);
        rootNode.add(node12);
        
        node12.add(book111);
        node12.add(book112);
        
        book112.add(book121);
        book112.add(book122);
        
        book122.add(book131);
        book122.add(book132);
        
        book132.add(book141);
        book132.add(book142);
        
        book142.add(book151);
        book142.add(book152);
        
        
        JTree tree = new JTree(rootNode);
        tree.setPreferredSize(new Dimension(150, 100));
        tree.addTreeExpansionListener(new TreeExpansionListener() {
            public void treeExpanded(TreeExpansionEvent event) {
                System.out.println("treeExpanded :- "+ event.getPath());
            }
            
            public void treeCollapsed(TreeExpansionEvent event) {
                System.out.println("treeExpanded :- "+ event.getPath());
            }
        });
        
        // Custom tree rendering!
        tree.setCellRenderer( new DefaultTreeCellRenderer() {
            
            public Component getTreeCellRendererComponent(JTree tree,
            Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                
                super.getTreeCellRendererComponent(
                tree, value, selected,
                expanded, leaf, row,
                hasFocus);
                
                if (value.toString().indexOf("Root") != -1)
                    setIcon(new ImageIcon(getClass().getResource("/demo/pic.gif")));
                else
                    setIcon(null);
                setText(value.toString());
                return this;
            }
            
        });
        JScrollPane treeScr = new JScrollPane(tree);
        treeScr.setPreferredSize(new Dimension(150, 150));
        comps.add(treeScr);
        
        // Bit of direct Canvas rendering!
        JComponent c = new JComponent() {
            public void paint(Graphics g) {
                g.drawString("Direct Graphics Rendering", 20, 20);
                g.drawOval(40, 40, 50, 50);
            }
        };
        c.setPreferredSize(new Dimension(200, 100));
        comps.add(c);
        
        
        // Menu stuff
        JMenuBar mb = new JMenuBar();
        
        JMenu mnuFile = new JMenu("File");
        mnuFile.setMnemonic('F');
        
        JCheckBoxMenuItem mnuFileToggle = new JCheckBoxMenuItem("Toggle", true);
        JRadioButtonMenuItem mnuFileRadio = new JRadioButtonMenuItem("Radio", true);
        JRadioButtonMenuItem mnuFileRadio2 = new JRadioButtonMenuItem("Radio2", false);
        
        JMenu mnuFileOpen = new JMenu("Open");
        mnuFileOpen.setMnemonic('O');
        JMenuItem mnuFileOpenDoc = new JMenuItem("Document");
        mnuFileOpenDoc.setMnemonic('D');
        mnuFileOpenDoc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessage();
            }
        });
        
        try {
            mnuFileOpenDoc.setIcon(new ImageIcon(getClass().getResource("/demo/pic.gif")));
        }
        catch (Exception e) {}
        
        JMenuItem mnuFileOpenFileDialog = new JMenuItem("File Dialog");
        mnuFileOpenFileDialog.setMnemonic('i');
        mnuFileOpenFileDialog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFileDialog();
            }
        });
        
        JMenuItem mnuFileOpenColorDialog = new JMenuItem("Color Dialog");
        mnuFileOpenColorDialog.setMnemonic('C');
        mnuFileOpenColorDialog.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showColorDialog();
            }
        });
        
        JMenuItem mnuFileExit = new JMenuItem("Exit");
        mnuFileExit.setMnemonic('x');
        mnuFileExit.setAccelerator(KeyStroke.getKeyStroke(
            KeyEvent.VK_X, InputEvent.CTRL_MASK));
        mnuFileExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                System.exit(0);
            }
        });
        
        
        mb.add(mnuFile);
            mnuFile.add(mnuFileToggle);
            mnuFile.addSeparator();
            mnuFile.add(mnuFileRadio); 
            mnuFile.add(mnuFileRadio2); 
            mnuFile.addSeparator();
            mnuFile.add(mnuFileOpen);
                mnuFileOpen.add(mnuFileOpenDoc);
                mnuFileOpen.add(mnuFileOpenFileDialog);
                mnuFileOpen.add(mnuFileOpenColorDialog);
            mnuFile.add(mnuFileExit);
        
        setJMenuBar(mb);
        
        // Toolbars !
        JToolBar bar = new JToolBar();
        getContentPane().add(bar, BorderLayout.NORTH);
        JButton btnTool1 = new JButton("ToolButton 1");
        bar.add(btnTool1);
        JButton btnTool2 = new JButton("ToolButton 2");
        bar.add(btnTool2);
        
        // Splitpane the lot!
        JSplitPane jsp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JButton("SplitPane"), comps);
        jsp.setResizeWeight(0.1);
        getContentPane().add(jsp, BorderLayout.CENTER);

        // Show the frame
        show();
    }
    
    public void showMessage() {
        JOptionPane.showMessageDialog(this, "You did something and I caught the event!");
    }
    
    public void showConfirm() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Are you sure you wanted to press that?"))
            System.out.println("You picked yes");
        else
            System.out.println("You picked no");
    }
    
    public void checkClose() {
        if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this, "Sure you want to close?")) {
            dispose();                        
            System.exit(0);
        }
    }
    
    public void showFileDialog() {
        try {
            JFileChooser jf = new JFileChooser();
            jf.setSelectedFile(new java.io.File(System.getProperty("user.home")));
            int picked = jf.showOpenDialog(this);
            if (picked == JFileChooser.CANCEL_OPTION)
                System.out.println("You cancelled the dialog");
            else {
                System.out.println("You chose: " + jf.getSelectedFile().getAbsolutePath());
            }   
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void showColorDialog() {
        try {
            JColorChooser jf = new JColorChooser();
            Color c = JColorChooser.showDialog(this, "Choose that colour!", null);
            if (c == null)
                System.out.println("You cancelled the dialog");
            else {
                System.out.println("You chose: " + c.toString());
            }   
        }
        catch (Exception e)  {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new EverythingSwing().test();
        //new Everything().testTabs();
    }
    
}
