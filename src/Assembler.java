class Assembler implements Printable{

    private SymbolTable symTab = new SymbolTable();
    private LiteralTable litTab = new LiteralTable();
    private Program program;

    public Assembler(Program program) {
        this.program = program;
    }

    public void passOne() {
        Integer curAddr = Integer.parseInt(program.getCommands().get(0).getOperand(), 16);
        for (Command curCommand : program.getCommands()) {
            symTab.addLabel(curCommand.getLabel(), curAddr);
            litTab.addLiteral(curCommand);
            curCommand.setAddress(curAddr);
            curAddr += curCommand.handle(curAddr, litTab);
        }
    }

    public void passTwo () {
        for (Command curCommand : program.getCommands()) {
            curCommand.constructMachineCode();
        }

    }

    public void print() {
        program.print();
        symTab.print();
        litTab.print();
    }
}