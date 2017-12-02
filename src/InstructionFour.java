public class InstructionFour extends Instruction {
    public InstructionFour(String label, String mnemonic, Integer opCode, String format, String operand) {
        setFields(label, mnemonic, opCode, format, operand);
    }

    public void constructMachineCode(SymbolTable symTab, LiteralTable litTab) {
        String address;
        String n;
        System.out.println(operand);
        if (operand.startsWith("#")) {
            String immediate = operand.substring(1);
            address = Integer.toBinaryString(Integer.valueOf(immediate, 10));
            n = "0"; //n = 0, i = 1
        } else {
            address = Integer.toBinaryString(symTab.getLabelAddress(operand));
            n = "1"; //n = 1, i = 1
        }
        address = NumberUtils.addLeadingZeroes(address, 20);
        String ixbpe = "10001";
        machineCode = trimmedOpcode() + n + ixbpe + address;
        System.out.println(machineCode);
    }
}