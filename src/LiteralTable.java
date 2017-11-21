import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LiteralTable {
    private class LitTableData {
        private String value;
        private Integer length;
        private Integer address;

        public String getValue() {
            return value;
        }

        public Integer getLength() {
            return length;
        }

        public Integer getAddress() {
            return address;
        }

        public LitTableData(String value, Integer length, Integer address) {
            this.value = value;
            this.length = length;
            this.address = address;
        }
    }

    private Map<String, LitTableData> litTab = new HashMap<>();

    public Integer addLiteralsToTable(List<String> auxArray, Integer curAddr) {
        Integer inc = 0;
        for (String literal : auxArray) {
            if (litTab.get(literal).address == null) {
                litTab.get(literal).address = curAddr;
                curAddr += litTab.get(literal).length;
                inc += litTab.get(literal).length;
            }
        }
        return inc;
    }

    public void addIfLiteral(Command curComm, List<String> auxArray) {
        if (curComm.getOperand() != null && curComm.getOperand().startsWith("=")) {
            if (!litTab.containsKey(curComm.getOperand())) {
                litTab.put(curComm.getOperand(), new LitTableData("ASCII", curComm.getByteInc(),
                        null));
                auxArray.add(curComm.getOperand());
            }
        }
    }
}