/*
   SwingWT
   Copyright(c)2004, Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/
package swingwt.awt;

import java.util.Set;

import swingwt.awt.event.ActionListener;
import swingwt.awt.event.ComponentListener;
import swingwt.awt.event.FocusListener;
import swingwt.awt.event.InputMethodListener;
import swingwt.awt.event.KeyListener;
import swingwt.awt.event.MouseListener;
import swingwt.awt.event.MouseMotionListener;
import swingwt.awt.peer.ComponentPeer;

import swingwtx.accessibility.AccessibleContext;
import swingwtx.swing.JComponent;

import org.eclipse.swt.widgets.Control;

/**
 * Provides convenience functionality for implementing an AWT component by wrapping around
 * the corresponding Swing version.
 * 
 * @author Dan
 */
public class AWTSwingWrapper extends swingwt.awt.Component {

    protected JComponent swingPeer = null;

        public void setSwingWTParent(swingwt.awt.Container parent) throws Exception { swingPeer.setSwingWTParent(parent); swingPeer.registerEvents(); }
	public Control getSWTPeer() { return swingPeer.getSWTPeer(); }
	public ComponentPeer getPeer() { return swingPeer.getPeer(); }
	public void show() { swingPeer.show(); }
	public void hide() { swingPeer.hide(); }
	public void setVisible(boolean b) { swingPeer.setVisible(b); }
	public boolean isVisible() { return swingPeer.isVisible(); }
	public boolean isShowing() { return swingPeer.isShowing(); }
	public void setEnabled(boolean b) { swingPeer.setEnabled(b); }
	public boolean isEnabled() { return swingPeer.isEnabled(); }
	public void requestFocus() { swingPeer.requestFocus(); }
	public void grabFocus() { swingPeer.grabFocus(); }
	public void repaint(int x, int y, int width, int height) { swingPeer.repaint(x,y,width,height); }
	public void repaint(long tm, int x, int y, int width, int height) { swingPeer.repaint(tm,x,y,width,height); }
	public void repaint() { swingPeer.repaint(); }
	public swingwt.awt.Color getBackground() { return swingPeer.getBackground(); }
	public void setBackground(swingwt.awt.Color c) { swingPeer.setBackground(c); }
	public swingwt.awt.Color getForeground() { return swingPeer.getForeground(); }
	public void setForeground(swingwt.awt.Color c) { swingPeer.setForeground(c); }
	public int getWidth() { return swingPeer.getWidth(); }
	public int getHeight() { return swingPeer.getHeight(); }
	public void setWidth(int width) { swingPeer.setWidth(width); }
	public void setHeight(int height) { swingPeer.setHeight(height); }
	public Graphics getGraphics() { return swingPeer.getGraphics(); }
	public Dimension getPreferredSize() { return swingPeer.getPreferredSize(); }
	protected Dimension computePreferredSize() { return ((Component)swingPeer).computePreferredSize(); }
	public void setPreferredSize(Dimension d) { swingPeer.setPreferredSize(d); }
	protected Dimension calculatePreferredSize() { return swingPeer.calculatePreferredSize(); }
	public Dimension getMaximumSize() { return swingPeer.getMaximumSize(); }
	public void setMaximumSize(Dimension d) { swingPeer.setMaximumSize(d); }
	public Dimension getMinimumSize() { return swingPeer.getMinimumSize(); }
	public void setMinimumSize(Dimension d) { swingPeer.setMinimumSize(d); }
	public boolean isMinimumSizeSet() { return swingPeer.isMinimumSizeSet(); }
	public Dimension minimumSize() { return swingPeer.minimumSize(); }
        public Dimension preferredSize() { return swingPeer.preferredSize(); }
	public void setSize(int width, int height) { swingPeer.setSize(width, height); }
	public void setSize(Dimension d) { swingPeer.setSize(d); }
	public Dimension getSize() { return swingPeer.getSize(); }
	public Dimension getPeerSize() { return swingPeer.getPeerSize(); }
	public void setFocusTraversalKeysEnabled(boolean b) { swingPeer.setFocusTraversalKeysEnabled(b); }
	public void setFocusTraversalKeys(int id,Set keystrokes){swingPeer.setFocusTraversalKeys(id, keystrokes);}
	public Point getLocation() { return swingPeer.getLocation(); }
	public Point getLocationOnScreen() { return swingPeer.getLocationOnScreen(); }
	public Image createImage(int width, int height) { return swingPeer.createImage(width, height); }
	public swingwt.awt.image.VolatileImage createVolatileImage(int width, int height) { return swingPeer.createVolatileImage(width, height); }
	public swingwt.awt.FontMetrics getFontMetrics(swingwt.awt.Font f) { return swingPeer.getFontMetrics(f); }
	public swingwt.awt.Font getFont() { return swingPeer.getFont(); }
	public void setFont(swingwt.awt.Font f) { swingPeer.setFont(f); }
	public boolean hasSetFont() { return swingPeer.hasSetFont(); }
	public String getToolTipText() { return swingPeer.getToolTipText(); }
	public void setToolTipText(String text) { swingPeer.setToolTipText(text); }
	public void setBounds(int x, int y, int width, int height) { swingPeer.setBounds(x,y,width,height); }
	public void setBounds(Rectangle r) { swingPeer.setBounds(r); }
	public Rectangle getBounds() { return swingPeer.getBounds(); }
	public float getAlignmentX() { return swingPeer.getAlignmentX(); }
	public float getAlignmentY() { return swingPeer.getAlignmentY(); }
	public void setAlignmentX(float val) { swingPeer.setAlignmentX(val); }
	public void setAlignmentY(float val) { swingPeer.setAlignmentY(val); }
	public void transferFocus() { swingPeer.transferFocus(); }
	public void transferFocusBackward() { swingPeer.transferFocusBackward(); }
	public void transferFocusUpCycle() { swingPeer.transferFocusUpCycle(); }
	public boolean contains(int x, int y) { return swingPeer.contains(x,y); }
	public void invalidate() { swingPeer.invalidate(); }
	public void validate() { swingPeer.validate(); }
	public String getActionCommand() { return swingPeer.getActionCommand(); }
	public void setActionCommand(String command) { swingPeer.setActionCommand(command); }
	public void setLocation(Point p) { swingPeer.setLocation(p); }
	public void setLocation(int x, int y) { swingPeer.setLocation(x,y); }
	public int getX() { return swingPeer.getX(); }
	public int getY() { return swingPeer.getY(); }
	public Container getParent() { return swingPeer.getParent(); }
	public String getName() { return swingPeer.getName(); }
	public void setName(String newName) { swingPeer.setName(newName); }
	public void setCursor(swingwt.awt.Cursor c) { swingPeer.setCursor(c); }
	public boolean isFocusable() { return swingPeer.isFocusable(); }
	public void setFocusable(boolean b) { swingPeer.setFocusable(b); }
	public swingwt.awt.Cursor getCursor() { return swingPeer.getCursor(); }
	public Toolkit getToolkit() { return swingPeer.getToolkit(); }
	public GraphicsConfiguration getGraphicsConfiguration() { return swingPeer.getGraphicsConfiguration(); }
	public ComponentOrientation getComponentOrientation() { return swingPeer.getComponentOrientation(); }
	public void setComponentOrientation(ComponentOrientation o) { swingPeer.setComponentOrientation(o); }
	public void dispose() { swingPeer.dispose(); }
	public void componentOnlyDispose() { swingPeer.componentOnlyDispose(); }
	public void addActionListener(ActionListener l) { swingPeer.addActionListener(l); }
	public void removeActionListener(ActionListener l) { swingPeer.removeActionListener(l); }
	public void addComponentListener(ComponentListener l) { swingPeer.addComponentListener(l); }
	public void removeComponentListener(ComponentListener l) { swingPeer.removeComponentListener(l); }
	public void addMouseListener(MouseListener l) { swingPeer.addMouseListener(l); }
	public void removeMouseListener(MouseListener l) { swingPeer.removeMouseListener(l); }
	public void addMouseMotionListener(MouseMotionListener l) { swingPeer.addMouseMotionListener(l); }
	public void removeMouseMotionListener(MouseMotionListener l) { swingPeer.removeMouseMotionListener(l); }
	public void addKeyListener(KeyListener l) { swingPeer.addKeyListener(l); }
	public void removeKeyListener(KeyListener l) { swingPeer.removeKeyListener(l); }
	public void addFocusListener(FocusListener l) { swingPeer.addFocusListener(l); }
	public void removeFocusListener(FocusListener l) { swingPeer.removeFocusListener(l); }
	public void addInputMethodListener(InputMethodListener l) { swingPeer.addInputMethodListener(l); }
	public void removeInputMethodListener(InputMethodListener l) { swingPeer.removeInputMethodListener(l); }
	public void dispatchEvent(AWTEvent awtEvent) { swingPeer.dispatchEvent(awtEvent); }
	public void addNotify() { swingPeer.addNotify(); }
	public void removeNotify() { swingPeer.removeNotify(); }
	public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) { return swingPeer.imageUpdate(img, infoflags, x, y, width, height); }
	public boolean hasFocus() { return swingPeer.hasFocus(); }
	public Object getTreeLock() { return swingPeer.getTreeLock(); }
	public AccessibleContext getAccessibleContext() { return swingPeer.getAccessibleContext(); }

}
