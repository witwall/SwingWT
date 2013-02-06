/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.event;

/**
 * This event object is used to determine if a particular
 * component passes/fails it's validation.
 */
public class ValidationEvent extends java.util.EventObject {
    
    protected boolean valid = false;
    protected String errorMessage = "";
    
    public ValidationEvent(Object source) { super(source); }
    
    /** Getter for property errorMessage. 
     * @return Value of property errorMessage.
     *
     */
    public java.lang.String getErrorMessage() {
        return errorMessage;
    }    
    
    /** Setter for property errorMessage. Set this value if
     *  the component fails validation and the reason why
     * @param errorMessage New value of property errorMessage.
     *
     */
    public void setErrorMessage(java.lang.String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    /** Getter for property valid.
     * @return Value of property valid.
     *
     */
    public boolean isValid() {
        return valid;
    }
    
    /** Setter for property valid. Set this appropriately
     *  depending on whether the component passes validation
     * @param valid New value of property valid.
     *
     */
    public void setValid(boolean valid) {
        this.valid = valid;
        if (valid) errorMessage = "";
    }
    
    public void setValid(boolean valid, String errorMessage) {
        this.valid = valid;
        this.errorMessage = errorMessage;
    }
    
}
