import java.util.*;

public class LiteralTable implements Printable {
    private class LitTableData {
        private String value;
        private Integer length;
        private Integer address;
        public LitTableData(Instruction curCommand) {
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
    private List<LitTableData> auxArray = new ArrayList<>();
    private List<ArrayList<LitTableData>> literalPool = new ArrayList<>();
    private Integer curLiteralPool = 0;

    public void addLiteral(Command curComm) {
        if (curComm.getOperand() != null && curComm.getOperand().startsWith("=")) {
            if (!litTab.containsKey(curComm.getOperand())) {
                LitTableData literal = new LitTableData((Instruction)curComm);
                litTab.put(literal.value, literal);
                auxArray.add(literal);
            }
        }
    }

    public Integer addLiteralsToTable(Integer curAddr) {
        Integer inc = 0;
        literalPool.add(new ArrayList<>());
        for (LitTableData literal : auxArray) {
            if (litTab.get(literal.value).address == null) {
                litTab.get(literal.value).address = curAddr;
                literalPool.get(curLiteralPool).add(literal);
                curAddr += literal.length;
                inc += literal.length;
            }
        }
        curLiteralPool++;
        auxArray.clear();
        return inc;
    }

    public Integer getLiteralAddr (String val) {
        try {
            return litTab.get(val).address;
        } catch (NullPointerException e) {
            System.out.println("Literal not in literal table");
        }
        return 0;
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