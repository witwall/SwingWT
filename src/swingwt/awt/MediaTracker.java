/*
   SwingWT
   Copyright(c)2003-2008, Robin Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
*/

package swingwt.awt;

/**
 * Basic MediaTracker support. Note that this is really
 * a compatibility class, since SwingWT image IO is blocking
 * and the request to load an image does not return until
 * the image is loaded.
 *
 * @author  Robin Rawson-Tetley
 */
public class MediaTracker {

    public static final int LOADING = 1;
    public static final int ABORTED = 2;
    public static final int ERRORED = 4;
    public static final int COMPLETE = 8;
    
    public MediaTracker(Component comp) {}

    public void addImage(Image image, int id) {
    }

    public void addImage(Image image, int id, int w, int h) {
    }

    public boolean checkAll() {
	return true;
    }

    public boolean checkAll(boolean load) {
	return true;
    }

    private boolean checkAll(boolean load, boolean verify) {
	return true;
    }

    public boolean isErrorAny() {
	return false;
    }

    public synchronized Object[] getErrorsAny() {
	return null;
    }

    public void waitForAll() throws InterruptedException {
    }

    public boolean waitForAll(long ms) throws InterruptedException {
        return true;
    }

    public int statusAll(boolean load) {
	return COMPLETE;
    }

    
    public boolean checkID(int id, boolean load) {
	return true;
    }

    
    public boolean isErrorID(int id) {
	return false;
    }

    public Object[] getErrorsID(int id) {
	return null;
    }

    public void waitForID(int id) throws InterruptedException {
    }

    public boolean waitForID(int id, long ms) throws InterruptedException {
	return true;
    }

    public int statusID(int id, boolean load) {
	return COMPLETE;
    }

    public void removeImage(Image image) {
    }

    public void removeImage(Image image, int id) {
    }

    public void removeImage(Image image, int id, int width, int height) {
    }

}
