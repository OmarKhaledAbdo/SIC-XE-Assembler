public class Run {
    public static void main(String[] args) {
        Integer x = -2;
        String bin = Integer.toBinaryString(x);
        bin = NumberUtils.adjustSize(bin, 12);
        System.out.println(bin.length());
        OpCodeTable opTab = new OpCodeTable("InstructionSet.txt");
        Program program = new Program("program2.txt", opTab);
        Assembler asm = new Assembler(program);
        asm.passOne();
        asm.passTwo();
        asm.print();
    }
}