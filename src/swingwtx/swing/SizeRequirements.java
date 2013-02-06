/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */
package swingwtx.swing;

public class SizeRequirements {
    
    public int minimum;
    public int preferred;
    public int maximum;
    public float alignment;
    
    public SizeRequirements() {
        minimum = 0;
        preferred = 0;
        maximum = 0;
        alignment = (float) .5;
    }
    
    public SizeRequirements(int min, int pref, int max, float a) {
        minimum = min;
        preferred = pref;
        maximum = max;
        if (a > 1)
            alignment = 1;
        else if (a < 0)
            alignment = 0;
        else
            alignment = a;
    }

    
    public static SizeRequirements getTiledSizeRequirements(SizeRequirements[] children) {
        SizeRequirements total = new SizeRequirements();
        for (int i = 0; i < children.length; i++) {
            SizeRequirements req = children[i];
            total.minimum = (int) Math.min((long) total.minimum + (long) req.minimum, Integer.MAX_VALUE);
            total.preferred = (int) Math.min((long) total.preferred + (long) req.preferred, Integer.MAX_VALUE);
            total.maximum = (int) Math.min((long) total.maximum + (long) req.maximum, Integer.MAX_VALUE);
        }
        return total;
    }
    public static SizeRequirements getAlignedSizeRequirements(SizeRequirements[] children) {
        SizeRequirements totalAscent = new SizeRequirements();
        SizeRequirements totalDescent = new SizeRequirements();
        for (int i = 0; i < children.length; i++) {
            SizeRequirements req = children[i];
            
            int ascent = (int) (req.alignment * req.minimum);
            int descent = req.minimum - ascent;
            totalAscent.minimum = Math.max(ascent, totalAscent.minimum);
            totalDescent.minimum = Math.max(descent, totalDescent.minimum);
            
            ascent = (int) (req.alignment * req.preferred);
            descent = req.preferred - ascent;
            totalAscent.preferred = Math.max(ascent, totalAscent.preferred);
            totalDescent.preferred = Math.max(descent, totalDescent.preferred);
            
            ascent = (int) (req.alignment * req.maximum);
            descent = req.maximum - ascent;
            totalAscent.maximum = Math.max(ascent, totalAscent.maximum);
            totalDescent.maximum = Math.max(descent, totalDescent.maximum);
        }
        int min = (int) Math.min((long) totalAscent.minimum + (long) totalDescent.minimum, Integer.MAX_VALUE);
        int pref = (int) Math.min((long) totalAscent.preferred + (long) totalDescent.preferred, Integer.MAX_VALUE);
        int max = (int) Math.min((long) totalAscent.maximum + (long) totalDescent.maximum, Integer.MAX_VALUE);
        float alignment = 0.0f;
        if (min > 0) {
            alignment = (float) totalAscent.minimum / min;
            alignment = alignment > 1 ? 1 : alignment < 0 ? 0 : alignment;
        }
        return new SizeRequirements(min, pref, max, alignment);
    }
    public static void calculateTiledPositions(int allocated,
                                                SizeRequirements total,
                                                SizeRequirements[] children,
                                                int[] offsets,
                                                int[] spans) {
        calculateTiledPositions(allocated, total, children, offsets, spans, true);
    }
    public static void calculateTiledPositions(int allocated,
                                                SizeRequirements total,
                                                SizeRequirements[] children,
                                                int[] offsets,
                                                int[] spans,
                                                boolean forward) {
        long min = 0;
        long pref = 0;
        long max = 0;
        for (int i = 0; i < children.length; i++) {
            min += children[i].minimum;
            pref += children[i].preferred;
            max += children[i].maximum;
        }
        if (allocated >= pref) {
            
            float totalPlay = Math.min(allocated - pref, max - pref);
            float factor = (max - pref == 0) ? 0 : totalPlay / (max - pref);
            int totalOffset;

            if( forward ) {
                totalOffset = 0;
                for (int i = 0; i < spans.length; i++) {
                    offsets[i] = totalOffset;
                    SizeRequirements req = children[i];
                    int play = (int)(factor * (req.maximum - req.preferred));
                    spans[i] = (int) Math.min((long) req.preferred + (long) play, Integer.MAX_VALUE);
                    totalOffset = (int) Math.min((long) totalOffset + (long) spans[i], Integer.MAX_VALUE);
                }
            } else {
                totalOffset = allocated;
                for (int i = 0; i < spans.length; i++) {
                    SizeRequirements req = children[i];
                    int play = (int)(factor * (req.maximum - req.preferred));
                    spans[i] = (int) Math.min((long) req.preferred + (long) play, Integer.MAX_VALUE);
                    offsets[i] = totalOffset - spans[i];
                    totalOffset = (int) Math.max((long) totalOffset - (long) spans[i], 0);
                }
            }
            
            
        } else {
            
            float totalPlay = Math.min(pref - allocated, pref - min);
            float factor = (pref - min == 0) ? 0 : totalPlay / (pref - min);
            int totalOffset;
            if( forward ) {
                totalOffset = 0;
                for (int i = 0; i < spans.length; i++) {
                    offsets[i] = totalOffset;
                    SizeRequirements req = children[i];
                    float play = factor * (req.preferred - req.minimum);
                    spans[i] = (int)(req.preferred - play);
                    totalOffset = (int) Math.min((long) totalOffset + (long) spans[i], Integer.MAX_VALUE);
                }
            } else {
                totalOffset = allocated;
                for (int i = 0; i < spans.length; i++) {
                    SizeRequirements req = children[i];
                    float play = factor * (req.preferred - req.minimum);
                    spans[i] = (int)(req.preferred - play);
                    offsets[i] = totalOffset - spans[i];
                    totalOffset = (int) Math.max((long) totalOffset - (long) spans[i], 0);
                }
            }
            
        }
    }

    public static void calculateAlignedPositions(int allocated,
                                                SizeRequirements total,
                                                SizeRequirements[] children,
                                                int[] offsets,
                                                int[] spans) {
        calculateAlignedPositions( allocated, total, children, offsets, spans, true );
    }
    
    public static void calculateAlignedPositions(int allocated,
                                                SizeRequirements total,
                                                SizeRequirements[] children,
                                                int[] offsets,
                                                int[] spans,
                                                boolean normal) {
        for (int i = 0; i < children.length; i++) {
            SizeRequirements req = children[i];
            float alignment = normal ? req.alignment : 1 - req.alignment;
            spans[i] = Math.min(req.maximum, allocated);
            offsets[i] = (int) ((allocated - spans[i]) * alignment);
            if (offsets[i] < 0) {
                spans[i] += offsets[i];
                offsets[i] = 0;
            } else if (offsets[i] + spans[i] > allocated) {
                spans[i] = allocated - offsets[i];
            }
        }
    }
    
    public static int[] adjustSizes(int delta, SizeRequirements[] children) {
        return new int[0];
    }
    
        
    public String toString() {
        return "[" + minimum + "," + preferred + "," + maximum + "]@" + alignment;
    }
}
