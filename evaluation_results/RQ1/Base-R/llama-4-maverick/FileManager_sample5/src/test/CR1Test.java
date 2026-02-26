import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private FileSystem fileSystem;
    private Editor textEditor;
    private Editor imageEditor;
    private Editor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        fileSystem = new FileSystem();
        textEditor = new Editor("Text Editor");
        imageEditor = new Editor("Image Editor");
        videoEditor = new Editor("Video Editor");
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem instance and add documents
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 100, textEditor);
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 200, imageEditor);
        Document doc3 = new Document("Document3", LocalDate.now(), "Author3", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB
        assertEquals("Total size should be 600 KB", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem instance, add documents, then remove one
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 150, textEditor);
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 250, imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 250 KB
        assertEquals("Total size should be 250 KB after removal", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create FileSystem instance with no documents added
        // FileSystem is already empty from @Before setup
        
        // Calculate total size of empty file system
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 0 KB
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem instance and add documents with mixed sizes
        Document docA = new Document("DocA", LocalDate.now(), "AuthorA", 50, textEditor);
        Document docB = new Document("DocB", LocalDate.now(), "AuthorB", 1000, imageEditor);
        Document docC = new Document("DocC", LocalDate.now(), "AuthorC", 250, videoEditor);
        
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
        assertEquals("Total size should be 1300 KB for mixed sizes", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem instance, add documents, then remove multiple
        Document report = new Document("Report", LocalDate.now(), "Author1", 400, textEditor);
        Document image = new Document("Image", LocalDate.now(), "Author2", 300, imageEditor);
        Document video = new Document("Video", LocalDate.now(), "Author3", 700, videoEditor);
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove "Image" and "Report"
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after multiple removals
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 700 KB
        assertEquals("Total size should be 700 KB after multiple removals", 700, totalSize);
    }
}