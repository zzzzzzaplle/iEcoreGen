import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Test Case 1: Calculate Total Size of Multiple Documents
        // Setup: Create FileSystem and add 3 documents with sizes 100, 200, 300 KB
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Execute: Calculate total document size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total should be 100 + 200 + 300 = 600 KB
        assertEquals("Total size should be 600 KB when documents have sizes 100, 200, 300", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Test Case 2: Calculate Total Size after Document Removal
        // Setup: Create FileSystem, add 2 documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Execute: Calculate total document size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total should be 250 KB (only Document2 remains)
        assertEquals("Total size should be 250 KB after removing 150 KB document", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Test Case 3: Empty FileSystem Calculation
        // Setup: FileSystem is created with no documents (empty by default)
        
        // Execute: Calculate total document size on empty file system
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total should be 0 for empty file system
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Test Case 4: Calculate Total Size of Documents with Mixed Sizes
        // Setup: Create FileSystem and add 3 documents with mixed sizes
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Execute: Calculate total document size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total should be 50 + 1000 + 250 = 1300 KB
        assertEquals("Total size should be 1300 KB for mixed size documents", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Test Case 5: Calculate Total Size after Multiple Removals
        // Setup: Create FileSystem, add 3 documents, then remove 2 of them
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
        
        // Remove "Image" and "Report"
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Execute: Calculate total document size after removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total should be 700 KB (only Video remains)
        assertEquals("Total size should be 700 KB after removing Image and Report", 700, totalSize);
    }
}