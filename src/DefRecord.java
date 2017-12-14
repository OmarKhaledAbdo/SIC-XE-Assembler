import java.util.List;

public class DefRecord {

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