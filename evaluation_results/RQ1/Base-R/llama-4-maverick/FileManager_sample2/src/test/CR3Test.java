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
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() {
        // Test counting documents created after specific date when file system has no documents
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify that result is 0 when no documents exist
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test counting documents created after specific date with one matching document
        Editor editor = new Editor("Test Editor");
        Document doc = new Document("Doc1", LocalDate.of(2023, 10, 5), "Author1", 10, editor);
        fileSystem.addDocument(doc);
        
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify that one document created after the target date is counted
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test counting documents created after specific date with multiple documents
        Editor editor = new Editor("Test Editor");
        
        // Add documents with different creation dates
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 10), "Author1", 15, editor);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 9, 20), "Author2", 20, editor);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 10, 1), "Author3", 5, editor);
        Document doc4 = new Document("Doc4", LocalDate.of(2023, 10, 10), "Author4", 25, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 15);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify that 3 documents created after 2023-09-15 are counted
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeTargetDate() {
        // Test counting documents when all documents are created before the target date
        Editor editor = new Editor("Test Editor");
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 28), "Author1", 12, editor);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 15), "Author2", 30, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 30);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify that no documents are counted when all are created before target date
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariedCreationDates() {
        // Test counting documents with varied creation dates relative to target date
        Editor editor = new Editor("Test Editor");
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 7, 30), "Author1", 10, editor);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 5), "Author2", 20, editor);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 9, 15), "Author3", 15, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        LocalDate targetDate = LocalDate.of(2023, 8, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Verify that 2 documents created after 2023-08-01 are counted
        assertEquals(2, result);
    }
}