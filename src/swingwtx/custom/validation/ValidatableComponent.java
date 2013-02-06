/*
 SwingWT
 Copyright(c)2003-2008, R. Rawson-Tetley

 For more information on distributing and using this program, please
 see the accompanying "COPYING" file.

 Contact me by electronic mail: bobintetley@users.sourceforge.net


 */

package swingwtx.custom.validation;

import swingwtx.swing.*;
import swingwtx.swing.event.*;
import swingwt.awt.*;
import swingwt.awt.event.*;

import java.util.*;

/**
 * This is the superclass of all components that can be validated through the
 * <code>ValidationGroup</code> class. When a component fails validation, an
 * icon is shown next to the component with a tooltip stating why the component
 * failed validation.
 * 
 * @author Robin Rawson-Tetley
 */
public abstract class ValidatableComponent extends JPanel {

	/** The component we are holding */
	protected JComponent comp = null;

	/** The component that will display the icon/tooltip */
	protected JLabel errorOutput = new JLabel();

	/** The validation group containing this component */
	protected ValidationGroup validationGroup = null;

	/** whether the component currently matches it's validation rules */
	protected boolean isValid = true;

	protected String errorMessage = "";

	/** Validation listeners */
	protected Vector validationListeners = new Vector();

	public void dispose() {
		comp.dispose();
		errorOutput.dispose();
		super.dispose();
	}

	public JComponent getComponent() {
		return comp;
	}

	public void addValidationListener(ValidationListener l) {
		validationListeners.add(l);
	}

	public void removeValidationListener(ValidationListener l) {
		validationListeners.remove(l);
	}

	/** Should be called by subclasses after component creation */
	public void setupComponent() {

		setLayout(new BorderLayout());
		add(comp, BorderLayout.CENTER);
		add(errorOutput, BorderLayout.EAST);

		// Blank icon
		errorOutput.setIcon(SwingWTUtils.getPixmap(ValidatableComponent.class, "blankicon.gif"));

		// Work out whether the component is valid by firing the
		// validation event when the component loses the focus
		comp.addFocusListener(new FocusAdapter() {
			public void focusLost(FocusEvent e) {
				fireValidation(false);
			}
		});

	}

	public void setIsValid(boolean b) {
		isValid = b;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setErrorMessage(String s) {
		errorMessage = s;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sends the validation event to the component listeners. Monitors the
	 * validation event as it comes back and updates the object accordingly.
	 * 
	 * @param forcePass
	 *            If set to true, it is assumed that validation passes - even if
	 *            it wouldn't, and the event is not called. This is used by the
	 *            clearErrors() call to reset fields.
	 */
	protected void fireValidation(boolean forcePass) {
		for (int i = 0; i < validationListeners.size(); i++) {
			ValidationEvent e = new ValidationEvent(this);
			if (!forcePass) {
				((ValidationListener) validationListeners.get(i)).validating(e);
				isValid = e.isValid();
				errorMessage = e.getErrorMessage();
			} else
				isValid = true;

			if (isValid) {
				errorOutput.setIcon(SwingWTUtils.getPixmap(ValidatableComponent.class, "blankicon.gif"));
				errorOutput.setToolTipText("");
			} else {
				errorOutput.setIcon(validationGroup.getIcon());
				errorOutput.setToolTipText(errorMessage);
			}
			// Relayout our container
			invalidate();
		}
	}

	public void setVisible(final boolean b) {
		comp.setVisible(b);
	}

	public boolean isVisible() {
		return comp.isVisible();
	}

	public void setEnabled(final boolean b) {
		comp.setEnabled(b);
	}

	public boolean isEnabled() {
		return comp.isEnabled();
	}

	public void requestFocus() {
		comp.requestFocus();
	}

	public void grabFocus() {
		comp.grabFocus();
	}

	public swingwt.awt.Color getBackground() {
		return comp.getBackground();
	}

	public void setBackground(swingwt.awt.Color c) {
		comp.setBackground(c);
	}

	public swingwt.awt.Color getForeground() {
		return comp.getForeground();
	}

	public void setForeground(swingwt.awt.Color c) {
		comp.setForeground(c);
	}

	public swingwt.awt.Font getFont() {
		return comp.getFont();
	}

	public void setFont(swingwt.awt.Font f) {
		comp.setFont(f);
	}

	public String getToolTipText() {
		return comp.getToolTipText();
	}

	public void setToolTipText(final String text) {
		comp.setToolTipText(text);
	}

	public void addActionListener(ActionListener l) {
		comp.addActionListener(l);
	}

	public void removeActionListener(ActionListener l) {
		comp.removeActionListener(l);
	}

	public void addMouseListener(MouseListener l) {
		comp.addMouseListener(l);
	}

	public void removeMouseListener(MouseListener l) {
		comp.removeMouseListener(l);
	}

	public void addKeyListener(KeyListener l) {
		comp.addKeyListener(l);
	}

	public void removeKeyListener(KeyListener l) {
		comp.removeKeyListener(l);
	}

	public void addFocusListener(FocusListener l) {
		comp.addFocusListener(l);
	}

	public void removeFocusListener(FocusListener l) {
		comp.removeFocusListener(l);
	}

	/**
	 * Getter for property validationGroup.
	 * 
	 * @return Value of property validationGroup.
	 *  
	 */
	public swingwtx.custom.validation.ValidationGroup getValidationGroup() {
		return validationGroup;
	}

	/**
	 * Setter for property validationGroup.
	 * 
	 * @param validationGroup
	 *            New value of property validationGroup.
	 *  
	 */
	public void setValidationGroup(
			swingwtx.custom.validation.ValidationGroup validationGroup) {
		this.validationGroup = validationGroup;
	}

}
