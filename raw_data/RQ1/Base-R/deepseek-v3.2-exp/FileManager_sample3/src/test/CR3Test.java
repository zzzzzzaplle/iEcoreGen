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
    public void testCase1_countDocumentsAfterDateWithNoDocuments() {
        // Test counting documents when file system is empty
        LocalDate cutoffDate = LocalDate.of(2023, 10, 1);
        
        // Call the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result
        assertEquals("Should return 0 when no documents exist", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() {
        // Set up: Add one document created after the cutoff date
        LocalDate cutoffDate = LocalDate.of(2023, 10, 1);
        
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Author1");
        doc1.setSize(10 * 1024); // 10KB in bytes
        
        // Add document to file system
        fileSystem.addDocument(doc1);
        
        // Call the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result
        assertEquals("Should count 1 document created after 2023-10-01", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() {
        // Set up: Add multiple documents with various creation dates
        LocalDate cutoffDate = LocalDate.of(2023, 9, 15);
        
        // Document created before cutoff date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 10));
        doc1.setAuthor("Author1");
        doc1.setSize(15 * 1024);
        
        // Document created after cutoff date
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20));
        doc2.setAuthor("Author2");
        doc2.setSize(20 * 1024);
        
        // Document created after cutoff date
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 10, 1));
        doc3.setAuthor("Author3");
        doc3.setSize(5 * 1024);
        
        // Document created after cutoff date
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(LocalDate.of(2023, 10, 10));
        doc4.setAuthor("Author4");
        doc4.setSize(25 * 1024);
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - should count 3 documents created after 2023-09-15
        assertEquals("Should count 3 documents created after 2023-09-15", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Set up: Add documents created before the cutoff date
        LocalDate cutoffDate = LocalDate.of(2023, 9, 30);
        
        // Document created before cutoff date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 28));
        doc1.setAuthor("Author1");
        doc1.setSize(12 * 1024);
        
        // Document created before cutoff date
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 15));
        doc2.setAuthor("Author2");
        doc2.setSize(30 * 1024);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Call the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - should return 0 since no documents created after cutoff
        assertEquals("Should return 0 when all documents created before cutoff date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Set up: Add documents with varied creation dates around the cutoff
        LocalDate cutoffDate = LocalDate.of(2023, 8, 1);
        
        // Document created before cutoff date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 7, 30));
        doc1.setAuthor("Author1");
        doc1.setSize(10 * 1024);
        
        // Document created after cutoff date
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 5));
        doc2.setAuthor("Author2");
        doc2.setSize(20 * 1024);
        
        // Document created after cutoff date
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 9, 15));
        doc3.setAuthor("Author3");
        doc3.setSize(15 * 1024);
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method under test
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Verify the result - should count 2 documents created after 2023-08-01
        assertEquals("Should count 2 documents created after 2023-08-01", 2, result);
    }
}