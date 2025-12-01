import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR1Test {
    
    private FileSystem fileSystem;
    private Document doc1, doc2, doc3, doc4, doc5;
    private Author author1, author2, author3;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        author1 = new Author("Author1");
        author2 = new Author("Author2");
        author3 = new Author("Author3");
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem instance and add documents with sizes 100, 200, 300 KB
        doc1 = new Document("Document1", LocalDate.now(), author1, 100L, Editor.TEXT_EDITOR);
        doc2 = new Document("Document2", LocalDate.now(), author2, 200L, Editor.IMAGE_EDITOR);
        doc3 = new Document("Document3", LocalDate.now(), author3, 300L, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 100 + 200 + 300 = 600 KB
        assertEquals("Total size should be 600 KB", 600L, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem instance and add documents
        doc1 = new Document("Document1", LocalDate.now(), author1, 150L, Editor.TEXT_EDITOR);
        doc2 = new Document("Document2", LocalDate.now(), author2, 250L, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 250 KB
        assertEquals("Total size should be 250 KB after removal", 250L, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create FileSystem instance with no documents added
        // No documents added to fileSystem
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 0 KB
        assertEquals("Total size should be 0 KB for empty file system", 0L, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem instance and add documents with mixed sizes
        doc1 = new Document("DocA", LocalDate.now(), author1, 50L, Editor.TEXT_EDITOR);
        doc2 = new Document("DocB", LocalDate.now(), author2, 1000L, Editor.IMAGE_EDITOR);
        doc3 = new Document("DocC", LocalDate.now(), author3, 250L, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 50 + 1000 + 250 = 1300 KB
        assertEquals("Total size should be 1300 KB for mixed document sizes", 1300L, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem instance and add documents
        doc1 = new Document("Report", LocalDate.now(), author1, 400L, Editor.TEXT_EDITOR);
        doc2 = new Document("Image", LocalDate.now(), author2, 300L, Editor.IMAGE_EDITOR);
        doc3 = new Document("Video", LocalDate.now(), author3, 700L, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove "Image" and "Report"
        fileSystem.removeDocument(doc2); // Image (300 KB)
        fileSystem.removeDocument(doc1); // Report (400 KB)
        
        // Calculate total size after removals
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 700 KB
        assertEquals("Total size should be 700 KB after multiple removals", 700L, totalSize);
    }
}