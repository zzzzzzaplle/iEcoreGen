import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_calculateTotalSizeOfMultipleDocuments() throws ParseException {
        // Create documents with specified sizes
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Assert expected output: 100 + 200 + 300 = 600 KB
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_calculateTotalSizeAfterDocumentRemoval() throws ParseException {
        // Create documents with specified sizes
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Assert expected output: 250 KB
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_emptyFileSystemCalculation() {
        // Create empty file system (no documents added)
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Assert expected output: 0 KB
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_calculateTotalSizeOfDocumentsWithMixedSizes() throws ParseException {
        // Create documents with specified sizes
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        
        // Add documents to file system
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Assert expected output: 50 + 1000 + 250 = 1300 KB
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_calculateTotalSizeAfterMultipleRemovals() throws ParseException {
        // Create documents with specified sizes
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        
        // Add documents to file system
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove Image and Report
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Assert expected output: 700 KB
        assertEquals(700, totalSize);
    }
}