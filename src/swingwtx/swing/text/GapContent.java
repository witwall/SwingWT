/*
   SwingWT
   Copyright(c)2003-2008 Robin Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/

package swingwtx.swing.text;

/**
 * This is Swing's super-scalable Document - in the
 * meantime, we just descend StringContent to make
 * it work.
 *
 * If anyone is interested, I believe the underlying principle
 * of this is supposed to be that operating changes around the
 * gap are cheap (as moving/resizing the gap is easy), whereas
 * having to move every character in the buffer up and down
 * according to edits is rather expensive.
 * 
 * FIXME: Implement properly
 *
 * @author  Robin Rawson-Tetley
 */
public class GapContent extends StringContent {
    public GapContent() { super(); }
    public GapContent(int initialSize) { super(initialSize); }
}
