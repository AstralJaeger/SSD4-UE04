package at.fhooe.ssd4.ue04.sax.data;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSAXSummaryDataHandler<T> implements AbstractSAXSummaryDataHandlerInterface<T> {

    protected final Map<SAXSummaryDataHandlerDataEnum, T> dataStore;

    protected AbstractSAXSummaryDataHandler() {
        this.dataStore = new HashMap<>(SAXSummaryDataHandlerDataEnum.values().length + 2);
    }

    public void putValue(SAXSummaryDataHandlerDataEnum key, T value) {
        dataStore.put(key, value);
    }

    public T getValue(SAXSummaryDataHandlerDataEnum key) {

        if (!dataStore.containsKey(key)) {
            throw new SAXSummaryDataHandlerValueNotFoundException(STR."key \{key} not found, \{dataStore.size()} values contained");
        }
        return dataStore.get(key);
    }
}
