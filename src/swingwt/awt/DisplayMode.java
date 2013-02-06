package swingwt.awt;

public class DisplayMode {
	// FIXME specify which mode
	public DisplayMode() {
	}

	public int getWidth() {
		// FIXME ask SWT
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}
	public int getHeight() {
		// FIXME ask SWT
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

}
