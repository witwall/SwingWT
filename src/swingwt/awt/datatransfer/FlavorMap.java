package swingwt.awt.datatransfer ;

import java.util.Map;

public interface FlavorMap {
    Map getNativesForFlavors(DataFlavor[] flavors);
    Map getFlavorsForNatives(String[] natives);
}
