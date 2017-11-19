import javafx.util.Pair;
import java.util.*;

class Assembler {

    private Map<String, Pair<String,String>> opTab = new HashMap<>();
    private Map<String, Integer> symTab = new HashMap<>();
    private ArrayList<Command> commands = new ArrayList<>();

    public Assembler() {
        constructOpTab();
        readProgram();
    }

    private void constructOpTab() {
        List<String []> input = Reader.readFile("InstructionSet.txt");
        for(String [] tokens: input) {
            try {
                if(tokens.length != 3) {
                    throw new Exception("Length is not 3!");
                }

            } catch (Exception e) {
                System.out.println(e.toString());
            }
            opTab.put(tokens[0], new Pair<>(tokens[1],tokens[2]));
        }
    }
    private void readProgram() {
        List<String[]> program =  Reader.readFile("program.txt");
        for(String [] tokens: program) {
            commands.add(new Command(tokens));
        }
    }

    public Integer getByteInc(String operand) {
        Integer incValue = 0;
        operand = operand.replaceAll("\\s","");
        switch (operand.charAt(0)) {
            case 'X':
                incValue =  (operand.length() - 3 + 1) / 2;
                break;
            case 'C':
                incValue = operand.length() - 3;
                break;
        }
        System.out.println(operand);
        System.out.println(incValue + "\n");
        return incValue;
    }

    public Integer getWordInc(String operand) {
        Integer incValue = 3 * operand.replaceAll("\\s","").trim().split("\\s*,\\s*").length;
        return incValue;
    }


    private Integer getIncValue(String directive, String operand) {
        Integer incValue = 0;
        if(operand == null) {
            return incValue;
        }
        switch(directive) {
            case "RESB":
                incValue = 1 * Integer.parseInt(operand);
                break;
            case "RESW":
                incValue = 3 * Integer.parseInt(operand);
                break;
            case "BYTE":
                incValue = getByteInc(operand);
                break;
            case "WORD":
                incValue = getWordInc(operand);
        }
        return incValue;
    }

    public void passOne() {
        Integer curAddr = Integer.parseInt(commands.get(0).operand, 16);
        for(Command curComm : commands) {
            symTab.put(curComm.label, curAddr);
            curComm.address = curAddr;
            if (curComm.isDirective) {   //Special handling is required
                curAddr += getIncValue(curComm.mnemonic, curComm.operand);
            } else {
                curAddr += Integer.valueOf(curComm.format);
            }
        }
        printCommands();
    }

    void printCommands() {
        System.out.println("Print Commands");
        for(Command curCommand : commands) {

            System.out.println(curCommand.label + " " + curCommand.mnemonic + " " +
                    Integer.toHexString(curCommand.address).toUpperCase());
        }

    }

    class Command {

        String format;
        private String label;
        private String mnemonic;
        private String operand;
        private Integer address;
        private boolean isDirective = false;

        public Command(String [] tokens) {
            Integer index = 0;
            if(!opTab.containsKey(tokens[index])) {
                label = tokens[index++];
            }
            mnemonic = tokens[index++];
            format = opTab.get(mnemonic).getKey();
            isDirective = format.equals("ALLOC") || format.equals("X");
            if(!format.equals(1) && !mnemonic.equals("END")) {
                operand = "";
                for(; index < tokens.length; index++) {
                    operand += tokens[index];
                }

            }
        }
    }
}