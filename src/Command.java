public class Command {
    private String format;
    private String label;
    private String mnemonic;
    private String operand;
    private Integer address;
    private boolean directive;

    public Command(String[] tokens, OpCodeTable opTab) {
        Integer index = 0;
        if (!opTab.containsKey(tokens[index])) {
            label = tokens[index++];
        }
        mnemonic = tokens[index++];
        format = opTab.getFirst(mnemonic);
        directive = format.equals("X");

        if (!format.equals(1) && !mnemonic.equals("END")) { //ELSE operand must be null
            operand = "";
            for (; index < tokens.length; index++) {
                operand += tokens[index];
            }
        }
    }


    public void setFormat(String format) {
        this.format = format;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic;
    }

    public void setOperand(String operand) {
        this.operand = operand;
    }

    public void setAddress(Integer address) {
        if (!mnemonic.equals("END") && !mnemonic.equals("LTORG")) {
            this.address = address;
        }
    }

    public void setDirective(boolean directive) {
        this.directive = directive;
    }

    public String getFormat() {

        return format;
    }

    public String getLabel() {
        return label;
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public String getOperand() {
        return operand;
    }

    public Integer getAddress() {
        return address;
    }

    public boolean isDirective() {
        return directive;
    }

    public String toString() {
        return String.format("%7s %s %s", label != null ? label : "", mnemonic, address != null ? Integer.toHexString(address).toUpperCase() : "");
    }

    public Integer getByteInc() {
        Integer incValue = 0;
        /* Modify temporary operand while maintaining the original */
        String operand = this.operand;
        if (operand.startsWith("=")) {
            operand = operand.substring(1);
        }
        operand = operand.replaceAll("\\s", "");
        switch (operand.charAt(0)) {
            case 'X':
                incValue = (operand.length() - 3 + 1) / 2;
                break;
            case 'C':
                incValue = operand.length() - 3;
                break;
        }
        return incValue;
    }

    public Integer getWordInc() {
        return 3 * operand.replaceAll("\\s", "").trim().split("\\s*,\\s*").length;
    }
}
