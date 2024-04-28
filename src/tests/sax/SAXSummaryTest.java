package tests.sax;

import java.io.File;
import java.io.PrintWriter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class SAXSummaryTest {

  public static void main(String[] args) {
    System.out.println(SAXSummaryTest.class.getSimpleName() + " BEGIN");

    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setValidating(true);
    SAXParser saxParser;
    String xmlFilesDir = "xmlfiles/";
    String inFile = xmlFilesDir + "ue4_fitnessdokument.xml";

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

    System.out.println(SAXSummaryTest.class.getSimpleName() + " END");
  }
}
