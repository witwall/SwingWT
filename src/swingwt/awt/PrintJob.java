package swingwt.awt;


public abstract class PrintJob 
{
    public abstract Graphics getGraphics();
    public abstract Dimension getPageDimension();
    
    public abstract int getPageResolution();
    public abstract boolean lastPageFirst();
    
    public abstract void end();
    // i Think this must execute end at finalize
    public void finalize() 
    {
    	end();
    }

}
