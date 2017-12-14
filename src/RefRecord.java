import java.util.Set;


public class RefRecord {

    public RefRecord (Set<String> extRef) {
        for(String ref : extRef) {
            add(ref);
        }
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body = "";

    public void add(String label) {
        if(body == null) {
            body = label;
        } else {
            body += " " + label;
        }
    }
}
