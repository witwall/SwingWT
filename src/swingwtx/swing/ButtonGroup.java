/*
   SwingWT
   Copyright(c)2003-2008, Tomer Bartletz
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: tomerb@users.sourceforge.net

 */
package swingwtx.swing;

import java.util.Enumeration;
import java.util.Vector;

/**
 * This class is used to create a multiple-exclusion scope for
 * a set of buttons. Creating a set of buttons with the
 * same <code>ButtonGroup</code> object means that
 * turning "on" one of those buttons
 * turns off all other buttons in the group.
 *
 * @author Tomer Barletz, tomerb@users.sourceforge.net
 * @version
 */
public class ButtonGroup {
    
	/**
	 * The buttons that participate in this group.
	 */
    protected Vector buttons = new Vector();
    
    /**
     * The model of the selected button.
     */
    protected ButtonModel selectedModel = null;
    
    /**
     * Creates an empty group.
     */
    public ButtonGroup() {
    }
    
    /**
     * Adds this button to the group.
     * 
     * @param b the button to be added.
     */
    public void add(AbstractButton b) {
    	if (null == b)
    		return;
    	
        buttons.add(b);
        
        if (b.isSelected())
            if (null == selectedModel)
                selectedModel = b.getModel();
            else
                b._setSelected(false);
        
        b.getModel().setGroup(this);
    }
    
    /**
     * Removes this button from the group.
     *
     * @param b
     */
    public void remove(AbstractButton b) {
    	if (null == b)
    		return;
    	
        buttons.remove(b);
        
        if(b.getModel() == selectedModel)
            selectedModel = null;
        
        b.getModel().setGroup(null);
    }
    
    /**
     * Gets an enumeration with all buttons in this group.
     *
     * @return
     */
    public Enumeration getElements() {
        return buttons.elements();
    }
    
    /**
     * Gets the selection model of the selected button.
     *
     * @return
     */
    public ButtonModel getSelection() {
        return selectedModel;
    }
    
    /**
     * If selectThisButton is true, then select this model.
     *
     * @param newModel
     * @param selectThisButton
     */
    public void setSelected(ButtonModel newModel, boolean selectThisButton) {
        if (selectThisButton && null != newModel && selectedModel != newModel) {
        	
            ButtonModel oldSelection = selectedModel;
            selectedModel = newModel;
            
            if (null != oldSelection)
                oldSelection.setSelected(false);
            
            newModel.setSelected(true);
        }
    }
    
    /**
     * Checks whether this is the selected button model,
     * according to this group.
     *
     * @param m
     * @return
     */
    public boolean isSelected(ButtonModel m) {
        return (selectedModel == m);
    }
    
    /**
     * The number of buttons in the group.
     *
     * @return
     */
    public int getButtonCount() {
    	return buttons.size();
    }
}
