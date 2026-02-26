import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        
        // SetUp: FileSystem contains no documents (already empty)
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        
        // SetUp: Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("One document created after 2023-10-01 should be counted", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        LocalDate targetDate = LocalDate.of(2023, 9, 15);
        
        // SetUp: Add multiple documents with different creation dates
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 10)); // Before target date
        doc1.setAuthor("Author1");
        doc1.setSize(15);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 9, 20)); // After target date
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 10, 1)); // After target date
        doc3.setAuthor("Author3");
        doc3.setSize(5);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Doc4");
        doc4.setCreationDate(LocalDate.of(2023, 10, 10)); // After target date
        doc4.setAuthor("Author4");
        doc4.setSize(25);
        fileSystem.addDocument(doc4);
        
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Three documents created after 2023-09-15 should be counted", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        LocalDate targetDate = LocalDate.of(2023, 9, 30);
        
        // SetUp: Add documents with creation dates before target date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 9, 28)); // Before target date
        doc1.setAuthor("Author1");
        doc1.setSize(12);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 15)); // Before target date
        doc2.setAuthor("Author2");
        doc2.setSize(30);
        fileSystem.addDocument(doc2);
        
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("No documents created after 2023-09-30 should be found", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        LocalDate targetDate = LocalDate.of(2023, 8, 1);
        
        // SetUp: Add documents with creation dates around target date
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 7, 30)); // Before target date
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Doc2");
        doc2.setCreationDate(LocalDate.of(2023, 8, 5)); // After target date
        doc2.setAuthor("Author2");
        doc2.setSize(20);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Doc3");
        doc3.setCreationDate(LocalDate.of(2023, 9, 15)); // After target date
        doc3.setAuthor("Author3");
        doc3.setSize(15);
        fileSystem.addDocument(doc3);
        
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Two documents created after 2023-08-01 should be counted", 2, result);
    }
}