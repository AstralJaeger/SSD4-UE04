package at.fhooe.ssd4.ue04.sax.greeting;

public interface AbstractGreetingProviderFactory {
    public AbstractGreetingProvider getGreetingProviderFactory(String gender, String name, String prefix, String suffix) throws IllegalArgumentException;
}
