/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing;

import swingwt.awt.*;
import swingwt.awt.event.*;
import swingwtx.swing.event.*;

import java.util.*;

/**
 * Implementation of Swing's JSpinner class. We actually use
 * a text box and a vertical scrollbar to get the same effect
 * with native widgets.
 *
 * @author  Robin Rawson-Tetley
 */
public class JSpinner extends JPanel implements ChangeListener {
    
    /** Item events */
    protected Vector changeListeners = new Vector();
    /** Model */
    protected SpinnerModel model = null;
    /** Composite Components */
    protected JTextField text = new JTextField();
    protected JScrollBar spin = new JScrollBar(JScrollBar.VERTICAL);
    
    protected int lastValue = 300000;
    protected boolean firstSetValue = true;
    
    public JSpinner() { this(new SpinnerNumberModel()); }
    
    public JSpinner(SpinnerModel model) { 
        
        super();
        
        setPreferredSize(new Dimension(100, 30));
        text.setPreferredSize(new Dimension(100, 30));
        spin.setPreferredSize(new Dimension(30, 30));
        setModel(model);
        setLayout(new BorderLayout()); 
       
        // Watch for losing the focus so we can update the
        // value
        text.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (JSpinner.this.model instanceof SpinnerNumberModel) {
                    try {
                        Integer i = new Integer(text.getText());
                        if (!getValue().equals(i)) {
                            setValue(i);
                            processChangeEvent(new ChangeEvent(JSpinner.this.model));
                        }
                    }
                    catch (NumberFormatException ex) {
                        text.setText(getValue().toString());
                    }
                }
                else
                {
                    setValue(text.getText());
                    processChangeEvent(new ChangeEvent(JSpinner.this.model));
                }
            }
        });

        // Watch for cursor keys
        text.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == e.VK_DOWN) {
                    nextItem();                 // Go the next item with cursor down
                    e.consume();
                }
                else if (e.getKeyCode() == e.VK_UP) {
                    previousItem();             // Go to the previous item with cursor up
                    e.consume();
                }
            }
        });
        
        // Watch for changes to the scroller
        spin.setMinimum(0);
        spin.setMaximum(650000);
        spin.setValue(30000);
        spin.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                if (e.getValue() > lastValue)
                    previousItem();
                else
                    nextItem();
                lastValue = e.getValue();
                processChangeEvent(new ChangeEvent(JSpinner.this.model));
            }
        });
        
        // Add the components
        add(text, BorderLayout.CENTER);
        add(spin, BorderLayout.EAST);
    }
    
    protected final static int CANCELED = 0;
    protected final static int INVISIBLE = 1;
    protected final static int VISIBLE = 2;
    
    public void setModel(SpinnerModel model) {
        this.model = model;
        model.addChangeListener(this);
    }
    
    public SpinnerModel getModel() {
        return model;
    }
    
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
    
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }
    
    public void removeItemListener(ChangeListener l) {
        changeListeners.remove(l);    
    }
    
    public void processChangeEvent(ChangeEvent e) {
        Iterator i = changeListeners.iterator();
        while (i.hasNext()) {
            ChangeListener l = (ChangeListener) i.next();
            l.stateChanged(e);
        }
    }
    
    public void setValue(Object o) {
        model.setValue(o);
    }
    
    public Object getValue() {
        return model.getValue();
    }
    
    
    public void setEnabled(boolean b) {
        text.setEnabled(b);
        spin.setEnabled(b);
    }
    
    public boolean isEnabled() {
        return spin.isEnabled();    
    }
    
    public String getToolTipText() {
        return text.getToolTipText();
    }
    
    public void setToolTipText(String tip) {
        text.setToolTipText(tip);    
    }
    
    protected void previousItem() {  
        model.setValue( model.getPreviousValue() );
    }
    protected void nextItem() { 
        model.setValue( model.getNextValue() );
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
    
    public void stateChanged(ChangeEvent e) {
        this.text.setText( model.getValue().toString());
    }
    
    /** Component displays oddly since we used a vertical scrollbar. This
     *  should lock it to no more than 30 pixels in height when LayoutManagers
     *  try to update it
     */
    public void setBounds(int x, int y, int width, int height) {
        if (height > 30) height = 30;
        super.setBounds(x, y, width, height);
    }
    
    /** Overriden to calculate non-realised
     *  preferred size.
     */
    protected swingwt.awt.Dimension calculatePreferredSize() {
        // Default 100x30
        swingwt.awt.Dimension size = new swingwt.awt.Dimension(100, 30);
        setSize(size);
        return size;
    }
    
    public void setSwingWTParent(Container parent) throws Exception {
        super.setSwingWTParent(parent);
        stateChanged(new ChangeEvent(model));
    }
    
}
