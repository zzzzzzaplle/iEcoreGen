import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;

public class CR1Test {
    private FileSystem fileSystem;
    private Document doc1, doc2, doc3, doc4;
    private Editor textEditor, imageEditor, videoEditor;

    @Before
    public void setUp() {
        // Initialize editors for use in tests
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }

    @Test
    public void testCase1_CalculateTotalSizeOfMultipleDocuments() {
        // SetUp: Create FileSystem instance and add documents
        fileSystem = new FileSystem();
        
        doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(200);
        doc2.setEditor(imageEditor);
        
        doc3 = new Document();
        doc3.setName("Document3");
        doc3.setSize(300);
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 100 + 200 + 300 = 600
        assertEquals("Total size should be 600 KB", 600, totalSize);
    }

    @Test
    public void testCase2_CalculateTotalSizeAfterDocumentRemoval() {
        // SetUp: Create FileSystem instance, add documents, then remove one
        fileSystem = new FileSystem();
        
        doc1 = new Document();
        doc1.setName("Document1");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        doc2 = new Document();
        doc2.setName("Document2");
        doc2.setSize(250);
        doc2.setEditor(imageEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        
        // Remove Document1
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removal
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 250 KB
        assertEquals("Total size should be 250 KB after removal", 250, totalSize);
    }

    @Test
    public void testCase3_EmptyFileSystemCalculation() {
        // SetUp: Create FileSystem instance with no documents
        fileSystem = new FileSystem();
        
        // Calculate total size on empty file system
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 0 KB
        assertEquals("Total size should be 0 for empty file system", 0, totalSize);
    }

    @Test
    public void testCase4_CalculateTotalSizeOfDocumentsWithMixedSizes() {
        // SetUp: Create FileSystem instance and add documents with mixed sizes
        fileSystem = new FileSystem();
        
        doc1 = new Document();
        doc1.setName("DocA");
        doc1.setSize(50);
        doc1.setEditor(textEditor);
        
        doc2 = new Document();
        doc2.setName("DocB");
        doc2.setSize(1000);
        doc2.setEditor(imageEditor);
        
        doc3 = new Document();
        doc3.setName("DocC");
        doc3.setSize(250);
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Calculate total size
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 50 + 1000 + 250 = 1300 KB
        assertEquals("Total size should be 1300 KB", 1300, totalSize);
    }

    @Test
    public void testCase5_CalculateTotalSizeAfterMultipleRemovals() {
        // SetUp: Create FileSystem instance, add documents, then remove multiple
        fileSystem = new FileSystem();
        
        doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(400);
        doc1.setEditor(textEditor);
        
        doc2 = new Document();
        doc2.setName("Image");
        doc2.setSize(300);
        doc2.setEditor(imageEditor);
        
        doc3 = new Document();
        doc3.setName("Video");
        doc3.setSize(700);
        doc3.setEditor(videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Remove "Image" and "Report"
        fileSystem.removeDocument(doc2);
        fileSystem.removeDocument(doc1);
        
        // Calculate total size after removals
        int totalSize = fileSystem.calculateTotalDocumentSize();
        
        // Verify expected output: 700 KB
        assertEquals("Total size should be 700 KB after multiple removals", 700, totalSize);
    }
}