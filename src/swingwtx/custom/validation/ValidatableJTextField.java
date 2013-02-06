/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.custom.validation;

import swingwtx.swing.*;

public class ValidatableJTextField extends ValidatableComponent implements SwingConstants {

    protected JTextField ccomp = null;
    
    public ValidatableJTextField() { ccomp = new JTextField(); comp = ccomp; setupComponent();}
    public ValidatableJTextField(int columns) { ccomp = new JTextField(columns); comp = ccomp; setupComponent();}
    public ValidatableJTextField(String text) { ccomp = new JTextField(text); comp = ccomp; setupComponent();}
    public ValidatableJTextField(String text, int columns) { ccomp = new JTextField(text, columns); comp = ccomp; setupComponent();}
    
    public String getText() { return ccomp.getText(); }
    public void setText(final String text) { ccomp.setText(text); }
    public int getColumns() { return ccomp.getColumns(); } 
    public void setColumns(int columns) { ccomp.setColumns(columns); }
    public void setEditable(boolean b) { ccomp.setEditable(b); }
    public boolean isEditable() { return ccomp.isEditable(); }
    
}
