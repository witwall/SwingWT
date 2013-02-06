/**
 * SwingWT
 *
 * This program is dual licenced under the terms of the Common Public 
 * License v1.0 and the GNU Lesser Public License (LGPL) version 2 or
 * later. Please choose whichever is most applicable to you.
 */
package swingwtx.accessibility;

/**
 * TODO Comment!!
 * 
 * @author Thiago Tonelli Bartolomei
 */
public class AccessibleRelation extends AccessibleBundle { 

    public static final String LABEL_FOR = new String("labelFor");
    public static final String LABELED_BY = new String("labeledBy");
    public static final String MEMBER_OF = new String("memberOf");
    public static final String CONTROLLER_FOR = new String("controllerFor");
    public static final String CONTROLLED_BY = new String("controlledBy");
    public static final String LABEL_FOR_PROPERTY = "labelForProperty";
    public static final String LABELED_BY_PROPERTY = "labeledByProperty";
    public static final String MEMBER_OF_PROPERTY = "memberOfProperty";
    public static final String CONTROLLER_FOR_PROPERTY = "controllerForProperty";
    public static final String CONTROLLED_BY_PROPERTY = "controlledByProperty";

    // Bellow here, fields since 1.5
    public static final String FLOWS_TO = "flowsTo"; 
    public static final String FLOWS_FROM = "flowsFrom"; 
    public static final String SUBWINDOW_OF = "subwindowOf"; 
    public static final String PARENT_WINDOW_OF = "parentWindowOf"; 
    public static final String EMBEDS = "embeds"; 
    public static final String EMBEDDED_BY = "embeddedBy"; 
    public static final String CHILD_NODE_OF = "childNodeOf";     
    public static final String FLOWS_TO_PROPERTY = "flowsToProperty";
    public static final String FLOWS_FROM_PROPERTY = "flowsFromProperty";
    public static final String SUBWINDOW_OF_PROPERTY = "subwindowOfProperty";
    public static final String PARENT_WINDOW_OF_PROPERTY = "parentWindowOfProperty";
    public static final String EMBEDS_PROPERTY = "embedsProperty";
    public static final String EMBEDDED_BY_PROPERTY = "embeddedByProperty";
    public static final String CHILD_NODE_OF_PROPERTY = "childNodeOfProperty";
    
    public AccessibleRelation(String key, Object target) {
    	
    }
    
    public AccessibleRelation(String key, Object[] target) {
    	
    }
}
