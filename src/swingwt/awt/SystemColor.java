/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import org.eclipse.swt.SWT;

import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingWTUtils;

/**
 * System Color mapped implementation to
 * values read from SWT
 *
 * @author  Robin Rawson-Tetley
 */
public final class SystemColor extends Color {
	
	/**
	 * Create cached array of all SWT system colors
	 */
	
	// COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT is the last color index (1-indexed)
	private static SystemColor[] systemColors = new SystemColor[SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT];
	static {
		for (int i=0; i<systemColors.length; i++) {
			systemColors[i] = new SystemColor(i+1);
		}
	}

	public final static SystemColor getSystemColor(int swtId) {
		return systemColors[swtId-1];
	}
	
	/**
     * Convenience references to system colors
     */
    public final static SystemColor desktop = getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
    public final static SystemColor activeCaption = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
    public final static SystemColor activeCaptionText = getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
    public final static SystemColor activeCaptionBorder = getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
    public final static SystemColor inactiveCaption = getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND);
    public final static SystemColor inactiveCaptionText = getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND);
    public final static SystemColor inactiveCaptionBorder = getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT);
    public final static SystemColor window = getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
    public final static SystemColor windowBorder = getSystemColor(SWT.COLOR_WIDGET_BORDER);
    public final static SystemColor windowText = getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
    public final static SystemColor menu = getSystemColor(SWT.COLOR_INFO_BACKGROUND);
    public final static SystemColor menuText = getSystemColor(SWT.COLOR_INFO_FOREGROUND);
    public final static SystemColor text = getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
    public final static SystemColor textText = getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
    public final static SystemColor textHighlight = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
    public final static SystemColor textHighlightText = getSystemColor(SWT.COLOR_TITLE_FOREGROUND);
    public final static SystemColor textInactiveText = getSystemColor(SWT.COLOR_TITLE_INACTIVE_FOREGROUND);
    public final static SystemColor control = getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
    public final static SystemColor controlText = getSystemColor(SWT.COLOR_WIDGET_FOREGROUND);
    public final static SystemColor controlHighlight = getSystemColor(SWT.COLOR_TITLE_BACKGROUND);
    public final static SystemColor controlLtHighlight = getSystemColor(SWT.COLOR_TITLE_BACKGROUND_GRADIENT);
    public final static SystemColor controlShadow = getSystemColor(SWT.COLOR_WIDGET_NORMAL_SHADOW);
    public final static SystemColor controlDkShadow = getSystemColor(SWT.COLOR_WIDGET_DARK_SHADOW);
    public final static SystemColor scrollbar = getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND);
    public final static SystemColor info = getSystemColor(SWT.COLOR_INFO_BACKGROUND);
    public final static SystemColor infoText = getSystemColor(SWT.COLOR_INFO_FOREGROUND);

    private int colorId;

    /**
     * Overridden constructors that ensure we're only accessed from static references initialized with
     * SystemColor(int).
     * @param swtColour
     */
    private SystemColor(org.eclipse.swt.graphics.Color swtColour) { super(swtColour); }
    private SystemColor(float r, float g, float b) { super(r, g, b); }
    private SystemColor(float r, float g, float b, float a) { super(r, g, b); }
    private SystemColor(int r, int g, int b) { super(r, g, b); }
    private SystemColor(int r, int g, int b, int a) { super(r, g, b, a); }
    
    // This is maybe weird, but just use the int (rgb) constructor for SystemColor
    private SystemColor(int colorId) { super(null); this.colorId = colorId; }
    
    private boolean initialized = false;
    private void validate() {
        if (!initialized) {
            SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                swtColour = SwingWTUtils.getDisplay().getSystemColor(colorId);
                initialized = true;
            }});
        }
    }
    
    /**
     * Versions of base Color classes that checks that the SWT color has already been initialized.
     * These should ONLY be called from the paint thread, or you'll get an InvalidAccess exception!
     */
    
    public org.eclipse.swt.graphics.Color getSWTColor()
    {
        validate();
        return super.getSWTColor();
    }
    public int getRed() { validate(); return super.getRed(); }
    public int getGreen() { validate(); return super.getGreen(); }
    public int getBlue() { validate(); return super.getBlue(); }
    public int getRGB() { validate(); return super.getBlue(); }
    protected int[] breakRGBComponents(int value) { validate(); return super.breakRGBComponents(value); }
    public Color brighter() { validate(); return super.brighter(); }
    public Color darker() { validate(); return super.darker(); }
    public String toString() { validate(); return super.toString(); }
    
    // Since we didn't create the color (Display class did), we are not responsible for disposing of it.
    public void dispose() {}
}
