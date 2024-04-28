package at.fhooe.ssd4.ue04.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class SAXSummaryTest {

    public static void main(String[] args) {
        String[] files = {"ue4_fitnessdokument.xml", "ue4_fitnessdokument_spezial.xml"};
        for (String file : files) {
            processFile(file);
        }
    }

    public static void processFile(String filename) {
        System.out.println(SAXSummaryTest.class.getSimpleName() + " BEGIN " + filename);

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser;
        String xmlFilesDir = "xmlfiles/";
        String inFile = xmlFilesDir + filename;

        try {
            saxParser = factory.newSAXParser();
            File file = new File(inFile);
            var handler = new SAXSummaryHandler();
            handler.setWriter(new PrintWriter(System.out));
            saxParser.parse(file, handler);
        } catch (Exception e) {
            System.out.println("Exception Type: " + e.getClass().toString());
            System.out.println("Exception Message: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(SAXSummaryTest.class.getSimpleName() + " END " + filename);
    }
}
