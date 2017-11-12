import java.util.Iterator;
import java.util.Map;

public class Debug {
    private static void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + "   " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}
