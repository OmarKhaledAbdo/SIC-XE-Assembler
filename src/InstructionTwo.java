import java.util.HashMap;

public class InstructionTwo extends Instruction {

    final private static HashMap <String, Integer> reg;
    static {
        reg = new HashMap<>();
        reg.put("A", 0);
        reg.put("X", 1);
        reg.put("L", 2);
        reg.put("B", 3);
        reg.put("S", 4);
        reg.put("T", 5);
        reg.put("F", 6);
        reg.put("PC", 8);
        reg.put("SW", 9);
    }
    public InstructionTwo(String label, String mnemonic, Integer opCode, String format, String operand) {
        setFields(label, mnemonic, opCode, format, operand);
    }
    public void constructMachineCode(Assembler asm) {
        String[] registers = operand.split("\\s*,\\s*");
//        for (String x : registers) {
//            System.out.println(x);
//        }
        String regA = Integer.toBinaryString(reg.get(registers[0]));
        String regB = "0";
        if(registers.length == 2) {
            regB = Integer.toBinaryString(reg.get(registers[1]));
        }
        regA = NumberUtils.adjustSize(regA, 4);
        regB = NumberUtils.adjustSize(regB, 4);
        machineCode = Integer.toBinaryString(opCode) + regA + regB;
        machineCode = NumberUtils.adjustSize(NumberUtils.binaryToHex(machineCode),4);
    }
}