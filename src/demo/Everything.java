/*
 * SwingWT demo. Displays most components, dialogs and things
 * as an example.
 *
 * @author R. Rawson-Tetley
 *
 */

package demo;

import swingwtx.custom.*;
import swingwtx.swing.*;
import swingwtx.swing.table.*;
import swingwtx.swing.tree.*;
import swingwtx.swing.event.*;
import swingwt.awt.*;
import swingwt.awt.event.*;

public class Everything extends JFrame {
    
    private JPanel comps = null;
    
    //private ImageIcon imgPic = SwingWTUtils.getPixmap("pic.gif", SwingWTUtils.SWINGWT_PIXMAP_SUBDIR, "/demo/");
    //private ImageIcon imgComputer = SwingWTUtils.getPixmap("Computer.png", SwingWTUtils.SWINGWT_PIXMAP_SUBDIR, "/demo/");
    private ImageIcon imgPic = SwingWTUtils.getPixmap(Everything.class, "pic.gif");
    private ImageIcon imgComputer = SwingWTUtils.getPixmap(Everything.class, "Computer.png");
    
    public void test() {
        
        setTitle("Test");
        setSize(900, 500);
        setLocation(200, 200);
        
        try {
            setIconImage(imgPic.getImage());
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
        comps = new JPanel();
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
            btn3.setIcon(imgComputer);
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
        tb.setPreferredSize(new Dimension(100, 25));
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
        jp.add(ta);
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
        
        // Spinner
        JSpinner spin = new JSpinner();
        comps.add(spin);
        
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
        tab.setPreferredSize(new Dimension(200, 150));
        
        // Custom cell renderering - yeah!
        tab.getColumnModel().getColumn(0).setCellRenderer( new DefaultTableCellRenderer() {
            
            public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                    setForeground(table.getSelectionForeground());
                }
                else {
                    setBackground(table.getBackground());
                    setForeground(table.getForeground());
                }
                setIcon(imgPic);
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
        tree.setPreferredSize(new Dimension(200, 150));
        tree.addTreeExpansionListener(new TreeExpansionListener() {
            public void treeExpanded(TreeExpansionEvent event) {
                System.out.println("treeExpanded :- "+ event.getPath());
            }
            
            public void treeCollapsed(TreeExpansionEvent event) {
                System.out.println("treeExpanded :- "+ event.getPath());
            }
        });
        tree.addWillTreeExpansionListener(new TreeWillExpandListener(){
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                System.out.println("treeWillExpand :- " + event.getPath());
                //If u dont want control to go to treeExpanded() method uncomment the next line of code
                //throw new ExpandVetoException(event);
            }
            public void treeWillCollapse(TreeExpansionEvent event){
                System.out.println("treeWillCollapse :- " + event.getPath());
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
                    setIcon(imgPic);
                else
                    setIcon(null);
                setText(value.toString());
                return this;
            }
            
        });
        comps.add(tree);
        
        
        // Bit of direct Canvas rendering!
        JComponent c = new JComponent() {
            public void paint(Graphics g) {
                g.drawString("Direct Graphics Rendering", 20, 20);
                g.drawOval(40, 40, 50, 50);
            }
        };
        c.setPreferredSize(new Dimension(200, 100));
        comps.add(c);
        
        // Task Tray demo (SwingWT ONLY)
        // Popup menu on task tray
        final JPopupMenu jpop2 = new JPopupMenu();
        JMenu mnuT = new JMenu("Popup!");
        jpop2.add(mnuT);
        JMenuItem mnuY = new JMenuItem("Tray Popup");
        mnuT.add(mnuY);
        JTaskTrayItem item = new JTaskTrayItem(
        		imgPic, 
                "Sample Task Tray Icon",
                jpop2);
        item.show();
        /// -------------
        
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
            mnuFileOpenDoc.setIcon(imgPic);
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
        
        JMenuItem mnuFileExit = new JMenuItem("Exit\tCtrl+X");
        mnuFileExit.setMnemonic('x');
        mnuFileExit.setAccelerator(KeyStroke.getKeyStroke(
        swingwt.awt.event.KeyEvent.VK_X, swingwt.awt.event.InputEvent.CTRL_MASK));
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
    
    public void testTabs() {
        
        setTitle("Testing Tabs");
        setLayout(new BorderLayout());
        
        JTabbedPane jtp = new JTabbedPane();
        
        JPanel pnl1 = new JPanel();
        pnl1.setLayout(new FlowLayout());
        
        JLabel lblTest = new JLabel("Test");
        pnl1.add(lblTest);
        
        JPanel pnl2 = new JPanel();
        pnl2.setLayout(new GridLayout(2,2));
        
        JButton btnTest = new JButton();
        btnTest.setIcon(imgPic);
        pnl2.add(btnTest);
        
        jtp.addTab("Test", pnl1);
        jtp.addTab("Again", pnl2);
        
        add(jtp, BorderLayout.CENTER);
        
        jtp.setIconAt(1, imgComputer);
        
        show();
        dispatchEvents();
        dispose();
        
    }
    
    public void testSlider() {
        setTitle("Test Slider");
        setSize(640, 480);
        setLocation(200, 200);
        org.eclipse.swt.widgets.Shell s = (org.eclipse.swt.widgets.Shell) this.getPeer();
        s.setLayout(new org.eclipse.swt.layout.RowLayout());
        org.eclipse.swt.widgets.Slider sl = new org.eclipse.swt.widgets.Slider(s, org.eclipse.swt.SWT.BORDER | org.eclipse.swt.SWT.HORIZONTAL);
        sl.setMaximum(100);
        sl.setMinimum(0);
        sl.setSelection(50);
        sl.setThumb(10);
        
        show();
        dispose();
        
    }
    
    public class TestAction extends AbstractAction {
        
        public void actionPerformed(ActionEvent e) {
        }
        
    }
    
    public static void main(String[] args) {
	if (SwingWTUtils.isMacOSX()) {
	    SwingWTUtils.initialiseMacOSX(new Runnable() {
		public void run() {
                    new Everything().test();
		}
	    });
	}
	else
	    new Everything().test();
    }
    
}
