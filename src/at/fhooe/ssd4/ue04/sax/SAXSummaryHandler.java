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
        var padding = "  ".repeat(stateStack.size());
        switch (qName.toLowerCase()) {
            case "fitnessdokument" -> {
                handleStartOfFitnessDocument();
                stateStack.push(SAXSummaryHandlerState.FITNESS_DOCUMENT);
            }
            case "person" -> {
                handleStartOfPerson(attributes);
                stateStack.push(SAXSummaryHandlerState.PERSON);

            }
            case "titel" -> {
                if (attributes.getValue("position").toLowerCase().equals("vor"))
                    stateStack.push(SAXSummaryHandlerState.TITLE_PREFIX);
                else if (attributes.getValue("position").toLowerCase().equals("nach"))
                    stateStack.push(SAXSummaryHandlerState.TITLE_SUFFIX);
            }
            case "vorname" -> {
                stateStack.push(SAXSummaryHandlerState.NAME);
            }
            case "nachname" -> {
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
            case FITNESS_DOCUMENT -> {
                writer.println("");
            }
            case PERSON -> {
                handleEndOfPerson();
                stateStack.push(SAXSummaryHandlerState.PERSON);
            }
            case TITLE_PREFIX -> {

            }
            case TITLE_SUFFIX -> {

            }
            case NAME -> {

            }
            case SURNAME -> {

            }
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
        var value = new String(ch, start, length);
        switch (stateStack.peek()) {
            case TITLE_PREFIX -> handleTitleCharacters(value, SAXSummaryDataHandlerDataEnum.PREFIX);
            case TITLE_SUFFIX -> handleTitleCharacters(value, SAXSummaryDataHandlerDataEnum.SUFFIX);
            case NAME -> this.saxSummaryDataHandler.putValue(SAXSummaryDataHandlerDataEnum.NAME, value);
            case SURNAME -> this.saxSummaryDataHandler.putValue(SAXSummaryDataHandlerDataEnum.SURNAME, value);
        }
    }

    private void handleTitleCharacters(String value, SAXSummaryDataHandlerDataEnum key) {
        if (this.saxSummaryDataHandler.hasValue(key)) {
            this.saxSummaryDataHandler.putValue(key, STR."\{this.saxSummaryDataHandler.getValue(key)} \{value}");
        } else {
            this.saxSummaryDataHandler.putValue(key, value);
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        // ignore: this method is left empty on purpose
    }
}
