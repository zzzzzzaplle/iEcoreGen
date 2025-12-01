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
        // SetUp: Create documents with different sizes
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        
        // Add documents to FileSystem
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 100 + 200 + 300 = 600
        assertEquals("Total size should be 600 for three documents", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create two documents
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        // Add documents to FileSystem
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 250 (only Document2 remains)
        assertEquals("Total size should be 250 after removing Document1", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: FileSystem is already empty (no documents added)
        
        // Calculate total size of empty FileSystem
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 0
        assertEquals("Total size should be 0 for empty FileSystem", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create documents with mixed sizes
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        
        // Add documents to FileSystem
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 50 + 1000 + 250 = 1300
        assertEquals("Total size should be 1300 for mixed size documents", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create three documents
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        
        // Add all documents to FileSystem
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove Image (300 KB) and Report (400 KB)
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 700 (only Video remains)
        assertEquals("Total size should be 700 after removing Image and Report", 700, totalSize);
    }
}