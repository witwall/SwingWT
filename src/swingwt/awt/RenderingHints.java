/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt;

import java.util.*;

public class RenderingHints implements Map {

	public static class Key{
		int key;
		
		protected Key(int privatekey){
			
		}
		
		public boolean equals(Object o) {
			return this==o;
		}

		protected int intKey() {
			return key;
		}
		
		public int hashCode() {
			return System.identityHashCode(this);
		}
		
	}
	
    HashMap hintmap = new HashMap();

    public static final Key KEY_ANTIALIASING = new Key(0);
    public static final Object VALUE_ANTIALIAS_ON = 
        "VALUE_ANTIALIAS_ON";
    public static final Object VALUE_ANTIALIAS_OFF =
	"VALUE_ANTIALIAS_OFF";
    public static final Object VALUE_ANTIALIAS_DEFAULT =
	 "VALUE_ANTIALIAS_DEFAULT";
    
    public static final Key KEY_RENDERING = new Key(1);
    public static final Object VALUE_RENDER_SPEED =
	 "VALUE_RENDER_SPEED";
    public static final Object VALUE_RENDER_QUALITY =
	 "VALUE_RENDER_QUALITY";
    public static final Object VALUE_RENDER_DEFAULT =
	 "VALUE_RENDER_DEFAULT";
    
    public static final Key KEY_DITHERING = new Key(2);
    public static final Object VALUE_DITHER_DISABLE =
	 "VALUE_DITHER_DISABLE";
    public static final Object VALUE_DITHER_ENABLE =
	 "VALUE_DITHER_ENABLE";
    public static final Object VALUE_DITHER_DEFAULT =
	 "VALUE_DITHER_DEFAULT";
    
    public static final Key KEY_TEXT_ANTIALIASING =  new Key(3);
    public static final Object VALUE_TEXT_ANTIALIAS_ON =
	 "VALUE_TEXT_ANTIALIAS_ON";
    public static final Object VALUE_TEXT_ANTIALIAS_OFF =
	 "VALUE_TEXT_ANTIALIAS_OFF";
    public static final Object VALUE_TEXT_ANTIALIAS_DEFAULT =
	 "VALUE_TEXT_ANTIALIAS_DEFAULT";
    
    public static final Key KEY_FRACTIONALMETRICS = new Key(4);
    public static final Object VALUE_FRACTIONALMETRICS_OFF =
	 "VALUE_FRACTIONALMETRICS_OFF";
    public static final Object VALUE_FRACTIONALMETRICS_ON =
	 "VALUE_FRACTIONALMETRICS_ON";
    public static final Object VALUE_FRACTIONALMETRICS_DEFAULT =
	 "VALUE_FRACTIONALMETRICS_DEFAULT";

    public static final Key KEY_INTERPOLATION = new Key(5);
    public static final Object VALUE_INTERPOLATION_NEAREST_NEIGHBOR =
	 "VALUE_INTERPOLATION_NEAREST_NEIGHBOR";
    public static final Object VALUE_INTERPOLATION_BILINEAR =
	 "VALUE_INTERPOLATION_BILINEAR";
    public static final Object VALUE_INTERPOLATION_BICUBIC =
	 "VALUE_INTERPOLATION_BICUBIC";

    public static final Key KEY_ALPHA_INTERPOLATION = new Key(6);
    public static final Object VALUE_ALPHA_INTERPOLATION_SPEED =
	 "VALUE_ALPHA_INTERPOLATION_SPEED";
    public static final Object VALUE_ALPHA_INTERPOLATION_QUALITY =
	 "VALUE_ALPHA_INTERPOLATION_QUALITY";
    public static final Object VALUE_ALPHA_INTERPOLATION_DEFAULT =
	 "VALUE_ALPHA_INTERPOLATION_DEFAULT";

    public static final Key KEY_COLOR_RENDERING = new Key(7);
    public static final Object VALUE_COLOR_RENDER_SPEED =
	 "VALUE_COLOR_RENDER_SPEED";
    public static final Object VALUE_COLOR_RENDER_QUALITY =
	 "VALUE_COLOR_RENDER_QUALITY";
    public static final Object VALUE_COLOR_RENDER_DEFAULT =
	 "VALUE_COLOR_RENDER_DEFAULT";

    public static final Key KEY_STROKE_CONTROL = new Key(8);
    public static final Object VALUE_STROKE_DEFAULT =
	"VALUE_STROKE_DEFAULT";
    public static final Object VALUE_STROKE_NORMALIZE =
	"VALUE_STROKE_NORMALIZE";
    public static final Object VALUE_STROKE_PURE =
	"VALUE_STROKE_PURE";

    public RenderingHints(Map init) {
	if (init != null) {
	    hintmap.putAll(init);
	}
    }
    public RenderingHints(Object key, Object value) {
	hintmap.put(key, value);
    }
    public int size() {
	return hintmap.size();
    }
    public boolean isEmpty() {
	return hintmap.isEmpty();
    }
    public boolean containsKey(Object key) {
	return hintmap.containsKey(key);
    }
    public boolean containsValue(Object value) {
	return hintmap.containsValue(value);
    }
    public Object get(Object key) {
	return hintmap.get(key);
    }
    public Object put(Object key, Object value) {
        return hintmap.put(key, value);
    }
    public void add(RenderingHints hints) {
	hintmap.putAll(hints.hintmap);
    }
    public void clear() {
	hintmap.clear();
    }
    public Object remove(Object key) {
	return hintmap.remove(key);
    }
    public void putAll(Map m) {
	if (m instanceof RenderingHints) {
	    hintmap.putAll(((RenderingHints) m).hintmap);
	} else {
	    Iterator iter = m.entrySet().iterator();
	    while (iter.hasNext()) {
		Map.Entry entry = (Map.Entry) iter.next();
		put(entry.getKey(), entry.getValue());
	    }
	}
    }
    public Set keySet() {
	return hintmap.keySet();
    }
    public Collection values() {
	return hintmap.values();
    }
    public Set entrySet() {
	return Collections.unmodifiableMap(hintmap).entrySet();
    }
    public int hashCode() {
	return hintmap.hashCode();
    }
}

