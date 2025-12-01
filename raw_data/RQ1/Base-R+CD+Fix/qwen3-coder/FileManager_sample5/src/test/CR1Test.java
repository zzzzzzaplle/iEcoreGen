import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    
    private FileSystem fileSystem;
    private Document doc1;
    private Document doc2;
    private Document doc3;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem instance and add three documents
        doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        
        doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        
        doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem instance, add two documents, then remove one
        doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1 from the FileSystem
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: Total document size = 250 KB
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create FileSystem instance with no documents added
        // No documents added to the file system
        
        // Calculate total size of empty file system
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: Total document size = 0 KB
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem instance and add three documents with mixed sizes
        doc1 = new Document();
        doc1.setName("DocA");
        doc1.setSize(50);
        
        doc2 = new Document();
        doc2.setName("DocB");
        doc2.setSize(1000);
        
        doc3 = new Document();
        doc3.setName("DocC");
        doc3.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem instance, add three documents, then remove two
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove "Image" (300 KB) and "Report" (400 KB)
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: Total document size = 700 KB
        assertEquals(700, totalSize);
    }
}