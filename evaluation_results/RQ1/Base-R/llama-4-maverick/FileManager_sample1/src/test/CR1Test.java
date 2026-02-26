import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create a FileSystem instance and add three documents
        Editor editor = new Editor("TestEditor");
        
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 100, editor);
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 200, editor);
        Document doc3 = new Document("Document3", LocalDate.now(), "Author3", 300, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create a FileSystem instance, add two documents, then remove one
        Editor editor = new Editor("TestEditor");
        
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 150, editor);
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 250, editor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1 from the FileSystem
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 250 KB
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create a FileSystem instance with no documents added
        
        // Calculate total size of empty file system
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 0 KB
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create a FileSystem instance and add three documents with mixed sizes
        Editor editor = new Editor("TestEditor");
        
        Document docA = new Document("DocA", LocalDate.now(), "AuthorA", 50, editor);
        Document docB = new Document("DocB", LocalDate.now(), "AuthorB", 1000, editor);
        Document docC = new Document("DocC", LocalDate.now(), "AuthorC", 250, editor);
        
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create a FileSystem instance, add three documents, then remove two
        Editor editor = new Editor("TestEditor");
        
        Document report = new Document("Report", LocalDate.now(), "Author1", 400, editor);
        Document image = new Document("Image", LocalDate.now(), "Author2", 300, editor);
        Document video = new Document("Video", LocalDate.now(), "Author3", 700, editor);
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove "Image" and "Report"
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after multiple removals
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 700 KB
        assertEquals(700, totalSize);
    }
}