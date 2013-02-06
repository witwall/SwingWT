package swingwtx.swing;

import java.io.*;
import swingwt.awt.*;


//TODO: implement the monitor
public class ProgressMonitorInputStream extends FilterInputStream
{
	private ProgressMonitor monitor = null;
	
    public ProgressMonitorInputStream(Component parentComponent,
                                      Object message,
                                      InputStream in) 
    {
        super(in);
   }
    public ProgressMonitor getProgressMonitor() {
        return monitor;
    }
    public int read() throws IOException 
    {
        int c = in.read();
        return c;
    }
    public int read(byte b[]) throws IOException 
    {
        int nr = in.read(b);
        return nr;
    }
    public int read(byte b[],int off,int len) throws IOException 
                    {
        int nr = in.read(b, off, len);
        return nr;
    }
    public long skip(long n) throws IOException 
    {
        long nr = in.skip(n);
        return nr;
    }
    public void close() throws IOException 
    {
        in.close();
    }
    public synchronized void reset() throws IOException 
    {
        in.reset();
    }
}
