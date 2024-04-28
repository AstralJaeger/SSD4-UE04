package tests.sax.greeting;

public class GreetingProviderFactoryImpl implements AbstractGreetingProviderFactory {

    @Override
    public AbstractGreetingProvider getGreetingProviderFactory(String gender, String name, String prefix, String suffix) throws IllegalArgumentException {

        switch (gender.toLowerCase()) {
            case "male":
                return new MaleGreetingProvider(name, prefix, suffix);
            case "female":
                return new FemaleGreetingProvider(name, prefix, suffix);
            default:
                throw new IllegalArgumentException("Unsupported gender");
        }
    }
}

