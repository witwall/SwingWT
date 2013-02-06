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
}
