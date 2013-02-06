/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

public class Checkbox extends swingwtx.swing.JCheckBox {
    public Checkbox() { super(); }
    public Checkbox(String text) { super(text); }
    public Checkbox(String text, boolean b) { super(text, b); }
    public Checkbox(String text, CheckboxGroup cbg, boolean b) {
        this(text, b, cbg);
    }
    public Checkbox(String text, boolean b, CheckboxGroup cbg) {
        super(text, b, cbg);
        cbg.add(this);
    }

    public boolean getState() { return super.isSelected(); }
    public void setState(boolean b) { super.setSelected(b); }
}
