package swingwt.awt.image;

import java.util.Vector;

import swingwt.awt.Rectangle;

public interface RenderedImage {
	 WritableRaster 	copyData(WritableRaster raster);
	 ColorModel 	getColorModel();
	 Raster 	getData();
	 Raster 	getData(Rectangle rect);
	 int 	getHeight();
	 int 	getMinTileX();
	 int 	getMinTileY();
	 int 	getMinX();
	 int 	getMinY();
	 int 	getNumXTiles();
	 int 	getNumYTiles();
	 Object 	getProperty(String name);
	 String[] 	getPropertyNames();
	 SampleModel 	getSampleModel();
	 Vector 	getSources();
	 Raster 	getTile(int tileX, int tileY);
	 int 	getTileGridXOffset();
	 int 	getTileGridYOffset();
	 int 	getTileHeight();
	 int 	getTileWidth();
	 int 	getWidth();
}
