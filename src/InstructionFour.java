public class InstructionFour extends Instruction {
    public InstructionFour(String label, String mnemonic, Integer opCode, String format, String operand) {
        setFields(label, mnemonic, opCode, format, operand);
    }

    public void constructMachineCode(Section sec) {
        //System.out.println(mnemonic + " " + trimmedOpcode());
        String address;
        Character n, i, x = '0';
        if (operand.startsWith("#")) {
            String[] operands = operand.replace("#", "").split("(?=[-+])");
            operands[0] = "+" + operands[0];
            Integer absValue = 0, relativeDiff = 0;
            for (String operand : operands) {
                if (sec.getExtRef().contains(operand.substring(1))) {
                    extRef.add(operand);
                } else if (sec.getSymTab().containsLabel(operand.substring(1))) {
                    relativeDiff += (operand.charAt(0) == '+' ? 1 : -1);
                    absValue = (operand.charAt(0) == '+' ? 1 : -1) * sec.getSymTab().getAddress(operand.substring(1));
                } else {
                    absValue += (operand.charAt(0) == '+' ? 1 : -1) * Integer.valueOf(operand.substring(1), 10);
                }
            }
            n = '0';
            i = '1';
            absolute = relativeDiff;
            address = Integer.toBinaryString(absValue);
        } else if (operand.startsWith("@")) {
            absolute = 1;
            String sym = operand.substring(1);
            address = Integer.toBinaryString(sec.getSymTab().getAddress(sym));
            n = '1'; //n = 1, i = 1
            i = '0';
        } else {
            //etc: listA + 4, listA, X
            Integer addressValue;
            String[] operands = operand.replace(" ", "").split("(?=[-,+])");
            //System.out.println(operands[0]);
            if (sec.getExtRef().contains(operands[0])) {
                extRef.add("+" + operands[0]);
                addressValue = 0;
                absolute = 0;
            } else {
                absolute = 1;
                addressValue = sec.getSymTab().getAddress(operands[0]);
            }
            //+ Constant
            if (operands.length > 1 && operands[1].charAt(0) != ',') {
                addressValue += (operands[1].charAt(0) == '+' ? 1 : -1) * Integer.valueOf(operands[1].substring(1), 10);
            }
            address = Integer.toBinaryString(addressValue);
            n = '1'; //n = 1, i = 1
            i = '1';
            x = (operands.length == 2 && operands[1].charAt(0) == ',') ? '1' : '0';

        }
        address = NumberUtils.adjustSize(address, 20);
        String bpe = "001";
        machineCode = trimmedOpcode() + n + i + x + bpe + address;
        machineCode = NumberUtils.adjustSize(NumberUtils.binaryToHex(machineCode), 8);
    }
}