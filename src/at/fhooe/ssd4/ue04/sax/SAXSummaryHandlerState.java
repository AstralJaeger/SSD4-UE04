package at.fhooe.ssd4.ue04.sax;

public enum SAXSummaryHandlerState {
    NONE(""),
    FITNESS_DOCUMENT("fitnessdokument"),
    PERSON("person"),
    TITLE_PREFIX("titel_vor"),
    TITLE_SUFFIX("titel_nach"),
    NAME("vorname"),
    SURNAME("nachname"),
    VITALDATA("vitaldaten"),
    MEASUREMENT("messung"),
    MEASUREMENT_VALUE("messwert"),
    NOTE("notiz"),
    IGNORED_STATE("other");

    private final String value;

    private SAXSummaryHandlerState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
