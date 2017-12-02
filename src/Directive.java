
public class Directive extends Command {
    public void setAddress(Integer address) {
        if (!mnemonic.equals("END") && !mnemonic.equals("LTORG")) {
            this.address = address;
        }
    }

    public void setOperand (String operand) {
        if(mnemonic.equals("END")) {
            this.operand = null;
        } else {
            this.operand = operand;
        }
    }

    @Override
    public void constructMachineCode() {

    }

    public Directive (String label, String mnemonic, String operand) {
        setLabel(label);
        setMnemonic(mnemonic);
        setOperand(operand);
    }

    public Integer handle(Integer curAddr, LiteralTable litTab) {
        Integer inc = 0;
        switch (mnemonic) {
            case "RESB":
                inc = 1 * Integer.parseInt(operand);
                break;
            case "RESW":
                inc = 3 * Integer.parseInt(operand);
                break;
            case "BYTE":
                inc = getByteInc();
                break;
            case "WORD":
                inc = getWordInc();
                break;
            case "LTORG":
                inc = litTab.addLiteralsToTable(curAddr);
                break;
            case "END":
                inc = litTab.addLiteralsToTable(curAddr);
                break;
        }
        return inc;
    }
}
