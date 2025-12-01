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
    public void testCase1_countDocumentsCreatedAfterSpecificDateWithNoDocuments() throws Exception {
        // SetUp: Create a FileSystem instance with no documents
        // The FileSystem is already created in setUp(), and it contains no documents by default
        
        // Input: Count the number of documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("Count should be 0 when no documents exist", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterSpecificDateWithOneDocument() throws Exception {
        // SetUp: Add a document created on 2023-10-05
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-10-05 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        // Input: Count the number of documents created after 2023-10-01
        Date targetDate = dateFormat.parse("2023-10-01 00:00:00");
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("Count should be 1 when one document exists after target date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterSpecificDateWithMultipleDocuments() throws Exception {
        // SetUp: Add multiple documents with different creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-10 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-09-20 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-10-01 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(dateFormat.parse("2023-10-10 00:00:00"));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.addDocument(doc4);
        
        // Input: Count the number of documents created after 2023-09-15
        Date targetDate = dateFormat.parse("2023-09-15 00:00:00");
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Count should be 3 when three documents exist after target date", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() throws Exception {
        // SetUp: Add documents created before the target date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-09-28 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-15 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.addDocument(doc2);
        
        // Input: Count the number of documents created after 2023-09-30
        Date targetDate = dateFormat.parse("2023-09-30 00:00:00");
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("Count should be 0 when no documents exist after target date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() throws Exception {
        // SetUp: Add documents with varying creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(dateFormat.parse("2023-07-30 00:00:00"));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(dateFormat.parse("2023-08-05 00:00:00"));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(dateFormat.parse("2023-09-15 00:00:00"));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.addDocument(doc3);
        
        // Input: Count the number of documents created after 2023-08-01
        Date targetDate = dateFormat.parse("2023-08-01 00:00:00");
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Count should be 2 when two documents exist after target date", 2, result);
    }
}