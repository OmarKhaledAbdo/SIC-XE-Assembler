import javafx.util.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OpCodeTable {
    private Map<String, Pair<String, String>> opTab = new HashMap<>();

    public OpCodeTable(String file) {
        List<String[]> input = Reader.readFile(file);
        for (String[] tokens : input) {
            try {
                if (tokens.length != 3) {
                    throw new Exception("Length is not 3!");
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
            opTab.put(tokens[0], new Pair<>(tokens[1], tokens[2]));
        }
    }

    public boolean containsKey(String key) {
        return opTab.containsKey(key);
    }

    public String getFirst(String key) {
        return opTab.get(key).getKey();
    }
}
