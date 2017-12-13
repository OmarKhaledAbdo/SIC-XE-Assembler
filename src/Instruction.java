

public abstract class Instruction extends Command {


    public static Instruction create(String label, String mnemonic, Integer opCode, String format, String operand) {
        switch (format) {
            case "1":
                return new InstructionOne(label, mnemonic, opCode, format);
            case "2":
                return new InstructionTwo(label, mnemonic, opCode, format, operand);
            case "3":
                return new InstructionThree(label, mnemonic, opCode, format, operand);
            case "4":
                return new InstructionFour(label, mnemonic, opCode, format, operand);
        }
        return null;
    }

    public void setFields(String label, String mnemonic, Integer opCode, String format, String operand) {
        setLabel(label);
        setMnemonic(mnemonic);
        setFormat(format);
        setOperand(operand);
        setOpCode(opCode);
    }

    //Returns the opCode after removing the last two bits
    protected String trimmedOpcode () {
        String opCode = Integer.toBinaryString(this.opCode);
        opCode = NumberUtils.adjustSize(opCode, 8);
        opCode = opCode.substring(0, 6);
        return opCode;
    }

    @Override
    public Integer handle(Integer curAddress, Section sec) {
        return Integer.valueOf(format);
    }

}
