package tests.sax.greeting;

public abstract class AbstractGreetingProvider {
    protected final String prefix;
    protected final String suffix;
    protected String name;

    protected AbstractGreetingProvider(String name, String prefix, String suffix) {
        if (prefix != null && prefix.isBlank()) this.prefix = null;
        else this.prefix = prefix;
        if (suffix != null && suffix.isBlank()) this.suffix = null;
        else this.suffix = suffix;
        this.name = name;
    }

    public abstract String provideGreeting();
}
