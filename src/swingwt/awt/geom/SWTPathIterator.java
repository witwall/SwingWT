package swingwt.awt.geom;

import org.eclipse.swt.graphics.Path;

public class SWTPathIterator implements PathIterator {
	Path swtPath;
	
	public SWTPathIterator(Path swtPath){
		this.swtPath = swtPath;
	}
	
	public Path getSWTPath() {
		return swtPath;
	}
	
	public int getWindingRule() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isDone() {
		// TODO Auto-generated method stub
		return false;
	}

	public void next() {
		// TODO Auto-generated method stub

	}

	public int currentSegment(float[] coords) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int currentSegment(double[] coords) {
		// TODO Auto-generated method stub
		return 0;
	}

}
