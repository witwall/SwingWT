/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwt.awt.image;

public interface ImageProducer {
    public void addConsumer(ImageConsumer ic);
    public boolean isConsumer(ImageConsumer ic);
    public void removeConsumer(ImageConsumer ic);
    public void startProduction(ImageConsumer ic);
    public void requestTopDownLeftRightResend(ImageConsumer ic);
}
