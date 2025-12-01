import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    private FileSystem fileSystem;
    private Document doc1, doc2, doc3, doc4, doc5;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // Test Case 1: "Calculate Total Size of Multiple Documents"
        // SetUp: Create FileSystem and add 3 documents with sizes 100, 200, 300 KB
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
        
        // Expected Output: Total document size = 100 + 200 + 300 = 600 KB
        assertEquals(600, fileSystem.calculateTotalSize());
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // Test Case 2: "Calculate Total Size after Document Removal"
        // SetUp: Create FileSystem, add 2 documents, then remove one
        doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Expected Output: Total document size = 250 KB
        assertEquals(250, fileSystem.calculateTotalSize());
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Test Case 3: "Empty FileSystem Calculation"
        // SetUp: Create FileSystem with no documents added
        
        // Expected Output: Total document size = 0 KB
        assertEquals(0, fileSystem.calculateTotalSize());
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // Test Case 4: "Calculate Total Size of Documents with Mixed Sizes"
        // SetUp: Create FileSystem and add 3 documents with sizes 50, 1000, 250 KB
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
        
        // Expected Output: Total document size = 50 + 1000 + 250 = 1300 KB
        assertEquals(1300, fileSystem.calculateTotalSize());
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // Test Case 5: "Calculate Total Size after Multiple Removals"
        // SetUp: Create FileSystem, add 3 documents, then remove 2
        doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(400);
        
        doc2 = new Document();
        doc2.setName("Image");
        doc2.setSize(300);
        
        doc3 = new Document();
        doc3.setName("Video");
        doc3.setSize(700);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        fileSystem.removeDocument(doc2); // Remove "Image" (300 KB)
        fileSystem.removeDocument(doc1); // Remove "Report" (400 KB)
        
        // Expected Output: Total document size = 700 KB
        assertEquals(700, fileSystem.calculateTotalSize());
    }
}