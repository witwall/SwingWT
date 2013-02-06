/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.custom.validation;

import swingwtx.swing.*;

import java.util.*;

/**
 * This isn't a real Swing class at all! This is a useful class that someone suggested
 * we include from C# WinForms! After all, if we're going to make a new GUI API that matches Swing
 * there's nothing wrong with including new bits and pieces if they're useful :)
 *
 * Besides, I hear the JGoodies people are charging for an implementation of this - only took
 * me a couple of hours to implement!
 *
 * This class allows you to monitor validation errors. Each of the validatable components
 * supports a new event listener called "ValidateListener", passing in a ValidateEvent - your
 * code should decide whether the component is ok and set true or false with a message. The same 
 * component can be a member of different validation groups, as each validation group allows one stock icon.
 *
 * I had a long think about this and decided the best way to deal with this was to make some
 * new versions of the regular components you might want to validate. They are all prefixed
 * with the word "Validatable" - eg: ValidatableJTextField, ValidatableJTextArea.
 *
 * @author  Robin Rawson-Tetley
 */
public class ValidationGroup {
    
    protected ImageIcon pImage = null;
    protected Vector components = new Vector();
    
    /** Last seen error message from a component */
    protected String errorMessage = "";
    
    /** Creates an error provider with the default error icon */
    public ValidationGroup() { 
    	pImage = SwingWTUtils.getPixmap(ValidationGroup.class, "erroricon.gif");
    }
    public ValidationGroup(ImageIcon image) { if (image == null) throw new IllegalArgumentException("Image can't be null"); pImage = image; }
    
    public void add(ValidatableComponent c) { c.setValidationGroup(this); components.add(c); }
    public void remove(ValidatableComponent c) { c.setValidationGroup(null); components.remove(c); }
    
    public ImageIcon getIcon() { return pImage; }
    public String getErrorMessage() { return errorMessage; }
    
    /** Calls the validation routines for each component this ValidationGroup is managing 
     *  @return true if validation is ok, otherwise returns false. Call getErrorMessage()
     *  for the last error message from a component.
     */
    public boolean checkValidation() {
        boolean andCheck = true;
        for (int i = 0; i < components.size(); i++) {
            ValidatableComponent c = (ValidatableComponent) components.get(i);
            c.fireValidation(false);
            if (!c.isValid()) {
                errorMessage = c.getErrorMessage();
            }
            andCheck = andCheck && c.isValid();
        }
        return andCheck;
    }

    /** Clears all error messages and icons from all components this
     *  ValidationGroup is managing.
     */
    public void clearErrors() {
        for (int i = 0; i < components.size(); i++) {
	    ValidatableComponent c = (ValidatableComponent) components.get(i);
	    c.fireValidation(true);
	}
	errorMessage = "";
    }

}
