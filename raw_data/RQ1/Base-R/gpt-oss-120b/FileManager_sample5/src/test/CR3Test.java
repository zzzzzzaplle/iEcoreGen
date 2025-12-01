import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Reset file system before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        // Setup: FileSystem contains no documents
        
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        // Setup: Add a document created on 2023-10-05
        
        Document doc = new Document("Doc1", LocalDate.of(2023, 10, 5), "Author1", 10240, Editor.TEXT_EDITOR);
        fileSystem.addDocument(doc);
        
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("Should count the single document created after target date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        // Setup: Add 4 documents with varying creation dates
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 10), "Author1", 15360, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 9, 20), "Author2", 20480, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 10, 1), "Author3", 5120, Editor.VIDEO_EDITOR);
        Document doc4 = new Document("Doc4", LocalDate.of(2023, 10, 10), "Author4", 25600, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 15);
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Should count 3 documents created after 2023-09-15", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeTargetDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        // Setup: Add 2 documents created before the target date
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 28), "Author1", 12288, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 15), "Author2", 30720, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 30);
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("Should return 0 when all documents are created before target date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        // Setup: Add 3 documents with dates around the target date
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 7, 30), "Author1", 10240, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 5), "Author2", 20480, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 9, 15), "Author3", 15360, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        LocalDate targetDate = LocalDate.of(2023, 8, 1);
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Should count 2 documents created after 2023-08-01", 2, result);
    }
}