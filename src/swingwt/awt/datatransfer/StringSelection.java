package swingwt.awt.datatransfer;

import java.io.IOException;

public class StringSelection implements Transferable, ClipboardOwner
{

    public StringSelection(String text)
    {
        // TODO 
    }

    public DataFlavor[] getTransferDataFlavors()
    {
        // TODO
        return null;
    }

    public boolean isDataFlavorSupported(DataFlavor flavor)
    {
        // TODO
        return false;
    }

    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException
    {
        // TODO
        return null;
    }

    public void lostOwnership(Clipboard clipboard, Transferable contents)
    {
        // TODO
    }

}
