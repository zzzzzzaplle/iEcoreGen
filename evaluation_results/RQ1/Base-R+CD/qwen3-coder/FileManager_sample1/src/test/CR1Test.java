import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() throws ParseException {
        // Create documents with specified sizes
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        doc2.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        
        Document doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        doc3.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size and verify
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total document size should be 600 KB", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() throws ParseException {
        // Create documents
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        doc1.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        doc2.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size and verify
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total document size should be 250 KB", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // Create empty file system (no documents added)
        
        // Calculate total size and verify
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total document size should be 0 KB for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() throws ParseException {
        // Create documents with mixed sizes
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        docA.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        docB.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        docC.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size and verify
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total document size should be 1300 KB", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() throws ParseException {
        // Create documents
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        report.setCreateDate(dateFormat.parse("2023-01-01 10:00:00"));
        
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        image.setCreateDate(dateFormat.parse("2023-01-02 10:00:00"));
        
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        video.setCreateDate(dateFormat.parse("2023-01-03 10:00:00"));
        
        // Add documents to file system
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove Image and Report
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size and verify
        int totalSize = fileSystem.calculateTotalDocumentSize();
        assertEquals("Total document size should be 700 KB", 700, totalSize);
    }
}