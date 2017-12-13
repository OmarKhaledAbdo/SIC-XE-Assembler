public abstract class Command {
    protected String format;
    protected String label;
    protected String mnemonic;
    protected String operand;
    protected Integer address;
    protected boolean directive;
    protected String machineCode;
    protected Integer opCode;


    public Command() {
    }

    public static Command create(String[] tokens, OpCodeTable opTab) {
        Integer index = 0;
        String label = null;

        for (int i = 0; i < tokens.length; i++) {
            System.out.print(tokens[i] + " ");
        }
        System.out.println();


        if (!opTab.containsKey(tokens[index])) {
            label = tokens[index++];
        }

        String mnemonic = tokens[index++];
        String format = opTab.getFormat(mnemonic);

        boolean isDirective = format.equals("X");

        String operand = "";
        //For word array.
        for (; index < tokens.length; index++) {
            //System.out.println(tokens[index]);
            if(operand.isEmpty()) {
                operand = tokens[index];
            }else {
                operand += " " + tokens[index];
            }
        }
        //System.out.println("Operand " + operand + "\n");
        if (isDirective) {
            return new Directive(label, mnemonic, operand);
        } else {
            Integer opCode = Integer.parseInt(opTab.getOpcode(mnemonic), 16);
            return Instruction.create(label, mnemonic, opCode, format, operand);
        }
    }


    public void setAddress(Integer address) {
        this.address = address;
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
    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
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

    public Integer getAddress() {
        return address;
    }

    public void setDirective(boolean directive) {
        this.directive = directive;
    }

    public Integer getOpCode() {
        return opCode;
    }

    public void setOpCode(Integer opCode) {
        this.opCode = opCode;
    }

    abstract public Integer handle(Integer curAddress, Assembler asm);
    abstract public void constructMachineCode(Assembler asm);



    public String toString() {
        if (getMachineCode() == null) {
            System.out.println(getMnemonic() + " " + getFormat());
        }
        return String.format("%7s %s %s %s", getLabel() != null ? getLabel() : "", getMnemonic(), getAddress() != null ? Integer.toHexString
                (getAddress()).toUpperCase() : "", getMachineCode() != "" ? getMachineCode() : "");
    }


    public Integer getByteInc() {
        //System.out.println("Operand " + operand);
        Integer incValue = 0;
        /* Modify temporary operand while maintaining the original */
        String operand = this.getOperand();
        if (operand.startsWith("=")) {
            operand = operand.substring(1);
        }
        //operand = operand.replaceAll("\\s", "");
        operand = operand.trim();
        switch (operand.charAt(0)) {
            case 'X':
                incValue = (operand.length() - 3 + 1) / 2;
                break;
            case 'C':
                incValue = operand.length() - 3;
                break;
        }
        System.out.println(operand + " incValue " + incValue);
        return incValue;
    }
    public Integer getWordInc() {
//        System.out.println("Operand " + getOperand());
//        System.out.println(3 * getOperand().replaceAll("\\s", "").trim().split("\\s*,\\s*").length + "\n\n");
        return 3 * getOperand().replaceAll("\\s", "").trim().split("\\s*,\\s*").length;
    }
    public String getOperandHexValue() {
        StringBuilder s = new StringBuilder();
        String operand = this.getOperand();
        //System.out.println(operand);
        operand = operand.replace("=", "");
        if (operand.startsWith("C")) {
            operand = operand.replace("C", "").replace("'", "");
            for (char ch : operand.toCharArray()) {
                s.append(String.format("%x", (int) ch));
            }
        } else if (operand.startsWith("X")) {
            operand = operand.replace("X", "").replace("'", "");
            if (operand.length() % 2 == 1) {
                operand = "0" + operand;
            }
            for (int i = 0; i < operand.length(); i += 2) {
                String str = operand.substring(i, i + 2);
                s.append(String.format("%1$02X", Integer.parseInt(str, 16)));
            }
        }
        String hexValue = s.toString().toUpperCase();
        //System.out.println("Hex value " + hexValue);
        return hexValue;
    }
}