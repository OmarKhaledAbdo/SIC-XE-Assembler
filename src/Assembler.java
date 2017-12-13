import java.util.*;

class Assembler implements Printable {

    private Section section;
    private ObjectProgram objectProgram = new ObjectProgram();
    private Integer lastUsedAddress;

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public ObjectProgram getObjectProgram() {
        return objectProgram;
    }

    public void setObjectProgram(ObjectProgram objectProgram) {
        this.objectProgram = objectProgram;
    }

    public Integer getLastUsedAddress() {
        return lastUsedAddress;
    }

    public void setLastUsedAddress(Integer lastUsedAddress) {
        this.lastUsedAddress = lastUsedAddress;
    }

    public Assembler(Section section) {
        this.section = section;
    }

    public void passOne() {
        Integer startAddr = Integer.parseInt(section.getCommands().get(0).getOperand(), 16);
        Integer curAddr = startAddr;
        for (Command curCommand : section.getCommands()) {
            section.getSymTab().addLabel(curCommand.getLabel(), curAddr);
            section.getLitTab().addLiteral(curCommand);
            curCommand.setAddress(curAddr);
            curAddr += curCommand.handle(curAddr, section);
        }
        lastUsedAddress = curAddr;
    }

    public void passTwo() {
        Integer startAddr = Integer.parseInt(section.getCommands().get(0).getOperand(), 16);

        objectProgram.setHeaderRecord(new HeaderRecord(section.getCommands().get(0).getLabel(),
                startAddr, lastUsedAddress - startAddr));

        DefRecord defRecord = new DefRecord();
        for(String def : section.getExtDef()) {
            defRecord.add(def,Integer.toHexString(section.getSymTab().getAddress(def)));
            objectProgram.setDefRecord(defRecord);
        }

        RefRecord refRecord = new RefRecord();
        for(String ref : section.getExtRef()) {
            refRecord.add(ref);
            objectProgram.setRefRecord(refRecord);
        }

        Integer curLiteralPool = 0;
        for (Command curCommand : section.getCommands()) {
            curCommand.constructMachineCode(section);
            if(curCommand.getMnemonic().equals("LTORG") || curCommand.getMnemonic().equals("END")) {
                Literal [] literals = section.getLitTab().getLiteralPool(curLiteralPool);
                for (Literal literal : literals) {
                    objectProgram.addToTextRecords(null, literal.getValue(), literal.getAddress());
                }
                curLiteralPool++;
            } else {
                objectProgram.addToTextRecords(curCommand.getMnemonic(), curCommand.getMachineCode(), curCommand.getAddress());
            }

        }

        objectProgram.setEndRecord(new EndRecord(startAddr));
    }
    public void print() {
        section.print();
        section.getSymTab().print();
        section.getLitTab().print();
        objectProgram.print();
    }
}