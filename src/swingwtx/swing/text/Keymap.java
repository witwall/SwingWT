package swingwtx.swing.text;

import swingwtx.swing.Action;
import swingwtx.swing.KeyStroke;
/**
 * 
 * @author Niklas
 *
 * Just a SwingWT interface for a keyMap ( not implemented yet )

 */
public interface Keymap {

    public String getName();
    public Action getDefaultAction();
    public void setDefaultAction(Action a);
    public Action getAction(KeyStroke key);
    public KeyStroke[] getBoundKeyStrokes();
    public Action[] getBoundActions();
    public KeyStroke[] getKeyStrokesForAction(Action a);
    public boolean isLocallyDefined(KeyStroke key);
    public void addActionForKeyStroke(KeyStroke key, Action a);
    public void removeKeyStrokeBinding(KeyStroke keys);
    public void removeBindings();
    public Keymap getResolveParent();
    public void setResolveParent(Keymap parent);

}
