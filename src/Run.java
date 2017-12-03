public class Run {
    public static void main(String[] args) {
        OpCodeTable opTab = new OpCodeTable("InstructionSet.txt");
        Program program = new Program("program2.txt", opTab);
        Assembler asm = new Assembler(program);
        asm.passOne();
        asm.passTwo();
        asm.print();
    }
}