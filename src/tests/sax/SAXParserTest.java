package tests.sax;

import java.io.File;

import javax.xml.parsers.SAXParserFactory;

public class SAXParserTest {

  public static void main(String[] args) {
    System.out.println(SAXParserTest.class.getSimpleName() + " BEGIN");

    // 1. Create a new SAXParserFactory object
    var factory = SAXParserFactory.newInstance();
    // 2. If you want to validate against a DTD, you can set it here
    factory.setValidating(true);

    try {
      // 3. Create a new SAXParser object ("built" by the factory)
      var saxParser = factory.newSAXParser();

      File file = new File("xmlfiles/CourseCatalog.xml");

      // 4. Parse the given file
      // - we will have to pass the new handler implementation (inherits from
      // DefaultHandler) as well
      saxParser.parse(file, new SAXPrintElementsHandler());

    } catch (Throwable e) {
      System.out.println("Exception Type: " + e.getClass().toString());
      System.out.println("Exception Message: " + e.getMessage());
      e.printStackTrace();
    }
    System.out.println(SAXParserTest.class.getSimpleName() + " END");

  }
}
