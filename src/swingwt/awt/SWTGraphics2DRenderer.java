/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Transform;

import swingwt.awt.font.FontRenderContext;
import swingwt.awt.font.GlyphVector;
import swingwt.awt.geom.AffineTransform;
import swingwt.awt.geom.PathIterator;
import swingwt.awt.geom.SWTPathIterator;
import swingwt.awt.image.BufferedImage;
import swingwt.awt.image.BufferedImageOp;
import swingwt.awt.image.ImageObserver;
import swingwtx.swing.SwingWTUtils;

/**
 * This class is the final point for the mapped AWT graphics stuff - it
 * provides the rendering capabilities to the Graphics/Graphics2D classes
 * via mappings to SWT GC calls.
 *
 * @author  Robin Rawson-Tetley
 */
public class SWTGraphics2DRenderer extends Graphics2D {
    
    /** The SWT graphics context for painting */
    protected GC gc = null;
    /** The current font for the GC */
    protected swingwt.awt.Font f = null;
    /** The current colour for the GC */
    protected swingwt.awt.Color c = null;
    /** the curennt stroke of the graphics context **/
    protected Stroke stroke = null;
    
    /** Whether this object should use a finalizer based destructor. This is
     *  set to false generally by Paint Events, as they will ultimately handle
     *  the GC's destruction.
     */
    protected boolean shouldDestroy = false;
    
    public SWTGraphics2DRenderer(final GC gcon) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc = gcon;
                
                // Note - we don't to clean these up, since the GC owns and
                // created them.
                f = new swingwt.awt.Font(gc.getFont());
                c = new swingwt.awt.Color(gc.getForeground());
            }
        });
    }
    
    /**
     * @param needToCleanUp Set to true if you want a finalize destructor
     */
    public SWTGraphics2DRenderer(GC gc, boolean needToCleanUp) {
        this(gc);
        shouldDestroy = needToCleanUp;
    }
    
    public Graphics create() { 
    	// return new SWTGraphics2DRenderer(gc.clone(), true);

    	// FIXME This is bad because users of this graphics might create
    	// a temporary graphics and then dispose it. 
        return this;
    }

    public void dispose() {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.dispose();
            }
        });
    }
    
    /** Returns the SWT GC used for direct manipulation */
    public GC getSWTGC() { return gc; }
    
    public void clearRect(final int x, final int y, final int width, final int height) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.fillRectangle(x, y, width, height);    
            }
        });
    }
    /** NOT IMPLEMENTED */
    public void clipRect(int x, int y, int width, int height) {  }
          
    public void copyArea(final int x, final int y, final int width, final int height, final  int dx, final int dy) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.copyArea(x, y, width, height, dx, dy);    
            }
        });
    }

    public void redrawUnder(final int x, final int y, final int width, final int height) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                org.eclipse.swt.graphics.Image image = new org.eclipse.swt.graphics.Image(
                        swingwtx.swing.SwingWTUtils.getDisplay(), width, height);
                gc.copyArea(image, x, y);
                gc.drawImage(image, x, y);
            }
        });
    }

    public void drawArc(final int x, final int y, final int width, final int height, final  int startAngle,final  int arcAngle) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawArc(x, y, width, height, startAngle, arcAngle);    
            }
        });
    }
    
    public void drawBytes(final byte[] data, final int offset, final int length, final int x, final int y) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawString(new String(data), x, y - f.getSize(), true);
            }
        });
    }
    public void drawChars(final char[] data, final int offset, final int length, final int x, final int y) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawString(new String(data), x, y - f.getSize(), true);
            }
        });
    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @param img
     * @param op
     * @param x
     * @param y
     */
    public void drawImage(BufferedImage img,
			   BufferedImageOp op,
			   int x,
			   int y) {
    	
    }
    
    public boolean drawImage(final swingwt.awt.Image img, final int x, final int y, final Color bgcolor, final ImageObserver observer) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawImage(img.image, x, y);
            }
        });
        return true;
    }     
    public boolean drawImage(final swingwt.awt.Image img, final int x, final int y, final ImageObserver observer) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawImage(img.image, x, y);    
            }
        });
        return true;
    }
    public boolean drawImage(final swingwt.awt.Image img, final int x, final int y, final int width, final int height, final Color bgcolor, final ImageObserver observer) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawImage(img.image, x, y);    
            }
        });
        return true;
    }
    public boolean drawImage(final swingwt.awt.Image img, final int x, final int y, final int width, final int height, final ImageObserver observer) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawImage(img.image, 0, 0, img.getWidth(null), img.getHeight(null), x, y, width, height);    
            }
        });
        return true;
    }
    public boolean drawImage(final swingwt.awt.Image img, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1, final int sy1, final int sx2, final int sy2, final Color bgcolor,final  ImageObserver observer) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawImage(img.image, sx1, sy1, sx2 - sx1, sy2 - sy1, dx1, dy1, dx2 - dx1, dy2 - dy1);
            }
        });
        return true;
    }
    public boolean drawImage(final swingwt.awt.Image img, final int dx1, final int dy1, final int dx2, final int dy2, final int sx1, final int sy1, final int sx2, final int sy2, ImageObserver observer) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawImage(img.image, sx1, sy1, sx2 - sx1, sy2 - sy1, dx1, dy1, dx2 - dx1, dy2 - dy1);
            }
        });
        return true;
    }

    public void drawLine(final int x1, final int y1, final int x2, final int y2) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawLine(x1, y1, x2, y2);    
            }
        });
    }
          
    public void drawOval(final int x, final int y, final int width, final int height) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawOval(x, y, width, height);
            }
        });
    }
        
    public void drawPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawPolygon(mergeArrays(xPoints, yPoints, nPoints));
            }
        });
    }

    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        gc.drawPolyline(mergeArrays(xPoints, yPoints, nPoints));
    }
    
    public void drawString(String s, float x, float y){
        drawString(s, (int)x - f.getSize(), (int)y, true);
    }

    public void drawString(final String str, final int x, final int y) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawString(str, x, y - f.getSize(), true);
            }
        });
    }
    
    public void drawString(final String str, final int x, final int y, final boolean transparent) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.drawString(str, x, y - f.getSize(), transparent);
            }
        });
    }
    
    public void fillArc(final int x, final int y, final int width, final int height, final int startAngle, final int arcAngle) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.setBackground(c.getSWTColor());
                gc.fillArc(x, y, width, height, startAngle, arcAngle);    
            }
        });
    }
          
    public void fillOval(final int x, final int y, final int width, final int height) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.setBackground(c.getSWTColor());
                gc.fillOval(x, y, width, height);
            }
        });     
    }
    
    public void fillPolygon(final int[] xPoints, final int[] yPoints, final int nPoints) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                //Save and restore color, otherwise a drawString() will fail
                org.eclipse.swt.graphics.Color old = gc.getBackground();
                gc.setBackground(c.getSWTColor());
                gc.fillPolygon(mergeArrays(xPoints, yPoints, nPoints));
                gc.setBackground(old);
            }
        });
    }
          
    public void fillRect(final int x, final int y, final int width, final int height) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.setBackground(c.getSWTColor());
                gc.fillRectangle(x, y, width, height);    
            }
        });
    }
    
    /** Calls <code>fillRect</code> instead */
    public void fillRoundRect(final int x, final int y, final int width, final int height, final int arcWidth, final int arcHeight) {
        fillRect(x, y, width, height);
    }
    
    public Font getFont() {
        return f;
    }
    
    public Color getColor() {
        return c;
    }
    
    public void setFont(swingwt.awt.Font f) {
        if (f != this.f) {
            this.f = f;
            swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    gc.setFont(SWTGraphics2DRenderer.this.f.getSWTFont());
                }
            });
        }
    }
    
    public void setColor(swingwt.awt.Color color) {
        this.c = color;
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.setForeground(c.getSWTColor());
            }
        });
    }
    
    protected void finalize() throws Throwable {
        if (shouldDestroy) {
            swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
                public void run() {
                    gc.dispose();
                }
            });
        }
    }
    
    /** NOT IMPLEMENTED */
    public void addRenderingHints(Map hints) {
    }
    
    /** NOT IMPLEMENTED */
    public void clip(Shape s) {
    }
    

    protected Path getSWTPath(Shape s) {
    	PathIterator path = s.getPathIterator(null);
    	Path swtPath = null;
    	
    	if(path instanceof SWTPathIterator) {
    		swtPath = ((SWTPathIterator)path).getSWTPath();
    	}

    	return swtPath;
    }
    
    /** NOT IMPLEMENTED */
    public void draw(Shape s) {
    	Path swtPath = getSWTPath(s);
    	    	
    	gc.drawPath(swtPath);
    }
    
    /** NOT IMPLEMENTED */
    public void drawGlyphVector(GlyphVector g, float x, float y) {
    }
    
    /** NOT IMPLEMENTED */
    public void fill(Shape s) {
    	Path swtPath = getSWTPath(s);
    	
    	gc.fillPath(swtPath);    	
    }
    
    public Color getBackground() {
        return new swingwt.awt.Color(gc.getBackground());
    }
    
    /** NOT IMPLEMENTED */
    public Composite getComposite() {
        return null;
    }
    
    /** NOT IMPLEMENTED */
    public GraphicsConfiguration getDeviceConfiguration() {
        return null;
    }
    
    /** NOT IMPLEMENTED */
    public FontRenderContext getFontRenderContext() {
        return null;
    }
    
    /** NOT IMPLEMENTED */
    public Paint getPaint() {
        return null;
    }
    
    /** NOT IMPLEMENTED */
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return null;
    }
    
    /** NOT IMPLEMENTED */
    public RenderingHints getRenderingHints() {
        return new RenderingHints(null);
    }
    
    /** NOT IMPLEMENTED */
    public Stroke getStroke() {
    	return stroke;
    }
    
    public AffineTransform getTransform() {
    	Transform gTrans = new Transform(SwingWTUtils.getDisplay()); 
    	gc.getTransform(gTrans);
    	return new AffineTransform(gTrans);
    }
    
    /** NOT IMPLEMENTED */
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        return false;
    }
    
    /** NOT IMPLEMENTED */
    public void rotate(double theta) {
    }
    
    /** NOT IMPLEMENTED */
    public void rotate(double theta, double x, double y) {
    }
    
    /** NOT IMPLEMENTED */
    public void scale(double sx, double sy) {
    }
    
    public void setBackground(final swingwt.awt.Color color) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.setBackground(color.getSWTColor());
            }
        });
    }
    
    /** NOT IMPLEMENTED */
    public void setComposite(Composite comp) {
    }
    
    /** NOT IMPLEMENTED */
    public void setPaint(Paint paint) {
    }
    
    /** NOT IMPLEMENTED */
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
    }
    
    /** NOT IMPLEMENTED */
    public void setRenderingHints(Map hints) {
    }
    
    public void setStroke(Stroke s) {
    	stroke = s;
    	
    	if(!(s instanceof BasicStroke)) {
    		return;
    	}
    	
    	BasicStroke bs = (BasicStroke)s;

    	// Setup the line width
    	gc.setLineWidth((int)bs.width);

    	// Setup the line cap
    	int gcCap = SWT.CAP_SQUARE;
    	switch( bs.cap ){
    	case BasicStroke.CAP_BUTT:
    		gcCap = SWT.CAP_FLAT;
    		break;
    	case BasicStroke.CAP_ROUND:
    		gcCap = SWT.CAP_ROUND;
    		break;
    	case BasicStroke.CAP_SQUARE:
    		gcCap = SWT.CAP_SQUARE;
    		break;    		
    	}
    	gc.setLineCap(gcCap);
    	
    	// Setup the line Join
    	int gcJoin = SWT.JOIN_MITER;
    	switch(bs.join) {
    	case BasicStroke.JOIN_BEVEL:
    		gcJoin = SWT.JOIN_BEVEL;
    		break;
    	case BasicStroke.JOIN_MITER:
    		gcJoin = SWT.JOIN_MITER;
    		break;
    	case BasicStroke.JOIN_ROUND:
    		gcJoin = SWT.JOIN_ROUND;
    	}  
    	gc.setLineJoin(gcJoin);
    }
    
    public void setTransform(AffineTransform Tx) {
    	Transform trans = Tx.getSWTTransform();
    	gc.setTransform(trans);
    }
    
    /** NOT IMPLEMENTED */
    public void shear(double shx, double shy) {
    }
    
    public void transform(AffineTransform Tx) {
    	Transform trans = Tx.getSWTTransform();
    	Transform gTrans = new Transform(SwingWTUtils.getDisplay());

    	// I think this is right but I'm not sure
    	// it might be just the opossite
    	gTrans.multiply(trans);
    	gc.setTransform(gTrans);
    }
    
    public void translate(double tx, double ty) {
    	Transform trans = new Transform(SwingWTUtils.getDisplay());
    	gc.getTransform(trans);
    	trans.translate((float)tx,(float)ty);
    	gc.setTransform(trans);
    }
    
    public void translate(int x, int y) {
    	Transform trans = new Transform(SwingWTUtils.getDisplay());
    	gc.getTransform(trans);
    	trans.translate((float)x,(float)y);
    	gc.setTransform(trans);
    }
    
    /** NOT IMPLEMENTED */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
    }
    
    /** NOT IMPLEMENTED */
    public Shape getClip() {
        return null;
    }
    
    public Rectangle getClipBounds() {
        final swingwt.awt.Rectangle r = new swingwt.awt.Rectangle();
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                org.eclipse.swt.graphics.Rectangle rc = gc.getClipping();
                r.height = rc.height;
                r.width = rc.width;
                r.x = rc.x;
                r.y = rc.y;
            }
        });
        return r;
    }
    
    public FontMetrics getFontMetrics(Font f) {
        return new swingwt.awt.FontMetrics(f);
    }
    
    /** NOT IMPLEMENTED */
    public void setClip(Shape clip) {
    }
    
    public void setClip(final int x, final int y, final int width, final int height) {
        swingwtx.swing.SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                gc.setClipping(x, y, width, height);
            }
        });
    }
    
    /** NOT IMPLEMENTED */
    public void setPaintMode() {
    }
    
    /** NOT IMPLEMENTED */
    public void setXORMode(Color c1) {
    }
    
    /**
     * Helper method to create SWT point arrays out of separate coordinates
     * arrays commonly used in Swing. It constructs an array by taking the
     * even elements from a1 and the odd ones from a2, in the order they are.
     * 
     * @author Silvio Moioli
     * @param a1 array for the even elements
     * @param a2 array for the odd elements
     * @param n length of a1 and a2
     * @return an "swt points array" with x coordinates in even elements and y
     * coordinates in odd ones.
     * */
    private int[] mergeArrays(int[] a1, int[]a2, int n){
        int len = n*2;
        int[] result = new int[len];
        int j=0;//result index
        for(int i=0; i<n; i++){
            result[j]=a1[i];
            result[j+1]=a2[i];
            j+=2;
        }
        return result;
    }
}
