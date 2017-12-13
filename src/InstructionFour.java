public class InstructionFour extends Instruction {

    Boolean abs = false;

    public InstructionFour(String label, String mnemonic, Integer opCode, String format, String operand) {
        setFields(label, mnemonic, opCode, format, operand);
    }

    public void constructMachineCode(Section sec) {
        System.out.println(mnemonic + " " + trimmedOpcode());
        String address;
        char n, i, x = '0';
        if (operand.startsWith("#")) {
            String immediate = operand.substring(1);
            address = Integer.toBinaryString(Integer.valueOf(immediate, 10));
            n = '0';
            i = '1';
        } else if (operand.startsWith("@")) {
            String sym = operand.substring(1);
            address = Integer.toBinaryString(sec.getSymTab().getAddress(sym));
            n = '1'; //n = 1, i = 1
            i = '0';
        } else {
            String[] tokens = operand.split("\\s*,\\s*");
            String sym = tokens[0];
            if(sec.getExtRef().contains(sym))
                address = "00000";
            else
                address = Integer.toBinaryString(sec.getSymTab().getAddress(sym));
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