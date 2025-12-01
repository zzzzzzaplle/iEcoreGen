import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_calculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create a FileSystem instance and add three documents with different sizes
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
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_calculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create a FileSystem instance, add two documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_emptyFileSystemCalculation() {
        // SetUp: Create a FileSystem instance with no documents
        // Calculate total size and verify expected output (should be 0)
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_calculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create a FileSystem instance and add three documents with mixed sizes
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
        
        // Calculate total size and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_calculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create a FileSystem instance, add three documents, then remove two
        Document docReport = new Document();
        docReport.setName("Report");
        docReport.setSize(400);
        
        Document docImage = new Document();
        docImage.setName("Image");
        docImage.setSize(300);
        
        Document docVideo = new Document();
        docVideo.setName("Video");
        docVideo.setSize(700);
        
        fileSystem.addDocument(docReport);
        fileSystem.addDocument(docImage);
        fileSystem.addDocument(docVideo);
        
        fileSystem.removeDocument(docImage);
        fileSystem.removeDocument(docReport);
        
        // Calculate total size after removals and verify expected output
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals(700, totalSize);
    }
}