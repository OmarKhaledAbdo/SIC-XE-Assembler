public class HeaderRecord {
    private String programName;
    private Integer startAddr;
    private Integer length;

    public HeaderRecord(String programName, Integer startAddr, Integer length) {
        this.programName = programName;
        this.startAddr = startAddr;
        this.length = length;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public Integer getStartAddr() {
        return startAddr;
    }

    public void setStartAddr(Integer startAddr) {
        this.startAddr = startAddr;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
