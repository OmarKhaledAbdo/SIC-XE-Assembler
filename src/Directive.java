public class Directive extends Command {

    public Directive(String label, String mnemonic, String operand) {
        setLabel(label);
        setMnemonic(mnemonic);
        setOperand(operand);
    }

    public void setAddress(Integer address) {
        if (mnemonic.equals("END") || mnemonic.equals("LTORG")) {
            this.address = null;
        } else {
            this.address = address;
        }
    }

    public void setOperand(String operand) {
        if (mnemonic.equals("END")) {
            this.operand = null;
        } else {
            this.operand = operand;
        }
    }

    @Override
    public String toString() {
        if(getMachineCode() == null) {
            System.out.println(getMnemonic() + " " + getFormat());
        }
        return String.format("%7s %s %s %s %s", getLabel() != null ? getLabel() : "", getMnemonic(), getOperand(), getAddress() != null ? Integer.toHexString
                        (getAddress()).toUpperCase() : "",
                getMachineCode().toUpperCase());
    }
    @Override
    public void constructMachineCode(Assembler asm) {
        machineCode = "";
        switch (mnemonic) {
            case "BYTE":
                machineCode = getOperandHexValue();
                break;
            case "WORD":
                machineCode = getWordHexValue();
                break;
        }
    }
    public String getWordHexValue() {
        String[] operands = operand.split("\\s*,\\s*");
        System.out.println(operands.length);
        StringBuilder str = new StringBuilder();
        for(String operand : operands) {
            System.out.println(operand);
            String val = Integer.toHexString(Integer.valueOf(operand, 10));
            // Word = 3 bytes = 6 Hex Digits
            val = NumberUtils.adjustSize(val, 6);
            str.append(val);
        }
        return str.toString();
    }
    public Integer handle(Integer curAddr, Assembler asm) {
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
                inc = asm.getLitTab().addLiteralsToTable(curAddr);
                break;
            case "END":
                inc = asm.getLitTab().addLiteralsToTable(curAddr);
                break;
            case "BASE":
                inc = 0;
                asm.setBaseAddr(Integer.valueOf(operand, 16));
                break;
        }
        return inc;
    }
}