public class InstructionOne extends Instruction {
    public InstructionOne(String label, String mnemonic, Integer opCode, String format) {
        setFields(label, mnemonic, opCode, format, null);
    }

    public void constructMachineCode(Section sec) {
        machineCode = Integer.toHexString(opCode);
        machineCode = NumberUtils.adjustSize(machineCode, 2);
    }
}
