import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize a fresh FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem and add three documents
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 100L, new TextEditor());
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 200L, new ImageEditor());
        Document doc3 = new Document("Document3", LocalDate.now(), "Author3", 300L, new VideoEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 100 + 200 + 300 = 600
        assertEquals("Total size should be 600", 600L, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem, add two documents, then remove one
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 150L, new TextEditor());
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 250L, new ImageEditor());
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1); // Remove Document1 (150 KB)
        
        // Calculate total size after removal
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 250 KB remaining
        assertEquals("Total size after removal should be 250", 250L, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: FileSystem with no documents
        
        // Calculate total size of empty FileSystem
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 0
        assertEquals("Total size of empty system should be 0", 0L, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem and add three documents with mixed sizes
        Document docA = new Document("DocA", LocalDate.now(), "AuthorA", 50L, new TextEditor());
        Document docB = new Document("DocB", LocalDate.now(), "AuthorB", 1000L, new ImageEditor());
        Document docC = new Document("DocC", LocalDate.now(), "AuthorC", 250L, new VideoEditor());
        
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 50 + 1000 + 250 = 1300
        assertEquals("Total size should be 1300", 1300L, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem, add three documents, then remove two
        Document report = new Document("Report", LocalDate.now(), "Author1", 400L, new TextEditor());
        Document image = new Document("Image", LocalDate.now(), "Author2", 300L, new ImageEditor());
        Document video = new Document("Video", LocalDate.now(), "Author3", 700L, new VideoEditor());
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove two documents
        fileSystem.removeDocument(image); // Remove 300 KB
        fileSystem.removeDocument(report); // Remove 400 KB
        
        // Calculate total size after removals
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 700 KB remaining
        assertEquals("Total size after multiple removals should be 700", 700L, totalSize);
    }
}