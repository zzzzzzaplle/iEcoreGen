import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {

    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void testCase1_CountDocumentsCreatedAfterSpecificDateWithNoDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // Setup: Create a FileSystem instance with no documents
        Date referenceDate = dateFormat.parse("2023-10-01");

        // Execute and verify
        int result = fileSystem.countDocumentsAfterDate(referenceDate);
        assertEquals(0, result);
    }

    @Test
    public void testCase2_CountDocumentsCreatedAfterSpecificDateWithOneDocument() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // Setup: Add a document named "Doc1" created on 2023-10-05
        Date referenceDate = dateFormat.parse("2023-10-01");
        Date documentDate = dateFormat.parse("2023-10-05");

        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(documentDate);
        doc1.setAuthor("Author1");
        doc1.setSize(10);

        fileSystem.addDocument(doc1);

        // Execute and verify
        int result = fileSystem.countDocumentsAfterDate(referenceDate);
        assertEquals(1, result);
    }

    @Test
    public void testCase3_CountDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-09-15
        // Setup: Add four documents with different creation dates
        Date referenceDate = dateFormat.parse("2023-09-15");

        Date doc1Date = dateFormat.parse("2023-09-10");
        Date doc2Date = dateFormat.parse("2023-09-20");
        Date doc3Date = dateFormat.parse("2023-10-01");
        Date doc4Date = dateFormat.parse("2023-10-10");

        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(doc1Date);
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(doc2Date);
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(doc3Date);
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.addDocument(doc3);

        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(doc4Date);
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.addDocument(doc4);

        // Execute and verify
        int result = fileSystem.countDocumentsAfterDate(referenceDate);
        assertEquals(3, result);
    }

    @Test
    public void testCase4_CountDocumentsCreatedBeforeTheSpecificDate() throws ParseException {
        // Input: Count the number of documents created after 2023-09-30
        // Setup: Add two documents created before the reference date
        Date referenceDate = dateFormat.parse("2023-09-30");

        Date doc1Date = dateFormat.parse("2023-09-28");
        Date doc2Date = dateFormat.parse("2023-08-15");

        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(doc1Date);
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(doc2Date);
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.addDocument(doc2);

        // Execute and verify
        int result = fileSystem.countDocumentsAfterDate(referenceDate);
        assertEquals(0, result);
    }

    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws ParseException {
        // Input: Count the number of documents created after 2023-08-01
        // Setup: Add three documents with various creation dates
        Date referenceDate = dateFormat.parse("2023-08-01");

        Date doc1Date = dateFormat.parse("2023-07-30");
        Date doc2Date = dateFormat.parse("2023-08-05");
        Date doc3Date = dateFormat.parse("2023-09-15");

        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(doc1Date);
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);

        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(doc2Date);
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);

        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(doc3Date);
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.addDocument(doc3);

        // Execute and verify
        int result = fileSystem.countDocumentsAfterDate(referenceDate);
        assertEquals(2, result);
    }

}