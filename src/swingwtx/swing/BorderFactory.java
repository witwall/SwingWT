/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwtx.swing;

import swingwtx.swing.border.*;
import swingwt.awt.*;

public abstract class BorderFactory {
    
    public static Border createLineBorder(Color color) {
        return new LineBorder(color, 1);
    }
    public static Border createLineBorder(Color color, int thickness)  {
        return new LineBorder(color, thickness);
    }
    
    static final Border sharedRaisedBevel = new BevelBorder(BevelBorder.RAISED);
    static final Border sharedLoweredBevel = new BevelBorder(BevelBorder.LOWERED);
    
    public static Border createRaisedBevelBorder() {
        return createSharedBevel(BevelBorder.RAISED);
    }
    public static Border createLoweredBevelBorder() {
        return createSharedBevel(BevelBorder.LOWERED);
    }
    public static Border createBevelBorder(int type) {
        return createSharedBevel(type);
    }
    public static Border createBevelBorder(int type, Color highlight, Color shadow) {
        return new BevelBorder(type, highlight, shadow);
    }
    public static Border createBevelBorder(int type,
    Color highlightOuter, Color highlightInner,
    Color shadowOuter, Color shadowInner) {
        return new BevelBorder(type, highlightOuter, highlightInner,
        shadowOuter, shadowInner);
    }
    static Border createSharedBevel(int type)	{
        if(type == BevelBorder.RAISED) {
            return sharedRaisedBevel;
        } else if(type == BevelBorder.LOWERED) {
            return sharedLoweredBevel;
        }
        return null;
    }
    
    static final Border sharedEtchedBorder = new EtchedBorder();
    private static Border sharedRaisedEtchedBorder;
    
    public static Border createEtchedBorder()    {
        return sharedEtchedBorder;
    }
    public static Border createEtchedBorder(Color highlight, Color shadow)    {
        return new EtchedBorder(highlight, shadow);
    }
    public static Border createEtchedBorder(int type)    {
        switch (type) {
            case EtchedBorder.RAISED:
                if (sharedRaisedEtchedBorder == null) {
                    sharedRaisedEtchedBorder = new EtchedBorder
                    (EtchedBorder.RAISED);
                }
                return sharedRaisedEtchedBorder;
            case EtchedBorder.LOWERED:
                return sharedEtchedBorder;
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
    public static Border createEtchedBorder(int type, Color highlight,
    Color shadow)    {
        return new EtchedBorder(type, highlight, shadow);
    }
    public static TitledBorder createTitledBorder(String title)     {
        return new TitledBorder(title);
    }
    public static TitledBorder createTitledBorder(Border border)       {
        return new TitledBorder(border);
    }
    public static TitledBorder createTitledBorder(Border border,
    String title) {
        return new TitledBorder(border, title);
    }
    public static TitledBorder createTitledBorder(Border border,
    String title,
    int titleJustification,
    int titlePosition)      {
        return new TitledBorder(border, title, titleJustification,
        titlePosition);
    }
    public static TitledBorder createTitledBorder(Border border,
    String title,
    int titleJustification,
    int titlePosition,
    Font titleFont) {
        return new TitledBorder(border, title, titleJustification,
        titlePosition, titleFont);
    }
    public static TitledBorder createTitledBorder(Border border,
    String title,
    int titleJustification,
    int titlePosition,
    Font titleFont,
    Color titleColor)       {
        return new TitledBorder(border, title, titleJustification,
        titlePosition, titleFont, titleColor);
    }
    final static Border emptyBorder = new EmptyBorder(0, 0, 0, 0);
    public static Border createEmptyBorder() {
        return emptyBorder;
    }
    public static Border createEmptyBorder(int top, int left,
    int bottom, int right) {
        return new EmptyBorder(top, left, bottom, right);
    }
    public static CompoundBorder createCompoundBorder() {
        return new CompoundBorder();
    }
    public static CompoundBorder createCompoundBorder(Border outsideBorder,
    Border insideBorder) {
        return new CompoundBorder(outsideBorder, insideBorder);
    }
    public static MatteBorder createMatteBorder(int top, int left, int bottom, int right,
    Color color) {
        return new MatteBorder(top, left, bottom, right, color);
    }
    public static MatteBorder createMatteBorder(int top, int left, int bottom, int right,
    Icon tileIcon) {
        return new MatteBorder(top, left, bottom, right, tileIcon);
    }
}

