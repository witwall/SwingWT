/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.datatransfer;

import org.eclipse.swt.dnd.*;

/**
 * This class differs slightly from the AWT implementation.
 * Mostly because type mapping is massively different between
 * the AWT and SWT implementations, so this works, you can knock
 * a couple of parameters off your calls, but it's woefully
 * inadequate and can only handle text.
 *
 */
public class Clipboard {

    private org.eclipse.swt.dnd.Clipboard clip = new org.eclipse.swt.dnd.Clipboard(swingwtx.swing.SwingWTUtils.getDisplay());
    
    public Clipboard() {}
   
    /**
     * Creates an instance of <code>Clipboard</code>
     * @param name <code>String</code>
     * @author Niklas Stopp
     * <p>name is not used. Just supplied for Swing support</p>
     **/
    public Clipboard(String  name) {}
    
    public Object getContents() {
        return clip.getContents(TextTransfer.getInstance());
    }
    
    /** NOT IMPLEMENTED - use setContents(Object) for now */
    public void setContents(Transferable contents, ClipboardOwner owner) {    
    }
    
    /** NOT IMPLEMENTED - use getContents() */
    public Transferable getContents(Object owner) {
        return null;
    }
    
    public String getName() {
        return "";
    }
    
    public void setContents(Object contents) {
        clip.setContents(new Object[] { contents }, new Transfer[] { TextTransfer.getInstance() });
    }
    
}
