/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.tree;

import swingwtx.swing.*;
import swingwt.awt.*;

public class DefaultTreeCellRenderer extends JLabel implements TreeCellRenderer {
    
    protected boolean hasFocus;
    protected boolean selected;
    
    public Component getTreeCellRendererComponent(swingwtx.swing.JTree tree, 
            Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
         
         this.hasFocus = hasFocus;
         this.selected = selected;
                
         // Default tree images
         if (leaf)
             if (expanded)
                setIcon(openIcon);
             else
                setIcon(closedIcon);
         else
             setIcon(leafIcon);
         
         setText(value.toString());
         return this;
    }
    
    public Color getTextSelectionColor() { return SystemColor.controlHighlight; }
    public Color getTextNonSelectionColor() { return SystemColor.controlText; }

    Icon leafIcon = SwingWTUtils.getPixmap(DefaultTreeCellRenderer.class, "doc.gif");
    Icon openIcon = SwingWTUtils.getPixmap(DefaultTreeCellRenderer.class, "folderopen.gif");
    Icon closedIcon = SwingWTUtils.getPixmap(DefaultTreeCellRenderer.class, "folder.gif");
	    
    public Icon getLeafIcon() { return leafIcon; }
    public Icon getOpenIcon() { return openIcon; }
    public Icon getClosedIcon() { return closedIcon; }

    public void setLeafIcon(Icon icon) { leafIcon = icon; }
    public void setOpenIcon(Icon icon) { openIcon = icon; }
    public void setClosedIcon(Icon icon) { closedIcon = icon; }

}
