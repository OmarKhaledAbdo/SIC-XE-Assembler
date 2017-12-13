import java.util.ArrayList;

public class ObjectProgram implements Printable{
    private HeaderRecord headerRecord;
    private DefRecord defRecord;

    private RefRecord refRecord;
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

    public DefRecord getDefRecord() { return defRecord; }

    public void setDefRecord(DefRecord defRecord) { this.defRecord = defRecord; }

    public RefRecord getRefRecord() { return refRecord; }

    public void setRefRecord(RefRecord refRecord) { this.refRecord = refRecord; }

    public void setEndRecord(EndRecord endRecord) {
        this.endRecord = endRecord;
    }

    public ArrayList<TextRecord> getTextRecords() {
        return textRecords;
    }
    public void setTextRecords(ArrayList<TextRecord> textRecords) {
        this.textRecords = textRecords;
    }


    boolean startNew = false;

    public void addToTextRecords (String mnemonic, String machineCode, Integer addr) {

        if(mnemonic != null && (mnemonic.equals("RESW") || mnemonic.equals("RESB"))) {
            startNew = true;
            return;
        }

        if(textRecords.isEmpty() ||
                startNew && textRecords.get(textRecords.size() - 1).getLength() > 0 ||
                textRecords.get(textRecords.size() - 1).getLength() + machineCode.length() > 60) {
            textRecords.add(new TextRecord(addr));
        }

        startNew = false;
        textRecords.get(textRecords.size() - 1).addToBody(machineCode);
    }

    @Override
    public void print() {
        String startAddr = NumberUtils.adjustSize(Integer.toHexString(getEndRecord().getStartAddr()),6).toUpperCase();
        String programLength = NumberUtils.adjustSize(Integer.toHexString(getHeaderRecord().getLength()),6).toUpperCase();
        System.out.println("HTE Record:");
        System.out.println("H " + getHeaderRecord().getProgramName() + " " + startAddr + " " + programLength);
        System.out.println("D " + getDefRecord().getBody().toUpperCase());
        System.out.println("R " + getRefRecord().getBody().toUpperCase());
        for(TextRecord record : getTextRecords()) {
            String firstExec = NumberUtils.adjustSize(Integer.toHexString(record.getFirstExec()),6);
            String recordLength = NumberUtils.adjustSize(Integer.toHexString(record.getLength()/2).toUpperCase(),2);
            System.out.println("T " + firstExec.toUpperCase() + " " + recordLength.toUpperCase() + record.getBody().toUpperCase());
        }
        System.out.println("E " + startAddr);
    }
}