import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: FileSystem contains no documents
        
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: Add a document created on 2023-10-05
        
        Editor editor = new Editor("TestEditor");
        Document doc = new Document("Doc1", LocalDate.of(2023, 10, 5), "Author1", 10240, editor);
        fileSystem.addDocument(doc);
        
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("One document created after the target date should be counted", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        // SetUp: Add multiple documents with different creation dates
        
        Editor editor = new Editor("TestEditor");
        
        // Add document created before target date (should not be counted)
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 10), "Author1", 15360, editor);
        fileSystem.addDocument(doc1);
        
        // Add documents created after target date (should be counted)
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 9, 20), "Author2", 20480, editor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 10, 1), "Author3", 5120, editor);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document("Doc4", LocalDate.of(2023, 10, 10), "Author4", 25600, editor);
        fileSystem.addDocument(doc4);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 15);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Three documents created after the target date should be counted", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        // SetUp: Add documents created before the target date
        
        Editor editor = new Editor("TestEditor");
        
        // Add documents created before target date (should not be counted)
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 28), "Author1", 12288, editor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 15), "Author2", 30720, editor);
        fileSystem.addDocument(doc2);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 30);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("No documents created after the target date should be found", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        // SetUp: Add documents with various creation dates
        
        Editor editor = new Editor("TestEditor");
        
        // Add document created before target date (should not be counted)
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 7, 30), "Author1", 10240, editor);
        fileSystem.addDocument(doc1);
        
        // Add documents created after target date (should be counted)
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 5), "Author2", 20480, editor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 9, 15), "Author3", 15360, editor);
        fileSystem.addDocument(doc3);
        
        LocalDate targetDate = LocalDate.of(2023, 8, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Two documents created after the target date should be counted", 2, result);
    }
}