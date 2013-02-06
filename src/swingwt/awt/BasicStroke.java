package swingwt.awt;

public class BasicStroke implements Stroke {
	public static final int 	CAP_BUTT 	= 0;
	public static final int 	CAP_ROUND 	= 1;
	public static final int 	CAP_SQUARE 	= 2;
	public static final int 	JOIN_BEVEL 	= 2;
	public static final int 	JOIN_MITER 	= 0;
	public static final int 	JOIN_ROUND 	= 1;
	
	float width;
	int cap;
	int join;
	float miterlimit;
	float [] dash;
	float dash_phase;	
	
	public BasicStroke(float width)
	{
		this(width, CAP_SQUARE, JOIN_MITER, 10f, null, 0);
	}
	
	public BasicStroke(float width, int cap, int join) 
	{
		this(width, cap, join, 10f, null, 0);
	}
	
	public BasicStroke(float width, int cap, 
			int join, float miterlimit, 
			float[] dash, float dash_phase)
	{
		this.width = width;
		this.cap = cap;
		this.join = join;
		this.miterlimit = miterlimit;
		this.dash = dash;
		this.dash_phase = dash_phase;
	}
	
	/**
	 * TODO Comment!!
	 *
	 * @param f
	 * @param capButt
	 * @param joinRound
	 * @param g
	 */
	public BasicStroke(float f, int capButt, int joinRound, float g) {
		// TODO Auto-generated constructor stub
	}

	public Shape createStrokedShape(Shape p) {		
		// I don't really know what to do here.  If we are letting swt handle
		// all the stroking when it draws the path, how are we going to return
		// a stroked shape.  
		return p;
	}

}
