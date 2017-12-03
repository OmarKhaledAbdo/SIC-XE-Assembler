
public class InstructionOne extends Instruction {
    public InstructionOne(String label, String mnemonic, Integer opCode, String format) {
        setFields(label, mnemonic, opCode, format, null);
    }
    @Override

    public String toString() {
        if(getMachineCode() == null) {
            System.out.println(getMnemonic() + " " + getFormat());
        }
        return String.format("%7s %s %s %s", getLabel() != null ? getLabel() : "", getMnemonic(),
                getAddress() != null ? Integer.toHexString(getAddress()).toUpperCase() : "",
                getMachineCode());
    }

    public void constructMachineCode(Assembler asm) {
        machineCode = Integer.toHexString(opCode);
        machineCode = NumberUtils.adjustSize(machineCode, 2);
    }
}
