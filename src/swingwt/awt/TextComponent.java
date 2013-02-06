/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwt.awt;

import swingwt.awt.event.EventListener;
import swingwt.awt.event.TextListener;

import swingwtx.swing.text.JTextComponent;

/**
 * @author Dan
 *
 */
public class TextComponent extends AWTSwingWrapper
{   
    public TextComponent() { 
        swingPeer = new JTextComponent();
    }
    
    private final JTextComponent getSwingPeer() { return (JTextComponent) swingPeer; }
    
	public int getCaretPosition() { return getSwingPeer().getCaretPosition(); }
	public String getText() { return getSwingPeer().getText(); }
	public boolean isEditable() { return getSwingPeer().isEditable(); }
	public void select(int selectionStart, int selectionEnd) { getSwingPeer().select(selectionStart, selectionEnd); }
	public void selectAll() { getSwingPeer().selectAll(); }
	public void setCaretPosition(int position) { getSwingPeer().setCaretPosition(position); }
	public void setEditable(boolean b) { getSwingPeer().setEditable(b); }
	public void setSelectionEnd(int selectionEnd) { getSwingPeer().setSelectionEnd(selectionEnd); }
	public void setSelectionStart(int selectionStart) { getSwingPeer().setSelectionStart(selectionStart); }
	public void setText(String t) { getSwingPeer().setText(t); }
    
    public void addTextListener(TextListener l) {
        // TODO: Implement
    }
    
	public EventListener[] getListeners(Class listenerType) {
	    // TODO: Implement
	    return null;
    }
    
	public TextListener[] getTextListeners() {
	    // TODO: Implement
	    return null;
    }
    
	public void removeTextListener(TextListener l) {
	    // TODO: Implement
    }
	
    // TODO: Implement
	/*
	public String getSelectedText() {
	    return getSwingPeer().getText().substring(getSelectionStart(), getSelectionEnd());
    }
	public int getSelectionEnd() {
	    return getSwingPeer().getSelectionEnd();
    }
	public int getSelectionStart() {
	    return getSwingPeer().getSelectionStart();
    }
	protected void processEvent(AWTEvent e) {
    }
    
	protected void processTextEvent(TextEvent e) {
    }
	public void enableInputMethods(boolean enable) {
    }
    */
}
