import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize file system with default editors
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Create documents for Text Editor only
        Document doc1 = new Document("Report", new Date(), "Author1", 150, textEditor);
        Document doc2 = new Document("Essay", new Date(), "Author2", 200, textEditor);
        Document doc3 = new Document("Proposal", new Date(), "Author3", 250, textEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for Text Editor = (150 + 200 + 250) / 3 = 200
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
        
        // Verify other editors are not in the result since they have no documents
        assertFalse("Image Editor should not be in result", result.containsKey("Image Editor"));
        assertFalse("Video Editor should not be in result", result.containsKey("Video Editor"));
    }
    
    @Test
    public void testCase2_noDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: No documents added to the file system
        
        // Call method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Empty map since no documents exist
        assertTrue("Result should be empty when no documents exist", result.isEmpty());
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Create documents for Video Editor only
        Document doc1 = new Document("Video1", new Date(), "Author1", 500, videoEditor);
        Document doc2 = new Document("Video2", new Date(), "Author2", 1000, videoEditor);
        Document doc3 = new Document("Video3", new Date(), "Author3", 750, videoEditor);
        Document doc4 = new Document("Video4", new Date(), "Author4", 300, videoEditor);
        
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for Video Editor = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertTrue("Result should contain Video Editor", result.containsKey("Video Editor"));
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
        
        // Verify other editors are not in the result since they have no documents
        assertFalse("Text Editor should not be in result", result.containsKey("Text Editor"));
        assertFalse("Image Editor should not be in result", result.containsKey("Image Editor"));
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Create documents for all three editors
        Document textDoc1 = new Document("Doc1", new Date(), "Author1", 100, textEditor);
        Document textDoc2 = new Document("Doc2", new Date(), "Author2", 200, textEditor);
        
        Document imageDoc1 = new Document("Image1", new Date(), "Author3", 1024, imageEditor);
        Document imageDoc2 = new Document("Image2", new Date(), "Author4", 1536, imageEditor);
        Document imageDoc3 = new Document("Image3", new Date(), "Author5", 512, imageEditor);
        
        Document videoDoc1 = new Document("Video1", new Date(), "Author6", 2048, videoEditor);
        
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: 
        // - Text Editor: (100 + 200) / 2 = 150
        // - Image Editor: (1024 + 1536 + 512) / 3 = 1024
        // - Video Editor: 2048
        assertEquals("Result should contain 3 editors", 3, result.size());
        
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);
        
        assertTrue("Result should contain Image Editor", result.containsKey("Image Editor"));
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);
        
        assertTrue("Result should contain Video Editor", result.containsKey("Video Editor"));
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Create 100 documents for Text Editor, each with size 10
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document("Document" + i, new Date(), "Author" + i, 10, textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average sizes
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Expected Output: Average size for Text Editor = (10 * 100) / 100 = 10
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
        
        // Verify other editors are not in the result since they have no documents
        assertFalse("Image Editor should not be in result", result.containsKey("Image Editor"));
        assertFalse("Video Editor should not be in result", result.containsKey("Video Editor"));
    }
}