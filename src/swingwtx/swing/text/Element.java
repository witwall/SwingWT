/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

public interface Element {
    public Document getDocument();
    public Element getParentElement();
    public String getName();
    public AttributeSet getAttributes();
    public int getStartOffset();
    public int getEndOffset();
    public int getElementIndex(int offset);
    public int getElementCount();
    public Element getElement(int index);
    public boolean isLeaf();

}

