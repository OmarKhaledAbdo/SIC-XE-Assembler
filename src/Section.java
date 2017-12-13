import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Section implements Printable{

    private SymbolTable symTab = new SymbolTable();
    private LiteralTable litTab = new LiteralTable();
    private Set<String> extRef = new HashSet<>();
    private List <String> extDef = new ArrayList<>();
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

    public Integer getBaseAddr() {
        return baseAddr;
    }

    public void setBaseAddr(Integer baseAddr) {
        this.baseAddr = baseAddr;
    }

    private ArrayList<Command> commands = new ArrayList<>();

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public Section(String file, OpCodeTable opTab) {
        List<String[]> program = Reader.readFile(file);
        for (String[] tokens : program) {
            commands.add(Command.create(tokens, opTab));
        }
    }

    public void print() {
        System.out.println("\nSection Addresses:\n");
        for (Command curCommand :commands) {
            System.out.println(curCommand);
        }
    }
}