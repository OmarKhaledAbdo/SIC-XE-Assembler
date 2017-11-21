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
            //For word array.
            for (; index < tokens.length; index++) {
                operand += tokens[index];
            }
        }
    }

    public void setAddress(Integer address) {
        if (!mnemonic.equals("END") && !mnemonic.equals("LTORG")) {
            this.address = address;
        }
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

    public boolean isDirective() {
        return directive;
    }

    public String toString() {
        return String.format("%7s %s %s", label != null ? label : "", mnemonic, address != null ? Integer.toHexString
                (address).toUpperCase() : "");
    }

    public String getOperandHexValue() {
        StringBuilder s = new StringBuilder();
        String operand = this.operand;

        if (operand.startsWith("=C")) {
            operand = operand.replace("=C'", "").replace("'", "");
            for (char ch : operand.toCharArray()) {
                s.append(String.format("%x", (int) ch));
            }
        } else if (operand.startsWith("=X")) {
            operand = operand.replace("=X'", "").replace("'", "");
            if(operand.length() % 2 == 1) {
                operand = "0" + operand;
            }
            for (int i = 0; i < operand.length(); i += 2) {
                String str = operand.substring(i, i + 2);
                s.append(String.format("%1$02X", Integer.parseInt(str, 16)));
            }
        }

        String hexValueOfLiteral = s.toString().toUpperCase();

        return hexValueOfLiteral;
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