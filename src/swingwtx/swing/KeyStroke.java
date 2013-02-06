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


/**
 * 
 * A SwingWT implementation of a KeyStroke
 * 
 * TODO: Not all methods is implemented they are just there...
 *
 */
public class KeyStroke extends AWTKeyStroke
{
    /** Only way to get one of these is through static methods */
	
	
    private KeyStroke() 
    { 
    	super();
    }
    public static KeyStroke getKeyStroke(char keyChar)
    {
    	return getKeyStroke(keyChar,0,0,false);
    }
    public static KeyStroke getKeyStroke(Character keyChar, int modifiers)
    {
    	return getKeyStroke(keyChar.charValue(),0,modifiers,false);
    }
    public static KeyStroke getKeyStroke(char keyChar, boolean onKeyRelease)
    {
    	return getKeyStroke(keyChar,0,0,onKeyRelease);
    }
    public static KeyStroke getKeyStroke(int keyCode, int modifiers) 
    {
    	return getKeyStroke(KeyEvent.CHAR_UNDEFINED,keyCode,modifiers,false);
    }
    
    public static KeyStroke getKeyStroke(int keyCode, int modifiers, boolean onKeyRelease)
    {
    	return getKeyStroke(KeyEvent.CHAR_UNDEFINED,keyCode,modifiers,onKeyRelease);
    }
    /** FIXME: NEEDS IMPLEMENTING */
    public static KeyStroke getKeyStroke(String namedConstant) 
    {
        // Translate named constant to key code and modifier
        // Eg: "control a" for CTRL modifier _+ a
        //     "shift alt b" for SHIFT|ALT + b
        return new KeyStroke();
    }
    public static KeyStroke getKeyStrokeForEvent(KeyEvent anEvent)
    {
    	boolean onKeyRelease = false;
    	// TODO: Fix the onKeyRelease       Pressed / Released  Niklas 060905
    	
    	return getKeyStroke(anEvent.getKeyChar(),anEvent.getKeyCode(),anEvent.getModifiers(),false);
    }
    public static KeyStroke getKeyStroke(int keyCode) 
    {
        return getKeyStroke(keyCode, 0);
    }
    private static KeyStroke getKeyStroke(char keyChar,int keyCode, int modifiers, boolean onKeyRelease) 
    {
    	KeyStroke stroke = new KeyStroke();
        stroke.setKeyCode(keyCode);
        stroke.setKeyChar(keyChar);
        stroke.setModifiers(modifiers);
        stroke.setOnKeyRelease(onKeyRelease);
        return stroke;
    }
    
    
    
    
    
    
        
    
    
}
