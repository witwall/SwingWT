/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

   

*/

package swingwtx.swing;

public interface MutableComboBoxModel extends ComboBoxModel {
    public void addElement( Object obj );
    public void removeElement( Object obj );
    public void insertElementAt( Object obj, int index );
    public void removeElementAt( int index );
}


