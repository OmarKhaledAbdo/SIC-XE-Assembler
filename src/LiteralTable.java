import java.util.*;

public class LiteralTable implements Printable {
    private class LitTableData {
        private String value;
        private Integer length;
        private Integer address;
        public LitTableData(Command curCommand) {
            this.value = curCommand.getOperandHexValue();
            this.length = curCommand.getByteInc();
            this.address = null; //Address still undetermined
        }
        public String toString() {
            return String.format("Value: %s"  + " Length: %s"  + " Address: %s",
                    value, length, Integer.toHexString(address).toUpperCase());
        }
    }

    private Map<String, LitTableData> litTab = new HashMap<>();
    private List<String> auxArray = new ArrayList<>();

    public void addLiteral(Command curComm) {
        if (curComm.getOperand() != null && curComm.getOperand().startsWith("=")) {
            if (!litTab.containsKey(curComm.getOperand())) {
                litTab.put(curComm.getOperandHexValue(), new LitTableData(curComm));
                auxArray.add(curComm.getOperandHexValue());
            }
        }
    }

    public Integer addLiteralsToTable(Integer curAddr) {
        Integer inc = 0;
        for (String literal : auxArray) {
            if (litTab.get(literal).address == null) {
                litTab.get(literal).address = curAddr;
                curAddr += litTab.get(literal).length;
                inc += litTab.get(literal).length;
            }
        }
        auxArray.clear();
        return inc;
    }

    public void print() {
        System.out.println("\nLiteral Table:\n");
        Iterator it = litTab.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}