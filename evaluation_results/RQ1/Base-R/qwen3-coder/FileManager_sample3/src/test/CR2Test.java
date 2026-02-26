import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize file system before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Create documents for "Text Editor" only
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor("Text Editor");
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor("Text Editor");
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor("Text Editor");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify results
        assertEquals("Text Editor average should be 200", 200.0, result.get("Text Editor"), 0.001);
        assertEquals("Image Editor should be 0 with no documents", 0.0, result.get("Image Editor"), 0.001);
        assertEquals("Video Editor should be 0 with no documents", 0.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: No documents added to file system
        
        // Call method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify all editors have average of 0
        assertEquals("Text Editor should be 0 with no documents", 0.0, result.get("Text Editor"), 0.001);
        assertEquals("Image Editor should be 0 with no documents", 0.0, result.get("Image Editor"), 0.001);
        assertEquals("Video Editor should be 0 with no documents", 0.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Create documents for "Video Editor" only with varying sizes
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor("Video Editor");
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor("Video Editor");
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor("Video Editor");
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor("Video Editor");
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify results
        assertEquals("Video Editor average should be 637.5", 637.5, result.get("Video Editor"), 0.001);
        assertEquals("Text Editor should be 0 with no documents", 0.0, result.get("Text Editor"), 0.001);
        assertEquals("Image Editor should be 0 with no documents", 0.0, result.get("Image Editor"), 0.001);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Create documents for all three editors
        
        // Text Editor documents
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor("Text Editor");
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor("Text Editor");
        
        // Image Editor documents
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor("Image Editor");
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor("Image Editor");
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor("Image Editor");
        
        // Video Editor document
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor("Video Editor");
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify results for all editors
        assertEquals("Text Editor average should be 150", 150.0, result.get("Text Editor"), 0.001);
        assertEquals("Image Editor average should be 1024", 1024.0, result.get("Image Editor"), 0.001);
        assertEquals("Video Editor average should be 2048", 2048.0, result.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Create 100 documents for "Text Editor", each with size 10
        
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + (i + 1));
            doc.setSize(10);
            doc.setEditor("Text Editor");
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average sizes
        Map<String, Double> result = fileSystem.computeAverageSizeByEditor();
        
        // Verify results
        assertEquals("Text Editor average should be 10", 10.0, result.get("Text Editor"), 0.001);
        assertEquals("Image Editor should be 0 with no documents", 0.0, result.get("Image Editor"), 0.001);
        assertEquals("Video Editor should be 0 with no documents", 0.0, result.get("Video Editor"), 0.001);
    }
}