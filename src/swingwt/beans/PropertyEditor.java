package swingwt.beans;

import java.beans.PropertyChangeListener;

public interface PropertyEditor 
{
    void addPropertyChangeListener(PropertyChangeListener listener);
    String getAsText();
    swingwt.awt.Component getCustomEditor();
    String getJavaInitializationString();
    String[] getTags();
    Object getValue();
    boolean isPaintable();
    void paintValue(swingwt.awt.Graphics gfx, swingwt.awt.Rectangle box);
    void removePropertyChangeListener(PropertyChangeListener listener);
    void setAsText(String text) throws java.lang.IllegalArgumentException;
    void setValue(Object value);
    boolean supportsCustomEditor();

    

}
