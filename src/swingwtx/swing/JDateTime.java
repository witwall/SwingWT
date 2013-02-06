/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwtx.swing;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DateTime;


public class JDateTime extends JComponent {
    
    protected DateTime ppeer = null;
    protected SimpleDateFormat format = new SimpleDateFormat();
    protected String pText = null;
    private Object retval = null;
    
    public JDateTime(SimpleDateFormat format) {
        this.format = format;
    }
    
    public void setText(final String text) {
        if (!SwingWTUtils.isSWTControlAvailable(ppeer))
            pText = text;
        SwingUtilities.invokeSync(new Runnable() { 
                public void run() {
                    // Parse the text 
                    try {
                        Date d = format.parse(text);
                        Calendar c = Calendar.getInstance();
                        c.setTime(d);
                        ppeer.setDay(c.get(Calendar.DAY_OF_MONTH));
                        ppeer.setMonth(c.get(Calendar.MONTH));
                        ppeer.setYear(c.get(Calendar.YEAR));
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        });
    }
    
    public String getText() {
        if (!SwingWTUtils.isSWTControlAvailable(ppeer))
            return pText;
        SwingUtilities.invokeSync(new Runnable() { 
                public void run() {
                    // Make a date from the current selection
                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.YEAR, ppeer.getYear());
                    c.set(Calendar.MONTH, ppeer.getMonth());
                    c.set(Calendar.DAY_OF_MONTH, ppeer.getDay());
                    Date d = c.getTime();
                    retval = format.format(d);                    
                }
            });
        return (String) retval;
    }
    
    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new DateTime(parent.getComposite(), 
                (border instanceof swingwtx.swing.border.EmptyBorder ? SWT.NONE : SWT.BORDER ));
        
        if (pForeground != null) setForeground(pForeground);
        if (pBackground != null) setBackground(pBackground);
        if (pText != null) setText(pText);
        if (pFont != null) ppeer.setFont(pFont.getSWTFont());
        if (pSize != null) ppeer.setSize(pSize.width, pSize.height);
        
        peer = ppeer;
        this.parent = parent;
    }

}
