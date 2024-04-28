package tests.sax.greeting;

public interface AbstractGreetingProviderFactory {
    public AbstractGreetingProvider getGreetingProviderFactory(String gender, String name, String prefix, String suffix) throws IllegalArgumentException;
}
