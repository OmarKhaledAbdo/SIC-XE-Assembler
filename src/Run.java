
public class Run {

    public static void main(String[] args) {
//        String x = "1";
//        Integer y = Integer.valueOf(x, 16);
//        System.out.println(Integer.toBinaryString(y));
//
        OpCodeTable opTab = new OpCodeTable("InstructionSet.txt");
        Program program = new Program("program2.txt", opTab);
        Assembler asm = new Assembler(program);
        asm.passOne();
        asm.passTwo();
        //asm.print();
    }
}