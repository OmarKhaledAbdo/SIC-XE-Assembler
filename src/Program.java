import java.util.ArrayList;
import java.util.List;


public class Program {
    private ArrayList<Command> commands = new ArrayList<>();

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public Program (String file, OpCodeTable opTab) {
        List<String[]> program = Reader.readFile(file);
        for (String[] tokens : program) {
            commands.add(new Command(tokens, opTab));
        }
    }



    void print() {
        System.out.println("\nProgram addresses\n");
        for (Command curCommand :commands) {
            System.out.println(curCommand);
            //System.out.println(curCommand.getLabel() + " " + curCommand.getMnemonic() + " " +
                    //(curCommand.getAddress() != null ? Integer.toHexString(curCommand.getAddress()).toUpperCase() : ""));
        }
    }
}
