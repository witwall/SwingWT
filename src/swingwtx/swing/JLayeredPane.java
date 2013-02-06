/*
   SwingWT
   Copyright(c)2003-2008, R. Rawson-Tetley
 
   For more information on distributing and using this program, please
   see the accompanying "COPYING" file.
 
   Contact me by electronic mail: bobintetley@users.sourceforge.net
 
 
 */

package swingwtx.swing;

import swingwt.awt.*;
import java.util.*;

/**
 * JLayeredPane implementation - a panel that allows management
 * of overlaid components.
 *
 * @author Clemens Eisserer
 */
public class JLayeredPane extends JPanel {
    
    public final static Integer DEFAULT_LAYER = new Integer(0);
    public final static Integer PALETTE_LAYER = new Integer(100);
    public final static Integer MODAL_LAYER = new Integer(200);
    public final static Integer POPUP_LAYER = new Integer(300);
    public final static Integer DRAG_LAYER = new Integer(400);
    public final static Integer FRAME_CONTENT_LAYER = new Integer(-30000);
    
    public final static String LAYER_PROPERTY = "layeredContainerLayer";
    
    /** Hashtable that stores all Components (no ClientProperties!) in relation to their layer-value specified. */
    protected Hashtable layerTable = new Hashtable(20);
    
    //this-reference for access from inner
    protected JLayeredPane self = this;
    
    //Caching of Layer/Component-Wrappers
    protected LayerComponent[] layerComponents;
    
    public JLayeredPane() {
        setLayout(null);
    }
    
    
    public Component add(String string, Component component) {
        Component result = super.add(string, component);
        reorderComponents();
        return result;
    }
    
    
    public Component add(Component component) {
        Component result = super.add(component);
        this.setLayer(component, DEFAULT_LAYER.intValue());
        return result;
    }
    
    public void add(Component component, Object constraints) {
        super.add(component, constraints);
        reorderComponents();
    }
    
    public void setSwingWTParent(Container cont) throws Exception {
        super.setSwingWTParent(cont);
        reorderComponents();
    }
    
  /*
   Reorder the Components
   */
    protected void reorderComponents() {
        SwingUtilities.invokeSync(new Runnable() {
            public void run() {
                if(SwingWTUtils.isSWTControlAvailable(self.getSWTPeer())) {
                    Component[] components = getComponents();
                    
                    //Check what can be recycled -> neary almost compensates he memory-issue with the wrappers
                    if(layerComponents == null || layerComponents.length != components.length) {
                        LayerComponent[] oldComponents = layerComponents;
                        layerComponents = new LayerComponent[components.length];
                        
                        if(oldComponents != null) {
                            int minLenght;
                            if(layerComponents.length < oldComponents.length) {
                                minLenght = layerComponents.length;
                            }else {
                                minLenght = oldComponents.length;
                            }
                            
                            System.arraycopy(oldComponents, 0, layerComponents, 0, minLenght);
                        }
                    }
                    
                    for(int i=0; i < components.length; i++) {
                        if(components[i] instanceof JComponent) {
                            JComponent jComp = (JComponent) components[i];
                            if(layerComponents[i] == null) {
                                layerComponents[i] = new LayerComponent(jComp, ((Integer) jComp.getClientProperty(LAYER_PROPERTY)).intValue());
                            }else {
                                layerComponents[i].component = jComp;
                                layerComponents[i].layerValue = ((Integer) jComp.getClientProperty(LAYER_PROPERTY)).intValue();
                            }
                        }else {
                            if(layerComponents[i] == null) {
                                layerComponents[i] = new LayerComponent(components[i], ((Integer) layerTable.get(components[i])).intValue());
                            }else {
                                layerComponents[i].component = components[i];
                                layerComponents[i].layerValue = ((Integer) layerTable.get(components[i])).intValue();
                            }
                        }
                    }
                    
                    //Sort the components
                    Arrays.sort(layerComponents, new LayerComperator());
                    
                    for(int i=1; i < layerComponents.length; i++) {
                        if(SwingWTUtils.isSWTControlAvailable(layerComponents[i-1].component.getSWTPeer()) && SwingWTUtils.isSWTControlAvailable(layerComponents[i].component.getSWTPeer())) {
                            layerComponents[i - 1].component.getSWTPeer().moveBelow(layerComponents[i].component.getSWTPeer());
                        }
                    }
                }
            }
        });
    }
    
    /* Not implemented! */
    public int getPosition(Component c) {
        return 0;
    }
    
    /* Not implemented! */
    protected  int insertIndexForLayer(int layer, int position) {
        return getComponentCount();
    }
    
    public void setLayer(Component c, int layer, int position) {
        putLayer((JComponent)c, layer);
        reorderComponents();
    }
    
    public Component[] getComponentsInLayer(int layer) {
        int componentCount = getComponentCount();
        LinkedList componentList = new LinkedList();
        
        for(int i=0; i < componentCount; i++) {
            Component comp = getComponent(i);
            
            if(comp instanceof JComponent) {
                JComponent jComp = (JComponent) comp;
                if(((Integer) jComp.getClientProperty(LAYER_PROPERTY)).intValue() == layer) {
                    componentList.add(comp);
                }
            }else {
                int actLayerVal = ((Integer) layerTable.get(comp)).intValue();
                
                if(actLayerVal == layer) {
                    componentList.add(comp);
                }
            }
        }
        
        return (Component[]) componentList.toArray(new Component[0]);
    }
    
    int getComponentCountInLayer(int layer) {
        int componentCount = getComponentCount();
        int actLayer;
        int layerCount = 0;
        
        for(int i = 0; i < componentCount; i++) {
            actLayer = getLayer(getComponent(i));
            //If the actual Comonent is in the requested layer -> layerCount++
            if(actLayer == layer) {
                layerCount++;
            }
            else if(layerCount > 0 || actLayer < layer) {
                break;
            }
        }
        
        return layerCount;
    }
    
    protected  Hashtable getComponentToLayer() {
        return layerTable;
    }
    
    //Isnt there a more effizient way?
    //Rob: yes - this :-)
    public int getIndexOf(Component c) {
        return comps.indexOf(c);
    }
    
    
    /*Compatibility Method to also support "AWT"-Components*/
    public int getLayer(Component c) {
        if(c instanceof JComponent) {
            return getLayer((JComponent) c);
        }else {
            return ((Integer) layerTable.get(c)).intValue();
        }
    }
    
    
    
    protected  Integer getObjectForLayer(int layer) {
        Integer intObj;
        
        switch(layer) {
            case 0:
                intObj = DEFAULT_LAYER;
                break;
            case 100:
                intObj = PALETTE_LAYER;
                break;
            case 200:
                intObj = MODAL_LAYER;
                break;
            case 300:
                intObj = POPUP_LAYER;
                break;
            case 400:
                intObj = DRAG_LAYER;
                break;
            default:
                intObj = new Integer(layer);
        }
        return intObj;
    }
    
    //Any more effiiziet ideas?
    public int lowestLayer() {
        int componentCount = getComponentCount();
        int lowestLayer = 0;
        
        if(componentCount > 0) {
            lowestLayer = Integer.MAX_VALUE;
            
            for(int i=0; i < componentCount; i++) {
                int actLayer = getLayer(getComponent(i));
                
                if(actLayer < lowestLayer) {
                    lowestLayer = actLayer;
                }
            }
        }
        
        return lowestLayer;
    }
    
    public int highestLayer() {
        int componentCount = getComponentCount();
        int highestLayer = 0;
        
        if(componentCount > 0) {
            highestLayer = Integer.MIN_VALUE;
            
            for(int i=0; i < componentCount; i++) {
                int actLayer = getLayer(getComponent(i));
                
                if(actLayer > highestLayer) {
                    highestLayer = actLayer;
                }
            }
        }
        
        return highestLayer;
    }
    
    
    public void	moveToBack(Component c) {
        setPosition(c, 0);
    }
    
    void moveToFront(Component c) {
        setPosition(c, highestLayer() + 1);
    }
    
    
    //Is Optimized Drawing possible at all??
    protected  String paramString() {
        return "false";
    }
    
    public void remove(int index) {
        Component comp = getComponent(index);
        
        /* components are stored in layerTable and need to be removed! */
        if((comp != null) && !(comp instanceof JComponent)) {
            layerTable.remove(comp);
        }
        
        super.remove(index);
    }
    
    
    public void setLayer(Component c, int layer) {
        setLayer(c, layer, -1);
    }
    
    
    public void setPosition(Component c, int position) {
        setLayer(c, getLayer(c), position);
    }
    
    
    /*Static Methods*/
    
    static void putLayer(JComponent c, int layer) {
        Integer layerValue = new Integer(layer);
        c.putClientProperty(LAYER_PROPERTY, layerValue);
    }
    
    
    
    public static int getLayer(JComponent c) {
        Integer layer = (Integer) c.getClientProperty(LAYER_PROPERTY);
        
        if(layer != null) {
            return layer.intValue();
        }
        
        /*If no layer propertie is specified -> JComponent is default layer*/
        return DEFAULT_LAYER.intValue();
    }
    
    
    
    public static JLayeredPane getLayeredPaneAbove(Component c) {
        //     Dummy check
        if(c == null) {
            return null;
        }
        
        /*Go up in the component-hirachy till we find the first Parent which is a layeredPane*/
        Component parentComponent = c.getParent();
        while(parentComponent != null && !(parentComponent instanceof JLayeredPane)) {
            parentComponent = parentComponent.getParent();
        }
        
        return (JLayeredPane)parentComponent;
    }
}

/*Comperator-Implementation needed for sorting the component-array*/
class LayerComperator implements Comparator {
    public int compare(Object comp1, Object comp2) {
        LayerComponent lComp1 = (LayerComponent) comp1;
        LayerComponent lComp2 = (LayerComponent) comp2;
        
        if(lComp1.layerValue < lComp2.layerValue) {
            return -1;
        }else
            if(lComp1.layerValue == lComp2.layerValue) {
                return 0;
            }else
                if(lComp1.layerValue > lComp2.layerValue) {
                    return 1;
                }
        
        return -1;
    }
}

/*
  Container component.
  Required for the LayerComperator because there are tow types of Components:
 * JComponents store the layerValue in their clientProperties
 * Component layer values are stored in the hashtable
   LayerComponent acts as a universal layer/component container.
 */
class LayerComponent {
    public Component component;
    public int layerValue;
    
    public LayerComponent(Component component, int layerValue) {
        this.component = component;
        this.layerValue = layerValue;
    }
}
