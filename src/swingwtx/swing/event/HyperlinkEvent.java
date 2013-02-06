/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley

   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.

   Contact me by electronic mail: bobintetley@users.sourceforge.net


*/


package swingwtx.swing.event;

import java.net.*;

public class HyperlinkEvent {
    
    public final static int ENTERED = 0;
    public final static int EXITED = 0;
    public final static int ACTIVATED = 0;
    
    protected EventType type = null;
    protected URL url = null;
    protected Object source = null;
    protected String description = "";
    
    public HyperlinkEvent(Object source, EventType type, URL u) {
        this.source = source;
        this.type = type;
        this.url = u;
    }
    
    public HyperlinkEvent(Object source, EventType type, URL u, String description) {
        this.source = source;
        this.type = type;
        this.url = u;
        this.description = description;
    }
    
    public URL getURL() { return url; }
    public String getDescription() { return description; }
    public EventType getEventType() { return type; }
    
    public static final class EventType {
        private EventType(String s) {
            typeString = s;
        }
        public static final EventType ENTERED = new EventType("ENTERED");
        public static final EventType EXITED = new EventType("EXITED");
        public static final EventType ACTIVATED = new EventType("ACTIVATED");
        public String toString() {
            return typeString;
        }

        private String typeString;
    }
    
}
