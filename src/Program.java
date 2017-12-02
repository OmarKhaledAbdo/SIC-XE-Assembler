import java.util.ArrayList;
import java.util.List;


public class Program implements Printable{
    private ArrayList<Command> commands = new ArrayList<>();

    public ArrayList<Command> getCommands() {
        return commands;
    }
    public Program (String file, OpCodeTable opTab) {
        List<String[]> program = Reader.readFile(file);
        for (String[] tokens : program) {
            commands.add(Command.create(tokens, opTab));
        }
    }

    public void print() {
        System.out.println("\nProgram Addresses:\n");
        for (Command curCommand :commands) {
            System.out.println(curCommand);
        }
    }
}
