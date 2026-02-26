import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private FileSystem fileSystem;
    private Document doc1, doc2, doc3;
    private Editor editor;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        editor = new Editor("DefaultEditor");
        
        // Create reusable document instances
        doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        doc1.setEditor(editor);
        doc1.setCreationDate(LocalDate.now());
        
        doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        doc2.setEditor(editor);
        doc2.setCreationDate(LocalDate.now());
        
        doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        doc3.setEditor(editor);
        doc3.setCreationDate(LocalDate.now());
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem and add three documents
        fileSystem.addDocument(doc1); // Size: 100 KB
        fileSystem.addDocument(doc2); // Size: 200 KB
        fileSystem.addDocument(doc3); // Size: 300 KB
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 100 + 200 + 300 = 600 KB
        assertEquals("Total size should be 600 KB", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem, add two documents, then remove one
        fileSystem.addDocument(doc1); // Size: 150 KB
        fileSystem.addDocument(doc2); // Size: 250 KB
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 250 KB
        assertEquals("Total size should be 250 KB after removal", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create FileSystem with no documents
        // No documents added to fileSystem
        
        // Calculate total size of empty file system
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 0 KB
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem and add documents with mixed sizes
        Document docA = new Document();
        docA.setName("DocA");
        docA.setSize(50);
        docA.setEditor(editor);
        docA.setCreationDate(LocalDate.now());
        
        Document docB = new Document();
        docB.setName("DocB");
        docB.setSize(1000);
        docB.setEditor(editor);
        docB.setCreationDate(LocalDate.now());
        
        Document docC = new Document();
        docC.setName("DocC");
        docC.setSize(250);
        docC.setEditor(editor);
        docC.setCreationDate(LocalDate.now());
        
        fileSystem.addDocument(docA); // Size: 50 KB
        fileSystem.addDocument(docB); // Size: 1000 KB
        fileSystem.addDocument(docC); // Size: 250 KB
        
        // Calculate total size
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 50 + 1000 + 250 = 1300 KB
        assertEquals("Total size should be 1300 KB", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem, add three documents, then remove two
        Document report = new Document();
        report.setName("Report");
        report.setSize(400);
        report.setEditor(editor);
        report.setCreationDate(LocalDate.now());
        
        Document image = new Document();
        image.setName("Image");
        image.setSize(300);
        image.setEditor(editor);
        image.setCreationDate(LocalDate.now());
        
        Document video = new Document();
        video.setName("Video");
        video.setSize(700);
        video.setEditor(editor);
        video.setCreationDate(LocalDate.now());
        
        fileSystem.addDocument(report); // Size: 400 KB
        fileSystem.addDocument(image);  // Size: 300 KB
        fileSystem.addDocument(video);  // Size: 700 KB
        
        // Remove "Image" and "Report"
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Calculate total size after removals
        long totalSize = fileSystem.calculateTotalSize();
        
        // Expected Output: 700 KB
        assertEquals("Total size should be 700 KB after multiple removals", 700, totalSize);
    }
}