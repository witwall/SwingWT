/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.border;

import swingwt.awt.*;

public class EtchedBorder extends AbstractBorder implements Border {
    public static final int RAISED  = 0;
    public static final int LOWERED = 1;
    protected int etchType = RAISED;
    
    public EtchedBorder() { this(LOWERED); }
    public EtchedBorder(int etchType) { this(etchType, null, null); }
    public EtchedBorder(Color highlight, Color shadow) { this(LOWERED, highlight, shadow); }
    public EtchedBorder(int etchType, Color highlight, Color shadow) { this.etchType = etchType; }
    
}
