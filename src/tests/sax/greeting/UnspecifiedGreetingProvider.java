package tests.sax.greeting;

public class UnspecifiedGreetingProvider extends AbstractGreetingProvider {

    public UnspecifiedGreetingProvider(String name, String prefix, String suffix) {
        super(name, prefix, suffix);
    }

    @Override
    public String provideGreeting() {
        StringBuilder greeting = new StringBuilder("Hallo ");
        if (super.prefix != null) {
            greeting.append(super.prefix).append(" ");
        }
        greeting.append(super.name);
        if (super.suffix != null) {
            greeting.append(" ").append(super.suffix);
        }
        return greeting.toString();
    }
}
