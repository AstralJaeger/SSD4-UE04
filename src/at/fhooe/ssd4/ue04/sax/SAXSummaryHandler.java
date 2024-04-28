package at.fhooe.ssd4.ue04.sax;

import java.io.PrintWriter;
import java.util.Stack;

import at.fhooe.ssd4.ue04.sax.data.AbstractSAXSummaryDataHandler;
import at.fhooe.ssd4.ue04.sax.data.SingletonSAXSummaryDataHandlerFactory;
import org.w3c.dom.Attr;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import at.fhooe.ssd4.ue04.sax.data.SAXSummaryDataHandlerDataEnum;
import at.fhooe.ssd4.ue04.sax.greeting.AbstractGreetingProvider;
import at.fhooe.ssd4.ue04.sax.greeting.AbstractGreetingProviderFactory;
import at.fhooe.ssd4.ue04.sax.greeting.SingletonGreetingProviderFactoryImpl;

public class SAXSummaryHandler extends DefaultHandler {

    private PrintWriter writer = null;
    private final Stack<SAXSummaryHandlerState> stateStack;
    private final AbstractSAXSummaryDataHandler<String> saxSummaryDataHandler;

    private final Stack<String> measurementTracker;

    public SAXSummaryHandler() {
        super();
        this.stateStack = new Stack<>();
        this.saxSummaryDataHandler = SingletonSAXSummaryDataHandlerFactory.getInstance().getSAXSummaryDataHandler();
        this.measurementTracker = new Stack<>();
    }

    public void setWriter(PrintWriter writer) {
        this.writer = writer;
    }

    @Override
    public void endDocument() throws SAXException {
        writer.flush();
        writer.close();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
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
                if (attributes.getValue("position").equalsIgnoreCase("vor"))
                    stateStack.push(SAXSummaryHandlerState.TITLE_PREFIX);
                else if (attributes.getValue("position").equalsIgnoreCase("nach"))
                    stateStack.push(SAXSummaryHandlerState.TITLE_SUFFIX);
            }
            case "vorname" -> {
                stateStack.push(SAXSummaryHandlerState.NAME);
            }
            case "nachname" -> {
                stateStack.push(SAXSummaryHandlerState.SURNAME);
            }
            case "vitaldaten" -> {
                handleStartOfVitaldata(attributes);
                stateStack.push(SAXSummaryHandlerState.VITALDATA);
            }
            case "messung" -> {
                handleStartOfMeasurement(attributes);
                stateStack.push(SAXSummaryHandlerState.MEASUREMENT);
            }
            case "messwert" -> {
                handleStartOfMeasurementValue(attributes);
                stateStack.push(SAXSummaryHandlerState.MEASUREMENT_VALUE);
            }
            case "notiz" -> {
                stateStack.push(SAXSummaryHandlerState.NOTE);
            }
            default -> stateStack.push(SAXSummaryHandlerState.IGNORED_STATE);

        }
    }

    private void handleStartOfFitnessDocument() {
        writer.println("Information zum Fitnessdokument");
    }

    private void handleStartOfPerson(Attributes attrs) {
        this.saxSummaryDataHandler.putValue(SAXSummaryDataHandlerDataEnum.GENDER, attrs.getValue("geschlecht"));
    }

    private void handleStartOfVitaldata(Attributes attrs) {
        if (!measurementTracker.empty()) {
            throw new IllegalStateException("measurementTracker should be empty at beginning of 'Vitaldaten' but has size " + measurementTracker.size());
        }
        writer.println("Folgende Messungen wurden maschinell durchgeführt:");
    }

    private void handleStartOfMeasurement(Attributes attrs) {
        if (!measurementTracker.empty()) {
            throw new IllegalStateException("measurementTracker should be empty at beginning of 'Messung' but has size " + measurementTracker.size());
        }
    }

    private void handleStartOfMeasurementValue(Attributes attrs) {
        var type = attrs.getValue("typ");
        var value = attrs.getValue("wert");
        var unit = attrs.getValue("einheit");

        measurementTracker.push(STR."- \{type}: \{value} \{unit}");
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (stateStack.empty())
            return;

        var state = stateStack.pop();
        switch (state) {
            case PERSON -> handleEndOfPerson();
            case MEASUREMENT -> handleEndOfMeasurement();
            case NOTE -> handleEndOfNote();
        }
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

    private void handleEndOfMeasurement() {
        measurementTracker.forEach(mv -> writer.println(mv));
        measurementTracker.clear();
    }

    private void handleEndOfNote() {

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        var value = new String(ch, start, length);
        switch (stateStack.peek()) {
            case TITLE_PREFIX -> handleTitleCharacters(value, SAXSummaryDataHandlerDataEnum.PREFIX);
            case TITLE_SUFFIX -> handleTitleCharacters(value, SAXSummaryDataHandlerDataEnum.SUFFIX);
            case NAME -> this.saxSummaryDataHandler.putValue(SAXSummaryDataHandlerDataEnum.NAME, value);
            case SURNAME -> this.saxSummaryDataHandler.putValue(SAXSummaryDataHandlerDataEnum.SURNAME, value);
            case NOTE -> {
                if (!value.equalsIgnoreCase("maschinell"))
                    measurementTracker.clear();
            }
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
