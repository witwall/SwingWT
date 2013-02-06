/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Shell;

import swingwt.awt.Color;
import swingwt.awt.Component;
import swingwt.awt.HeadlessException;
import swingwt.awt.event.ActionListener;

/**
 * JColorChooser cannot descend a Swing component in SWT as it is not
 * an embeddable widget. Given time, we could develop one from scratch
 * that behaved the same, but no time at present.
 */
public class JColorChooser {

    protected Color initialColor;
    protected String dialogTitle = "Select Colour";
    
    public JColorChooser() {
        this(Color.white);
    }
    
    public JColorChooser(Color initialColor) {
	this.initialColor = initialColor;
    }
    
    public static Color showDialog(Component component,
        String title, Color initialColor) throws HeadlessException {

        if (initialColor == null) initialColor = Color.white;
        ColorDialog cd = new ColorDialog(component.getSWTPeer().getShell(), SWT.NONE);
        cd.setText(title);
        cd.setRGB(initialColor.getSWTColor().getRGB());
        org.eclipse.swt.graphics.RGB chosenCol = cd.open();
        if (chosenCol == null)
            return null;
        else
            return new swingwt.awt.Color(chosenCol.red, chosenCol.green, chosenCol.blue);

    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @param b
     */
    public void setDragEnabled(boolean b) {
    	
    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @return
     */
    public Color getColor() {
    	return initialColor;
    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @param c
     * @param title
     * @param modal
     * @param chooserPane
     * @param okListener
     * @param cancelListener
     * @return
     */
    public static JDialog createDialog(Component c, String title, boolean modal,
            JColorChooser chooserPane, ActionListener okListener,
            ActionListener cancelListener) {
    	return null;
    	//return chooserPane.new ColorChooserDialog(c, title, modal, okListener, cancelListener);
    }
    
    class ColorChooserDialog extends JDialog {
    	ColorDialog dialog;
    	 ActionListener okListener;
         ActionListener cancelListener;
         
    	/**
		 * TODO Comment!!
		 *
		 */
		public ColorChooserDialog(Component c, String title, boolean modal, ActionListener okListener, ActionListener cancelListener) {
			this.peer = new Shell(c.getSWTPeer().getShell(),  SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			this.dialog = new ColorDialog(c.getSWTPeer().getShell(), SWT.EMBEDDED);
			dialog.setRGB(initialColor.getSWTColor().getRGB());
			this.okListener = okListener;
			this.cancelListener = cancelListener;
		}
		
		public void show() {
			//peer.open();	
			RGB rgb = dialog.open();
			if (null == rgb) {
				if (null != cancelListener) {
					cancelListener.actionPerformed(null);
				}
			} else {
				JColorChooser.this.initialColor = new swingwt.awt.Color(rgb.red, rgb.green, rgb.blue);
				okListener.actionPerformed(null);
			}
		}
    }
}
