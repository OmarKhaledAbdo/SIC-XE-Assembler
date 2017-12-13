import java.util.*;

public class LiteralTable implements Printable {


    private Map<String, Literal> litTab = new HashMap<>();
    private List<Literal> auxArray = new ArrayList<>();
    private List<ArrayList<Literal>> literalPool = new ArrayList<>();
    private Integer curLiteralPool = 0;

    public void addLiteral(Command curComm) {
        if (curComm.getOperand() != null && curComm.getOperand().startsWith("=")) {
            if (!litTab.containsKey(curComm.getOperand())) {
                Literal literal = new Literal((Instruction)curComm);
                //System.out.println("Put to literal tabe");
                litTab.put(literal.getValue(), literal);
                auxArray.add(literal);
            }
        }
    }

    public Integer addLiteralsToTable(Integer curAddr) {
        //System.out.println("Add to Literal Table");
        Integer inc = 0;
        literalPool.add(new ArrayList<>());
        for (Literal literal : auxArray) {
            if (litTab.get(literal.getValue()).getAddress() == null) {
                litTab.get(literal.getValue()).setAddress(curAddr);
                literalPool.get(curLiteralPool).add(literal);
                curAddr += literal.getLength();
                inc += literal.getLength();
            }
        }
        curLiteralPool++;
        auxArray.clear();
        return inc;
    }

    public Integer getLiteralAddr (String val) {
        try {
            return litTab.get(val).getAddress();
        } catch (NullPointerException e) {
            System.out.println("Literal not in literal table");
        }
        return 0;
    }

    public Literal[] getLiteralPool(int literalIndex) {
        try {
            if(literalIndex >= literalPool.size()) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            System.out.print("LiteralIndex out of range of literalPools");
        }
       Literal[] literalValues = new Literal[literalPool.get(literalIndex).size()];
        for(int i = 0; i < literalValues.length; i++) {
            literalValues[i] = literalPool.get(literalIndex).get(i);
        }
        return literalValues;
    }

    public void print() {
        System.out.println("\nLiteral Table:\n" + litTab.size());
        Iterator it = litTab.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
    }
}