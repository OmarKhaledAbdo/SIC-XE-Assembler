public class TextRecord {
    private String firstExec;
    private String body;
    private Integer length;

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }



    public String getFirstExec() {
        return firstExec;
    }

    public void setFirstExec(String firstExec) {
        this.firstExec = firstExec;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public  void addToBody (String machineCode) {
        if(body.isEmpty()) {
            body += machineCode;
        } else {
            body += " " + machineCode;
        }
        length += machineCode.length();
    }


}


