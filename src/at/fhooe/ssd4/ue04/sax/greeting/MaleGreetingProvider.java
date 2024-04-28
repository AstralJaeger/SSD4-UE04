package at.fhooe.ssd4.ue04.sax.greeting;

public class MaleGreetingProvider extends AbstractGreetingProvider {

    public MaleGreetingProvider(String name, String prefix, String suffix) {
        super(name, prefix, suffix);
    }

    @Override
    public String provideGreeting() {
        StringBuilder greeting = new StringBuilder("Sehr geehrter Herr ");
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
