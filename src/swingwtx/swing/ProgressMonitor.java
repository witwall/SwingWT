
package swingwtx.swing;
import swingwtx.accessibility.*;
import swingwt.awt.*;

import swingwt.awt.event.*;
import swingwtx.swing.event.*;

import java.text.*;
import java.util.Locale;

//TODO: implements progress-monitor
// This is just a stub to make it compile
public class ProgressMonitor extends Object implements Accessible
{

    public ProgressMonitor(Component parentComponent,
                           Object message,
                           String note,
                           int min,
                           int max) 
    {
    
    }
	public AccessibleContext getAccessibleContext() 
	{
	    return ProgressMonitor.this.getAccessibleContext();
	}
    public void setProgress(int nv) 
    {
 
    }


    public void close() {
    }


    public int getMinimum() {
    	return 0;
    }

    public void setMinimum(int m) {
        
    }
    public int getMaximum() {
        return 0;
    }
    public void setMaximum(int m) {
        
    }
    public boolean isCanceled() 
    {
    	return false;
    }
    public void setMillisToDecideToPopup(int millisToDecideToPopup) {

    }
    public int getMillisToDecideToPopup() {
    	return 0;
    }
    public void setMillisToPopup(int millisToPopup) {
        
    }
    public int getMillisToPopup() {
        return 0;
    }
    public void setNote(String note) {
    }
    public String getNote() {
        return "note";
    }
    protected AccessibleContext accessibleContext = null;
    
 
    
}
