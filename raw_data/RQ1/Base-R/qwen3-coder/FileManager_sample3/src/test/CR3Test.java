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
        // Initialize a new FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: FileSystem contains no documents
        
        // Create the target date
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        
        // Count documents created after the target date
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: Add a document created on 2023-10-05
        
        // Create and configure the document
        Document doc1 = new Document();
        doc1.setName("Doc1");
        doc1.setCreationDate(LocalDate.of(2023, 10, 5));
        doc1.setAuthor("Author1");
        doc1.setSize(10);
        
        // Add document to file system
        fileSystem.addDocument(doc1);
        
        // Create the target date
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        
        // Count documents created after the target date
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("One document created after 2023-10-01 should be found", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        // SetUp: Add 4 documents with different creation dates
        
        // Create and configure documents
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
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Create the target date
        LocalDate targetDate = LocalDate.of(2023, 9, 15);
        
        // Count documents created after the target date
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Three documents created after 2023-09-15 should be found", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        // SetUp: Add 2 documents created before the target date
        
        // Create and configure documents
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
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Create the target date
        LocalDate targetDate = LocalDate.of(2023, 9, 30);
        
        // Count documents created after the target date
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("No documents created after 2023-09-30 should be found", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        // SetUp: Add 3 documents with dates before and after the target date
        
        // Create and configure documents
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
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Create the target date
        LocalDate targetDate = LocalDate.of(2023, 8, 1);
        
        // Count documents created after the target date
        int result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Two documents created after 2023-08-01 should be found", 2, result);
    }
}