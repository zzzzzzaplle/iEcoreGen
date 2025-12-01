import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    
    @Before
    public void setUp() {
        // Initialize fresh FileSystem before each test
        fileSystem = new FileSystem();
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Create editors and associate "Text Editor" with 3 documents
        Editor textEditor = fileSystem.getEditors().get("Text Editor");
        
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify the average size for Text Editor
        assertEquals(200.0, averageSizes.get("Text Editor"), 0.001);
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: Create editors but don't add any documents for Image Editor
        // FileSystem constructor already initializes all editors
        
        // Call the method to compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify that Image Editor is not in the result map (no documents associated)
        assertFalse("Image Editor should not be in results when no documents exist", 
                   averageSizes.containsKey("Image Editor"));
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Associate "Video Editor" with 4 documents of varying sizes
        Editor videoEditor = fileSystem.getEditors().get("Video Editor");
        
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        fileSystem.addDocument(doc1);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        fileSystem.addDocument(doc2);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        fileSystem.addDocument(doc3);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify the average size for Video Editor
        assertEquals(637.5, averageSizes.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Associate each editor with specified documents
        
        // Text Editor: 2 documents (100, 200)
        Editor textEditor = fileSystem.getEditors().get("Text Editor");
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        fileSystem.addDocument(textDoc1);
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        fileSystem.addDocument(textDoc2);
        
        // Image Editor: 3 documents (1024, 1536, 512)
        Editor imageEditor = fileSystem.getEditors().get("Image Editor");
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        fileSystem.addDocument(imageDoc1);
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        fileSystem.addDocument(imageDoc2);
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        fileSystem.addDocument(imageDoc3);
        
        // Video Editor: 1 document (2048)
        Editor videoEditor = fileSystem.getEditors().get("Video Editor");
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        fileSystem.addDocument(videoDoc1);
        
        // Call the method to compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify average sizes for all editors
        assertEquals(150.0, averageSizes.get("Text Editor"), 0.001);
        assertEquals(1024.0, averageSizes.get("Image Editor"), 0.001);
        assertEquals(2048.0, averageSizes.get("Video Editor"), 0.001);
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Associate "Text Editor" with 100 documents, each of size 10
        Editor textEditor = fileSystem.getEditors().get("Text Editor");
        
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average sizes
        Map<String, Double> averageSizes = fileSystem.computeAverageSizePerEditor();
        
        // Verify the average size for Text Editor
        assertEquals(10.0, averageSizes.get("Text Editor"), 0.001);
    }
}