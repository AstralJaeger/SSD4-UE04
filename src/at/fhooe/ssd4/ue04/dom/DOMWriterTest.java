package at.fhooe.ssd4.ue04.dom;

import java.io.File;
import java.util.Date;

// Generates a "Course Catalog" XML file with one course 

public class DOMWriterTest {

    public static void main(String[] args) {
        System.out.println("DOMWriterTest BEGIN");
        // TODO create factory

        try {
            // TODO create document builder and new document


            // TODO Create new courseCatalog element with some attributes


            // TODO Append element to document


            // TODO create comment with date and time and add it to the document


            // TODO create degree programme element


            // TODO add the new element to the courseCatalog


            // TODO Create new Course element and append to degree programme
            // <Course id = 'cID8314' semesterHours='1' semester='4'>SSD4</Course>


            // TODO create new Title element, append text to it - append this element to a course


            // TODO Description element (mixed content)


            // TODO create credit element (e.g. 1 ECTS)


            // TODO course type element


            //Serialize the DOM to the given file (the output xml file might not be valid according to given DTD)
            String fileName = "CourseCatalog_DOMWriter_1_" + new Date().toString().replaceAll("[ :]", "_") + ".xml";
            File file = new File("xmlfiles/" + fileName);
            // TODO Use TransformerFactory and Transformer (with OutputProperty)

            // TODO transform the source to the file


            System.out.println("New file was created in " + file.getAbsolutePath());

        } catch (Throwable e) {
            System.out.println("Exception Type: " + e.getClass().toString());
            System.out.println("Exception Message: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("DOMWriterTest END");

    }
}
