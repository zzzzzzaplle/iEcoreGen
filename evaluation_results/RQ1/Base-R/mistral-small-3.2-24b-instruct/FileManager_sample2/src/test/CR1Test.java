import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create a FileSystem instance and add documents with specified sizes
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100L);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200L);
        
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300L);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB
        assertEquals(600L, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create a FileSystem instance and add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150L);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250L);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1 from the FileSystem
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 250 KB
        assertEquals(250L, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create a FileSystem instance with no documents added
        // No documents added to fileSystem
        
        // Calculate total size of empty file system
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 0 KB
        assertEquals(0L, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create a FileSystem instance and add documents with mixed sizes
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50L);
        
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000L);
        
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250L);
        
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
        assertEquals(1300L, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create a FileSystem instance, add documents, then remove multiple
        Document report = new Document();
        report.setName("Report");
        report.setSize(400L);
        
        Document image = new Document();
        image.setName("Image");
        image.setSize(300L);
        
        Document video = new Document();
        video.setName("Video");
        video.setSize(700L);
        
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove "Image" (300 KB) and "Report" (400 KB)
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: Total document size = 700 KB
        assertEquals(700L, totalSize);
    }
}