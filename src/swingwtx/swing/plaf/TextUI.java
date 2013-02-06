package swingwtx.swing.plaf;

import swingwt.awt.*;
import swingwtx.swing.text.*;
public abstract class TextUI extends ComponentUI
{
	public abstract void damageRange(JTextComponent t, int p0, int p1);
    public abstract void damageRange(JTextComponent t, int p0, int p1,Position.Bias firstBias,Position.Bias secondBias);
    public abstract EditorKit getEditorKit(JTextComponent t);
    public abstract int getNextVisualPositionFrom(JTextComponent t,int pos, Position.Bias b,int direction, Position.Bias[] biasRet)throws BadLocationException;
    public abstract View getRootView(JTextComponent t);
    public String getToolTipText(JTextComponent t, Point pt) {return null;}
    public abstract Rectangle modelToView(JTextComponent t, int pos) throws BadLocationException;
    public abstract Rectangle modelToView(JTextComponent t, int pos, Position.Bias bias) throws BadLocationException;
    public abstract int viewToModel(JTextComponent t, Point pt);
    public abstract int viewToModel(JTextComponent t, Point pt,Position.Bias[] biasReturn);
    
    
    
    
}
