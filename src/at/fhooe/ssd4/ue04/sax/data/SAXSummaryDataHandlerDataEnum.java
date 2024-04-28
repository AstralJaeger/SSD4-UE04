package at.fhooe.ssd4.ue04.sax.data;

public enum SAXSummaryDataHandlerDataEnum {

    GENDER("gender"),
    PREFIX("prefix"),
    SUFFIX("suffix"),
    NAME("name"),
    SURNAME("surname");

    private String data;

    private SAXSummaryDataHandlerDataEnum(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
