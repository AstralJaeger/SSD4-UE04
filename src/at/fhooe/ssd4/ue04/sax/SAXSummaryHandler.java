package at.fhooe.ssd4.ue04.sax;

import java.io.PrintWriter;
import java.util.Stack;

import at.fhooe.ssd4.ue04.sax.data.AbstractSAXSummaryDataHandler;
import at.fhooe.ssd4.ue04.sax.data.SingletonSAXSummaryDataHandlerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import at.fhooe.ssd4.ue04.sax.data.SAXSummaryDataHandlerDataEnum;
import at.fhooe.ssd4.ue04.sax.greeting.AbstractGreetingProvider;
import at.fhooe.ssd4.ue04.sax.greeting.AbstractGreetingProviderFactory;
import at.fhooe.ssd4.ue04.sax.greeting.SingletonGreetingProviderFactoryImpl;

public class SAXSummaryHandler extends DefaultHandler {

    private PrintWriter writer = null;
    private final StringBuilder builder;
    private final Stack<SAXSummaryHandlerState> stateStack;
    private final AbstractSAXSummaryDataHandler<String> saxSummaryDataHandler;

    public SAXSummaryHandler() {
        super();
        this.builder = new StringBuilder();
        this.stateStack = new Stack<>();
        this.saxSummaryDataHandler = SingletonSAXSummaryDataHandlerFactory.getInstance().getSAXSummaryDataHandler();
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
                handleStartOfFitnessDocument();
                stateStack.push(SAXSummaryHandlerState.FITNESS_DOCUMENT);
            }
            case "person" -> {
                handleStartOfPerson(attributes);
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

    private void handleStartOfPerson(Attributes attrs) {
        this.saxSummaryDataHandler.putValue(SAXSummaryDataHandlerDataEnum.GENDER, attrs.getValue("geschlecht"));
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (stateStack.empty())
            return;

        var padding = "  ".repeat(stateStack.size());
        var state = stateStack.pop();
        switch (state) {
            case PERSON -> {
                handleEndOfPerson();
                stateStack.push(SAXSummaryHandlerState.PERSON);
            }
        }


        if (state != SAXSummaryHandlerState.IGNORED_STATE) {
            writer.println(STR."\{padding}-\{state}");
        } else {
            writer.println(STR."\{padding}-default ");
        }
    }

    private void handleStartOfFitnessDocument() {
        writer.println("Information zum Fitnessdokument");
    }

    private void handleEndOfPerson() {
        String name = this.saxSummaryDataHandler.getValue(SAXSummaryDataHandlerDataEnum.NAME);
        String surname = this.saxSummaryDataHandler.getValue(SAXSummaryDataHandlerDataEnum.SURNAME);
        SingletonGreetingProviderFactoryImpl singletonGreetingProviderFactoryImpl = SingletonGreetingProviderFactoryImpl.getInstance();
        AbstractGreetingProviderFactory greetingProviderFactory = singletonGreetingProviderFactoryImpl.getGreetingProviderFactory();
        AbstractGreetingProvider greetingProvider = greetingProviderFactory.getGreetingProviderFactory(
                this.saxSummaryDataHandler.getValue(SAXSummaryDataHandlerDataEnum.GENDER),
                STR."\{name} \{surname}",
                this.saxSummaryDataHandler.getValue(SAXSummaryDataHandlerDataEnum.PREFIX),
                this.saxSummaryDataHandler.getValue(SAXSummaryDataHandlerDataEnum.SUFFIX)
        );
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
