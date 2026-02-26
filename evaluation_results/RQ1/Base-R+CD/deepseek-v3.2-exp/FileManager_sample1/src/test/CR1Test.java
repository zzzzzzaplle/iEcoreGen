import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Test Case 1: Calculate Total Size of Multiple Documents
        // SetUp: Create documents with sizes 100, 200, 300 KB
        
        // Create and add Document1 with size 100 KB
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        fileSystem.addDocument(doc1);
        
        // Create and add Document2 with size 200 KB
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        fileSystem.addDocument(doc2);
        
        // Create and add Document3 with size 300 KB
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        fileSystem.addDocument(doc3);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 600 KB", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Test Case 2: Calculate Total Size after Document Removal
        // SetUp: Add two documents, remove one, then calculate total size
        
        // Create and add Document1 with size 150 KB
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        fileSystem.addDocument(doc1);
        
        // Create and add Document2 with size 250 KB
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        fileSystem.addDocument(doc2);
        
        // Remove Document1 from the FileSystem
        fileSystem.removeDocument(doc1);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 250 KB after removal", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Test Case 3: Empty FileSystem Calculation
        // SetUp: Create FileSystem with no documents
        
        // Calculate total size of empty FileSystem
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify that total size is 0 for empty FileSystem
        assertEquals("Total size should be 0 for empty FileSystem", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Test Case 4: Calculate Total Size of Documents with Mixed Sizes
        // SetUp: Add documents with sizes 50, 1000, 250 KB
        
        // Create and add DocA with size 50 KB
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        fileSystem.addDocument(docA);
        
        // Create and add DocB with size 1000 KB
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        fileSystem.addDocument(docB);
        
        // Create and add DocC with size 250 KB
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        fileSystem.addDocument(docC);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 1300 KB", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Test Case 5: Calculate Total Size after Multiple Removals
        // SetUp: Add three documents, remove two, then calculate total size
        
        // Create and add Report with size 400 KB
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        fileSystem.addDocument(report);
        
        // Create and add Image with size 300 KB
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        fileSystem.addDocument(image);
        
        // Create and add Video with size 700 KB
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        fileSystem.addDocument(video);
        
        // Remove Image (300 KB) and Report (400 KB)
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total size should be 700 KB after removals", 700, totalSize);
    }
}