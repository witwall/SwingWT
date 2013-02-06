/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import swingwt.awt.font.FontRenderContext;
import swingwt.awt.font.GlyphVector;
import swingwt.awt.font.LineMetrics;
import swingwt.awt.font.SWTGlyphVector;
import swingwt.awt.font.TextAttribute;
import swingwtx.swing.SwingUtilities;
import swingwtx.swing.SwingWTUtils;

import org.eclipse.swt.graphics.FontData;

import java.text.CharacterIterator;
import java.util.*;

public class Font {
    
    private org.eclipse.swt.graphics.Font peer = null;
    
    /** Destroy the font when finalize is called. Not required for
     *  the constructor passing the font - only do this when this
     *  instance created the SWT font */
    protected boolean disposeOnFinalize = true;

    public static final int PLAIN = 0;
    public static final int BOLD = 1;
    public static final int ITALIC = 2;
    public static final int ROMAN_BASELINE = 0;
    public static final int CENTER_BASELINE = 1;
    public static final int HANGING_BASELINE = 2;
    public static final int TRUETYPE_FONT = 0;
    
    protected String name = "Dialog";
    protected int style = PLAIN;
    protected int size = 12;
    
    public Font(final String name, final int style, final int size) {
        this.name = name;
        this.style = style;
        this.size = size;
        createPeer();
    }
    
    public Font(final String name, final int style, final int size, boolean f) {
        this.name = name;
        this.style = style;
        this.size = size;
        createPeer();
    }
    
    public Font(Map attributes) {
        setAttributes(attributes);
        createPeer();
    }
    
    /** Creates a font from a matching SWT font */
    public Font(org.eclipse.swt.graphics.Font swtfont) {
        peer = swtfont;
        populateFromSWTFontData(swtfont.getFontData()[0]);
    }
    
    /** Creates a font from the SWT fontdata */
    public Font(org.eclipse.swt.graphics.FontData fd) {
        peer = new org.eclipse.swt.graphics.Font(SwingWTUtils.getDisplay(), fd);
        populateFromSWTFontData(fd);
    }
    
    public static Font getFont(Map attributes) {
        return new Font(attributes);    
    }
    
    private static int swingToSwtSize(int swingSize) {
        return (int) (swingSize * 0.75);
    }

    private static int swtToSwingSize(int swtSize) {
        return (int) ((4 * swtSize) / 3.0);
    }

    // TODO temporary
    private static Font stubFont = null;
    public static Font getStubFont()
    {
        if (stubFont == null) {
            SwingUtilities.invokeSync( new Runnable() {
                public void run() {
                    stubFont = new Font(swingwtx.swing.SwingWTUtils.getDisplay().getSystemFont());
                }
            });
        }
        return stubFont;
    }
    
    /** Creates the SWT peer from the Font info we have */
    protected void createPeer() {
        SwingUtilities.invokeSync( new Runnable() {
            public void run() {
                // Map AWT constants to SWT
                int swtStyle = org.eclipse.swt.SWT.NONE;
                if ((style & BOLD) > 0)
                    swtStyle = swtStyle | org.eclipse.swt.SWT.BOLD;
                if ((style & ITALIC) > 0)
                    swtStyle = swtStyle | org.eclipse.swt.SWT.ITALIC;
                int swtSize = swingToSwtSize(size);
                peer = new org.eclipse.swt.graphics.Font(swingwtx.swing.SwingWTUtils.getDisplay(), name, swtSize, swtStyle);
                // If this class created it, it's up to it to 
                // destroy the peer.
                disposeOnFinalize = true;
            }
        }); 
    }
    
    /** Sets font properties based on a SWT font */
    protected void populateFromSWTFontData(org.eclipse.swt.graphics.FontData fd) {
        
        // Must be on dispatch thread if we have a real SWT font to throw about
        disposeOnFinalize = false;

        this.name = fd.getName();
        style = PLAIN;
        if ((fd.getStyle() & org.eclipse.swt.SWT.BOLD) > 0)
            style = style | BOLD;
        if ((fd.getStyle() & org.eclipse.swt.SWT.ITALIC) > 0)
            style = style | ITALIC;
        size = swtToSwingSize(fd.getHeight());
    }
    
    public void setAttributes(Map attributes) {
        if (attributes != null) {
        	Object value;
        	
        	value = attributes.get(TextAttribute.FAMILY);
        	if(value == null) {
        		value = attributes.get("family");
        	}
        	
        	if(value != null) {
        		this.name = value.toString();
        	}

        	value = attributes.get(TextAttribute.SIZE);
        	if(value == null) {
        		value = attributes.get("size");
        	}
        	
        	if(value != null) {
        		this.size = (int)Float.parseFloat(value.toString());
        	}

        	value = attributes.get("style");
        	if(value != null) {
        		this.style = Integer.parseInt(value.toString());
        	}
        }   
    }
    
    public Map getAttributes() {
        Hashtable ht = new Hashtable();
        ht.put("family", this.name);
        ht.put("size", new Integer(this.size));
        ht.put("style", new Integer(this.style));
        return ht;
    }
    
    public String getName() { return name; }
    public String getFontName() { return name; }
    public String getFamily() { return name; }
    public String getFontName(Locale l) { return getFontName(); }
    public String getFamily(Locale l) { return getFamily(); }
    
    public int getStyle() { return style; }
    public int getSize() { return size; }
    public float getSize2D() { return (float) size; }
    
    public boolean isBold() { return (style & BOLD) > 0; }
    public boolean isPlain() { return style == PLAIN; }
    public boolean isItalic() { return (style & ITALIC) > 0; }
    
    public Font deriveFont(float newSize) { return new Font(name, style, (int) newSize); }    
    public Font deriveFont(int newStyle) { return new Font(name, newStyle, size); }
    public Font deriveFont(int newStyle, float newSize) { return new Font(name, newStyle, (int) newSize); }

    public GlyphVector createGlyphVector(FontRenderContext frc,
            CharacterIterator ci){
    	return new SWTGlyphVector(this);
    }
    
    public LineMetrics getLineMetrics(String str,
            FontRenderContext frc) {
    	FontMetrics fm = Toolkit.getToolkit().getFontMetrics(this);
    	return fm.getLineMetrics(str, null);
    }
    
    public LineMetrics getLineMetrics(String str,
            int beginIndex,
            int limit,
            FontRenderContext frc){
    	FontMetrics fm = Toolkit.getToolkit().getFontMetrics(this);
    	return fm.getLineMetrics(str, beginIndex, limit, null);
    }
    
    public LineMetrics getLineMetrics(CharacterIterator ci, 
    		int beginIndex, 
    		int limit, FontRenderContext frc) 
    {
    	FontMetrics fm = Toolkit.getToolkit().getFontMetrics(this);
    	return fm.getLineMetrics(ci.toString(), beginIndex, limit, null);    	
    }
    public LineMetrics getLineMetrics(char[] chars,
            int beginIndex,
            int limit,
            FontRenderContext frc){
    	FontMetrics fm = Toolkit.getToolkit().getFontMetrics(this);
    	return fm.getLineMetrics(chars, beginIndex, limit, null);
    }
    
    public int canDisplayUpTo(String str){
    	// pretend we can display every char
    	return -1;
    }
    
    public int canDisplayUpTo(char[] text,
            int start,
            int limit) {
    	// pretend we can display every char
    	return -1;
    }

    public int canDisplayUpTo(CharacterIterator iter,
            int start,
            int limit) {
    	// pretend we can display every char
    	return -1;    	
    }
    public org.eclipse.swt.graphics.Font getSWTFont() {
        return peer;    
    }
    
    public void dispose() {
        peer.dispose();    
    }
    
    protected void finalize() throws Throwable {
        if (disposeOnFinalize) dispose();
    }

    public String toString() {
        String styleName;        
        switch (style) {
            case PLAIN:  styleName = "plain";  break;
            case BOLD:   styleName = "bold";   break;
            case ITALIC: styleName = "italic"; break;
            default:     styleName = "other";  break;
        }
        String style = "?";
        
        return getClass().getName() + "[name=" + name + ",style=" + styleName + ",size=" + size + "]";
    }
}
