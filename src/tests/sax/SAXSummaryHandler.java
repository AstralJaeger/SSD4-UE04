package tests.sax;

import java.io.PrintWriter;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import tests.sax.greeting.AbstractGreetingProvider;
import tests.sax.greeting.AbstractGreetingProviderFactory;
import tests.sax.greeting.SingletonGreetingProviderFactoryImpl;

public class SAXSummaryHandler extends DefaultHandler {

    private PrintWriter writer = null;
    private final StringBuilder builder;
    private final Stack<SAXSummaryHandlerState> stateStack;

    public SAXSummaryHandler() {
        super();
        this.builder = new StringBuilder();
        this.stateStack = new Stack<>();
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
        writer.flush();
        writer.close();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        var padding = "  ".repeat(stateStack.size() + 1);
        switch (qName.toLowerCase()) {
            case "fitnessdokument" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.FITNESS_DOCUMENT}");
                stateStack.push(SAXSummaryHandlerState.FITNESS_DOCUMENT);
            }
            case "person" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.PERSON}");
                stateStack.push(SAXSummaryHandlerState.PERSON);
            }
            case "titel" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.TITLE}");
                stateStack.push(SAXSummaryHandlerState.TITLE);
            }
            case "vorname" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.NAME}");
                stateStack.push(SAXSummaryHandlerState.NAME);
            }
            case "nachname" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.SURNAME}");
                stateStack.push(SAXSummaryHandlerState.SURNAME);
            }
            case "vitaldaten" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.VITALDATA}");
                stateStack.push(SAXSummaryHandlerState.VITALDATA);
            }
            case "messung" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.MEASUREMENT}");
                stateStack.push(SAXSummaryHandlerState.MEASUREMENT);
            }
            case "messwert" -> {
                writer.println(STR."\{padding}+\{SAXSummaryHandlerState.MEASUREMENT_VALUE}");
                stateStack.push(SAXSummaryHandlerState.MEASUREMENT_VALUE);
            }
            default -> {
                writer.println(STR."\{padding}+default");
                stateStack.push(SAXSummaryHandlerState.IGNORED_STATE);
            }

        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (stateStack.empty())
            return;

        var padding = "  ".repeat(stateStack.size());
        var state = stateStack.pop();
        if (state != SAXSummaryHandlerState.IGNORED_STATE) {
            writer.println(STR."\{padding}-\{state}");
        } else {
            writer.println(STR."\{padding}-default ");
        }
    }

    private void handleFitnessDocument() {
        writer.println("Information zum Fitnessdokument");
    }

    private void handlePerson(Attributes attributes) {
        String gender = attributes.getValue("geschlecht");
        String name = attributes.getValue("vorname");
        String surname = attributes.getValue("nachname");
        SingletonGreetingProviderFactoryImpl singletonGreetingProviderFactoryImpl = SingletonGreetingProviderFactoryImpl.getInstance();
        AbstractGreetingProviderFactory greetingProviderFactory = singletonGreetingProviderFactoryImpl.getGreetingProviderFactory();
        AbstractGreetingProvider greetingProvider = greetingProviderFactory.getGreetingProviderFactory(gender, STR."\{name} \{surname}", "", "");
        writer.println(greetingProvider.provideGreeting());
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
//        writer.println(new String(ch, start, length));
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // ignore: this method is left empty on purpose
    }
}
