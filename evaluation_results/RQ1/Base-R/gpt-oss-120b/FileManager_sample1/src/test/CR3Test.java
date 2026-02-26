import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

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
        // Input: Count the number of documents created after 2023-10-01.
        LocalDate date = LocalDate.of(2023, 10, 1);
        
        // SetUp: FileSystem contains no documents (already initialized in setUp)
        
        // Execute the method under test
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Verify expected output: Total documents created after 2023-10-01 = 0
        assertEquals("No documents should be counted when file system is empty", 0L, result);
    }
    
    @Test
    public void testCase2_countDocumentsCreatedAfterWithOneDocument() {
        // Test Case 2: Count documents created after a specific date with one document
        // Input: Count the number of documents created after 2023-10-01.
        LocalDate date = LocalDate.of(2023, 10, 1);
        
        // SetUp: Add a document created on 2023-10-05
        Document doc = new Document(
            "Doc1", 
            LocalDate.of(2023, 10, 5), 
            "Author1", 
            10240L, // 10KB in bytes
            new TextEditor()
        );
        fileSystem.addDocument(doc);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Verify expected output: Total documents created after 2023-10-01 = 1
        assertEquals("Should count the single document created after the specified date", 1L, result);
    }
    
    @Test
    public void testCase3_countDocumentsCreatedAfterWithMultipleDocuments() {
        // Test Case 3: Count documents created after a specific date with multiple documents
        // Input: Count the number of documents created after 2023-09-15.
        LocalDate date = LocalDate.of(2023, 9, 15);
        
        // SetUp: Add documents with various creation dates
        Document doc1 = new Document(
            "Doc1", 
            LocalDate.of(2023, 9, 10), 
            "Author1", 
            15360L, // 15KB
            new TextEditor()
        );
        Document doc2 = new Document(
            "Doc2", 
            LocalDate.of(2023, 9, 20), 
            "Author2", 
            20480L, // 20KB
            new ImageEditor()
        );
        Document doc3 = new Document(
            "Doc3", 
            LocalDate.of(2023, 10, 1), 
            "Author3", 
            5120L, // 5KB
            new VideoEditor()
        );
        Document doc4 = new Document(
            "Doc4", 
            LocalDate.of(2023, 10, 10), 
            "Author4", 
            25600L, // 25KB
            new TextEditor()
        );
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Verify expected output: Total documents created after 2023-09-15 = 3
        assertEquals("Should count 3 documents created after 2023-09-15", 3L, result);
    }
    
    @Test
    public void testCase4_countDocumentsCreatedBeforeTheSpecificDate() {
        // Test Case 4: Count documents created before the specific date
        // Input: Count the number of documents created after 2023-09-30.
        LocalDate date = LocalDate.of(2023, 9, 30);
        
        // SetUp: Add documents created before the specified date
        Document doc1 = new Document(
            "Doc1", 
            LocalDate.of(2023, 9, 28), 
            "Author1", 
            12288L, // 12KB
            new TextEditor()
        );
        Document doc2 = new Document(
            "Doc2", 
            LocalDate.of(2023, 8, 15), 
            "Author2", 
            30720L, // 30KB
            new ImageEditor()
        );
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Verify expected output: Total documents created after 2023-09-30 = 0
        assertEquals("Should return 0 when all documents are created before the specified date", 0L, result);
    }
    
    @Test
    public void testCase5_countDocumentsWithVariationInCreationDates() {
        // Test Case 5: Count documents with variation in creation dates
        // Input: Count the number of documents created after 2023-08-01.
        LocalDate date = LocalDate.of(2023, 8, 1);
        
        // SetUp: Add documents with creation dates before and after the specified date
        Document doc1 = new Document(
            "Doc1", 
            LocalDate.of(2023, 7, 30), 
            "Author1", 
            10240L, // 10KB
            new TextEditor()
        );
        Document doc2 = new Document(
            "Doc2", 
            LocalDate.of(2023, 8, 5), 
            "Author2", 
            20480L, // 20KB
            new ImageEditor()
        );
        Document doc3 = new Document(
            "Doc3", 
            LocalDate.of(2023, 9, 15), 
            "Author3", 
            15360L, // 15KB
            new VideoEditor()
        );
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute the method under test
        long result = fileSystem.countDocumentsCreatedAfter(date);
        
        // Verify expected output: Total documents created after 2023-08-01 = 2
        assertEquals("Should count 2 documents created after 2023-08-01", 2L, result);
    }
}