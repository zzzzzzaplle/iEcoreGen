import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize a new FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsAfterDateWithNoDocuments() {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        
        // SetUp: FileSystem contains no documents (already initialized in setUp)
        
        LocalDate specifiedDate = LocalDate.of(2023, 10, 1);
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("Count should be 0 when no documents exist", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        
        // SetUp: Add a document created on 2023-10-05
        Document doc = new Document();
        doc.setName("Doc1");
        doc.setCreationDate(LocalDate.of(2023, 10, 5));
        doc.setAuthor("Author1");
        doc.setSize(10);
        
        fileSystem.addDocument(doc);
        
        LocalDate specifiedDate = LocalDate.of(2023, 10, 1);
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("Count should be 1 when one document exists after specified date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        
        // SetUp: Add multiple documents with different creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 10));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 10, 1));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(LocalDate.of(2023, 10, 10));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        LocalDate specifiedDate = LocalDate.of(2023, 9, 15);
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Count should be 3 when three documents exist after specified date", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        
        // SetUp: Add documents created before the specified date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 28));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 15));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        LocalDate specifiedDate = LocalDate.of(2023, 9, 30);
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("Count should be 0 when no documents exist after specified date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        
        // SetUp: Add documents with varied creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 7, 30));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 5));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 9, 15));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        LocalDate specifiedDate = LocalDate.of(2023, 8, 1);
        int result = fileSystem.countDocumentsAfterDate(specifiedDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Count should be 2 when two documents exist after specified date", 2, result);
    }
}