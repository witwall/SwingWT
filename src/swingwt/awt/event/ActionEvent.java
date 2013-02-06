/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net

*/


package swingwt.awt.event;

import swingwt.awt.*;

/** Event class used for representing Action events 
 *  Thank you to Diane Trout for submitting additional bits.
 *
 *  @author Robin Rawson-Tetley
 *  @author Diane Trout
 */
public class ActionEvent extends AWTEvent implements java.io.Serializable {
     
    public static final int SHIFT_MASK		= InputEvent.SHIFT_MASK;
    public static final int CTRL_MASK		= InputEvent.CTRL_MASK;
    public static final int META_MASK		= InputEvent.META_MASK;
    public static final int ALT_MASK		= InputEvent.ALT_MASK;
    public static final int ACTION_FIRST        = 1001;
    public static final int ACTION_LAST		= 1001;
    public static final int ACTION_PERFORMED	= ACTION_FIRST;  

     private String command;
     int modifiers;

     public ActionEvent(Object source, int id) {
         this(source, id, "", 0);
     }
     
      /** Creates an ActionEvent from a source object and id
       * @param source The source object generating the event
       * @param id An id referencing the event
       */
     public ActionEvent(Object source, int id, String command) {
       this(source, id, command, 0);
     }

     public ActionEvent(Object source, int id, String command, int modifiers) {
         super(source, id);
         this.command = command;
         this.modifiers = modifiers;
     }

     public String getActionCommand() {
       return this.command;
     }
     
     public void setActionCommand(String command) {
            this.command = command;
     }

     public int getModifiers() {
         return modifiers;
     }
     
}
