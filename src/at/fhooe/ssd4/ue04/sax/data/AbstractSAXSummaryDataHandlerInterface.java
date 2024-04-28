package at.fhooe.ssd4.ue04.sax.data;

public interface AbstractSAXSummaryDataHandlerInterface<T> {

    public void putValue(SAXSummaryDataHandlerDataEnum key, T value);

    public T getValue(SAXSummaryDataHandlerDataEnum key);
}
