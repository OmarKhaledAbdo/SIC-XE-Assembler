public class TextRecord {

    private Integer firstExec;
    private String body;
    private Integer length = 0;

    public TextRecord(Integer firstExec) {
        this.firstExec = firstExec;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getFirstExec() {
        return firstExec;
    }

    public void setFirstExec(Integer firstExec) {
        this.firstExec = firstExec;
    }

    public  void addToBody (String machineCode) {
        if(body == null) {
            body = machineCode;
        } else {
            body += " " + machineCode;
        }
        length += machineCode.length();
    }
}


