public class InstructionThree extends Instruction {
    private Character n;
    private Character i;
    private Character x;
    private Character b;
    private Character p;
    private String disp;
    final private Character e = '0';

    public InstructionThree(String label, String mnemonic, Integer opCode, String format, String operand) {
        setFields(label, mnemonic, opCode, format, operand);
    }

    private void setRelativity(Integer targetAddr, Integer baseAddr) {
        Integer pc = address + Integer.valueOf(format, 10);

        //System.out.println("TargetAddress " + targetAddr + "\n\n");

        Integer diff = targetAddr - pc;
        if (diff > 2047 || diff < -2048) { //Base Relative
            try {
                diff = targetAddr - baseAddr;
                if (diff > 4095 || diff < 0) {
                    throw new Exception("Out of base range");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            //System.out.println("BASE\n");
            b = '1';
            p = '0';
        } else {
            b = '0';
            p = '1';
        }
        disp = NumberUtils.adjustSize(Integer.toBinaryString(diff), 12);
    }

    public void constructMachineCode(Section sec) {
        if (mnemonic.equals("RSUB")) {
            machineCode = "4F0000";
            return;
        }
        operand = operand.replace(" ", "");
        if (operand.startsWith("@")) { //indirect
            //invariants
            n = '1';
            i = '0';
            x = '0';
            if (sec.getSymTab().containsLabel(operand.substring(1))) {
                String symb = operand.substring(1);
                Integer targetAddr = sec.getSymTab().getAddress(symb);
                setRelativity(targetAddr, sec.getBaseAddr());
            } else { //immediate , etc @1000
                b = '0';
                p = '0';
                Integer targetAddr = Integer.valueOf(operand.substring(1), 10);
                disp = NumberUtils.adjustSize(Integer.toBinaryString(targetAddr), 12);
            }
        } else if (operand.startsWith("#")) { //Immediate
            n = '0';
            i = '1';
            x = '0';
            String[] operands = operand.replace("#", "").split("(?=[-+])");
            // etc: LDA #LENGTH
            if(operands.length == 1 && sec.getSymTab().containsLabel(operands[0])) {
                Integer targetAddr = sec.getSymTab().getAddress(operands[0]);
                setRelativity(targetAddr, sec.getBaseAddr());
            } else { //etc: LDA #LENGTH + LIST + 5, result must be absolute
                b = '0';
                p = '0';
                Integer absValue = 0;
                operands[0] = "+" + operands[0];
                for(String operand : operands) {
                    if(sec.getSymTab().containsLabel(operand.substring(1))) {
                        absValue = (operand.charAt(0) == '+' ? 1 : -1) *  sec.getSymTab().getAddress(operand.substring(1));
                    } else {
                        absValue += (operand.charAt(0) == '+' ? 1 : -1) * Integer.valueOf(operand.substring(1), 10);
                    }
                }
                disp = NumberUtils.adjustSize(Integer.toBinaryString(absValue), 12);
            }
        } else if (operand.startsWith("=")) {
            n = '1';
            i = '1';
            x = '0';
            String val = getOperandHexValue();
            Integer targetAddr = sec.getLitTab().getLiteralAddr(val);
            setRelativity(targetAddr, sec.getBaseAddr());
        } else {  //Simple, op m, x OR op c, x
            String[] operands = operand.split("\\s*(?=[-+,])\\s*");
            n = '1';
            i = '1';
            //mem, X OR mem OR mem + constant
            x = (operands.length == 1  || operands[1].charAt(0) != ',') ? '0' : '1';
            Integer targetAddr = sec.getSymTab().getAddress(operands[0]);
            if (operands.length > 1 && operands[1].charAt(0) != ',' ) {
                targetAddr += (operands[1].charAt(0) == '+' ? 1 : -1) * Integer.valueOf(operands[1].substring(1), 10);
            }
            setRelativity(targetAddr, sec.getBaseAddr());
        }
        machineCode = trimmedOpcode() + n + i + x + b + p + e + disp;
        machineCode = NumberUtils.adjustSize(NumberUtils.binaryToHex(machineCode),6);
    }
}