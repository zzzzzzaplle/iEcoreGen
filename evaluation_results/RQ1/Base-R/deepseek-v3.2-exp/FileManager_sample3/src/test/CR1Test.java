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
        // SetUp: Create FileSystem and add three documents
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
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Verify expected output: 100 + 200 + 300 = 600
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem and add two documents
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
        
        // Calculate total size after removal
        long totalSize = fileSystem.calculateTotalSize();
        
        // Verify expected output: 250
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: FileSystem is already empty due to @Before setup
        
        // Calculate total size of empty file system
        long totalSize = fileSystem.calculateTotalSize();
        
        // Verify expected output: 0
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem and add three documents with mixed sizes
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
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Verify expected output: 50 + 1000 + 250 = 1300
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem and add three documents
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
        
        // Remove Image and Report
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals
        long totalSize = fileSystem.calculateTotalSize();
        
        // Verify expected output: 700
        assertEquals(700, totalSize);
    }
}