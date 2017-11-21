import java.util.ArrayList;
import java.util.List;

class Assembler {

    private SymbolTable symTab = new SymbolTable();
    private LiteralTable litTab = new LiteralTable();
    private Program program;

    public Assembler(Program program) {
        this.program = program;
    }

    private Integer handleDirective(Command curCommand, Integer curAddr, List<String> auxArray) {
        Integer inc = 0;
        switch (curCommand.getMnemonic()) {
            case "RESB":
                inc = 1 * Integer.parseInt(curCommand.getOperand());
                break;
            case "RESW":
                inc = 3 * Integer.parseInt(curCommand.getOperand());
                break;
            case "BYTE":
                inc = curCommand.getByteInc();
                break;
            case "WORD":
                inc = curCommand.getWordInc();
                break;
            case "LTORG":
                inc = litTab.addLiteralsToTable(auxArray, curAddr);
                break;
            case "END":
                inc = litTab.addLiteralsToTable(auxArray, curAddr);
                break;
        }
        return inc;
    }

    private Integer handleInstruction(Command curCommand) {
        return Integer.valueOf(curCommand.getFormat());
    }

    public void passOne() {
        Integer curAddr = Integer.parseInt(program.getCommands().get(0).getOperand(), 16);
        List<String> auxArray = new ArrayList<>();
        for (Command curCommand : program.getCommands()) {
            symTab.put(curCommand.getLabel(), curAddr);
            litTab.addIfLiteral(curCommand, auxArray);
            curCommand.setAddress(curAddr);
            if (curCommand.isDirective()) {   //Special handling is required
                curAddr += handleDirective(curCommand, curAddr, auxArray);
            } else {
                curAddr += handleInstruction(curCommand);
            }
        }
    }

    public void print() {
        program.print();
        symTab.print();
    }
}