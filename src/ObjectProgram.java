import java.util.ArrayList;

public class ObjectProgram implements Printable{
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
        String startAddr = NumberUtils.adjustSize(Integer.toHexString(getEndRecord().getStartAddr()),6);
        String programLength = NumberUtils.adjustSize(Integer.toHexString(getHeaderRecord().getLength()),6);
        System.out.println("HTE Record:");
        System.out.println("H " + getHeaderRecord().getProgramName() + " " + startAddr + " " + programLength);
        for(TextRecord record : getTextRecords()) {
            String firstExec = NumberUtils.adjustSize(Integer.toHexString(record.getFirstExec()),6);
            String recordLength = NumberUtils.adjustSize(Integer.toHexString(record.getLength()/2).toUpperCase(),2);
            System.out.println("T " + firstExec + " " + recordLength + record.getBody().toUpperCase());
        }
        System.out.println("E " + startAddr);
    }
}