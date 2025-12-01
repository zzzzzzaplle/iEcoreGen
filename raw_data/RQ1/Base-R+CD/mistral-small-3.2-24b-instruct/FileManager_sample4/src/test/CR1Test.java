import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    private FileSystem fileSystem;
    private Document doc1;
    private Document doc2;
    private Document doc3;
    private Document doc4;
    private Document doc5;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        doc1 = new Document();
        doc2 = new Document();
        doc3 = new Document();
        doc4 = new Document();
        doc5 = new Document();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem and add documents with sizes 100, 200, 300 KB
        doc1.setSize(100);
        doc2.setSize(200);
        doc3.setSize(300);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 600 KB
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem, add documents with sizes 150, 250 KB, then remove one
        doc1.setSize(150);
        doc2.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 250 KB
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create FileSystem with no documents
        // Calculate total size on empty system
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 0 KB
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem and add documents with sizes 50, 1000, 250 KB
        doc1.setSize(50);
        doc2.setSize(1000);
        doc3.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 1300 KB
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem, add documents with sizes 400, 300, 700 KB, then remove two
        doc1.setSize(400);
        doc2.setSize(300);
        doc3.setSize(700);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.removeDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Expected Output: 700 KB
        assertEquals(700, totalSize);
    }
}