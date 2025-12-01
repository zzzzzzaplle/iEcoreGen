import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    private FileSystem fileSystem;
    private TextEditor textEditor;
    private ImageEditor imageEditor;
    private VideoEditor videoEditor;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        fileSystem = new FileSystem();
        textEditor = new TextEditor();
        imageEditor = new ImageEditor();
        videoEditor = new VideoEditor();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        // Set editor names
        textEditor.setName("Text Editor");
        imageEditor.setName("Image Editor");
        videoEditor.setName("Video Editor");
        
        // Add editors to file system
        fileSystem.getEditors().add(textEditor);
        fileSystem.getEditors().add(imageEditor);
        fileSystem.getEditors().add(videoEditor);
    }
    
    @Test
    public void testCase1_SingleEditorWithMultipleDocuments() {
        // Test Case 1: Single Editor with Multiple Documents
        // Input: Calculate average document size for a single editor
        
        // Create documents for Text Editor
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
        
        // Call method to compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (150 + 200 + 250) / 3 = 200
        assertEquals(200.0f, result.get("TextEditor"), 0.001f);
        
        // Verify other editors are not in the result since they have no documents
        assertFalse(result.containsKey("ImageEditor"));
        assertFalse(result.containsKey("VideoEditor"));
    }
    
    @Test
    public void testCase2_NoDocumentsForAnEditor() {
        // Test Case 2: No Documents for an Editor
        // Input: Calculate average document size for an editor with no documents
        
        // Call method to compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Image Editor is not in the result since it has no documents
        assertFalse(result.containsKey("ImageEditor"));
        
        // Verify the map is empty since no documents are associated with any editor
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_EditorWithDocumentsOfVaryingSizes() {
        // Test Case 3: Editor with Documents of Varying Sizes
        // Input: Calculate average document size for an editor with varying sizes
        
        // Create documents for Video Editor with varying sizes
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
        
        // Call method to compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Video Editor average size = (500 + 1000 + 750 + 300) / 4 = 637.5
        assertEquals(637.5f, result.get("VideoEditor"), 0.001f);
        
        // Verify only Video Editor is in the result
        assertEquals(1, result.size());
        assertTrue(result.containsKey("VideoEditor"));
    }
    
    @Test
    public void testCase4_MultipleEditorsWithDocumentDistribution() {
        // Test Case 4: Multiple Editors with Document Distribution
        // Input: Calculate average document sizes for multiple editors with distributed documents
        
        // Create documents for Text Editor
        Document textDoc1 = new Document();
        textDoc1.setSize(100);
        textDoc1.setEditor(textEditor);
        
        Document textDoc2 = new Document();
        textDoc2.setSize(200);
        textDoc2.setEditor(textEditor);
        
        // Create documents for Image Editor
        Document imageDoc1 = new Document();
        imageDoc1.setSize(1024);
        imageDoc1.setEditor(imageEditor);
        
        Document imageDoc2 = new Document();
        imageDoc2.setSize(1536);
        imageDoc2.setEditor(imageEditor);
        
        Document imageDoc3 = new Document();
        imageDoc3.setSize(512);
        imageDoc3.setEditor(imageEditor);
        
        // Create document for Video Editor
        Document videoDoc1 = new Document();
        videoDoc1.setSize(2048);
        videoDoc1.setEditor(videoEditor);
        
        // Add all documents to file system
        fileSystem.addDocument(textDoc1);
        fileSystem.addDocument(textDoc2);
        fileSystem.addDocument(imageDoc1);
        fileSystem.addDocument(imageDoc2);
        fileSystem.addDocument(imageDoc3);
        fileSystem.addDocument(videoDoc1);
        
        // Call method to compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (100 + 200) / 2 = 150
        assertEquals(150.0f, result.get("TextEditor"), 0.001f);
        
        // Verify Image Editor average size = (1024 + 1536 + 512) / 3 = 1024
        assertEquals(1024.0f, result.get("ImageEditor"), 0.001f);
        
        // Verify Video Editor average size = 2048
        assertEquals(2048.0f, result.get("VideoEditor"), 0.001f);
        
        // Verify all three editors are in the result
        assertEquals(3, result.size());
    }
    
    @Test
    public void testCase5_LargeNumberOfDocumentsForOneEditor() {
        // Test Case 5: Large Number of Documents for One Editor
        // Input: Calculate average document size for editor with many documents
        
        // Create 100 documents for Text Editor, each with size 10
        for (int i = 0; i < 100; i++) {
            Document doc = new Document();
            doc.setName("Document" + (i + 1));
            doc.setSize(10);
            doc.setEditor(textEditor);
            fileSystem.addDocument(doc);
        }
        
        // Call method to compute average sizes per editor
        Map<String, Float> result = fileSystem.findAverageSizePerEditor();
        
        // Verify Text Editor average size = (10 * 100) / 100 = 10
        assertEquals(10.0f, result.get("TextEditor"), 0.001f);
        
        // Verify only Text Editor is in the result
        assertEquals(1, result.size());
        assertTrue(result.containsKey("TextEditor"));
    }
}