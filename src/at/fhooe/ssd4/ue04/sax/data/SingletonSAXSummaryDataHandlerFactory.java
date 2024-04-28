package at.fhooe.ssd4.ue04.sax.data;

public class SingletonSAXSummaryDataHandlerFactory implements AbstractSAXSummaryDataHandlerFactory<java.lang.String> {

    private static SingletonSAXSummaryDataHandlerFactory instance = null;

    protected AbstractSAXSummaryDataHandler<String> handlerInstance = null;

    private SingletonSAXSummaryDataHandlerFactory() {
    }

    public static SingletonSAXSummaryDataHandlerFactory getInstance() {
        if (instance == null) {
            instance = new SingletonSAXSummaryDataHandlerFactory();
        }
        return instance;
    }

    @Override
    public AbstractSAXSummaryDataHandler<String> getSAXSummaryDataHandler() {
        if (handlerInstance == null) {
            this.handlerInstance = new SAXSummaryDataHandler<>();
        }
        return this.handlerInstance;
    }
}
