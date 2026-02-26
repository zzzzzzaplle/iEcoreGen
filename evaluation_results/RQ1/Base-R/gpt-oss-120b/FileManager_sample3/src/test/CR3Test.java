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
        // Initialize a fresh FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_countDocumentsCreatedAfterWithNoDocuments() {
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: FileSystem contains no documents
        
        LocalDate date = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Expected Output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be found when file system is empty", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01
        // SetUp: Add a document named "Doc1" created on 2023-10-05
        
        Author author = new Author("Author1");
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 10, 5), author, 10240L, Editor.TEXT_EDITOR);
        fileSystem.addDocument(doc1);
        
        LocalDate date = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Expected Output: Total documents created after 2023-10-01 = 1
        assertEquals("One document created after 2023-10-01 should be found", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15
        // SetUp: Add four documents with various creation dates
        
        Author author1 = new Author("Author1");
        Author author2 = new Author("Author2");
        Author author3 = new Author("Author3");
        Author author4 = new Author("Author4");
        
        // Document created before the target date
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 10), author1, 15360L, Editor.TEXT_EDITOR);
        // Document created after the target date
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 9, 20), author2, 20480L, Editor.IMAGE_EDITOR);
        // Document created on the boundary (after the target date)
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 10, 1), author3, 5120L, Editor.VIDEO_EDITOR);
        // Document created after the target date
        Document doc4 = new Document("Doc4", LocalDate.of(2023, 10, 10), author4, 25600L, Editor.TEXT_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        LocalDate date = LocalDate.of(2023, 9, 15);
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Expected Output: Total documents created after 2023-09-15 = 3
        assertEquals("Three documents created after 2023-09-15 should be found", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30
        // SetUp: Add two documents both created before the target date
        
        Author author1 = new Author("Author1");
        Author author2 = new Author("Author2");
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 28), author1, 12288L, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 15), author2, 30720L, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        LocalDate date = LocalDate.of(2023, 9, 30);
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Expected Output: Total documents created after 2023-09-30 = 0
        assertEquals("No documents created after 2023-09-30 should be found", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01
        // SetUp: Add three documents with various creation dates
        
        Author author1 = new Author("Author1");
        Author author2 = new Author("Author2");
        Author author3 = new Author("Author3");
        
        // Document created before the target date
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 7, 30), author1, 10240L, Editor.TEXT_EDITOR);
        // Document created after the target date
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 5), author2, 20480L, Editor.IMAGE_EDITOR);
        // Document created after the target date
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 9, 15), author3, 15360L, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        LocalDate date = LocalDate.of(2023, 8, 1);
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Expected Output: Total documents created after 2023-08-01 = 2
        assertEquals("Two documents created after 2023-08-01 should be found", 2, result);
    }
}