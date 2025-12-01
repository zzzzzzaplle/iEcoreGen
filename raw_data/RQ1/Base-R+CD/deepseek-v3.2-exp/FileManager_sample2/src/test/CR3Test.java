import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR3Test {

    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with no documents
        // The FileSystem is already initialized empty by setUp()

        // Input: Count documents created after 2023-10-01
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);

        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }

    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() throws Exception {
        // SetUp: Add a document created on 2023-10-05
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);

        fileSystem.addDocument(doc1);

        // Input: Count documents created after 2023-10-01
        Date cutoffDate = dateFormat.parse("2023-10-01 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);

        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("One document created after 2023-10-01 should be found", 1, result);
    }

    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() throws Exception {
        // SetUp: Add multiple documents with different creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);

        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);

        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);

        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreateDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);

        // Input: Count documents created after 2023-09-15
        Date cutoffDate = dateFormat.parse("2023-09-15 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);

        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Three documents created after 2023-09-15 should be found", 3, result);
    }

    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Add documents created before 2023-09-30
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);

        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);

        // Input: Count documents created after 2023-09-30
        Date cutoffDate = dateFormat.parse("2023-09-30 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);

        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("No documents created after 2023-09-30 should be found", 0, result);
    }

    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Add documents with variation in creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreateDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);

        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreateDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);

        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreateDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Input: Count documents created after 2023-08-01
        Date cutoffDate = dateFormat.parse("2023-08-01 00:00:00");
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);

        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Two documents created after 2023-08-01 should be found", 2, result);
    }
}