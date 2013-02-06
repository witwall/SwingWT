/*
   SwingWT
   Copyright(c)2003-2008 Daniel Naab

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: dannaab@users.sourceforge.net

*/

package swingwt.awt.font;

/**
 * @author  Naab
 * @version %I%, %G%
 */
public class TextHitInfo
{
    private int charIndex;
    private boolean isLeadingEdge;

    public static TextHitInfo leading(int charIndex) { return new TextHitInfo(charIndex, true); }
    public static TextHitInfo trailing(int charIndex) { return new TextHitInfo(charIndex, false); }
    public static TextHitInfo beforeOffset(int offset) { return new TextHitInfo(offset - 1, false); }
    public static TextHitInfo afterOffset(int offset) { return new TextHitInfo(offset, true); }

    private TextHitInfo(int charIndex, boolean isLeadingEdge)
    {
        this.charIndex = charIndex;
        this.isLeadingEdge = isLeadingEdge;
    }

    public TextHitInfo getOffsetHit(int delta) { return new TextHitInfo(charIndex + delta, isLeadingEdge); }

    public int getCharIndex() { return charIndex; }
    public boolean isLeadingEdge() { return isLeadingEdge; }

    public int getInsertionIndex()
    {
        int insertionIndex = charIndex;
        if (!isLeadingEdge) insertionIndex++;

        return insertionIndex;
    }

    public TextHitInfo getOtherHit()
    {
        TextHitInfo textHitInfo;

        if (isLeadingEdge)
            textHitInfo = trailing(charIndex - 1);
        else
            textHitInfo = leading(charIndex + 1);

        return textHitInfo;
    }

    public int hashCode() { return charIndex; }
    public boolean equals(Object obj)
    {
        boolean equals = false;

        if ( (obj != null) &&
             (obj instanceof TextHitInfo) &&
             (charIndex == ((TextHitInfo)obj).charIndex) &&
             (isLeadingEdge == ((TextHitInfo)obj).isLeadingEdge) )
            equals = true;

        return equals;
    }
}
