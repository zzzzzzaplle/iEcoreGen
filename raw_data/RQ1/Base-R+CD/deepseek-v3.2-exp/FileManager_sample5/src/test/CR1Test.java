import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    private FileSystem fileSystem;
    private Document doc1, doc2, doc3, docA, docB, docC, report, image, video;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        
        // Initialize documents for reuse in tests
        doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        
        doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        
        doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        
        docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        
        docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        
        docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        
        report = new Document();
        report.setName("Report");
        report.setSize(400);
        
        image = new Document();
        image.setName("Image");
        image.setSize(300);
        
        video = new Document();
        video.setName("Video");
        video.setSize(700);
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Test Case 1: Calculate Total Size of Multiple Documents
        // SetUp: Create FileSystem and add 3 documents
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 100 + 200 + 300 = 600 KB
        assertEquals("Total size should be 600 KB", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Test Case 2: Calculate Total Size after Document Removal
        // SetUp: Add documents and then remove one
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 250 KB (only doc2 remains)
        assertEquals("Total size should be 250 KB after removal", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Test Case 3: Empty FileSystem Calculation
        // SetUp: FileSystem with no documents (already empty from setUp)
        
        // Calculate total size of empty system
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 0 KB
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Test Case 4: Calculate Total Size of Documents with Mixed Sizes
        // SetUp: Add documents with mixed sizes
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 50 + 1000 + 250 = 1300 KB
        assertEquals("Total size should be 1300 KB for mixed sizes", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Test Case 5: Calculate Total Size after Multiple Removals
        // SetUp: Add documents and then remove two of them
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 700 KB (only video remains)
        assertEquals("Total size should be 700 KB after multiple removals", 700, totalSize);
    }
}