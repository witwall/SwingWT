
package swingwtx.swing;

import swingwt.awt.*;
import swingwt.awt.event.*;



import java.io.*;
import java.text.*;
import java.util.*;

public class JFormattedTextField extends JTextField 
{
    public static final int COMMIT = 0;
    public static final int COMMIT_OR_REVERT = 1;
    public static final int REVERT = 2;
    public static final int PERSIST = 3;
    public JFormattedTextField() {
        super();
    }
    public JFormattedTextField(Object value) {
        this();
        setValue(value);
    }
    public JFormattedTextField(java.text.Format format) 
    {
        this();

    }
    public JFormattedTextField(AbstractFormatter formatter) 
    {
    }
    public JFormattedTextField(AbstractFormatterFactory factory) 
    {
   
    }
    public JFormattedTextField(AbstractFormatterFactory factory, Object currentValue) 
    {
    }
    public void setFocusLostBehavior(int behavior) 
    {
   
    }
    public int getFocusLostBehavior() 
    {
        return 0;
    }
    public void setFormatterFactory(AbstractFormatterFactory tf) 
    {
    }
    public AbstractFormatterFactory getFormatterFactory() {
        return null;
    }
    public AbstractFormatter getFormatter() 
    {
        return null;
    }
    public void setValue(Object value) 
    {
    
    }
    public Object getValue() 
    {
        return null;
    }
    public void commitEdit() throws ParseException {
    }
    public boolean isEditValid() {
        return false;
    }
	public void run() 
	{

    }
    public Action[] getActions() 
    {
    	return null;
    }
    public String getUIClassID() 
    {
        return "JFormattedTextFieldUI";
    }
//    public void setDocument(Document doc) 
//    {
//    }
    public static abstract class AbstractFormatterFactory 
    {
        public abstract AbstractFormatter getFormatter(JFormattedTextField tf);
    }
    public static abstract class AbstractFormatter implements Serializable 
    {
    
        public void install(JFormattedTextField ftf) 
        {
        }

        public void uninstall() 
        {
        
        }

        public abstract Object stringToValue(String text) throws ParseException;
        public abstract String valueToString(Object value) throws ParseException;
        protected JFormattedTextField getFormattedTextField() 
        {
            return null;
        }
    }
}
