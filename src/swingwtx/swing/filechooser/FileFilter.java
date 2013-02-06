/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/

package swingwtx.swing.filechooser;

import java.io.*;

public abstract class FileFilter {
    public abstract boolean accept(File f);
    public abstract String getDescription(); 
}
