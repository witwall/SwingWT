/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwt.awt.event;

import swingwt.awt.AWTEvent;
import swingwt.awt.Component;

import swingwt.awt.font.TextHitInfo;

import java.text.AttributedCharacterIterator;

/**
 * @author Dan Naab
 */
public class InputMethodEvent extends AWTEvent
{
    public static final int INPUT_METHOD_FIRST = 1100;
    public static final int INPUT_METHOD_TEXT_CHANGED = INPUT_METHOD_FIRST;
    public static final int CARET_POSITION_CHANGED = INPUT_METHOD_FIRST + 1;
    public static final int INPUT_METHOD_LAST = INPUT_METHOD_FIRST + 1;

    private AttributedCharacterIterator text;
    private int committedCharacterCount;
    private TextHitInfo caret;
    private TextHitInfo visiblePosition;
    private long when;

    public InputMethodEvent( Component source, int id, AttributedCharacterIterator text, int committedCharacterCount,
                             TextHitInfo caret, TextHitInfo visiblePosition )
    {
        this( source, id, 0, text, committedCharacterCount, caret, visiblePosition );
    }

    public InputMethodEvent( Component source, int id, long when, AttributedCharacterIterator text,
                             int committedCharacterCount, TextHitInfo caret, TextHitInfo visiblePosition )
    {
        super(source, id);
        this.text = text;
        this.committedCharacterCount = committedCharacterCount;
        this.caret = caret;
        this.visiblePosition = visiblePosition;
        this.when = when;
    }

    public InputMethodEvent( Component source, int id, TextHitInfo caret, TextHitInfo visiblePosition )
    {
        this( source, id, 0, null, 0, caret, visiblePosition );
    }

    public AttributedCharacterIterator getText() { return text; }
    public int getCommittedCharacterCount() { return committedCharacterCount; }
    public TextHitInfo getCaret() { return caret; }
    public TextHitInfo getVisiblePosition() { return visiblePosition; }
    public void consume() { consumed = true; }
    public boolean isConsumed() { return consumed; }

    public long getWhen()
    {
        throw new UnsupportedOperationException("getWhen (event time) is not implemented!");
    }

    public String paramString()
    {
        return toString();
    }
}
