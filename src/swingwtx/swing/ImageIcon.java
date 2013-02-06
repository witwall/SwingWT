/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwtx.swing;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.swt.graphics.ImageData;

import swingwt.awt.Component;
import swingwt.awt.Graphics;
import swingwt.awt.Image;
import swingwt.awt.MediaTracker;
import swingwt.awt.image.ImageObserver;


public class ImageIcon implements Icon {
    
    private String description = null;
    private swingwt.awt.Image image = null;
    
    private int imageLoadStatus = 0;
    
    /** Creates an empty imageicon */
    public ImageIcon() {
        this.image = new swingwt.awt.Image();
    }
    
    public ImageIcon(swingwt.awt.Image image) {   
        this.image = image;
    }
    
    public ImageIcon(swingwt.awt.Image image, String description) {   
        this.image = image;
        setDescription(description);
    }
    
    public ImageIcon(String filename) {
        this(filename, "");
    }
    
    public ImageIcon(String filename, String description) {
        try {
        	FileInputStream fileStream = new FileInputStream(new File(filename));
            image = loadAWTImage(fileStream);
            fileStream.close();
            setDescription(description);    
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public ImageIcon(URL location) {
        if (location != null) {
            try {
                InputStream urlStream = location.openStream();
                image = loadAWTImage(urlStream);
                urlStream.close();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }
    
    public ImageIcon(URL location, String description) {
        this(location);
        setDescription(description);
    }

    public ImageIcon(byte[] data) {
	this(data, "");
    }
     
    public ImageIcon(byte[] data, String description) {
        try {
        	image = loadAWTImage(new ByteArrayInputStream(data));
            setDescription(description);
        } 
	catch (Exception e) { 
	     e.printStackTrace(); 
	}
    }
				
    
    public static Image loadAWTImage(InputStream stream) {
        Image image = new swingwt.awt.Image();
        ImageData swtImageData = new ImageData(stream);
        if(swtImageData.transparentPixel != -1) {
        	// the image has a transparentPixel
        	// at least on some versions of gtk with cairo these
        	// types of image aren't handled correctly so we convert
        	// it to an image with alphaData
        	swtImageData.alphaData = new byte [swtImageData.width*swtImageData.height];
        	int alphaIndex = 0;
        	for(int y=0; y<swtImageData.height; y++) {
        		for(int x=0; x<swtImageData.width; x++) {
        			int pixel = swtImageData.getPixel(x,y);
        			if(pixel == swtImageData.transparentPixel) {
        				swtImageData.alphaData[alphaIndex] = 0;
        			} else {
        				swtImageData.alphaData[alphaIndex] = (byte)255;
        			}
        			alphaIndex++;
        		}
        	}
        	swtImageData.transparentPixel = -1;
        }
        image.image = new org.eclipse.swt.graphics.Image(SwingWTUtils.getDisplay(), swtImageData);
        return image;
    }
    
    /** Getter for property description.
     * @return Value of property description.
     *
     */
    public java.lang.String getDescription() {
        return description;
    }    
    
    /** Setter for property description.
     * @param description New value of property description.
     *
     */
    public void setDescription(java.lang.String description) {
        this.description = description;
    }    
    
    /** Getter for property image.
     * @return Value of property image.
     *
     */
    public swingwt.awt.Image getImage() {
        return image;
    }    
        
    /** Setter for property image.
     * @param image New value of property image.
     *
     */
    public void setImage(swingwt.awt.Image image) {
        this.image = image;
    }    

    public int getIconHeight() {
        if (image == null) return 0;
        if (image.image == null) return 0;
        return image.image.getBounds().height;
    }
    
    public int getIconWidth() {
        if (image == null) return 0;
        if (image.image == null) return 0;
        return image.image.getBounds().width;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (image != null)
            g.drawImage(image, x, y, null);
    }
    public int getImageLoadStatus()
    {
    	return MediaTracker.COMPLETE;
    }
    
    /**
     * 
     * TODO Comment!!
     *
     * @return
     */
    public ImageObserver getImageObserver() {
        return null;
    }
}
