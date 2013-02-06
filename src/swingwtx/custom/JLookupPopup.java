/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/
package swingwtx.custom;

import swingwt.awt.*;
import swingwt.awt.event.*;
import swingwtx.swing.*;
import swingwtx.swing.event.*;

import java.util.*;

/**
 * Very similar to JComboBox, this component is an uneditable text
 * field with a button instead to popup a list of selectable items.
 * 
 * A field at the top of the popup allows text searching.
 * In addition, hotkeys in the text field allow searching like
 * a combo box.
 *
 * I have designed this as a fast alternative for JComboBox as I 
 * discovered JComboBox performs very poorly under Linux/GTK2 with
 * 200+ objects
 *
 * @author  Robin Rawson-Tetley
 */
public class JLookupPopup extends JPanel {
    
    /** The list of selectable objects */
    protected Vector items = new Vector();
    /** Popup menu events */
    protected Vector popupListeners = new Vector();
    /** Item events */
    protected Vector itemListeners = new Vector();
    
    protected JTextField text = new JTextField();
    protected JButton button = new JButton("...");
    protected int selectedIndex = -1;
    
    /** Creates a new JLookupPopup */
    public JLookupPopup() { 
        super(); 
        setLayout(new BorderLayout()); 
        
        // Initialise text
        text.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 16777218) {
                    nextItem();                 // Go the next item with cursor down
                    e.consume();
                }
                else if (e.getKeyCode() == 16777217) {
                    previousItem();             // Go to the previous item with cursor up
                    e.consume();
                }
                else if (e.getKeyChar() == ' ') {
                    buttonPressed();            // If space is pressed, pretend the button was hit
                    e.consume();
                }
                else {
                    alphaSearchItem(e.getKeyChar());// Otherwise, try and find a match for the key char pressed
                    e.consume();
                }
            }
        });
        
        // Initialise button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonPressed();
            }
        });
        
        // Add the components
        add(text, BorderLayout.CENTER);
        add(button, BorderLayout.EAST);
    }
    
    protected final static int CANCELED = 0;
    protected final static int INVISIBLE = 1;
    protected final static int VISIBLE = 2;
    
    public void addFocusListener(FocusListener l) {
        text.addFocusListener(l);    
    }
    public void removeFocusListener(FocusListener l) {
        text.removeFocusListener(l);    
    }
    public void addKeyListener(KeyListener l) {
        text.addKeyListener(l);    
    }
    public void removeKeyListener(KeyListener l) {
        text.removeKeyListener(l);    
    }
    public void addPopupMenuListener(PopupMenuListener l) {
        popupListeners.add(l);
    }
    
    public void removePopupMenuListener(PopupMenuListener l) {
        popupListeners.remove(l);
    }
    
    public void addItemListener(ItemListener l) {
        itemListeners.add(l);
    }
    
    public void removeItemListener(ItemListener l) {
        itemListeners.remove(l);    
    }
    
    public void processPopupMenuEvent(int id) {
        Iterator i = popupListeners.iterator();
        PopupMenuEvent e = new PopupMenuEvent(this);
        while (i.hasNext()) {
            PopupMenuListener l = (PopupMenuListener) i.next();
            switch(id) {
                case CANCELED: l.popupMenuCanceled(e); break;
                case INVISIBLE: l.popupMenuWillBecomeInvisible(e); break;
                case VISIBLE: l.popupMenuWillBecomeVisible(e); break;
            }
        }
    }
    
    public void processItemEvent(ItemEvent e) {
        Iterator i = itemListeners.iterator();
        while (i.hasNext()) {
            ItemListener l = (ItemListener) i.next();
            l.itemStateChanged(e);
        }
    }
    
    /** Add a selection to the list */
    public void addItem(Object item) {
        items.add(item);
        // If this is the first item, auto select it
        if (items.size() == 1)
            setSelectedIndex(0);
    }
    
    /** Remove a selection from the list */
    public void removeItem(Object item) {
        items.remove(item);    
    }
    
    /** Returns the selected item by it's index */
    public int getSelectedIndex() {
        return selectedIndex;
    }
    /** Sets the selected item in the list */
    public void setSelectedIndex(int i) {
        selectedIndex = i;
        text.setText( items.get(i).toString() );
    }
    /**
     * Sets the selected item
     */
    public void setSelectedItem(Object o) {
        text.setText(o.toString());
        selectedIndex = items.indexOf(o);
    }
    /**
     * Returns the selected item
     */
    public Object getSelectedItem() {
        return items.get(selectedIndex);
    }
    /** Returns the number of items in the list */
    public int getItemCount() {
       return items.size();
    }
    /** Returns the item at a given position */
    public Object getItemAt(int index) {
        return items.get(index);    
    }
    /** Clears the list */
    public void removeAllItems() {
        items.removeAllElements();
        text.setText("");
        selectedIndex = -1;
    }
    /**
     * Sets whether the lookup is enabled
     */
    public void setEnabled(boolean b) {
        text.setEnabled(b);
        button.setEnabled(b);
    }
    /**
     * Returns true if the lookup is enabled
     */
    public boolean isEnabled() {
        return button.isEnabled();    
    }
    /** Returns the tooltip text for the component */
    public String getToolTipText() {
        return text.getToolTipText();
    }
    /** Sets the tooltiptext for the component */
    public void setToolTipText(String tip) {
        text.setToolTipText(tip);    
    }
    
    /** We don't care for this component - it just keeps compatibility with JComboBox */
    public void setMaximumRowCount(int i) {}
    
    /**
     * Gets called when the user presses the button. The 
     * dialog should be popped up to prompt the user for
     * a new selection.
     */
    protected void buttonPressed() {
        new LookupPopup(this);
    }
    
    protected void previousItem() {  
        // Do nothing if no items
        if (items.size() == 0) return;
        // If nothing selected, go to the first
        if (getSelectedIndex() == -1)
            setSelectedIndex(0);
        if (getSelectedIndex() > 0)
            setSelectedIndex(getSelectedIndex() - 1);
    }
    protected void nextItem() { 
        // Do nothing if no items
        if (items.size() == 0) return;
        // If nothing selected, go to the first
        if (getSelectedIndex() == -1)
            setSelectedIndex(0);
        if (getSelectedIndex() < (items.size() - 1))
            setSelectedIndex(getSelectedIndex() + 1);
    }
    
    /**
     * Performs an alpha search for the next item based 
     * on the key pressed
     */
    protected void alphaSearchItem(char cs) { 
        
        // See what's already selected - this way we can cycle
        // up the list like Swing does.
        int currentlySelected = getSelectedIndex();

        // The current character we are dealing with
        String curChar = "";
        if (currentlySelected != -1)
            curChar = getSelectedItem().toString().substring(0, 1).toLowerCase();

        boolean foundMatch = false;

        for (int i = 0; i < items.size(); i++) {
            String s = items.get(i).toString();
            // This item start with the same letter?
            if (s.substring(0, 1).toLowerCase().equals(new String(new char[] { cs }).toLowerCase()))  {

                // Was the last keystroke on this letter? If so, the index must be higher
                if (curChar.equals(new String(new char[] { cs }).toLowerCase()))
                    i = currentlySelected + 1;

                // If we've run out of letters, don't do anything
                if (i >= items.size()) 
                    break;

                // Now that we've moved the match up, make sure the letters still
                // match - if they don't, then we've gone too far - don't do
                // anything
                if (!items.get(i).toString().substring(0, 1).toLowerCase().equals(new String(new char[] { cs }).toLowerCase()))
                    break;
                
                // We have a match and it's ok - set it and finish
                setSelectedIndex(i);
                break;
                
            }
        }
    }

    public void requestFocus() {
        text.requestFocus();
    }

    public void grabFocus() {
	text.grabFocus();
    }

    public JTextField getJTextField() {
	return text;
    }
    
    /**
     * The popup form. This consists of
     * a dialog with a search box, a list of
     * items, Ok/Cancel buttons and a progress
     * bar to show it's fill progress (it opens
     * immediately and shows how far it has gotten
     * filling the list).
     *
     * Hitting ok updates the currently selected
     * item in the many control
     *
     * @author Robin Rawson-Tetley
     */
    private class LookupPopup extends JFrame {
        
        private JLookupPopup popup = null;
        private JList lst = null;
        
        public LookupPopup(JLookupPopup selector) {
            
            popup = selector;
            
            // Create the list
            
            SwingUtilities.invokeSync( new Runnable() {
                public void run() {
                    
                    lst = new JList(popup.items);
                    lst.addMouseListener( new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            if (e.getClickCount() == 2)
                                btnOkPressed();
                        }
                    });

                    // Buttons
                    JButton btnOk = new JButton("Ok");
                    btnOk.setMnemonic('o');
                    btnOk.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            btnOkPressed();
                        }
                    });

                    JButton btnCancel = new JButton("Cancel");
                    btnCancel.setMnemonic('c');
                    btnCancel.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            btnCancelPressed();
                        }
                    });

                    JPanel pnlButtons = new JPanel();
                    pnlButtons.setLayout(new FlowLayout());
                    pnlButtons.add(btnOk);
                    pnlButtons.add(btnCancel);

                    getContentPane().setLayout(new BorderLayout());
                    getContentPane().add(lst, BorderLayout.CENTER);
                    getContentPane().add(pnlButtons, BorderLayout.SOUTH);
                }
            });
            
            // Calculate the mouse position on screen.
            int mouseX = SwingWTUtils.getDisplay().getCursorLocation().x;
            int mouseY = SwingWTUtils.getDisplay().getCursorLocation().y;
            Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
            
            // If the mouse position is greater than 400 pixels from the screen edge, then
            // make it 400 pixels from the edge
            if (mouseX > (screen.width - 400))
                mouseX = screen.width - 400;
            if (mouseY > (screen.height - 400))
                mouseY = screen.height- 400;
            
            setSize(400, 400);
            setLocation(mouseX, mouseY);
            setTitle("Select");
            
            show();
            
            SwingUtilities.invokeIn(new Runnable() {
                public void run() {
                    
                    // Set focus to the list
                    lst.grabFocus();
                    
                    // Highlight the selection if there is one
                    if (popup.getSelectedIndex() != -1)
                        lst.setSelectedIndex(popup.getSelectedIndex());
                    
                    // If the selection is offscreen - jump to it
                    ((org.eclipse.swt.widgets.Table) lst.getSWTPeer()).showSelection();
                    
                    // Fire events to say the popup was shown
                    popup.processPopupMenuEvent(JLookupPopup.VISIBLE);
                }
            }, 100);
            
        }
        
        protected void btnOkPressed() {
            popup.processItemEvent(new ItemEvent(popup, ItemEvent.DESELECTED, popup.getSelectedItem(), 0));
            popup.setSelectedIndex(lst.getSelectedIndex());
            dispose();
            popup.processItemEvent(new ItemEvent(popup, ItemEvent.SELECTED, popup.getSelectedItem(), 0));
            popup.processPopupMenuEvent(JLookupPopup.INVISIBLE);
        }
        protected void btnCancelPressed() {
            popup.processPopupMenuEvent(JLookupPopup.CANCELED);
            dispose();
            popup.processPopupMenuEvent(JLookupPopup.INVISIBLE);
        }
        
    }
    
}
