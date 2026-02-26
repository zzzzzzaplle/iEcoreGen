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
        // Set up: Create documents for Text Editor
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
        
        // Call method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify result for Text Editor
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals("Text Editor average size should be 200", 200.0f, result.get("Text Editor"), 0.001f);
    }
    
    @Test
    public void testCase2_noDocumentsForEditor() {
        // Set up: No documents added to Image Editor
        
        // Call method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify result for Image Editor
        assertTrue("Result should contain Image Editor", result.containsKey("Image Editor"));
        assertEquals("Image Editor average size should be 0", 0.0f, result.get("Image Editor"), 0.001f);
    }
    
    @Test
    public void testCase3_editorWithDocumentsOfVaryingSizes() {
        // Set up: Create documents for Video Editor with varying sizes
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
        
        // Call method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify result for Video Editor
        assertTrue("Result should contain Video Editor", result.containsKey("Video Editor"));
        assertEquals("Video Editor average size should be 637.5", 637.5f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase4_multipleEditorsWithDocumentDistribution() {
        // Set up: Create documents for all three editors
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
        
        // Call method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify results for all editors
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertTrue("Result should contain Image Editor", result.containsKey("Image Editor"));
        assertTrue("Result should contain Video Editor", result.containsKey("Video Editor"));
        
        assertEquals("Text Editor average size should be 150", 150.0f, result.get("Text Editor"), 0.001f);
        assertEquals("Image Editor average size should be 1024", 1024.0f, result.get("Image Editor"), 0.001f);
        assertEquals("Video Editor average size should be 2048", 2048.0f, result.get("Video Editor"), 0.001f);
    }
    
    @Test
    public void testCase5_largeNumberOfDocumentsForOneEditor() {
        // Set up: Create 100 documents with size 10 for Text Editor
        for (int i = 1; i <= 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + i);
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average size per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify result for Text Editor
        assertTrue("Result should contain Text Editor", result.containsKey("Text Editor"));
        assertEquals("Text Editor average size should be 10", 10.0f, result.get("Text Editor"), 0.001f);
    }
}