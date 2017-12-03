import java.util.ArrayList;

public class ObjectProgram {
    private HeaderRecord headerRecord;

    private EndRecord endRecord;
    private ArrayList<TextRecord> textRecords = new ArrayList<>();

    public HeaderRecord getHeaderRecord() {
        return headerRecord;
    }

    public void setHeaderRecord(HeaderRecord headerRecord) {
        this.headerRecord = headerRecord;
    }

    public EndRecord getEndRecord() {
        return endRecord;
    }

    public void setEndRecord(EndRecord endRecord) {
        this.endRecord = endRecord;
    }

    public ArrayList<TextRecord> getTextRecords() {
        return textRecords;
    }

    public void setTextRecords(ArrayList<TextRecord> textRecords) {
        this.textRecords = textRecords;
    }
}
