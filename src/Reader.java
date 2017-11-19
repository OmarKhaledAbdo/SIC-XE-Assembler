import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Reader {
    static public List<String[]> readFile(String fileName) {
        BufferedReader buffread;
        List <String[]> ret = new ArrayList <> ();
        try {
            String str;
            buffread = new BufferedReader(new FileReader(new File(fileName)));
            while((str = buffread.readLine()) != null) {
                str = str.toUpperCase();
                ret.add(str.trim().split(" +"));
            }
            buffread.close();
        }
        catch (IOException e) {
        }
        return ret;
    }
}