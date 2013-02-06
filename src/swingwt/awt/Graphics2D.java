/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.util.Map;

import swingwt.awt.font.FontRenderContext;
import swingwt.awt.font.GlyphVector;
import swingwt.awt.geom.AffineTransform;
import swingwt.awt.image.BufferedImage;
import swingwt.awt.image.BufferedImageOp;

public abstract class Graphics2D extends Graphics {

	public abstract void drawString(String s, float x, float y);
    public abstract void draw(Shape s); 
    public abstract void drawGlyphVector(GlyphVector g, float x, float y);
    public abstract void fill(Shape s);
    public abstract boolean hit(Rectangle rect,
				Shape s,
				boolean onStroke);
    public abstract GraphicsConfiguration getDeviceConfiguration();
    public abstract void setComposite(Composite comp);
    public abstract void setPaint( Paint paint );
    public abstract void setStroke(Stroke s);
    public abstract void setRenderingHint(RenderingHints.Key hintKey, Object hintValue);
    public abstract Object getRenderingHint(RenderingHints.Key hintKey);
    public abstract void setRenderingHints(Map hints);
    public abstract void addRenderingHints(Map hints);
    public abstract RenderingHints getRenderingHints();
    public abstract void translate(int x, int y);
    public abstract void translate(double tx, double ty);
    public abstract void rotate(double theta);
    public abstract void rotate(double theta, double x, double y);
    public abstract void scale(double sx, double sy);
    public abstract void shear(double shx, double shy);
    public abstract void transform(AffineTransform Tx);
    public abstract void setTransform(AffineTransform Tx);
    public abstract AffineTransform getTransform();
    public abstract Paint getPaint();
    public abstract Composite getComposite();
    public abstract void setBackground(Color color);
    public abstract Color getBackground();
    public abstract Stroke getStroke();
    public abstract void clip(Shape s);
    public abstract FontRenderContext getFontRenderContext();
    
    public abstract void drawImage(BufferedImage img,
			   BufferedImageOp op,
			   int x,
			   int y);
}
