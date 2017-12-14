import java.util.List;

/**
 * Created by HeshamSaleh on 12/13/17.
 */
public class DefRecord {

    private String label;
    private String address;

    public DefRecord (List<String> extDef, Section sec) {
        for(String def : extDef) {
            add(def, Integer.toHexString(sec.getSymTab().getAddress(def)));
        }
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body = "";

    public void add(String label, String address) {
        if(body == null) {
            body = label + " " + NumberUtils.adjustSize(address, 6);
        } else {
            body += " " + label + " " + NumberUtils.adjustSize(address, 6);
        }
    }
}