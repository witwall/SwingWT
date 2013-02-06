/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */


package swingwt.awt;

import swingwt.awt.event.*;


import java.io.Serializable;

/**
 * A SwingWT variant of a AWTKeyStroke
 * 
 * @author Niklas Gustafsson
 *
 */
public class AWTKeyStroke implements Serializable {

	private int keyCode = 0;
    private int modifiers = 0;
    private boolean onKeyRelease = false;
    private char keyChar = 0;

    protected AWTKeyStroke() 
    {
    }
    protected AWTKeyStroke(char keyChar, int keyCode, int modifiers,
			   boolean onKeyRelease) 
    {
    	this.keyChar = keyChar;
    	this.keyCode = keyCode;
    	this.modifiers = modifiers;
    	this.onKeyRelease = onKeyRelease;
    }
    public static AWTKeyStroke getAWTKeyStroke(char keyChar) 
    {
    	return getAWTKeyStroke(keyChar,0,0,false);
    }
    public static AWTKeyStroke getAWTKeyStroke(Character keyChar, int modifiers) 
    {
    	return getAWTKeyStroke(keyChar.charValue(),0,modifiers,false);
    }
    public static AWTKeyStroke getAWTKeyStroke(int keyCode, int modifiers, boolean onKeyRelease) 
    {
    	return getAWTKeyStroke(KeyEvent.CHAR_UNDEFINED,keyCode,modifiers,onKeyRelease);
    }
    public static AWTKeyStroke getAWTKeyStroke(int keyCode, int modifiers) 
    {
    	return getAWTKeyStroke(KeyEvent.CHAR_UNDEFINED,keyCode,modifiers,false);
    }
    public static AWTKeyStroke getAWTKeyStrokeForEvent(KeyEvent anEvent) 
    {
    	boolean onKeyRelease = false;
    	// TODO: Fix the onKeyRelease       Pressed / Released  
    	
    	return getAWTKeyStroke(anEvent.getKeyChar(),anEvent.getKeyCode(),anEvent.getModifiers(),false);

    }
    // TODO: Implement this  don't need this now   
    public static AWTKeyStroke getAWTKeyStroke(String s) 
    {
    	return null;
    }
    private static AWTKeyStroke getAWTKeyStroke(char keyChar,int keyCode, int modifiers, boolean onKeyRelease) 
    {
        AWTKeyStroke stroke = new AWTKeyStroke();
        stroke.keyChar=keyChar;
        stroke.keyCode = keyCode;
        stroke.modifiers=modifiers;
        stroke.onKeyRelease=onKeyRelease;
        return stroke;
    }
    public final char getKeyChar() 
    {
        return keyChar;
    }
    
    /** Getter for property keyCode.
     * @return Value of property keyCode.
     *
     */
    public final int getKeyCode() {
       return keyCode;
    }
    /** Getter for property modifiers.
     * @return Value of property modifiers.
     *
     */
    public final int getModifiers() 
    {
        return modifiers;
    }
    public final boolean isOnKeyRelease() 
    {
       return onKeyRelease;
    }
    //  Setters  protected
    protected void setKeyChar(char keyChar) 
    {
        this.keyChar = keyChar;
    }
    /** Setter for property keyCode.
     * @param keyCode New value of property keyCode.
     *
     */
    protected void setKeyCode(int keyCode) 
    {
        this.keyCode = keyCode;
    }
    /** Setter for property modifiers.
     * @param modifiers New value of property modifiers.
     *
     */
    protected void setModifiers(int modifiers) 
    {
        this.modifiers = modifiers;
    }

    
    protected void setOnKeyRelease(boolean onKeyRelease) 
    {
       this.onKeyRelease=onKeyRelease;
    }
    public final int getKeyEventType() 
    {
    	
    	if (keyCode == KeyEvent.VK_UNDEFINED) 
    	{
    		return KeyEvent.KEY_TYPED;
    	} 
    	else 
    	{
    		if (onKeyRelease)
    		{
    			return KeyEvent.KEY_RELEASED;
    		}
    		else
    		{
    			return KeyEvent.KEY_PRESSED;
    		}
    	}
    }

    public int hashCode() 
    {
    	return this.getClass().hashCode();
    }
    public final boolean equals(Object anObject) 
    {
        return false;
    }
    public String toString() 
    {

    	return super.toString();  // Dummy
    }
    // TODO: needs to be implemented, not sure how.
    static String getModifiersText(int modifiers) 
    {
        StringBuffer buf = new StringBuffer();

        return buf.toString();
    }

}

