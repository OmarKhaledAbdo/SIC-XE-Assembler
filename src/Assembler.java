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
            opTab.put(tokens[0],new Pair<>(tokens[1],tokens[2]));
        }
    }

    private void readProgram() {
        List<String[]> program =  Reader.readFile("program.txt");
        for(String [] tokens: program) {
            commands.add(new Command(tokens));
        }
    }


    public void passOne() {
        Integer curAddr = Integer.valueOf(commands.get(0).operand);
        for(Command curComm : commands) {
            symTab.put(curComm.label, curAddr);
            curComm.address = curAddr;
            if (curComm.isDirective) {
                int incValue = 0;
                switch (curComm.mnemonic) {
                    case "RESB":
                        incValue = 1;
                        break;
                    case "RESW":
                        incValue = 3;
                        break;
                }
                if(curComm.operand != null) { //Some directives do not have operands, therefore, don't affect the currentAdress
                    curAddr += incValue * Integer.valueOf(curComm.operand);
                }
            } else {
                curAddr += Integer.valueOf(curComm.format);
            }
        }
        printCommands();
    }

    void printCommands() {
        System.out.println("Print Commands");
        for(Command curCommand : commands) {
            System.out.println(curCommand.label + " " + curCommand.mnemonic + " " + curCommand.address);
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
                operand = tokens[index];
            }
        }
    }
}