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
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 100, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 200, Editor.IMAGE_EDITOR);
        Document doc3 = new Document("Document3", LocalDate.now(), "Author3", 300, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size and verify expected output
        long totalSize = fileSystem.getTotalSize();
        assertEquals("Total size should be 600 KB", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create a FileSystem instance, add two documents, then remove one
        Document doc1 = new Document("Document1", LocalDate.now(), "Author1", 150, Editor.TEXT_EDITOR);
        Document doc2 = new Document("Document2", LocalDate.now(), "Author2", 250, Editor.IMAGE_EDITOR);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal and verify expected output
        long totalSize = fileSystem.getTotalSize();
        assertEquals("Total size should be 250 KB after removal", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create a FileSystem instance with no documents
        // No documents added to fileSystem
        
        // Calculate total size of empty file system and verify expected output
        long totalSize = fileSystem.getTotalSize();
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create a FileSystem instance and add three documents with mixed sizes
        Document docA = new Document("DocA", LocalDate.now(), "AuthorA", 50, Editor.TEXT_EDITOR);
        Document docB = new Document("DocB", LocalDate.now(), "AuthorB", 1000, Editor.IMAGE_EDITOR);
        Document docC = new Document("DocC", LocalDate.now(), "AuthorC", 250, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size and verify expected output
        long totalSize = fileSystem.getTotalSize();
        assertEquals("Total size should be 1300 KB", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create a FileSystem instance, add three documents, then remove two
        Document report = new Document("Report", LocalDate.now(), "Author1", 400, Editor.TEXT_EDITOR);
        Document image = new Document("Image", LocalDate.now(), "Author2", 300, Editor.IMAGE_EDITOR);
        Document video = new Document("Video", LocalDate.now(), "Author3", 700, Editor.VIDEO_EDITOR);
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals and verify expected output
        long totalSize = fileSystem.getTotalSize();
        assertEquals("Total size should be 700 KB after removals", 700, totalSize);
    }
}