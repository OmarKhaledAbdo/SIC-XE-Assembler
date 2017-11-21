
public class Run {
    public static void main(String[] args) {
        OpCodeTable opTab = new OpCodeTable("InstructionSet.txt");
        Program program = new Program("program.txt", opTab);
        Assembler asm = new Assembler(program);
        asm.passOne();
        asm.print();
    }
}