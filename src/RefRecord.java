import java.util.Set;

/**
 * Created by HeshamSaleh on 12/13/17.
 */
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
