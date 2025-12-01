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
        // Test Case 1: Count documents created after a specific date with no documents
        LocalDate cutoffDate = LocalDate.of(2023, 10, 1);
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase2_countDocumentsAfterDateWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // SetUp: Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        fileSystem.addDocument(doc1);
        
        LocalDate cutoffDate = LocalDate.of(2023, 10, 1);
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals(1, result);
    }
    
    @Test
    public void testCase3_countDocumentsAfterDateWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // SetUp: Add four documents with different creation dates
        
        // Document 1: created on 2023-09-10 (before cutoff)
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 10));
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        
        // Document 2: created on 2023-09-20 (after cutoff)
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        // Document 3: created on 2023-10-01 (after cutoff)
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 10, 1));
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        
        // Document 4: created on 2023-10-10 (after cutoff)
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(LocalDate.of(2023, 10, 10));
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        LocalDate cutoffDate = LocalDate.of(2023, 9, 15);
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals(3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // SetUp: Add two documents created before the cutoff date
        
        // Document 1: created on 2023-09-28 (before cutoff)
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 28));
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        
        // Document 2: created on 2023-08-15 (before cutoff)
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 15));
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        LocalDate cutoffDate = LocalDate.of(2023, 9, 30);
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals(0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // SetUp: Add three documents with different creation dates relative to cutoff
        
        // Document 1: created on 2023-07-30 (before cutoff)
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 7, 30));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        // Document 2: created on 2023-08-05 (after cutoff)
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 5));
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        
        // Document 3: created on 2023-09-15 (after cutoff)
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 9, 15));
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        LocalDate cutoffDate = LocalDate.of(2023, 8, 1);
        int result = fileSystem.countDocumentsAfterDate(cutoffDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals(2, result);
    }
}