/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwt.awt.event;

import swingwt.awt.*;

import org.eclipse.swt.*;

public class KeyEvent extends InputEvent implements java.io.Serializable {
    
    private char keyChar = ' ';
    private int keyCode = 0;
    private int keyLocation;
    private long keyWhen;
    public int eventID = 0;
    
    public static final int KEY_FIRST = 400;
    public static final int KEY_LAST  = 402;
    public static final int KEY_TYPED = KEY_FIRST;
    public static final int KEY_PRESSED = 1 + KEY_FIRST; 
    public static final int KEY_RELEASED = 2 + KEY_FIRST; 
    
    public static final int TYPED = KEY_TYPED;
    public static final int PRESSED = KEY_PRESSED;
    public static final int RELEASED = KEY_RELEASED;
    
    public static final int VK_ENTER = '\n';
    public static final int VK_BACK_SPACE = '\b';
    public static final int VK_TAB  = '\t';
    public static final int VK_CANCEL = 0x03;
    public static final int VK_CLEAR = 0x0C;
    public static final int VK_SHIFT = 0x10;
    public static final int VK_CONTROL = 0x11;
    public static final int VK_ALT  = 0x12;
    public static final int VK_PAUSE = 0x13;
    public static final int VK_CAPS_LOCK = 0x14;
    public static final int VK_ESCAPE = 0x1B;
    public static final int VK_SPACE = 0x20;
    public static final int VK_PAGE_UP = 0x21;
    public static final int VK_PAGE_DOWN = 0x22;
    public static final int VK_END = 0x23;
    public static final int VK_HOME = 0x24;
    public static final int VK_LEFT = 0x25;
    public static final int VK_UP = 0x26;
    public static final int VK_RIGHT = 0x27;
    public static final int VK_DOWN = 0x28;
    public static final int VK_COMMA = 0x2C;
    public static final int VK_MINUS = 0x2D;
    public static final int VK_PERIOD = 0x2E;
    public static final int VK_SLASH = 0x2F;
    public static final int VK_0 = 0x30;
    public static final int VK_1 = 0x31;
    public static final int VK_2 = 0x32;
    public static final int VK_3 = 0x33;
    public static final int VK_4 = 0x34;
    public static final int VK_5 = 0x35;
    public static final int VK_6 = 0x36;
    public static final int VK_7 = 0x37;
    public static final int VK_8 = 0x38;
    public static final int VK_9 = 0x39;
    public static final int VK_SEMICOLON = 0x3B;
    public static final int VK_EQUALS = 0x3D;
    public static final int VK_A = 0x41;
    public static final int VK_B = 0x42;
    public static final int VK_C = 0x43;
    public static final int VK_D = 0x44;
    public static final int VK_E = 0x45;
    public static final int VK_F = 0x46;
    public static final int VK_G = 0x47;
    public static final int VK_H = 0x48;
    public static final int VK_I = 0x49;
    public static final int VK_J = 0x4A;
    public static final int VK_K = 0x4B;
    public static final int VK_L = 0x4C;
    public static final int VK_M = 0x4D;
    public static final int VK_N = 0x4E;
    public static final int VK_O = 0x4F;
    public static final int VK_P = 0x50;
    public static final int VK_Q = 0x51;
    public static final int VK_R = 0x52;
    public static final int VK_S = 0x53;
    public static final int VK_T = 0x54;
    public static final int VK_U = 0x55;
    public static final int VK_V = 0x56;
    public static final int VK_W = 0x57;
    public static final int VK_X = 0x58;
    public static final int VK_Y = 0x59;
    public static final int VK_Z = 0x5A;
    public static final int VK_OPEN_BRACKET = 0x5B;
    public static final int VK_BACK_SLASH = 0x5C;
    public static final int VK_CLOSE_BRACKET  = 0x5D;
    public static final int VK_NUMPAD0 = 0x60;
    public static final int VK_NUMPAD1 = 0x61;
    public static final int VK_NUMPAD2 = 0x62;
    public static final int VK_NUMPAD3 = 0x63;
    public static final int VK_NUMPAD4 = 0x64;
    public static final int VK_NUMPAD5 = 0x65;
    public static final int VK_NUMPAD6 = 0x66;
    public static final int VK_NUMPAD7 = 0x67;
    public static final int VK_NUMPAD8 = 0x68;
    public static final int VK_NUMPAD9 = 0x69;
    public static final int VK_MULTIPLY = 0x6A;
    public static final int VK_ADD = 0x6B;
    public static final int VK_SEPARATOR = 0x6C;
    public static final int VK_SUBTRACT = 0x6D;
    public static final int VK_DECIMAL = 0x6E;
    public static final int VK_DIVIDE = 0x6F;
    public static final int VK_DELETE = 0x7F;
    public static final int VK_NUM_LOCK = 0x90;
    public static final int VK_SCROLL_LOCK = 0x91;
    public static final int VK_F1 = 0x70;
    public static final int VK_F2 = 0x71;
    public static final int VK_F3 = 0x72;
    public static final int VK_F4 = 0x73;
    public static final int VK_F5 = 0x74;
    public static final int VK_F6 = 0x75;
    public static final int VK_F7 = 0x76;
    public static final int VK_F8 = 0x77;
    public static final int VK_F9 = 0x78;
    public static final int VK_F10 = 0x79;
    public static final int VK_F11 = 0x7A;
    public static final int VK_F12 = 0x7B;
    public static final int VK_PRINTSCREEN = 0x9A;
    public static final int VK_INSERT = 0x9B;
    public static final int VK_HELP = 0x9C;
    public static final int VK_META = 0x9D;
    public static final int VK_BACK_QUOTE = 0xC0;
    public static final int VK_QUOTE = 0xDE;
    public static final int VK_KP_UP = 0xE0;
    public static final int VK_KP_DOWN = 0xE1;
    public static final int VK_KP_LEFT = 0xE2;
    public static final int VK_KP_RIGHT = 0xE3;
    public static final int VK_AMPERSAND = 0x96;
    public static final int VK_ASTERISK = 0x97;
    public static final int VK_QUOTEDBL = 0x98;
    public static final int VK_LESS = 0x99;
    public static final int VK_GREATER  = 0xa0;
    public static final int VK_BRACELEFT = 0xa1;
    public static final int VK_BRACERIGHT = 0xa2;
    public static final int VK_AT = 0x0200;
    public static final int VK_COLON = 0x0201;
    public static final int VK_CIRCUMFLEX = 0x0202;
    public static final int VK_DOLLAR = 0x0203;
    public static final int VK_EURO_SIGN = 0x0204;
    public static final int VK_EXCLAMATION_MARK = 0x0205;
    public static final int VK_INVERTED_EXCLAMATION_MARK = 0x0206;
    public static final int VK_LEFT_PARENTHESIS = 0x0207;
    public static final int VK_NUMBER_SIGN = 0x0208;
    public static final int VK_PLUS = 0x0209;
    public static final int VK_RIGHT_PARENTHESIS = 0x020A;
    public static final int VK_UNDERSCORE = 0x020B;
    
    public final static int SWTVK_A = 'A';
    public final static int SWTVK_B = 'B';
    public final static int SWTVK_C = 'C';
    public final static int SWTVK_D = 'D';
    public final static int SWTVK_E = 'E';
    public final static int SWTVK_F = 'F';
    public final static int SWTVK_G = 'G';
    public final static int SWTVK_H = 'H';
    public final static int SWTVK_I = 'I';
    public final static int SWTVK_J = 'J';
    public final static int SWTVK_K = 'K';
    public final static int SWTVK_L = 'L';
    public final static int SWTVK_M = 'M';
    public final static int SWTVK_N = 'N';
    public final static int SWTVK_O = 'O';
    public final static int SWTVK_P = 'P';
    public final static int SWTVK_Q = 'Q';
    public final static int SWTVK_R = 'R';
    public final static int SWTVK_S = 'S';
    public final static int SWTVK_T = 'T';
    public final static int SWTVK_U = 'U';
    public final static int SWTVK_V = 'V';
    public final static int SWTVK_W = 'W';
    public final static int SWTVK_X = 'X';
    public final static int SWTVK_Y = 'Y';
    public final static int SWTVK_Z = 'Z';
    public final static int SWTVK_SPACE = ' ';
    public final static int SWTVK_0 = '0';
    public final static int SWTVK_1 = '1';
    public final static int SWTVK_2 = '2';
    public final static int SWTVK_3 = '3';
    public final static int SWTVK_4 = '4';
    public final static int SWTVK_5 = '5';
    public final static int SWTVK_6 = '6';
    public final static int SWTVK_7 = '7';
    public final static int SWTVK_8 = '8';
    public final static int SWTVK_9 = '9';
    public static final int SWTVK_SEMICOLON = ';';
    public static final int SWTVK_EQUALS = '=';
    public static final int SWTVK_COMMA = ',';
    public static final int SWTVK_MINUS = '-';
    public static final int SWTVK_PERIOD = '.';
    public static final int SWTVK_SLASH = '/';
    public static final int SWTVK_OPEN_BRACKET = '(';
    public static final int SWTVK_BACK_SLASH = '\\';
    public static final int SWTVK_CLOSE_BRACKET = ')';
    public static final int SWTVK_UNDERSCORE = '_';
    public static final int SWTVK_ADD = '+';
    public static final int SWTVK_PLUS = '+';
    public static final int SWTVK_NUMBER_SIGN = '+';
    public static final int SWTVK_MULTIPLY = '*';
    public static final int SWTVK_SUBTRACT = '-';
    public static final int SWTVK_DECIMAL = '.';
    public static final int SWTVK_DIVIDE = '.';
    public static final int SWTVK_BACK_QUOTE = '`';
    public static final int SWTVK_QUOTE = '\'';
    public static final int SWTVK_AMPERSAND = '&';
    public static final int SWTVK_ASTERISK = '*';
    public static final int SWTVK_QUOTEDBL = '"';
    public static final int SWTVK_LESS = '<';
    public static final int SWTVK_GREATER = '>';
    public static final int SWTVK_BRACELEFT = '{';
    public static final int SWTVK_BRACERIGHT = '}';
    public static final int SWTVK_AT = '@';
    public static final int SWTVK_CIRCUMFLEX = '~';
    public static final int SWTVK_DOLLAR = '$';
    public static final int SWTVK_EURO_SIGN = '$';
    public static final int SWTVK_EXCLAMATION_MARK = '!';
    public static final int SWTVK_INVERTED_EXCLAMATION_MARK = '!';
    public static final int SWTVK_LEFT_PARENTHESIS = '(';
    public static final int SWTVK_RIGHT_PARENTHESIS = ')';
    public static final int SWTVK_COLON = ':';
    public static final int SWTVK_TAB = '\t';
    public static final int SWTVK_F1 = SWT.F1;
    public static final int SWTVK_F2 = SWT.F2;
    public static final int SWTVK_F3 = SWT.F3;
    public static final int SWTVK_F4 = SWT.F4;
    public static final int SWTVK_F5 = SWT.F5;
    public static final int SWTVK_F6 = SWT.F6;
    public static final int SWTVK_F7 = SWT.F7;
    public static final int SWTVK_F8 = SWT.F8;
    public static final int SWTVK_F9 = SWT.F9;
    public static final int SWTVK_F10 = SWT.F10;
    public static final int SWTVK_F11 = SWT.F11;
    public static final int SWTVK_F12 = SWT.F12;
    public static final int SWTVK_ENTER = SWT.CR;
    public static final int SWTVK_BACK_SPACE = 8;
    public static final int SWTVK_DELETE = SWT.DEL;
    public static final int SWTVK_ESCAPE = SWT.ESC;

    // NOTE: I think this is right... should use the arrow ids instead of left/right/etc (intended for alignment)
    public static final int SWTVK_LEFT = SWT.ARROW_LEFT;
    public static final int SWTVK_KP_LEFT = SWT.ARROW_LEFT;
    public static final int SWTVK_RIGHT = SWT.ARROW_RIGHT;
    public static final int SWTVK_KP_RIGHT = SWT.ARROW_RIGHT;
    public static final int SWTVK_UP = SWT.ARROW_UP;
    public static final int SWTVK_KP_UP = SWT.ARROW_UP;
    public static final int SWTVK_DOWN = SWT.ARROW_DOWN;
    public static final int SWTVK_KP_DOWN = SWT.ARROW_DOWN;
    public static final int SWTVK_HOME = SWT.HOME;
    public static final int SWTVK_END = SWT.END;
    /*
    public static final int SWTVK_LEFT = SWT.LEFT;
    public static final int SWTVK_KP_LEFT = SWT.LEFT;
    public static final int SWTVK_RIGHT = SWT.RIGHT;
    public static final int SWTVK_KP_RIGHT = SWT.RIGHT;
    public static final int SWTVK_UP = SWT.UP;
    public static final int SWTVK_KP_UP = SWT.UP;
    public static final int SWTVK_DOWN = SWT.DOWN;
    public static final int SWTVK_KP_DOWN = SWT.DOWN;
    public static final int SWTVK_HOME = SWT.HOME;
    public static final int SWTVK_END = SWT.END;
    */
    
    public static final int SWTVK_PAGE_UP = SWT.PAGE_UP;
    public static final int SWTVK_PAGE_DOWN = SWT.PAGE_DOWN;
    public static final int SWTVK_INSERT = SWT.INSERT;
    public static final int SWTVK_SHIFT = SWT.SHIFT;
    public static final int SWTVK_CONTROL = SWT.CONTROL;
    public static final int SWTVK_ALT = SWT.ALT;
    public static final int SWTVK_META = SWT.ALT;
    public static final int SWTVK_CANCEL = SWT.CANCEL;
    public static final int SWTVK_CLEAR = SWT.NONE;
    public static final int SWTVK_PAUSE = SWT.PAUSE;
    public static final int SWTVK_CAPS_LOCK = SWT.CAPS_LOCK;
    public static final int SWTVK_SEPARATOR = SWT.SEPARATOR;
    public static final int SWTVK_NUM_LOCK = SWT.NUM_LOCK;
    public static final int SWTVK_SCROLL_LOCK = SWT.SCROLL_LOCK;
    public static final int SWTVK_PRINTSCREEN = SWT.PRINT_SCREEN;
    public static final int SWTVK_HELP = SWT.HELP;

    public static final int KEY_LOCATION_UNKNOWN = 0;
    public static final int KEY_LOCATION_STANDARD = 1;
    public static final int KEY_LOCATION_LEFT = 2;
    public static final int KEY_LOCATION_RIGHT = 3;
    public static final int KEY_LOCATION_NUMPAD = 4;
    
    public static final int VK_UNDEFINED = 0;
    public static final char CHAR_UNDEFINED = '\uffff';

    // Since 1.5
    public static final int VK_CONTEXT_MENU = 0x020D;
    
    
    /** Map of SWT key constants to AWT constants. This is to
      * ensure binary compatibility for existing Swing/AWT apps.
      */
    private static int[] translationMap = new int[] {
        VK_ENTER,               SWTVK_ENTER, 
        VK_BACK_SPACE,          SWTVK_BACK_SPACE,
        VK_TAB,                 SWTVK_TAB,
        VK_CANCEL,              SWTVK_CANCEL,
        VK_CLEAR,               SWTVK_CLEAR,
        VK_SHIFT,               SWTVK_SHIFT,
        VK_CONTROL,             SWTVK_CONTROL,
        VK_ALT,                 SWTVK_ALT,
        VK_PAUSE,               SWTVK_PAUSE,
        VK_CAPS_LOCK,           SWTVK_CAPS_LOCK,
        VK_ESCAPE,              SWTVK_ESCAPE,
        VK_SPACE,               SWTVK_SPACE,
        VK_PAGE_UP,             SWTVK_PAGE_UP,
        VK_PAGE_DOWN,           SWTVK_PAGE_DOWN,
        VK_END,                 SWTVK_END,
        VK_HOME,                SWTVK_HOME,
        VK_LEFT,                SWTVK_LEFT,
        VK_UP,                  SWTVK_UP,
        VK_RIGHT,               SWTVK_RIGHT,
        VK_DOWN,                SWTVK_DOWN,
        VK_COMMA,               SWTVK_COMMA,
        VK_MINUS,               SWTVK_MINUS,
        VK_PERIOD,              SWTVK_PERIOD,
        VK_SLASH,               SWTVK_SLASH,
        VK_0,                   SWTVK_0,
        VK_1,                   SWTVK_1,
        VK_2,                   SWTVK_2,
        VK_3,                   SWTVK_3,
        VK_4,                   SWTVK_4,
        VK_5,                   SWTVK_5,
        VK_6,                   SWTVK_6,
        VK_7,                   SWTVK_7,
        VK_8,                   SWTVK_8,
        VK_9,                   SWTVK_9,
        VK_SEMICOLON,           SWTVK_SEMICOLON,
        VK_EQUALS,              SWTVK_EQUALS,
        VK_A,                   SWTVK_A,
        VK_B,                   SWTVK_B,
        VK_C,                   SWTVK_C,
        VK_D,                   SWTVK_D,
        VK_E,                   SWTVK_E,
        VK_F,                   SWTVK_F,
        VK_G,                   SWTVK_G,
        VK_H,                   SWTVK_H,
        VK_I,                   SWTVK_I,
        VK_J,                   SWTVK_J,
        VK_K,                   SWTVK_K,
        VK_L,                   SWTVK_L,
        VK_M,                   SWTVK_M,
        VK_N,                   SWTVK_N,
        VK_O,                   SWTVK_O,
        VK_P,                   SWTVK_P,
        VK_Q,                   SWTVK_Q,
        VK_R,                   SWTVK_R,
        VK_S,                   SWTVK_S,
        VK_T,                   SWTVK_T,
        VK_U,                   SWTVK_U,
        VK_V,                   SWTVK_V,
        VK_W,                   SWTVK_W,
        VK_X,                   SWTVK_X,
        VK_Y,                   SWTVK_Y,
        VK_Z,                   SWTVK_Z,
        VK_OPEN_BRACKET,        SWTVK_OPEN_BRACKET,
        VK_BACK_SLASH,          SWTVK_BACK_SLASH,
        VK_CLOSE_BRACKET,       SWTVK_CLOSE_BRACKET,
        VK_NUMPAD0,             SWTVK_0,
        VK_NUMPAD1,             SWTVK_1,
        VK_NUMPAD2,             SWTVK_2,
        VK_NUMPAD3,             SWTVK_3,
        VK_NUMPAD4,             SWTVK_4,
        VK_NUMPAD5,             SWTVK_5,
        VK_NUMPAD6,             SWTVK_6,
        VK_NUMPAD7,             SWTVK_7,
        VK_NUMPAD8,             SWTVK_8,
        VK_NUMPAD9,             SWTVK_9,
        VK_MULTIPLY,            SWTVK_MULTIPLY,
        VK_ADD,                 SWTVK_ADD,
        VK_SEPARATOR,           SWTVK_SEPARATOR,
        VK_SUBTRACT,            SWTVK_SUBTRACT,
        VK_DECIMAL,             SWTVK_DECIMAL,
        VK_DIVIDE,              SWTVK_DIVIDE,
        VK_DELETE,              SWTVK_DELETE,
        VK_NUM_LOCK,            SWTVK_NUM_LOCK,
        VK_SCROLL_LOCK,         SWTVK_SCROLL_LOCK,
        VK_F1,                  SWTVK_F1,
        VK_F2,                  SWTVK_F2,
        VK_F3,                  SWTVK_F3,
        VK_F4,                  SWTVK_F4,
        VK_F5,                  SWTVK_F5,
        VK_F6,                  SWTVK_F6,
        VK_F7,                  SWTVK_F7,
        VK_F8,                  SWTVK_F8,
        VK_F9,                  SWTVK_F9,
        VK_F10,                 SWTVK_F10,
        VK_F11,                 SWTVK_F11,
        VK_F12,                 SWTVK_F12,
        VK_PRINTSCREEN,         SWTVK_PRINTSCREEN,
        VK_INSERT,              SWTVK_INSERT,
        VK_HELP,                SWTVK_HELP,
        VK_META,                SWTVK_META,
        VK_BACK_QUOTE,          SWTVK_BACK_QUOTE,
        VK_QUOTE,               SWTVK_QUOTE,
        VK_KP_UP,               SWTVK_UP,
        VK_KP_DOWN,             SWTVK_DOWN,
        VK_KP_LEFT,             SWTVK_LEFT,
        VK_KP_RIGHT,            SWTVK_RIGHT,
        VK_AMPERSAND,           SWTVK_AMPERSAND,
        VK_ASTERISK,            SWTVK_ASTERISK,
        VK_QUOTEDBL,            SWTVK_QUOTEDBL,
        VK_LESS,                SWTVK_LESS,
        VK_GREATER,             SWTVK_GREATER,
        VK_BRACELEFT,           SWTVK_BRACELEFT,
        VK_BRACERIGHT,          SWTVK_BRACERIGHT,
        VK_AT,                  SWTVK_AT,
        VK_COLON,               SWTVK_COLON,
        VK_CIRCUMFLEX,          SWTVK_CIRCUMFLEX,
        VK_DOLLAR,              SWTVK_DOLLAR,
        VK_EURO_SIGN,           SWTVK_EURO_SIGN,
        VK_EXCLAMATION_MARK,    SWTVK_EXCLAMATION_MARK,
        VK_INVERTED_EXCLAMATION_MARK, SWTVK_INVERTED_EXCLAMATION_MARK,
        VK_LEFT_PARENTHESIS,    SWTVK_LEFT_PARENTHESIS,
        VK_NUMBER_SIGN,         SWTVK_NUMBER_SIGN,
        VK_PLUS,                SWTVK_PLUS,
        VK_RIGHT_PARENTHESIS,   SWTVK_RIGHT_PARENTHESIS,
        VK_UNDERSCORE,          SWTVK_UNDERSCORE
    };
    
    public KeyEvent(Component source, int id) {
        this(source, id, 0, 0, 0, '\0', KEY_LOCATION_UNKNOWN);
    }
    
    public KeyEvent(Component source, int id, long when, int modifiers, int keyCode) {
        this(source, id, when, modifiers, keyCode, '\0', KEY_LOCATION_UNKNOWN);
    }
    
    public KeyEvent(Component source, int id, long when, int modifiers,
            		int keyCode, char keyChar) {
        this(source, id, when, modifiers, keyCode, keyChar, KEY_LOCATION_UNKNOWN);
    }
    
    public KeyEvent(Component source, int id, long when, int modifiers,
            		int keyCode, char keyChar, int keyLocation) {
        super(source, id, when, modifiers);
        this.keyCode = keyCode;
        this.keyChar = keyChar;
        this.keyLocation = keyLocation;
	this.keyWhen = when;
    }

    /** Getter for property keyChar.
     * @return Value of property keyChar.
     *
     */
    public char getKeyChar() {
        return keyChar;
    }    
    
    /** Setter for property keyChar.
     * @param keyChar New value of property keyChar.
     *
     */
    public void setKeyChar(char keyChar) {
        this.keyChar = keyChar;
    }    
    
    /** Getter for property keyCode.
     * @return Value of property keyCode.
     *
     */
    public int getKeyCode() {
        return keyCode;
    }
    
    /** Setter for property keyCode.
     * @param keyCode New value of property keyCode.
     *
     */
    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
    
    /** Setter for property modifiers.
     * @param modifiers New value of property modifiers.
     *
     */
    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }
    
    /** Returns the AWT key code for an SWT key */
    public static int translateSWTKey(int swtKey) {
        int awt = 0;
        for (int i = 1; i < translationMap.length; i += 2) {
            if (translationMap[i] == swtKey) {
                awt = translationMap[i - 1];
                break;
            }
        }
        return awt;
    }
    
    /** Returns the SWT key code for an AWT key */
    public static int translateAWTKey(int awtKey) {
        int swt = 0;
        for (int i = 0; i < translationMap.length; i += 2) {
            if (translationMap[i] == awtKey) {
                swt = translationMap[i + 1];
                break;
            }
        }
        return swt;
    }
    
    public long getWhen() {
	return keyWhen;
    }

    public int getKeyLocation() {
	return keyLocation;
    }
    public static String getKeyText(int keyCode) 
    {
        if (keyCode >= VK_0 && keyCode <= VK_9 || 
            keyCode >= VK_A && keyCode <= VK_Z) 
        {
            return String.valueOf((char)keyCode);
        }
        else
        {
        	return "not implemented";
        }
    }
    //TODO: add more action-keys
    public boolean isActionKey() 
    {
        switch (keyCode) {
          case VK_HOME:
          case VK_END:
          case VK_PAGE_UP:
          case VK_PAGE_DOWN:
          case VK_UP:
          case VK_DOWN:
          case VK_LEFT:
          case VK_RIGHT:
          case VK_KP_LEFT: 
          case VK_KP_UP: 
          case VK_KP_RIGHT: 
          case VK_KP_DOWN: 
          case VK_F1:
          case VK_F2:
          case VK_F3:
          case VK_F4:
          case VK_F5:
          case VK_F6:
          case VK_F7:
          case VK_F8:
          case VK_F9:
          case VK_F10:
          case VK_F11:
          case VK_F12:
          case VK_PRINTSCREEN:
          case VK_SCROLL_LOCK:
          case VK_CAPS_LOCK:
          case VK_NUM_LOCK:
          case VK_PAUSE:
          case VK_INSERT:
          case VK_HELP:
              return true;
        }
        return false;
    }
}
