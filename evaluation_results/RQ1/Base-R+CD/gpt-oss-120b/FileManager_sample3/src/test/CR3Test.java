import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals(0, result);
    }

    @Test
    public void testCase2_CountDocumentsCreatedAfterSpecificDateWithOneDocument() throws ParseException {
        // Input: Count the number of documents created after 2023-10-01
        // Setup:
        // 1. Create a FileSystem instance
        // 2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size
        // 10KB
        Date referenceDate = dateFormat.parse("2023-10-01");
        Date docCreateDate = dateFormat.parse("2023-10-05");

        Document doc1 = new Document("Doc1", docCreateDate, "Author1", 10240, new TextEditor());
        fileSystem.addDocument(doc1);

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals(1, result);
    }

    @Test
    public void testCase3_CountDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws ParseException {
        // Input: Count the number of documents created after 2023-09-15
        // Setup:
        // 1. Create a FileSystem instance
        // 2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size
        // 15KB
        // 3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size
        // 20KB
        // 4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size
        // 5KB
        // 5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size
        // 25KB
        Date referenceDate = dateFormat.parse("2023-09-15");

        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-10"), "Author1", 15360, new TextEditor());
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-09-20"), "Author2", 20480, new ImageEditor());
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-10-01"), "Author3", 5120, new VideoEditor());
        Document doc4 = new Document("Doc4", dateFormat.parse("2023-10-10"), "Author4", 25600, new TextEditor());

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals(3, result);
    }

    @Test
    public void testCase4_CountDocumentsCreatedBeforeTheSpecificDate() throws ParseException {
        // Input: Count the number of documents created after 2023-09-30
        // Setup:
        // 1. Create a FileSystem instance
        // 2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size
        // 12KB
        // 3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size
        // 30KB
        Date referenceDate = dateFormat.parse("2023-09-30");

        Document doc1 = new Document("Doc1", dateFormat.parse("2023-09-28"), "Author1", 12288, new TextEditor());
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-15"), "Author2", 30720, new ImageEditor());

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals(0, result);
    }

    @Test
    public void testCase5_CountDocumentsWithVariationInCreationDates() throws ParseException {
        // Input: Count the number of documents created after 2023-08-01
        // Setup:
        // 1. Create a FileSystem instance
        // 2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size
        // 10KB
        // 3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size
        // 20KB
        // 4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size
        // 15KB
        Date referenceDate = dateFormat.parse("2023-08-01");

        Document doc1 = new Document("Doc1", dateFormat.parse("2023-07-30"), "Author1", 10240, new TextEditor());
        Document doc2 = new Document("Doc2", dateFormat.parse("2023-08-05"), "Author2", 20480, new ImageEditor());
        Document doc3 = new Document("Doc3", dateFormat.parse("2023-09-15"), "Author3", 15360, new VideoEditor());

        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);

        // Execute the method under test
        int result = fileSystem.countDocumentsAfterDate(referenceDate);

        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals(2, result);
    }

}