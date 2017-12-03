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

    public String toString() {
        String machineCode = Integer.toHexString(Integer.valueOf(getMachineCode(), 2)).toUpperCase();
        machineCode = NumberUtils.adjustSize(machineCode, 6);
        return String.format("%7s %s %s %s %s", getLabel() != null ? getLabel() : "", getMnemonic(), getOperand(),
                getAddress() != null ? Integer.toHexString(getAddress()).toUpperCase() : "", machineCode);
    }

    private void setRelativity(Integer targetAddr, Integer baseAddr) {
        Integer pc = address + Integer.valueOf(format, 10);
        Integer diff = targetAddr - pc;
        if (diff > 2047) { //Base Relative
            try {
                diff = targetAddr - baseAddr;
                if (diff > 4095 || diff < 0) {
                    throw new Exception("Out of base range");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            b = '1';
            p = '0';
        } else {
            try {
                if (diff < -2048) {
                    throw new Exception("Negative and smaller than PC range");
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
//            System.out.println("PC relative");
//            System.out.println("Diff " + diff);
            b = '0';
            p = '1';
        }
        disp = NumberUtils.adjustSize(Integer.toBinaryString(diff), 12);
    }

    public void constructMachineCode(Assembler asm) {
        //Special case
        if (mnemonic.equals("RSUB")) {
            machineCode = trimmedOpcode() + "110000000000000000";
            return;
        }

        System.out.println(mnemonic);

        if (operand.startsWith("@")) { //indirect
            //invariants
            n = '1';
            i = '0';
            x = '0';
            if (asm.getSymTab().containsLabel(operand.substring(1))) {
                String symb = operand.substring(1);
                Integer targetAddr = asm.getSymTab().getAddress(symb);
                setRelativity(targetAddr, asm.getBaseAddr());
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
            if (asm.getSymTab().containsLabel(operand.substring(1))) {
                String symb = operand.substring(1);
                Integer targetAddr = asm.getSymTab().getAddress(symb);
                setRelativity(targetAddr, asm.getBaseAddr());
            } else {
                b = '0';
                p = '0';
                Integer targetAddr = Integer.valueOf(operand.substring(1), 10);
                disp = NumberUtils.adjustSize(Integer.toBinaryString(targetAddr), 12);
            }
        } else if (operand.startsWith("=")) {
            n = '1';
            i = '1';
            x = '0';
            String val = getOperandHexValue();
            Integer targetAddr = asm.getLitTab().getLiteralAddr(val);
            setRelativity(targetAddr, asm.getBaseAddr());
        } else {  //Simple, op m, x OR op c, x

            String[] tokens = operand.split("\\s*,\\s");
            n = '1';
            i = '1';
            x = tokens.length == 1 ? '0' : '1';
            String operandOne = tokens[0];
            Integer targetAddr = asm.getSymTab().getAddress(operandOne);
            setRelativity(targetAddr, asm.getBaseAddr());
//            } else {
//                Integer targetAddr = Integer.valueOf(operandOne, 10);
//                disp = NumberUtils.adjustSize(Integer.toBinaryString(targetAddr), 12);
//            }
        }
        machineCode = trimmedOpcode() + n + i + x + b + p + e + disp;
        System.out.println(machineCode);
    }
}