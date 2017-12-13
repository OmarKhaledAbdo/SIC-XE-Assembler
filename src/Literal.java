public class Literal {
    private String value;
    private Integer length;
    private Integer address;
    public Literal(Instruction curCommand) {
        this.setValue(curCommand.getOperandHexValue());
        this.setLength(curCommand.getByteInc());
        this.setAddress(null); //Address still undetermined
    }
    public String toString() {
        return String.format("Value: %s"  + " Length: %s"  + " Address: %s",
                getValue(), getLength(), Integer.toHexString(getAddress()).toUpperCase());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getAddress() {
        return address;
    }

    public void setAddress(Integer address) {
        this.address = address;
    }
}