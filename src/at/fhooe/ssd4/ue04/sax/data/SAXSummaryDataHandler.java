package at.fhooe.ssd4.ue04.sax.data;

public class SAXSummaryDataHandler<T> extends AbstractSAXSummaryDataHandler<T> {

    protected SAXSummaryDataHandler() {
        super();
    }

    @Override
    public void putValue(SAXSummaryDataHandlerDataEnum key, T value) {
        super.putValue(key, value);
    }

    @Override
    public T getValue(SAXSummaryDataHandlerDataEnum key) {
        return super.getValue(key);
    }
}
