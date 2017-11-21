import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SymbolTable {
    private Map<String, Integer> symTab = new HashMap<>();
    public void put(String key, Integer value) {
        if(key != null) {
            symTab.put(key, value);
        }
    }
    public void print() {
        System.out.println("\nSymbol table:\n");
        Iterator it = symTab.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Integer addr = (Integer) pair.getValue();
            System.out.println(pair.getKey() + "   " + Integer.toHexString(addr).toUpperCase());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
