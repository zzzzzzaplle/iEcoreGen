import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private FileSystem fileSystem;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_calculateTotalSizeOfMultipleDocuments() throws ParseException {
        // Create documents with specified sizes
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        Document doc1 = new Document("Document1", createDate, "Author1", 100, null);
        Document doc2 = new Document("Document2", createDate, "Author2", 200, null);
        Document doc3 = new Document("Document3", createDate, "Author3", 300, null);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected result: 100 + 200 + 300 = 600 KB
        assertEquals(600, totalSize);
    }
    
    @Test
    public void testCase2_calculateTotalSizeAfterDocumentRemoval() throws ParseException {
        // Create documents with specified sizes
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        Document doc1 = new Document("Document1", createDate, "Author1", 150, null);
        Document doc2 = new Document("Document2", createDate, "Author2", 250, null);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected result: 250 KB
        assertEquals(250, totalSize);
    }
    
    @Test
    public void testCase3_emptyFileSystemCalculation() {
        // Create empty file system (no documents added)
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected result: 0 KB
        assertEquals(0, totalSize);
    }
    
    @Test
    public void testCase4_calculateTotalSizeOfDocumentsWithMixedSizes() throws ParseException {
        // Create documents with specified sizes
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        Document docA = new Document("DocA", createDate, "AuthorA", 50, null);
        Document docB = new Document("DocB", createDate, "AuthorB", 1000, null);
        Document docC = new Document("DocC", createDate, "AuthorC", 250, null);
        
        // Add documents to file system
        fileSystem.addDocument(docA);
        fileSystem.addDocument(docB);
        fileSystem.addDocument(docC);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected result: 50 + 1000 + 250 = 1300 KB
        assertEquals(1300, totalSize);
    }
    
    @Test
    public void testCase5_calculateTotalSizeAfterMultipleRemovals() throws ParseException {
        // Create documents with specified sizes
        Date createDate = dateFormat.parse("2023-01-01 10:00:00");
        Document report = new Document("Report", createDate, "Author1", 400, null);
        Document image = new Document("Image", createDate, "Author2", 300, null);
        Document video = new Document("Video", createDate, "Author3", 700, null);
        
        // Add documents to file system
        fileSystem.addDocument(report);
        fileSystem.addDocument(image);
        fileSystem.addDocument(video);
        
        // Remove Image and Report
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected result: 700 KB
        assertEquals(700, totalSize);
    }
}