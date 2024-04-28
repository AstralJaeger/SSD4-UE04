package tests.sax.greeting;

public class SingletonGreetingProviderFactoryImpl implements AbstractSingletonGreetingProviderFactory {

    private static SingletonGreetingProviderFactoryImpl INSTANCE;

    private SingletonGreetingProviderFactoryImpl() {
        // ignore: Kept private on purpose
    }

    public static SingletonGreetingProviderFactoryImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SingletonGreetingProviderFactoryImpl();
        }
        return INSTANCE;
    }

    @Override
    public AbstractGreetingProviderFactory getGreetingProviderFactory() {
        return new GreetingProviderFactoryImpl();
    }
}
