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
        // Test Case 1: Count documents created after a specific date with no documents
        // Input: Count the number of documents created after 2023-10-01.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. The FileSystem contains no documents.
        // Expected Output: Total documents created after 2023-10-01 = 0
        
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        assertEquals("Should return 0 when no documents exist", 0, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-10-05, author "Author1", size 10KB.
        // Expected Output: Total documents created after 2023-10-01 = 1
        
        Editor editor = new Editor("Test Editor");
        Document doc = new Document("Doc1", LocalDate.of(2023, 10, 5), "Author1", 10, editor);
        fileSystem.addDocument(doc);
        
        LocalDate targetDate = LocalDate.of(2023, 10, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        assertEquals("Should return 1 when one document is created after target date", 1, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-10, author "Author1", size 15KB.
        // 3. Add a document named "Doc2" created on 2023-09-20, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-10-01, author "Author3", size 5KB.
        // 5. Add a document named "Doc4" created on 2023-10-10, author "Author4", size 25KB.
        // Expected Output: Total documents created after 2023-09-15 = 3
        
        Editor editor1 = new Editor("Editor1");
        Editor editor2 = new Editor("Editor2");
        Editor editor3 = new Editor("Editor3");
        Editor editor4 = new Editor("Editor4");
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 10), "Author1", 15, editor1);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 9, 20), "Author2", 20, editor2);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 10, 1), "Author3", 5, editor3);
        Document doc4 = new Document("Doc4", LocalDate.of(2023, 10, 10), "Author4", 25, editor4);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 15);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        assertEquals("Should return 3 when three documents are created after target date", 3, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-09-28, author "Author1", size 12KB.
        // 3. Add a document named "Doc2" created on 2023-08-15, author "Author2", size 30KB.
        // Expected Output: Total documents created after 2023-09-30 = 0
        
        Editor editor1 = new Editor("Editor1");
        Editor editor2 = new Editor("Editor2");
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 9, 28), "Author1", 12, editor1);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 15), "Author2", 30, editor2);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        LocalDate targetDate = LocalDate.of(2023, 9, 30);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        assertEquals("Should return 0 when all documents are created before target date", 0, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01.
        // SetUp: 
        // 1. Create a FileSystem instance.
        // 2. Add a document named "Doc1" created on 2023-07-30, author "Author1", size 10KB.
        // 3. Add a document named "Doc2" created on 2023-08-05, author "Author2", size 20KB.
        // 4. Add a document named "Doc3" created on 2023-09-15, author "Author3", size 15KB.
        // Expected Output: Total documents created after 2023-08-01 = 2
        
        Editor editor1 = new Editor("Editor1");
        Editor editor2 = new Editor("Editor2");
        Editor editor3 = new Editor("Editor3");
        
        Document doc1 = new Document("Doc1", LocalDate.of(2023, 7, 30), "Author1", 10, editor1);
        Document doc2 = new Document("Doc2", LocalDate.of(2023, 8, 5), "Author2", 20, editor2);
        Document doc3 = new Document("Doc3", LocalDate.of(2023, 9, 15), "Author3", 15, editor3);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        LocalDate targetDate = LocalDate.of(2023, 8, 1);
        long result = fileSystem.countDocumentsCreatedAfter(targetDate);
        
        assertEquals("Should return 2 when two documents are created after target date", 2, result);
    }
}