import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Assembler {

    private Map<String, Pair<String,String>> opTab = new HashMap<>();
    private Map<String, Integer> symTab = new HashMap<>();
    private ArrayList<Command> commands = new ArrayList<>();

    public Assembler() {
        constructOpTab();
        readProgram();
    }

    private void constructOpTab() {
        BufferedReader buffread;
        try {
            String str;
            buffread = new BufferedReader(new FileReader(new File("InstructionSet.txt")));
            while((str = buffread.readLine()) != null) {
                String[] tokens = str.trim().split(" +");  ///Changed from space.
                opTab.put(tokens[0],new Pair<>(tokens[1],tokens[2]));
            }
            buffread.close();
        } catch (IOException e) {

        }
    }

    private void readProgram() {
        BufferedReader buffread;
        try {
            String str;
            buffread = new BufferedReader(new FileReader(new File("program.txt")));
            while((str = buffread.readLine()) != null) {
                commands.add(new Command(str));
            }
            buffread.close();
        } catch (IOException e) {
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

        public Command(String command) {
            String[] tokens = command.trim().split(" +"); ///Changed from space.
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
