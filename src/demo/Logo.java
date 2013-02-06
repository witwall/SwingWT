package demo;


public class Logo {

	public Logo() {

		// Lets do it in Swing first
		javax.swing.JFrame swFrame = new javax.swing.JFrame();
		swFrame.getContentPane().setLayout(new java.awt.BorderLayout());
		swFrame.setSize(640, 480);
		javax.swing.JDesktopPane p = new javax.swing.JDesktopPane();
		swFrame.getContentPane().add(p, java.awt.BorderLayout.CENTER);
		javax.swing.JInternalFrame f = new javax.swing.JInternalFrame();
                f.setClosable(true); f.setMaximizable(true); f.setIconifiable(true);
		p.add(f);
		f.getContentPane().setLayout(new java.awt.BorderLayout());
		javax.swing.JLabel swLabel = new javax.swing.JLabel("SwingWT");
		swLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 42));
		f.getContentPane().add(swLabel, java.awt.BorderLayout.CENTER);
		f.show();
		try {
                    f.setMaximum(true);
			f.setSelected(true);
		}
		catch (Exception e) {}
		swFrame.show();

		// Now SwingWT
		swingwtx.swing.JFrame wtFrame = new swingwtx.swing.JFrame();
		wtFrame.getContentPane().setLayout(new swingwt.awt.BorderLayout());
		swingwtx.swing.JLabel wtLabel = new swingwtx.swing.JLabel("SwingWT");
		wtLabel.setFont(new swingwt.awt.Font("Dialog", swingwt.awt.Font.BOLD + swingwt.awt.Font.ITALIC, 36));
		wtFrame.getContentPane().add(wtLabel, swingwt.awt.BorderLayout.CENTER);
		wtFrame.setSize(640, 480);
		wtFrame.show();

	}


	public static void main(String[] args) {
		new Logo();
	}
}
