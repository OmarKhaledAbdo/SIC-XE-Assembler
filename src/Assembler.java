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
        objectProgram.setDefRecord(new DefRecord(section.getExtDef(), section));
        objectProgram.setRefRecord(new RefRecord(section.getExtRef()));

        Integer curLiteralPool = 0;
        for (Command curCommand : section.getCommands()) {
            curCommand.constructMachineCode(section);
            if (curCommand.getMnemonic().equals("LTORG") || curCommand.getMnemonic().equals("END")) {
                Literal[] literals = section.getLitTab().getLiteralPool(curLiteralPool);
                for (Literal literal : literals) {
                    objectProgram.addToTextRecords(null, literal.getValue(), literal.getAddress());
                }
                curLiteralPool++;
            } else {
                objectProgram.addToTextRecords(curCommand.getMnemonic(), curCommand.getMachineCode(), curCommand.getAddress());
                //System.out.println("extRef: " + curCommand.extRef);
                if (curCommand.extRef.size() > 0) {
                    ModificationRecord modRec = new ModificationRecord();
                    if (curCommand.getMnemonic().equals("WORD"))
                        modRec.add(curCommand.getAddress(), curCommand.extRef, " 06 ");
                    else
                        modRec.add(curCommand.getAddress() + 1, curCommand.extRef, " 05 ");
                    objectProgram.addToModificationRecords(modRec);
                }
                System.out.println(curCommand.getMnemonic() + curCommand.absolute);
                if (curCommand.absolute != null) {
                    System.out.println("Absolute Good");
                    Character sign = curCommand.absolute < 0 ? '-' : '+';
                    for (int i = 0; i < Math.abs(curCommand.absolute); i++) {
                        ModificationRecord modRec = new ModificationRecord();
                        if (curCommand.getMnemonic().equals("WORD")) {
                            modRec.add(curCommand.getAddress(), sign + objectProgram.getHeaderRecord().getProgramName(), " 06 ");
                        }
                        else {
                            modRec.add(curCommand.getAddress(), sign + objectProgram.getHeaderRecord().getProgramName(), " 05 ");
                        }
                        objectProgram.addToModificationRecords(modRec);
                    }

                }
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