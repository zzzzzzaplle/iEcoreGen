import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;

public class CR2Test {
    
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    
    @Before
    public void setUp() {
        // Initialize FileSystem and editors before each test
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        textEditor.setName("Text Editor");
        imageEditor = new ImageEditor();
        imageEditor.setName("Image Editor");
        videoEditor = new VideoEditor();
        videoEditor.setName("Video Editor");
    }
    
    @Test
    public void testCase1_singleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // SetUp: Create documents for Text Editor
        Document doc1 = new Document();
        doc1.setName("Report");
        doc1.setSize(150);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setName("Essay");
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        Document doc3 = new Document();
        doc3.setName("Proposal");
        doc3.setSize(250);
        doc3.setEditor(textEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        
        // Call the method to compute average size for Text Editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify the average size for Text Editor
        assertEquals(200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_noDocumentsForEditor() {
        // Test Case 2: No Documents for an Editor
        // SetUp: No documents added for Image Editor
        // Call the method to compute average size for Image Editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify that Image Editor is not in the result map (since it has no documents)
        assertFalse(result.containsKey("Image Editor"));
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // SetUp: Create documents for Video Editor
        Document doc1 = new Document();
        doc1.setName("Video1");
        doc1.setSize(500);
        doc1.setEditor(videoEditor);
        
        Document doc2 = new Document();
        doc2.setName("Video2");
        doc2.setSize(1000);
        doc2.setEditor(videoEditor);
        
        Document doc3 = new Document();
        doc3.setName("Video3");
        doc3.setSize(750);
        doc3.setEditor(videoEditor);
        
        Document doc4 = new Document();
        doc4.setName("Video4");
        doc4.setSize(300);
        doc4.setEditor(videoEditor);
        
        // Add documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        
        // Call the method to compute average size for Video Editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify the average size for Video Editor
        assertEquals(637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // SetUp: Create documents for all three editors
        // Text Editor documents
        Document doc1 = new Document();
        doc1.setSize(100);
        doc1.setEditor(textEditor);
        
        Document doc2 = new Document();
        doc2.setSize(200);
        doc2.setEditor(textEditor);
        
        // Image Editor documents
        Document doc3 = new Document();
        doc3.setSize(1024);
        doc3.setEditor(imageEditor);
        
        Document doc4 = new Document();
        doc4.setSize(1536);
        doc4.setEditor(imageEditor);
        
        Document doc5 = new Document();
        doc5.setSize(512);
        doc5.setEditor(imageEditor);
        
        // Video Editor document
        Document doc6 = new Document();
        doc6.setSize(2048);
        doc6.setEditor(videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(doc1);
        fileSystem.addDocument(doc2);
        fileSystem.addDocument(doc3);
        fileSystem.addDocument(doc4);
        fileSystem.addDocument(doc5);
        fileSystem.addDocument(doc6);
        
        // Call the method to compute average sizes for all editors
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify average sizes for all editors
        assertEquals(150.0f, result.get("Text Editor"), 0.001f);
        assertEquals(1024.0f, result.get("Image Editor"), 0.001f);
        assertEquals(2048.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // SetUp: Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call the method to compute average size for Text Editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify the average size for Text Editor
        assertEquals(10.0f, result.get("Text Editor"), 0.001f);
    }
}