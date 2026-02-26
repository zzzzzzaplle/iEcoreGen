import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem instance and add documents with sizes
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
        
        // Execute: Calculate total document size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total document size should be 600 KB
        assertEquals("Total size of 100+200+300 should be 600", 600, totalSize);
    }
    
    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem instance, add documents, then remove one
        Document doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        
        Document doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Execute: Calculate total document size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total document size should be 250 KB after removal
        assertEquals("Total size after removing 150KB document should be 250", 250, totalSize);
    }
    
    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: FileSystem instance with no documents
        // No documents added to fileSystem
        
        // Execute: Calculate total document size for empty file system
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total document size should be 0 KB
        assertEquals("Total size of empty file system should be 0", 0, totalSize);
    }
    
    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem instance and add documents with mixed sizes
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
        
        // Execute: Calculate total document size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total document size should be 1300 KB
        assertEquals("Total size of 50+1000+250 should be 1300", 1300, totalSize);
    }
    
    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem instance, add documents, then remove two
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
        fileSystem.removeDocument(image);
        fileSystem.removeDocument(report);
        
        // Execute: Calculate total document size after multiple removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify: Total document size should be 700 KB
        assertEquals("Total size after removing 300KB and 400KB should be 700", 700, totalSize);
    }
}