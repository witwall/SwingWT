/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net
 
*/

package swingwtx.imageio;

import swingwt.awt.image.*;
import swingwtx.swing.*;

import java.net.*;
import java.io.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

/**
 * Incomplete ImageIO support
 *
 * @author  Naab
 * @version %I%, %G%
 */
public class ImageIO
{
    private static String[] knownImageFormats = new String[] {"bmp", "ico", "jpg", "jpeg", "gif", "png"};
    
    /**
     * Return hardcoded list of known SWT format types.  I'm not aware of a SWT method to return this,
     * but I expect that one exists.
     */
    public static String[] getReaderFormatNames()
    {
        return knownImageFormats;
    }
    
    public static BufferedImage read(InputStream input) throws IOException {
        BufferedImage im = new BufferedImage(new org.eclipse.swt.graphics.Image(SwingWTUtils.getDisplay(), input));
        return im;
    }

    public static BufferedImage read(File file) throws IOException {
        return read(new FileInputStream(file));
    }

    public static BufferedImage read(URL input) throws IOException {
        return read(input.openStream());
    }

     
	public static void write(BufferedImage image, String formatName, OutputStream output) throws IOException {
        if (output == null) {
            throw new IllegalArgumentException("output == null!");
        }
	    ImageLoader loader = new ImageLoader();
	    loader.data = new ImageData[] {image.getSWTImage().getImageData()};
	    loader.save(output, getFormat(formatName));
	}
	
	public static void write(BufferedImage image, String formatName, File output) throws IOException {
		write(image,formatName,new FileOutputStream(output));
	}
	
	private static int getFormat(String format)
	{
		if(format.equalsIgnoreCase("dib")||format.equalsIgnoreCase("dib"))
		{
			return SWT.IMAGE_BMP;
		}else if(format.equalsIgnoreCase("jpeg")||format.equalsIgnoreCase("jpg"))
		{
			return SWT.IMAGE_JPEG;
		}else if(format.equalsIgnoreCase("rle"))
		{
			return SWT.IMAGE_BMP_RLE;
		}else if(format.equalsIgnoreCase("ico"))
		{
			return SWT.IMAGE_ICO;
		}else if(format.equalsIgnoreCase("png"))
		{
			return SWT.IMAGE_PNG;
		}else if(format.equalsIgnoreCase("gif"))
		{
			return SWT.IMAGE_GIF;
		}else if(format.equalsIgnoreCase("tif")||format.equalsIgnoreCase("tiff"))
		{
			return SWT.IMAGE_TIFF;
		}
		else
			return SWT.IMAGE_UNDEFINED;
/*		switch(format.toLowerCase())
		{
			case "dib":
			case "bmp":return SWT.IMAGE_BMP;
			case "rle":return SWT.IMAGE_BMP_RLE;
			case "ico": return SWT.IMAGE_ICO;
			case "jpeg":
			case "jpg":return SWT.IMAGE_JPEG;
			case "png":return SWT.IMAGE_PNG;
			case "gif":return SWT.IMAGE_GIF;
			case "tif":
			case "tiff":return SWT.IMAGE_TIFF;
		
		}*/
		
	}
	
	    
}
