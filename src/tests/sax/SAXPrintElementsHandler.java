package tests.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class SAXPrintElementsHandler extends DefaultHandler {

    private int depth = 0;
    private StringBuffer blanks = new StringBuffer();

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        System.out.println("*".repeat(10) + " Start Document " + "*".repeat(10));
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
        System.out.println(blanks.toString() + "*".repeat(10) + " End Document " + "*".repeat(10));
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        depth++;
        blanks.append(" ".repeat(3));
        System.out.println(blanks.toString() + depth + " - " + qName);
        for (var i = 0; i < attributes.getLength(); i++) {
            System.out.println(blanks.toString() + " @ " + attributes.getQName(i) + " : " + attributes.getValue(i) + " ".repeat(3));
        }
        System.out.println();
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String s = new String(ch, start, length);
        System.out.println(blanks.toString() + " ".repeat(3) + (depth + 1) + " - '" + s + "'");
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        depth--;
        blanks.delete(0, 3);
        System.out.println(blanks.toString() + "*".repeat(10) + " End Element " + "*".repeat(10));
    }

    //
    // ErrorHandler methods
    //

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        printError("Fatal Error", e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        printError("Error", e);
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        printError("Warning", e);
    }

    //
    // Protected methods
    //

    /** Prints the error message. */
    protected void printError(String type, SAXParseException ex) {

        System.err.print("[");
        System.err.print(type);
        System.err.print("] ");
        if (ex == null) {
            System.err.println("!!!");
        }
        String systemId = ex.getSystemId();
        if (systemId != null) {
            int index = systemId.lastIndexOf('/');
            if (index != -1)
                systemId = systemId.substring(index + 1);
            System.err.print(systemId);
        }
        System.err.print(':');
        System.err.print(ex.getLineNumber());
        System.err.print(':');
        System.err.print(ex.getColumnNumber());
        System.err.print(": ");
        System.err.print(ex.getMessage());
        System.err.println();
        System.err.flush();

    } // printError(String,SAXParseException)
}