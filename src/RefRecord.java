/**
 * Created by HeshamSaleh on 12/13/17.
 */
public class RefRecord {
    private String label;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    private String body;

    public void add(String label) {
        if(body == null) {
            body = label;
        } else {
            body += " " + label;
        }
    }
}
