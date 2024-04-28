package at.fhooe.ssd4.ue04.dom;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DOMErrorHandler implements ErrorHandler {
    // treat fatal errors
    @Override
    public void fatalError(SAXParseException spe) throws SAXException {
        System.out.println("[Fatal Error] " + spe.getSystemId() + ", Line " + spe.getLineNumber() + ": "
                + spe.getLocalizedMessage());
        throw spe;
    }

    // Write errors to standard output
    @Override
    public void error(SAXParseException spe) throws SAXParseException {
        System.out.println(
                "[Error] " + spe.getSystemId() + ", Line " + spe.getLineNumber() + ": " + spe.getLocalizedMessage());
        throw spe;
    }

    // Write warnings to standard output
    @Override
    public void warning(SAXParseException spe) throws SAXParseException {
        System.out.println(
                "[Warning] " + spe.getSystemId() + ", Line " + spe.getLineNumber() + ": " + spe.getLocalizedMessage());
    }
}
