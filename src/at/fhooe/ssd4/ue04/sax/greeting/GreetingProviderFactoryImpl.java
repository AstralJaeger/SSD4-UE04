package at.fhooe.ssd4.ue04.sax.greeting;

public class GreetingProviderFactoryImpl implements AbstractGreetingProviderFactory {

    @Override
    public AbstractGreetingProvider getGreetingProviderFactory(String gender, String name, String prefix, String suffix) throws IllegalArgumentException {
        return switch (gender.toLowerCase()) {
            case "männlich" -> new MaleGreetingProvider(name, prefix, suffix);
            case "weiblich" -> new FemaleGreetingProvider(name, prefix, suffix);
            case "divers" -> new DiverseGreetingProvider(name, prefix, suffix);
            default -> new UnspecifiedGreetingProvider(name, prefix, suffix);
        };
    }
}

