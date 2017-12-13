public class InstructionFour extends Instruction {
    public InstructionFour(String label, String mnemonic, Integer opCode, String format, String operand) {
        setFields(label, mnemonic, opCode, format, operand);
    }

    public void constructMachineCode(Assembler asm) {
        System.out.println(mnemonic + " " + trimmedOpcode());
        String address;
        char n, i, x = '0';
        if (operand.startsWith("#")) {
            String immediate = operand.substring(1);
            address = Integer.toBinaryString(Integer.valueOf(immediate, 10));
            n = '0';
            i = '1';
        } else if (operand.startsWith("@")) {
            String symb = operand.substring(1);
            address = Integer.toBinaryString(asm.getSymTab().getAddress(symb));
            n = '1'; //n = 1, i = 1
            i = '0';
        } else {
            String[] tokens = operand.split("\\s*,\\s*");
            String symb = tokens[0];
            address = Integer.toBinaryString(asm.getSymTab().getAddress(symb));
            n = '1'; //n = 1, i = 1
            i = '1';
            x = tokens.length == 1 ? '0' : '1';
        }
        address = NumberUtils.adjustSize(address, 20);
        String bpe = "001";
        machineCode = trimmedOpcode() + n + i + x + bpe + address;
        machineCode = NumberUtils.adjustSize(NumberUtils.binaryToHex(machineCode),8);
    }
}