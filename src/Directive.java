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
        if (mnemonic.equals("END") || mnemonic.equals("LTORG")) {
            this.operand = null;
        } else {
            this.operand = operand;
        }
    }

    @Override
    public String toString() {
        if (getMachineCode() == null) {
            System.out.println(getMnemonic() + " " + getFormat());
        }
        return String.format("%7s %s %s Addr: %s %s", getLabel() != null ? getLabel() : "", getMnemonic(), getOperand() != null ? getOperand() : "",
                getAddress() != null ? Integer.toHexString(getAddress()).toUpperCase() : "",
                getMachineCode().toUpperCase());
    }

    @Override
    public void constructMachineCode(Section sec) {
        machineCode = "";
        switch (mnemonic) {
            case "BYTE":
                machineCode = getOperandHexValue();
                break;
            case "WORD":
                if (operand.indexOf(',') != -1) {
                    machineCode = getWordHexValue();
                    absolute = 0;
                } else { //expression
                    machineCode = getWordExpressionValue(sec);
                }
                break;
            case "BASE":
                if (operand.startsWith("#")) {
                    sec.setBaseAddr(Integer.valueOf(operand.substring(1), 16));
                } else {
                    sec.setBaseAddr(sec.getSymTab().getAddress(operand));
                }
                break;
            default:
        }
    }

    public String getWordExpressionValue(Section sec) {
        Integer absValue = 0, relativeDiff = 0;
        String[] operands = operand.replace(" ", "").split("(?=[-+])");
        operands[0] = "+" + operands[0];
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
        absolute = relativeDiff;
        String hexValue = NumberUtils.adjustSize(Integer.toHexString(absValue), 6);
        return hexValue;
    }

    public String getWordHexValue() {
        String[] operands = operand.split("\\s*,\\s*");
        //System.out.println(operands.length);
        StringBuilder str = new StringBuilder();
        for (String operand : operands) {
            System.out.println(operand);
            String val = Integer.toHexString(Integer.valueOf(operand, 10));
            // Word = 3 bytes = 6 Hex Digits
            val = NumberUtils.adjustSize(val, 6);
            str.append(val);
        }
        return str.toString();
    }
    public Integer handle(Integer curAddr, Section sec) {
        Integer inc;
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
                inc = sec.getLitTab().addLiteralsToTable(curAddr);
                break;
            case "END":
                inc = sec.getLitTab().addLiteralsToTable(curAddr);
                break;
            case "BASE":
                inc = 0;
                break;
            case "EXTDEF":
                inc = 0;
                String[] tokens = operand.split("\\s*,\\s*");
                for (String sym : tokens) {
                    sec.getExtDef().add(sym);
                }
                break;
            case "EXTREF":
                inc = 0;
                tokens = operand.split("\\s*,\\s*");
                for (String sym : tokens) {
                    sec.getExtRef().add(sym);
                }
                break;
            default:
                inc = 0;
        }
        return inc;
    }
}