package at.fhooe.ssd4.ue04.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMParserTest {

    public static void main(String[] args) {
        System.out.println("DOMParserTest BEGIN");
        /* Get an instance of the DocumentBuilderFactory */
        // TODO
        var factory = DocumentBuilderFactory.newInstance();

        /*
         * By default, parsers won't validate documents. Set this to true to turn on
         * validation against DTD
         */
        // TODO
        factory.setValidating(true);

        /*
         * Determines whether whitespace within element contents will be ignored. The
         * default value is false
         */
        // TODO
        factory.setIgnoringElementContentWhitespace(false);

        /*
         * Determines whether external entity references will be expanded. If true, the
         * external data will be inserted into the document. The default value is true
         */
        // TODO
        factory.setExpandEntityReferences(true);

        /*
         * Determines whether comments within the file will be ignored. The default
         * value is false
         */
        // TODO
        factory.setIgnoringComments(false);

        try {
            /*
             * Get a new DocumentBuilder. The DocumentBuilder will do the actual parsing to
             * create the Document object
             */
            // TODO
            var builder = factory.newDocumentBuilder();

            /* Determine feature availability of the parser (demo!) */
            /*
            DOMImplementation domImpl = builder.getDOMImplementation();
            String feature[]
                    = {"Core", "XML", "HTML", "Views", "Stylesheets", "CSS", "CSS2", "Events",
                    "UIEvents", "MouseEvents", "MutationEvents", "HTMLEvents", "Range",
                    "Traversal"};
            for (int i = 0; i < feature.length; i++) {
                System.out.print(feature[i]);
                if (!domImpl.hasFeature(feature[i], "2.0")) {
                    System.out.println(" not supported");
                } else {
                    System.out.println(" supported");
                }
            }
             */

            /* Specify the ErrorHandler to be used by the parser. */
            // TODO
            builder.setErrorHandler(new DOMErrorHandler());

            // TODO Consider Try/Catch
            File file = new File("xmlfiles/CourseCatalog.xml");
            /*
             * Parse the xml file and create the document Document allows direct access to
             * the child node that is the document element of the document.
             */
            // TODO parse the file
            var doc = builder.parse(file);

            // root element
            // TODO retrieve root element
            var courseCatalog = doc.getDocumentElement();

            Attr year = courseCatalog.getAttributeNode("year");

            System.out.println("All attributes for element 'CourseCatalog': ");
            // TODO
            for (var i = 0; i < courseCatalog.getAttributes().getLength(); i++) {
                var attribute = courseCatalog.getAttributes().item(i);
                System.out.println(STR."\{" ".repeat(3)} - \{attribute.getNodeName()}, Value: \{attribute.getNodeValue()}");
            }

            // Will retrieve the CourseCatalog child entries
            // TODO
            var courseCatalogChildNodes = courseCatalog.getChildNodes();
            for (var i = 0; i < courseCatalogChildNodes.getLength(); i++) {
                var child = courseCatalogChildNodes.item(i);
                if (child.getNodeValue() == null) {
                    System.out.println(STR."\{" ".repeat(3)} - Element \{child.getNodeName()}");
                } else {
                    System.out.println(STR."\{" ".repeat(3)} - Element \{child.getNodeName()}, Value: \{child.getNodeValue().replaceAll("[\\t\\n\\r]+", "")}");
                }

            }

            // Will retrieve the Degree Programme child entries
            // TODO

            // Will retrieve the Course child entries
            // TODO


            System.out.println("***** getElementsByTagName() *****");
            // TODO

            System.out.println("*************************************************************************************");
            // using XPath
            // TODO create XPath expression
            // z.B. ID aller Kurse ausgeben, deren Raum in einem FH-Gebäude liegt (building beginnt mit 'FH')


            // TODO get type of Course with ID cID8314

        } catch (Throwable e) {
            System.out.println("Exception Type: " + e.getClass().toString());
            System.out.println("Exception Message: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("DOMParserTest END");
    }

    private static String printNodeType(short sNodeType) {
        switch (sNodeType) {
            case Node.ELEMENT_NODE:
                return "ELEMENT_NODE";
            case Node.ATTRIBUTE_NODE:
                return "ATTRIBUTE_NODE";
            case Node.TEXT_NODE:
                return "TEXT_NODE";
            case Node.CDATA_SECTION_NODE:
                return "CDATA_SECTION_NODE";
            case Node.ENTITY_REFERENCE_NODE:
                return "ENTITY_REFERENCE_NODE";
            case Node.ENTITY_NODE:
                return "ENTITY_NODE";
            case Node.PROCESSING_INSTRUCTION_NODE:
                return "PROCESSING_INSTRUCTION_NODE";
            case Node.COMMENT_NODE:
                return "COMMENT_NODE";
            case Node.DOCUMENT_NODE:
                return "DOCUMENT_NODE";
            case Node.DOCUMENT_TYPE_NODE:
                return "DOCUMENT_TYPE_NODE";
            case Node.DOCUMENT_FRAGMENT_NODE:
                return "DOCUMENT_FRAGMENT_NODE";
            case Node.NOTATION_NODE:
                return "NOTATION_NODE";
            default:
                return "Unknown type";
        }
    }
}
