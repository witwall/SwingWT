package swingwt.awt.font;

import swingwt.awt.Font;
import swingwt.awt.Shape;
import swingwt.awt.geom.AffineTransform;
import swingwt.awt.geom.Point2D;
import swingwt.awt.geom.Rectangle2D;

public class SWTGlyphVector extends GlyphVector {
	Font awtFont;

	// It seems like the changes coming swt 3.2 will make this possible
	// right now we just need to create some dummy info
	private float [] glyphPositions = new float[1];
	
	public SWTGlyphVector(Font awtFont){
		this.awtFont = awtFont;
	}
	
	public Font getFont() {
		return awtFont;
	}

	public FontRenderContext getFontRenderContext() {
		// TODO Auto-generated method stub
		return null;
	}

	public void performDefaultLayout() {
		// TODO Auto-generated method stub

	}

	public int getNumGlyphs() {
		// TODO Auto-generated method stub
		return 1;
	}

	public int getGlyphCode(int glyphIndex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int[] getGlyphCodes(int beginGlyphIndex, int numEntries,
			int[] codeReturn) {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle2D getLogicalBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle2D getVisualBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public Shape getOutline() {
		// TODO Auto-generated method stub
		return null;
	}

	public Shape getOutline(float x, float y) {
		// TODO Auto-generated method stub
		return null;
	}

	public Shape getGlyphOutline(int glyphIndex) {
		return new Rectangle2D.Float(0,0,0,0);
	}

	public Point2D getGlyphPosition(int glyphIndex) {
		return new Point2D.Float(0,0);
	}

	public void setGlyphPosition(int glyphIndex, Point2D newPos) {
		// TODO Auto-generated method stub

	}

	public AffineTransform getGlyphTransform(int glyphIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setGlyphTransform(int glyphIndex, AffineTransform newTX) {
		// TODO Auto-generated method stub

	}

	public float[] getGlyphPositions(int beginGlyphIndex, int numEntries,
			float[] positionReturn) {
		return glyphPositions;
	}

	public Shape getGlyphLogicalBounds(int glyphIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public Shape getGlyphVisualBounds(int glyphIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public GlyphMetrics getGlyphMetrics(int glyphIndex) {
		return new GlyphMetrics(0f,new Rectangle2D.Float(0,0,0,0),(byte)0);
	}

	public GlyphJustificationInfo getGlyphJustificationInfo(int glyphIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean equals(GlyphVector set) {
		// TODO Auto-generated method stub
		return false;
	}

}
