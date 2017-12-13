public class Run {
    public static void main(String[] args) {
        OpCodeTable opTab = new OpCodeTable("InstructionSet.txt");
        Section section = new Section("program3.txt", opTab);
        Assembler asm = new Assembler(section);
        asm.passOne();
        asm.passTwo();
        asm.print();

    }
}