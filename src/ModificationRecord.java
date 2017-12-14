import java.util.ArrayList;
import java.util.List;

/**
 * Created by HeshamSaleh on 12/13/17.
 */
public class ModificationRecord {
    private String modRec = "";

    public String getModRec() {
        return modRec;
    }

    public void setModRec(String modRec) {
        this.modRec = modRec;
    }

    public void add(int address, List<String> extRef, String halfByte) {
        for(int i = 0; i < extRef.size(); i++) {
            modRec += NumberUtils.adjustSize(Integer.toHexString(address).toUpperCase(),6) + halfByte + extRef.get(i);
            if(i+1 < extRef.size())
                modRec += "\nM ";
        }
    }
}