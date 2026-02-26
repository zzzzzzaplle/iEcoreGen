import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR3Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() {
        // SetUp: Create a FileSystem instance with no documents
        // Input: Count documents created after 2023-10-01
        LocalDate cutoffDate = LocalDate.of(2023, 10, 1);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsCreatedAfter(cutoffDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 0 when no documents exist", 
                     0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // SetUp: Create a FileSystem instance and add one document created after cutoff date
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 10, 5), "Author1", 10240, null);
        fileSystem.addDocument(doc1);
        
        // Input: Count documents created after 2023-10-01
        LocalDate cutoffDate = LocalDate.of(2023, 10, 1);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsCreatedAfter(cutoffDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-10-01 should be 1 when one document exists after that date", 
                     1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // SetUp: Create a FileSystem instance and add multiple documents with varying creation dates
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 10), "Author1", 15360, null);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 9, 20), "Author2", 20480, null);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 10, 1), "Author3", 5120, null);
        Document doc4 = new Document("Doc4", LocalDate.of(2023, 10, 10), "Author4", 25600, null);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Input: Count documents created after 2023-09-15
        LocalDate cutoffDate = LocalDate.of(2023, 9, 15);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsCreatedAfter(cutoffDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-09-15 should be 3", 
                     3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // SetUp: Create a FileSystem instance and add documents created before the cutoff date
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 28), "Author1", 12288, null);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 15), "Author2", 30720, null);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Input: Count documents created after 2023-09-30
        LocalDate cutoffDate = LocalDate.of(2023, 9, 30);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsCreatedAfter(cutoffDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-09-30 should be 0 when all documents are created before that date", 
                     0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // SetUp: Create a FileSystem instance and add documents with varied creation dates
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 7, 30), "Author1", 10240, null);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 5), "Author2", 20480, null);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 9, 15), "Author3", 15360, null);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Input: Count documents created after 2023-08-01
        LocalDate cutoffDate = LocalDate.of(2023, 8, 1);
        
        // Execute the method under test
        int result = fileSystem.countDocumentsCreatedAfter(cutoffDate);
        
        // Verify the expected output
        assertEquals("Total documents created after 2023-08-01 should be 2", 
                     2, result);
    }

}