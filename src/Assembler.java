import java.util.*;

class Assembler implements Printable {

    private SymbolTable symTab = new SymbolTable();
    private LiteralTable litTab = new LiteralTable();
    private Program program;
    private ObjectProgram objectProgram = new ObjectProgram();
    private Set <String> extRef = new HashSet<>();
    private List <String> extDef = new ArrayList<>();
    private Integer lastUsedAddress;
    private Integer baseAddr;

    public List<String> getExtDef() {
        return extDef;
    }

    public void setExtDef(List<String> extDef) {
        this.extDef = extDef;
    }

    public Set<String> getExtRef() {
        return extRef;
    }

    public void setExtRef(Set<String> extRef) {
        this.extRef = extRef;
    }

    public SymbolTable getSymTab() {
        return symTab;
    }

    public void setSymTab(SymbolTable symTab) {
        this.symTab = symTab;
    }

    public LiteralTable getLitTab() {
        return litTab;
    }

    public void setLitTab(LiteralTable litTab) {
        this.litTab = litTab;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public ObjectProgram getObjectProgram() {
        return objectProgram;
    }

    public void setObjectProgram(ObjectProgram objectProgram) {
        this.objectProgram = objectProgram;
    }

    public Integer getLastUsedAddress() {
        return lastUsedAddress;
    }

    public void setLastUsedAddress(Integer lastUsedAddress) {
        this.lastUsedAddress = lastUsedAddress;
    }

    public Integer getBaseAddr() {
        return baseAddr;
    }

    public void setBaseAddr(Integer baseAddr) {
        this.baseAddr = baseAddr;
    }


    public Assembler(Program program) {
        this.program = program;
    }

    public void passOne() {
        Integer startAddr = Integer.parseInt(program.getCommands().get(0).getOperand(), 16);
        Integer curAddr = startAddr;
        for (Command curCommand : program.getCommands()) {
            symTab.addLabel(curCommand.getLabel(), curAddr);
            litTab.addLiteral(curCommand);
            curCommand.setAddress(curAddr);
            curAddr += curCommand.handle(curAddr, this);
        }
        lastUsedAddress = curAddr;
    }

    public void passTwo() {
        Integer startAddr = Integer.parseInt(program.getCommands().get(0).getOperand(), 16);

        objectProgram.setHeaderRecord(new HeaderRecord(program.getCommands().get(0).getLabel(),
                startAddr, lastUsedAddress - startAddr));

        Integer curLiteralPool = 0;
        for (Command curCommand : program.getCommands()) {
            curCommand.constructMachineCode(this);
            if(curCommand.getMnemonic().equals("LTORG") || curCommand.getMnemonic().equals("END")) {
                Literal [] literals = litTab.getLiteralPool(curLiteralPool);
                for (Literal literal : literals) {
                    objectProgram.addToTextRecords(null, literal.getValue(), literal.getAddress());
                }
                curLiteralPool++;
            } else {
                objectProgram.addToTextRecords(curCommand.getMnemonic(), curCommand.getMachineCode(), curCommand.getAddress());
            }
        }

        objectProgram.setEndRecord(new EndRecord(startAddr));
    }
    public void print() {
        program.print();
        symTab.print();
        litTab.print();
        objectProgram.print();
    }
}