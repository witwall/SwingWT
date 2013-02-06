/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/
package swingwtx.swing.text;

public interface Position {

    public int getOffset();

    public static final class Bias {
	public static final Bias Forward = new Bias("Forward");
	public static final Bias Backward = new Bias("Backward");
        public String toString() {
	    return name;
	}
        private Bias(String name) {
	    this.name = name;
	}
	private String name;
    }
}
