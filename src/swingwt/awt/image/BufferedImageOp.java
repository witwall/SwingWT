/**
 * SwingWT
 *
 * This program is dual licenced under the terms of the Common Public 
 * License v1.0 and the GNU Lesser Public License (LGPL) version 2 or
 * later. Please choose whichever is most applicable to you.
 */
package swingwt.awt.image;

import swingwt.awt.RenderingHints;
import swingwt.awt.geom.Point2D;
import swingwt.awt.geom.Rectangle2D;


/**
 * TODO Comment!!
 * 
 * @author Thiago Tonelli Bartolomei
 */
public interface BufferedImageOp {
    public BufferedImage filter(BufferedImage src, BufferedImage dest);
    public Rectangle2D getBounds2D (BufferedImage src);
    public BufferedImage createCompatibleDestImage (BufferedImage src, ColorModel destCM);
    public Point2D getPoint2D (Point2D srcPt, Point2D dstPt);
    public RenderingHints getRenderingHints();

}
