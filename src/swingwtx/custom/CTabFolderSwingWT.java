/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.custom;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;

/**
 * The CTabFolder has problems with tooltips in the context
 * with which we use it in SwingWT (it probably doesn't matter
 * for Eclipse) - this subclass prevents it displaying any
 * tooltips.
 */
public class CTabFolderSwingWT extends CTabFolder {

    public CTabFolderSwingWT(Composite parent, int style) {
        super(parent, style);
    }

    public void setToolTipText(String tip) {
	super.setToolTipText(null);
    }
}
