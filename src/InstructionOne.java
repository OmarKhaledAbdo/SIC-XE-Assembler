
public class InstructionOne extends Instruction {
    public InstructionOne(String label, String mnemonic, Integer opCode, String format) {
        setFields(label, mnemonic, opCode, format, null);
    }
    @Override
    public void constructMachineCode() {

    }
}
