/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwtx.swing;

import swingwt.awt.event.*;

/**
 * @author Laurent Martell
 */
public class ToolTipManager extends MouseAdapter implements MouseMotionListener  {

    final static ToolTipManager sharedInstance = new ToolTipManager();

    ToolTipManager() {
    }

    boolean enabled;
    public void setEnabled(boolean flag) {
        enabled = flag;
    }
    public boolean isEnabled() {
        return enabled;
    }

    protected boolean lightWeightPopupEnabled;
    protected boolean heavyWeightPopupEnabled;
    public void setLightWeightPopupEnabled(boolean enabled){
        lightWeightPopupEnabled = enabled;
    }
    public boolean isLightWeightPopupEnabled() { 
        return lightWeightPopupEnabled;
    }

    String toolTipText;

    Timer initialTimer = new Timer(1000,new insideTimerAction());
    public void setInitialDelay(int milliseconds) {
        initialTimer.setInitialDelay(milliseconds);
    }
    public int getInitialDelay() {
        return initialTimer.getInitialDelay();
    }

    Timer dismissTimer = new Timer(1000,new outsideTimerAction());
    public void setDismissDelay(int milliseconds) {
        dismissTimer.setInitialDelay(milliseconds);
    }
    public int getDismissDelay() {
        return dismissTimer.getInitialDelay();
    }

    Timer reshowTimer = new Timer(3000,new stillInsideTimerAction());
    public void setReshowDelay(int milliseconds) {
        reshowTimer.setInitialDelay(milliseconds);
    }
    public int getReshowDelay() {
        return reshowTimer.getInitialDelay();
    }

    public static ToolTipManager sharedInstance() {
        return sharedInstance;
    }

    public void registerComponent(JComponent component) { /* TODO */ }
    public void unregisterComponent(JComponent component) { /* TODO */ }

    public void mouseEntered(MouseEvent event) { /* TODO */ }
    public void mouseExited(MouseEvent event) { /* TODO */ }
    public void mousePressed(MouseEvent event) { /* TODO */ }
    public void mouseDragged(MouseEvent event) { /* TODO */ }
    public void mouseMoved(MouseEvent event) { /* TODO */ }

    protected class insideTimerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) { /* TODO */ }
    }

    protected class outsideTimerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) { /* TODO */ }
    }

    protected class stillInsideTimerAction implements ActionListener {
        public void actionPerformed(ActionEvent e) { /* TODO */ }
    }
}
