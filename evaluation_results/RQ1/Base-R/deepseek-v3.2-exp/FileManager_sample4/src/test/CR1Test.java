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
        // SetUp: Create FileSystem instance and add three documents
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
        long totalSize = fileSystem.calculateTotalSize();
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem instance, add two documents, then remove one
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
        
        // Calculate total size after removal and verify expected output
        long totalSize = fileSystem.calculateTotalSize();
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create FileSystem instance with no documents
        // No documents added to fileSystem
        
        // Calculate total size for empty file system and verify expected output
        long totalSize = fileSystem.calculateTotalSize();
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem instance and add three documents with mixed sizes
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
        long totalSize = fileSystem.calculateTotalSize();
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
        
        // Remove "Image" and "Report"
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals and verify expected output
        long totalSize = fileSystem.calculateTotalSize();
        assertEquals(700, totalSize);
    }
}