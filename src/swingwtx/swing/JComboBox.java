/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import swingwtx.swing.event.*;
import swingwt.awt.Component;
import swingwt.awt.event.*;

import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
//import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.*;

import java.util.*;

public class JComboBox extends swingwtx.swing.JComponent
	implements ListDataListener {

    protected Combo ppeer = null;
    //protected CCombo ppeer = null;
    
    protected String pText = "";
    protected Vector litems = null;
    protected int pSelectedIndex = -1;
    protected int pMaxRows = 8;
    protected Vector popupListeners = new Vector();
    protected Vector itemListeners = new Vector();
    protected Vector changeListeners = new Vector();
    protected ComboBoxModel model = null;

    /** Used for thread safe property accessor calls */
    private Object retValue = null;

    /** Used to determine if the combo can be entered into */
    protected boolean isEditable = false;

    private ComboBoxEditor editor = null;
    
    public JComboBox() { litems = new Vector(); model = new DefaultComboBoxModel(litems); }

    public JComboBox(Object[] items) {
        litems = new Vector();
        for (int i = 0; i < items.length; i++) {
            litems.add(items[i]);
        }
        if (!isEditable && litems.size() > 0)
            pSelectedIndex = 0;
        model = new DefaultComboBoxModel(litems);
    }

    public JComboBox(Vector items) {
        litems = items;
        if (!isEditable && litems != null && litems.size() > 0)
            pSelectedIndex = 0;
        model = new DefaultComboBoxModel(items);
    }

    public JComboBox(ComboBoxModel model) {
        this.model = model;
        copyModel();
        if (!isEditable && litems != null && litems.size() > 0)
            pSelectedIndex = 0;
    }

    protected final static int CANCELED = 0;
    protected final static int INVISIBLE = 1;
    protected final static int VISIBLE = 2;
    
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);    
    }
    
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);    
    }

    public ChangeListener[] getChangeListeners() {
        return (ChangeListener[]) changeListeners.toArray(new ChangeListener[0]);
    }
    public void addPopupMenuListener(PopupMenuListener l) {
        popupListeners.add(l);
    }

    public void removePopupMenuListener(PopupMenuListener l) {
        popupListeners.remove(l);
    }
    
    public PopupMenuListener[] getPopupMenuListener() {
        return (PopupMenuListener[]) popupListeners.toArray(new PopupMenuListener[0]);
    }

    public void addItemListener(ItemListener l) {
        itemListeners.add(l);
    }

    public void removeItemListener(ItemListener l) {
        itemListeners.remove(l);
    }
    
    public ItemListener[] getItemListeners() {
        return (ItemListener[]) itemListeners.toArray(new ItemListener[0]);
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
	    
	// The model needs to be informed of the new selection
	if (model != null)
	    model.setSelectedItem(e.getItem());
			
        for (int i = 0; i < itemListeners.size(); i++) {
            ItemListener l = (ItemListener) itemListeners.get(i);
            l.itemStateChanged(e);
        }
    }

    public void processChangeEvent(ChangeEvent e) {
        for (int i = 0; i < changeListeners.size(); i++) {
            ChangeListener l = (ChangeListener) changeListeners.get(i);
            l.stateChanged(e);
        }
    }
    

    /** Handles copying of Combo box models so they can be
     *  handled by SWT
     */
    protected void copyModel() {
        if (model == null) return;
        litems = new Vector(model.getSize());
        for (int i = 0; i < model.getSize(); i++) {
            litems.add(model.getElementAt(i));
        }
    }

    public Object getSelectedItem() {
        retValue = null;
        SwingUtilities.invokeSync(new Runnable() { public void run() {
            if (isEditable) {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    retValue = pText;
                else
                    retValue = ppeer.getText();
            }
            else {
                if ((!SwingWTUtils.isSWTControlAvailable(ppeer)) && pSelectedIndex > -1)
                    retValue = litems.get(pSelectedIndex);
                else if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    if (ppeer.getSelectionIndex() != -1)
                        retValue = litems.get(ppeer.getSelectionIndex());
            }
        }});
        return retValue;
    }
    
    public Object[] getSelectedObjects() {
        return new Object[] { getSelectedItem() };
    }

    public void setSelectedItem(final Object text) {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (isEditable) {
                    if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                        pText = text.toString();
                    else
                        ppeer.setText(text.toString());
                }
                else {
                    // If we are dealing with a String, use a string comparison
		    if (litems != null) {
                        if (text instanceof String) {
                            Object[] itm = litems.toArray();
                            for (int i = 0; i < itm.length; i++)
                                 if (itm[i].toString().equals(text.toString())) {
                                    setSelectedIndex(i);
                                    break;
                                }
                        }
                        else {
                            // Object comparison
                            int index = litems.indexOf(text);
                            if (index != -1) setSelectedIndex(index);
                        }
		    }
                }
            }
        });
    }

    public ComboBoxModel getModel() { return model; }
    public void setModel(ComboBoxModel model) {
    	if (this.model != null) {
    	    this.model.removeListDataListener(this);
    	}
    	model.addListDataListener(this);
    	
        this.model = model;
        copyModel();
        if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
	    String[] items = new String[litems.size()];
	    for (int i=0; i < litems.size(); i++) {
		Object o = litems.get(i);
		items[i] = o == null ? "" : o.toString();
		// I don't like adding them one at a time, but
		// we have no choice with 3.1M5
		ppeer.add(items[i]);
	    }
	    // We can't use this any more - it crashes on GTK2???
	    //ppeer.setItems(items);
        }
    }

    public void addItem(final Object item) {
        if (litems == null)
            litems = new Vector();
        litems.add(item);

        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    ppeer.add(item.toString());
                    fixMacOffsetProblem();
                }
            }
        });
    }
    
    /**
     * MacOSX has problems with combo boxes appearing
     * offset and weird
     */
    private void fixMacOffsetProblem() {
	/*
	 * This does not work. How the hell do we fix this?
	 *
        if (SwingWTUtils.isMacOSX()) {
            // Remember where we are now
            Control c = SwingWTUtils.getDisplay().getFocusControl();
            // Focus the peer
            ppeer.forceFocus();
            // Refocus
            if (c != null) c.forceFocus();
  	    // Force a redraw
	    ppeer.update();
        }
	*/
    }

    public void insertItemAt(final Object item, final int index) {
        if (litems == null)
            litems = new Vector();
        litems.add(index, item);
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                    ppeer.add(item.toString(), index);
                    fixMacOffsetProblem();
                }
            }
        });
    }

    public int getSelectedIndex() {
        retValue = null;
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    retValue = new Integer(pSelectedIndex);
                else
                    retValue = new Integer(ppeer.getSelectionIndex());
            }
        });
        return ((Integer) retValue).intValue();
    }

    public Object getItemAt(int index) {
        if (index == -1)
            return null;
        return litems.get(index);
    }
    
    public swingwt.awt.Component getComponent(int n) {
        return this;
    }

    public int getItemCount() {
        return litems.size();
    }

    public void setSelectedIndex(final int index) {
        final JComboBox me = this;
        if (model!=null) {
            if (index!=-1)
                model.setSelectedItem(model.getElementAt(index));
            else
                model.setSelectedItem(null);
        }
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (!SwingWTUtils.isSWTControlAvailable(ppeer))
                    pSelectedIndex = index;
                else {
                    ppeer.select(index);
                    processActionEvent(0);
                    ItemEvent ie = new ItemEvent(me, 0, getSelectedItem(), ItemEvent.SELECTED);
                    processItemEvent(ie);
                }
            }
        });
    }

    public void removeAllItems() {
        if (litems != null)
            litems.removeAllElements();
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.removeAll();
            }
        });
    }

    public void removeItem(final Object item) {
        litems.remove(item);
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.remove(item.toString());
            }
        });
    }

    public void removeItemAt(final int index) {
        litems.remove(index);
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if (SwingWTUtils.isSWTControlAvailable(ppeer))
                    ppeer.remove(index);
            }
        });
    }

    public void setEditable(boolean b) {
        isEditable = b;
    }

    public boolean getEditable() {
        return isEditable;
    }

    public int getMaximumRowCount() {
        return pMaxRows;
    }
    public void setMaximumRowCount(int max) {
        pMaxRows = max;
    }

    /**
     * Once a parent component receives an "add" call for a child, this being
     * the child, this should be called to tell us to instantiate the peer
     * and load in any cached properties.
     */
    public void setSwingWTParent(swingwt.awt.Container parent) throws Exception {
        descendantHasPeer = true;
        ppeer = new Combo(parent.getComposite(), SWT.BORDER | SWT.DROP_DOWN );
        //ppeer = new CCombo(parent.getComposite(), SWT.BORDER | SWT.DROP_DOWN );
        
        if (litems != null) {
	    String[] items = new String[litems.size()];
	    for (int i=0; i < litems.size(); i++) {
		Object o = litems.get(i);
		items[i] = o == null ? "" : o.toString();
		// I don't like adding them one at a time, but
		// we have no choice with 3.1M5
		ppeer.add(items[i]);
	    }
	    // We can't use this any more - it crashes on GTK2???
	    //ppeer.setItems(items);
        }
        
	// Visible rows
        ppeer.setVisibleItemCount(pMaxRows);


        if (pSelectedIndex != -1)
            ppeer.select(pSelectedIndex);
	// Real Swing combos default to the first item if none are selected
        else if (litems != null && litems.size() > 0) {
            pSelectedIndex = 0;
            ppeer.select(0);
        }
        
        // If the combo is editable, default the text supplied if we have some
        if (isEditable)
            if (!pText.equals(""))
                ppeer.setText(pText);

        peer = ppeer;
        this.parent = parent;

        registerComboEvents();
        fixMacOffsetProblem();

    }

    protected void registerComboEvents() {

        final JComboBox me = this;

        // Popup menu event mapped listener
        ppeer.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                // Only fire when the text is changed
                if (ppeer.getSelectionIndex() != -1) {
                    processPopupMenuEvent(INVISIBLE);
                    processPopupMenuEvent(VISIBLE);
                    processPopupMenuEvent(CANCELED);
                }
            }
        });

        // Selection changed
        ppeer.addSelectionListener(new SelectionAdapter() {
            public void widgetSelected(SelectionEvent e) {
                ItemEvent ie = new ItemEvent(me, 0, getSelectedItem(), ItemEvent.SELECTED);
                processItemEvent(ie);
                ChangeEvent ce = new ChangeEvent(me);
                processChangeEvent(ce);
                processPopupMenuEvent(INVISIBLE);
                processActionEvent(0); // Fire an action event when selection changes
            }
        });

        // Changing selection on key strokes for non-editable combos
        handleComboKeyStrokes();

    }

    /**
     * This routine will add the key handlers necessary for searching
     * through combos by the key pressed (as Windows does, and as Swing does).
     *
     * This code really isn't too pretty, as SWT deactivates the key events if
     * SWT.READ_ONLY is specified, so I have to handle that too, also you
     * can't consume KeyEvents under a Combo (must be a bug), so I use a timer
     * over the event pump to reset values. As I say, it's dirty but it works.
     *
     * @author Robin Rawson-Tetley
     */
    protected void handleComboKeyStrokes() {

        // If the combo isn't editable, then set up hot keys to search the list
        // when the user presses a key. I can't BELIEVE I have to frigging code this
        // and SWT doesn't do this anyway!

        if (isEditable) return;

        ppeer.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
            public void keyPressed(org.eclipse.swt.events.KeyEvent e) {

                // Cancel the keystroke -- maybe one day it will work...
                e.doit = false;

                // If ALT or CTRL is pressed, ignore this code.
                if ( ((e.stateMask & SWT.ALT) > 0) ||
                     ((e.stateMask & SWT.CTRL) > 0) ) return;

                // See what's already selected - this way we can cycle
                // up the list like Swing does.
                int currentlySelected = getSelectedIndex();

                // The current character we are dealing with
                String curChar = "";
		String selected = "";
		
                if (currentlySelected != -1)
		    selected = getSelectedItem().toString();
		
                if (currentlySelected != -1 && selected.length() > 0)
                    curChar = selected.substring(0, 1).toLowerCase();

                boolean foundMatch = false;

                if (litems!=null) {
                    for (int i = 0; i < litems.size(); i++) {
                        String s = litems.get(i).toString();
                        // This item start with the same letter?
                        if ( s.length() > 0 &&
                             s.substring(0, 1).toLowerCase().equals(new String(new char[] { e.character }).toLowerCase()) )
                        {

                            // Was the last keystroke on this letter? If so, the index must be higher
                            if (curChar.equals(new String(new char[] { e.character }).toLowerCase()))
                                i = currentlySelected + 1;

                            // If we've run out of letters, don't do anything
                            if (i >= litems.size())
                                break;

                            // Now that we've moved the match up, make sure the letters still
                            // match - if they don't, then we've gone too far - don't do
                            // anything
                            if (!litems.get(i).toString().substring(0, 1).toLowerCase().equals(new String(new char[] { e.character }).toLowerCase()))
                                break;

                            foundMatch = true;

                            // Because we can't cancel the keystroke, we let it
                            // go and then change the value again afterwards - thanks SWT!
                            final int matchedIndex = i;
                            SwingUtilities.invokeIn(new Runnable() {
                                    public void run() {
                                        setSelectedItem(litems.get(matchedIndex));
                                    }
                                }, 1);
                            break;

                        }
                    }
                }

                // We didn't find one, reassert the current value to overwrite
                // the keystroke.
                if (!foundMatch) {
                    final Object selItem = getSelectedItem();
                    SwingUtilities.invokeIn(new Runnable() {
                        public void run() {
                            setSelectedItem(selItem);
                        }
                    }, 1);
                }

            }
        });

    }

    /*
     * @see swingwtx.swing.event.ListDataListener#intervalAdded(swingwtx.swing.event.ListDataEvent)
     */
    public void intervalAdded(ListDataEvent e) {
        for (int i=e.getIndex0(); i<=e.getIndex1(); i++) {
            if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                addItem(model.getElementAt(i));
                //ppeer.add(model.getElementAt(i).toString());
            }
        }
    }

    /*
     * @see swingwtx.swing.event.ListDataListener#intervalRemoved(swingwtx.swing.event.ListDataEvent)
     */
    public void intervalRemoved(ListDataEvent e) {
        for (int i=e.getIndex0(); i<=e.getIndex1(); i++) {
            if (SwingWTUtils.isSWTControlAvailable(ppeer)) {
                removeItemAt(i);
                //ppeer.remove(model.getElementAt(i).toString());
            }
        }
    }

    /*
     * @see swingwtx.swing.event.ListDataListener#contentsChanged(swingwtx.swing.event.ListDataEvent)
     */
    public void contentsChanged(ListDataEvent e) {
        // TOOD: We need to update existing here
    }
    
    public ComboBoxEditor getEditor() {
    	return new ComboBoxEditor(){

			public void addActionListener(ActionListener l) {
				// TODO Auto-generated method stub
				
			}

			public Component getEditorComponent() {
				// TODO Auto-generated method stub
				return null;
			}

			public Object getItem() {
				// TODO Auto-generated method stub
				return null;
			}

			public void removeActionListener(ActionListener l) {
				// TODO Auto-generated method stub
				
			}

			public void selectAll() {
				// TODO Auto-generated method stub
				
			}

			public void setItem(Object anObject) {
				// TODO Auto-generated method stub
				
			}
    		
    	};
    }
    
    protected void selectedItemChanged() {
        // TODO
    }

    public void setPrototypeDisplayValue(String proto) {
        // TODO
    }

    public static interface KeySelectionManager {
         public int selectionForKey(char aKey, ComboBoxModel aModel);
    }

    public void setKeySelectionManager(KeySelectionManager keySelectionManager) {
        if (keySelectionManager == null) {
            // TODO: implement setKeySelectionManager(null)
        } 
	else {
            final KeySelectionManager manager = keySelectionManager;
	    final ComboBoxModel model = getModel();
            ppeer.addKeyListener(new org.eclipse.swt.events.KeyAdapter() {
                 public void keyPressed(org.eclipse.swt.events.KeyEvent e) {
                     // If ALT or CTRL is pressed, ignore this code.
                     if ( ((e.stateMask & SWT.ALT) > 0) ||
                         ((e.stateMask & SWT.CTRL) > 0) ) return;

                     final int selection = manager.selectionForKey(e.character,model);
                     if (selection >= 0) {
                         SwingUtilities.invokeIn(new Runnable() {
                              public void run() {
                                   setSelectedItem(litems.get(selection));
                              }
                          }, 1);
                     }
                 }
             });
	 }
     }

     public void getKeySelectionManager() {
         // TODO: implement getKeySelectionManager
     }
     
     public void setRenderer(ListCellRenderer aRenderer) {
    	 // TODO: implement
     }
     public ActionListener[] getActionListeners()
  	{
  		int s = actionListeners.size();
  		ActionListener[] f = new ActionListener[s];
  		for (int i = 0;i<actionListeners.size();i++)
  		{
  			f[i]=(ActionListener)actionListeners.elementAt(i);
  		}
  		return f;
  	}
     public void setEditor(ComboBoxEditor editor)
     {
    	 this.editor = editor;
     }
}
