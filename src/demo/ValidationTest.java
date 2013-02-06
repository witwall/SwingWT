/*
 * ValidationTest.java
 * 
 * Shows a demonstration of SwingWT's C#/WinForms like validation capability
 * 
 * @author Robin Rawson-Tetley 
 */

package demo;

import swingwt.awt.*;
import swingwt.awt.event.*;
import swingwtx.swing.*;
import swingwtx.swing.event.*;
import swingwtx.custom.validation.*;

import java.util.*;

public class ValidationTest extends JFrame {
    
    ValidatableJTextField txtFullName;
    ValidatableJTextField txtTown;
    ValidatableJTextField txtAge;
    ValidatableJTextField txtPhone;
    ValidationGroup vg;
    
    public ValidationTest() {
        
        setTitle("ValidationGroup Sample");
        setSize(new Dimension(300, 200));
        
        getContentPane().setLayout(new BorderLayout());
        
        vg = new ValidationGroup();
        
        // Create the body panel with our 4 validatable fields on
        JPanel pnlMain = new JPanel();
        pnlMain.setLayout(new TableLayout(0, 2));
        
        pnlMain.add(new JLabel("Full Name:"));
        
        txtFullName = new ValidatableJTextField();
        txtFullName.setPreferredSize(new Dimension(200, 25));
        // Validation of full name
        txtFullName.addValidationListener(new ValidationListener() {
            public void validating(ValidationEvent e) {
                if (txtFullName.getText().trim().length() == 0)
                    e.setValid(false, "Please enter the full name of the customer.");
                else {
                    if (txtFullName.getText().indexOf(" ") == -1)
                        e.setValid(false, "Please enter at least the first and last name of the customer.");
                    else
                        e.setValid(true);
                }
            }
        });
        pnlMain.add(txtFullName);
        vg.add(txtFullName); // Add to the error provider
        
        
        pnlMain.add(new JLabel("Town:"));
        
        txtTown = new ValidatableJTextField();
        txtTown.setPreferredSize(new Dimension(200, 25));
        txtTown.addValidationListener(new ValidationListener() {
            public void validating(ValidationEvent e) {
                if (txtTown.getText().trim().length() == 0)
                    e.setValid(false, "Please enter the customer's town.");
                else
                    e.setValid(true);
            }
        });
        pnlMain.add(txtTown);
        vg.add(txtTown);     // Add to the error provider
        
        pnlMain.add(new JLabel("Age:"));
        
        txtAge = new ValidatableJTextField();
        txtAge.setPreferredSize(new Dimension(200, 25));
        txtAge.addValidationListener(new ValidationListener() {
            public void validating(ValidationEvent e) {
                if (!isNumber(txtAge.getText()))
                    e.setValid(false, "Please enter the customer's age, as a whole number.");
                else
                    e.setValid(true);
            }
            public boolean isNumber(String no) {
                try { Integer.parseInt(no); } catch (NumberFormatException e) { return false; } return true;    
            }
        });
        pnlMain.add(txtAge);
        vg.add(txtAge);     // Add to the error provider
        
        pnlMain.add(new JLabel("Phone #"));
        
        txtPhone = new ValidatableJTextField();
        txtPhone.setPreferredSize(new Dimension(200, 25));
        txtPhone.addValidationListener(new ValidationListener() {
            public void validating(ValidationEvent e) {
                if (
                   (txtPhone.getText().trim().length() == 0) ||
                   (txtPhone.getText().trim().length() != 8)
                   )
                    e.setValid(false, "Please enter the customer's phone number in the format nnn-nnnn.");
                else
                    e.setValid(true);
            }
        });
        pnlMain.add(txtPhone);
        vg.add(txtPhone);     // Add to the error provider
        
        
        // Buttons
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new FlowLayout());
        
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setMnemonic('c');
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();    
            }
        });
        pnlButtons.add(btnCancel);
        
        JButton btnOk = new JButton("Ok");
        btnOk.setMnemonic('o');
        final ValidationTest valTest = this;
        btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!vg.checkValidation())
                    JOptionPane.showMessageDialog(valTest, "Validation Failed.", "Error", JOptionPane.ERROR_MESSAGE);
                else
                    dispose();
            }
        });
        pnlButtons.add(btnOk);
        setDefaultButton(btnOk);
        
        // Focus management - really just an example to show you have it flexibly can be done
        ValidationFocusManager fm = new ValidationFocusManager();
        Vector comps = new Vector();
        comps.add(txtFullName.getComponent());
        comps.add(txtTown.getComponent());
        comps.add(txtAge.getComponent());
        comps.add(txtPhone.getComponent());
        comps.add(btnCancel);
        comps.add(btnOk);
        fm.setComponents(comps);
        comps = null;
        FocusManager.setCurrentManager(fm);
        
        // Add everything to screen
        getContentPane().add(pnlMain, BorderLayout.CENTER);
        getContentPane().add(pnlButtons, BorderLayout.SOUTH);
        
        // Check validation before display
        //vg.checkValidation();
        
        // Display the form
        show();
        
    }
    
    public static void main(String[] args) {
	if (SwingWTUtils.isMacOSX()) {
	    SwingWTUtils.initialiseMacOSX(new Runnable() {
		public void run() {
                    new ValidationTest();
		}
	    });
	}
	else
	    new ValidationTest();    
    }
    
}

/**
 * Useful little class - allows you to pass a vector of
 * components in the tab order you want. 
 */
class ValidationFocusManager extends FocusManager {
    Vector comps = null;
    public Vector getComponents() { return comps; }
    public void setComponents(Vector v) { comps = v; }
    public void focusNextComponent(Component c) {
        for (int i = 0; i < comps.size(); i++) {
            if (comps.get(i).equals(c))
                if (i < comps.size() -1) {
                    ((Component) comps.get(i+1)).grabFocus();
                    return;
                }
        }
    }
    public void focusPreviousComponent(Component c) {
        for (int i = 0; i < comps.size(); i++) {
            if (comps.get(i).equals(c))
                if (i > 0) {
                    ((Component) comps.get(i-1)).grabFocus();
                    return;
                }
        }
    }
}
