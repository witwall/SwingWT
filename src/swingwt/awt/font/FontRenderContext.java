/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.font;

import swingwt.awt.geom.*;

public class FontRenderContext {
    private AffineTransform tx;
    private boolean bIsAntiAliased;
    private boolean bUsesFractionalMetrics;
    
    protected FontRenderContext() {
    }
    
    public FontRenderContext(AffineTransform tx,
                            boolean isAntiAliased,
                            boolean usesFractionalMetrics) {
        if (tx != null && !tx.isIdentity()) {
            this.tx = new AffineTransform(tx);
        }
        this.bIsAntiAliased = isAntiAliased;
        this.bUsesFractionalMetrics = usesFractionalMetrics;
    }
    
    public AffineTransform getTransform() {
        return (tx == null) ? new AffineTransform() : new AffineTransform(tx);
    }
    
    public boolean isAntiAliased() {
        return this.bIsAntiAliased;
    }
    
    public boolean usesFractionalMetrics() {
        return this.bUsesFractionalMetrics;
    }
    
    public boolean equals(FontRenderContext rhs) {
	if (this == rhs) {
	    return true;
	}
	if (rhs != null &&
	    rhs.bIsAntiAliased == bIsAntiAliased &&
	    rhs.bUsesFractionalMetrics == bUsesFractionalMetrics) {
	    return tx == null ? rhs.tx == null : tx.equals(rhs.tx);
	}
	return false;
    }
}
