package swingwtx.swing;

import swingwt.awt.Component;
import swingwt.awt.event.ActionListener;

public interface ComboBoxEditor {
	void 	addActionListener(ActionListener l);
	Component 	getEditorComponent();
	Object 	getItem();
	void 	removeActionListener(ActionListener l);
	void 	selectAll();
	void 	setItem(Object anObject); 
}
